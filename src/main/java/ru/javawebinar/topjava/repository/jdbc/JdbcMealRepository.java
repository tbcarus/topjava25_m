package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            jdbcTemplate.update("INSERT INTO meals (user_id, date_time, description, calories) " +
                            "VALUES (?, ?, ?, ?)",
                    userId, meal.getDateTime(), meal.getDescription(), meal.getCalories());
            return meal;
        } else {
            return jdbcTemplate.update(
                    "UPDATE meals SET date_time = ?, description = ?, calories =? " +
                            "WHERE user_id = ?",
                    meal.getDateTime(), meal.getDescription(), meal.getCalories(), userId) != 0 ? meal : null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id = ? AND user_id = ?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query(
                "SELECT id, date_time, description, calories " +
                        "FROM meals " +
                        "WHERE id = ? " +
                        "AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT id, date_time, description, calories " +
                        "FROM meals " +
                        "WHERE user_id = ? " +
                        "ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query(
                "SELECT id, date_time, description, calories " +
                        "FROM meals " +
                        "WHERE user_id = ? " +
                        "AND date_time >= ? " +
                        "AND date_time < ? " +
                        "ORDER BY date_time DESC", ROW_MAPPER, userId, startDateTime, endDateTime);
    }
}
