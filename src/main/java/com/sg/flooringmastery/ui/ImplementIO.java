/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author juanm
 */
public class ImplementIO implements UserIO{
    
    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        double num = sc.nextDouble();
        sc.nextLine();
        return num;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        print(prompt);
        double rDouble = sc.nextDouble();
        sc.nextLine();
        while (rDouble < min || rDouble > max) {
            System.out.println(prompt);
            rDouble = sc.nextDouble();
        }
        return rDouble;
    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        float rFl = sc.nextFloat();
        sc.nextLine();
        return rFl;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        print(prompt);
        float rFloat = sc.nextInt();
        while (rFloat < min || rFloat > max) {
            System.out.println("Enter a float from " + min + " and " + max);
            rFloat = sc.nextFloat();
            sc.nextLine();
        }
        return rFloat;
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        int rInteger = sc.nextInt();
        sc.nextLine();
        return rInteger;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        print(prompt);
        int rInt = sc.nextInt();
        sc.nextLine();

        while (rInt < min || rInt > max) {
            System.out.println("Enter an int from " + min + " and " + max);
            rInt = sc.nextInt();
            sc.nextLine();
        }
        return rInt;
    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        long rLong = sc.nextLong();
        sc.nextLine();
        return rLong;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        print(prompt);
        long rLong = sc.nextInt();
        sc.nextLine();
        while (rLong < min || rLong > max) {
            System.out.println("Enter a Long from " + min + " and " + max);
            rLong = sc.nextLong();
            sc.nextLine();
        }
        return rLong;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        String rString = sc.nextLine();
        return rString;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        print (prompt);
        BigDecimal rBigDecimal = new BigDecimal(sc.nextLine());
        return rBigDecimal;
    }
    
}
