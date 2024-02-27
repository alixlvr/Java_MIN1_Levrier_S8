package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.service.VehiculeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int numberOfVehicles = 0;
		try {
			numberOfVehicles = VehiculeService.getInstance().count();
			request.setAttribute("numberOfVehicles", numberOfVehicles);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}


	}

}
