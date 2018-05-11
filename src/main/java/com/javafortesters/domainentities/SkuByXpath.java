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
 * Finally got it to work. Will change/tidy to add sku: qty: totals and discount calc and return all to the calling class.
 *  -- Need to --
 * 1. rename class & re-title field names.
 * 2. bring the xpaths required into here and remove from the calling class.
 */
public class SkuByXpath extends WebDriverTestBase{



    private String returnedsku;
    public String getReturnedSku() {
        return returnedsku;
    }


    public SkuByXpath(List skuLinks) {

        Iterator<WebElement> itrSkuLinks = skuLinks.iterator();

        //Get the skus.
        while (itrSkuLinks.hasNext()) {
            String sku = (itrSkuLinks.next().getText());
            returnedsku = sku.trim();
        }
    }
}
