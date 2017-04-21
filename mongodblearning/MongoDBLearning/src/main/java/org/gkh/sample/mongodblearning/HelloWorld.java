/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hepgk
 */
public class HelloWorld {
    
    public static void main(String[] args) {
        Map<String, Integer> testMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Integer testInteger = testMap.get(String.valueOf(i));
                if (testInteger == null) {
                    testInteger = Integer.valueOf(0);
                }
                testMap.put(String.valueOf(i),++testInteger);
            }
        }
        
        testMap.forEach((key, value) -> {
                System.out.println("key: " + key);
                System.out.println("value: " + value);
            });
    }
    
}
