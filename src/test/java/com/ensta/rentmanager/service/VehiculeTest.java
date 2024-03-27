package com.ensta.rentmanager.service;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.VehiculeDao;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehiculeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class VehiculeTest {

    @Mock
    private VehiculeDao vehiculeDao;
    @InjectMocks
    private VehiculeService vehiculeService;

    @Test
    public void Create_Vehicule_With_Empty_Constructor_should_fail() throws ServiceException{
        Vehicule vehiculeWithEmptyConstructeur = new Vehicule(1, "", "Toyota400",4);
        try{
            vehiculeService.create(vehiculeWithEmptyConstructeur);
            fail("aucune exception n'a été levée pour un constructeur de véhicule vide" );
        }catch(ServiceException e){
            assertEquals("Le constructeur ne peut pas être vide", e.getMessage());
        }
    }

    @Test
    public void Create_Vehicule_With_Not_Enough_Place_Should_Fail() {
        Vehicule vehiculeWithOneSeat = new Vehicule(1, "Toyota", "Toyota400",1);
        try{
            vehiculeService.create(vehiculeWithOneSeat);
            fail("aucune exception n'a été levée pour un véhicule avec deux places" );
        }catch(ServiceException e){
            assertEquals("Le nombre de places dans le vehicule doit etre strictement supérieur à 1", e.getMessage());
        }
    }
}
