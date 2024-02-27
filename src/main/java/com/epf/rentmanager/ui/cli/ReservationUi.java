package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class ReservationUi {

    private final ReservationService reservationService;
    public static ReservationUi instance;

    private ReservationUi() {
        this.reservationService = ReservationService.getInstance();
    }

    public static ReservationUi getInstance() {
        if (instance == null) {
            instance = new ReservationUi();
        }
        return instance;
    }

    public long createReservation() {
        int id = 0;

        int id_client = IOUtils.readInt("Entrez l'id du client: ");
        int id_vehicule = IOUtils.readInt("Entrez l'id du véhicule: ");
        LocalDate debut = IOUtils.readDate("Entrez la date de début de la reservation (format: jj/mm/aaaa): ", true);
        LocalDate fin = IOUtils.readDate("Entrez la date de fin de la reservation (format: jj/mm/aaaa): ", true);

        try{
            Reservation reservation = new Reservation(id, id_client, id_vehicule, debut, fin);
            // Appeler la méthode de création de reservation
            long id_res = reservationService.create(reservation);
            System.out.println("Client créé transmis à service avec succès");
            return id_client;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

    public List<Reservation> AllReservation(){
        try{
            List<Reservation> reservations = reservationService.findAll();
            System.out.println("All reservation tranmis à service avec succès");
            return reservations;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

    public List<Reservation> findByClient(){
        try{
            long id = IOUtils.readInt("Entrez l'id du client pour afficher ces réservations: ");
            return reservationService.findByClient(id);

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

    public List<Reservation> findByVehicule(){
        try{
            long id = IOUtils.readInt("Entrez l'id du véhicule pour afficher ces réservations: ");
            return reservationService.findByVehicule(id);

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }


    public long delete(){
        try{
            long id = IOUtils.readInt("Entrez l'id de la reservation à supprimer: ");
            Reservation reservation = reservationService.findById(id);
            long new_id = reservationService.delete(reservation);
            System.out.println("delete reservation tranmis à service avec succès");
            return new_id;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

}
