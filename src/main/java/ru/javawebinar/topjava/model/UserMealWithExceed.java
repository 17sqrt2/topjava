package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserMealWithExceed {
	private final LocalDateTime dateTime;

	private final String description;

	private final int calories;

	private final boolean exceed;

	public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
		this.dateTime = dateTime;
		this.description = description;
		this.calories = calories;
		this.exceed = exceed;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public LocalDate getDate() {
		return dateTime.toLocalDate();
	}

	public LocalTime getTime() {
		return dateTime.toLocalTime();
	}

	public String getDescription() {
		return description;
	}

	public int getCalories() {
		return calories;
	}

	public boolean isExceed() {
		return exceed;
	}

	@Override
	public String toString() {
		return "Meal{" +
				"dateTime=" + dateTime +
				", description='" + description + '\'' +
				", calories=" + calories +
				", exceed=" + exceed +
				'}';
	}
}
