package com.vuukle.comment_library.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

class AdsBannerHolder extends RecyclerView.ViewHolder {


    AdsBannerHolder(View itemView) {
        super(itemView);
        WebView webView = (WebView)itemView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/affinity_app.html");
    }
}
