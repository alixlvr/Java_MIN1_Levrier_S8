package com.ensta.rentmanager.service;


import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class ReservationTest {

    @Mock
    private ReservationDao reservationDao;
    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void create_reservation_should_fail_when_id_user_is_empty() {
        Reservation reservationWithNoClientId = new Reservation(1, 0, 3, LocalDate.of(2022, 9, 29), LocalDate.of(2023, 9, 29));
        // When
        try {
            reservationService.create(reservationWithNoClientId);
            fail("Aucune exception n'a été levée pour un id de client vide");

        } catch (ServiceException e) {
            // Then
            assertEquals("L'id du client et l'id du vehicule ne doit pas être vide", e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void create_reservation_should_fail_when_id_vehicule_is_empty() {
        Reservation reservationWithNoVehiculeId = new Reservation(1, 3, 0, LocalDate.of(2022, 9, 29), LocalDate.of(2023, 9, 29));
        // When
        try {
            reservationService.create(reservationWithNoVehiculeId);
            fail("Aucune exception n'a été levée pour un id de client vide");

        } catch (ServiceException e) {
            // Then
            assertEquals("L'id du client et l'id du vehicule ne doit pas être vide", e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
