package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
	}

	public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		return mealList.stream()
				//filtered by time all user meals
				.filter(s -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
				//create new object UserMealWithExceed for filtered object
				.map((meal) -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
						//grouping all user meal and sum calories for every day
						mealList.stream().collect(Collectors.groupingBy((x) -> x.getDateTime()
								.toLocalDate())).get(meal.getDateTime().toLocalDate())
								//check calories for every day and check daily limit
								.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay))
				//return to filtered list
				.collect(Collectors.toList());
	}
}
