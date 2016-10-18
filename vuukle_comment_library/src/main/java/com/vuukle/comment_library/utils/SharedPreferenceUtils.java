package com.vuukle.comment_library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;


public class SharedPreferenceUtils {

    public static void putListString(Activity context, String key, ArrayList<String> stringList) {
        SharedPreferences preferences = context.getPreferences(Context.MODE_PRIVATE);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    public static ArrayList<String> getListString(Activity context, String key) {
        SharedPreferences preferences = context.getPreferences(Context.MODE_PRIVATE);
        return new ArrayList<>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }
}
