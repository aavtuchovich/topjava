package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

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


}