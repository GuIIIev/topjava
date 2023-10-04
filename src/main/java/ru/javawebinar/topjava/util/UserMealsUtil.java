package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 8, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 19, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        mealsTo.forEach(System.out::println);

        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        HashMap<LocalDate, Integer> mapCalories = new HashMap<>();
        HashSet<UserMeal> setUserMeal = new HashSet<>();
        int sumCalories = 0;
        List<UserMealWithExcess> UMWEList = new ArrayList<>();
        LocalDate currUserMealLocalDate = meals.get(0).getDateTime().toLocalDate();

        Collections.sort(meals);
        for (UserMeal m : meals) {
            if (m.getDateTime().toLocalTime().isAfter(startTime) && m.getDateTime().toLocalTime().isBefore(endTime)) {
                if (currUserMealLocalDate.equals(m.getDateTime().toLocalDate())) {
                    sumCalories += m.getCalories();
                    setUserMeal.add(m);
                    mapCalories.put(m.getDateTime().toLocalDate(), sumCalories);
                } else {
                    sumCalories = m.getCalories();
                    setUserMeal.add(m);
                    mapCalories.put(m.getDateTime().toLocalDate(), sumCalories);
                    currUserMealLocalDate = m.getDateTime().toLocalDate();
                }
            }

        }

        assert setUserMeal != null;
        for (UserMeal s : setUserMeal) {
            UMWEList.add(
                    new UserMealWithExcess(
                            s.getDateTime(),
                            s.getDescription(),
                            s.getCalories(),
                            mapCalories.get(s.getDateTime().toLocalDate()) > 2000
                    )
            );
        }
        Collections.sort(UMWEList);
        return UMWEList;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
