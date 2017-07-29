package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MealService service;

	public Meal get(int id) {
		return service.get(id);
	}

	public void delete(int id) {
		service.delete(id);
	}

	public void update(Meal meal) {
		service.save(meal);
	}

	public Collection<MealWithExceed> getAllByUserId(int userId) {
		return MealsUtil.getWithExceeded(service.getAllByUserId(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
	}

	public List<MealWithExceed> filterMealByDate(LocalDateTime from, LocalDateTime to) {

		List<Meal> meals = service.getAllByUserId(AuthorizedUser.id()).stream().filter((meal -> meal.getDateTime().isAfter(from) && meal.getDate().isBefore(to.toLocalDate()) && meal.getTime().isBefore(to.toLocalTime()))).sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
		return MealsUtil.getWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
	}
}