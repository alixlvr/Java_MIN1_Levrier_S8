package com.epf.rentmanager.servlet.Vehicule;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicules/list")
public class VehiculeListServlet extends HttpServlet {
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

        try{
            request.setAttribute("vehicules", vehiculeService.findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicules/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la récupération de la liste des véhicules", e);
        }

    }

}
