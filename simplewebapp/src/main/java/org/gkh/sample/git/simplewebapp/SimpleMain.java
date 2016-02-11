/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp;

import org.gkh.sample.git.simplewebapp.service.impl.SimpleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gkh.sample.git.simplewebapp.service.Service;
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
    private Service simpleService;
    
    public static void main(String[] args) {
        logger.debug("Starting main...");
        SpringApplication.run(SimpleMain.class, args);
    }

    @Override
    public void run(String... args) {
        logger.debug("Run...");
        simpleService.testRun();
        
    }

    public Service getSimpleService() {
        return simpleService;
    }

    public void setSimpleService(Service simpleService) {
        this.simpleService = simpleService;
    }
    
    
}
