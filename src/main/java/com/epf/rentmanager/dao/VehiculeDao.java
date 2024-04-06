package com.epf.rentmanager.dao;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehiculeDao {
	public VehiculeDao() {}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicule(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicule WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicule WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicule;";
	private static final String COUNT_VEHICULES_QUERY = "SELECT COUNT(*) FROM Vehicule;";
	private static final String UPDATE_VEHICULE_QUERY = "UPDATE vehicule SET constructeur=?, modele=?, nb_places=? WHERE id=?";

	public long create(Vehicule vehicule) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, statement.RETURN_GENERATED_KEYS)){

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
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY, statement.RETURN_GENERATED_KEYS)){

			preparedStatement.setInt(1, vehicle.getId());
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
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY)){


			preparedStatement.setInt(1, (int) id);
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int places = resultSet.getInt("nb_places");

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
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY)){

			ps.execute();

			ResultSet resultSet = ps.executeQuery();
			List<Vehicule>Allvehicule = new ArrayList<>();

			while (resultSet.next()) {
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

	public int count() throws DaoException{
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_VEHICULES_QUERY)){

			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des vehicules de l'etablissement",e);
		}
		return -1;
	}

	public void update(Vehicule vehicule) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_VEHICULE_QUERY)) {

			statement.setString(1, vehicule.getConstructeur());
			statement.setString(2, vehicule.getModele());
			statement.setInt(3, vehicule.getNb_places());
			statement.setLong(4, vehicule.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la mise à jour du client", e);
		}
	}
	

}
