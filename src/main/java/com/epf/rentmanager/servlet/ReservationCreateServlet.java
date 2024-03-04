package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/reservation/create")
public class ReservationCreateServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private ReservationService reservationService;


        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            try{
                int client_id = Integer.parseInt(request.getParameter("client_id"));
                int vehicule_id = Integer.parseInt(request.getParameter("vehicule_id"));
                LocalDate debut = LocalDate.parse(request.getParameter("debut"));
                LocalDate fin = LocalDate.parse(request.getParameter("fin"));

                Reservation reservation = new Reservation(0, client_id, vehicule_id, debut, fin);
                ReservationService.getInstance().create(reservation);
                response.sendRedirect(request.getContextPath() + "/reservation/list");

            } catch (Exception e) {
                throw new ServletException("Erreur lors de la création du reservation",e);
            }
        }
    }
