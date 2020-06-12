package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final MealDao mealDAO = new MealDaoInMemory();

    private static final String CREATE_OR_UPDATE = "/meals/meal.jsp";
    private static final String MEALS_LIST = "/meals.jsp";

    private static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forwardLink = "";
        String redirectLink = "";
        Optional<String> action = Optional.ofNullable(req.getParameter("action"));
        log.debug("Exec GET request with " + action.orElse("def") + " action");
        switch (action.orElse("def")) {
            case "create":
                redirectLink = CREATE_OR_UPDATE;
                break;

            case "update":
                forwardLink = CREATE_OR_UPDATE;
                Meal meal = mealDAO.read(Long.parseLong(req.getParameter("mealId")));
                req.setAttribute("meal", meal);
                log.debug("Meal with id=" + meal.getId() + " will be updated");
                break;

            case "delete":
                redirectLink = "/meals";
                mealDAO.delete(Long.parseLong(req.getParameter("mealId")));
                log.debug("Meal with id=" + req.getParameter("mealId") + " has been deleted");
                break;

            default:
                forwardLink = MEALS_LIST;
                req.setAttribute("mealList", MealsUtil.filteredByStreams(mealDAO.getAll(),
                        LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                log.debug("Show all meal");
                break;
        }

        if (redirectLink.isEmpty()) {
            req.getRequestDispatcher(forwardLink).forward(req, resp);
            log.debug("Forward to " + forwardLink);
        } else {
            resp.sendRedirect(req.getContextPath() + redirectLink);
            log.debug("Redirect to " + redirectLink);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime mealTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String idParameter = req.getParameter("id");
        if (idParameter.isEmpty()) {
            Meal meal = new Meal(0, mealTime, description, calories);
            mealDAO.create(meal);
        } else {
            long id = Long.parseLong(idParameter);
            mealDAO.update(new Meal(id, mealTime, description, calories));
        }
        resp.sendRedirect(req.getContextPath() + "/meals");
    }
}
