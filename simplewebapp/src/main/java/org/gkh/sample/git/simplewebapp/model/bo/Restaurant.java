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
@Document(collection = "restuarants")
public class Restaurant {
    
    @Id
    private String id;
    
    private Address address;
}
