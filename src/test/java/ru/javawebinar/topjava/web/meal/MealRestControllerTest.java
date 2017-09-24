package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealRestControllerTest extends AbstractControllerTest {
	private static final String REST_URL = MealRestController.REST_URL + '/';

	@Test
	public void getAll() throws Exception {
		mockMvc.perform(get(REST_URL))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MATCHER.contentListMatcher(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
	}

	@Test
	public void getMeal() throws Exception {
		mockMvc.perform(get(REST_URL + MEAL1_ID))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MATCHER.contentMatcher(MEAL1));
	}

	@Test
	public void removeMeal() throws Exception {
		mockMvc.perform(delete(REST_URL + MEAL1_ID))
				.andExpect(status().isOk())
				.andDo(print());
		MATCHER.assertListEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(START_SEQ));
	}

	@Test
	public void create() throws Exception {
		// TODO: 22.09.2017 Added test create
		Meal expectedMeal = new Meal(LocalDateTime.parse("2017-12-03T10:15:30"), "new meal test", 2000);
		ResultActions action = mockMvc.perform(post(REST_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.writeValue(expectedMeal))).andExpect(status().isCreated());
		Meal returned = MATCHER.fromJsonAction(action);
		expectedMeal.setId(returned.getId());
		MATCHER.assertEquals(expectedMeal, returned);
		MATCHER.assertListEquals(Arrays.asList(expectedMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(START_SEQ));
	}

	@Test
	public void update() throws Exception {
		Meal updated = MEAL1;
		updated.setCalories(50);
		mockMvc.perform(put(REST_URL + MEAL1_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.writeValue(updated)))
				.andExpect(status().isOk());

		MATCHER.assertEquals(updated, mealService.get(MEAL1_ID,START_SEQ));
		updated.setCalories(500);
	}

	@Test
	public void getBetween() throws Exception {
		mockMvc.perform(get(REST_URL + "getBetween?startDate=" + "2001-01-01"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MATCHER.contentListMatcher(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
	}

}