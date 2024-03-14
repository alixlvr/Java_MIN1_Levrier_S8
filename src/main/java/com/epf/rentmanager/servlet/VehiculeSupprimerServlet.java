package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicules/delete")
public class VehiculeSupprimerServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    private VehiculeService vehiculeService;

//    @Override
//    public void init() throws ServletException {
//        super.init();
//        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//    }
    public VehiculeSupprimerServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long vehiculeId = Long.parseLong(request.getParameter("id"));
            Vehicule vehicule = vehiculeService.findById(vehiculeId);
            vehiculeService.delete(vehicule);
            response.sendRedirect(request.getContextPath() + "/reservation/list");
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la suppression de la réservation", e);
        }
    }
}
