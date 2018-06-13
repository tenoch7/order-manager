/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model;

import java.math.BigDecimal;

/**
 *
 * @author juanm
 */
public class StateTax {
//    private final String stateCode;
    private final BigDecimal taxRate;

    public StateTax(/*String stateCode, */BigDecimal taxRate) {
//        this.stateCode = stateCode;
        this.taxRate = taxRate;
    }

//    public String getStateCode() {
//        return stateCode;
//    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
    
    
}
