package ua.nure.baranov.server.logic;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.User;
@WebServlet("/login")
public class Login extends HttpServlet{
	private static final long serialVersionUID = 8097235372201198098L;
	private static final Logger LOGGER = LogManager.getLogger(Login.class);
	private static String salt;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("Login procedure started");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No such algorithm, consider rewriting");
			resp.sendRedirect("login.jsp");
			return;
		}
		String saltedPassword = DatatypeConverter.printHexBinary(digest.digest((salt + password).getBytes()));
		LOGGER.trace("Salted password is --> "+saltedPassword);
		
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

	@Override
	public void init() throws ServletException {
		LOGGER.debug("Login servlet initialization");
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			LOGGER.fatal("Failed to found properties file, unable to get salt");
			throw new RuntimeException(e);
		}
		salt = properties.getProperty("salt");
		LOGGER.debug("Login servlet initialization finished");
	}
	
	
}
