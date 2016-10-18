package com.vuukle.comment_library.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ArticleWebViewHolder extends RecyclerView.ViewHolder {

    private WebView webView;


    ArticleWebViewHolder(View itemView) {
        super(itemView);
        webView = (WebView) itemView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(CommentsAdapter.ARTICLE_URL);

    }

    public void initWebView(String url) {
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
