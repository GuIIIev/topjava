package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(19, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(19, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        List<UserMeal> userMealList = new ArrayList<>();
        List<UserMealWithExcess> userMealWithExcessesList = new ArrayList<>();


        for (UserMeal m : meals) {
            if (TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealList.add(m);
                caloriesMap.merge(m.getDateTime().toLocalDate(), m.getCalories(), Integer::sum);
            }
        }

        for (UserMeal s : userMealList) {
            userMealWithExcessesList.add(
                    new UserMealWithExcess(
                            s.getDateTime(),
                            s.getDescription(),
                            s.getCalories(),
                            caloriesMap.get(s.getDateTime().toLocalDate()) > caloriesPerDay
                    )
            );
        }

        return userMealWithExcessesList;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMeal> userMealList = meals
                .stream()
                .filter(element -> TimeUtil.isBetweenHalfOpen(element.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());


        Map<LocalDate, Integer> caloriesMap = userMealList.stream().reduce(
                new HashMap<>(),
                (hashMap, e) -> {
                    hashMap.merge(e.getDateTime().toLocalDate(), e.getCalories(), Integer::sum);
                    return hashMap;
                },
                (m, m2) -> {
                    m.putAll(m2);
                    return m;
                });

        return userMealList.stream()
                .map(el -> new UserMealWithExcess(
                        el.getDateTime(),
                        el.getDescription(),
                        el.getCalories(),
                        caloriesMap.get(el.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}