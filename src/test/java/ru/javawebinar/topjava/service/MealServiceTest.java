package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
		"classpath:spring/spring-app.xml",
		"classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

	static {
		SLF4JBridgeHandler.install();
	}

	@Autowired
	private MealService service;

	@Autowired
	private DbPopulator dbPopulator;

	@Before
	public void setUp() throws Exception {
		dbPopulator.execute();
	}

	@Test(expected = NotFoundException.class)
	public void testNotFoundDelete() throws Exception {
		service.delete(1, USER_ID);
	}

	@Test(expected = NotFoundException.class)
	public void testGetNotFound() throws Exception {
		service.get(1, USER_ID);
	}

	@Test(expected = NotFoundException.class)
	public void testUpdateNotFound() throws Exception {
		Meal mealUpd = new Meal();
		mealUpd.setId(1);
		mealUpd.setDescription("Updated description");
		mealUpd.setCalories(100);
		service.update(mealUpd, USER_ID);
		MATCHER.assertEquals(mealUpd, service.get(mealUpd.getId(), USER_ID));
	}

	@Test
	public void get() throws Exception {
		Meal meal = service.get(USER_MEALS_LIST.get(0).getId(), USER_ID);
		MATCHER.assertEquals(meal, USER_MEALS_LIST.get(0));
	}

	@Test
	public void delete() throws Exception {
		service.delete(ADMIN_MEALS_LIST.get(0).getId(), ADMIN_ID);
		ADMIN_MEALS_LIST.remove(0);
		MATCHER.assertCollectionEquals(ADMIN_MEALS_LIST, service.getAll(ADMIN_ID));
	}

	@Test
	public void getBetweenDates() throws Exception {
		Collection<Meal> meals = service.getBetweenDates(LocalDate.parse("2007-12-03"), LocalDate.parse("2007-12-03"), USER_ID);
		MATCHER.assertCollectionEquals(USER_MEALS_LIST, meals);
	}

	@Test
	public void getBetweenDateTimes() throws Exception {
		Collection<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2007,12,03,1,0,0), LocalDateTime.of(2007,12,03,23,59,59), USER_ID);
		MATCHER.assertCollectionEquals(USER_MEALS_LIST, meals);
	}

	@Test
	public void getAll() throws Exception {
		Collection<Meal> meals = service.getAll(USER_ID);
		MATCHER.assertCollectionEquals(USER_MEALS_LIST, meals);
	}

	@Test
	public void update() throws Exception {
		Meal mealUpd = new Meal(USER_MEALS_LIST.get(0));
		mealUpd.setDescription("Updated description");
		mealUpd.setCalories(100);
		service.update(mealUpd, USER_ID);
		MATCHER.assertEquals(mealUpd, service.get(mealUpd.getId(), USER_ID));
	}

	@Test
	public void save() throws Exception {
		Meal meal = new Meal(null, LocalDateTime.now(), "new lunch", 1200);
		Meal createdMeal = service.save(meal, USER_ID);
		meal.setId(createdMeal.getId());
		USER_MEALS_LIST.add(createdMeal);
		MATCHER.assertCollectionEquals(USER_MEALS_LIST, service.getAll(USER_ID));
		USER_MEALS_LIST.remove(createdMeal);
	}

}