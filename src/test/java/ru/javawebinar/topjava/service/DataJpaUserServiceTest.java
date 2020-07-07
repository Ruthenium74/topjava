package ru.javawebinar.topjava.service;

import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void getUserWithMeals() throws Exception {
        User user = service.getUserWithMeals(USER_ID);
        MEAL_MATCHER.assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void getUserWithoutMeals() throws Exception {
        User user = service.get(USER_ID);
        Assert.assertThrows(LazyInitializationException.class, () -> user.getMeals().size());
    }
}
