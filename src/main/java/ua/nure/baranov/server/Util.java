package ua.nure.baranov.server;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.dao.mysql.MySQLDAOUtils;
import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Flight;

public class Util {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String salt;
	
	private Util() {
	}

	
	static {
		Properties properties = new Properties();
		try {
			properties.load(MySQLDAOUtils.class.getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			LOGGER.fatal("Failed to found properties file, unable to get salt");
			throw new RuntimeException(e);
		}
		salt = properties.getProperty("salt");
	}
	

	public static Flight getFlight(int id) throws IOException {
		Flight flight = null;
		try {
			flight = DAOFactory.getDAOFactory().getFlightDAO().getById(id);
		} catch (DatabaseException e) {
			LOGGER.error("Unable to find flight with the id " + id);
			throw new IOException(e);
			// TODO: consider whether it is good or bad practice
		}
		return flight;
	}
	
	public static String saltedPassword(String password) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No such algorithm, consider rewriting");
			return null;
		}
		return DatatypeConverter.printHexBinary(digest.digest((salt + password).getBytes()));
	}
}
