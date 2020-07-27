package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

public class MealToTestData {
    public static TestMatcher<MealTo> MEAL_TO_MATCHER = TestMatcher.usingFieldsComparator(MealTo.class);

    public static final MealTo MEAL_TO_1 = new MealTo(MealTestData.MEAL1.getId(), MealTestData.MEAL1.getDateTime(),
            MealTestData.MEAL1.getDescription(), MealTestData.MEAL1.getCalories(), false);
    public static final MealTo MEAL_TO_2 = new MealTo(MealTestData.MEAL2.getId(), MealTestData.MEAL2.getDateTime(),
            MealTestData.MEAL2.getDescription(), MealTestData.MEAL2.getCalories(), false);
    public static final MealTo MEAL_TO_3 = new MealTo(MealTestData.MEAL3.getId(), MealTestData.MEAL3.getDateTime(),
            MealTestData.MEAL3.getDescription(), MealTestData.MEAL3.getCalories(), false);
    public static final MealTo MEAL_TO_4 = new MealTo(MealTestData.MEAL4.getId(), MealTestData.MEAL4.getDateTime(),
            MealTestData.MEAL4.getDescription(), MealTestData.MEAL4.getCalories(), true);
    public static final MealTo MEAL_TO_5 = new MealTo(MealTestData.MEAL5.getId(), MealTestData.MEAL5.getDateTime(),
            MealTestData.MEAL5.getDescription(), MealTestData.MEAL5.getCalories(), true);
    public static final MealTo MEAL_TO_6 = new MealTo(MealTestData.MEAL6.getId(), MealTestData.MEAL6.getDateTime(),
            MealTestData.MEAL6.getDescription(), MealTestData.MEAL6.getCalories(), true);
    public static final MealTo MEAL_TO_7 = new MealTo(MealTestData.MEAL7.getId(), MealTestData.MEAL7.getDateTime(),
            MealTestData.MEAL7.getDescription(), MealTestData.MEAL7.getCalories(), true);

    public static final List<MealTo> MEAL_TOS = List.of(MEAL_TO_7, MEAL_TO_6, MEAL_TO_5, MEAL_TO_4, MEAL_TO_3,
            MEAL_TO_2, MEAL_TO_1);
}
