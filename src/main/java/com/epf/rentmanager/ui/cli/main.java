package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        // Création d'une instance de ClientUi
        ClientUi clientUi = ClientUi.getInstance();
        //creation d'une instance de vehiculeUi
        VehiculeUi vehiculeUi = VehiculeUi.getInstance();
        //creation d'une instance de vehiculeUi
        ReservationUi reservationUi = ReservationUi.getInstance();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Choisissez une option :");
            System.out.println("a. Créer un Client");
            System.out.println("b. Lister tous les Clients");
            System.out.println("c. Supprimer un client à partir de son id");
            System.out.println("d. Créer un Véhicule");
            System.out.println("e. Lister tous les Véhicules");
            System.out.println("f. Supprimer un Véhicule à partir de son id");
            System.out.println("g. Créer une Réservation");
            System.out.println("h. Lister toutes les Réservations");
            System.out.println("i. Lister toutes les Réservations par id client");
            System.out.println("j. Lister toutes les Réservations par id véhicule");
            System.out.println("k. Supprimer une Réservations à partir de son id");
            System.out.println("l. Quitter");

            String choix = scanner.nextLine();
            switch (choix) {
                case "a":
                    //not working
                    System.out.println("Création d'un nouveau client :");
                    long newClientId = clientUi.createClient();
                    System.out.println("Nouveau client créé avec l'ID : " + newClientId);
                    break;
                case "b":
                    System.out.println("Liste de tous les clients :");
                    clientUi.AllClient().forEach(System.out::println);
                    break;
                case "c":
                    System.out.println("Suppresion d'un client :");
                    long newIdc = clientUi.delete();
                    System.out.println("client supprimer avec succès");
                    break;
                case "d":
                    System.out.println("Création d'un nouveau véhicule :");
                    long newVehicleId = vehiculeUi.createVehicule();
                    System.out.println("Nouveau véhicule créé avec l'ID : " + newVehicleId);
                    break;
                case "e":
                    System.out.println("Liste de tous les véhicules :");
                    for (Vehicule vehicule : vehiculeUi.AllVehicule()) {
                        System.out.println(vehicule);
                    }
                    break;
                case "f":
                    System.out.println("Suppresion d'un véhicule :");
                    long newIdv = vehiculeUi.delete();
                    System.out.println("véhicule supprimer avec succès");
                    break;
                case "g":
                    System.out.println("Création d'une nouvelle reservation :");
                    long newResId = reservationUi.createReservation();
                    System.out.println("Nouvelle reservation créé avec l'ID : " + newResId);
                    break;
                case "h":
                    System.out.println("Liste de toutes les reservations :");
                    for (Reservation reservation : reservationUi.AllReservation()) {
                        System.out.println(reservation);
                    }
                    break;
                case "i":
                    System.out.println("Liste de toutes les reservations par id client :");
                    for (Reservation reservation : reservationUi.findByClient()) {
                        System.out.println(reservation);
                    }
                    break;
                case "j":
                    System.out.println("Liste de toutes les reservations par id véhicule :");
                    for (Reservation reservation : reservationUi.findByVehicule()) {
                        System.out.println(reservation);
                    }
                    break;
                case "k":
                    System.out.println("Suppresion d'une reservation :");
                    long newIdr = reservationUi.delete();
                    System.out.println("reservation supprimer avec succès");
                    break;
                case "l":
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez saisir à nouveau.");
            }
        }
        scanner.close();
    }
}
