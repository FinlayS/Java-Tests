package com.javafortesters.domainentities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DataDrivenTestGet {

    private String testnumber;
    private String teststore;
    private String testprofile;
    private String testpaymenycard;
    private String testpromocode;
    private int filelength;

    public String getTestpaymenycard() {
        return testpaymenycard;
    }
    public String getTestprofile() {
        return testprofile;
    }
    public String getTestpromocode() {return testpromocode;}
    public String getTestnumber() {return testnumber;}
    public String getTeststore() {return teststore;}
    public int getFilelength() {
        return filelength;
    }

    public DataDrivenTestGet(String testid) {

        try {
            File inputfile = new File(System.getProperty("user.dir")
                    + "\\DataDrivenTests.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
            Document doc = docbuilder.parse(inputfile);
            doc.getDocumentElement().normalize();
            NodeList nodelist = doc.getElementsByTagName("TestData");
            filelength = nodelist.getLength();

            for (int tmp = 0; tmp < filelength; tmp++) {
                Node nNode = nodelist.item(tmp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String isTID  =  eElement.getElementsByTagName("ddtID")
                            .item(0).getTextContent();
                    if (testid.equals(isTID)) {
                        testnumber = eElement.getElementsByTagName("iTDTestID")
                                .item(0).getTextContent();
                        teststore = eElement.getElementsByTagName("iTDTestStoreID")
                                .item(0).getTextContent();
                        testprofile = eElement.getElementsByTagName("iTDProfileID")
                                .item(0).getTextContent();
                        testpaymenycard = eElement.getElementsByTagName("iTDCardID")
                                .item(0).getTextContent();
                        testpromocode = eElement.getElementsByTagName("iTDPromoCode")
                                .item(0).getTextContent();

                        break;

                    } else {
                        testnumber = ("TestIDNotfound");
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


}



