package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((meal -> save(meal, 1)));
        MealsUtil.MEALS_FOR_SECOND_USER.forEach((meal -> save(meal, 2)));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> meals = repository.get(userId);
            if (meals == null) {
                repository.put(userId, new ConcurrentHashMap<>());
                meals = repository.get(userId);
            }
            meals.put(meal.getId(), meal);
            return meal;
        } else {
            if (get(meal.getId(), userId) == null) return null;
            return repository.get(userId).put(meal.getId(), meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if (get(id, userId) == null) return false;
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals == null || meals.get(id) == null) {
            return null;
        }
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFilteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllFilteredByDate(int userId, LocalDate fromDate, LocalDate toDate) {
        return getAllFilteredByPredicate(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), fromDate, toDate));
    }

    private List<Meal> getAllFilteredByPredicate(int userId, Predicate<Meal> filter) {
        return repository.get(userId).values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

