package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal create(Meal meal);
    Meal read(long id);
    Meal update(Meal meal);
    Meal delete(long id);
    List<Meal> getAll();
}
