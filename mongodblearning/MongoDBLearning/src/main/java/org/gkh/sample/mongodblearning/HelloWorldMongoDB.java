/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Dell - User
 */
public class HelloWorldMongoDB {
    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        
        MongoDatabase db = client.getDatabase("test");
    }
}
