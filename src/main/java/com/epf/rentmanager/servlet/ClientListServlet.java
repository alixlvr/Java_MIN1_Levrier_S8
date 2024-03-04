package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/client/list")
public class ClientListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClientService clientService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Client> client = ClientService.getInstance().findAll();
            request.setAttribute("client", client);
            request.getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Erreur lors de la récupération de la liste des clients", e);
        }
    }
}
