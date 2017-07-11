package ru.javawebinar.topjava.util;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserMealsUtilTest {
	private static List<UserMeal> userMeals;
	private String[] description = new String[]{"Завтрак", "Обед", "Ужин"};

	@Before
	public void setUp() throws Exception {
		userMeals = new ArrayList<>();
	}

	public void initArrayList(int count) {
		for (int i = 0; i < count; i++) {
			userMeals.add(new UserMeal(LocalDateTime.of(2015, Month.MAY, new Random().nextInt(25) + 1, new Random()
					.nextInt(23), 0),
					description[new Random().nextInt(3)],
					new Random().nextInt(1000)));
		}
	}

	@Test
	public void getFilteredWithExceeded() throws Exception {
		int count = 1000000;
		initArrayList(count);
		Long startTime = System.currentTimeMillis();
		UserMealsUtil.getFilteredWithExceeded(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		System.out.println("Время выполнения " + userMeals.size() + " записей :" + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		initArrayList(count);
		UserMealsUtil.getFilteredWithExceeded(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		System.out.println("Время выполнения " + userMeals.size() + " записей :" + (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();
		UserMealsUtil.getFilteredWithExceeded(userMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		initArrayList(count * 2);
		System.out.println("Время выполнения " + userMeals.size() + " записей :" + (System.currentTimeMillis() - startTime));
	}

}