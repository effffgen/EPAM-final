package ua.nure.baranov.server.logic;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Flight;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = -730317445247351018L;
	private static final Logger LOGGER  = LogManager.getLogger();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.trace("Getting all flights");
		List<Flight> flights = null;
		try {
			flights = DAOFactory.getDAOFactory().getFlightDAO().getAll();
		} catch (DatabaseException e) {
			LOGGER.error("Error during getting flight list");
		}	
		request.setAttribute("flights", flights);
		request.getRequestDispatcher("opdash.jsp").forward(request, response);
	}

}
