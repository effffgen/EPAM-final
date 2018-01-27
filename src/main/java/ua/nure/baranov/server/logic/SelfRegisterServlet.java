package ua.nure.baranov.server.logic;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.mysql.MysqlDAOFactory;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;
import ua.nure.baranov.server.Util;

@WebServlet("/register")
public class SelfRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 6286188159697758124L;

	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		// TODO:check validity and availability of username
		String firstName = req.getParameter("firstname");
		// TODO:check validity
		String lastName = req.getParameter("lastname");
		// TODO:check validity
		String password = req.getParameter("password");
		// TODO:check validity
		String email = req.getParameter("email");
		// TODO:check validity

		LOGGER.trace("USERNAME --> "+username);
		LOGGER.trace("FIRST NAME --> "+firstName);
		LOGGER.trace("LAST NAME --> "+lastName);
		LOGGER.trace("PASSWORD --> "+password);
		LOGGER.trace("EMAIL --> "+email);
		
		String saltedPassword = Util.saltedPassword(password);
		if (saltedPassword != null) {
			Calendar calendar = Calendar.getInstance();
			User user = User.builder()
				.setUsername(username)
				.setPassword(saltedPassword)
				.setCreationDate(calendar)
				.setEmail(email)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setRole(Role.NONE)
				.build();			
			// register user
			try {
				user = MysqlDAOFactory.getDAOFactory().getUserDAO().create(user);
			} catch (DatabaseException e) {
				LOGGER.error("Error during the registration process: " + e.getMessage());
				req.setAttribute("error", e);
				resp.sendRedirect("register.jsp");
				return;
			}
			if (user.getId() != null) {
				HttpSession session = req.getSession();
				session.setAttribute("user", user);
				resp.sendRedirect("regcomplete.jsp");
			}
		} else {
			return;
		}

	}
}