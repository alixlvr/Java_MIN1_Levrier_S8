package com.epf.rentmanager.servlet.Vehicule;

import com.epf.rentmanager.Exception.ServiceException;
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

@WebServlet("/vehicules/edit")
public class VehiculeEditServlet extends HttpServlet {

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
            long vehiculeId = Long.parseLong(request.getParameter("id"));
            Vehicule vehicule = vehiculeService.findById(vehiculeId);
            
            request.setAttribute("vehicule", vehicule);
            request.getRequestDispatcher("/WEB-INF/views/vehicules/edit.jsp").forward(request, response);

        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des informations du client à éditer", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nb_places = Integer.parseInt(request.getParameter("nb_places"));

            Vehicule vehicule = new Vehicule(0, constructeur, modele, nb_places);
            vehiculeService.update(vehicule);
            response.sendRedirect(request.getContextPath() + "/vehicules/list");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création du vehicule",e);
        }
    }
}
