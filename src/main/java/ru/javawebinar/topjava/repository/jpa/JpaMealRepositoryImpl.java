package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public Meal save(Meal meal, int userId) {
		User ref = em.getReference(User.class, userId);
		meal.setUser(ref);
		if (meal.isNew()) {
			em.persist(meal);
			return meal;
		} else {
			return em.merge(meal);
		}
	}

	@Override
	@Transactional
	public boolean delete(int id, int userId) {
		return false;
	}

	@Override
	public Meal get(int id, int userId) {
		return null;
	}

	@Override
	public List<Meal> getAll(int userId) {
		return null;
	}

	@Override
	public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
		return null;
	}
}