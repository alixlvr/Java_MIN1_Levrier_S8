package com.epf.rentmanager.service;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public long create(Reservation reservation) throws ServiceException, DaoException {

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

    public boolean VehiculeDejaReserve(long vehicule_id, LocalDate debut, LocalDate fin) throws ServiceException {
        try {
            return reservationDao.VehiculeDejaReserve(vehicule_id, debut, fin);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la suppression des vehicules (service)", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean ReservationPlusSeptJours(long client_id, long vehicule_id, LocalDate debut, LocalDate fin) throws ServiceException {
        try {
            return reservationDao.ReservationPlusSeptJours(client_id, vehicule_id, debut, fin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean ReservationDureVehicule(LocalDate debut, LocalDate fin, List<Reservation> vehiculeReservations) throws ServiceException {
        try {
            return reservationDao.ReservationDureVehicule(debut, fin, vehiculeReservations);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

        public int count() throws ServiceException{
        try {
            return reservationDao.count();
        } catch(DaoException e) {
            throw new ServiceException("Erreur lors de la recuperation de la DAO client (compter le nb de client)", e);
        }
    }

    public void update(Reservation reservation) throws ServiceException {
        try {
            reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recuperation de la DAO vehicule (update d'un vehicule)", e);
        }
    }
}
