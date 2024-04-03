package com.epf.rentmanager.service;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {

	private final ClientDao clientDao;

	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	
	public long create(Client client) throws ServiceException {
		// Vérification de l'âge du client
		LocalDate today = LocalDate.now();
		LocalDate eighteenYearsAgo = today.minusYears(18);
		if (client.getNaissance().isAfter(eighteenYearsAgo)) {
			throw new ServiceException("Le client doit avoir au moins 18 ans.");
		}

//		// Vérification de l'unicité de l'adresse email
//		if (clientService.isEmailAlreadyTaken(client.getEmail())) {
//			throw new ServiceException("Cette adresse email est déjà utilisée par un autre client.");
//		}

		// Vérification de la longueur du nom et du prénom
		if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
			throw new ServiceException("Le nom et le prénom du client doivent faire au moins 3 caractères.");
		}

		// Vérification que le nom et le prénom du client ne sont pas vide
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

	public int count() throws ServiceException{
		try {
			return clientDao.count();
		} catch(DaoException e) {
			throw new ServiceException("Erreur lors de la recuperation de la DAO client (compter le nb de client)", e);
		}
	}
	
}
