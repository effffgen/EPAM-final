package ua.nure.baranov.dao;

import java.sql.Connection;
import java.util.List;

import ua.nure.baranov.dao.support.DatabaseException;
import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;

public interface UserDAO extends GenericDAO<User>{
	@Override
	User create(User user) throws DatabaseException;

	@Override
	List<User> getAll() throws DatabaseException;

	@Override
	User getById(Integer id, Connection con) throws DatabaseException;
	
	@Override
	boolean update(User user) throws DatabaseException;
	
	@Override
	boolean delete(Integer id) throws DatabaseException;
	
	User findByLoginPass(String login, String password) throws DatabaseException;
		
	boolean isUsernameTaken(String username) throws DatabaseException;

	boolean setPasswordById(Integer id, String password) throws DatabaseException;

	List<User> getByRole(Role role, boolean withoutRole) throws DatabaseException;
}
