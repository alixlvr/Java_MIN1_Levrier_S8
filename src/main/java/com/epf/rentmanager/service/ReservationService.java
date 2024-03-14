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


    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer une reservation
        if (reservation.getVehicule_id() == 0 && reservation.getClient_id() == 0) {
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
        // TODO: récupérer un véhicule par son id
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche par l'id de la reservation", e);
        }
    }

    public List<Reservation> findByClient(long id) throws ServiceException {
        // TODO: récupérer un véhicule par son id
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche par l'id du client (reservation)", e);
        }
    }

    public List<Reservation> findByVehicule(long id) throws ServiceException {
        // TODO: récupérer un véhicule par son id
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche par l'id du véhicule (reservation)", e);
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        // TODO: récupérer tous les clients
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche de toutes les reservations", e);
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        // TODO: delete un clients
        try {
            return reservationDao.delete(reservation);
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
