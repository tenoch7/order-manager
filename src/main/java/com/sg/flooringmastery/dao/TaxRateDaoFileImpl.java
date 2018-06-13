/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.model.ProductDetail;
import com.sg.flooringmastery.model.StateTax;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author juanm
 */
public class TaxRateDaoFileImpl implements TaxRateDao {

    private Map<String, Object> stateMap;
    private final String fileLocation;
    private FileMapper fileMapper;

    public TaxRateDaoFileImpl(Map<String, Object> stateMap, FileMapper fileMapper, String fileLocation) {
        this.stateMap = stateMap;
        this.fileMapper = fileMapper; //Puts the file into a map
        this.fileLocation = fileLocation; //"taxes.txt"
    }

    
    
    @Override
    public Map getTaxRate() throws FileNotFoundException {

        Function<String[], Object> lineToTaxesDetailFunction = (String[] lineValues) -> {
            BigDecimal taxRate = new BigDecimal(lineValues[1]);

            StateTax stateTax = new StateTax(taxRate);
            return stateTax;
        };
        this.fileMapper.mapFile(fileLocation, stateMap, lineToTaxesDetailFunction);
//        StateTax stateTax = (StateTax) stateMap.get(stateCode);
        return stateMap;
    }

}
