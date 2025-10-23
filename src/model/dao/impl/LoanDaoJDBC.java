package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.LoanDao;
import model.entities.Book;
import model.entities.Loan;
import model.entities.User;

public class LoanDaoJDBC implements LoanDao {

	private Connection conn;

	public LoanDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Loan findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Loan> findByUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Loan> findByBook(Book b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Loan> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Loan l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Loan l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
