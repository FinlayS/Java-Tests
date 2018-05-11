package com.javafortesters.webdrivertest;

import org.openqa.selenium.ElementNotVisibleException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 16/01/2015.
 * Time: 11:07
 */
public class CreateAndWriteToLog  {


    public static  void CreateLog()
            throws IOException, ElementNotVisibleException {
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
    }

}
