package com.vuukle.comment_library.utils;

import android.util.Log;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public static String formatDateFromString(String inputDate){

        if (inputDate != null) {
            Date parsed;
            String outputDate = "";
            SimpleDateFormat df_input = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", java.util.Locale.getDefault());
            try {
                parsed = df_input.parse(inputDate);
                PrettyTime p = new PrettyTime();
                outputDate = p.format(parsed);
            } catch (ParseException e) {
                Log.d("ParseException", e.toString());
            }

            return outputDate;
        }else {
            return "";
        }
    }
}
