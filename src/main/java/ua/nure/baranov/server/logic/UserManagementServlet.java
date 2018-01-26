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
import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;

@WebServlet("/users")
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("act");
		if (action == null) {
			action = "new";
		}
		try {
			List<User> users;

			switch (action) {
			case "new":
				users = DAOFactory.getDAOFactory().getUserDAO().getByRole(Role.NONE, false);
				break;
			case "registered":
				users = DAOFactory.getDAOFactory().getUserDAO().getByRole(Role.NONE, true);
				break;
			case "all":
				users = DAOFactory.getDAOFactory().getUserDAO().getAll();
				break;
			default:
				throw new IllegalArgumentException("invalid option");
			}

			request.setAttribute("users", users);
			request.getRequestDispatcher("users.jsp").forward(request, response);
		} catch (DatabaseException e) {
			LOGGER.error("Cannot obtain user list: " + e.getMessage());
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
