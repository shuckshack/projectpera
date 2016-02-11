/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.repository;

import java.util.List;
import org.gkh.sample.git.simplewebapp.model.bo.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author hepgk
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);
}
