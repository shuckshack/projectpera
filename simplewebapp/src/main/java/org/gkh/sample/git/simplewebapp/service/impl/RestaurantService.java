/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gkh.sample.git.simplewebapp.model.bo.Address;
import org.gkh.sample.git.simplewebapp.model.bo.Restaurant;
import org.gkh.sample.git.simplewebapp.model.factory.Factory;
import org.gkh.sample.git.simplewebapp.model.repository.RestaurantRepository;
import org.gkh.sample.git.simplewebapp.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author hepgk
 */
@Component
public class RestaurantService implements Service {

    static final Logger logger = LogManager.getLogger(RestaurantService.class.getName());

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private Factory<Restaurant> restaurantFactory;

    @Override
    public void testRun() {
        logger.debug("testRun()");
//        restaurantRepository.deleteAll();
        List<Restaurant> testRestaurants = createTestData();
        insertTestData(testRestaurants);
        readTestData(testRestaurants);
        deleteTestData(testRestaurants);
        readTestData(testRestaurants);
    }

    public List<Restaurant> createTestData() {
        logger.debug("createTestData()");
        List<Restaurant> testRestaurant = new ArrayList<>();
        Restaurant restaurant = restaurantFactory.createObject();
        restaurant.setName("Dell's");
        restaurant.setCuisine("Filipino");
        testRestaurant.add(restaurant);
        restaurant = restaurantFactory.createObject();
        restaurant.setName("Kafe Krema");
        restaurant.setCuisine("Cafe");
        testRestaurant.add(restaurant);
        return testRestaurant;
    }

    public void insertTestData(List<Restaurant> testRestaurants) {
        logger.debug("insertTestData()");
        testRestaurants.stream().forEach(restaurantRepository::insert);
    }

    public void deleteTestData(List<Restaurant> testRestaurants) {
        logger.debug("deleteTestData()");
        testRestaurants.stream().forEach(restaurantRepository::delete);
    }

    public void readTestData(List<Restaurant> testRestaurants) {
        logger.debug("readTestData()");
        testRestaurants.stream().forEach((o) -> {
            logger.debug(restaurantRepository.findByName(o.getName()));
        });
    }

    public RestaurantRepository getRestaurantRepository() {
        return restaurantRepository;
    }

    public void setRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Factory<Restaurant> getRestaurantFactory() {
        return restaurantFactory;
    }

    public void setRestaurantFactory(Factory<Restaurant> restaurantFactory) {
        this.restaurantFactory = restaurantFactory;
    }

}
