/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juanm
 */
public interface OrderDao {
    
    void addOrder(String orderNum, Order order, String date) throws IOException; 
    
    public Order getOneOrder(String date, String orderNum) throws IOException;
    
    public List<Order> getOrdersByDate(String date) throws IOException;
    
//    public NumOfOrder getNumOfOrder() throws FileNotFoundException;
    
    public Order editOrder(String orderNum, String date, Order order) throws IOException;
    
    public Order removeOrder(String orderNum, String date) throws IOException;
    
//    public NumOfOrder updateNumOfOrder(long orderNum, NumOfOrder writeNumOfOrder) throws FileNotFoundException, IOException;
    
}
