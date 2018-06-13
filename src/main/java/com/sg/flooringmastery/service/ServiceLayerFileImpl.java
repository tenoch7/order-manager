/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxRateDao;
import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.ProductDetail;
import com.sg.flooringmastery.model.StateTax;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author juanm
 */
public class ServiceLayerFileImpl implements ServiceLayer {

    OrderDao orderDao;
    TaxRateDao taxRateDao;
    ProductDao productDao;

    public ServiceLayerFileImpl(OrderDao orderDao, TaxRateDao taxRateDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.taxRateDao = taxRateDao;
        this.productDao = productDao;
    }

    @Override
    public BigDecimal getTaxRate(String stateCode) throws FileNotFoundException, InvalidStateException {
        Map<String, Object> states = taxRateDao.getTaxRate();

        StateTax stateTax = (StateTax) states.get(stateCode);
        return stateTax.getTaxRate();
//        return taxRateDao.getTaxRate();
    }

    @Override
    public Set<String> getStateCodes() throws FileNotFoundException, InvalidStateException {
        Map<String, Object> states = taxRateDao.getTaxRate();
        Set<String> stateKeys = states.keySet();
//        for (String currentKey : stateKeys) {
//            String currentState = states.get(currentKey).toString();
//        }
        return stateKeys;
    }

    @Override
    public ProductDetail getProductDetail(String productType) throws FileNotFoundException, InvalidProductNameException {
        Map<String, Object> product = productDao.getProductCost();
        ProductDetail productDetail = (ProductDetail) product.get(productType);

//        ProductDetail productDetail = productDao.getProductCost();
        return productDetail;
    }

    @Override
    public Set<String> getProductNames() throws FileNotFoundException, InvalidStateException {
        Map<String, Object> product = productDao.getProductCost();
        Set<String> productKeys = product.keySet();
        return productKeys;
    }

    @Override
    public String generateOrderNum() {
        long longOrderNum = 0;
        do {
            longOrderNum = UUID.randomUUID().getMostSignificantBits();
        } while (longOrderNum < 1);
        String orderNum = String.valueOf(longOrderNum);
        return orderNum;

    }

    @Override
    public BigDecimal getTotalMaterialCost(BigDecimal materialCostPerSquareFoot, float area) {
        BigDecimal totalMaterialCost = (materialCostPerSquareFoot.multiply(BigDecimal.valueOf(area))).setScale(2, RoundingMode.HALF_UP);
        return totalMaterialCost;
    }

    @Override
    public BigDecimal getTotalLaborCost(BigDecimal laborCostPerSquareFoot, float area) {
        BigDecimal totalLaborCost = (laborCostPerSquareFoot.multiply(BigDecimal.valueOf(area))).setScale(2, RoundingMode.HALF_UP);
        return totalLaborCost;
    }

    @Override
    public BigDecimal getTotaltax(BigDecimal totalMaterialCost, BigDecimal totalLaborCost, BigDecimal taxRate) {
        BigDecimal totalTax = (((totalMaterialCost.add(totalLaborCost)).multiply(taxRate))
                .divide(BigDecimal.valueOf(100))).setScale(2, RoundingMode.HALF_UP);
        return totalTax;
    }

    @Override
    public BigDecimal getOrderTotal(BigDecimal totalMaterialCost, BigDecimal totalLaborCost, BigDecimal totalTax) {
        BigDecimal orderTotal = totalMaterialCost.add(totalLaborCost).add(totalTax);
        return orderTotal;
    }

    @Override
    public void createOrder(String orderNum, String customerName, String state, BigDecimal taxRate, String productType, float area,
            BigDecimal materialCostPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal totalMaterialCost,
            BigDecimal totalLaborCost, BigDecimal totalTax, BigDecimal orderTotal) throws IOException {

        Order order = new Order(orderNum, customerName, state, taxRate, productType, area, materialCostPerSquareFoot,
                laborCostPerSquareFoot, totalMaterialCost, totalLaborCost, totalTax, orderTotal);

        order.setOrderNum(orderNum);
        order.setCustomerName(customerName);
        order.setState(state);
        order.setTaxRate(taxRate);
        order.setProductType(productType);
        order.setArea(area);
        order.setMaterialCostPerSquareFoot(materialCostPerSquareFoot);
        order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        order.setTotalMaterialCost(totalMaterialCost);
        order.setTotalLaborCost(totalLaborCost);
        order.setTotalTax(totalTax);
        order.setOrderTotal(orderTotal);
        LocalDate ld = LocalDate.now();
        String dateToday = ld.format(DateTimeFormatter.ofPattern("MMddyyy"));
        orderDao.addOrder(orderNum, order, dateToday);
    }

    @Override
    public List<Order> getOrdersByDate(String date) throws FileNotFoundException, IOException {
        return orderDao.getOrdersByDate(date);
    }

    @Override
    public Order getOneOrder(String date, String orderNum) throws FileNotFoundException, IOException {
        Order order = orderDao.getOneOrder(date, orderNum);
        if (order == null) {
            throw new FileNotFoundException("Order/File not found");
        }
        return order;
    }

    @Override
    public void editOrder(String orderNum, String date, Order order) throws IOException {
        orderDao.editOrder(orderNum, date, order);
    }

    @Override
    public void removeOrder(String orderNum, String date) throws IOException, OrderNotFoundException {
        Order order = orderDao.removeOrder(orderNum, date);
        if (order == null) {
            throw new OrderNotFoundException("Order doesn't exist");
        }
    }

}
