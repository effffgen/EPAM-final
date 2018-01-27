package ua.nure.baranov.dao;

import java.util.List;

import ua.nure.baranov.entity.Entity;

public interface PaginatedDAO<T extends Entity> {

	List<T> getById(Integer id, int offset, int count);
	
	
}
