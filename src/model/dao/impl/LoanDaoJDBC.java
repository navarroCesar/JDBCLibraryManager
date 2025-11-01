package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.LoanDao;
import model.entities.Author;
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT loan.id AS loan_id, loan.user_id AS loan_user_id, loan.book_id AS loan_book_id, "
							+ "loan.borrow_date AS loan_borrow_date, loan.return_date AS loan_return_date, "
							+ "user.id AS user_id, user.name AS user_name, user.email AS user_email, "
							+ "book.id AS book_id, book.title AS book_title, "
							+ "author.id AS author_id, author.name AS author_name " + "FROM loan "
							+ "INNER JOIN user ON loan.user_id = user.id "
							+ "INNER JOIN book ON loan.book_id = book.id "
							+ "INNER JOIN author ON book.author_id = author.id " + "WHERE loan.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Author a = instantiateAuthor(rs);
				Book b = instantiateBook(rs, a);
				User u = instantiateUser(rs);
				Loan l = instantiateLoan(rs, u, b);
				return l;
			} else {
				System.out.println("Loan not found. ");
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Loan> findByUser(User u) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT " + "l.id AS loan_id, " + "l.borrow_date AS loan_borrow_date, "
					+ "l.return_date AS loan_return_date, " + "u.id AS user_id, " + "u.name AS user_name, "
					+ "u.email AS user_email, " + "b.id AS book_id, " + "b.title AS book_title, "
					+ "a.id AS author_id, " + "a.name AS author_name " + "FROM loan l "
					+ "INNER JOIN user u ON l.user_id = u.id " + "INNER JOIN book b ON l.book_id = b.id "
					+ "INNER JOIN author a ON b.author_id = a.id " + "WHERE u.id = ? " + "ORDER BY l.borrow_date DESC");
			st.setInt(1, u.getId());
			rs = st.executeQuery();

			List<Loan> list = new ArrayList<>();
			Map<Integer, User> userMap = new HashMap<>();
			Map<Integer, Book> bookMap = new HashMap<>();

			while (rs.next()) {
				User user = userMap.get(rs.getInt("user_id"));
				if (user == null) {
					user = instantiateUser(rs);
					userMap.put(rs.getInt("user_id"), user);
				}

				Author author = new Author();
				author.setId(rs.getInt("author_id"));
				author.setName(rs.getString("author_name"));

				Book book = bookMap.get(rs.getInt("book_id"));
				if (book == null) {
					book = instantiateBook(rs, author);
					bookMap.put(rs.getInt("book_id"), book);
				}

				Loan loan = instantiateLoan(rs, user, book);
				list.add(loan);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Loan> findByBook(Book b) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT " + "l.id AS loan_id, " + "l.borrow_date AS loan_borrow_date, "
					+ "l.return_date AS loan_return_date, " + "u.id AS user_id, " + "u.name AS user_name, "
					+ "u.email AS user_email, " + "b.id AS book_id, " + "b.title AS book_title, "
					+ "a.id AS author_id, " + "a.name AS author_name " + "FROM loan l "
					+ "INNER JOIN user u ON l.user_id = u.id " + "INNER JOIN book b ON l.book_id = b.id "
					+ "INNER JOIN author a ON b.author_id = a.id " + "WHERE b.id = ? " + "ORDER BY l.borrow_date DESC");
			st.setInt(1, b.getId());
			rs = st.executeQuery();

			List<Loan> list = new ArrayList<>();
			Map<Integer, User> userMap = new HashMap<>();
			Map<Integer, Book> bookMap = new HashMap<>();

			while (rs.next()) {
				User user = userMap.get(rs.getInt("user_id"));
				if (user == null) {
					user = instantiateUser(rs);
					userMap.put(rs.getInt("user_id"), user);
				}

				Author author = new Author();
				author.setId(rs.getInt("author_id"));
				author.setName(rs.getString("author_name"));

				Book book = bookMap.get(rs.getInt("book_id"));
				if (book == null) {
					book = instantiateBook(rs, author);
					bookMap.put(rs.getInt("book_id"), book);
				}

				Loan loan = instantiateLoan(rs, user, book);
				list.add(loan);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Loan> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT " + "l.id AS loan_id, " + "l.borrow_date AS loan_borrow_date, "
					+ "l.return_date AS loan_return_date, " + "u.id AS user_id, " + "u.name AS user_name, "
					+ "u.email AS user_email, " + "b.id AS book_id, " + "b.title AS book_title, "
					+ "a.id AS author_id, " + "a.name AS author_name " + "FROM loan l "
					+ "INNER JOIN user u ON l.user_id = u.id " + "INNER JOIN book b ON l.book_id = b.id "
					+ "INNER JOIN author a ON b.author_id = a.id " + "ORDER BY l.borrow_date DESC");

			rs = st.executeQuery();

			List<Loan> list = new ArrayList<>();
			Map<Integer, User> userMap = new HashMap<>();
			Map<Integer, Book> bookMap = new HashMap<>();

			while (rs.next()) {
				User user = userMap.get(rs.getInt("user_id"));
				if (user == null) {
					user = instantiateUser(rs);
					userMap.put(rs.getInt("user_id"), user);
				}

				Author author = new Author();
				author.setId(rs.getInt("author_id"));
				author.setName(rs.getString("author_name"));

				Book book = bookMap.get(rs.getInt("book_id"));
				if (book == null) {
					book = instantiateBook(rs, author);
					bookMap.put(rs.getInt("book_id"), book);
				}

				Loan loan = instantiateLoan(rs, user, book);
				list.add(loan);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Loan l) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO loan (user_id, book_id, borrow_date, return_date) " + "VALUES (?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, l.getUser().getId());
			st.setInt(2, l.getBook().getId());
			st.setDate(3, new Date(l.getBorrowDate().getTime()));
			st.setDate(4, new Date(l.getReturnDate().getTime()));

			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					l.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Loan l) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE loan SET user_id = ?, book_id = ?, borrow_date = ?, return_date = ? " + "WHERE id = ?");
			st.setInt(1, l.getUser().getId());
			st.setInt(2, l.getBook().getId());
			st.setDate(3, new Date(l.getBorrowDate().getTime()));
			st.setDate(4, new Date(l.getReturnDate().getTime()));
			st.setInt(5, l.getId());

			int rows = st.executeUpdate();
			if (rows == 0) {
				throw new DbException("Unexpected error! No rows affected.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM loan WHERE id = ?");
			st.setInt(1, id);

			int rows = st.executeUpdate();
			if (rows == 0) {
				throw new DbException("Loan not found. Please enter a valid ID.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	private Loan instantiateLoan(ResultSet rs, User user, Book book) throws SQLException {
		Loan loan = new Loan();
		loan.setId(rs.getInt("loan_id"));
		loan.setUser(user);
		loan.setBook(book);
		loan.setBorrowDate(rs.getDate("loan_borrow_date"));
		loan.setReturnDate(rs.getDate("loan_return_date"));
		return loan;
	}

	private User instantiateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("user_id"));
		user.setName(rs.getString("user_name"));
		user.setEmail(rs.getString("user_email"));
		return user;
	}

	private Book instantiateBook(ResultSet rs, Author author) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("book_id"));
		book.setTitle(rs.getString("book_title"));
		book.setAuthor(author);
		return book;
	}

	private Author instantiateAuthor(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setId(rs.getInt("author_id"));
		author.setName(rs.getString("author_name"));
		return author;
	}

}
