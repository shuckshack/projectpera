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
public class Address {
    
    private String street;
    private String zipcode;
    private String building;
    private String[] coord;
    
    @Override
    public String toString() {
        return String.format("Address[building=%s, street=%s, zipcode=%s]", building, street, zipcode);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String[] getCoord() {
        return coord;
    }

    public void setCoord(String[] coord) {
        this.coord = coord;
    }
    
    
}
