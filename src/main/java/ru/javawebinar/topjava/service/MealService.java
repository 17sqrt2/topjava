package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
	Meal create(Meal meal, int userId);

	void delete(int id, int userId) throws NotFoundException;

	Meal get(int id, int userId) throws NotFoundException;

	Meal update(Meal meal, int userId) throws NotFoundException;

	List<Meal> getAll(int userId);

	List<Meal> getFilterDate(LocalDate start, LocalDate end, int userId);

	List<Meal> getFilterDateTime(LocalDateTime start, LocalDateTime end, int userId);
}