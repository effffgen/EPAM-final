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
import ua.nure.baranov.entity.Request;

/**
 * Servlet implementation class RequestDetails
 */
@WebServlet("/requests/get")
public class RequestDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("Getting request details");
		String idStr = request.getParameter("id");
		if (idStr == null) {
			LOGGER.error("Action has been set to \"get\", but no id has been provided");
			throw new IllegalArgumentException("Action has been set to \"get\", but no id has been provided");
		}
		LOGGER.trace(idStr);
		Request req = null;
			try {
				req = DAOFactory.getDAOFactory().getRequestsDAO().getById(Integer.valueOf(idStr));
			} catch (NumberFormatException | DatabaseException e) {
				LOGGER.error("Cannot get request to display: " + e.getMessage());
			}
		request.setAttribute("req", req);
		request.getRequestDispatcher("/requests/details.jsp").forward(request, response);
	}
}
