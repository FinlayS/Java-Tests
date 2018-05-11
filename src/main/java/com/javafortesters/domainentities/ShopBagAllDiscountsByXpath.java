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
 * Finally got it to work. Will change/tidy to add sku: qty: totals and discount calc and return all to the calling class.
 *  -- Need to --
 * 1. rename class & re-title field names.
 * 2. bring the xpaths required into here and remove from the calling class.
 */
public class ShopBagAllDiscountsByXpath extends WebDriverTestBase{

    public Double valuealldiscount;
    public Double valueindividualunit;
    public Double valuesummedunits;
    public ArrayList<Double> up  = new ArrayList<>();
    public ArrayList<Double> tp  = new ArrayList<>();
    public ArrayList<Double> qt  = new ArrayList<>();
    public ArrayList<Double> dc  = new ArrayList<>();
    public ArrayList<String> sk  = new ArrayList<>();
    public Double getValueAllDiscount() {
        return valuealldiscount;
    }
    public Double getValueIndividualUnit() {
        return valueindividualunit;
    }
    public Double getValueSummedUnits() {
        return valuesummedunits;
    }
    public ArrayList<Double> getUp() {return up;}
    public ArrayList<Double> getTp() {return tp;}
    public ArrayList<Double> getQt() {return qt;}
    public ArrayList<Double> getDc() {return dc;}
    public ArrayList<String> getSk() {return sk;}

    public ShopBagAllDiscountsByXpath(List priceLinks, List qtyLinks, List skuLinks) {
        valuealldiscount = 0.0;
        valueindividualunit = 0.0;
        valuesummedunits = 0.0;
        boolean freebie = false;
        Iterator<WebElement> itrPriceLinks = priceLinks.iterator();
        Iterator<WebElement> itrQtyLinks = qtyLinks.iterator();
        Iterator<WebElement> itrSkuLinks = skuLinks.iterator();

        int x = 1;
        // Get the unit and total prices
        while (itrPriceLinks.hasNext()) {
            String iprice = (itrPriceLinks.next().getText());

            if (iprice != null && !iprice.trim().isEmpty()) {
                x++;
                String pnowNumber = iprice.replaceAll("[^0-9.,]", "");      // Remove all/any chars
                pnowNumber = pnowNumber.replace(",", ".");                  // For €uro we use a bloody comma as a dec seperator!!
                if (pnowNumber.isEmpty()) {                                 // Bloody free gifts have no price innit!
                    pnowNumber = "0.0";
                }
                double d = Double.parseDouble(pnowNumber);                  // Now parse the result to number for return.

                if ((x % 2) == 0) {               // read is even so this is unit prices

                    if (d == (0)) {               // if the unit price is £0 thin its a free gift.
                        freebie = true;
                        //System.out.println("1" +freebie);
                    }
                    up.add(d);
                    valueindividualunit = valueindividualunit + d;
                    //System.out.println("fIU = " + d + "count " + x);

                } else {                            // read is odd so is total price.
                    if (freebie == false) {
                        //System.out.println("2" +freebie);
                        tp.add(d);
                        valuesummedunits = valuesummedunits + d;
                        //System.out.println("SU = " + d + "count " + x);

                    } else {                        // For our calc we need to swap the freebie total with the previous £0 written for units.
                        //System.out.println("3" +freebie);
                        tp.add(0.0);                      // Added this to get free price in units price.
                        //System.out.println(up);
                        int index = x-1;
                        //System.out.println("x= "+x +" index= "+index);
                        up.remove(up.size()-1);
                        //System.out.println(up);
                        up.add(d);
                        valueindividualunit = valueindividualunit + d;
                        //System.out.println("lIU = " + d + "count " + x);
                        freebie = false;
                    }
                }
            }   // close curly was here.
        }
        // Get the unit quantities.
        while (itrQtyLinks.hasNext()) {
            String iqty = (itrQtyLinks.next().getText());
            String qnowNumber = iqty.replaceAll("[^0-9.,]", "");            // Tidy with RegEx
            qnowNumber = qnowNumber.replace(",", ".");
            double q = Double.parseDouble(qnowNumber);                      // Now parse to number for return.
            qt.add(q);
        }
        //Get the skus.
        while (itrSkuLinks.hasNext()) {
            String sku = (itrSkuLinks.next().getText());
            sku = sku.trim();                                               // Tidy whitespace
            //System.out.println("Sku: " + sku);
            sk.add(sku);
        }

        // Calc any discounts (assume array size eq for all)
        for (int i = 0; i < qt.size(); i++) {
            Double qtysum = qt.get(i) * up.get(i);                          // For sku multiply qty with unit price.
            Double DiscTotal;

            if (qtysum > 0.0 && tp.get(i)==0.0) {                           // Item is has 100% discount.
                DiscTotal = qtysum * -1;
            } else {
                DiscTotal = qtysum - tp.get(i);                             // subtract each total from above to calc any %
            }
            dc.add(DiscTotal);                                              // Add to new array.
            valuealldiscount = valuealldiscount + DiscTotal;
        }

        for (int z = 0; z < (qt.size() - sk.size()); z++) {
            sk.add("FREE");                                                 // Free gifts have no sku listed, if more qtys than skus, Add it.
        }
        /*
        System.out.println("Qty Array= " +qt.size());
        System.out.println("Sku Array= " +sk.size());

        System.out.println("Unit prices" + up);
        System.out.println("Total Unit " + valueindividualunit);

        System.out.println("Unit qtys " + qt);

        System.out.println("Total " + tp);
        System.out.println("Total Summed Units " + valuesummedunits);
        System.out.println("Items " + sk);
        */

    }
}
