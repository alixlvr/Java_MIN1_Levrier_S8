package com.epf.rentmanager.service;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.VehiculeDao;
import com.epf.rentmanager.model.Vehicule;

import java.util.List;

public class VehiculeService {

	private VehiculeDao vehiculeDao;
	public static VehiculeService instance;
	
	private VehiculeService() {
		this.vehiculeDao = VehiculeDao.getInstance();
	}
	
	public static VehiculeService getInstance() {
		if (instance == null) {
			instance = new VehiculeService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicule vehicule) throws ServiceException {
		// TODO: créer un véhicule
		// Vérification du nom et du prénom du client
		if (vehicule.getConstructeur().isEmpty()) {
			throw new ServiceException("Le constructeur ne peut pas être vide");
		}else if (vehicule.getNb_places() <= 1) {
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

	public int count() throws ServiceException{
		// count nombre de vehicule
		try {
			return vehiculeDao.count();
		} catch(DaoException e) {
			throw new ServiceException("Erreur lors de la suppresion des vehicules par ID(service)", e);
		}
	}
	
}
