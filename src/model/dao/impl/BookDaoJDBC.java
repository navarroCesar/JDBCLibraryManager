package model.dao.impl;

import java.sql.Connection;
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT book.*, author.name AS AuthorName " + "FROM book INNER JOIN author "
					+ "ON book.author_id = author.id WHERE book.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Author a = instantiateAuthor(rs);
				Book b = instantiateBook(rs, a);
				return b;
			} else {
				System.out.println("Book not found. ");
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
	public List<Book> findByAuthor(Author a) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT book.*, author.name AS AuthorName FROM book "
					+ "INNER JOIN author ON book.author_id = author.id " + "WHERE author.id = ? "
					+ "ORDER BY book.title;");
			st.setInt(1, a.getId());
			rs = st.executeQuery();

			List<Book> list = new ArrayList<>();
			while (rs.next()) {
				Book b = instantiateBook(rs, a);
				list.add(b);
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
	public List<Book> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT book.*, author.name AS AuthorName FROM book INNER JOIN author "
					+ "ON book.author_id = author.id " + "ORDER BY book.title");
			rs = st.executeQuery();

			List<Book> list = new ArrayList<>();
			Map<Integer, Author> map = new HashMap<>();
			while (rs.next()) {
				Author a = map.get(rs.getInt("author_id"));
				if (a == null) {
					a = instantiateAuthor(rs);
					map.put(rs.getInt("author_id"), a);
				}
				Book b = instantiateBook(rs, a);
				list.add(b);
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
	public void insert(Book b) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO book (title, author_id) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, b.getTitle());
			st.setInt(2, b.getAuthor().getId());

			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					b.setId(id);
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
	public void update(Book b) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE book SET title = ? WHERE id = ?");
			st.setString(1, b.getTitle());
			st.setInt(2, b.getId());

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
			st = conn.prepareStatement("DELETE FROM Book WHERE id = ?");
			st.setInt(1, id);

			int rows = st.executeUpdate();
			if (rows == 0) {
				throw new DbException("Book not found. Please enter a valid ID.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	private Book instantiateBook(ResultSet rs, Author a) throws SQLException {
		Book b = new Book();
		b.setId(rs.getInt("id"));
		b.setTitle(rs.getString("title"));
		b.setAuthor(a);
		return b;
	}

	private Author instantiateAuthor(ResultSet rs) throws SQLException {
		Author a = new Author();
		a.setId(rs.getInt("author_id"));
		a.setName(rs.getString("AuthorName"));
		return a;
	}

}
