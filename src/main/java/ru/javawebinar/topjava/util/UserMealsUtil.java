package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
	public static void main(String[] args) {
		List<UserMeal> mealList = Arrays.asList(
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
				new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
		);
		getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
				.forEach(userMealWithExceed -> System.out.println(userMealWithExceed.toString()));
	}

	public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		boolean useStreamApi = false;
		if (useStreamApi)
			return getFilteredWithExceededStreamApi(mealList, startTime, endTime, caloriesPerDay);
		else
			return getFilteredWithExceededArrays(mealList, startTime, endTime, caloriesPerDay);
	}

	private static List<UserMealWithExceed> getFilteredWithExceededArrays(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Map<LocalDate, Integer> dateSums = new HashMap<>();

		List<UserMealWithExceed> mealListWithExceed = new ArrayList<>();
		mealList.forEach(userMeal -> {
			dateSums.compute(userMeal.getDate(), (k, v) -> (v == null ? 0 : v) + userMeal.getCalories());
			boolean exceed = dateSums.get(userMeal.getDate()) > caloriesPerDay;
			if (TimeUtil.isBetween(userMeal.getTime(), startTime, endTime))
				mealListWithExceed.add(
						new UserMealWithExceed(
								userMeal.getDateTime(),
								userMeal.getDescription(),
								userMeal.getCalories(),
								exceed)
				);
			if (exceed) {
				List<Integer> indexes = new ArrayList<>();
				mealListWithExceed.forEach(userMealWithExceed -> {
					if (userMealWithExceed.getDate().equals(userMeal.getDate()) && !userMealWithExceed.isExceed())
						indexes.add(mealListWithExceed.indexOf(userMealWithExceed));
				});
				indexes.forEach(index -> {
					UserMealWithExceed auxMeal = mealListWithExceed.get(index);
					mealListWithExceed.set(index,
							new UserMealWithExceed(
									auxMeal.getDateTime(),
									auxMeal.getDescription(),
									auxMeal.getCalories(),
									exceed));
				});
			}
		});
		return mealListWithExceed;
	}

	private static List<UserMealWithExceed> getFilteredWithExceededStreamApi(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Map<LocalDate, Integer> dateSums = mealList.stream().collect(
				Collectors.groupingBy(
						UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)
				)
		);

		return mealList.stream()
				.filter(
						userMeal -> TimeUtil.isBetween(userMeal.getTime(), startTime, endTime))
				.map(userMeal -> new UserMealWithExceed(
						userMeal.getDateTime(),
						userMeal.getDescription(),
						userMeal.getCalories(),
						dateSums.get(userMeal.getDate()) > caloriesPerDay))
				.collect(Collectors.toList());
	}
}
