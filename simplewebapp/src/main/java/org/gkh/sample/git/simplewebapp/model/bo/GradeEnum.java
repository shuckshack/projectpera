/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.bo;

/**
 *
 * @author hepgk
 */
public enum GradeEnum {
    
    NONE("N/A"),ONE_STAR("*"),TWO_STAR("**"),THREE_STAR("***"),FOUR_STAR("****"),FIVE_STAR("*****");
    
    private final String gradeString;
    
    private GradeEnum(String gradeString) {
        this.gradeString = gradeString;
    }
    
    @Override
    public String toString() {
        return this.gradeString;
    }
}
