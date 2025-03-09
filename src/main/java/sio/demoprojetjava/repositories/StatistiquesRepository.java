package sio.demoprojetjava.repositories;

import sio.demoprojetjava.model.Reservations;
import sio.demoprojetjava.tools.DataSourceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StatistiquesRepository {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public StatistiquesRepository() {
        this.cnx = DataSourceProvider.getCnx();
    }

    // nb resa par station
    public HashMap<String, Integer> getNbReservations() {
        HashMap<String, Integer> datas = new HashMap<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                "SELECT s.name, COUNT(r.id) AS nb_reservations FROM station s " +
                        "JOIN reservation r ON s.station_id = r.station_id_depart " +
                        "GROUP BY s.station_id ORDER BY nb_reservations DESC LIMIT 10")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    datas.put(resultSet.getString("name"), resultSet.getInt("nb_reservations"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }


    // date_resa par date
    public ArrayList<Reservations> getNbResa() {
        ArrayList<Reservations> tableauDate = new ArrayList<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                "SELECT date_reservation, COUNT(id) AS total_reservations " +
                        "FROM reservation " +
                        "GROUP BY date_reservation " +
                        "ORDER BY date_reservation DESC");
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                tableauDate.add(new Reservations(rs.getDate("date_reservation"), rs.getInt("total_reservations")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableauDate;
    }


    // nb stations
    public int getLesStations() {
        int nbStations = 0;
        try (PreparedStatement ps = cnx.prepareStatement("SELECT COUNT(*) FROM station");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                nbStations = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nbStations;
    }

    // nb user
    public int getLesUser() {
        int nbUser = 0;
        try (PreparedStatement ps = cnx.prepareStatement("SELECT COUNT(*) FROM user");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                nbUser = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nbUser;
    }

    // nb user actif
    public HashMap<String, Integer> getUserPlusActif() {
        HashMap<String, Integer> datas = new HashMap<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                "SELECT reservation.id, user.email, reservation.date_reservation, reservation.type_velo, " +
                        "reservation.station_id_depart, reservation.station_id_arrivee " +
                        "FROM reservation JOIN user ON reservation.id_user = user.id limit 5");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Modify accordingly if you need user-specific stats
                datas.put(resultSet.getString("email"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }


    //top 10 des users avec le plus de reservations
    public HashMap<String, Integer> getTop10UsersReservations() {
        HashMap<String, Integer> topUsers = new HashMap<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                """
                                SELECT user.email, COUNT(reservation.id) AS nb_reservations
                                                FROM reservation
                                                JOIN user ON reservation.id_user = user.id
                                                GROUP BY user.email
                                                ORDER BY nb_reservations DESC
                                                LIMIT 10
                                                
                        """);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                topUsers.put(resultSet.getString("email"), resultSet.getInt("nb_reservations"));
            }
        } catch (SQLException e) {
            e.printStackTrace(
            );
        }

        return


                topUsers;
        }





    //arrondissemtns

    public HashMap<String, Integer> getArrondissements() {
        HashMap<String, Integer> datas = new HashMap<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                "SELECT CASE " +
                        "WHEN LEFT(user.code_postale, 2) = '75' THEN 'Paris' " +
                        "WHEN LEFT(user.code_postale, 2) = '93' THEN 'Seine-Saint-Denis (93)' " +
                        "WHEN LEFT(user.code_postale, 2) = '94' THEN 'Val-de-Marne (94)' " +
                        "WHEN LEFT(user.code_postale, 2) = '92' THEN 'Hauts-de-Seine (92)' " +
                        "WHEN LEFT(user.code_postale, 2) = '91' THEN 'Essonne (91)' " +
                        "WHEN LEFT(user.code_postale, 2) = '95' THEN 'Val-d''Oise (95)' " +
                        "ELSE 'Autres' END AS arrondissement, " +
                        "COUNT(*) AS nb_users " +
                        "FROM user " +
                        "GROUP BY arrondissement")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    datas.put(resultSet.getString("arrondissement"), resultSet.getInt("nb_users"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }





    //top 10 des stations avec le plus de capacité
    public HashMap<String, Integer> getTop10StationsCapacite() {
        HashMap<String, Integer> topStations = new HashMap<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                """
                SELECT station.name, station.capacity FROM station ORDER BY station.capacity DESC LIMIT 10;
                """);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                topStations.put(resultSet.getString("name"), resultSet.getInt("capacity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topStations;
    }



    public HashMap<String, Integer> getAgesUser() throws SQLException {
        HashMap<String, Integer> getAgesUser = new HashMap<>();
        PreparedStatement preparedStatement = cnx.prepareStatement(
                "SELECT " +
                        "    SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) BETWEEN 0 AND 17 THEN 1 ELSE 0 END) AS age_0_17, " +
                        "    SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) BETWEEN 18 AND 25 THEN 1 ELSE 0 END) AS age_18_25, " +
                        "    SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) BETWEEN 26 AND 35 THEN 1 ELSE 0 END) AS age_26_35, " +
                        "    SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) BETWEEN 36 AND 50 THEN 1 ELSE 0 END) AS age_36_50, " +
                        "    SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) >= 51 THEN 1 ELSE 0 END) AS age_51_plus " +
                        "FROM user;");
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            getAgesUser.put("0-17 ans", resultSet.getInt("age_0_17"));
            getAgesUser.put("18-25 ans", resultSet.getInt("age_18_25"));
            getAgesUser.put("26-35 ans", resultSet.getInt("age_26_35"));
            getAgesUser.put("36-50 ans", resultSet.getInt("age_36_50"));
            getAgesUser.put("51+ ans", resultSet.getInt("age_51_plus"));
        }
        return getAgesUser;
    }








    //type de velo

    public HashMap<String, Integer> getTypeVeloByReservation() throws SQLException {
        HashMap<String, Integer> typeVeloByReservation = new HashMap<>();
        PreparedStatement preparedStatement = cnx.prepareStatement("SELECT reservation.type_velo, COUNT(*)\n" +
                "FROM reservation\n" +
                "WHERE reservation.type_velo != ''\n" +
                "GROUP BY reservation.type_velo\n");
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            typeVeloByReservation.put(resultSet.getString(1), resultSet.getInt(2));

        }
        return typeVeloByReservation;
    }

}











