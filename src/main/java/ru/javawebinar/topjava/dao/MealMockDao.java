package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealMockDao {
	Meal add(Meal meal);

	void update(int id, Meal meal);

	void delete(int id);

	List<Meal> getAll();

	Meal get(int id);
}
