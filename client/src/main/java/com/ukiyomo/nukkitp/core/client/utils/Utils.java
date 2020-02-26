package com.ukiyomo.nukkitp.core.client.utils;

import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

    public static boolean checkUUID4(String str) {
        return null != str && Pattern.matches(".{8}-.{4}-.{4}-.{4}-.{12}", str);
    }

    public static boolean isIP(String address)
    {
        if(address.length() < 7 || address.length() > 15)
        {
            return false;
        }

        return Pattern.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}", address);
    }

    public static boolean isPosition(String position) {
        return false;
    }

    public static Integer toInteger(String val) {

        Integer found = null;

        try {
            return Integer.valueOf(val);
        } catch (NumberFormatException ignore) { }

        return found;
    }

    public static long getNowMillisecond() {
        return new Date().getTime();
    }

}
