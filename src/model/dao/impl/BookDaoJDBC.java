package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.BookDao;
import model.entities.Author;
import model.entities.Book;

public class BookDaoJDBC implements BookDao {

	private Connection conn;

	public BookDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Book findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findByAuthor(Author a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Book a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Book a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
