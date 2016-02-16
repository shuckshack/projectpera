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
import org.gkh.sample.git.simplewebapp.model.bo.Restaurant;
import org.gkh.sample.git.simplewebapp.model.factory.Factory;
import org.gkh.sample.git.simplewebapp.model.factory.impl.RestaurantWithAddressAndGradeFactory;
import org.gkh.sample.git.simplewebapp.model.factory.impl.RestaurantWithAddressFactory;
import org.gkh.sample.git.simplewebapp.model.repository.RestaurantRepository;
import org.gkh.sample.git.simplewebapp.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hepgk
 */
@Service
public class RestaurantService implements SimpleService {

    static final Logger LOG = LogManager.getLogger(RestaurantService.class.getName());

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    @Qualifier(RestaurantWithAddressFactory.NAME)
    private Factory<Restaurant> restaurantFactory;
    
    @Autowired
    @Qualifier(RestaurantWithAddressAndGradeFactory.NAME)
    private Factory<Restaurant> restaurantWithGradeFactory;

    @Override
    public void testRun() {
        LOG.debug("testRun()");
//        restaurantRepository.deleteAll();
        List<Restaurant> testRestaurants = createTestData();
        insertTestData(testRestaurants);
        readTestData(testRestaurants);
        deleteTestData(testRestaurants);
        readTestData(testRestaurants);
    }

    public List<Restaurant> createTestData() {
        LOG.debug("createTestData()");
        List<Restaurant> testRestaurant = new ArrayList<>();
        Restaurant restaurant = restaurantFactory.createObject();
        restaurant.setName("Dell's");
        restaurant.setCuisine("Filipino");
        testRestaurant.add(restaurant);
        restaurant = restaurantWithGradeFactory.createObject();
        restaurant.setName("Kafe Krema");
        restaurant.setCuisine("Cafe");
        testRestaurant.add(restaurant);
        return testRestaurant;
    }

    public void insertTestData(List<Restaurant> testRestaurants) {
        LOG.debug("insertTestData()");
        testRestaurants.stream().forEach(restaurantRepository::insert);
    }

    public void deleteTestData(List<Restaurant> testRestaurants) {
        LOG.debug("deleteTestData()");
        testRestaurants.stream().forEach(restaurantRepository::delete);
    }

    public void readTestData(List<Restaurant> testRestaurants) {
        LOG.debug("readTestData()");
        testRestaurants.stream().forEach((o) -> {
            Restaurant queriedRestaurant = restaurantRepository.findByName(o.getName());
            if (queriedRestaurant != null) {
                LOG.debug(queriedRestaurant);
            } else {
                LOG.debug("Restaurant with name: " + o.getName() + " not found.");
            }
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

    public Factory<Restaurant> getRestaurantWithGradeFactory() {
        return restaurantWithGradeFactory;
    }

    public void setRestaurantWithGradeFactory(Factory<Restaurant> restaurantWithGradeFactory) {
        this.restaurantWithGradeFactory = restaurantWithGradeFactory;
    }

    
}
