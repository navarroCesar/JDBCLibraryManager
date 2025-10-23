package model.dao;

import java.util.List;

import model.entities.User;

public interface UserDao {

	User findById(Integer id);

	List<User> findAll();

	void insert(User u);

	void update(User u);

	void deleteById(Integer id);
}
