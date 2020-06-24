package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal meal = service.get(FIRST_MEAL_ID, FIRST_MEAL_OWNER_ID);
        assertMatch(meal, FIRST_MEAL);
    }

    @Test
    public void getNotOwner() {
        assertThrows(NotFoundException.class, () -> service.get(FIRST_MEAL_ID, FIRST_MEAL_OWNER_ID + 1));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL_ID, FIRST_MEAL_OWNER_ID));
    }

    @Test
    public void delete() {
        service.delete(FIRST_MEAL_ID, FIRST_MEAL_OWNER_ID);
        assertNull(repository.get(FIRST_MEAL_ID, FIRST_MEAL_OWNER_ID));
    }

    @Test
    public void deleteNotOwner() {
        assertThrows(NotFoundException.class, () -> service.delete(FIRST_MEAL_ID, FIRST_MEAL_OWNER_ID + 1));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL_ID, FIRST_MEAL_OWNER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(LocalDate.parse("2020-06-21"), LocalDate.parse("2020-06-21"),
                FIRST_MEAL_OWNER_ID), THIRD_MEAL, FORTH_MEAL, SECOND_MEAL, FIRST_MEAL);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(FIRST_MEAL_OWNER_ID), SEVENTH_MEAL, SIXTH_MEAL, FIFTH_MEAL,
                THIRD_MEAL, FORTH_MEAL, SECOND_MEAL, FIRST_MEAL);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, FIRST_MEAL_OWNER_ID);
        assertMatch(service.get(FIRST_MEAL_ID, FIRST_MEAL_OWNER_ID), updated);
    }

    @Test
    public void updateNotOwner() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), FIRST_MEAL_OWNER_ID + 1));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal createdMeal = service.create(newMeal, FIRST_MEAL_OWNER_ID);
        Integer newId = createdMeal.getId();
        newMeal.setId(newId);
        assertMatch(createdMeal, newMeal);
        assertMatch(service.get(newId, FIRST_MEAL_OWNER_ID), newMeal);
    }
}