/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.model.factory;

/**
 *
 * @author hepgk
 */
public interface Factory<T extends Object> {
    
    public <S extends T> S createObject();
    
}
