package sio.demoprojetjava.services;

import sio.demoprojetjava.model.Reservations;
import sio.demoprojetjava.model.Station;
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

    public int getLesStations() {
        return statistiquesRepository.getLesStations();
    }

    public int getLesUser() {
        return statistiquesRepository.getLesUser();
    }

    public HashMap<String, Integer> getUserPlusActif() {
        return statistiquesRepository.getUserPlusActif();
    }


    public HashMap<String, Integer> getTop10UsersReservations() {
        return statistiquesRepository.getTop10UsersReservations();
    }

    public HashMap<String, Integer> getTop10StationsCapacite() {
        return statistiquesRepository.getTop10StationsCapacite();
    }

    public HashMap<String, Integer> getAgesUser() throws SQLException {
        return statistiquesRepository.getAgesUser();
    }




    public HashMap<String, Integer> getTypeVeloByReservation() throws SQLException {
        return statistiquesRepository.getTypeVeloByReservation();

    }

    public HashMap<String, Integer> getArrondissements() {
        return statistiquesRepository.getArrondissements();
    }

}