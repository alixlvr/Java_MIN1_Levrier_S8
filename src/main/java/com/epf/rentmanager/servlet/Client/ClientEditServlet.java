package com.epf.rentmanager.servlet.Client;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/client/edit")
public class ClientEditServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(request.getParameter("id"));
            Client client = clientService.findById(clientId);

            request.setAttribute("client", client);
            request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);

        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("Erreur lors de la récupération des informations du client à éditer", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            LocalDate naissance = LocalDate.parse(request.getParameter("naissance"));

            Client client = new Client(0, nom, prenom, email,naissance);
            clientService.update(client);
            response.sendRedirect(request.getContextPath() + "/client/list");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création du client",e);
        }
    }
}

