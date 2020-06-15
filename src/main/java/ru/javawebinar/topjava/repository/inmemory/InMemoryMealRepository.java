package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((meal -> save(meal, 1)));
        MealsUtil.MEALS_FOR_SECOND_USER.forEach((meal -> save(meal, 2)));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            // handle case: update, but not present in storage
            try {
                return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
                    if (oldMeal.getUserId().equals(userId)) {
                        meal.setUserId(oldMeal.getUserId());
                        return meal;
                    }
                    throw new NotFoundException(oldMeal.toString() + " don't belong to userId=" + userId);
                });
            } catch (NotFoundException e) {
                return null;
            }
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null || !meal.getUserId().equals(userId)) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null || !meal.getUserId().equals(userId)) {
            return null;
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFilteredByDescriptionAndDateTime(int userId, String description, LocalDate fromDate,
                                                                   LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        if (description == null) description = "";
        if (fromDate == null) fromDate = LocalDate.MIN;
        if (toDate == null) toDate = LocalDate.MAX;
        if (fromTime == null) fromTime = LocalTime.MIN;
        if (toTime == null) toTime = LocalTime.MAX;
        final String constDescription = description;
        final LocalTime constFromTime = fromTime;
        final LocalTime constToTime = toTime;
        final LocalDate constFromDate = fromDate;
        final LocalDate constToDate = toDate;
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), constFromDate, constToDate) &&
                        DateTimeUtil.isBetweenHalfOpen(meal.getTime(), constFromTime, constToTime) &&
                        meal.getDescription().contains(constDescription))
                .collect(Collectors.toList());
    }
}

