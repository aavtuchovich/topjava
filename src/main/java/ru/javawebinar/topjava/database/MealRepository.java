package ru.javawebinar.topjava.database;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/*
CRUD
 */
public interface MealRepository {
	void add(Meal meal);

	Meal get(int id);

	Collection<Meal> getAll();

	void delete(int id);
}
