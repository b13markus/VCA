package com.vuukle.comment_library;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.vuukle.comment_library.exception.VuukleCommentsException;
import com.vuukle.comments.vuuklecommentlibrary.R;

public class VuukleApiBuilder {

    private String API_KEY;
    private String SECRET_KEY;
    private String HOST;
    private Context mContext;
    private SharedPreferences sPrefs;
    private SharedPreferences.Editor mEditor;


   public VuukleApiBuilder (Context context){
       mContext = context;
       sPrefs = mContext.getSharedPreferences(mContext.getString(R.string.vuukle), Context.MODE_PRIVATE);
       mEditor = sPrefs.edit();

   }


    /**
     * Required field
     * <p>Set your API key for API. To get API KEY you need :
     * <p> 1) Sign in to dashboard thouth vuukle.com
     * <p> 2) Navigate to domain from home page of dashboard (first page after signing in)
     * <p>3) Choose in menu Integration, then API Docs from the dropdown
     * <p> 4) Then you will be able to see API and secret keys
     * <p>  ---- or ----
     * <p>  1) Sign in to dashboard thouth vuukle.com
     * <p> 2) after signing in, in header you can find ‘Integration’ click -> choose API docs in the drop-down.
     */
    public VuukleApiBuilder setVuukleApiKey(String apiKey) {
        API_KEY = apiKey;
        return this;
    }

    /**
     * Required field
     * <p>Set your API key for API. To get SECRET KEY you need :
     * <p> 1) Sign in to dashboard through vuukle.com
     * <p> 2) Navigate to domain from home page of dashboard (first page after signing in)
     * <p>3) Choose in menu Integration, then API Docs from the dropdown
     * <p> 4) Then you will be able to see api and SECRET keys
     * <p>  ---- or ----
     * <p>  1) Sign in to dashboard thouth vuukle.com
     * <p> 2) after signing in, in header you can find ‘Integration’ click -> choose API docs in the drop-down.
     */
    public VuukleApiBuilder setVuukleSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
        return this;
    }

    /**
     * Required field
     * <p> Set host for Api. Host - this is domain of the publisher’s site(e.g. indianexpress.com, thehindu.com etc.).
     * <p>For example: You are the owner of indianexpress.com and have own app where want’s to setup this library,
     * so when library installed on your app, You should paste domain for ‘host’ property without http:// or https:// or www.
     */
    public VuukleApiBuilder setVuukleHost(String host) {
        if(host != null) {
            HOST = host.toLowerCase();
        }
        return this;
    }


    public boolean build() {
        try {
            checkAllParametersForException();
            sPrefs.edit()
                    .putString(mContext.getString(R.string.host), HOST)
                    .putString(mContext.getString(R.string.secret_key), SECRET_KEY)
                    .putString(mContext.getString(R.string.api_key), API_KEY)
                    .apply();
            return true;
        } catch (VuukleCommentsException e) {
            Log.e("df", "VUUKLE COMMENT EXCEPTION", e);
            return false;
        }
    }

    private void checkAllParametersForException() throws VuukleCommentsException {
        checkForException(HOST, R.string.host);
        checkForException(API_KEY, R.string.api_key);
        checkForException(SECRET_KEY, R.string.secret_key);
    }


    private void checkForException(String parameter, int stringExceptionId) throws VuukleCommentsException {
        if (TextUtils.isEmpty(parameter)) {
            throw new VuukleCommentsException(mContext.getString(stringExceptionId));
        }
    }

}
