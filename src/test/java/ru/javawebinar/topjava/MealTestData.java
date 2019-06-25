package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
	public static final List<Meal> USER_MEALS = Stream.of(
			new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "User breakfast", 500),
			new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "User lunch", 1000),
			new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "User dinner", 500),
			new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "User breakfast", 1000),
			new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "User lunch", 500),
			new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "User dinner", 510)
	).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());

	public static final List<Meal> ADMIN_MEALS = Stream.of(
			new Meal(START_SEQ + 8, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Admin lunch", 510),
			new Meal(START_SEQ + 9, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Admin dinner", 1500)
	).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
}
