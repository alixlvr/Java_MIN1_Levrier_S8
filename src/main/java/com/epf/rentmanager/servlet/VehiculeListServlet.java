package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.service.VehiculeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicules/list")
public class VehiculeListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VehiculeService vehiculeService = VehiculeService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            request.setAttribute("vehicules", vehiculeService.findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicules/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la récupération de la liste des véhicules", e);
        }

    }

}
