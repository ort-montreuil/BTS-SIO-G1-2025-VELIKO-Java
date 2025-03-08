package sio.demoprojetjava.model;

import java.util.Date;

public class Reservations {

    private int idResa;
    private int idUser;
    private Date date_resa;
    private String typeVelos;
    private int idDep;
    private  int idArrivee;
    private int nbResa;

    public Reservations(int idResa, int idUser, Date date_resa, String typeVelos, int idDep, int idArrivee, int nbResa) {
        this.idResa = idResa;
        this.idUser = idUser;
        this.date_resa = date_resa;
        this.typeVelos = typeVelos;
        this.idDep = idDep;
        this.idArrivee = idArrivee;
        this.nbResa = nbResa;
    }
    public Reservations(Date date_resa,int nbResa) {
        this.date_resa = date_resa;
        this.nbResa = nbResa;
    }


    public int getIdResa() {
        return idResa;
    }

    public void setIdResa(int idResa) {
        this.idResa = idResa;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDate_resa() {
        return date_resa;
    }

    public void setDate_resa(Date date_resa) {
        this.date_resa = date_resa;
    }

    public String getTypeVelos() {
        return typeVelos;
    }

    public void setTypeVelos(String typeVelos) {
        this.typeVelos = typeVelos;
    }

    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    public int getIdArrivee() {
        return idArrivee;
    }

    public void setIdArrivee(int idArrivee) {
        this.idArrivee = idArrivee;
    }

    public int getNbResa() {
        return nbResa;
    }

    public void setNbResa(int nbResa) {
        this.nbResa = nbResa;
    }
}
