package com.epf.rentmanager.servlet.Client;

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

@WebServlet("/client/create")
public class ClientCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            LocalDate naissance = LocalDate.parse(request.getParameter("naissance"));

            // Vérifier si le client a au moins 18 ans
            LocalDate now = LocalDate.now();
            if (naissance.plusYears(18).isAfter(now)) {
                request.setAttribute("birthdateError", "Vous devez avoir au moins 18 ans pour vous inscrire.");
                request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                return;
            }

            // Vérification si l'adresse email existe déjà
            if (clientService.emailExists(email)) {
                request.setAttribute("EmailError", "Cette adresse e-mail est déjà utilisée. Veuillez en choisir une autre.");
                request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                return;
            }

            // Vérification de la longueur du nom et du prénom
            if (nom.length() < 3) {
                request.setAttribute("NomError", "Le nom doit faire au moins 3 caractères.");
                request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                return;
            }
            if (prenom.length() < 3) {
                request.setAttribute("PrenomError", "Le prenom doit faire au moins 3 caractères.");
                request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                return;
            }

            Client client = new Client(0, nom, prenom, email, naissance);
            clientService.create(client);
            response.sendRedirect(request.getContextPath() + "/client/list");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création du client", e);
        }
    }
}
