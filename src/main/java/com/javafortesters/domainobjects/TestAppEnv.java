package com.javafortesters.domainobjects;

/**
 * Created with com.javafortesters.domainobjects.environment
 * Created by: Finlay S.
 * Date: 12/09/2014.
 * Time: 11:44
 */
public class TestAppEnv {
    public static final String DOMAIN = "192.123.0.3";
    public static final String PORT = "67";

    public static String getUrl() {
        return "http://" + DOMAIN + ":" + PORT;
    }
}
