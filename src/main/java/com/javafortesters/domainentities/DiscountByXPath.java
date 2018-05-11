package com.javafortesters.domainentities;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 10/12/2014.
 * Time: 11:46
 */
public class DiscountByXPath extends WebDriverTestBase{

    private Double allitemsdiscount;
    public ArrayList<String> dd  = new ArrayList<>();
    public ArrayList<Double> dv  = new ArrayList<>();
    public Double getAllItemsDiscount() {
        return allitemsdiscount;
    }
    public ArrayList<String> getDd() {return dd;}
    public ArrayList<Double> getDv() {return dv;}

    public DiscountByXPath(List allLinks) {

        allitemsdiscount = 0.0;
        Iterator<WebElement> itr = allLinks.iterator();

        int x = 1;

        while(itr.hasNext()) {
            String element = (itr.next().getText());

            if(element != null && !element.trim().isEmpty()) {
                x++;
                /* The WebElement list has the description and discount prices but as we can't get to the element
               names to identify first, second, third read etc, we check if read number is odd or even. */
                if ((x % 2) == 0) {
                    // read is even so discount description
                    dd.add(element);
                    //beforediscount = beforediscount + d;
                } else {
                    // read is odd so discount value
                    String nowNumber = element.replaceAll("[^0-9.,]", "");
                    // For €uro we use a bloody comma!!
                    nowNumber = nowNumber.replace(",", ".");
                    // Now parse to number for return and make it negative.
                    double d = Double.parseDouble(nowNumber) * -1;
                    dv.add(d);
                    allitemsdiscount = allitemsdiscount + d;
                }
            }
        }
    }
}
