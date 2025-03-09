package sio.demoprojetjava.controller;

import sio.demoprojetjava.model.Reservations;
import sio.demoprojetjava.services.StatistiquesServices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StatistiquesController {
    private StatistiquesServices statistiquesServices;

    public StatistiquesController() {
        this.statistiquesServices = new StatistiquesServices();
    }

    public HashMap<String, Integer> getNbReservations() {
       return statistiquesServices.getNbReservations();
    }

//    public HashMap<String,Integer> getReserationParPeriode()
// {
    // return statistiquesServices.getReserationParPeriode();
 //}

    public ArrayList<Reservations>getNbResa() throws SQLException {
        return statistiquesServices.getNbResa();
    }

    public int getLesStations() {
        return statistiquesServices.getLesStations();
    }

    public int getLesUser() {
        return statistiquesServices.getLesUser();
    }

    public HashMap<String, Integer> getUserPlusActif() {
        return statistiquesServices.getUserPlusActif();
    }
}
