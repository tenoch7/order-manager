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
public class ProductDetail {
    private final BigDecimal materialCostPerSquareFoot;
    private final BigDecimal laborCostPerSquareFoot;

    public ProductDetail(BigDecimal materialCostPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.materialCostPerSquareFoot = materialCostPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }


    public BigDecimal getMaterialCostPerSquareFoot() {
        return materialCostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }
    
    
    
}
