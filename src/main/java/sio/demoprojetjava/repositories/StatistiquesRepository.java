package sio.demoprojetjava.repositories;

import sio.demoprojetjava.model.Reservations;
import sio.demoprojetjava.tools.DataSourceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StatistiquesRepository {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public StatistiquesRepository() {
        this.cnx = DataSourceProvider.getCnx();
    }

    public HashMap<String, Integer> getNbReservations() {
        HashMap<String, Integer> datas = new HashMap<>();
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT s.name, COUNT(r.id) AS nb_reservations FROM station s JOIN reservation r ON s.station_id = r.station_id_depart GROUP BY s.station_id ORDER BY nb_reservations DESC LIMIT 10;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                datas.put(resultSet.getString("name"), resultSet.getInt("nb_reservations"));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    public HashMap<String, Integer> getReserationParPeriode() {
        HashMap<String, Integer> datas = new HashMap<>();
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT \n" +
                    "    CASE \n" +
                    "        WHEN HOUR(heure_debut) BETWEEN 6 AND 11 THEN 'Matin'\n" +
                    "        WHEN HOUR(heure_debut) BETWEEN 12 AND 17 THEN 'Après-midi'\n" +
                    "        WHEN HOUR(heure_debut) BETWEEN 18 AND 23 THEN 'Soir'\n" +
                    "        ELSE 'Nuit'\n" +
                    "    END AS periode,\n" +
                    "    COUNT(*) AS nb_reservations\n" +
                    "FROM reservation\n" +
                    "GROUP BY periode\n" +
                    "ORDER BY FIELD(periode, 'Matin', 'Après-midi', 'Soir', 'Nuit');\n");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                datas.put(resultSet.getString("periode"), resultSet.getInt("nb_reservations"));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas;
    }

    public HashMap<String, Integer> getUserPlusActif() {
        HashMap<String, Integer> datas = new HashMap<>();
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement("SELECT email_user, COUNT(*) AS nb_reservations\n" +
                    "FROM reservation\n" +
                    "GROUP BY email_user\n" +
                    "ORDER BY nb_reservations DESC\n" +
                    "LIMIT 10;\n");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                datas.put(resultSet.getString("nom"), resultSet.getInt("nb_reservations"));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    public int getLesStations() throws SQLException {
        int nbVelos = 0;
        PreparedStatement ps = cnx.prepareStatement("SELECT COUNT(*) FROM station;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            nbVelos = rs.getInt(1);
        }
        return nbVelos;
    }

    public int getLesUser() throws SQLException {
        int nbUser = 0;
        PreparedStatement ps = cnx.prepareStatement("SELECT COUNT(*) FROM user;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            nbUser = rs.getInt(1);
        }
        return nbUser;
    }

    public ArrayList<Reservations> getNbResa() throws SQLException {
        ArrayList<Reservations> tableauDate = new ArrayList<>();
        PreparedStatement ps = cnx.prepareStatement("SELECT date_reservation, COUNT(id) AS total_reservations " +
                "FROM reservation " +
                "GROUP BY date_reservation " +
                "ORDER BY date_reservation DESC;");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            tableauDate.add(new Reservations(rs.getDate("date_reservation"), rs.getInt("total_reservations")));
        }

        // Fermeture des ressources
        rs.close();
        ps.close();

        return tableauDate;
      }
}
