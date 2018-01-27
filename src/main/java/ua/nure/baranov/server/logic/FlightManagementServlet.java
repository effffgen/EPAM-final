
package ua.nure.baranov.server.logic;

import java.io.IOException;

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
import ua.nure.baranov.server.Util;

@WebServlet("/flight")
public class FlightManagementServlet extends HttpServlet {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final long serialVersionUID = -7395298700939323660L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setFlightDetails(req, resp);
		req.getRequestDispatcher("flights/view.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("act");
		if (action == null) {
			LOGGER.trace("No action has been provided, it's illegal");
			throw new IllegalArgumentException("No action provided for flight manipulation");
		}
		switch (action) {
		case "delete":
			processDeletion(req, resp);
			break;
		case "modify":
			//TODO: deal with it
			break;
		default:
			throw new IllegalArgumentException("Wrong action value");

		}
	}

	private void setFlightDetails(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String idString = req.getParameter("id");
		LOGGER.debug("Details about flight requested");
		LOGGER.trace("id --> " + idString);
		if (idString == null) {
			LOGGER.warn("No flight id was provided, sending to the dashboard");
			resp.sendRedirect("dashboard");
			return;
		}
		int id = Integer.valueOf(idString);
		Flight flight = Util.getFlight(id);
		LOGGER.trace("Result --> " + flight);
		req.setAttribute("flight", flight);
	}

	private void processDeletion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		LOGGER.debug("Flight delete requested");
		Integer id = Integer.valueOf(req.getParameter("id"));
		LOGGER.trace("id --> " + id);
		if (id == null) {
			return;
		}
		boolean result = false;
		try {
			result = DAOFactory.getDAOFactory().getFlightDAO().delete(id);
		} catch (DatabaseException e) {
			LOGGER.error("Flight deletion failed, try again later");
			LOGGER.error(e.getMessage());
		}
		if (result) {
			LOGGER.debug("Flight " + id + " successfully deleted");
		}
		resp.sendRedirect("dashboard");
	}

}
