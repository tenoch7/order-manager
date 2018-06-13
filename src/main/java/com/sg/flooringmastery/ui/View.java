/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author juanm
 */
public class View {

    UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public String getState(Set stateKeys) {
        boolean isValidState;
        String stateEntry;
        do {
        io.print("Please select a state code: ");
        Iterator<String> iterator = stateKeys.iterator();
        while (iterator.hasNext()) {
            String currentString = iterator.next();
            io.print(currentString);
        }
        stateEntry = io.readString("");
//        String stateEntry = io.readString("Enter state code: \nOhio - OH\nPennsylvania - PA\nMichigan - MI\nIndiana - IN");
        stateEntry = stateEntry.toUpperCase();
        
        isValidState = stateKeys.contains(stateEntry);
        } while (!isValidState);
        return stateEntry;
    }

    public String getProduct(Set productKeys) {
        boolean isValidProduct;
        String productEntry;
        do{
            io.print("Please select a product: ");
            Iterator<String> iterator = productKeys.iterator();
         while (iterator.hasNext()) {
            String currentString = iterator.next();
            io.print(currentString);
        }
        productEntry = io.readString("");
        
//        String productEntry = io.readString("Enter product type: \nCarpet\nLaminate\nTile\nWood");
        productEntry = productEntry.toLowerCase();
        productEntry = productEntry.substring(0, 1).toUpperCase() + productEntry.substring(1);
        isValidProduct = productKeys.contains(productEntry);
        } while (!isValidProduct);
        return productEntry;
    }

    public String getCustomerName() {
        String customerName;
        do {
            customerName = io.readString("Enter customer name (letters only please)");
        } while (customerName.trim().isEmpty() || !customerName.matches("[a-zA-Z]+"));
        customerName = customerName.toLowerCase();
        customerName = customerName.substring(0, 1).toUpperCase() + customerName.substring(1);
        return customerName;
    }

    public float getArea() {
//        float area = io.readFloat("Enter area: ");
//        return area;
        Scanner sc = new Scanner(System.in);
        boolean isNumber;
        float area = 0;

        do {
            System.out.println("Enter area: ");
            if (sc.hasNextFloat()) {
                area = sc.nextFloat();
                isNumber = true;
            } else {
                System.out.println("Invalid area, enter again: ");
                isNumber = false;
                sc.next();
            }
        } while (!(isNumber));
        return area;
    }

    public void printError(String message) {
        io.print(message);
    }

    public void print(String message) {
        io.print(message);
    }

    public int getMenuSelection() {
        io.print("<<Flooring Program>>\n"
                + "1. Display orders\n"
                + "2. Add order\n"
                + "3. Edit order\n"
                + "4. Remove order\n"
                + "5. Display one order\n"
                + "6. Quit");
        Scanner sc = new Scanner(System.in);
        boolean isNumber;
        int menuSelection = 0;

        do {
            System.out.println("Enter a number (1-6) from the menu: ");
            if (sc.hasNextInt()) {
                menuSelection = sc.nextInt();
                isNumber = true;
            } else {
                System.out.println("Invalid entry, enter again: ");
                isNumber = false;
                sc.next();
            }
        } while (!(isNumber) || menuSelection < 1 || menuSelection > 6);
//        int menuSelection = io.readInt("Please enter a number (1-6) from the menu: ", 1, 6);
        return menuSelection;
    }

