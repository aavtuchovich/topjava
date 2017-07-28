package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class InMemoryMealRepositoryImpl implements MealRepository {
	private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
	private AtomicInteger counter = new AtomicInteger(0);

	{
		MealsUtil.MEALS.forEach(this::save);
	}

	@Override
	public Meal save(Meal meal) {
		if (meal.isNew()) {
			meal.setId(counter.incrementAndGet());
		}
		repository.put(meal.getId(), meal);
		return meal;
	}

	@Override
	public void delete(int id) {
		repository.remove(id);
	}

	@Override
	public Meal get(int id) {
		if (repository.containsKey(id)) {
			return repository.get(id);
		}
		return null;
	}

	@Override
	public Collection<Meal> getAll() {
		return repository.values();
	}

	@Override
	public Collection<Meal> getAllByUserId(int userId) {
		return repository.values().stream().filter(((user) -> user.getUserId() == userId)).sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
	}
}

