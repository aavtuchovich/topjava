package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("datajpa")
public class DataJpaMealRepositoryImpl implements MealRepository {

	private static final Sort SORT_BY_DATETIME = new Sort("date_time");
	@Autowired
	private CrudMealRepository crudRepository;
	@Autowired
	private CrudUserRepository crudUserRepository;

	@Override
	public Meal save(Meal meal, int userId) {
		if (!meal.isNew() && get(meal.getId(), userId) == null) {
			return null;
		}
		meal.setUser(crudUserRepository.findOne(userId));
		return crudRepository.save(meal);
	}

	@Override
	public boolean delete(int id, int userId) {
		return crudRepository.delete(id, userId);
	}

	@Override
	public Meal get(int id, int userId) {
		Meal meal = crudRepository.findOne(id);
		return meal != null && meal.getUser().getId() == userId ? meal : null;
	}

	@Override
	public List<Meal> getAll(int userId) {
		return crudRepository.getAll(userId);
	}

	@Override
	public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
		return crudRepository.getBetween(startDate, endDate, userId);
	}
}
