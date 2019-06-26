package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

//import static org.junit.Assert.assertThat;

@ContextConfiguration({
		"classpath:spring/spring-app.xml",
		"classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

	static {
		// Only for postgres driver logging
		// It uses java.util.logging and logged via jul-to-slf4j bridge
		SLF4JBridgeHandler.install();
	}

	@Autowired
	private MealService service;

	@Test
	public void get() throws Exception {
		Meal meal = service.get(USER_FIRST_MEAL_ID, USER_ID);
		assertMatch(USER_MEALS.get(0), meal);
	}

	@Test(expected = NotFoundException.class)
	public void getNotFound() throws Exception {
		service.get(WRONG_MEAL_ID, ADMIN_ID);
	}

	@Test(expected = NotFoundException.class)
	public void getNotYours() throws Exception {
		service.get(USER_FIRST_MEAL_ID, ADMIN_ID);
	}

	@Test
	public void delete() {
		service.delete(USER_FIRST_MEAL_ID, USER_ID);
		assertMatch(service.getAll(USER_ID), USER_MEALS.subList(1, USER_MEALS.size()));
	}

	@Test(expected = NotFoundException.class)
	public void deletedNotFound() throws Exception {
		service.delete(WRONG_MEAL_ID, ADMIN_ID);
	}

	@Test(expected = NotFoundException.class)
	public void deletedNotYours() throws Exception {
		service.delete(ADMIN_FIRST_MEAL_ID, USER_ID);
	}

	@Test
	public void getBetweenDates() {
		LocalDate start = LocalDate.of(2015, Month.MAY, 31);
		LocalDate end = LocalDate.of(2015, Month.MAY, 31);
		List<Meal> actual = service.getBetweenDates(start, end, USER_ID);
		List<Meal> expected = USER_MEALS.stream()
				.filter(meal -> Util.isBetween(meal.getDate(), start, end))
				.collect(Collectors.toList());
		assertMatch(actual, expected);
	}

	@Test
	public void getBetweenDateTimes() {
		LocalDateTime start = LocalDateTime.of(2015, Month.MAY, 31, 13, 0);
		LocalDateTime end = LocalDateTime.of(2015, Month.MAY, 31, 20, 0);
		List<Meal> actual = service.getBetweenDateTimes(start, end, USER_ID);
		List<Meal> expected = USER_MEALS.stream()
				.filter(meal -> Util.isBetween(meal.getDateTime(), start, end))
				.collect(Collectors.toList());
		assertMatch(actual, expected);
	}

	@Test
	public void getAllUser() {
		assertMatch(service.getAll(USER_ID), USER_MEALS);
	}

	@Test
	public void getAllAdmin() {
		assertMatch(service.getAll(ADMIN_ID), ADMIN_MEALS);
	}

	@Test
	public void update() {
		Meal updated = new Meal(ADMIN_MEALS.get(0));
		updated.setDescription("Admin supper");
		updated.setCalories(1450);
		service.update(updated, ADMIN_ID);
		assertMatch(service.get(updated.getId(), ADMIN_ID), updated);
	}

	@Test(expected = NotFoundException.class)
	public void updateNotFound() throws Exception {
		Meal notExistingMeal = new Meal(WRONG_MEAL_ID, LocalDateTime.of(2015, Month.JUNE, 1, 11, 0), "Admin breakfast", 50);
		service.update(notExistingMeal, ADMIN_ID);
	}

	@Test(expected = NotFoundException.class)
	public void updateNotYours() throws Exception {
		Meal meal = service.get(ADMIN_FIRST_MEAL_ID, ADMIN_ID);
		service.update(meal, USER_ID);
	}

	@Test
	public void create() {
		Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 11, 0), "Admin breakfast", 50);
		Meal created = service.create(newMeal, ADMIN_ID);
		newMeal.setId(created.getId());
		List<Meal> expected = new ArrayList<>(ADMIN_MEALS);
		expected.add(newMeal);
		assertMatch(service.getAll(ADMIN_ID), expected.stream()
				.sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()));
	}

	@Test(expected = DuplicateKeyException.class)
	public void createDuplicate() {
		Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 11, 0), "Admin breakfast", 50);
		Meal firstMeal = service.create(newMeal, ADMIN_ID);
		newMeal.setId(null);
		Meal duplicateMeal = service.create(newMeal, ADMIN_ID);
	}
}