package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int client_id;
    private int vehicule_id;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(){}

    public Reservation(int id, int client_id, int vehicule_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicule_id = vehicule_id;
        this.debut = debut;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getVehicule_id() {
        return vehicule_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setId(){
        this.id = id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setVehicule_id(int vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }


    @Override
    public String toString() {
        return "Reservation est composé de {" +
                "id unique= " + id +
                ", client_id= " + client_id +
                ", vehicule_id= " + vehicule_id +
                ", debut= " + debut +
                ", fin= " + fin +
                '}';
    }
}
