package model.dao;

import java.util.List;

import model.entities.Author;

public interface AuthorDao {

	Author findById(Integer id);

	List<Author> findAll();

	void insert(Author a);

	void update(Author a);

	void deleteById(Integer id);

}
