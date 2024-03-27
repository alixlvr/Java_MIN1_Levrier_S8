package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/client/details")
public class ClientDetailsServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehiculeService vehiculeService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(request.getParameter("id"));
            Client client = clientService.findById(clientId);
            List<Reservation> reservations = reservationService.findByClient(clientId);

            List<Vehicule> vehicules = new ArrayList<>();
            for (Reservation reservation : reservations) {
                vehicules.add(vehiculeService.findById(reservation.getVehicule_id()));
            }

            // Transfert des détails du client à la page JSP
            request.setAttribute("client", client);
            request.setAttribute("reservations", reservations);
            request.setAttribute("vehicules", vehicules);
            request.getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des détails du client", e);
        }
    }
}
