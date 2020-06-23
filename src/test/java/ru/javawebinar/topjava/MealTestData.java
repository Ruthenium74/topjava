package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int FIRST_MEAL_ID = 100002;
    public static final Meal FIRST_MEAL = new Meal(FIRST_MEAL_ID, LocalDateTime.parse("2020-06-21T09:10:00"),
            "Завтрак", 400);
    public static final int FIRST_MEAL_OWNER_ID = UserTestData.USER_ID;

    public static Meal getNew() {
        return new Meal(LocalDateTime.parse("2020-06-24T09:22:52"), "NewMeal", 999);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(FIRST_MEAL);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(500);
        updated.setDateTime(LocalDateTime.parse("2020-06-23T16:06:20"));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertContains(Iterable<Meal> actual, Meal... expectedContain) {
        assertThat(actual).usingFieldByFieldElementComparator().contains(expectedContain);
    }

    public static void assertDoesntContain(Iterable<Meal> actual, Meal... expectedDontContain) {
        assertThat(actual).usingFieldByFieldElementComparator().doesNotContain(expectedDontContain);
    }
}
