/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.repository;

import java.util.List;
import org.gkh.sample.git.simplewebapp.model.bo.Restaurant;
import org.gkh.sample.git.simplewebapp.model.bo.Address;

/**
 *
 * @author hepgk
 */
public interface RestaurantRepository {
    public Restaurant findByName(String name);
    public List<Restaurant> findByAddress(Address address);
}
