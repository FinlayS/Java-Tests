package com.javafortesters.domainentities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class StoreDetailsGet {

    private String storeid;
    private String brand;
    private String country;
    private String region;
    private String regionselect;
    private String language;
    private String environmentid;
    private String environment;
    private String baseurl;
    private String paycardid;
    private String selectxpath;
    private String sizexpath;
    private String selector;
    private String signintext;
    private String chgpwdtext;
    private String signouttext;
    private String category;
    private String promocode;
    private String csrStore;
    private String arcStoreID;


    public String getStoreid() {
        return storeid;
    }
    public String getBrand() {
        return brand;
    }
    public String getCountry() {
        return country;
    }
    public String getRegion() {
        return region;
    }
    public String getRegionselect() {
        return regionselect;
    }
    public String getLanguage() {
        return language;
    }
    public String getEnvironmentid() {
        return environmentid;
    }
    public String getEnvironment() {
        return environment;
    }
    public String getBaseurl() {
        return baseurl;
    }
    public String getPaycardid() {
        return paycardid;
    }
    public String getSelectxpath() {
        return selectxpath;
    }
    public String getSizexpath() {
        return sizexpath;
    }
    public String getSelector() {
        return selector;
    }
    public String getSignintext() {
        return signintext;
    }
    public String getChgpwdtext() {
        return chgpwdtext;
    }
    public String getSignouttext() {
        return signouttext;
    }
    public String getCategory() {
        return category;
    }
    public String getPromocode() {
        return promocode;
    }
    public String getCsrStore() {return csrStore;}
    public String getArcStoreID() {return arcStoreID;}


    public StoreDetailsGet(String store ) {

        try {
            File inputfile = new File(System.getProperty("user.dir")
                    + "\\StoreList.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
            Document doc = docbuilder.parse(inputfile);
            doc.getDocumentElement().normalize();
            //NodeList nodelist = doc.getElementsByTagName("EBTStoreData");
            NodeList nodelist = doc.getElementsByTagName("StagingStoreData");
            //NodeList nodelist = doc.getElementsByTagName("PPLStoreData");
            //NodeList nodelist = doc.getElementsByTagName("PPSStoreData");

            for (int tmp = 0; tmp < nodelist.getLength(); tmp++) {
                Node nNode = nodelist.item(tmp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    storeid  =  eElement.getAttribute("iStoreID");

                    if (store.equals(storeid)) {
                        brand = eElement.getElementsByTagName("iBrand")
                                .item(0).getTextContent();
                        country = eElement.getElementsByTagName("iCountry")
                                .item(0).getTextContent();
                        region = eElement.getElementsByTagName("iRegion")
                                .item(0).getTextContent();
                        regionselect = eElement.getElementsByTagName("iRegionSelect")
                                .item(0).getTextContent();
                        language = eElement.getElementsByTagName("iLanguage")
                                .item(0).getTextContent();
                        environmentid = eElement.getElementsByTagName("iEnvironmentID")
                                .item(0).getTextContent();
                        environment = eElement.getElementsByTagName("iEnvironment")
                                .item(0).getTextContent();
                        baseurl = eElement.getElementsByTagName("ibaseurl")
                                .item(0).getTextContent();
                        paycardid = eElement.getElementsByTagName("iPayCardID")
                                .item(0).getTextContent();
                        selectxpath = eElement.getElementsByTagName("iSelectXpath")
                                .item(0).getTextContent();
                        sizexpath = eElement.getElementsByTagName("iSizeXpath")
                                .item(0).getTextContent();
                        selector = eElement.getElementsByTagName("iSelectOr")
                                .item(0).getTextContent();
                        signintext = eElement.getElementsByTagName("iSignInText")
                                .item(0).getTextContent();
                        chgpwdtext = eElement.getElementsByTagName("iChangePwdText")
                                .item(0).getTextContent();
                        signouttext = eElement.getElementsByTagName("iSignOutText")
                                .item(0).getTextContent();
                        category = eElement.getElementsByTagName("iCategory")
                                .item(0).getTextContent();
                        promocode = eElement.getElementsByTagName("iPromoCode")
                                .item(0).getTextContent();
                        csrStore = eElement.getElementsByTagName("iCSRStore")
                                .item(0).getTextContent();
                        arcStoreID = eElement.getElementsByTagName("iArcStoreID")
                                .item(0).getTextContent();
                        break;

                    } else {
                        //store = store;
                        }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}



