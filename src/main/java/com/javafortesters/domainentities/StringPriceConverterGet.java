package com.javafortesters.domainentities;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 10/12/2014.
 * Time: 11:46
 */
public class StringPriceConverterGet {

    private Double returnedprice;
    public Double getReturnedprice() {
        return returnedprice;
    }

    public StringPriceConverterGet(String iprice) {

        // Remove chars with RegEx but leave decimal point.
        String nowNumber = iprice.replaceAll("[^0-9.]", "");

        // Now parse to number for return.
        double d = Double.parseDouble(nowNumber);
        returnedprice = d;

        //System.out.println("Now a number: '"+nowNumber+'\'');
        //System.out.println(d);

    }

}
