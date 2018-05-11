package com.javafortesters.webdrivertest;

import org.junit.Test;

public class cogzSnippet {


    java.util.Date now = new java.util.Date();
    java.text.DateFormat dateformat = new java.text.SimpleDateFormat("dd-MM-yy.HH.mm.ss");
    String strDate = dateformat.format(now);


    java.util.Random rand = new java.util.Random();


    int iRand = 0;
    int tempLogon = 0;
    int tempPwd = 0;

    @Test
    public void main() {
        {
            //run 1000 times
            for (int c = 0; c < 1000; c++) { //run 1000 times

                for (int j = 0; j < 2; j++) {
                    iRand = (int) (rand.nextDouble() * 100000000L);
                    if (j == 0) {
                        tempLogon = iRand;
                    } else {
                        tempPwd = iRand;
                    }
                }
                //Added by IN435664 for INC - 3660 .TEMP [Start]
                //String tempLogonId=tmpStoreId+".TEMP."+strDate+"."+tempLogon;
                String tempLogonId = ".TEMP." + strDate + "." + tempLogon;
                //Added by IN435664 for INC - 3660 .TEMP [End]
                String tempLogonPassword = "TEMP." + tempPwd;

                // Find & delete consecutive numbers in iRand
                StringBuffer sb = new StringBuffer(tempLogonPassword);
                int occ = 0;
                for (int i = sb.length() - 2; i >= 0; i--)

                    if (sb.charAt(i) == sb.charAt(i + 1)) {
                        sb.deleteCharAt(i + 1);
                        occ++;
                    }

                // Tell me if 3 or more numbers were deleted.
                if (occ > 2) {
                    System.out.println(tempPwd);
                    System.out.println(sb);
                    System.out.println(occ + " numbers deleted");
                    System.out.println("------------------");
                }
            }
        }
    }
}
