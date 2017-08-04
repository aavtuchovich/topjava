package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MealTestData {

	public static final List<Meal> USER_MEALS_LIST;
	public static final List<Meal> ADMIN_MEALS_LIST;

	static {
		USER_MEALS_LIST = new ArrayList<>();
		USER_MEALS_LIST.add(new Meal(100003, LocalDateTime.of(2007,12,03,9,15,30), "завтрак", 500));
		USER_MEALS_LIST.add(new Meal(100002, LocalDateTime.of(2007, 12, 03, 12, 15, 30), "обед", 1520));
		USER_MEALS_LIST.add(new Meal(100004, LocalDateTime.of(2007,12,03,21,15,30), "ужин", 320));
		ADMIN_MEALS_LIST = new ArrayList<>();
		ADMIN_MEALS_LIST.add(new Meal(100006, LocalDateTime.of(2007,12,4,7,15,30), "завтрак", 200));
		ADMIN_MEALS_LIST.add(new Meal(100005, LocalDateTime.of(2007,12,4,13,15,30), "обед", 1320));
		ADMIN_MEALS_LIST.add(new Meal(100007, LocalDateTime.of(2007,12,4,19,15,30), "ужин", 220));
	}

	public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
			(expected, actual) -> expected == actual || (
					Objects.equals(expected.getId(), actual.getId())
							&& Objects.equals(expected.getDescription(), actual.getDescription())
							&& Objects.equals(expected.getCalories(), actual.getCalories())
							&& Objects.equals(expected.getDateTime(), actual.getDateTime())
			));
/*

    public static final BeanMatcher<User> MATCHER = new BeanMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getCaloriesPerDay(), actual.getCaloriesPerDay())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
//                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );
 */
}
