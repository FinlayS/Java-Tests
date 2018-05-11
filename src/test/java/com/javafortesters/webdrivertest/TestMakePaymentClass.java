package com.javafortesters.webdrivertest;

import com.javafortesters.domainentities.PaymentCardGet;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 18/06/2015.
 * Time: 15:30
 */
public class TestMakePaymentClass extends TestAllStoresBasket {

    public TestMakePaymentClass (String cardNumer, Boolean csrIsOn) {
        boolean newCardEntry = true;
        boolean orderComplete = false;
        int loopCount;

        PaymentCardGet card = new PaymentCardGet(cardNumer);

        // Added to control the scroll bars
        JavascriptExecutor jse = (JavascriptExecutor)driver;
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        WebDriverWait waitnew = new WebDriverWait(driver, 25);

        paymentLoop:
        for (loopCount = 0; loopCount < 15; loopCount++) {
            if (orderComplete) {
                break paymentLoop;
            }

            // If newCardEntry is true then change the payment method (only on the first attempt to pay!)
            if ((loopCount == 0) && (newCardEntry = true)) {
                if(!csrIsOn){
                WebElement changeAddPaymentMethod = driver.findElement(By.xpath("//*[@class='btn_add_payment_method']"));
                Actions builder = new Actions(driver);
                builder.moveToElement(changeAddPaymentMethod).click(changeAddPaymentMethod).perform();
                }
                try {
                    jse.executeScript("scroll(0, 350)");
                } catch (WebDriverException e) {
                }

                //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='" + card.getCardbrand() + "']")));
                WebElement selectCardType = driver.findElement(By.xpath("//input[@id='" + card.getCardbrand() + "']"));
                selectCardType.click();

                WebElement inputCardNum = driver.findElement(By.xpath("//input[@id='card']"));
                inputCardNum.sendKeys(card.getCardno());

                WebElement expireMonth = driver.findElement(By.xpath("//select[@id='expiry_month']"));
                expireMonth.sendKeys(card.getEndmonth());

                WebElement expireYear = driver.findElement(By.xpath("//select[@id='expiry_year']"));
                expireYear.sendKeys(card.getEndyear());

                WebElement updateButton = driver.findElement(By.xpath("//*[starts-with(@class, 'submit_button btn_add_card')]"));
                updateButton.click();
            }
                                /* The track my order indicates payment was excepted, if you find it, don't try and pay again */
            if (driver.findElements(By.xpath("//*[starts-with(@class, 'btn_track_order')]")).size() == 0) {
                WebElement cvcNo = driver.findElement(By.xpath("//input[starts-with(@id, 'sec14')]"));
                cvcNo.sendKeys(card.getCvcno());
                WebElement termsCheck = driver.findElement(By.id("terms_box"));
                termsCheck.click();
                WebElement payButton = driver.findElement(By.id("btn_conf_and_pay"));
                payButton.click();

                // The card type may require a response. If asked, give the defined response from 'cards.xml'
                if (card.getResponse() != null) {
                    try {
                        WebElement tmpFrame=null;
                        List<WebElement> frames = driver.findElements(By.tagName("frame"));
                        for(WebElement element:frames){
                            System.out.println(element.getAttribute("id")+"\t"+element.getAttribute("name"));
                            if(element.getAttribute("name").equals("ACSFrame")){
                                tmpFrame=element;
                            }
                        }
                        driver.switchTo().frame(tmpFrame);
                        String response = ("//input[contains(@value, " + card.getResponse() + ")]");
                        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        //File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        //FileUtils.copyFile(scrFile, new File(theDir + "\\" + isBrand + isCountry + isEnvironment + "_Payment.png"));
                        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(response)));

                        WebElement selectResponse = driver.findElement(By.xpath(response));
                        selectResponse.click();
                    } catch (TimeoutException | NoSuchFrameException e) { // You weren't asked for test response | & payment failed.
                    }
                }
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            } else {
                orderComplete = true;
            }   //break paymentLoop;
        }

        int paymentAttempts = loopCount - 1;


    }

}