    public String getDate() {
        Scanner sc = new Scanner(System.in);
        boolean isMonth;
        int dateMonth = 0;

        do {
            System.out.println("Please enter Order date.\nMonth(1-12):");
            if (sc.hasNextInt()) {
                dateMonth = sc.nextInt();
                isMonth = true;
            } else {
                System.out.println("Invalid month, try again.");
                isMonth = false;
                sc.next();
            }
        } while (!(isMonth) || dateMonth < 1 || dateMonth > 12);

        boolean isDay;
        int dateDay = 0;
        do {
            System.out.println("Day(1-31):");
            if (sc.hasNextInt()) {
                dateDay = sc.nextInt();
                isDay = true;
            } else {
                System.out.println("Invalid day, try again.");
                isDay = false;
                sc.next();
            }
        } while (!(isDay) || dateDay < 1 || dateDay > 31);

        boolean isYear;
        int dateYear = 0;
        do {
            System.out.println("Year(yyyy):");
            if (sc.hasNextInt()) {
                dateYear = sc.nextInt();
                isYear = true;
            } else {
                System.out.println("Invalid year, try again.");
                isYear = false;
                sc.next();
            }
        } while (!(isYear) || dateYear < 1950 || dateYear > 2018);

        // adding a zero to the left of the month and days  1 - 9 to follow the date format 05312018
        // and changing int to String
        String month = String.valueOf(dateMonth);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = String.valueOf(dateDay);
        if (day.length() == 1) {
            day = "0" + day;
        }
        String year = String.valueOf(dateYear);

        return month + day + year;
    }

    public String enterOrderNum() {
        return io.readString("Please enter order number");
    }

    public void displayOrder(Order order) {
        if (order != null) {
            System.out.println(order.getOrderNum() + ","
                    + order.getCustomerName() + ","
                    + order.getState() + ","
                    + order.getTaxRate() + ","
                    + order.getProductType() + ","
                    + order.getArea() + ","
                    + order.getMaterialCostPerSquareFoot() + ","
                    + order.getLaborCostPerSquareFoot() + ","
                    + order.getTotalMaterialCost() + ","
                    + order.getTotalLaborCost() + ","
                    + order.getTotalTax() + ","
                    + order.getOrderTotal());
        } else {
            io.print("Order doesn't exist");
        }
//        io.readString("Please hit enter to continue");
    }

    public void displayOrdersByDate(List<Order> orders, String date) {
        if (orders != null) {

            for (Order currentOrder : orders) {
                io.print(String.valueOf(currentOrder.getOrderNum()) + ","
                        + String.valueOf(currentOrder.getCustomerName()) + ","
                        + String.valueOf(currentOrder.getState()) + ","
                        + String.valueOf(currentOrder.getTaxRate()) + ","
                        + String.valueOf(currentOrder.getProductType()) + ","
                        + String.valueOf(currentOrder.getArea()) + ","
                        + String.valueOf(currentOrder.getMaterialCostPerSquareFoot()) + ","
                        + String.valueOf(currentOrder.getLaborCostPerSquareFoot()) + ","
                        + String.valueOf(currentOrder.getTotalMaterialCost()) + ","
                        + String.valueOf(currentOrder.getTotalLaborCost()) + ","
                        + String.valueOf(currentOrder.getTotalTax()) + ","
                        + String.valueOf(currentOrder.getOrderTotal()));
            }

        } else {
            io.print("Order doesn't exist");
        }
//        io.readString("Please hit enter to continue");
    }

