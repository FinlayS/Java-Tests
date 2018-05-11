package com.javafortesters.xmlgenrationclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA 13.1.4
 * Created by: Finlay S.
 * Project: javafortesters
 * Date: 20/01/2015.
 * Time: 10:25
 */
public class Customer {

    private String name;
    private Address address;
    private List<PhoneNumber> phoneNumbers;

    public Customer() {
        phoneNumbers = new ArrayList<PhoneNumber>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
