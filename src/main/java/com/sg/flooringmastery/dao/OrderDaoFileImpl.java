/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author juanm
 */
public class OrderDaoFileImpl implements OrderDao {

    private Map<String, Object> ordersMap; // Key = orderNum,  Object = Order
    private String filePrefix; //"Orders_"
    private boolean isProduction; //True = Prod,  False = Training
    private FileMapper fileMapper; // Puts the file into a map

    public OrderDaoFileImpl(Map<String, Object> ordersMap, FileMapper fileMapper, String filePrefix, boolean isProduction) {
        this.ordersMap = ordersMap;
        this.fileMapper = fileMapper;
        this.filePrefix = filePrefix;
        this.isProduction = isProduction;
    }


    
    @Override
    public void addOrder(String orderNum, Order order, String date) throws IOException {
        ordersMap.clear();
        ordersMap.put(orderNum, order);
        saveOrders(date, true);
    }

    @Override
    public Order editOrder(String orderNum, String date, Order order) throws IOException {
        Order orderEdited = (Order)ordersMap.replace(orderNum, order);
        saveOrders(date, false);
        return orderEdited;
    }

    @Override
    public Order removeOrder(String orderNum, String date) throws IOException {
        Order orderEdited = (Order)ordersMap.remove(orderNum);
        boolean isAppend = false;
        saveOrders(date, isAppend);
        return orderEdited;
    }

    @Override
    public Order getOneOrder(String date, String orderNum) throws IOException {
        loadOrders(date);
        return (Order)ordersMap.get(orderNum);
    }

    @Override
    public List<Order> getOrdersByDate(String date) throws IOException {
        ordersMap.clear();
        loadOrders(date);
        Collection<Object> objects = ordersMap.values();
        List<Order> ordersList = (List)(new ArrayList<>(objects));
        return ordersList;
    }
    
    void saveOrders(String date, boolean isAppend) throws IOException {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        if (isProduction == true) {
        String fileLocation = filePrefix + date + ".txt";
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(fileLocation, isAppend));
        } catch (IOException ex) {
            throw new IOException("File not found." + ex);
        }
        Collection<Object> objects = ordersMap.values();
        Order currentOrder;
        for (Object currentObject : objects) {
            currentOrder = (Order) currentObject;
            out.println(currentOrder.getOrderNum() + ","
                    + currentOrder.getCustomerName() + ","
                    + currentOrder.getState() + ","
                    + currentOrder.getTaxRate() + ","
                    + currentOrder.getProductType() + ","
                    + currentOrder.getArea() + ","
                    + currentOrder.getMaterialCostPerSquareFoot() + ","
                    + currentOrder.getLaborCostPerSquareFoot() + ","
                    + currentOrder.getTotalMaterialCost() + ","
                    + currentOrder.getTotalLaborCost() + ","
                    + currentOrder.getTotalTax() + ","
                    + currentOrder.getOrderTotal());
        }
        out.flush();
        out.close();
        }
    }
    
    
    void loadOrders(String date) throws FileNotFoundException {
        Function<String[], Object> lineToOrderFunction = (String[] lineValues) -> {
            Order order = new Order(lineValues[0]);
            order.setCustomerName(lineValues[1]);
            order.setState(lineValues[2]);
            order.setTaxRate(new BigDecimal(lineValues[3]));
            order.setProductType(lineValues[4]);
            order.setArea(Float.valueOf(lineValues[5]));
            order.setMaterialCostPerSquareFoot(new BigDecimal(lineValues[6]));
            order.setLaborCostPerSquareFoot(new BigDecimal(lineValues[7]));
            order.setTotalMaterialCost(new BigDecimal(lineValues[8]));
            order.setTotalLaborCost(new BigDecimal(lineValues[9]));
            order.setTotalTax(new BigDecimal(lineValues[10]));
            order.setOrderTotal(new BigDecimal(lineValues[11]));

            return order;
        };
        String fileLocation = filePrefix + date + ".txt";

        this.fileMapper.mapFile(fileLocation, ordersMap, lineToOrderFunction);
    }
    
}
