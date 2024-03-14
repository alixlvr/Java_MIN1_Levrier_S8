package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicules/create")
public class VehiculeCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    private VehiculeService vehiculeService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/vehicules/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String marque = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int places = Integer.parseInt(request.getParameter("nb_places"));

            Vehicule vehicule = new Vehicule(0, marque, modele, places);
            vehiculeService.create(vehicule);
            response.sendRedirect(request.getContextPath() + "/vehicules/list");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création du véhicule",e);
        }
    }
}