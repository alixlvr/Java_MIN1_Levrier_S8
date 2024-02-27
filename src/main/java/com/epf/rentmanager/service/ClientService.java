package com.epf.rentmanager.service;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;

import java.util.List;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		// Vérification du nom et du prénom du client
		if (client.getNom().isEmpty()) {
			throw new ServiceException("Le nom du client ne peut pas être vide");
		}else if (client.getPrenom().isEmpty()) {
			throw new ServiceException("Le prénom du client ne peut pas être vide");
		}
		client.setNom(client.getNom().toUpperCase()); //mettre le nom en majuscule dans la bd

		// Appel du DAO pour créer ou mettre à jour le client
		try {
            return clientDao.create(client);
		} catch (DaoException e) {
			// Si une exception DAO se produit, la relancer sous forme de ServiceException
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client", e);
		}
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche par l'id du client", e);
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous_ les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche des clients", e);
		}
	}

	public long delete(Client client) throws ServiceException {
		// TODO: delete un clients
		try {
			return clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression des clients (service)", e);
		}
	}
	
}
