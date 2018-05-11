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
public class NumberByXpathGet extends WebDriverTestBase{

    private int returnednumber;
    public int getReturnednumber() {
        return returnednumber;
    }

    public NumberByXpathGet(List allLinks) {

        returnednumber = 0;
        Iterator<WebElement> itr = allLinks.iterator();
        while(itr.hasNext()) {
            String iprice = (itr.next().getText());

                // Remove and chars with RegEx.
            String nowNumber = iprice.replaceAll("[^0-9]", "");
            int i = Integer.parseInt(nowNumber);
            returnednumber = i;
        }
    }
}
