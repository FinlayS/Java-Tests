package com.javafortesters.webdrivertest;
import com.javafortesters.domainentities.*;
//import com.javafortesters.xmlgenrationclasses.generate_or03.GenerateOr03XML;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

//import com.javafortesters.domainentities.interim.retryingFindClick;

    /**
     * Created with IntelliJ IDEA 13.1.4
     * Created by: Finlay S.
     * Project: javafortesters
     * Date: 26/11/2014.
     * Time: 14:01
     */
    public class TestAllStoresBasket extends WebDriverTestBase {


        @Test
        public void main() throws IOException, ElementNotVisibleException {


            /***************************************
             * Create a directory for the run output.
             ****************************************/
            SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_HH_mm");
            String date = sdf.format(new Date());
            String folder = ("Run_" + date);
            File theDir = new File("Q:/Testing/Results/" + folder);
            if (!theDir.exists()) {
                System.out.println("creating directory: " + theDir);
                boolean result = false;

                try {
                    theDir.mkdir();
                } catch (SecurityException se) {
                    //handle it
                }
            }

            /***************************************
             * Create the log file
             ****************************************/
            File aTempFile = new File(theDir + File.separator + "TestRunLog" + date + ".csv");
            aTempFile.createNewFile();
            assertThat(aTempFile.isFile(), is(true));
            FileWriter writer = new FileWriter(aTempFile);
            BufferedWriter buffer = new BufferedWriter(writer);
            PrintWriter print = new PrintWriter(buffer);
            print.println("Brand ,Country ,Environment ,Action ,Product ,Quantity ,Was Price ," +
                    "Item Price ,Total Value ,Item % ,Shipping ,Total % ,Price Paid  ");
            print.close();
            // Added to control the scroll bars
            JavascriptExecutor jse = (JavascriptExecutor)driver;

            /****************************************
             * Read through the store list and
             * execute the test for each store.
             ****************************************/
            try {     /* got the code to read XML from here
                        http://www.studyselenium.com/2014/01/java-program-to-read-xml-file-object.html */
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
                //NodeList nodelist = doc.getElementsByTagName("EFixStoreData");
                //NodeList nodelist = doc.getElementsByTagName("SPitStoreData");



                for (int tmp = 0; tmp < nodelist.getLength(); tmp++) {
                    Node nNode = nodelist.item(tmp);

                    readIndividualStoreLoop:
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String inputStore = eElement.getAttribute("iStoreID");
                        // Enter and individual store code here otherwise run for all by TagName (NodeList above)
                        String storeToCompare = new String("");


                        if (!storeToCompare.isEmpty()) {
                            if (!inputStore.equals(storeToCompare)) {
                                break readIndividualStoreLoop;
                            }
                        }

                        /// Test begins here!!!

                        String sTrue = "true";
                        String sFalse = "false";
                        //Double wasprice ;
                        //Double nowprice ;
                        Double itemprice;
                        //Double bagprice ;
                        //Double discount ;
                        //Double shipping ;
                        //Double basketitemtotal ;
                        Double alllitems = 0.0;
                        Double additionalitems = 0.0;
                        //Double pricepaid = 0.0;
                        int productgrid = 1;
                        int column = 0;
                        //int row = 0;
                        int divCount = 0;
                        int itemCount = 0;
                        int loopCount;
                        int paymentAttempts = 0;
                        boolean thisItemInBag = false;
                        boolean anyItemInBag = false;
                        boolean csrIsOn = false;
                        boolean orderConfirmed = false;
                        String userProfileNo = "";
                        String response ="";

                        //UserGet user = new UserGet("1");


                        ArrayList<String> or03Totals = new ArrayList<>();
                        ArrayList<String> or03Items = new ArrayList<>();

                        // Get Strings from XML input.
                        String isBrand = eElement.getElementsByTagName("iBrand")
                                .item(0).getTextContent();
                        String isCountry = eElement.getElementsByTagName("iCountry")
                                .item(0).getTextContent();
                        String isCurrency = eElement.getElementsByTagName("iCurrency")
                                .item(0).getTextContent();
                        String isRegion = eElement.getElementsByTagName("iRegion")
                                .item(0).getTextContent();
                        String isRegionSelect = eElement.getElementsByTagName("iRegionSelect")
                                .item(0).getTextContent();
                        String isLanguage = eElement.getElementsByTagName("iLanguage")
                                .item(0).getTextContent();
                        String isEnvironment = eElement.getElementsByTagName("iEnvironment")
                                .item(0).getTextContent();
                        String isBaseUrl = eElement.getElementsByTagName("ibaseurl")
                                .item(0).getTextContent();
                        String isCategory = eElement.getElementsByTagName("iCategory")
                                .item(0).getTextContent();
                        String isSizeXpath = eElement.getElementsByTagName("iSizeXpath")
                                .item(0).getTextContent();
                        String isSelectXpath = eElement.getElementsByTagName("iSelectXpath")
                                .item(0).getTextContent();
                        String isSelectOr = eElement.getElementsByTagName("iSelectOr")
                                .item(0).getTextContent();
                        String isPayCardID = eElement.getElementsByTagName("iPayCardID")
                                .item(0).getTextContent();
                        String isPromoCode = eElement.getElementsByTagName("iPromoCode")
                                .item(0).getTextContent();
                        String isCSRStore = eElement.getElementsByTagName("iCSRStore")
                                .item(0).getTextContent();
                        String isArcStoreID = eElement.getElementsByTagName("iArcStoreID")
                                .item(0).getTextContent();

                        // link the card type to the brand.
                        PaymentCardGet card = new PaymentCardGet(isPayCardID);
                        //String paymentCardIDNumber = card.getCardid();


                        // Check the Store input XML to see if we are testing the CSR function.
                        // if so get the profile and url associated and open the browser.
                        if (isCSRStore.isEmpty()) {
                            driver.get(isBaseUrl);
                            userProfileNo = "1";
                        } else {
                            String CSRLogonPage = isCSRStore + "&storeId=" + isArcStoreID;
                            userProfileNo = inputStore;
                            driver.get(CSRLogonPage);
                            csrIsOn = true;
                        }

                        // Tidy and wait
                        driver.manage().deleteAllCookies();
                        WebDriverWait wait = new WebDriverWait(driver, 25);

                        // Get test profile data from input XML
                        UserGet user = new UserGet(userProfileNo);

                        if (csrIsOn){
                            WebElement csrLoginName = driver.findElement(By.id("logonId"));
                            csrLoginName.sendKeys(user.getUsername());
                            WebElement csrLoginPassword = driver.findElement(By.id("logonPassword"));
                            csrLoginPassword.sendKeys(user.getPassword());
                            WebElement csrCustomerName = driver.findElement(By.id("cscustomer"));
                            csrCustomerName.sendKeys(user.getCusPCProfile());
                            WebElement submit_csr = driver.findElement(By.id("submit"));
                            submit_csr.click();
                            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

                            // Get the response to see if we logged on correctly
                            try {
                                driver.findElement(By.id("server_message"));
                            } catch (NoSuchElementException e) {  // if successful we'd not see the server message as we'd have moved on.

                            } finally { // If we fail to log in then deal with it.
                                if (driver.findElements(By.id("server_message")).size() != 0) {
                                    //System.out.println("The tag name is: " + logOnSuccess.getText());
                                    WebElement logOnSuccess = driver.findElement(By.id("server_message"));
                                    String logonResponse = logOnSuccess.getText();
                                    Assert.assertThat(logonResponse.contains("not been found"), is(true));
                                    csrIsOn = false;
                                    System.out.println("We failed to log into CSR Tool, check password in ProfileList.XML");
                                }
                            }
                        }

                        /****************************************
                         * Check to see if we need to use
                         * the region selector.
                         ****************************************/
                        if (isRegionSelect.equals(sTrue)) {
                            WebElement RegionSelect = driver.findElement(By.xpath("//p[starts-with(@id, 'rs_alert_links')]/a"));
                            RegionSelect.click();
                            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                            WebElement region_selector = driver.findElement(By.id("rs_region_selector"));
                            //System.out.println(isRegion);
                            region_selector.sendKeys(isRegion);

                            // You might not need to select language, if it's hidden then skip it.
                            if (driver.findElement(By.id("rs_language_selector")).isDisplayed()) {
                                WebElement language_selector = driver.findElement(By.id("rs_language_selector"));
                                language_selector.sendKeys(isLanguage);
                            }
                            WebElement submit_rs = driver.findElement(By.id("rs_btn_go_1"));
                            submit_rs.click();
                            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                        }

                        /****************************************
                         * Search from the input category and buy
                         * (up to four items by changing the
                         * compare loop to value in the loop below
                         ****************************************/
                        int prodCount = 1;
                        for (int i = 0; i < prodCount; i++) { //
                            //divCount++;

                            // Increase the product grid so
                            // we buy a different item each test.
                            //row = productgrid / 4;
                            column = productgrid % 1 + 1;
                            productgrid++;


                            /**
                             * This is where to start the category/item selection check
                             **/


                            // Open the home page & add the category to build the url.
                            String searchUrl = (isBaseUrl + isCategory);
                            driver.get(searchUrl);
                            driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);

                            driver.findElement(By.xpath("//div[@class='row']/div["                                      // CE3 readiness: find and select the first available PLP item.
                                   + column + "]/a")).click();

                            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

                            /**
                             * This is where to end the category and do the item selection
                             **/


                            /**
                             * This is where to end item selection check. We're in the PDP now!
                             **/
                            thisItemInBag = false;
                            // Scroll down a wee bit in case the PDP image zoom is displayed and hiding the buttons.
                            try {
                                jse.executeScript("scroll(0, 250)");

                                // Scroll back up?
                                jse.executeScript("scroll(250, 0)");
                                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                            } catch (WebDriverException e) {
                            } // For some reason this fails only in TS ID?...because it's decommissioned!!

                            // Decide the size selector method displayed and add one of the first available sizes to the bag.
                            try {                                                                                               // One size has hidden element.
                                if (isSelectOr.equals(sFalse)) {

                                    if (driver.findElements(By.xpath(isSizeXpath)).size() != 0) {                               // Confirm the size selector is a grid.

                                        driver.findElement(By.xpath(isSizeXpath)).click();                                      // ID the first size available and select it.
                                        new Select(driver.findElement(By.id("product_quantity_full"))).selectByValue("1");      // select one to buy
                                    } else {

                                        if (driver.findElement(By.xpath(
                                                "//input/id[contains(@title," + isSelectXpath + ")]")).isDisplayed()) {
                                            WebElement menuItem = driver.findElement(By.xpath(
                                                    //"//select/option[contains(@title," + isSelectXpath + ")]"));
                                                    "//input/id[contains(@title," + isSelectXpath + ")]"));
                                            menuItem.click();
                                        }
                                    }
                                } else {                                                                                        // The size selector is a dropdown.
                                    if (isSelectOr.equals(sTrue)) {                                                             // find and select the first available size.
                                        WebElement menuItem = driver.findElement(By.xpath(isSelectXpath +
                                                "[contains(@title," + isSizeXpath + ")]"));
                                        if (menuItem.isDisplayed()) {
                                            menuItem.click();
                                        } else {
                                            driver.findElement(By.id("inp_search_text")).click();                               // It may be the load of main image hides the dropdown.
                                            menuItem.click();                                                                   // so we've clicked elsewhere to hide it, now try again.
                                        }
                                    }
                                }
                            } catch (ElementNotVisibleException e) {
                                System.out.println("Must be a one size sku?");
                            } catch (NoSuchElementException e) {
                                System.out.println("Item was out of stock?");
                                if (prodCount < 4) { // Otherwise we just loop forever.
                                    prodCount++;
                                } else {
                                    prodCount = 0;
                                    divCount++;
                                }
                            }

                            if (driver.findElements(By.id("btn_add_2_shop_cart")).size() != 0) {                            // item in stock
                                driver.findElement(By.id("btn_add_2_shop_cart")).click();                                   // Add to bag
                                thisItemInBag = true;
                                anyItemInBag = true;
                            }

                            /****************************************
                             * PDP - Page details:
                             * Get all the prices and totals and
                             * write them to the log.
                             ****************************************/
                            boolean doIwantToRecordIfDebugging = false;
                            if (doIwantToRecordIfDebugging) {

                                if (thisItemInBag) {
                                    // Product prices
                                    List<WebElement> WasLinks =                                                                     // Save the prices related to the item.
                                            driver.findElements(By.xpath("//*[starts-with(@class, 'was_price')]/span"));            // Get Was, Now and current item prices and add to the log.
                                    PriceByXpathGet WasPrice = new PriceByXpathGet(WasLinks, isCurrency);
                                    Double wasprice = WasPrice.getReturnedprice();

                                    List<WebElement> NowLinks =                                                                     // Get now price and if not found get product price.
                                            driver.findElements(By.xpath("//*[starts-with(@class, 'now_price')]/span"));
                                    PriceByXpathGet NowPrice = new PriceByXpathGet(NowLinks, isCurrency);
                                    Double nowprice = NowPrice.getReturnedprice();

                                    if (nowprice == 0.0) {
                                        List<WebElement> ProdLinks =
                                                driver.findElements(By.xpath("//*[starts-with(@class, 'product_price')]/span"));
                                        //*[starts-with(@class, 'btn active')]
                                        PriceByXpathGet ProdPrice = new PriceByXpathGet(ProdLinks, isCurrency);
                                        itemprice = ProdPrice.getReturnedprice();
                                    } else {
                                        itemprice = nowprice;
                                    }

                                    // Did the product add to bag successfully?
                                    if (driver.findElements(By.id("ajax_success")).size() != 0) {                                   //

                                        // Get the mini bag value.
                                        List<WebElement> bagSumLinks =
                                                //driver.findElements(By.xpath("//*[starts-with(@id, 'wrapper_basket_summary')]/a/span[3]"));
                                                driver.findElements(By.xpath("//*[starts-with(@id, 'header_basket_value')]"));

                                        PriceByXpathGet bagSumPrice = new PriceByXpathGet(bagSumLinks, isCurrency);
                                        Double bagprice = bagSumPrice.getReturnedprice();

                                        // Get the sku of the item added.
                                        List<WebElement> skuLinks =
                                                driver.findElements(By.xpath("//*[@class='product_code']/span"));
                                        SkuByXpath skuID = new SkuByXpath(skuLinks);
                                        String Sku = skuID.getReturnedSku();

                                        // Add all the details to the log
                                        alllitems = alllitems + itemprice;
                                        writer = new FileWriter(aTempFile, true);
                                        buffer = new BufferedWriter(writer);
                                        print = new PrintWriter(buffer);
                                        print.println(isBrand + "," + isCountry + "," + isEnvironment + "," + "Added to basket" +
                                                "," + Sku + "," + 1 + "," + wasprice + "," + itemprice + "," + +bagprice + "," + ",");
                                        print.close();
                                    }
                                }
                            }
                            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        }

                        /****************************************
                         * Logon page via PDP > Basket link:
                         * Get all the prices and totals and
                         * write them to the log.
                         *
                         ****************************************/
                        // Click the basket to start the logon process.
                        String MiniBagLinkSum = "//*[@id='wrapper_basket_summary']/a";
                        String MiniBagLinkItm = "//*[@id='header_basket_items']";
                        String MiniBagLinkVal = "//*[@id='header_basket_value']";

                        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MiniBagLinkSum)));


                        /** and below should cope where the checkout button has been disabled/hidden etc etc (on test systems only? **/
                    try {                                                                            // This is the mini bag link.
                        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MiniBagLinkSum));
                        WebElement goBasketSum = driver.findElement(By.xpath(MiniBagLinkSum));
                        goBasketSum.click();
                    } catch ( WebDriverException  e){   //CSR tool "ordering for" overlay hides the normal basket link.
                        WebElement goBasketVal = driver.findElement(By.xpath(MiniBagLinkVal));
                        try {
                            goBasketVal.click();
                        } catch (ElementNotVisibleException e1){
                            WebElement goBasketItm = driver.findElement(By.xpath(MiniBagLinkItm));
                            goBasketItm.click();
                        }
                    }
                        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

                        // This is login.
                        String bottomcheckoutbuttonpath = "//*[@id='checkout_submit_bottom']";
                        /**
                         * below I need to add a timoutexception for the checkout button or empty bag check.
                         * If the prodCount is maxed and this happens then all items we attempted to add
                         * were OOS or there as a failure adding to bag (wrong SelectOr?)
                         * It's only happened once but if we were to schedule.......?
                         * **/
                        if (anyItemInBag) {
                            System.out.println("What's in the bag populated flag? " + anyItemInBag);
                            try {
                                //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(bottomcheckoutbuttonpath)));
                                driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
                            } catch (TimeoutException e){
                                driver.navigate().refresh();
                                ///wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(bottomcheckoutbuttonpath)));
                            }


                            WebElement botomcheckoutbutton = driver.findElement(By.xpath(bottomcheckoutbuttonpath));
                            botomcheckoutbutton.click();
                            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                            if (!csrIsOn) {
                                WebElement mailID = driver.findElement(By.id("login_email"));
                                mailID.sendKeys(user.getUsername());
                                WebElement pwd = driver.findElement(By.id("password"));
                                pwd.sendKeys(user.getPassword());
                                WebElement submitLogin = driver.findElement(By.id("login_submit"));
                                submitLogin.click();
                                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                                /****************************************
                                * Basket summary check:
                                * Get to the payment page then back to
                                * basket summary, this way we pick up
                                * any additional 'previous basket' items.
                                ****************************************/
                                /* But first, you may not be at the payment page because you have items in your basket from a previous visit
                                Test for the presence of the CVC input box, if not found, click 'checkout' */
                                String finalCheckOutButtonpath = "//*[starts-with(@class, 'btn_checkout')]";
                                try {
                                    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(finalCheckOutButtonpath)));
                                    driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
                                } catch (TimeoutException e){
                                    //driver.navigate().refresh();
                                }

                                    if (driver.findElements(By.xpath("//input[starts-with(@id, 'sec14')]")).size() == 0) {              // CVC input not found.

                                        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(finalCheckOutButtonpath)));
                                        WebElement primaryCheckout = driver.findElement(By.xpath(finalCheckOutButtonpath));
                                        primaryCheckout.click();
                                        // Click Checkout.
                                    }
                            }

                            /* Because of this above scenario, we need to scrape the basket summary after login to get any
                            additional items so now we've made sure we're on payment then > cont shop > checkout again.  */
                            // But no need of we are in CSR tool
                            System.out.println("Line 509 "+csrIsOn);

                            String continueShoppingButton = "//*[starts-with(@class, 'btn_continue_shopping')]";
                            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(continueShoppingButton)));

                            WebElement contshopbutton = driver.findElement(By.xpath(continueShoppingButton));
                            //retryingFindClick test1 = new retryingFindClick();
                            //public boolean retryingFindClick2(By contshopbutton) {
                                boolean result = false;
                                int attempts = 0;
                                while(attempts < 3) {
                                    try {
                                        driver.findElement((By.xpath(continueShoppingButton))).click();
                                        result = true;
                                        break;
                                    } catch(StaleElementReferenceException e) {
                                    }
                                    attempts++;
                                }
                                                            //
                            try {                                                                            // This is the mini bag link.
                                //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MiniBagLinkSum)));
                                WebElement goBasketSum = driver.findElement(By.xpath(MiniBagLinkSum));
                                goBasketSum.click();
                            } catch ( WebDriverException  e){   //CSR tool "ordering for" overlay hides the normal basket link.
                                WebElement goBasketVal = driver.findElement(By.xpath(MiniBagLinkVal));
                                try {
                                    goBasketVal.click();
                                } catch (ElementNotVisibleException e1){
                                    WebElement goBasketItm = driver.findElement(By.xpath(MiniBagLinkItm));
                                    goBasketItm.click();
                                }
                            }



                            /****************************************
                            * Basket summary confirmed:
                            * 1. Apply a discount code if defined.
                            * 2. Grab discount values.
                            * 3. Grab all other values.
                            * 4. Print all to the log.
                            ****************************************/
                            /*****************************************
                            *  Apply a discount code if defined.
                            ****************************************/
                            boolean applyDicountIfDefined = false;
                            if (applyDicountIfDefined) {
                                if (!isPromoCode.isEmpty()) {
                                    String promoCodeInputField = "promo_code_no";
                                    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(promoCodeInputField)));
                                    WebElement discountcode = driver.findElement(By.id(promoCodeInputField));
                                    discountcode.sendKeys(isPromoCode);
                                    WebElement submitCode = driver.findElement(By.id("chk_verify_code_btn"));
                                    submitCode.click();
                                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                                }
                            }
                            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


                        } /// **** End of any item in bag


                        /*****************************************
                         *  Grab discount values.
                         ****************************************/
                        List<WebElement> discountLinks =
                                driver.findElements(By.xpath("//*[@class='shopping_bag_discounts cf']/span"));                // The skus.
                        DiscountByXPath BasketSumDiscounts = new DiscountByXPath(discountLinks);
                        Double allItemsDiscount = BasketSumDiscounts.getAllItemsDiscount();


                        /*****************************************
                         *  Grab all other values.
                         ****************************************/
                        List<WebElement> ShipLinks =
                                driver.findElements(By.xpath("//select/option[@selected='selected']/span/span"));
                        PriceByXpathGet ShipPrice = new PriceByXpathGet(ShipLinks, isCurrency);
                        Double shipping = ShipPrice.getReturnedprice();

                        // get the items, prices, discounts & quantities.
                        List<WebElement> priceLinks =
                                driver.findElements(By.xpath(
                                        "//*[(@class='item_price product_price') " +                    // The listed product unit price.
                                                "or (@class='product_price') " +                        // Bloody BHS would be different! (
                                                "or (@class='now_price product_price') " +              // The now price if reduced (replaces above).
                                                "or (@class='item_total')]"));                          // The quantity x price total.
                        List<WebElement> qtyLinks =
                                driver.findElements(By.xpath("//*[@class='item_quantity']"));           // The quantities.
                        List<WebElement> skuLinks =
                                driver.findElements(By.xpath("//*[@class='item_sku']"));                // The skus.
                        ShopBagSumAllDetailsByXpath BasketSumDetails = new ShopBagSumAllDetailsByXpath(priceLinks, qtyLinks, skuLinks, isCurrency);
                        Double SumTotal = BasketSumDetails.getValueSummedUnits();
                        Double SumDiscount = BasketSumDetails.getValueAllDiscount();
                        Double SumUnitValues = BasketSumDetails.getValueIndividualUnit();

                        // Get my sku and currency from the page source as it in the JS output string.
                        String WholeSource = (driver.getPageSource());
                        GetAllBrandSkusAndCurrency BrandSkuAndCurrency = new GetAllBrandSkusAndCurrency(WholeSource);

                        /*****************************************
                         *  Print all to the log.
                         ****************************************/
                        // Print to the log for each item in the basket
                        writer = new FileWriter(aTempFile, true);
                        buffer = new BufferedWriter(writer);
                        print = new PrintWriter(buffer);
                        //print.println();
                        Double TotalUnitValue = 0.0;
                        for (int i = 0; i < BasketSumDetails.getSk().size(); i++) {
                            String Sku = BasketSumDetails.getSk().get(i);
                            String BrandSku = BrandSkuAndCurrency.getBs().get(i);
                            Double UnitPrice = BasketSumDetails.getUp().get(i);
                            Double Qty = BasketSumDetails.getQt().get(i);
                            Double Total = BasketSumDetails.getTp().get(i);
                            Double Discount = BasketSumDetails.getDc().get(i);
                            Double UnitValue = BasketSumDetails.getQt().get(i) * BasketSumDetails.getUp().get(i);
                            TotalUnitValue = TotalUnitValue + UnitValue;
                            or03Items.add("######");
                            or03Items.add(BrandSku);
                            or03Items.add(Qty.toString());
                            or03Items.add(UnitPrice.toString());
                            itemCount = itemCount + 1;

                            print.println(isBrand + "," + isCountry + "," + isEnvironment + "," + "Items of basket sum" +
                                    "," + Sku + "," + Qty + "," + "," + UnitPrice + "," + Total + "," + Discount + ","
                                    + "," + "," + ",");
                        }

                        // Write the discounts to the log.
                        for (int i = 0; i < BasketSumDiscounts.getDd().size(); i++) {
                            String Description = BasketSumDiscounts.getDd().get(i);
                            Double Total = BasketSumDiscounts.getDv().get(i);

                            print.println(isBrand + "," + isCountry + "," + isEnvironment + "," + "Basket Discounts" +
                                    "," + Description + "," + "," + "," + "," + "," + ","
                                    + "," + Total + "," + ",");
                        }

                        // Add the totals
                        print.println(isBrand + "," + isCountry + "," + isEnvironment + "," + "Totals of basket sum" +
                                "," + "," + "," + "," + TotalUnitValue + "," + SumTotal + "," + SumDiscount + "," + shipping
                                + "," + "," + ",");
                        print.close();

                        /*****************************************
                         * Do the checkout, capture the details
                         * to the output log. Do a screenshot.
                         ****************************************/

                        //  so now we have included a test for the CSR application, things are a bit different here!
                        if (csrIsOn){

                            //Do the basket first
                            WebElement botomcheckoutbutton3 = driver.findElement(By.xpath("//*[@id='checkout_submit_bottom']"));
                            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='checkout_submit_bottom']")));
                            try {
                                jse.executeScript("scroll(0, 350)");
                            } catch (WebDriverException e) {
                            }
                            botomcheckoutbutton3.click();
                            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                            //Build a random email address from input parameters.
                            //System.out.println("Line 424 "+csrIsOn);
                            String shipMailNam = (user.getCusMailName());
                            String shipMailDom = (user.getCusMailDomain());

                            long randomnumber = System.currentTimeMillis();
                            String shipMailNumber = String.valueOf(randomnumber);
                            String shipMailAddress = shipMailNam + shipMailNumber + shipMailDom;

                            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("shipping_email1")));
                            WebElement shippingMailID = driver.findElement(By.id("shipping_email1"));
                            shippingMailID.sendKeys(shipMailAddress);
                            System.out.println(shipMailAddress);

                            //Title
                            WebElement cusTitle = driver.findElement(By.id("address_title"));
                            cusTitle.sendKeys(user.getCusTitle());
                            //Forename
                            WebElement cusForename = driver.findElement(By.id("address_first_name"));
                            cusForename.sendKeys(user.getCusForename());
                            //Surname
                            WebElement cusSurname = driver.findElement(By.id("address_surname"));
                            cusSurname.sendKeys(user.getCusSurname());
                            //PhoneNo
                            WebElement cusPhoneNo = driver.findElement(By.id("address_main_telephone"));
                            cusPhoneNo.sendKeys(user.getCusPhoneNo());
                            //Country
                            WebElement cusCountry = driver.findElement(By.id("address_country"));
                            cusCountry.sendKeys(user.getCusCountry());
                            //House Name or Number
                            WebElement cusHouse = driver.findElement(By.id("address_house_number"));
                            cusHouse.sendKeys(user.getCusHouseNamNum());
                            //Postcode
                            WebElement cusPostcode = driver.findElement(By.id("address_postcode"));
                            cusPostcode.sendKeys(user.getCusPostCode());

                            // Scroll down a wee bit in as Chrome cannot find the button otherwise.
                            try {
                                jse.executeScript("scroll(0, 250)");
                                // Scroll back up?
                                //jse.executeScript("scroll(250, 0)");
                            } catch (WebDriverException e) {
                            }
                            System.out.println("Line 465 "+csrIsOn);
                            //QAS Lookup
                            WebElement postCodeLookUp = driver.findElement(By.id("chk_find_address_btn"));
                            postCodeLookUp.click();

                            String addressIsValid = "//*[starts-with(@class, 'text address_town valid')]";

                            WebElement proceedToPay = driver.findElement(By.xpath("//*[starts-with(@class, 'btn_proceed')]"));
                            driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);

                            // Scroll down a wee bit in as Chrome cannot find the button otherwise.
                            try {
                                jse.executeScript("scroll(0, 850)");
                                // Scroll back up?
                                //jse.executeScript("scroll(250, 0)");
                            } catch (WebDriverException e) {
                            }

                            proceedToPay.click();

                        }

                        if(!csrIsOn) {

                            //Now we can checkout proper.
                            WebElement botomcheckoutbutton2 = driver.findElement(By.xpath("//*[@id='checkout_submit_bottom']"));
                            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='checkout_submit_bottom']")));
                            try {
                                jse.executeScript("scroll(0, 350)");
                            } catch (WebDriverException e) {
                            }
                            botomcheckoutbutton2.click();

                        }
                            //self.browser.execute_script("arguments[0].click();", botomcheckoutbutton2);

                            /* Now we should be on the payment page so enter details and submit*/
                        boolean newCardEntry = true;
                        boolean orderComplete = false;
                        //int loopCount;

                        paymentLoop:
                        for (loopCount = 0; loopCount < 15; loopCount++) {
                            if (orderComplete) {
                                break paymentLoop;
                            }

                            // If newCardEntry is true then change the payment method (only on the first attempt to pay!)
                            if ((loopCount == 0) && (newCardEntry = true)) {
                                if(!csrIsOn) {
                                    WebElement changeAddPaymentMethod = driver.findElement(By.xpath("//*[@class='btn_add_payment_method']"));
                                    try {
                                        jse.executeScript("scroll(0, 1550)"); //Scroll a wee bit
                                    } catch (NoSuchElementException e) {
                                        jse.executeScript("scroll(0, 5550)"); //Scroll a muckle bit
                                    }
                                    Actions builder = new Actions(driver);
                                    builder.moveToElement(changeAddPaymentMethod).click(changeAddPaymentMethod).perform();
                                }
                                //try {
                                //    jse.executeScript("scroll(0, 1550)");
                                //} catch (WebDriverException e) {
                                //}

                                //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='" + card.getCardbrand() + "']")));
                                WebElement selectCardType = driver.findElement(By.xpath("//input[@id='" + card.getCardbrand() + "']"));
                                try {
                                    jse.executeScript("scroll(0, 1550)");
                                } catch (WebDriverException e) {
                                }
                                selectCardType.click();

                                WebElement inputCardNum = driver.findElement(By.xpath("//input[@id='card']"));
                                inputCardNum.sendKeys(card.getCardno());

                                WebElement expireMonth = driver.findElement(By.xpath("//select[@id='expiry_month']"));
                                expireMonth.sendKeys(card.getEndmonth());

                                WebElement expireYear = driver.findElement(By.xpath("//select[@id='expiry_year']"));
                                expireYear.sendKeys(card.getEndyear());

                                if(!csrIsOn) {
                                    WebElement updateButton = driver.findElement(By.xpath("//*[starts-with(@class, 'submit_button btn_add_card')]"));
                                    updateButton.click();
                                }
                            }
                                /* The track my order indicates payment was excepted, if you find it, don't try and pay again */

                            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

                            if (driver.findElements(By.xpath("//*[starts-with(@class, 'btn_track_order')]")).size() == 0) {
                                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                                WebElement cvcNo = driver.findElement(By.xpath("//input[starts-with(@id, 'sec14')]"));
                                cvcNo.sendKeys(card.getCvcno());
                                WebElement termsCheck = driver.findElement(By.id("terms_box"));
                                termsCheck.click();

                                if(csrIsOn) { // This might be different on different environments, have had to change while moving from PP to Stage
                                    // WebElement updateButton = driver.findElement(By.xpath("//*[starts-with(@class, 'submit_button btn_add_card')]"));
                                    WebElement payButton = driver.findElement(By.xpath("//*[starts-with(@class, 'btn_confirm_and_pay')]"));
                                    payButton.click();
                                    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                                }else {

                                    WebElement payButton2 = driver.findElement(By.id("btn_conf_and_pay"));
                                    payButton2.click();

                                    // The card type may require a response. If asked, give the defined response from 'cards.xml'
                                    System.out.println("Card Response: " +card.getResponse());
                                    //String reponse = new String
                                    if (!card.getResponse().isEmpty()) {

                                        try {
                                            WebElement tmpFrame = null;
                                            List<WebElement> frames = driver.findElements(By.tagName("frame"));
                                            for (WebElement element : frames) {
                                                System.out.println(element.getAttribute("id") + " " + element.getAttribute("name"));
                                                if (element.getAttribute("name").equals("ACSFrame")) {
                                                    tmpFrame = element;
                                                }
                                            }
                                            driver.switchTo().frame(tmpFrame);
                                            if (card.getResponse().equals("3DS")){
                                                response = ("//input[contains(@value, " + card.getAuthreply() + ")]");
                                            } else {
                                                response = ("//input[contains(@value, " + card.getResponse() + ")]");
                                            }
                                            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                                            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                                            FileUtils.copyFile(scrFile, new File(theDir + "\\" + isBrand + isCountry + isEnvironment + "_Payment.png"));
                                            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(response)));

                                            WebElement selectResponse = driver.findElement(By.xpath(response));
                                            selectResponse.click();
                                            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                                        } catch (TimeoutException | NoSuchFrameException e) { // You weren't asked for test response | & payment failed.
                                        }
                                    }
                                }
                                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                            } else {
                                orderComplete = true;
                            }   //break paymentLoop;
                        }
                            paymentAttempts = loopCount - 1;
                        //}

                        /// after here

                        // Total of items
                        List<WebElement> ConfSubTot =
                                driver.findElements(By.xpath("//*[@class='shopping_bag_subtotal cf']/span[2]"));
                        PriceByXpathGet SubTotPrice = new PriceByXpathGet(ConfSubTot, isCurrency);
                        Double subTotPrice = SubTotPrice.getReturnedprice();

                        // Total of discount
                        List<WebElement> ConfDiscTot =
                                driver.findElements(By.xpath("//*[@class='shopping_bag_discounts cf']/span[2]"));
                        PriceByXpathGet DiscTotPrice = new PriceByXpathGet(ConfDiscTot, isCurrency);
                        Double discTotPrice = DiscTotPrice.getReturnedprice();
                        discTotPrice = discTotPrice * -1;

                        // Total of shipping
                        List<WebElement> ConfShipTot =
                                driver.findElements(By.xpath("//*[@class='shopping_bag_delivery cf']/span[2]"));
                        PriceByXpathGet ShipTotPrice = new PriceByXpathGet(ConfShipTot, isCurrency);
                        Double shipTotPrice = ShipTotPrice.getReturnedprice();

                        // Total of payment
                        List<WebElement> ConfPaidTot =
                                driver.findElements(By.xpath("//*[@class='shopping_bag_total cf']/span[2]"));
                        PriceByXpathGet PaidTotPrice = new PriceByXpathGet(ConfPaidTot, isCurrency);
                        Double paidTotPrice = PaidTotPrice.getReturnedprice();

                        // order number
                        List<WebElement> ConfOrderNo =
                                driver.findElements(By.xpath("//*[@id='confirmed_order_id']"));
                        NumberByXpathGet OrderNumber = new NumberByXpathGet(ConfOrderNo);
                        int orderNumber = OrderNumber.getReturnednumber();

                        or03Totals.add(Integer.toString(OrderNumber.getReturnednumber()));
                        or03Totals.add(BrandSkuAndCurrency.getCurreny());
                        or03Totals.add(subTotPrice.toString());
                        or03Totals.add(shipTotPrice.toString());
                        or03Totals.add(paidTotPrice.toString());

                        // Print the basket
                        writer = new FileWriter(aTempFile, true);
                        buffer = new BufferedWriter(writer);
                        print = new PrintWriter(buffer);
                        print.println(isBrand + "," + isCountry + "," + isEnvironment + "," + "Order Confirmation" +
                                "," + orderNumber + "," + "," + "," + subTotPrice + "," + "," + "," + shipTotPrice
                                + "," + discTotPrice + "," + paidTotPrice + ",");
                        print.println(isBrand + "," + isCountry + "," + isEnvironment + "," + card.getCardbrand() + " Payment Attempts" +
                                "," + "'" + card.getCardno() + "," + paymentAttempts + "," + "," + "," + "," + "," +
                                "," + "," + ",");
                        //print.println();
                        print.println();
                        print.close();

                        //GenerateOr03XML.main(or03Totals, or03Items, BasketSumDetails.getSk().size(), folder);

                        // Print a screenshot of the final confirmation page.
                        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        FileUtils.copyFile(scrFile, new File(theDir + "\\" + isBrand + isCountry + isEnvironment + ".png"));
                        //driver.close();
                        //Test Ends here!!!
                        //}
                        }
                    }
//                }
            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace();
            }
        }   // Paste here:
    }
