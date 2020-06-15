package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithIdAndOwnerId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithIdAndOwnerId(repository.delete(id, userId), id, userId);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithIdAndOwnerId(repository.get(id, userId), id, userId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Collection<Meal> getAllFilteredByDescriptionAndDateTime(int userId, String description,
                                                                   LocalDate fromDate, LocalDate toDate,
                                                                   LocalTime fromTime, LocalTime toTime) {
        return repository.getAllFilteredByDescriptionAndDateTime(userId, description, fromDate, toDate, fromTime, toTime);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithIdAndOwnerId(repository.save(meal, userId), meal.getId(), userId);
    }
}