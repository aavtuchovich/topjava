package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class MealController extends MealRestController {
	@Autowired
	public MealController(MealService service) {
		super(service);
	}


	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String remove(@RequestParam("id") int id) {
		delete(id);
		return "redirect:meals";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
				request.getParameter("description"),
				Integer.parseInt(request.getParameter("calories")));
		if (getId(request).isEmpty()) {
			create(meal);
		} else {
			update(meal, Integer.parseInt(getId(request)));
		}
		return "redirect:meals";
	}

	private String getId(HttpServletRequest request) {
		return request.getParameter("id");
	}

	@RequestMapping(value = "filter", method = RequestMethod.POST)
	public String filter(Model model, HttpServletRequest request) {
		LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
		LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
		LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
		LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
		model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
		return "meals";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Model model, @RequestParam("id") int id) {
		model.addAttribute("meal", get(id));
		model.addAttribute("action", "update");
		return "mealForm";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
				"", 1000));
		model.addAttribute("action", "create");
		return "mealForm";
	}

	@RequestMapping(value = "/meals", method = RequestMethod.GET)
	public String getAll(Model model) {
		model.addAttribute("meals", getAll());
		return "meals";
	}
}
