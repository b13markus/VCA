package com.vuukle.comments.internalcommentslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.vuukle.comment_library.VuukleApiBuilder;
import com.vuukle.comment_library.VuukleCommentsBuilder;
import com.vuukle.comment_library.libraryApi.VuukleInfo;
import com.vuukle.vuuklecomment.R;

public class MainActivity extends AppCompatActivity {

    private String API_KEY = "777854cd-9454-4e9f-8441-ef0ee894139e";
    private String SECRET_KEY = "07115720-6848-11e5-9bc9-002590f371ee";
    private String HOST = "vuukle.com";
    private String TIME_ZONE = "Europe/Kiev";
    private String URL = "https://www.google.com.ua/";
    private String TAGS = "articleTag1, articleTag";
    private String TITLE = "The title of the article";
    private String ARTICLE_ID = "00048";
    private int paginationToCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVuukleAPI();
        startCommentsFragment();

        Log.d("Myyy", VuukleInfo.getCountComments(this)+"");
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
                .setEmoteVisible(false)
                .setSwipeToRefreshEnable(false)
                .setFragmentId(R.id.container)
                .addArticleWebView(true)
                .setTopArticle(true)
                .build();
    }
}