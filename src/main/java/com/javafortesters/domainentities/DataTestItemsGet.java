package com.javafortesters.domainentities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class DataTestItemsGet {

    public ArrayList<String> itemcodes  = new ArrayList<>();
    public ArrayList<String> itemqauntites  = new ArrayList<>();
    public ArrayList<String> itemsizes  = new ArrayList<>();
    private String itemcode;
    private int filelength;

    public ArrayList<String> getItemcodes() {return itemcodes;}
    public ArrayList<String> getItemqauntites() {return itemqauntites;}
    public ArrayList<String> getItemsizes() {return itemsizes;}

    public DataTestItemsGet(String itemid) {

        try {
            File inputfile = new File(System.getProperty("user.dir")
                    + "\\DataTestItems.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
            Document doc = docbuilder.parse(inputfile);
            doc.getDocumentElement().normalize();
            NodeList nodelist = doc.getElementsByTagName("TestItem");
            filelength = nodelist.getLength();

            for (int tmp = 0; tmp < filelength; tmp++) {
                Node nNode = nodelist.item(tmp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String isIID  =  eElement.getElementsByTagName("iDTITestID")
                            .item(0).getTextContent();
                    if (itemid.equals(isIID)) {
                        itemcodes.add(eElement.getElementsByTagName("iTestItemCode")
                                .item(0).getTextContent());
                        itemqauntites.add(eElement.getElementsByTagName("iTestItemQty")
                                .item(0).getTextContent());
                        itemsizes.add(eElement.getElementsByTagName("iTestItemSize")
                                .item(0).getTextContent());

                        //break;

                    } else {
                        itemcode = ("TestItemNotfound");
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


}



