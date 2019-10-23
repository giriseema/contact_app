package com.contactapp.www.contactsapp.constant;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Constant class for common Constant and method used by application
 */
public class Constant {
    public static final String CONTACT_DETAIL_KEY = "CONTACT_DETAIL";
    public static final int TIME_OUT = 90000;
    public static final String JSON_STRING = "[\n" +
            "\t{\n" +
            "     \"firstName\": \"John\",\n" +
            "     \"lastName\": \"Smith\",\n" +
            "     \"phoneNumber\":  \"+917732903523\"          \n" +
            "\t},\n" +
            "\t{\n" +
            "     \"firstName\": \"Ron\",\n" +
            "     \"lastName\": \"Weasley\",\n" +
            "     \"phoneNumber\":  \"+919971792703\"\n" +
            "\t},\n" +
            "\t{\n" +
            "     \"firstName\": \"Hermione\",\n" +
            "     \"lastName\": \"Granger\",\n" +
            "     \"phoneNumber\":  \"212 555-1233\"\n" +
            "\t},\n" +
            "\t{\n" +
            "     \"firstName\": \"Harry\",\n" +
            "     \"lastName\": \"Potter\",\n" +
            "     \"phoneNumber\":  \"212 555-1234\"\n" +
            "\t}\n" +
            "]";

    /**
     * get time from the particular dateformat
     * @param milliSeconds time in millisecond
     * @param dateFormat upcoming date format
     * @return require date format
     */
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * isOnline check the internet connectivity before sending sms and OTP
     * @param context instance of activity
     * @return
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager != null && (connectivityManager.getActiveNetworkInfo() != null);
    }
}
