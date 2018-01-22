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

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("Logout requested");
		HttpSession sesion = request.getSession();
		sesion.setAttribute("user", null);
		LOGGER.trace("Now redirecting to login page");
		response.sendRedirect("login");
	}

}
