/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.factory.impl;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gkh.sample.git.simplewebapp.model.bo.Grade;
import org.gkh.sample.git.simplewebapp.model.bo.GradeEnum;
import org.gkh.sample.git.simplewebapp.model.bo.Restaurant;
import org.gkh.sample.git.simplewebapp.model.factory.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(RestaurantWithAddressAndGradeFactory.NAME)
public class RestaurantWithAddressAndGradeFactory implements Factory<Restaurant> {
    
    static final Logger LOG = LogManager.getLogger(RestaurantWithAddressAndGradeFactory.class.getName());
    
    
    public static final String NAME = "RestaurantWithAddressAndGradeFactory";
    
    @Autowired
    @Qualifier(RestaurantWithAddressFactory.NAME)
    private Factory<Restaurant> restaurantFactory;

    @Override
    public Restaurant createObject() {
        Restaurant restaurant = restaurantFactory.createObject();
        Grade grade = createDefaultGrade();
        LOG.debug("creating blank Restaurant object with default grade:" + grade);
        restaurant.setGrades(new Grade[] {grade});
        return restaurant;
    }
    
    private Grade createDefaultGrade() {
        Grade grade = new Grade();
        grade.setDate(new Date());
        grade.setGrade(GradeEnum.NONE);
        grade.setScore(0.0);
        return grade;
    }

    public Factory<Restaurant> getRestaurantFactory() {
        return restaurantFactory;
    }

    public void setRestaurantFactory(Factory<Restaurant> restaurantFactory) {
        this.restaurantFactory = restaurantFactory;
    }
    
}
