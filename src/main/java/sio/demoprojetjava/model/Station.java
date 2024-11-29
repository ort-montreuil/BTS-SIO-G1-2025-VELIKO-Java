package sio.demoprojetjava.model;

import sio.demoprojetjava.DataSourceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Station  {
    private double latitude;
    private double longitude;


    public Station(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

//    public static List<Station> getStations() throws SQLException {
//        List<Station> stations = new ArrayList<>();
//        Connection cnx = DataSourceProvider.getCnx();
//        Statement stmt = cnx.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT lat, lon FROM station");
//        while (rs.next()) {
//            double latitude = rs.getDouble("lat");
//            double longitude = rs.getDouble("lon");
//            stations.add(new Station(latitude, longitude));
//        }
//        return stations;
//    }
}