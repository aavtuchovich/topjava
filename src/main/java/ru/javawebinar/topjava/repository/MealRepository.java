package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal Meal);

    void delete(int id)throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    Collection<Meal> getAll();

    Collection<Meal> getAllByUserId(int userId);
}
