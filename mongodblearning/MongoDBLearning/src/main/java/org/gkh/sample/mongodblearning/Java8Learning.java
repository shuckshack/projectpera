/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author hepgk
 */
public class Java8Learning {
    
    public static void main(String[] args) {
        List<String> numbers = new ArrayList<>();
        numbers.add("4");
        numbers.add("7");
        numbers.set(1,"5");
        numbers.add("8");
        numbers.remove(0);
        for (Iterator<String> it = numbers.iterator(); it.hasNext();) {
            String number = it.next();
            System.err.println(number);
        }
    }
    
    
    
}
