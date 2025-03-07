package sio.demoprojetjava.services;

import sio.demoprojetjava.repositories.StatistiquesRepository;

import java.util.HashMap;

public class StatistiquesServices {
    private StatistiquesRepository statistiquesRepository;

    public StatistiquesServices() {
        this.statistiquesRepository = new StatistiquesRepository();
    }

    public HashMap<String, Integer> getNbReservations() {
        return statistiquesRepository.getNbReservations();
    }
}
