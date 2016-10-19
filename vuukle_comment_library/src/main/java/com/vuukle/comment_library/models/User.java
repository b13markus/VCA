package com.vuukle.comment_library.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.vuukle.comments.vuuklecommentlibrary.R;


public class User {

    public static String getUserName(Activity activity){
        SharedPreferences sPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sPref.getString(activity.getResources().getString(R.string.user_name), "");
    }

    public static String getUserEmail(Activity activity){
        SharedPreferences sPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sPref.getString(activity.getResources().getString(R.string.user_email), "");
    }

    public static void setUser(Activity mContext, String name, String email) {
        SharedPreferences sPref = (mContext).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(mContext.getString(R.string.user_name), name);
        ed.putString(mContext.getString(R.string.user_email), email);
        ed.apply();

        if(name.isEmpty()&& email.isEmpty()){
            setUserLogedIn(mContext, false);
        }else {
            setUserLogedIn(mContext, true);
        }
    }

    private static void setUserLogedIn(Activity context, boolean isLogedIn) {
        SharedPreferences sPref = (context).getPreferences(Context.MODE_PRIVATE);
        sPref.edit().putBoolean(context.getString(R.string.user_is_logedin), isLogedIn).apply();
    }

    public static boolean isUserLogedIn(Activity activity){
        SharedPreferences sPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sPref.getBoolean(activity.getResources().getString(R.string.user_is_logedin), false);
    }
}
