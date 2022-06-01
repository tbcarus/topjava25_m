package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Create meal {} for user {}", meal, userId);
        ValidationUtil.checkNew(meal);
        return service.create(meal, userId);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void update(Meal meal) {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Update meal {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Get all meals for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.getAuthUserId();
        log.info("Get all meals for user {} filtered by date and time", userId);
        return MealsUtil.getTos(service.getAllBetween(userId, startDate, endDate, startTime, endTime), SecurityUtil.authUserCaloriesPerDay());
    }
}