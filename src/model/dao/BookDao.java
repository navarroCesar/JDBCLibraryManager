package model.dao;

import java.util.List;

import model.entities.Author;
import model.entities.Book;

public interface BookDao {

	Book findById(Integer id);

	List<Book> findByAuthor(Author a);

	List<Book> findAll();

	void insert(Book a);

	void update(Book a);

	void deleteById(Integer id);
}
