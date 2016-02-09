/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author hepgk
 */
public class SimpleMain {
    
    static final Logger logger = LogManager.getLogger(SimpleMain.class.getName());
    
    public static void main(String[] args) {
        System.err.println("Starting...");
        new SimpleMain().start();
    }
    
    public void start() {
        logger.entry();
        logger.debug("debug");
        logger.info("info");
        logger.error("error");
    }
}
