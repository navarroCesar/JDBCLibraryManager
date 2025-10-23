package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.AuthorDao;
import model.entities.Author;

public class AuthorDaoJDBC implements AuthorDao {

	private Connection conn;

	public AuthorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Author findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Author a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Author a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
