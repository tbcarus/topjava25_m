package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class JspMealController extends AbstractMealController {

    @GetMapping("/meals")
    public String userMeals(Model model) {
        log.info("getAll");
        model.addAttribute("meals", super.getAll());
        return "/meals";
    }

    @GetMapping("/meals/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping("/meals/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(getId(request)));
        return "mealForm";
    }

    @GetMapping("/meals/create")
    public String create(HttpServletRequest request, Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now(), "", 100));
        return "mealForm";
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
