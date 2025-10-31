package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UserDao;
import model.entities.User;

public class UserDaoJDBC implements UserDao {

	private Connection conn;

	public UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public User findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				User u = instantiateUser(rs);
				return u;
			} else {
				System.out.println("User not found. ");
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
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(User u) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO user (name, email) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, u.getName());
			st.setString(2, u.getEmail());

			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					u.setId(id);
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
	public void update(User u) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE user SET name = ?, email = ? WHERE id = ?");
			st.setString(1, u.getName());
			st.setString(2, u.getEmail());
			st.setInt(3, u.getId());

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
			st = conn.prepareStatement("DELETE FROM user WHERE id = ?");
			st.setInt(1, id);

			int rows = st.executeUpdate();
			if (rows == 0) {
				throw new DbException("User not found. Please enter a valid ID.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	private User instantiateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		return user;
	}

}
