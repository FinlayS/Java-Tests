package com.javafortesters.xmlgenrationclasses;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WriteXMLFileNew {

    public static void main(List<String> header, List<String> items, Integer itemCount, String folder) {

        // Get the current date/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");
        String nowDate =sdf.format(new Date());

        ///Calculate a ship date (2 days from current?)
        Date date = new Date();
        long twoDaysFromNowTime = date.getTime();
        twoDaysFromNowTime = twoDaysFromNowTime +
                (1000 * 60 * 60 * 24 * 2);
        Date twoDaysFromNow = new Date(twoDaysFromNowTime);
        String twoDate =sdf.format(twoDaysFromNow);

        // Create a random number for Tracking reference
        long randomnumber = System.currentTimeMillis();
        String trkRef = String.valueOf(randomnumber);
        trkRef = ("TRKNO" +trkRef);

        for(int i = 0; i < header.size(); i++) {
            System.out.println("itemCount from XML " +itemCount);
        }

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements **FIXED VALUE**
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Update_WCS_OrderStatus");
            doc.appendChild(rootElement);

            // Control **CONTROL NODE**
            Element control = doc.createElement("ControlArea");
            rootElement.appendChild(control);

            // verb **FIXED VALUE**
            Element verb = doc.createElement("Verb");
            verb.appendChild(doc.createTextNode("Update"));
            control.appendChild(verb);

            // noun **FIXED VALUE**
            Element noun = doc.createElement("Noun");
            noun.appendChild(doc.createTextNode("WCS_OrderStatus"));
            control.appendChild(noun);

            // DataArea **CONTROL NODE**
            Element dataarea = doc.createElement("DataArea");
            dataarea.appendChild(doc.createTextNode(""));
            rootElement.appendChild(dataarea);

            // Order Status **CONTROL NODE**
            Element orderstatus = doc.createElement("OrderStatus");
            orderstatus.appendChild(doc.createTextNode(""));
            dataarea.appendChild(orderstatus);

//** Order Status Header elements **/
            // Order Status Header **CONTROL NODE**
            Element orderstatusheader = doc.createElement("OrderStatusHeader");
            orderstatusheader.appendChild(doc.createTextNode(""));
            orderstatus.appendChild(orderstatusheader);

            /**     Order Number **DYNAMIC VALUE**
            **/
            Element ordernumber = doc.createElement("OrderNumber");
            ordernumber.appendChild(doc.createTextNode(header.get(0)));
            orderstatusheader.appendChild(ordernumber);
            ordernumber.setAttribute("type", "ByWCS");

            /**     Total Header Pricing
             * (Currency) **DYNAMIC VALUES**
            **/
            Element totalpriceinfoheader = doc.createElement("TotalPriceInfo");
            orderstatusheader.appendChild(totalpriceinfoheader);
            totalpriceinfoheader.setAttribute("currency", (header.get(1)));

            /**     Total Net Price **DYNAMIC VALUES**
             **/
            Element totalnetpriceheader = doc.createElement("TotalNetPrice");
            totalnetpriceheader.appendChild(doc.createTextNode(header.get(2)));
            totalpriceinfoheader.appendChild(totalnetpriceheader);

            /**     Total Ship Price **DYNAMIC VALUES**
             **/
            Element totalshippingpriceheader = doc.createElement("TotalShippingPrice");
            totalshippingpriceheader.appendChild(doc.createTextNode(header.get(3)));
            totalpriceinfoheader.appendChild(totalshippingpriceheader);

            /**     Total Selling Price **DYNAMIC VALUES**
             **/
            Element totalsellingpriceheader = doc.createElement("TotalSellingPrice");
            totalsellingpriceheader.appendChild(doc.createTextNode(header.get(4)));            totalpriceinfoheader.appendChild(totalsellingpriceheader);

            // Order Status **FIXED VALUE**
            Element headerstatus = doc.createElement("Status");
            headerstatus.appendChild(doc.createTextNode("S"));
            orderstatusheader.appendChild(headerstatus);

            // Order Date **RUNTIME VARIABLE**
            Element placeddate = doc.createElement("PlacedDate");
            placeddate.appendChild(doc.createTextNode(nowDate));
            orderstatusheader.appendChild(placeddate);

            //  The 4 Blank customer fields ** FIXED **/
            Element cf1 = doc.createElement("CustomerField");
            orderstatusheader.appendChild(cf1);
            Element cf2 = doc.createElement("CustomerField");
            orderstatusheader.appendChild(cf2);
            Element cf3 = doc.createElement("CustomerField");
            orderstatusheader.appendChild(cf3);
            Element cf4 = doc.createElement("CustomerField");
            orderstatusheader.appendChild(cf4);

//** Order Status Item elements **/
            int itemArrayCount = 0;
            for (int i = 0; i < itemCount; i++) {
                // Order Status Item **CONTROL NODE**
                Element orderstatusitem = doc.createElement("OrderStatusItem");
                orderstatusitem.appendChild(doc.createTextNode(""));
                orderstatus.appendChild(orderstatusitem);

                // Item Number WCS Value that cannot be got at. Fixed to '9999999' for now.
                Element itemnumber = doc.createElement("ItemNumber");
                itemnumber.appendChild(doc.createTextNode(items.get(0 + itemArrayCount)));
                orderstatusitem.appendChild(itemnumber);
                itemnumber.setAttribute("type", "ByWCS");

                /**     Product Number By Merchant (SKU) **DYNAMIC VALUES**
                 **/
                Element productnumberbymerchant = doc.createElement("ProductNumberByMerchant");
                productnumberbymerchant.appendChild(doc.createTextNode(items.get(1 + itemArrayCount)));
                orderstatusitem.appendChild(productnumberbymerchant);

                // Quantity Info **CONTROL NODE**
                Element quantityinfo = doc.createElement("QuantityInfo");
                orderstatusitem.appendChild(quantityinfo);

                /**     Shipped Quantity **DYNAMIC VALUES**
                 **/
                Element shippedquantity = doc.createElement("ShippedQuantity");
                String itemQty = String.valueOf(items.get(2 + itemArrayCount).split("\\.")[0]);
                shippedquantity.appendChild(doc.createTextNode(itemQty));
                quantityinfo.appendChild(shippedquantity);

                /**     Total Item Pricing
                 * (Currency) **DYNAMIC VALUE**
                 **/
                Element totalpriceinfoitem = doc.createElement("TotalPriceInfo");
                orderstatusitem.appendChild(totalpriceinfoitem);
                totalpriceinfoitem.setAttribute("currency", (header.get(1 )));

                /** Item Price **DYNAMIC VALUE**
                 **/
                Element totalnetpriceitem = doc.createElement("TotalNetPrice");
                totalnetpriceitem.appendChild(doc.createTextNode(items.get(3 + itemArrayCount)));
                totalpriceinfoitem.appendChild(totalnetpriceitem);

                // Shipping Price **FIXED VALUE**
                Element totalshippingpriceitem = doc.createElement("TotalShippingPrice");
                totalshippingpriceitem.appendChild(doc.createTextNode("0.00"));
                totalpriceinfoitem.appendChild(totalshippingpriceitem);

                /** Item Price **DYNAMIC VALUE**
                 **/
                Element totalsellingpriceitem = doc.createElement("TotalSellingPrice");
                totalsellingpriceitem.appendChild(doc.createTextNode(items.get(3 + itemArrayCount)));
                totalpriceinfoitem.appendChild(totalsellingpriceitem);

                // Item status **FIXED VALUE**
                Element itemstatus = doc.createElement("Status");
                itemstatus.appendChild(doc.createTextNode("S"));
                orderstatusitem.appendChild(itemstatus);

                // Shipping Info **CONTROL NODE**
                Element shippinginfo = doc.createElement("ShippingInfo");
                orderstatusitem.appendChild(shippinginfo);

                // Ship Date **RUN TIME VARIABLE**
                Element shippeddate = doc.createElement("ActualShipDate");
                shippeddate.appendChild(doc.createTextNode(twoDate));
                shippinginfo.appendChild(shippeddate);

                // Ship Date **RUN TIME VARIABLE**
                Element trackingreference = doc.createElement("TrackingReference");
                trackingreference.appendChild(doc.createTextNode(trkRef));
                orderstatusitem.appendChild(trackingreference);

                // Carrier **FIXED VALUE**
                Element carreier = doc.createElement("Carrier");
                carreier.appendChild(doc.createTextNode("PNT"));
                orderstatusitem.appendChild(carreier);
                itemArrayCount = (i+1) * 4;
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "Update_WCS_OrderStatus_30.dtd");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "7");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Q:/Testing/Results/" +folder+ "/OR03_"+header.get(0) +".xml"));

            // Output to console for testing
            //StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}



