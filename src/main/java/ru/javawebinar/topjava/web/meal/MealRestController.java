package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal get(int id) {
        log.info("get meal with id={} for userId={}", id, authUserId());
        return service.get(id, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("getAll for userId={}", authUserId());
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create {} for userId={}", meal, authUserId());
        return service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        log.info("update {} for userId={}", meal, authUserId());
        service.update(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete id={} for userId={}", id, authUserId());
        service.delete(id, authUserId());
    }

    public List<MealTo> getByDateTime(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        log.info("getBy Date from={} to={} and Time from={} to={} for userId={}", fromDate, toDate, fromTime, toTime,
                authUserId());
        return MealsUtil.getTos(service.getAllFilteredByDate(authUserId(), fromDate,
                toDate), authUserCaloriesPerDay()).stream()
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalTime(), fromTime, toTime))
                .collect(Collectors.toList());
    }
}