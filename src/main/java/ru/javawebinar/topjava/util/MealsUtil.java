package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
	private static final int CALORIES_LIMIT = 2000;

	public static int getCaloriesLimit() {
		return CALORIES_LIMIT;
	}

	public static List<MealTo> getFilteredWithExcess(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
				.collect(
						Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
				);

		return meals.stream()
				.filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
				.map(meal -> new MealTo(
						meal.getId(),
						meal.getDateTime(),
						meal.getDescription(),
						meal.getCalories(),
						caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
				.collect(Collectors.toList());
	}

	public static List<MealTo> getAllWithExcess(List<Meal> meals, int caloriesPerDay) {
		return getFilteredWithExcess(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
	}
}