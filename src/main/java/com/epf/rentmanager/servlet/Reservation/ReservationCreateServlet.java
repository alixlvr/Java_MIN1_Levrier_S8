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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/reservation/create")
public class ReservationCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
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
            List<Client> clients = clientService.findAll();
            List<Vehicule> vehicules = vehiculeService.findAll();

            request.setAttribute("clients", clients);
            request.setAttribute("vehicules", vehicules);
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new RuntimeException("erreur lors de la création de la reservation servlet", e);
        }

    }
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int client_id = Integer.parseInt(request.getParameter("client_id"));
            int vehicule_id = Integer.parseInt(request.getParameter("vehicule_id"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(request.getParameter("debut"), formatter);
            LocalDate fin = LocalDate.parse(request.getParameter("fin"), formatter);

            // Vérifier si une réservation existe déjà pour cette voiture à cette date
            if (reservationService.VehiculeDejaReserve(vehicule_id, debut, fin)) {
                request.setAttribute("ReservationDateError", "Une réservation pour cette voiture à cette date existe déjà. Vous ne pouvez pas la reserver à cette date.");
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
                return;
            }

            // Vérifier si la réservation est valide
            if (reservationService.ReservationPlusSeptJours(client_id, vehicule_id, debut, fin)) {
                request.setAttribute("7joursError", "Impossible de créer une reservation de plus de sept jours par la meme personne.");
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
                return;
            }

            Reservation reservation = new Reservation(0, client_id, vehicule_id, debut, fin);
            reservationService.create(reservation);
            response.sendRedirect(request.getContextPath() + "/reservation/list");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création du reservation", e);
        }
    }
}



