package com.javafortesters.createupdatetestlog;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 08/12/2014.
 * Time: 10:30
 */
public class CreateTestRunLog {
    private String path;
    public String getPath() {
        return path;
    }

    public CreateTestRunLog(String savedpath) throws IOException {

            SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_HH_mm");
            String date = sdf.format(new Date());
            String folder = ("Run_" + date);
            path = ("Q:/Testing/Results/" + folder);
            File theDir = new File(path);
            System.out.println("path " + path);
            System.out.println("dir " + theDir);

            if (!theDir.exists()) {
                System.out.println("creating directory: " + theDir);
                boolean result = false;

                try {
                    theDir.mkdir();

                } catch (SecurityException se) {
                    //handle it
                }

                File aTempFile = new File(theDir + File.separator + "TestRunLog" + ".csv");
                aTempFile.createNewFile();
                assertThat(aTempFile.isFile(), is(true));
                FileWriter writer = new FileWriter(aTempFile);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);
                print.println("test run started");
                print.close();
            }
        }
           /*if ( == "");
            {
                // Write to the file and close it.
                FileWriter writer = new FileWriter(aTempFile);
                BufferedWriter buffer = new BufferedWriter(writer);
                PrintWriter print = new PrintWriter(buffer);
                print.println("test run started");
                print.close();
            }
                writer = new FileWriter(aTempFile, true);
                buffer = new BufferedWriter(writer);
                print = new PrintWriter(buffer);
                print.println("===============================");
                print.println("===============================");
                print.close();
            }
            }*/




    public void testToWriteCloseOpenApendToAFile() throws IOException {

        // Name the directory
        String tempDirectory = System.getProperty("java.io.tmpdir");
        long randomnumber = System.currentTimeMillis();
        String s = String.valueOf(randomnumber);
        String newDirectoryStructure = tempDirectory + randomnumber + File.separator;

        // Create it and check it exists
        File aDirectory = new File(newDirectoryStructure);
        aDirectory.mkdirs();
        assertThat(aDirectory.isDirectory(), is(true));

        // Create a file of the same name as the folder and check it exists.
        File aTempFile = new File(s + ".tmp");
        aTempFile.createNewFile();
        assertThat(aTempFile.isFile(), is(true));
        // Tell me what/where it is.
        System.out.println("Check file " + aTempFile.getAbsolutePath());

        // Write to the file and close it.
        FileWriter writer = new FileWriter(aTempFile);
        BufferedWriter buffer = new BufferedWriter(writer);
        PrintWriter print = new PrintWriter(buffer);
        print.println("Append Print to Buffered Writer");
        print.close();

        // Now append to the file after it's closed.
        writer = new FileWriter(aTempFile, true);
        buffer = new BufferedWriter(writer);
        print = new PrintWriter(buffer);
        print.println("===============================");
        print.println("===============================");
        print.close();

        // So the string length is is a count of each char plus 2 for each line (hence get the line seperator.
        String lineEnd = System.lineSeparator();
        long fileLen = 93L + lineEnd.length() + lineEnd.length() + lineEnd.length();
        System.out.println(System.lineSeparator());
        System.out.println(lineEnd.length());
        System.out.println(fileLen);
        System.out.println(aTempFile.length());

        assertThat(aTempFile.length(), is(fileLen));

    }
}
