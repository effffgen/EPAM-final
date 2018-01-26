package ua.nure.baranov.dao;

import java.sql.Connection;
import java.util.List;

import ua.nure.baranov.entity.Request;
import ua.nure.baranov.entity.RequestStatus;

public interface RequestDAO extends GenericDAO<Request>{
	
	List<Request> getRequestsByStatus(RequestStatus status) throws DatabaseException;

	@Override
	boolean update(Request request) throws DatabaseException;
	
	@Override
	Request getById(Integer id, Connection connection) throws DatabaseException;
	
	boolean updateStatus(Integer id, RequestStatus status) throws DatabaseException;

	boolean deleteByFlightId(Integer id, Connection connection) throws DatabaseException;
}
