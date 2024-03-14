package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehiculeService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class VehiculeUi {

    private VehiculeService vehiculeService;
    public static VehiculeUi instance;

    public VehiculeUi() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.vehiculeService = context.getBean(VehiculeService.class);
    }

//    public static VehiculeUi getInstance() {
//        if (instance == null) {
//            instance = new VehiculeUi();
//        }
//        return instance;
//    }

    public long createVehicule() {
        int id = 0;

        String constructeur = IOUtils.readString("Entrez le nom du constructeur: ", true);
        String modele = IOUtils.readString("Entrez le nom du modele du véhicule: ", false);
        int places = IOUtils.readInt("Entrez le nombre de places du véhicule: ");

        try{
            Vehicule vehicule = new Vehicule(id, constructeur, modele, places);
            // Appeler la méthode de création de client
            long id_client = vehiculeService.create(vehicule);
            System.out.println("Vehicule créé transmis à service avec succès");
            return id_client;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui pour la création du véhicule", e);
        }
    }

    public List<Vehicule> AllVehicule(){
        try{
            List<Vehicule> vehicule = vehiculeService.findAll();
            System.out.println("All vehicule tranmis à service avec succès");
            return vehicule;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

    public long delete(){
        try{
            int id = IOUtils.readInt("Entrez l'id du véhicule à supprimer: ");
            Vehicule vehicule = vehiculeService.findById(id);
            long new_id = vehiculeService.delete(vehicule);
            System.out.println("All client tranmis à service avec succès");
            return new_id;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }
}
