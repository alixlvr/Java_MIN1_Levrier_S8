package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehiculeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home/vehicule/list")
public class VehiculeListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VehiculeService vehiculeService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try{
            List<Vehicule> vehicules = VehiculeService.getInstance().findAll();
            request.setAttribute("vehicules", vehicules);
            request.getRequestDispatcher("/WEB-INF/views/vehicle/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la récupération de la liste des véhicules", e);
        }

    }

}
