package ua.nure.baranov.dao;

import java.util.List;

import ua.nure.baranov.entity.Role;
import ua.nure.baranov.entity.User;

public interface UserDAO {
	User createUser(User user) throws DatabaseException;
	
	List<User> findUsersByRole(Role role) throws DatabaseException;
	
	User findByLoginPass(String login, String password) throws DatabaseException;

	User getUserByID(int id) throws DatabaseException;
	
	boolean isUsernameTaken(String username) throws DatabaseException;
}
