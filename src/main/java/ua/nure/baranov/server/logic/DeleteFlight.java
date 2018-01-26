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

/**
 * Servlet implementation class DeleteFlight
 */
@WebServlet("/deleteFlight")
public class DeleteFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("Flight delete requested");
		Integer id = Integer.valueOf(req.getParameter("id"));
		LOGGER.trace("id --> "+id);
		if(id == null) {
			return;
		}
		boolean result = false;
		try {
			result = DAOFactory.getDAOFactory().getFlightDAO().delete(id);
		} catch (DatabaseException e) {
			LOGGER.error("Flight deletion failed, try again later");
			LOGGER.error(e.getMessage());
		}
		if(result) {
			LOGGER.debug("Flight " + id + " successfully deleted");
		}
		resp.sendRedirect("dashboard");
	}

}
