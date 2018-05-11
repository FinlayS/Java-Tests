package com.javafortesters.domainentities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class PaymentCardGet {

    private String cardid;
    private String cardbrand;
    private String cardno;
    private String cvcno;
    private String strmonth;
    private String stryear;
    private String endmonth;
    private String endyear;
    private String issue;
    private String response;
    private String delay;
    private String acreditor;
    private String authreply;
    private String pamentprofile;
    private String paymentpassword;
    private int filelength;


    public String getCardid() {
        return cardid;
    }
    public String getCardno() {
        return cardno;
    }
    public String getCvcno() {
        return cvcno;
    }
    public String getStrmonth() {
        return strmonth;
    }
    public String getStryear() {
        return stryear;
    }
    public String getEndmonth() {
        return endmonth;
    }
    public String getEndyear() {
        return endyear;
    }
    public String getIssue() {
        return issue;
    }
    public String getResponse() {
        return response;
    }
    public String getDelay() {
        return delay;
    }
    public String getCardbrand() {
        return cardbrand;
    }
    public String getAcreditor() {
        return acreditor;
    }
    public String getAuthreply() {
        return authreply;
    }
    public String getPamentprofile() {
        return pamentprofile;
    }
    public String getPaymentpassword() {
        return paymentpassword;
    }
    public int getFilelength() {
        return filelength;
    }

    public PaymentCardGet(String cardid) {

        try {
            File inputfile = new File(System.getProperty("user.dir")
                    + "\\CardList.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
            Document doc = docbuilder.parse(inputfile);
            doc.getDocumentElement().normalize();
            NodeList nodelist = doc.getElementsByTagName("CardData");
            filelength = nodelist.getLength();

            for (int tmp = 0; tmp < filelength; tmp++) {
                Node nNode = nodelist.item(tmp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String ispID  =  eElement.getElementsByTagName("pcID")
                            .item(0).getTextContent();
                    if (cardid.equals(ispID)) {
                        cardid = eElement.getElementsByTagName("pcID")
                                .item(0).getTextContent();
                        cardbrand = eElement.getElementsByTagName("iCardBrand")
                                .item(0).getTextContent();
                        cardno = eElement.getElementsByTagName("iCardNo")
                                .item(0).getTextContent();
                        cvcno = eElement.getElementsByTagName("iCVCNo")
                                .item(0).getTextContent();
                        strmonth = eElement.getElementsByTagName("iStrMonth")
                                .item(0).getTextContent();
                        stryear = eElement.getElementsByTagName("iStrYear")
                                .item(0).getTextContent();
                        endmonth = eElement.getElementsByTagName("iEndMth")
                                .item(0).getTextContent();
                        endyear = eElement.getElementsByTagName("iEndYear")
                                .item(0).getTextContent();
                        issue = eElement.getElementsByTagName("iIssue")
                                .item(0).getTextContent();
                        response = eElement.getElementsByTagName("iResponse")
                                .item(0).getTextContent();
                        delay = eElement.getElementsByTagName("iDelay")
                                .item(0).getTextContent();
                        acreditor = eElement.getElementsByTagName("iAcreditor")
                                .item(0).getTextContent();
                        authreply = eElement.getElementsByTagName("iAuthReply")
                                .item(0).getTextContent();
                        pamentprofile = eElement.getElementsByTagName("iPaymentProfile")
                                .item(0).getTextContent();
                        paymentpassword = eElement.getElementsByTagName("iPaymentPassword")
                                .item(0).getTextContent();
                        break;

                    } else {
                        cardno = ("CardNotFound");
                        }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}



