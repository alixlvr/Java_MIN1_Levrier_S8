package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class ClientUi {

    private ClientService clientService;
    public static ClientUi instance;

    private ClientUi() {
        this.clientService = ClientService.getInstance();
    }

    public static ClientUi getInstance() {
        if (instance == null) {
            instance = new ClientUi();
        }
        return instance;
    }

    public long createClient() {
        int id = 0;

        String firstName = IOUtils.readString("Entrez le prénom du client: ", true);
        String lastName = IOUtils.readString("Entrez le nom du client: ", true);
        String email = IOUtils.readString("Entrez l'email du client: ", true);
        LocalDate naissance = IOUtils.readDate("Entrez la date de naissance du client (format: jj/mm/aaaa): ", true);

        try{
            Client client = new Client(id, firstName, lastName, email, naissance);
            // Appeler la méthode de création de client
            long id_client = clientService.create(client);
            System.out.println("Client créé transmis à service avec succès");
            return id_client;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

    public List<Client> AllClient(){
        try{
            // Appeler la méthode de création de client
            List<Client> client = clientService.findAll();
            System.out.println("All client tranmis à service avec succès");
            return client;

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

    public long delete(){
        try{
            int id = IOUtils.readInt("Entrez l'id du client à supprimer: ");
            Client client = clientService.findById(id);
            return clientService.delete(client);

        } catch (ServiceException e) {
            throw new RuntimeException("Erreur lors de la partie Ui", e);
        }
    }

}
