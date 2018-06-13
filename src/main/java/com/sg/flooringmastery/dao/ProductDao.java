/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 *
 * @author juanm
 */
public interface ProductDao {
    
    public Map getProductCost() throws FileNotFoundException;
}
