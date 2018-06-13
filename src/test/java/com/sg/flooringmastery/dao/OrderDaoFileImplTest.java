/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.Order;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 *
 * @author juanm
 */
public class OrderDaoFileImplTest {

    private static final String TEST_FILENAME = "testPrefix_testDate.txt";
    private static final String TEST_PREFIX = "testPrefix_";
    private static final String TEST_DATE = "testDate";

    public OrderDaoFileImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        File testOrders = new File(TEST_FILENAME);
        if (testOrders.exists()) {
            testOrders.delete();
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class OrderDaoFileImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        //Create file
        writeValues(new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"}, false);
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        orderDaoFileImpl.addOrder("2", new Order("2", "nameTest2", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN), TEST_DATE);
        String[] lineOneValues = new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"};
        String[] lineTwoValues = new String[]{"2", "nameTest2", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"};
        String[][] fileContents = new String[2][12];
        fileContents[0] = lineOneValues;
        fileContents[1] = lineTwoValues;
        assertFileContents(fileContents);
    }

    /**
     * Test of editOrder method, of class OrderDaoFileImpl.
     */
    @Test
    public void testEditOrder() throws Exception {
        //Create file
//        writeValues(new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"}, false);
        Map<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("2", new Order("2", "nameTest", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN));
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        Order actualOrder = orderDaoFileImpl.editOrder("2", TEST_DATE, new Order("2", "nameTest2", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN));
        //Assert result
        Assert.assertEquals("2", actualOrder.getOrderNum());
        Assert.assertEquals("nameTest", actualOrder.getCustomerName());
        Assert.assertEquals("stateTest", actualOrder.getState());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTaxRate());
        Assert.assertEquals("productTypeTest", actualOrder.getProductType());
        Assert.assertEquals(1.0f, actualOrder.getArea(), 0f);
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getMaterialCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getLaborCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getTotalMaterialCost());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTotalLaborCost());
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getTotalTax());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getOrderTotal());
    }

    /**
     * Test of removeOrder method, of class OrderDaoFileImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        //Create file
        Map<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("1", new Order("1", "nameTest", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN));
        ordersMap.put("2", new Order("2", "nameTest", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN));
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        Order actualOrder = orderDaoFileImpl.removeOrder("2", TEST_DATE);
        String[] lineOneValues = new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"};
        String[][] fileContents = new String[1][12];
        fileContents[0] = lineOneValues;
        assertFileContents(fileContents);
        //Assert result
        Assert.assertEquals("2", actualOrder.getOrderNum());
        Assert.assertEquals("nameTest", actualOrder.getCustomerName());
        Assert.assertEquals("stateTest", actualOrder.getState());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTaxRate());
        Assert.assertEquals("productTypeTest", actualOrder.getProductType());
        Assert.assertEquals(1.0f, actualOrder.getArea(), 0f);
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getMaterialCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getLaborCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getTotalMaterialCost());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTotalLaborCost());
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getTotalTax());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getOrderTotal());
    }

    /**
     * Test of getOneOrder method, of class OrderDaoFileImpl.
     */
    @Test
    public void testGetOneOrder() throws Exception {
        //Create file
        writeValues(new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"}, false);
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        Order actualOrder = orderDaoFileImpl.getOneOrder(TEST_DATE, "1");
        //Verify result
        Assert.assertEquals("1", actualOrder.getOrderNum());
        Assert.assertEquals("nameTest", actualOrder.getCustomerName());
        Assert.assertEquals("stateTest", actualOrder.getState());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTaxRate());
        Assert.assertEquals("productTypeTest", actualOrder.getProductType());
        Assert.assertEquals(1.0f, actualOrder.getArea(), 0f);
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getMaterialCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getLaborCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getTotalMaterialCost());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTotalLaborCost());
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getTotalTax());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getOrderTotal());
    }

    /**
     * Test of getOrdersByDate method, of class OrderDaoFileImpl.
     */
    @Test
    public void testGetOrdersByDate_FileFound() throws Exception {
        //Create file
        writeValues(new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"}, false);
        
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        List<Order> actualOrders = orderDaoFileImpl.getOrdersByDate(TEST_DATE);
        //Verify result
        Assert.assertEquals(1, actualOrders.size());
        final Order testOrder = new Order("1", "nameTest", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
        final Order actualOrder = actualOrders.get(0);
        Assert.assertEquals("1", actualOrder.getOrderNum());
        Assert.assertEquals("nameTest", actualOrder.getCustomerName());
        Assert.assertEquals("stateTest", actualOrder.getState());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTaxRate());
        Assert.assertEquals("productTypeTest", actualOrder.getProductType());
        Assert.assertEquals(1.0f, actualOrder.getArea(), 0f);
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getMaterialCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getLaborCostPerSquareFoot());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getTotalMaterialCost());
        Assert.assertEquals(BigDecimal.ZERO, actualOrder.getTotalLaborCost());
        Assert.assertEquals(BigDecimal.ONE, actualOrder.getTotalTax());
        Assert.assertEquals(BigDecimal.TEN, actualOrder.getOrderTotal());
    }

    /**
     * Test of getOrdersByDate method, of class OrderDaoFileImpl.
     */
    @Test(expected = FileNotFoundException.class)
    public void testGetOrdersByDate_FileNotFound() throws Exception {
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested throws exception
        orderDaoFileImpl.getOrdersByDate(TEST_DATE);
    }

    /**
     * Test of saveOrders method, of class OrderDaoFileImpl.
     */
    @Test
    public void testSaveOrders_isProduction_isAppend() throws Exception {
        //Test Setup
        Map<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("1", new Order("1", "nameTest", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN));
        boolean isProduction = true;
        FileMapper fileMapper = Mockito.mock(FileMapper.class);
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        boolean isAppend = true;
        orderDaoFileImpl.saveOrders(TEST_DATE, isAppend);
        //Side-effect verification
        String[][] linesToAssert = new String[1][12];
        final String[] testOrderValues = new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"};
        linesToAssert[0] = testOrderValues;
        assertFileContents(linesToAssert);
        //Save map into file again
        orderDaoFileImpl.saveOrders(TEST_DATE, isAppend);
        String[][] linesToAssert2 = new String[2][12];
        linesToAssert2[0] = testOrderValues;
        linesToAssert2[1] = testOrderValues;
        assertFileContents(linesToAssert2);

    }

    /**
     * Test of saveOrders method, of class OrderDaoFileImpl.
     */
    @Test
    public void testSaveOrders_isProduction_isNotAppend() throws Exception {
        //Test Setup
        Map<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("1", new Order("1", "nameTest", "stateTest", BigDecimal.ZERO, "productTypeTest", 1.0f, BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN));
        boolean isProduction = true;
        FileMapper fileMapper = Mockito.mock(FileMapper.class);
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        boolean isAppend = false;
        orderDaoFileImpl.saveOrders(TEST_DATE, isAppend);
        //Side-effect verification
        String[][] linesToAssert = new String[1][12];
        linesToAssert[0] = new String[]{"1", "nameTest", "stateTest", "0", "productTypeTest", "1.0", "1", "10", "10", "0", "1", "10"};
        assertFileContents(linesToAssert);
    }

    /**
     * Test of saveOrders method, of class OrderDaoFileImpl.
     */
    @Test
    public void testSaveOrders_isNotProduction() throws Exception {
        //Test Setup
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = false;
        FileMapper fileMapper = Mockito.mock(FileMapper.class);
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Method being tested
        boolean isAppend = true;
        orderDaoFileImpl.saveOrders(TEST_DATE, isAppend);
        //Side-effect verification
        File testOrders = new File(TEST_FILENAME);
        if (testOrders.exists()) {
            try {
                testOrders.delete();
            } catch (Exception e) {
                System.out.println(e);
            }
            Assert.fail("SaveOrders created a file when it shouldn't have.");
        }

    }

    /**
     * Test of loadOrders method, of class OrderDaoFileImpl.
     */
    @Test
    public void testLoadOrders() throws Exception {
        //Test Setup
        Map<String, Object> ordersMap = new HashMap<>();
        FileMapper fileMapper = Mockito.mock(FileMapper.class);
        boolean isProduction = true;
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        // Method being tested
        orderDaoFileImpl.loadOrders("testDate");
        // Side-effect verification
        Mockito.verify(fileMapper, Mockito.times(1)).mapFile(Matchers.eq(TEST_FILENAME), Matchers.same(ordersMap), Matchers.any(Function.class));
        Mockito.verifyNoMoreInteractions(fileMapper);
    }

    private static void writeValues(String[] values, boolean isAppend) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter(TEST_FILENAME, isAppend));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(values[i]);
        }
        pw.println(sb);
        pw.flush();
        pw.close();
    }

    private static void assertFileContents(String[][] expectedValues) throws FileNotFoundException {
        File testOrders = new File(TEST_FILENAME);
        if (!testOrders.exists()) {
            Assert.fail("SaveOrders did not create a file.");
        }
        FileReader productsFile = new FileReader(TEST_FILENAME);
        try (Scanner sc = new Scanner(productsFile)) {
            sc.hasNextLine();
            for (int i = 0; i < expectedValues.length; i++) {
                String nxtLn;
                try {
                    nxtLn = sc.nextLine(); //Get the "i"th line
                } catch (NoSuchElementException e) {
                    Assert.fail("Less orders than expected!");
                    break;
                }
                String[] actualValues = nxtLn.split(","); //Split the line
                for (int j = 0; j < expectedValues[i].length; j++) {
                    Assert.assertEquals(expectedValues[i][j], actualValues[j]);
                }
            }
            if (sc.hasNextLine()) {
                Assert.fail("Not all content in file was asserted!");
            }
        }
    }
}
