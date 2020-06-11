package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAOFactory;
import ru.javawebinar.topjava.dao.MealDAO;
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

    private static final MealDAO mealDAO = DAOFactory.getMealDao();

    private static final String CREATE_OR_UPDATE = "/meal.jsp";
    private static final String MEALS_LIST = "/meals.jsp";

    private static final int caloriesPerDay = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forwardLink = "";
        String redirectLink = "";
        Optional<String> action = Optional.ofNullable(req.getParameter("action"));
        switch (action.orElse("def"))
        {
            case "create":
                redirectLink = req.getContextPath() + CREATE_OR_UPDATE;
                break;

            case "update":
                forwardLink = CREATE_OR_UPDATE;
                Meal meal = mealDAO.read(Long.parseLong(req.getParameter("mealId")));
                req.setAttribute("meal", meal);
                break;

            case "delete":
                redirectLink = "meals";
                mealDAO.delete(Long.parseLong(req.getParameter("mealId")));
                break;

            default:
                forwardLink = MEALS_LIST;
                req.setAttribute("mealList", MealsUtil.filteredByStreams(mealDAO.getAllMeals(),
                        LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
                break;
        }

        if (redirectLink.isEmpty()) {
            req.getRequestDispatcher(forwardLink).forward(req, resp);
        }
        else
        {
            resp.sendRedirect(redirectLink);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime mealTime = LocalDateTime.parse(req.getParameter("dateTime"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Optional<String> idParameter = Optional.ofNullable(req.getParameter("id"));
        long id = Long.parseLong(idParameter.orElse("0"));
        if (id == 0)
        {
            Meal meal = new Meal(0 ,mealTime, description, calories);
            mealDAO.create(meal);
        }
        else
        {
            mealDAO.update(new Meal(id, mealTime, description, calories));
        }
        resp.sendRedirect("meals");
    }
}
