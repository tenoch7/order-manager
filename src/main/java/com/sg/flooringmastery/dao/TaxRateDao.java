/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.StateTax;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author juanm
 */
public interface TaxRateDao {
    
    public Map getTaxRate() throws FileNotFoundException;
}
