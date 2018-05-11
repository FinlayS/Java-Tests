package com.javafortesters.xmlgenrationclasses;

//import org.junit.Test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 23/01/2015.
 * Time: 12:14
 */


public class TestWriteXML {
    //@Test
    public void mainTest(){

        //String args[] = new String[25];

        ArrayList<String> Header = new ArrayList<>();
        ArrayList<String> Items = new ArrayList<>();

/**
        args[0] = "595959"; // Ordernumber
        args[1] = "USD"; // Currency
        args[2] = "190.00"; // Header: Total net price
        args[3] = "5.00"; // Header: Shipping price
        args[4] = "195.00"; // Header: Selling Price
        args[5] = "999A9999"; // WCS Code
        args[6] = "602105452144"; // Brand Product Sku
        args[7] = "10"; // Ship (order} quantity
        args[8] = "19.00"; // Item: Total net & Total Selling price
        args[5] = "######"; // WCS Code
***/


        Header.add("595959");
        Header.add("HEADERUSD"); // Currency
        Header.add("HEADER190.00"); // Header: Total net price
        Header.add("HEADER5.00"); // Header: Shipping price
        Header.add("HEADERx195.00"); // Header: Selling Price
        Items.add("ITEM999A9999"); // WCS Code
        Items.add("ITEM602105452144"); // Brand Product Sku
        Items.add("1.00"); // Ship (order} quantity
        Items.add("ITEMx19.00"); // Item: Total net & Total Selling price
        Items.add("ITEM111A9999"); // WCS Code
        Items.add("ITEM602105452144"); // Brand Product Sku
        Items.add("ITEM10.00"); // Ship (order} quantity
        Items.add("ITEMx219.00"); // Item: Total net & Total Selling price
        Items.add("ITEM999A9999"); // WCS Code
        Items.add("ITEM602105452144"); // Brand Product Sku
        Items.add("ITEM0.00"); // Ship (order} quantity
        Items.add("ITEMx119.00"); // Item: Total net & Total Selling price

        //args.clear();

        int count =3;
        String folder = "/";
        //System.out.println(args.size());

        WriteXMLFileNew.main(Header, Items, count, folder);
    }
}
