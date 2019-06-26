package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

	private static final Meal USER_MEAL_1 = new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "User breakfast", 500);
	private static final Meal USER_MEAL_2 = new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "User lunch", 1000);
	private static final Meal USER_MEAL_3 = new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "User dinner", 500);
	private static final Meal USER_MEAL_4 = new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "User breakfast", 1000);
	private static final Meal USER_MEAL_5 = new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "User lunch", 500);
	private static final Meal USER_MEAL_6 = new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "User dinner", 510);

	private static final Meal ADMIN_MEAL_1 = new Meal(START_SEQ + 8, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Admin lunch", 510);
	private static final Meal ADMIN_MEAL_2 = new Meal(START_SEQ + 9, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Admin dinner", 1500);

	public static final List<Meal> USER_MEALS = Arrays.asList(USER_MEAL_6, USER_MEAL_5, USER_MEAL_4, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
	public static final List<Meal> ADMIN_MEALS = Arrays.asList(ADMIN_MEAL_2, ADMIN_MEAL_1);

	public static final int USER_FIRST_MEAL_ID = USER_MEALS.get(0).getId();
	public static final int ADMIN_FIRST_MEAL_ID = ADMIN_MEALS.get(0).getId();
	public static final int WRONG_MEAL_ID = 1;

	public static void assertMatch(Meal actual, Meal expected) {
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}

	public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
		assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
	}
}
