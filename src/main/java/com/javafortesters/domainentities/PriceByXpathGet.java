package com.javafortesters.domainentities;

import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 10/12/2014.
 * Time: 11:46
 */
public class PriceByXpathGet extends WebDriverTestBase{

    private Double returnedprice;
    public Double getReturnedprice() {
        return returnedprice;
    }

    public PriceByXpathGet(List allLinks, String currency) {

        returnedprice = 0.0;
        int x = 1;

        Iterator<WebElement> itr = allLinks.iterator();
        while(itr.hasNext()) {
            x = x+ 1;
            String iprice = (itr.next().getText());

                // Remove chars with RegEx but leave decimal point and comma for €
                // (i don't know how to replace the comma within the same ReGex..yet!).
            String nowNumber = iprice.replaceAll("[^0-9.,]", "");
                // For €uro we use a bloody comma!!
            if (currency == "EUR") {
                nowNumber = nowNumber.replace(",", ".");
            } else {
                nowNumber = nowNumber.replace(",", "");
            }

            if (nowNumber.isEmpty()) {                                 // If the price is hidden in the PDP.
                nowNumber = "0.0";
            }
                // Now parse to number for return.
            double d = Double.parseDouble(nowNumber);
            returnedprice = d;
        }
    }

}
