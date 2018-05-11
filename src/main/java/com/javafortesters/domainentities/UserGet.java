package com.javafortesters.domainentities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class UserGet {

    private String username;
    private String password;
    private String cusMailName;
    private String cusMailDomain;
    private String cusTitle;
    private String cusForename;
    private String cusSurname;
    private String cusPhoneNo;
    private String cusCountry;
    private String cusHouseNamNum;
    private String cusAddressLine1;
    private String cusAddressLine2;
    private String cusTownCity;
    private String cusPostCode;
    private String cusPCProfile;


    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public String getCusMailName() {return cusMailName;}
    public String getCusMailDomain(){return cusMailDomain;}
    public String getCusTitle(){return cusTitle;}
    public String getCusForename(){return cusForename;}
    public String getCusSurname() {return cusSurname;}
    public String getCusPhoneNo(){return cusPhoneNo;}
    public String getCusCountry(){return cusCountry;}
    public String getCusHouseNamNum(){return cusHouseNamNum;}
    public String getCusAddressLine1(){return cusAddressLine1;}
    public String getCusAddressLine2(){return cusAddressLine2;}
    public String getCusTownCity(){return cusTownCity;}
    public String getCusPostCode() {return cusPostCode;}
    public String getCusPCProfile(){return cusPCProfile;}



    public UserGet(String userid) {

        try {
            File inputfile = new File(System.getProperty("user.dir")
                    + "\\ProfileList.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
            Document doc = docbuilder.parse(inputfile);
            doc.getDocumentElement().normalize();
            NodeList nodelist = doc.getElementsByTagName("ProfileData");

            for (int tmp = 0; tmp < nodelist.getLength(); tmp++) {
                Node nNode = nodelist.item(tmp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String ispID  =  eElement.getElementsByTagName("pID")
                            .item(0).getTextContent();
                    if (userid.equals(ispID)) {
                        username = eElement.getElementsByTagName("iProfile")
                                .item(0).getTextContent();
                        password = eElement.getElementsByTagName("iPasswordCur")
                                .item(0).getTextContent();
                        cusAddressLine1 = eElement.getElementsByTagName("iAdd1")
                                .item(0).getTextContent();
                        cusAddressLine2 = eElement.getElementsByTagName("iAdd2")
                                .item(0).getTextContent();
                        cusMailName = eElement.getElementsByTagName("iMailName")
                                .item(0).getTextContent();
                        cusMailDomain = eElement.getElementsByTagName("iMailDomain")
                                .item(0).getTextContent();
                        cusTitle = eElement.getElementsByTagName("iTitle")
                                .item(0).getTextContent();
                        cusForename = eElement.getElementsByTagName("iForename")
                                .item(0).getTextContent();
                        cusSurname = eElement.getElementsByTagName("iSurname")
                                .item(0).getTextContent();
                        cusPhoneNo = eElement.getElementsByTagName("iPhoneNo")
                                .item(0).getTextContent();
                        cusCountry = eElement.getElementsByTagName("iCountry")
                                .item(0).getTextContent();
                        cusHouseNamNum = eElement.getElementsByTagName("iHouseNamNum")
                                .item(0).getTextContent();
                        cusTownCity = eElement.getElementsByTagName("iTownCity")
                                .item(0).getTextContent();
                        cusPostCode = eElement.getElementsByTagName("iPostCode")
                                .item(0).getTextContent();
                        cusPCProfile = eElement.getElementsByTagName("iProfilepcID")
                                .item(0).getTextContent();
                        break;

                    } else {
                        username = ("UserNotFound");
                        password = ("PasswordNotFound");
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}



