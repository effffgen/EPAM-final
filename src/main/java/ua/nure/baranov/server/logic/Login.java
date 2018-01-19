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
import ua.nure.baranov.dao.mysql.MySQLDAOUtils;
import ua.nure.baranov.entity.User;
@WebServlet("/login")
public class Login extends HttpServlet{
	private static final long serialVersionUID = 8097235372201198098L;
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("Login procedure started");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String saltedPassword = MySQLDAOUtils.saltedPassword(password);
		LOGGER.trace("Salted password is --> "+saltedPassword);
		if(saltedPassword == null) {
			resp.sendRedirect("login.jsp");
			return;
		}
		User user = null;
		try {
			user = DAOFactory.getDAOFactory().getUserDAO().findByLoginPass(login, saltedPassword);
		} catch (DatabaseException e) {
			LOGGER.error("Cannot authentethicate user");
			resp.sendRedirect("login.jsp");
			return;
		}
		HttpSession session = req.getSession();
		if (user != null) {
			session.removeAttribute("error");
			session.setAttribute("user", user);
			properRedirect(user, req, resp);
			return;
		}
		else {
			session.setAttribute("error", "Invalid login or password");
			resp.sendRedirect("login.jsp");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
		else {
			properRedirect(user, req, resp);
		}
	}
	
	
	private void properRedirect(User user, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//TODO: write proper redirects
		resp.sendRedirect("dashboard");
	}

	
	
}
