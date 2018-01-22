
package ua.nure.baranov.server.logic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.Flight;
import ua.nure.baranov.server.Util;

@WebServlet("/flightdetails")
public class FlightDetails extends HttpServlet {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final long serialVersionUID = -7395298700939323660L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		LOGGER.debug("Details about flight " + idString + " requested");
		if(idString == null) {
			LOGGER.warn("No flight id was provided, sending to the dashboard");
			resp.sendRedirect("dashboard");
			return;
		}
		int id = Integer.valueOf(idString);
		Flight flight = Util.getFlight(id);
		LOGGER.trace("Result --> " + flight);
		req.setAttribute("flight", flight);
		req.getRequestDispatcher("flightdetails.jsp").forward(req, resp);
	}

}