    public Order editOrder(Order order, Set stateKeys, Set productKeys) {

        //Edit NAME
        boolean keepGoingName;
        do {
            String keepOrChangeName = io.readString("Do you want to edit Name (" + order.getCustomerName() + ")? (Y)es - (N)o");
            if (keepOrChangeName.equals("n") || keepOrChangeName.equals("N")) {
                io.print("Field not changed");
                keepGoingName = false;
            } else if (keepOrChangeName.equals("y") || keepOrChangeName.equals("Y")) {
                String customerName = getCustomerName();
                order.setCustomerName(customerName);
                keepGoingName = false;
            } else {
                io.print("Invalid entry, try again.");
                keepGoingName = true;
            }
        } while (keepGoingName == true);

        //Edit STATE
        boolean keepGoingState;
        boolean kpGS;
        do {
            String keepOrChangeState = io.readString("Do you want to edit State (" + order.getState() + ")? (Y)es - (N)o");
            if (keepOrChangeState.equals("n") || keepOrChangeState.equals("N")) {
                io.print("Field not changed");
                keepGoingState = false;
            } else if (keepOrChangeState.equals("y") || keepOrChangeState.equals("Y")) {
//                do {
                    String stateCode = getState(stateKeys);
                    stateCode = stateCode.trim();
//                    if (stateCode.equals("OH") || stateCode.equals("PA") || stateCode.equals("MI") || stateCode.equals("IN")) {
                        order.setState(stateCode);
//                        kpGS = false;
//                    } else {
//                        kpGS = true;
//                    }
//                } while (kpGS == true);
                keepGoingState = false;
            } else {
                io.print("Invalid entry, try again.");
                keepGoingState = true;
            }
        } while (keepGoingState == true);

        //Edit PRODUCT
        boolean keepGoingProduct;
        boolean kpGP;
        do {
            String keepOrChangeProduct = io.readString("Do you want to edit product (" + order.getProductType() + ")? (Y)es - (N)o");
            if (keepOrChangeProduct.equals("n") || keepOrChangeProduct.equals("N")) {
                io.print("Field not changed");
                keepGoingProduct = false;
            } else if (keepOrChangeProduct.equals("y") || keepOrChangeProduct.equals("Y")) {
//                do {
                    String productType = getProduct(productKeys);
                    productType = productType.trim();
//                    if (productType.equals("Carpet") || productType.equals("Laminate") || productType.equals("Tile") || productType.equals("Wood")) {
                        order.setProductType(productType);
//                        kpGP = false;
//                    } else {
//                        kpGP = true;
//                    }
//                } while (kpGP == true);
                keepGoingProduct = false;
            } else {
                io.print("Invalid entry, try again.");
                keepGoingProduct = true;
            }
        } while (keepGoingProduct == true);

        //Edit AREA
        boolean keepGoingArea;
        do {
            String keepOrChangeArea = io.readString("Do you want to edit area (" + order.getArea() + ")? (Y)es - (N)o");
            if (keepOrChangeArea.equals("n") || keepOrChangeArea.equals("N")) {
                io.print("Field not changed");
                keepGoingArea = false;
            } else if (keepOrChangeArea.equals("y") || keepOrChangeArea.equals("Y")) {
                float area = getArea();
                order.setArea(area);
                keepGoingArea = false;
            } else {
                io.print("Invalid entry, try again.");
                keepGoingArea = true;
            }
        } while (keepGoingArea == true);

        return order;
    }

    public String requestConfirmation() {
        boolean keepAsking;
        String yesOrNo;
        do {
            yesOrNo = io.readString("Are you sure you want to delete the order? (Y)es - (N)o");
            if (yesOrNo.equals("y") || yesOrNo.equals("Y") || yesOrNo.equals("n") || yesOrNo.equals("N")) {
                yesOrNo = yesOrNo.toLowerCase().trim();
                keepAsking = false;
            } else {
                keepAsking = true;
            }
        } while (keepAsking == true);
        return yesOrNo;
    }

    public boolean commitChanges(String orderNum, String customerName, String stateEntry, BigDecimal taxRate, String productEntry,
            BigDecimal materialCostPerSquareFoot, BigDecimal laborCostPerSquareFoot, float area, BigDecimal totalMaterialCost, BigDecimal totalLaborCost,
            BigDecimal totalTax, BigDecimal orderTotal) {

        io.print(
                "Order#: " + orderNum
                + "\nCustomer name: " + customerName
                + "\nState: " + stateEntry
                + "\nTax Rate: " + taxRate
                + "\nProduct: " + productEntry
                + "\nMaterial cost: $" + materialCostPerSquareFoot + " Labor cost: $" + laborCostPerSquareFoot
                + "\nArea: " + area + "sqft"
                + "\nTotal cost of Material: $" + totalMaterialCost
                + "\nTotal cost of Labor: $" + totalLaborCost
                + "\nTotal Taxes: $" + totalTax
                + "\nOrder TOTAL: $" + orderTotal);

        String yesNo;
        do {
            yesNo = io.readString("Do you want to commit the order? (Y)es - (N)o");
            yesNo = yesNo.trim().toLowerCase();
        } while (!yesNo.equals("y") && !yesNo.equals("n"));

        boolean commit;
        if (yesNo.equals("y")) {
            commit = true;
        } else {
            commit = false;
        }
        return commit;
    }
}
