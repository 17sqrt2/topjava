package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMockImpl implements MealDao {
	private Map<Integer, Meal> mealMap;
	private AtomicInteger mapSize;

	public MealDaoMockImpl() {
		mealMap = new ConcurrentHashMap<>();
		mapSize = new AtomicInteger(mealMap.size());
		add(new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
		add(new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
		add(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
		add(new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
		add(new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
		add(new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
	}

	@Override
	public Meal add(Meal meal) {
		int id = mapSize.incrementAndGet();
		meal.setId(id);
		mealMap.put(id, meal);
		return new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
	}

	@Override
	public void update(int id, Meal meal) {
		mealMap.replace(id, meal);
	}

	@Override
	public void delete(int id) {
		mealMap.remove(id);
	}

	@Override
	public List<Meal> getAll() {
		return new ArrayList<>(mealMap.values());
	}

	@Override
	public Meal get(int id) {
		Meal meal = mealMap.get(id);
		return new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
	}
}
