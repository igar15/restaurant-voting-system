package ru.igar15.rest_voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.igar15.rest_voting_system.model.Menu;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.igar15.rest_voting_system.MenuTestData.*;
import static ru.igar15.rest_voting_system.RestaurantTestData.RESTAURANT1_ID;
import static ru.igar15.rest_voting_system.RestaurantTestData.RESTAURANT2_ID;

public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Test
    public void create() {
        Menu created = service.create(getNew(), RESTAURANT1_ID);
        int newId = created.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(service.get(newId, RESTAURANT1_ID), newMenu);
    }

    @Test
    public void duplicateDateCreate() {
        assertThrows(DataAccessException.class,
                () -> service.create(new Menu(null, LocalDate.of(2021, Month.FEBRUARY, 25), false), RESTAURANT1_ID));
    }

    @Test
    public void delete() {
        service.delete(MENU1_ID, RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(MENU1_ID, RESTAURANT1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT1_ID));
    }

    @Test
    public void deleteFromAnotherRestaurant() {
        assertThrows(NotFoundException.class, () -> service.delete(MENU1_ID, RESTAURANT2_ID));
    }

    @Test
    public void get() {
        Menu menu = service.get(MENU1_ID, RESTAURANT1_ID);
        MENU_MATCHER.assertMatch(menu, menu1);
    }

    @Test
    public void getByDate() {
        Menu menu = service.getByDate(RESTAURANT1_ID, LocalDate.of(2021, Month.FEBRUARY, 25));
        MENU_MATCHER.assertMatch(menu, menu1);
    }

    @Test
    public void getByDateNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByDate(RESTAURANT1_ID, LocalDate.of(2021, Month.FEBRUARY, 20)));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT1_ID));
    }

    @Test
    public void getFromAnotherRestaurant() {
        assertThrows(NotFoundException.class, () -> service.get(MENU1_ID, RESTAURANT2_ID));
    }

    @Test
    public void getAll() {
        List<Menu> menus = service.getAll(RESTAURANT1_ID);
        MENU_MATCHER.assertMatch(menus, menu2, menu1);
    }

    @Test
    public void update() {
        Menu updated = getUpdated();
        service.update(updated, RESTAURANT1_ID);
        MENU_MATCHER.assertMatch(service.get(MENU1_ID, RESTAURANT1_ID), getUpdated());
    }

    @Test
    public void updateFromAnotherRestaurant() {
        assertThrows(NotFoundException.class, () -> service.update(menu1, RESTAURANT2_ID));
    }
}