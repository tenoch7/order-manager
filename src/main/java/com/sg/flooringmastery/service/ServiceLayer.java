/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.ProductDetail;
import com.sg.flooringmastery.model.StateTax;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author juanm
 */
public interface ServiceLayer {
   
    public BigDecimal getTaxRate(String stateCode) throws FileNotFoundException, InvalidStateException;
    
    public Set<String> getStateCodes() throws FileNotFoundException, InvalidStateException;
    
    public Set<String> getProductNames() throws FileNotFoundException, InvalidStateException;
    
    public ProductDetail getProductDetail(String productType) throws FileNotFoundException, InvalidProductNameException;
    
    public String generateOrderNum();
    
//    public void updateOrderNum(long orderNum) throws FileNotFoundException;
    
    public BigDecimal getTotalMaterialCost(BigDecimal materialCostPerSquareFoot, float area);
    
    public BigDecimal getTotalLaborCost(BigDecimal laborCostPerSquareFoot, float area);
    
    public BigDecimal getTotaltax(BigDecimal totalMaterialCost, BigDecimal totalLaborCost, BigDecimal taxRate);
    
    public BigDecimal getOrderTotal(BigDecimal totalMaterialCost, BigDecimal totalLaborCost, BigDecimal totalTax);
    
    public void createOrder(String orderNum, String customerName, String state, BigDecimal taxRate, String productType, float area, BigDecimal materialCostPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal totalMaterialCost, BigDecimal totalLaborCost, BigDecimal totalTax, BigDecimal total) throws IOException;
    
    public Order getOneOrder(String date, String orderNum) throws FileNotFoundException, IOException;
    
    public List<Order> getOrdersByDate(String date) throws FileNotFoundException, IOException;    
    
    public void editOrder(String orderNum, String date, Order order) throws IOException;
    
    public void removeOrder(String orderNum, String date) throws IOException, OrderNotFoundException;
}
