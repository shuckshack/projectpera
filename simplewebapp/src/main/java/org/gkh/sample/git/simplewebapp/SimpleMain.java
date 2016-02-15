/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gkh.sample.git.simplewebapp.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author hepgk
 */
@SpringBootApplication
public class SimpleMain implements CommandLineRunner {

    static final Logger logger = LogManager.getLogger(SimpleMain.class.getName());

    @Autowired
    private SimpleService customerService;
    
    @Autowired
    private SimpleService restaurantService;
    
    public static void main(String[] args) {
        logger.debug("Starting main...");
        SpringApplication.run(SimpleMain.class, args);
        logger.debug("Exiting main...");
        System.exit(0);
    }

    @Override
    public void run(String... args) {
        logger.debug("Run...");
        customerService.testRun();
        restaurantService.testRun();
        
    }

    public SimpleService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(SimpleService customerService) {
        this.customerService = customerService;
    }

    public SimpleService getRestaurantService() {
        return restaurantService;
    }

    public void setRestaurantService(SimpleService restaurantService) {
        this.restaurantService = restaurantService;
    }
    
    
}
