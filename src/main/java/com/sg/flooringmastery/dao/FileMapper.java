/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author juanm
 */
public class FileMapper {
    
    public void mapFile(String fileName, Map<String, Object> map, Function <String[], Object> lineToObjectFunction) throws FileNotFoundException {
        FileReader productsFile = new FileReader(fileName);
        Scanner sc = new Scanner(productsFile);
        try{
            while (sc.hasNextLine()) {
                String nxtLn = sc.nextLine();
                String[] lineValues = nxtLn.split(",");
                String key = lineValues[0];
                Object object = lineToObjectFunction.apply(lineValues);
                map.put(key, object);
            }
        } finally {
            sc.close();
        }
    }
}
