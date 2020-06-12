package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoInMemory implements MealDao {
    private final Map<Long, Meal> map = new ConcurrentHashMap<>();
    private final static AtomicLong id = new AtomicLong(1);

    public MealDaoInMemory() {
        create(new Meal(0, LocalDateTime.of(2020, Month.JUNE, 9, 10, 16),
                "Завтрак", 500));
        create(new Meal(0, LocalDateTime.of(2020, Month.JUNE, 9, 12, 20),
                "Обед", 1000));
        create(new Meal(0, LocalDateTime.of(2020, Month.JUNE, 9, 18, 26),
                "Ужин", 500));
        create(new Meal(0, LocalDateTime.of(2020, Month.JUNE, 9, 22, 46),
                "Перекус", 200));
        create(new Meal(0, LocalDateTime.of(2020, Month.JUNE, 10, 10, 16),
                "Завтрак", 500));
        create(new Meal(0, LocalDateTime.of(2020, Month.JUNE, 10, 12, 26),
                "Обед", 1000));
        create(new Meal(0, LocalDateTime.of(2020, Month.JUNE, 10, 19, 6),
                "Ужин", 400));
    }

    @Override
    public Meal create(Meal meal) {
        long newId = id.getAndIncrement();
        Meal createdMeal = new Meal(newId, meal.getDateTime(), meal.getDescription(),
                meal.getCalories());
        map.put(newId, createdMeal);
        return createdMeal;
    }

    @Override
    public Meal read(long id) {
        return map.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        return map.replace(meal.getId(), meal);
    }

    @Override
    public Meal delete(long id) {
        return map.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}
