package sio.demoprojetjava.services;

import sio.demoprojetjava.model.Reservations;
import sio.demoprojetjava.repositories.StatistiquesRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StatistiquesServices {
    private StatistiquesRepository statistiquesRepository;

    public StatistiquesServices() {
        this.statistiquesRepository = new StatistiquesRepository();
    }

    public HashMap<String, Integer> getNbReservations() {
        return statistiquesRepository.getNbReservations();
    }

 // public HashMap<String, Integer> getReserationParPeriode() {
 //       return statistiquesRepository.getReserationParPeriode();
 // }

    public ArrayList<Reservations> getNbResa() throws SQLException {
        return statistiquesRepository.getNbResa();
    }
}