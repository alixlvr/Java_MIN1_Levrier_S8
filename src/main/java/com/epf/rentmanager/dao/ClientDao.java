package com.epf.rentmanager.dao;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDao {
	public ClientDao() {}

//	private static ClientDao instance = null;
//	private ClientDao() {}
//	public static ClientDao getInstance() {
//		if(instance == null) {
//			instance = new ClientDao();
//		}
//		return instance;
//	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENT_QUERY = "SELECT COUNT(*) FROM Client;";

	public long create(Client client) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, statement.RETURN_GENERATED_KEYS);
		){
			// Assignation des valeurs aux paramètres de la requête
			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));

			// Exécution de la requête
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
                return resultSet.getInt(1);
			}else {
				System.out.println("Erreur lors de la création du client");
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client par ID", e);
        }
		return -1;
	}


	public long delete(Client client) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY, statement.RETURN_GENERATED_KEYS);){

			// Assignation des valeurs aux paramètres de la requête
			preparedStatement.setInt(1, client.getId());

			// Exécution de la requête
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
                return resultSet.getInt(1);
			}else {
				System.out.println("Erreur lors de la suppression du client");
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client par ID", e);
		}
		return -1;
	}


	public Client findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);){

			// Assignation des valeurs aux paramètres de la requête
			preparedStatement.setInt(1, (int) id);

			// Exécution de la requête
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				// Récupération des données du client trouvé
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();

				// Création d'une instance de Client avec les données trouvées
                return new Client((int)id, nom, prenom, email, naissance);

			}else {
				System.out.println("Aucun client trouvé avec l'ID : " + id);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client par ID", e);
		}
		return null;
	}


        public List<Client> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);){

			//execution de la requete
			preparedStatement.execute();
			//resultat de la requete
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Client>AllClient = new ArrayList<>();

			// Traitement des résultats
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				Client client = new Client(id, nom, prenom, email, naissance);
				AllClient.add(client);
			}
			return AllClient;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client par ID", e);
		}
    }

	public int count() throws DaoException{
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(COUNT_CLIENT_QUERY);){

			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des clients de l'etablissement",e);
		}
		return -1;
	}

}
