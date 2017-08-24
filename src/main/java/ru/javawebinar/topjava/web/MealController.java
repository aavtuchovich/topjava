package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

@Controller
public class MealController {
    @Autowired
    private MealService mealService;

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam int id) {
        int userId = AuthorizedUser.id();
        mealService.delete(id, userId);
        return "redirect:meals";
    }

    /*
      case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, @RequestParam int id) {
        return "redirect:meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model, @RequestParam int id) {
        return "redirect:meals";
    }

}
