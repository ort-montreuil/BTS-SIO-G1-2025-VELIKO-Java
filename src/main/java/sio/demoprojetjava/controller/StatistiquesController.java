package sio.demoprojetjava.controller;

import sio.demoprojetjava.services.StatistiquesServices;

import java.util.HashMap;

public class StatistiquesController {
    private StatistiquesServices statistiquesServices;

    public StatistiquesController() {
        this.statistiquesServices = new StatistiquesServices();
    }

    public HashMap<String, Integer> getNbReservations() {
       return statistiquesServices.getNbReservations();
    }
}
