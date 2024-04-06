package com.epf.rentmanager.servlet.Reservation;

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

@WebServlet("/reservation/list")
public class ReservationListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehiculeService vehiculeService;
    @Autowired
    private ClientService clientService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Reservation> reservations = reservationService.findAll();
            List<Vehicule> vehicules = new ArrayList<>();
            List<Client> clients = new ArrayList<>();
            for (Reservation reservation : reservations) {
                Vehicule vehicule = vehiculeService.findById(reservation.getVehicule_id());
                vehicules.add(vehicule);
                Client client = clientService.findById(reservation.getClient_id());
                clients.add(client);
            }

            request.setAttribute("reservation", reservations);
            request.setAttribute("vehicule", vehicules);
            request.setAttribute("client", clients);

            request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la récupération de la liste des clients", e);
        }
    }

}
