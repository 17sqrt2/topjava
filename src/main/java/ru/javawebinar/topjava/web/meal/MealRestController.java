package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.getAuthUserId();
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.getAuthUserId();
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.getAuthUserId();
        return service.get(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.getAuthUserId();
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.getAuthUserId();
        return MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.getAuthUserId();

        List<Meal> meals = service.getFilterDate(
                startDate == null ? LocalDate.MIN : startDate,
                endDate == null ? LocalDate.MAX : endDate,
                userId
        );

        return MealsUtil.getFilteredWithExcess(
                meals,
                SecurityUtil.authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime
        );
    }
}