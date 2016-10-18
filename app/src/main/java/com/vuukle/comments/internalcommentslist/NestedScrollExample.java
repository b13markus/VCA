package com.vuukle.comments.internalcommentslist;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vuukle.comment_library.VuukleApiBuilder;
import com.vuukle.comment_library.VuukleCommentsBuilder;
import com.vuukle.vuuklecomment.R;

public class NestedScrollExample extends AppCompatActivity {

    private String API_KEY = "777854cd-9454-4e9f-8441-ef0ee894139e";
    private String SECRET_KEY = "07115720-6848-11e5-9bc9-002590f371ee";
    private String HOST = "vuukle.com";
    private String TIME_ZONE = "Europe/Kiev";
    private String URL = "http://vuukle.com/test_files/test48.html";
    private String TAGS = "articleTag1, articleTag";
    private String TITLE = "The title of the article";
    private String ARTICLE_ID = "00048";
    private int paginationToCount = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nested_scroll_view);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        if (myWebView != null) {
            myWebView.setWebViewClient(new MyWebViewClient());
        }
        WebSettings webSettings = myWebView != null ? myWebView.getSettings() : null;
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
            myWebView.loadUrl("https://www.google.com.ua/");
        }
    }

    private void initVuukleAPI() {
        new VuukleApiBuilder(this).setVuukleApiKey(API_KEY)
                .setVuukleHost(HOST)
                .setVuukleSecretKey(SECRET_KEY)
                .build();
    }

    private void startCommentsFragment() {
        new VuukleCommentsBuilder()
                .setVuukleArticleId(ARTICLE_ID)
                .setTimeZone(TIME_ZONE)
                .setArticleUrl(URL)
                .setVuukleTags(TAGS)
                .setVuukleTitle(TITLE)
                .setPaginationToCount(paginationToCount)
                .setContext(this)
                .setSwipeToRefreshEnable(false)
                .setEmoteVisible(false)
                .setFragmentId(R.id.container)
                .build();
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("onPageFinished", "onPageFinished: ");
            // start load comments after webview is downloaded
            initVuukleAPI();
            startCommentsFragment();
        }
    }
}
