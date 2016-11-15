/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author hepgk
 */
public class Java8Learning {
    
    public static void main(String[] args) {
        Set<Integer> numSet1 = new HashSet<>();
        List<Integer> numList1 = new ArrayList<>();
        
        numList1.add(1);
        numList1.add(2);
        numList1.add(3);
        numList1.add(2);
        numList1.add(1);
        
        numSet1.addAll(numList1);
        
        numSet1.forEach((num) -> System.out.println(num));
        System.out.println("======");
        numList1.forEach((num) -> System.out.println(num));
    }
    
    
    
}
