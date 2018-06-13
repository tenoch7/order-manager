/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author juanm
 */
public class Order {
    private String orderNum;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private float area;
    private BigDecimal materialCostPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal totalMaterialCost;
    private BigDecimal totalLaborCost;
    private BigDecimal totalTax;
    private BigDecimal orderTotal;

    public Order(String orderNum) {
        this.orderNum = orderNum;
    }

    
    
    public Order(String orderNum, String customerName, String state, BigDecimal taxRate, String productType, float area, BigDecimal materialCostPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal totalMaterialCost, BigDecimal totalLaborCost, BigDecimal totalTax, BigDecimal orderTotal) {
        this.orderNum = orderNum;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.materialCostPerSquareFoot = materialCostPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.totalMaterialCost = totalMaterialCost;
        this.totalLaborCost = totalLaborCost;
        this.totalTax = totalTax;
        this.orderTotal = orderTotal;
    }

//    public Order(long orderNum) {
//        this.orderNum = orderNum;
//    }
//    
    

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    
    
    public String getOrderNum() {
        return orderNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public BigDecimal getMaterialCostPerSquareFoot() {
        return materialCostPerSquareFoot;
    }

    public void setMaterialCostPerSquareFoot(BigDecimal materialCostPerSquareFoot) {
        this.materialCostPerSquareFoot = materialCostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalLaborCost() {
        return totalLaborCost;
    }

    public void setTotalLaborCost(BigDecimal totalLaborCost) {
        this.totalLaborCost = totalLaborCost;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }
    
    
    
    
}
