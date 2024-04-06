package com.epf.rentmanager.service;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.VehiculeDao;
import com.epf.rentmanager.model.Vehicule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService {

	private final VehiculeDao vehiculeDao;

	public VehiculeService(VehiculeDao vehiculeDao) {
		this.vehiculeDao = vehiculeDao;
	}


	public long create(Vehicule vehicule) throws ServiceException {
		// Vérification du nom et du prénom du client
		if (vehicule.getConstructeur().isEmpty()) {
			throw new ServiceException("Le constructeur ne peut pas être vide");
		} else if (vehicule.getNb_places() <= 1) {
			throw new ServiceException("Le nombre de places dans le vehicule doit etre strictement supérieur à 1");
		}

		// Appel du DAO pour créer ou mettre à jour le client
		try {
			vehiculeDao.create(vehicule);
		} catch (DaoException e) {
			// Si une exception DAO se produit, la relancer sous forme de ServiceException
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client", e);
		}
		return -1;
	}

	public Vehicule findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		try {
			return vehiculeDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche par l'id du véhicule", e);
		}
	}

	public List<Vehicule> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return vehiculeDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche de tous les véhiculess", e);
		}
	}

	public long delete(Vehicule vehicule) throws ServiceException {
		// TODO: delete un vehicule
		try {
			return vehiculeDao.delete(vehicule);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppresion des vehicules par ID(service)", e);
		}
	}

	public int count() throws ServiceException {
		try {
			return vehiculeDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppresion des vehicules par ID(service)", e);
		}
	}

	public void update(Vehicule vehicule) throws ServiceException {
		try {
			vehiculeDao.update(vehicule);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recuperation de la DAO vehicule (update d'un vehicule)", e);
		}
	}

}
