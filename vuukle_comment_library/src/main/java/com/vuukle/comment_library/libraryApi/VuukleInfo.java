package com.vuukle.comment_library.libraryApi;

import android.content.Context;
import android.content.SharedPreferences;

import com.vuukle.comments.vuuklecommentlibrary.R;

public class VuukleInfo {
    private static String COUNT_COMMENTS_KEY = "count_comments";

    public static int getCountComments(Context context) {
        SharedPreferences sPrefs = context.getSharedPreferences(context.getString(R.string.vuukle), Context.MODE_PRIVATE);
        return sPrefs.getInt(COUNT_COMMENTS_KEY,0);
    }

}
