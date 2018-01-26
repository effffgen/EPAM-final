package ua.nure.baranov.server.logic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.User;
import ua.nure.baranov.server.Util;

/**
 * Servlet implementation class UserSettingsServlet
 */
@WebServlet("/settings")
public class UserSettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 5348583022151394694L;
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Password change requested");
		try {

			String action = request.getParameter("action");
			if ("update".equals(action)) {
				performUpdate(request, response);

			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("dashboard");
	}

	private void performUpdate(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String oldPass = request.getParameter("currentPass");
		String newPass = request.getParameter("newPass");
		// TODO: validate

		String oldSalted = Util.saltedPassword(oldPass);
		String newSalted = Util.saltedPassword(newPass);

		LOGGER.trace(oldSalted);
		LOGGER.trace(user.getPassword());
		if (user.getPassword().equals(oldSalted)) {
			DAOFactory.getDAOFactory().getUserDAO().setPasswordById(user.getId(), newSalted);
			user.setPassword(newSalted);
		}

	}

}
