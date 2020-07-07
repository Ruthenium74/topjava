package ru.javawebinar.topjava.service;

import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {
    @Autowired
    MealService mealService;

    @Test
    public void getWithUser() throws Exception {
        MEAL_WITH_USER_MATCHER.assertMatch(mealService.getWithUser(MEAL1_ID, USER_ID), getMeal1WithUser());
    }

    @Test
    public void getWithoutUser() throws Exception {
        Meal meal = mealService.get(MEAL1_ID, USER_ID);
        Assert.assertThrows(LazyInitializationException.class, () -> meal.getUser().getEmail());
    }
}
