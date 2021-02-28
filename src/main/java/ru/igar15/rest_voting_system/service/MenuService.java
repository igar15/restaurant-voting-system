package ru.igar15.rest_voting_system.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.igar15.rest_voting_system.model.Menu;
import ru.igar15.rest_voting_system.model.Restaurant;
import ru.igar15.rest_voting_system.repository.MenuRepository;
import ru.igar15.rest_voting_system.repository.RestaurantRepository;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    public void delete(int id, int restaurantId) {
        Menu menu = get(id, restaurantId);
        menuRepository.delete(menu);
    }

    public Menu get(int id, int restaurantId) {
        return menuRepository.findByIdAndRestaurant_Id(id, restaurantId).orElseThrow(() -> new NotFoundException("Not found menu with id=" + id));
    }

    public List<Menu> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurant_IdOrderByDateDesc(restaurantId);
    }

    public void update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        get(menu.id(), restaurantId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
    }
}