package com.javafortesters.domainentities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 27/01/2015.
 * Time: 14:50
 */
public class GetAllBrandSkusAndCurrency extends WebDriverTestBase{
    public String localCurrency;
    public String inputString;
    public ArrayList<String> bs  = new ArrayList<>();
    public String getCurreny() {
        return localCurrency;
    }
    public ArrayList<String> getBs() {return bs;}

    public GetAllBrandSkusAndCurrency(String inputString) {

        //String inputString = new
                //=(driver.getPageSource());
        List<Integer> brandSkus;
        List<Integer> currency;
        brandSkus = findAllOccurrences(inputString, "sku_code");
        currency = findAllOccurrences(inputString, "currency");

        // Get Skus.
        for (int x = 0; x < brandSkus.size(); x++) {
            int posn1 = brandSkus.get(x) + 10;
            int posn2 = posn1 + 19;
            String s = inputString.substring(posn1, posn2).replaceAll("[^0-9]", "");
            bs.add(s);
        }
        // Now get the the first appearance of currency.
        int posn1 = currency.get(0) + 10;
        int posn2 = posn1 + 10;
        localCurrency = inputString.substring(posn1, posn2).replaceAll("[^A-Z, a-z]", "");
    }
    private List<Integer> findAllOccurrences(String string,
                                             String substring) {

        List<Integer> results = new ArrayList<>();

        if(string==null || substring==null){
            throw new IllegalArgumentException("Cannot search using null");
        }
        if(substring.isEmpty()){
            throw new IllegalArgumentException(
                    "Cannot search for Empty substring");
        }
        // set search to the start of the string
        int lastfoundPosition = 0;

        do{
            // try and find the substring
            lastfoundPosition = string.indexOf( substring,
                    lastfoundPosition);

            // if we found it
            if(lastfoundPosition!=-1){

                // add it to the results
                results.add(lastfoundPosition);

                // next start after this index
                lastfoundPosition++;
            }
            // keep looking until we can't find it
        }while(lastfoundPosition!=-1);

        return results;
    }
}
