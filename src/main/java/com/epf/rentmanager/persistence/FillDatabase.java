package com.epf.rentmanager.persistence;

import org.h2.tools.DeleteDbFiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FillDatabase {


    public static void main(String[] args) throws Exception {
        try {
            DeleteDbFiles.execute("~", "RentManagerDatabase", true);
            insertWithPreparedStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement createPreparedStatement = null;

        List<String> createTablesQueries = new ArrayList<>();
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Client(id INT primary key auto_increment, client_id INT, nom VARCHAR(100), prenom VARCHAR(100), email VARCHAR(100), naissance DATETIME)");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Vehicule(id INT primary key auto_increment, constructeur VARCHAR(100), modele VARCHAR(100), nb_places TINYINT(255))");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Reservation(id INT primary key auto_increment, client_id INT, foreign key(client_id) REFERENCES Client(id), vehicule_id INT, foreign key(vehicule_id) REFERENCES Vehicule(id), debut DATETIME, fin DATETIME)");

        try {
            connection.setAutoCommit(false);

            for (String createQuery : createTablesQueries) {
            	createPreparedStatement = connection.prepareStatement(createQuery);
	            createPreparedStatement.executeUpdate();
	            createPreparedStatement.close();
            }

            // Remplissage de la base avec des Vehicules et des Clients
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO Vehicule(constructeur, modele, nb_places) VALUES('Renault','Clio', 4)");
            stmt.execute("INSERT INTO Vehicule(constructeur, modele, nb_places) VALUES('Peugeot','208', 4)");
            stmt.execute("INSERT INTO Vehicule(constructeur, modele, nb_places) VALUES('Seat', 'Leon', 4)");
            stmt.execute("INSERT INTO Vehicule(constructeur, modele, nb_places) VALUES('Nissan','Qashqai', 4)");
            
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Dupont', 'Jean', 'jean.dupont@email.com', '1988-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Morin', 'Sabrina', 'sabrina.morin@email.com', '1988-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Afleck', 'Steeve', 'steeve.afleck@email.com', '1988-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Rousseau', 'Jacques', 'jacques.rousseau@email.com', '1988-01-22')");
                    
            connection.commit();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
