package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.formatter.LocalDateFormat;
import ru.javawebinar.topjava.util.formatter.LocalTimeFormat;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        if (meal.getId() == null) {
            meal.setId(id);
            super.update(meal, id);
        } else if (meal.getId().equals(id)) {
            super.update(meal, id);
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/filter")
    public List<MealTo> getBetween(@RequestParam(required = false) @LocalDateFormat LocalDate fromDate,
                                   @RequestParam(required = false) @LocalTimeFormat LocalTime fromTime,
                                   @RequestParam(required = false) @LocalDateFormat LocalDate toDate,
                                   @RequestParam(required = false) @LocalTimeFormat LocalTime toTime) {
        return super.getBetween(fromDate, fromTime, toDate, toTime);
    }

    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }
}