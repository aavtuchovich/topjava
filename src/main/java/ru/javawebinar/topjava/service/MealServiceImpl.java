package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {
	@Autowired
	private MealRepository repository;


	@Override
	public Meal save(Meal meal) {
		return repository.save(meal);
	}

	@Override
	public void delete(int id) throws NotFoundException {
		repository.delete(id);
	}

	@Override
	public Meal get(int id) throws NotFoundException {
		return repository.get(id);
	}

	@Override
	public Collection<Meal> getAllByUserId(int userId) {
		return repository.getAllByUserId(userId);
	}
}