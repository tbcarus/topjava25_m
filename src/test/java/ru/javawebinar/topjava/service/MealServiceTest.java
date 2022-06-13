package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

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
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MealTestData.counter + 2, USER_ID);
        assertMatch(meal, MealTestData.meal3);
    }
    @Test
    public void getWrong() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.counter + 2, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MealTestData.counter + 1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.counter + 1, USER_ID));
    }
    @Test
    public void deleteWrong() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.counter + 1, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> list = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30)
                , LocalDate.of(2020, Month.JANUARY, 30)
                , USER_ID);
        MealTestData.assertMatch(list
                , MealTestData.meal3
                , MealTestData.meal2
                , MealTestData.meal1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, MealTestData.meal7
                , MealTestData.meal6
                , MealTestData.meal5
                , MealTestData.meal4
                , MealTestData.meal3
                , MealTestData.meal2
                , MealTestData.meal1);
    }

    @Test
    public void update() {
        Meal updated = service.get(MealTestData.counter + 2, USER_ID);
        updated.setDescription("UPDATED");
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 12, 10, 12));
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(updated.getId(), USER_ID), updated);
    }

    @Test
    public void updateWrong() {
        Meal updated = service.get(MealTestData.counter + 2, USER_ID);
        updated.setDescription("UPDATED");
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(created.getId(), USER_ID), newMeal);
    }
}