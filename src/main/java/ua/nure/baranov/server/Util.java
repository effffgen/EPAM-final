package ua.nure.baranov.server;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.baranov.dao.DatabaseException;
import ua.nure.baranov.dao.factory.DAOFactory;
import ua.nure.baranov.entity.Flight;

public class Util {
	private Util() {
	}

	private static final Logger LOGGER = LogManager.getLogger();

	public static Flight getFlight(int id) throws IOException {
		Flight flight = null;
		try {
			flight = DAOFactory.getDAOFactory().getFlightDAO().getFlightByID(id);
		} catch (DatabaseException e) {
			LOGGER.error("Unable to find flight with the id " + id);
			throw new IOException(e);
			// TODO: consider whether it is good or bad practice
		}
		return flight;
	}
}
