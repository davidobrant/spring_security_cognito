package org.example.spring_security_cognito.utils;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Utils {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatPostDate(Date date) {
        return formatter.format(date);
    }

}
