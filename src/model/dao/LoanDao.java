package model.dao;

import java.util.List;

import model.entities.Book;
import model.entities.Loan;
import model.entities.User;

public interface LoanDao {

	Loan findById(Integer id);

	List<Loan> findByUser(User u);

	List<Loan> findByBook(Book b);

	List<Loan> findAll();;

	void insert(Loan l);

	void update(Loan l);

	void deleteById(Integer id);
}
