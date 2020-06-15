package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        log.info("create {} for userId={}", meal, authUserId());
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} for userId={}", meal, authUserId());
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete id={} for userId={}", id, authUserId());
        service.delete(id, authUserId());
    }

    public List<MealTo> getByDescriptionAndDateTime(String description, String fromDate, String toDate,
                                                    String fromTime, String toTime) {
        log.info("getByDescription={} and Date from={} to={} and Time from={} to={} for userId={}",
                description, fromDate, toDate, fromTime, toTime, authUserId());
        LocalDate parsedFromDate = fromDate == null || fromDate.isEmpty() ? null : LocalDate.parse(fromDate);
        LocalDate parsedToDate = toDate == null || toDate.isEmpty() ? null : LocalDate.parse(toDate);
        LocalTime parsedFromTime = fromTime == null || fromTime.isEmpty() ? null : LocalTime.parse(fromTime);
        LocalTime parsedToTime = toTime == null || toTime.isEmpty() ? null : LocalTime.parse(toTime);
        return MealsUtil.getTos(service.getAllFilteredByDescriptionAndDateTime(authUserId(), description, parsedFromDate,
                parsedToDate, parsedFromTime, parsedToTime), authUserCaloriesPerDay());
    }
}