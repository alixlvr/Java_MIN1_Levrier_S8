package com.epf.rentmanager.servlet;

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

@WebServlet("/reservation/delete")
public class ReservationSupprimerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ReservationService reservationService;
    public ReservationSupprimerServlet() {}

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long reservationId = Long.parseLong(request.getParameter("id"));
            Reservation reservation = reservationService.findById(reservationId);
            reservationService.delete(reservation);
            response.sendRedirect(request.getContextPath() + "/reservation/list");
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la suppression de la réservation", e);
        }
    }
}