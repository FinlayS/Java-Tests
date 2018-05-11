package com.javafortesters.createupdatetestlog;

import java.io.*;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 08/12/2014.
 * Time: 13:43
 */
public class AppendTestRunLog {
    private String path;
    //public String getPath() {
    //    return path;
    //}

    public AppendTestRunLog(String savedpath, String printstring) throws IOException {
        String aTempFile = (savedpath + File.separator + "TestRunLog" + ".csv");
        //aTempFile.createNewFile();
        //assertThat(aTempFile.isFile(), is(true));
        FileWriter writer = new FileWriter(aTempFile);
        BufferedWriter buffer = new BufferedWriter(writer);
        PrintWriter print = new PrintWriter(buffer);

        writer = new FileWriter(aTempFile, true);
        buffer = new BufferedWriter(writer);
        print = new PrintWriter(buffer);
        print.println(printstring);
        print.close();
    }

}
