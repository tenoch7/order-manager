/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.ProductDetail;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author juanm
 */
public class ProductDaoFileImpl implements ProductDao{
    private Map<String, Object> productMap;
    private final String productsFileName;
    private FileMapper fileMapper;

    public ProductDaoFileImpl(Map<String, Object> productMap, FileMapper fileMapper, String productsFileName) {
        this.productMap = productMap;
        this.fileMapper = fileMapper;
        this.productsFileName = productsFileName;
    }
    
    @Override
    public Map getProductCost() throws FileNotFoundException {
        Function<String[], Object> lineToProductDetailFunction = (String[] lineValues) -> { 
            BigDecimal materialCostPerSquareFoot = new BigDecimal(lineValues[1]);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(lineValues[2]);
            ProductDetail productDetail = new ProductDetail(materialCostPerSquareFoot, laborCostPerSquareFoot);
            return productDetail;
        };
        this.fileMapper.mapFile(productsFileName, productMap, lineToProductDetailFunction);
//        Object object = productMap.get(productType);
//        ProductDetail productDetail = (ProductDetail)object;
        
        return productMap;
    }
    
    
}
