package com.ensta.rentmanager.service;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    @Mock
    private ClientDao clientDAO; // On mocke le DAO
    @InjectMocks
    private ClientService clientService; // On injecte le mock dans le service à tester

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.clientDAO.findAll()).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    public void findById_should_return_true_when_find_correct_user() throws DaoException, ServiceException {
        // Définition des données de test
        Client expectedUser = new Client(1, "Badetz", "Eleonore", "e.b@epfedu.fr", LocalDate.of(2002, 2, 2));
        // Définition du comportement du mock
        when(clientDAO.findById(1)).thenReturn(expectedUser);
        // Appel de la méthode à tester
        Client actualUser = clientService.findById(1);
        // Vérification du résultat
        assertEquals(expectedUser, actualUser);
        verify(clientDAO, times(1)).findById(1); // Vérification que la méthode du DAO a été appelée une fois avec l'argument 1
    }

    @Test
    public void create_user_should_fail_when_user_with_empty_name() {
        Client clientWithEmptyName = new Client(1, "", "Evie", "Evie@epfedu.fr", LocalDate.of(2002, 9, 29));
        // When
        try {
            clientService.create(clientWithEmptyName);
            fail("Aucune exception n'a été levée pour un nom de client vide");

        } catch (ServiceException e) {
            // Then
            assertEquals("Le nom du client ne peut pas être vide", e.getMessage());
        }
    }

    @Test
    public void create_user_should_fail_when_user_with_empty_firstname() {
        Client clientWithEmptyName = new Client(1, "Le Pottier", "", "Evie@epfedu.fr", LocalDate.of(2002, 9, 29));
        // When
        try {
            clientService.create(clientWithEmptyName);
            fail("Aucune exception n'a été levée pour un nom de client vide");
        } catch (ServiceException e) {
            // Then
            assertEquals("Le prénom du client ne peut pas être vide", e.getMessage());
        }
    }
}
