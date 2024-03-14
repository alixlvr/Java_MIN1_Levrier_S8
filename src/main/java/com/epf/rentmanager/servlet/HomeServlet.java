package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Autowired
	private ClientService clientService;
	@Autowired
	private VehiculeService vehiculeService;
	@Autowired
	private ReservationService reservationService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int numberOfVehicles = 0;
		int numberOfClients = 0;
		int numberOfReservations = 0;
		try {
			numberOfVehicles = vehiculeService.count();
			request.setAttribute("numberOfVehicles", numberOfVehicles);

			numberOfClients = clientService.count();
			request.setAttribute("numberOfClients", numberOfClients);

			numberOfReservations = reservationService.count();
			request.setAttribute("numberOfReservations", numberOfReservations);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}


	}

}
