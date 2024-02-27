package com.epf.rentmanager.dao;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicule_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicule_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicule_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicule_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicule_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, statement.RETURN_GENERATED_KEYS);){

			// Assignation des valeurs aux paramètres de la requête
			ps.setInt(1,reservation.getClient_id());
			ps.setInt(2, reservation.getVehicule_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));

			// Exécution de la requête
			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();
			if(resultSet.next()){
                return resultSet.getInt(1);
			}

		}catch (SQLException e){
			throw new DaoException("Erreur lors de la recherche du client par ID", e);
		}
		return -1;
	}


	public long delete(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY, statement.RETURN_GENERATED_KEYS);){

			// Assignation des valeurs aux paramètres de la requête
			ps.setInt(1,reservation.getVehicule_id());

			// Exécution de la requête
			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();
			if(resultSet.next()){
                return resultSet.getInt(1);
			}

		}catch (SQLException e){
			throw new DaoException("Erreur lors de la recherche du client par ID", e);
		}
		return -1;
	}

	public Reservation findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATION_QUERY);){

			// Assignation des valeurs aux paramètres de la requête
			preparedStatement.setInt(1, (int) id);

			// Exécution de la requête
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				// Récupération des données du client trouvé
				int client_id = resultSet.getInt("client_id");
				int vehicule_id = resultSet.getInt("vehicule_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();

				// Création d'une instance de Client avec les données trouvées
				return new Reservation((int)id, client_id, vehicule_id, debut, fin);

			}else {
				System.out.println("Aucune reservation trouvé avec l'ID : " + id);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la reservation par ID", e);
		}
		return null;
	}


	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);){

			// Assignation de la valeur au paramètre de la requête
			ps.setInt(1, (int) clientId);

			//execution de la requete
			ps.execute();
			//resultat de la requete
			ResultSet resultSet = ps.executeQuery();
			List<Reservation>ReservatClientX = new ArrayList<>();

			// Traitement des résultats
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int vehicule_id = resultSet.getInt("vehicule_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, (int) clientId, vehicule_id, debut, fin);
				ReservatClientX.add(reservation);
			}
			return ReservatClientX;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la reservation par ID client", e);
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);){

			// Assignation de la valeur au paramètre de la requête
			ps.setInt(1, (int) vehicleId);

			//execution de la requete
			ps.execute();
			//resultat de la requete
			ResultSet resultSet = ps.executeQuery();
			List<Reservation>ReservatVehiculeX = new ArrayList<>();

			// Traitement des résultats
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id, (int) vehicleId, debut, fin);
				ReservatVehiculeX.add(reservation);
			}
			return ReservatVehiculeX;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la reservation par ID vehicule", e);
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);){

			//execution de la requete
			ps.execute();
			//resultat de la requete
			ResultSet resultSet = ps.executeQuery();
			List<Reservation>AllReservation = new ArrayList<>();

			// Traitement des résultats
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				int vehicule_id = resultSet.getInt("vehicule_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id, (int) vehicule_id, debut, fin);
				AllReservation.add(reservation);
			}
			return AllReservation;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des reservations de l'etablissement", e);
		}
	}
}
