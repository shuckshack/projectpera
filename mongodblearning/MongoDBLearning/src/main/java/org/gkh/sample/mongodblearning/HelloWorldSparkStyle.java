/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import spark.Request;
import spark.Response;
import spark.Spark;

/**
 *
 * @author hepgk
 */
public class HelloWorldSparkStyle {
    
    public static void main(String[] args) {
        Spark.get("/", (Request rqst, Response rspns) -> "Hello World from SPARK!");
    }
    
}
