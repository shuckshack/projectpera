/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.factory.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gkh.sample.git.simplewebapp.model.bo.Address;
import org.gkh.sample.git.simplewebapp.model.bo.Restaurant;
import org.gkh.sample.git.simplewebapp.model.factory.Factory;
import org.springframework.stereotype.Component;

/**
 *  Factory to create restaurant objects with default address.
 * @author hepgk
 */
@Component
public class RestaurantWithAddressFactory implements Factory<Restaurant> {

    static final Logger logger = LogManager.getLogger(RestaurantWithAddressFactory.class.getName());
    
    private static int defaultAddCtr = 10;
    
    private Address createDefaultAddress() {
        Address address = new Address();
        address.setBuilding("Building");
        address.setCoord(new String[] {"0","0"});
        address.setStreet(defaultAddCtr++ + " Street");
        address.setZipcode("1000");
        return address;
    }
    
    @Override
    public Restaurant createObject() {
        Address defaultAddress = createDefaultAddress();
        logger.debug("creating blank Restaurant object with default address:" + defaultAddress);
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(defaultAddress);
        return restaurant;
    }
    
}
