package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.AuthorDao;
import model.entities.Author;

public class AuthorDaoJDBC implements AuthorDao {

	private Connection conn;

	public AuthorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Author findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM author WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Author a = instantiateAuthor(rs);
				return a;
			} else {
				System.out.println("Author not found. ");
				return null;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Author> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * from author");
			rs = st.executeQuery();

			List<Author> list = new ArrayList<>();
			while (rs.next()) {
				Author a = instantiateAuthor(rs);
				list.add(a);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void insert(Author a) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO author (name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, a.getName());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					a.setId(id);
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
	public void update(Author a) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE author SET name = ? WHERE id = ?");
			st.setString(1, a.getName());
			st.setInt(2, a.getId());

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
			st = conn.prepareStatement("DELETE FROM author WHERE id = ?");
			st.setInt(1, id);

			int rows = st.executeUpdate();
			if (rows == 0) {
				throw new DbException("Author not found. Please enter a valid ID.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	private Author instantiateAuthor(ResultSet rs) throws SQLException {
		Author a = new Author();
		a.setId(rs.getInt("id"));
		a.setName(rs.getString("name"));
		return a;

	}

}
