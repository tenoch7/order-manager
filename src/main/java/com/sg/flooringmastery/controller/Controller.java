/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.model.Order;
import com.sg.flooringmastery.model.ProductDetail;
import com.sg.flooringmastery.service.InvalidProductNameException;
import com.sg.flooringmastery.service.ServiceLayer;
import com.sg.flooringmastery.service.InvalidStateException;
import com.sg.flooringmastery.service.OrderNotFoundException;
import com.sg.flooringmastery.ui.View;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 *
 * @author juanm
 */
public class Controller {

    ServiceLayer service;
    View view;

    public Controller(ServiceLayer service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        while (keepGoing) {
            menuSelection = view.getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayAllOrdersByDate();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    displayOneOrder();
                    break;
                case 6:
                    keepGoing = false;
                    view.print("End of session.");
                    break;
                default:
                    view.printError("Please enter a number between 1 - 6");
                    break;
            }
        }

    }

    private void displayOneOrder() {
        String orderDate = view.getDate();
        String orderNum = view.enterOrderNum();

        Order orderRequested;
        try {
            orderRequested = service.getOneOrder(orderDate, orderNum);

            view.displayOrder(orderRequested);
        } catch (IOException ex) {
            view.printError("File not found" + ex);
        }
    }

    private void displayAllOrdersByDate() {
        String orderDate = view.getDate();

        List<Order> orderRequested;
        try {
            orderRequested = service.getOrdersByDate(orderDate);
            view.displayOrdersByDate(orderRequested, orderDate);
        } catch (IOException ex) {
            view.printError("No orders stored on that date. " + ex);
        }
    }

    private void removeOrder() {
        String orderDate = view.getDate();
        String orderNum = view.enterOrderNum();

        Order orderRequested;
        try {
            orderRequested = service.getOneOrder(orderDate, orderNum);
            if (orderRequested != null) {
                view.displayOrder(orderRequested);
                String confirmation = view.requestConfirmation();
                if (confirmation.equals("y")) {
                    service.removeOrder(orderRequested.getOrderNum(), orderDate);
                    view.print("Order#" + orderRequested.getOrderNum() + " successfully removed!");
                } else {
                    view.print("Order NOT removed.");
                }
            } else {
                view.printError("No match date/order# found");
            }
        } catch (IOException | OrderNotFoundException ex) {
            view.printError("File not found" + ex);
        }
    }

    private void editOrder() {
        //Get date and orderNumber to retreive Order
        String orderDate = view.getDate();
        String orderNum = view.enterOrderNum();

        Order orderRequested;
        try {
            orderRequested = service.getOneOrder(orderDate, orderNum);
        } catch (IOException ex) {
            view.printError("Order/file not found " + ex);
            return;
        }
        
        Set stateKeys;
        Set productKeys;
            try {
            stateKeys = service.getStateCodes();
            productKeys = service.getProductNames();
            } catch(FileNotFoundException | InvalidStateException ex) {
                view.printError("File not found" + ex);
                return;
            }
        
        Order orderEdited = view.editOrder(orderRequested, stateKeys, productKeys);

        //Updating taxRate
        try {
            BigDecimal taxRateUpdated = service.getTaxRate(orderEdited.getState());
            orderEdited.setTaxRate(taxRateUpdated);
        } catch (FileNotFoundException | InvalidStateException ex) {
            view.printError("State not found" + ex);
        }

        //Updating material and labor costs
        try {
            ProductDetail productDetail = service.getProductDetail(orderEdited.getProductType());
            BigDecimal materialCostPerSquareFoot = productDetail.getMaterialCostPerSquareFoot();
            BigDecimal laborCostPerSquareFoot = productDetail.getLaborCostPerSquareFoot();

            orderEdited.setMaterialCostPerSquareFoot(materialCostPerSquareFoot);
            orderEdited.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

            BigDecimal totalMaterialCostUpdated = service.getTotalMaterialCost(materialCostPerSquareFoot, orderEdited.getArea());
            BigDecimal totalLaborCostUpdated = service.getTotalLaborCost(laborCostPerSquareFoot, orderEdited.getArea());

            orderEdited.setTotalMaterialCost(totalMaterialCostUpdated);
            orderEdited.setTotalLaborCost(totalLaborCostUpdated);

        } catch (FileNotFoundException | InvalidProductNameException ex) {
            view.printError("Error retreiving product costs" + ex);
        }

        BigDecimal totalTaxUpdated = service.getTotaltax(orderEdited.getTotalMaterialCost(), orderEdited.getTotalLaborCost(), orderEdited.getTaxRate());
        orderEdited.setTotalTax(totalTaxUpdated);

        BigDecimal orderTotalUpdated = service.getOrderTotal(orderEdited.getTotalMaterialCost(), orderEdited.getTotalLaborCost(), orderEdited.getTotalTax());
        orderEdited.setOrderTotal(orderTotalUpdated);

        try {
            service.editOrder(orderNum, orderDate, orderEdited);
        } catch (IOException ex) {
            view.printError("Error editing order" + ex);
        }
    }

    private void addOrder() {

        //Order number is generated with UUID
        String orderNum = service.generateOrderNum();

        //User enters customer name
        String customerName = view.getCustomerName();

        //User enters state and the program gains access to tax rate 
        String stateEntry;
        BigDecimal taxRate = null;
        do {
            Set stateKeys;
            try {
            stateKeys = service.getStateCodes();
            } catch(FileNotFoundException | InvalidStateException ex) {
                view.printError("File not found" + ex);
                return;
            }
                stateEntry = view.getState(stateKeys);
            try {
                taxRate = service.getTaxRate(stateEntry);
                if (taxRate == null) {
                    view.printError("=== Invalid state, please enter again ===");
                }

            } catch (FileNotFoundException | InvalidStateException ex) {
                view.printError("File not found." + ex.getMessage());
            }
        } while (taxRate == null);

        //User enters product type and the program gains access to material and labor costs 
        ProductDetail productDetail = null;
        String productEntry;
        Set productKeys;
        try{
        productKeys = service.getProductNames();
        } catch (FileNotFoundException | InvalidStateException ex) {
            view.printError("File not found" + ex);
            return;
        }
        do {
            productEntry = view.getProduct(productKeys);
            try {
                productDetail = service.getProductDetail(productEntry);
                if (productDetail == null) {
                    view.printError("=== Invalid product, please enter again ===");
                }
            } catch (FileNotFoundException | InvalidProductNameException ex) {
                view.printError("File not found //" + ex.getMessage());
            }
        } while (productDetail == null);
        BigDecimal materialCostPerSquareFoot = productDetail.getMaterialCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = productDetail.getLaborCostPerSquareFoot();

        //User enters area to calculate costs
        float area = view.getArea();

        //Program uses AREA and costsPerSquareFoot to calculate TOTAL cost of material, labor and tax
        BigDecimal totalMaterialCost = service.getTotalMaterialCost(materialCostPerSquareFoot, area);
        BigDecimal totalLaborCost = service.getTotalLaborCost(laborCostPerSquareFoot, area);
        BigDecimal totalTax = service.getTotaltax(totalMaterialCost, totalLaborCost, taxRate);
        //Progams adds all subTotals to get the orderTotal
        BigDecimal orderTotal = service.getOrderTotal(totalMaterialCost, totalLaborCost, totalTax);

        boolean commit = view.commitChanges(orderNum, customerName, stateEntry, taxRate, productEntry, materialCostPerSquareFoot, laborCostPerSquareFoot, area, totalMaterialCost, totalLaborCost, totalTax, orderTotal);

        if (commit) {

            try {
                //All fields of Order are sent to services to createOrder
                service.createOrder(orderNum, customerName, stateEntry, taxRate, productEntry, area, materialCostPerSquareFoot,
                        laborCostPerSquareFoot, totalMaterialCost, totalLaborCost, totalTax, orderTotal);
            } catch (IOException ex) {
                System.out.println("Error " + ex);
            }
        } else {
            view.print("Order NOT saved!");
        }

    }

}
