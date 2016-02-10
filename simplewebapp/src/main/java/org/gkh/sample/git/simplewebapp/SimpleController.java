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
public class SimpleController {
    
    static final Logger logger = LogManager.getLogger(SimpleController.class.getName());
    
    public void start() {
        logger.debug("Debug!");
        logger.info("Info!");
        logger.error("Error!");
    }
    
}
