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

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.Request;
import ua.nure.baranov.entity.RequestStatus;

/**
 * Servlet implementation class Requests
 */
@WebServlet("/requests")
public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("Requests page requested");
		String action = request.getParameter("act");
		LOGGER.trace("Action --> " + action);
		if(action == null) {
			LOGGER.trace("No action provided, assuming \"new\" action by default");
			action = "new";
		}
		//sending action to the jsp page to be properly displayed
		request.setAttribute("action", action);
		try {
		List<Request> requests = null;
		switch(action) {
		case "new":
			requests = DAOFactory.getDAOFactory().getRequestsDAO().getRequestsByStatus(RequestStatus.ON_APPROVAL);
			break;
		case "approved":
			requests = DAOFactory.getDAOFactory().getRequestsDAO().getRequestsByStatus(RequestStatus.APPROVED);
			break;
		case "rejected":
			requests = DAOFactory.getDAOFactory().getRequestsDAO().getRequestsByStatus(RequestStatus.REJECTED);
			break;
		case "my":
	//		requests = DAOFactory.getDAOFactory().getRequestsDAO().getRequestsByStatus(RequestStatus.ON_APPROVAL);
			break;
		default:
			throw new IllegalArgumentException();
		}
		
		request.setAttribute("requests", requests);
		request.getRequestDispatcher("requests/view.jsp").forward(request, response);
		
		} catch(DatabaseException e) {
			LOGGER.error("Error during getting request list: " + e.getMessage());
		}
		
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("act");
		String idStr = req.getParameter("id");
		LOGGER.debug("Request state change has been requested");
		LOGGER.trace("For request with id --> "+ idStr);
		LOGGER.trace("Action --> " + action);
		RequestStatus newStatus;
		
		switch(action) {
		case "approve":
			newStatus = RequestStatus.APPROVED;
			break;
		case "reject":
			newStatus = RequestStatus.REJECTED;
			break;
		default:
			throw new IllegalArgumentException("Wrong action value");
		}
		if(idStr == null) {
			resp.sendRedirect("requests");
			return;
		}
		int id = Integer.valueOf(idStr);
		boolean result = false;
		try {
			result = DAOFactory.getDAOFactory().getRequestsDAO().updateStatus(id, newStatus);
		} catch (DatabaseException e) {
			LOGGER.error("There was an error during updating status: " + e.getMessage());
		}
		if(result) {
			LOGGER.debug("Status has been changed successfully");
		}
		else {
			LOGGER.warn("Status has not been changed, please check the logs");
		}
		resp.sendRedirect("requests");
	}
}
