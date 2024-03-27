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

@WebServlet("/vehicules/details")
public class VehiculeDetailsServlet extends HttpServlet {

    @Autowired
    private VehiculeService vehiculeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long vehiculeId = Long.parseLong(request.getParameter("id"));
            Vehicule vehicule = vehiculeService.findById(vehiculeId);
            List<Reservation> reservations = reservationService.findByVehicule(vehiculeId);

            List<Client> clients = new ArrayList<>();
            for (Reservation reservation : reservations) {
                clients.add(clientService.findById(reservation.getClient_id()));
            }
            // Transfert des détails du client à la page JSP
            request.setAttribute("vehicule", vehicule);
            request.setAttribute("clients", clients);
            request.setAttribute("reservations", reservations);

            request.getRequestDispatcher("/WEB-INF/views/vehicules/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des détails du client", e);
        }
    }
}
