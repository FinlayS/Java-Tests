package com.javafortesters.domainentities;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

//import org.junit.Before;

public abstract class WebDriverTestBase {

    protected WebDriver driver;

//    @Before

    public void setUp(){
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        System.setProperty("webdriver.chrome.driver", "chromedriver");
        //System.setProperty("webdriver.chrome.driver", "Users/simpsof/Desktop/chromedriver");
        //System.setProperty("webdriver.chrome.logfile", "Q/TEsting/blah/chromedriver.log");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        capabilities.setCapability("chrome.binary", "chromedriver");
        //capabilities.setCapability("chrome.binary", "Users/simpsof/Desktop/chromedriver");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        capabilities.setCapability("enablePersitentHover", false);
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    //@After
   public void tearDown() {
        //driver.close();
    }
}