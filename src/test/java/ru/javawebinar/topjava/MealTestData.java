package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int FIRST_MEAL_ID = START_SEQ + 2;
    public static final Meal FIRST_MEAL = new Meal(FIRST_MEAL_ID,
            LocalDateTime.of(2020, 6, 21, 9, 10, 0), "Завтрак", 400);
    public static final int FIRST_MEAL_OWNER_ID = START_SEQ;
    public static final int NOT_FOUND_MEAL_ID = 1;

    public static final Meal SECOND_MEAL = new Meal(FIRST_MEAL_ID + 1,
            LocalDateTime.of(2020, 6, 21, 13, 20, 0), "Обед", 800);
    public static final Meal THIRD_MEAL = new Meal(FIRST_MEAL_ID + 2,
            LocalDateTime.of(2020, 6, 21, 21, 30, 0), "Ужин", 600);
    public static final Meal FORTH_MEAL = new Meal(FIRST_MEAL_ID + 3,
            LocalDateTime.of(2020, 6, 21, 17, 23, 1), "Перекус", 300);
    public static final Meal FIFTH_MEAL = new Meal(FIRST_MEAL_ID + 4,
            LocalDateTime.of(2020, 6, 22, 8, 30, 0), "Завтрак", 300);
    public static final Meal SIXTH_MEAL = new Meal(FIRST_MEAL_ID + 5,
            LocalDateTime.of(2020, 6, 22, 12, 35, 10), "Обед", 800);
    public static final Meal SEVENTH_MEAL = new Meal(FIRST_MEAL_ID + 6,
            LocalDateTime.of(2020, 6, 22, 19, 35, 50), "Ужин", 500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 6, 24, 9, 22, 52),
                "NewMeal", 999);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(FIRST_MEAL);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(500);
        updated.setDateTime(LocalDateTime.of(2020, 6, 23, 16, 6, 20));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
