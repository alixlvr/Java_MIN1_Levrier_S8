package com.epf.rentmanager.servlet.Client;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/client/delete")
public class ClientSupprimerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationService reservationService;
    public ClientSupprimerServlet() {}

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(request.getParameter("id"));
            // Supprimer toutes les réservations associées au client
            reservationService.deleteByClientId(clientId);
            Client client = clientService.findById(clientId);
            clientService.delete(client);
            response.sendRedirect(request.getContextPath() + "/client/list");
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la suppression de la réservation", e);
        }
    }
}