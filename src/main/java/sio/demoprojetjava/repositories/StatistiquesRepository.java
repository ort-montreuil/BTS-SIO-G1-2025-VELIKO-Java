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

    // nb user actif
    public HashMap<String, Integer> getUserPlusActif() {
        HashMap<String, Integer> datas = new HashMap<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                "SELECT reservation.id, user.email, reservation.date_reservation, reservation.type_velo, " +
                        "reservation.station_id_depart, reservation.station_id_arrivee " +
                        "FROM reservation JOIN user ON reservation.id_user = user.id");
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

    // resa pr station de depart
    public HashMap<String, Integer> getResaParStationDepart() {
        HashMap<String, Integer> datas = new HashMap<>();
        try (PreparedStatement preparedStatement = cnx.prepareStatement(
                "SELECT station.name, COUNT(*) AS nb_reservations " +
                        "FROM reservation " +
                        "JOIN station ON reservation.station_id_depart = station.station_id " +
                        "GROUP BY station.name " +
                        "ORDER BY nb_reservations DESC");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                datas.put(resultSet.getString("name"), resultSet.getInt("nb_reservations"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

}
