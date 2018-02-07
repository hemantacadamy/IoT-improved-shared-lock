package com.example.hemant.sharedlock;

/**
 * Created by Hemant on 09-04-2017.
 */

public class IpAddress {
    public static String ip;

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        IpAddress.ip = ip;
    }
}
