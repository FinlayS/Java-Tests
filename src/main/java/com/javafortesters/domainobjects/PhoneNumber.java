package com.javafortesters.domainobjects;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 20/01/2015.
 * Time: 09:03
 */
public class PhoneNumber {
        private int code;
        private String number;
        // ... constructors and methods


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}




