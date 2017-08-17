package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

	User create(User user);

	void delete(int id) throws NotFoundException;

	User get(int id) throws NotFoundException;

	User getByEmail(String email) throws NotFoundException;

	default User getWithMeals(int id) {
		throw new UnsupportedOperationException();
	}

	void update(User user);

	void evictCache();

	List<User> getAll();
}