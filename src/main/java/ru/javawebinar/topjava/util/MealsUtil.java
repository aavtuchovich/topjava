package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

	public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime, LocalTime endTime,
	                                                           int caloriesPerDay) {
		Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
				.collect(
						Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
				);

		return meals.stream()
				.filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
				.map(meal ->
						new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
								caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
				.collect(Collectors.toList());
	}
}