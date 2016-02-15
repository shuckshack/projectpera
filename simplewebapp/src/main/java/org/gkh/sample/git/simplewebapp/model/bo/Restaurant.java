/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author hepgk
 */
@Document(collection = "restaurants")
public class Restaurant {
    
    @Id
    private String id;
    
    private Address address;
    private String borough;
    private String cuisine;
    private Grade[] grades;
    private String name;
    private String restaurantId;
    
    @Override
    public String toString() {
        String stringVal;
        if (grades != null && grades.length > 0) {
            Grade grade = grades[grades.length - 1];
            String gradeValue = grade.getGrade().toString();
            double score = grade.getScore();
            stringVal = String.format("Restaurant[id=%s, name=%s, cuisine=%s, street=%s, latestGrade=%s, latestScore=%s]", id, name, cuisine, address.getStreet(), gradeValue, score);
        } else {
            stringVal = String.format("Restaurant[id=%s, name=%s, cuisine=%s, street=%s]", id, name, cuisine, address.getStreet());
        }
        return stringVal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Grade[] getGrades() {
        return grades;
    }

    public void setGrades(Grade[] grades) {
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    
    
}
