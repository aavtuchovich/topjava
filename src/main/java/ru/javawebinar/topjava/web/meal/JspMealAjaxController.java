package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

@RestController
@RequestMapping("/ajax/meals")
public class JspMealAjaxController extends AbstractMealController {
	@Override
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		super.delete(id);
	}


	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MealWithExceed> getAll() {
		return super.getAll();
	}

}
