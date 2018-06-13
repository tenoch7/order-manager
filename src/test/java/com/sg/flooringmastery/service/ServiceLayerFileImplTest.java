/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FileMapper;
import com.sg.flooringmastery.dao.OrderDaoFileImpl;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.ProductDaoFileImpl;
import com.sg.flooringmastery.dao.TaxRateDao;
import com.sg.flooringmastery.dao.TaxRateDaoFileImpl;
import com.sg.flooringmastery.model.ProductDetail;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author juanm
 */
public class ServiceLayerFileImplTest {
    
    private static final String TEST_PREFIX = "testPrefix_";
    
    public ServiceLayerFileImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTaxRate method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetTaxRate() throws Exception {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        BigDecimal ohio = service.getTaxRate("OH");
        assertEquals("6.25", ohio.toString());
    }

    /**
     * Test of getStateCodes method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetStateCodes() throws Exception {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        Set<String> stateKeys = service.getStateCodes();
        int numOfStates = stateKeys.size();
        assertEquals(4, numOfStates); //4 states currently on the file
        
    }

    /**
     * Test of getProductDetail method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetProductDetail() throws Exception {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        ProductDetail productDetail =  service.getProductDetail("Wood");
        BigDecimal materialCost = productDetail.getMaterialCostPerSquareFoot();
        BigDecimal laborCost = productDetail.getLaborCostPerSquareFoot();
        assertEquals("5.15", materialCost.toString());
        assertEquals("4.75", laborCost.toString());
        
    }

    /**
     * Test of getProductNames method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetProductNames() throws Exception {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        Set<String> productKeys = service.getProductNames();
        int numOfProducts = productKeys.size();
        assertEquals(4, numOfProducts); //4 products currently on file
    }

    /**
     * Test of generateOrderNum method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGenerateOrderNum() {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        String orderNum1 = service.generateOrderNum();
        String orderNum2 = service.generateOrderNum();
        assertNotSame(orderNum1, orderNum2); //UUID generated orderNumber will not match
    }

    /**
     * Test of getTotalMaterialCost method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetTotalMaterialCost() {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        BigDecimal totalMaterialCost = service.getTotalMaterialCost(BigDecimal.valueOf(3.5), 10f);
        assertEquals("35.00", totalMaterialCost.toString());//Method multiplies materialCost*area
    }

    /**
     * Test of getTotalLaborCost method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetTotalLaborCost() {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        BigDecimal totalLaborCost = service.getTotalLaborCost(BigDecimal.valueOf(4.15), 10f);
        assertEquals("41.50", totalLaborCost.toString());//Method multiplies laborCost*area
    }

    /**
     * Test of getTotaltax method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetTotaltax() {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        
        //Method being tested
        BigDecimal totalTax = service.getTotaltax(BigDecimal.valueOf(35), BigDecimal.valueOf(41.5), BigDecimal.valueOf(6.25));
        assertEquals("4.78", totalTax.toString());//Method add costs, multiplies by taxRate and divides by 100
    }

    /**
     * Test of getOrderTotal method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetOrderTotal() {
        //Test Setup - Order
        Map<String, Object> ordersMap = new HashMap<>();
        boolean isProduction = true;
        FileMapper fileMapper = new FileMapper();
        OrderDaoFileImpl orderDaoFileImpl = new OrderDaoFileImpl(ordersMap, fileMapper, TEST_PREFIX, isProduction);
        //Test Setup - TaxRateDao
        Map<String, Object> stateMap = new HashMap<>();
        TaxRateDao taxRateDao = new TaxRateDaoFileImpl(stateMap, fileMapper, "taxes.txt");
        //Test Setup - ProductDao
        Map<String, Object> productMap = new HashMap<>();
        ProductDao productDao = new ProductDaoFileImpl(productMap, fileMapper, "products.txt");
        //Test Setup - SERVICE
        ServiceLayerFileImpl service = new ServiceLayerFileImpl(orderDaoFileImpl, taxRateDao, productDao);
        BigDecimal totalMaterialCost = service.getTotalMaterialCost(BigDecimal.valueOf(3.5), 10f);
        BigDecimal totalLaborCost = service.getTotalLaborCost(BigDecimal.valueOf(4.15), 10f);
        BigDecimal totalTax = service.getTotaltax(BigDecimal.valueOf(35), BigDecimal.valueOf(41.5), BigDecimal.valueOf(6.25));

        //Method being tested
        BigDecimal orderTotal = service.getOrderTotal(totalMaterialCost, totalLaborCost, totalTax);
        assertEquals("81.28", orderTotal.toString());//Method adds costs and tax
    }

    /**
     * Test of createOrder method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testCreateOrder() throws Exception {
    }

    /**
     * Test of getOrdersByDate method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetOrdersByDate() throws Exception {
    }

    /**
     * Test of getOneOrder method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testGetOneOrder() throws Exception {
    }

    /**
     * Test of editOrder method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testEditOrder() throws Exception {
    }

    /**
     * Test of removeOrder method, of class ServiceLayerFileImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
    }
    
}
