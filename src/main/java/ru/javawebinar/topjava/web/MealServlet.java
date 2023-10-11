package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(UserServlet.class);
    private static final int caloriesPerDay = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //log.debug("redirect to meals");

        //response.sendRedirect("meals.jsp");
        request.setAttribute("mealsTo",
                filteredByStreams(MealTestData.createListMeals(), LocalTime.of(0, 0), LocalTime.of(23, 0), caloriesPerDay)); // 7 -12
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
