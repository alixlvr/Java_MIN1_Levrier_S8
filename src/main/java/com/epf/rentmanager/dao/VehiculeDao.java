package com.epf.rentmanager.dao;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeDao {
	
	private static VehiculeDao instance = null;
	private VehiculeDao() {}
	public static VehiculeDao getInstance() {
		if(instance == null) {
			instance = new VehiculeDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicule(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicule WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicule WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicule;";

	public long create(Vehicule vehicule) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, statement.RETURN_GENERATED_KEYS);){

			// Assignation des valeurs aux paramètres de la requête
			ps.setString(1, vehicule.getConstructeur());
			ps.setString(2, vehicule.getModele());
			ps.setInt(3, vehicule.getNb_places());

			// Exécution de la requête
			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();
			if(resultSet.next()){
				return resultSet.getInt(1);
			}else {
			System.out.println("Erreur lors de l'ajout du vehicule");
			}

		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule", e);
		}
		return -1;
	}

	public long delete(Vehicule vehicle) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY, statement.RETURN_GENERATED_KEYS);){

			// Assignation des valeurs aux paramètres de la requête
			preparedStatement.setInt(1, vehicle.getId());

			// Exécution de la requête
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()){
				return resultSet.getInt(1);
			}else {
				System.out.println("Erreur lors de la suppression du vehicule");
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du vehicule par ID", e);
		}
		return -1;
	}

	public Vehicule findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);){

			// Assignation des valeurs aux paramètres de la requête
			preparedStatement.setInt(1, (int) id);

			// Exécution de la requête
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				// Récupération des données du vehicule trouvé
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int places = resultSet.getInt("nb_places");

				// Création d'une instance de Client avec les données trouvées
				return new Vehicule((int)id, constructeur, modele, places);

			}else {
				System.out.println("Aucun vehicule trouvé avec l'ID : " + id);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du vehicule par ID", e);
		}
		return null;
	}

	public List<Vehicule> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);){

			//execution de la requete
			ps.execute();
			//resultat de la requete
			ResultSet resultSet = ps.executeQuery();
			List<Vehicule>Allvehicule = new ArrayList<>();

			// Traitement des résultats
			while (resultSet.next()) {
				// Récupération des données du vehicule trouvé
				int id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int places = resultSet.getInt("nb_places");
				Vehicule vehicule = new Vehicule(id, constructeur, modele, places);
				Allvehicule.add(vehicule);
			}
			return Allvehicule;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des vehicules de l'etablissement", e);
		}
		
	}
	

}
