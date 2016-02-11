/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.repository;

import java.util.List;
import org.gkh.sample.git.simplewebapp.model.bo.Restaurant;
import org.gkh.sample.git.simplewebapp.model.bo.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author hepgk
 */
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    public Restaurant findByName(String name);
    public List<Restaurant> findByAddress(Address address);
}
