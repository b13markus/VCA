package com.vuukle.comment_library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    public static boolean emailIsValid(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void hideKeyboard(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static boolean dataIsValid(String userEmail, String userName, String commentMessage) {
        return emailIsValid(userEmail) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(commentMessage);
    }
}
