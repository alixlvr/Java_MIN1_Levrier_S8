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

@WebServlet("/reservation/details")
public class ReservationDetailsServlet extends HttpServlet {

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
            long reservationId = Long.parseLong(request.getParameter("id"));
            Reservation reservation = reservationService.findById(reservationId);

            Vehicule vehicule = vehiculeService.findById(reservation.getVehicule_id());
            Client client = clientService.findById(reservation.getClient_id());

            request.setAttribute("vehicule", vehicule);
            request.setAttribute("client", client);
            request.setAttribute("reservation", reservation);

            request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des détails du client", e);
        }
    }
}