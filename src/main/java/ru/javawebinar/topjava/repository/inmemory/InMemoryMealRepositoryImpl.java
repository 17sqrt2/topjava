package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl.USER_ID;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, USER_ID));

        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 1, 10, 0, 0), "New Admin Meal 1", 123), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 10, 10, 0, 0), "New Admin Meal 2", 1321), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2019, Month.JUNE, 10, 14, 0, 0), "New User Meal 1", 1777), USER_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}, user {}", meal, userId);
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal {}, user {}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal {}, user {}", id, userId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? null : mealMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get all meals, user {} ", userId);
        return getFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getFiltered(LocalDateTime start, LocalDateTime end, int userId) {
        log.info("get filtered meals, period ({} .. {}), user {}", start, end, userId);
        return getFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDateTime(), start, end));
    }

    public List<Meal> getFiltered(LocalDate start, LocalDate end, int userId) {
        log.info("get filtered meals, period ({} .. {}), user {}", start, end, userId);
        return getFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), start, end));
    }

    private List<Meal> getFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return CollectionUtils.isEmpty(mealMap) ? Collections.emptyList() : mealMap.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

