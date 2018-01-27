package ua.nure.baranov.server.logic;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;
import ua.nure.baranov.server.Util;

@WebServlet("/users")
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("act");
		if (action == null) {
			LOGGER.trace("No action has been provided, using default value");
			action = "get";
		}
		LOGGER.trace("users.action --> " + action);
		switch (action) {
		case "get":
			doGetUsers(request, response);
			break;
		case "create":
			createPageRedirect(request, response);
			break;
		case "modify":
			modifyPageRedirect(request, response);
			break;
		default:
			throw new IllegalStateException("Wrong action");
		}

	}

	private void modifyPageRedirect(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, ServletException, IOException {
		String idStr = request.getParameter("id");
		if (idStr != null) {
			try {
				request.setAttribute("action", "modify");
				request.setAttribute("user", DAOFactory.getDAOFactory().getUserDAO().getById(Integer.valueOf(idStr)));
				request.getRequestDispatcher("users/manipulate.jsp").forward(request, response);
			} catch (DatabaseException e) {
				LOGGER.error("Cannot load modify user page: " + e.getMessage());
			}
		} else {
			throw new IllegalStateException("action has been set as \"modify\", but no id has been provided");
		}
	}

	private void createPageRedirect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("action", "create");
		request.getRequestDispatcher("users/manipulate.jsp").forward(request, response);
	}

	private void doGetUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String criteria = request.getParameter("criteria");
		if (criteria == null) {
			LOGGER.trace("No criteria for \"get\" action has been provided, using default values");
			criteria = "new";
		}
		LOGGER.trace("users.get.criteria --> " + criteria);
		try {
			List<User> users;
			switch (criteria) {
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
			request.setAttribute("criteria", criteria);
			request.setAttribute("users", users);
			request.getRequestDispatcher("users/view.jsp").forward(request, response);
		} catch (DatabaseException e) {
			LOGGER.error("Cannot obtain user list: " + e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("act");
		LOGGER.debug("Got user manipulation request");
		LOGGER.trace("Action --> " + action);
		try {
			switch (action) {
			case "create": {
				User user = assembleAndValidateWithPassword(request, response);
				DAOFactory.getDAOFactory().getUserDAO().create(user);
				break;
			}
			case "modify": {
				User user = assembleAndValidateWithId(request, response);
				processModification(user, request, response);
				break;
			}
			case "delete":
				if(processDeletion(request,response)) {
					response.sendRedirect("login");
					return;
				}
				break;
			default:
				throw new IllegalStateException("Wrong action parameter");
			}
		} catch (DatabaseException e) {
			LOGGER.error("Cannot process changes in users list: " + e.getMessage());
			throw new IOException(e);
		}
		response.sendRedirect("users");
	}

	private void processModification(User user, HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		DAOFactory.getDAOFactory().getUserDAO().update(user);
		if(currentUser != null && currentUser.getId().equals(user.getId())) {
			session.setAttribute("user", user);
		}
	}

	private boolean processDeletion(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, DatabaseException {
		HttpSession session = request.getSession();
		String idStr = request.getParameter("id");
		Integer id = Integer.valueOf(idStr);
		DAOFactory.getDAOFactory().getUserDAO().delete(id);
		User user = (User) session.getAttribute("user");
		if(user != null && user.getId().equals(id)) {
			session.setAttribute("user", null);
			return true;
		}
		return false;
	}

	private User assembleAndValidateWithPassword(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		// TODO: validate
		User user = assembleAndValidate(request, response);
		user.setPassword(Util.saltedPassword(password));
		return user;
	}

	private User assembleAndValidateWithId(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		LOGGER.trace("id -->" + id);
		// TODO: validate
		User user = assembleAndValidate(request, response);
		user.setId(Integer.valueOf(id));
		return user;
	}

	
	
	private User assembleAndValidate(HttpServletRequest request, HttpServletResponse response) {
		Calendar calendar = Calendar.getInstance();
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String role = request.getParameter("role");
		// TODO: server-side validation
		User user = User.builder().setUsername(username).setFirstName(firstName).setLastName(lastName).setEmail(email)
				.setRole(Role.valueOf(role)).setCreationDate(calendar).build();

		return user;
	}

}
