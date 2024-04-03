package com.epf.rentmanager.service;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException, DaoException {

        // Vérifie si la voiture est déjà réservée le même jour
        if (reservationDao.VehiculeDejaReserve(reservation.getVehicule_id(), reservation.getDebut())) {
            throw new ServiceException("La voiture est déjà réservée pour cette date.");
        }

        // Vérifie de la durée de reservation
        if (reservationDao.VehiculeReserveMoinsSeptJours(reservation.getVehicule_id(), reservation.getClient_id(), reservation.getDebut(), reservation.getFin())) {
            throw new ServiceException("La voiture est déjà réservée pour plus de 7 jours de suite par le même utilisateur.");
        }

        // Vérifie si la voiture est réservée 30 jours de suite sans pause
        if (reservationDao.VehiculeReservePlusTrenteJours(reservation.getVehicule_id(), reservation.getDebut(), reservation.getFin())) {
            throw new ServiceException("La voiture est déjà réservée pour 30 jours de suite sans pause.");
        }

        if (reservation.getVehicule_id() == 0 || reservation.getClient_id() == 0) {
            throw new ServiceException("L'id du client et l'id du vehicule ne doit pas être vide");
        }
        try {
            reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création ou de la mise à jour de la reservation", e);
        }
        return -1;
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche par l'id de la reservation", e);
        }
    }

    public List<Reservation> findByClient(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche par l'id du client (reservation)", e);
        }
    }

    public List<Reservation> findByVehicule(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche par l'id du véhicule (reservation)", e);
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de toutes les reservations", e);
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression des vehicules (service)", e);
        }
    }

    public long deleteByClientId(long clientId) throws ServiceException {
        try {
            return reservationDao.deleteByIdClient(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression des vehicules (service)", e);
        }
    }

    public long deleteByVehiculeId(long vehiculeId) throws ServiceException {
        try {
            return reservationDao.deleteByIdVehicule(vehiculeId);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression des vehicules (service)", e);
        }
    }

    public int count() throws ServiceException{
        try {
            return reservationDao.count();
        } catch(DaoException e) {
            throw new ServiceException("Erreur lors de la recuperation de la DAO client (compter le nb de client)", e);
        }
    }
}
