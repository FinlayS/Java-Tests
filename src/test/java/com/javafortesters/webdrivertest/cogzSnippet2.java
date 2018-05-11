package com.javafortesters.webdrivertest;

import org.junit.Test;

public class cogzSnippet2 {


    java.util.Date now = new java.util.Date();
    java.text.DateFormat dateformat = new java.text.SimpleDateFormat("dd-MM-yy.HH.mm.ss");
    String strDate=dateformat.format(now);


    java.util.Random rand = new java.util.Random();


    int iRand=0;
    int tempLogon=0;
    int tempPwd=0;

    @Test
    public void main() {
        {

            for (int j = 0; j < 2; j++) {
                iRand = (int) (rand.nextDouble() * 100000000L);
                if (j == 0) {
                    tempLogon = iRand;
                } else {
                    tempPwd = iRand;

                }
                //Added by IN435664 for INC - 3660 .TEMP [Start]
                //String tempLogonId=tmpStoreId+".TEMP."+strDate+"."+tempLogon;
                String tempLogonId = ".TEMPLogOn." + strDate + "." + tempLogon;
                //Added by IN435664 for INC - 3660 .TEMP [End]
                String tempLogonPassword = "TEMPPass." + tempPwd;
                System.out.println(tempLogonPassword);
                System.out.println(tempLogonId);


//                //public static StringBuffer singleOccurence(String s);
//                StringBuffer sb = new StringBuffer(tempLogonPassword);
//                for (int i = sb.length() - 2; i >= 0; i--)
//
//                    if (sb.charAt(i) == sb.charAt(i + 1))
//                        sb.deleteCharAt(i + 1);
//                System.out.println(sb);
//                return;
            }
        }
    }

}
