/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh;

/**
 *
 * @author hepgk
 */
public class test {

    public static void main(String[] args) {
        System.out.println(String.format("%7s", "abc").replace(' ', '0'));
        String test = " af.awb_move_seq = 0 and "
                + "exists (select 1 from awb a "
                + "where af.awb_seq = a.seq "
                + "and a.house_flag != 'H'%1$s) ";
        System.out.println(String.format(test, ""));
        System.out.println(String.format(test, " and a.seq between 78439 and 78453"));
                
    }
}
