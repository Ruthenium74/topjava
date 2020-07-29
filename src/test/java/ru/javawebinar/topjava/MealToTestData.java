package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealToTestData {
    public static final MealTo MEAL_TO_1 = MealsUtil.createTo(MealTestData.MEAL1, false);
    public static final MealTo MEAL_TO_2 = MealsUtil.createTo(MealTestData.MEAL2, false);
    public static final MealTo MEAL_TO_3 = MealsUtil.createTo(MealTestData.MEAL3, false);
    public static final MealTo MEAL_TO_4 = MealsUtil.createTo(MealTestData.MEAL4, true);
    public static final MealTo MEAL_TO_5 = MealsUtil.createTo(MealTestData.MEAL5, true);
    public static final MealTo MEAL_TO_6 = MealsUtil.createTo(MealTestData.MEAL6, true);
    public static final MealTo MEAL_TO_7 = MealsUtil.createTo(MealTestData.MEAL7, true);

    public static final List<MealTo> MEAL_TOS = List.of(MEAL_TO_7, MEAL_TO_6, MEAL_TO_5, MEAL_TO_4, MEAL_TO_3,
            MEAL_TO_2, MEAL_TO_1);
}
