package com.epf.rentmanager.servlet.Reservation;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/reservation/edit")
public class ReservationEditServlet extends HttpServlet {

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

            request.setAttribute("reservation", reservation);
            request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);

        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des informations de la réservation à éditer", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int client_id = Integer.parseInt(request.getParameter("client_id"));
            int vehicule_id = Integer.parseInt(request.getParameter("vehicule_id"));
            LocalDate debut = LocalDate.parse(request.getParameter("debut"));
            LocalDate fin = LocalDate.parse(request.getParameter("fin"));

            Reservation reservation = new Reservation(0, client_id,vehicule_id, debut, fin);
            reservationService.update(reservation);
            response.sendRedirect(request.getContextPath() + "/reservation/list");

        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la mise à jour de la réservation", e);
        }
    }
}
