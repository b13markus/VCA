package com.vuukle.comment_library;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuukle.comment_library.adapter.CommentsAdapter;
import com.vuukle.comments.vuuklecommentlibrary.R;

public class CommentsListFragment extends Fragment {

    private static String ARTICLE_ID, API_KEY, SECRET_KEY, TIME_ZONE, HOST, URL, TAGS, TITLE;
    private static int paginationToCount;
    private static boolean isEmoteVisible;
    private static boolean isSwipeToRefreshEnable;
    private static boolean addArticleWebView;
    private static boolean addAdsBanner;
    private static boolean addTopArticle;

    protected static CommentsListFragment newInstance(String ARTICLE_ID,
                                                      String API_KEY,
                                                      String SECRET_KEY,
                                                      String TIME_ZONE,
                                                      String HOST,
                                                      String URL,
                                                      String TAGS,
                                                      String TITLE,
                                                      int paginationToCount,
                                                      Context mContext,
                                                      boolean isEmoteVisible,
                                                      boolean isSwipeToRefreshEnable,
                                                      boolean addArticleWebView,
                                                      boolean addAdsBanner,
                                                      boolean addTopArticle) {
        Bundle args = new Bundle();
        args = putValuesToBundle(args,
                mContext,
                ARTICLE_ID,
                API_KEY,
                SECRET_KEY,
                TIME_ZONE,
                HOST,
                URL,
                TAGS,
                TITLE,
                paginationToCount,
                isEmoteVisible,
                isSwipeToRefreshEnable,
                addArticleWebView,
                addTopArticle);
        CommentsListFragment fragment = new CommentsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle putValuesToBundle(Bundle args, Context mContext,
                                            String ARTICLE_ID,
                                            String API_KEY,
                                            String SECRET_KEY,
                                            String TIME_ZONE,
                                            String HOST,
                                            String URL,
                                            String TAGS,
                                            String TITLE,
                                            int paginationToCount,
                                            boolean isEmoteVisible,
                                            boolean isSwipeToRefreshEnable,
                                            boolean addArticleWebView,
                                            boolean addTopArticle) {
        args.putString(mContext.getString(R.string.article_id), ARTICLE_ID);
        args.putString(mContext.getString(R.string.api_key), API_KEY);
        args.putString(mContext.getString(R.string.secret_key), SECRET_KEY);
        args.putString(mContext.getString(R.string.time_zone), TIME_ZONE);
        args.putString(mContext.getString(R.string.host), HOST);
        args.putString(mContext.getString(R.string.url), URL);
        args.putString(mContext.getString(R.string.tags), TAGS);
        args.putString(mContext.getString(R.string.title), TITLE);
        args.putInt(mContext.getString(R.string.pagination_to_count), paginationToCount);
        args.putBoolean(mContext.getString(R.string.is_emote_visible), isEmoteVisible);
        args.putBoolean(mContext.getString(R.string.is_swipe_to_refresh_enable), isSwipeToRefreshEnable);
        args.putBoolean(mContext.getString(R.string.addArticleWebView), addArticleWebView);
        args.putBoolean(mContext.getString(R.string.addAdsBanner), addAdsBanner);
        args.putBoolean(mContext.getString(R.string.addTopArticle), addTopArticle);
        return args;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.comments_list_view, container, false);
        getValuesFromBundle();
        initSwipeToRefresh(fragmentView);
        return fragmentView;
    }

    private void initSwipeToRefresh(View fragmentView) {
//        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.activity_main_swipe_refresh_layout);
//        swipeRefreshLayout.setEnabled(isSwipeToRefreshEnable);
        initRecyclerView(null, fragmentView);
    }

    private void initRecyclerView(SwipeRefreshLayout swipeRefreshLayout, View fragmentView) {
        CommentsAdapter adapter = new CommentsAdapter(getActivity(),
                0,
                paginationToCount,
                null,
                API_KEY,
                SECRET_KEY,
                TIME_ZONE,
                HOST,
                URL,
                ARTICLE_ID,
                TAGS,
                TITLE,
                isEmoteVisible,
                addArticleWebView,
                addAdsBanner,
                addTopArticle);
        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.comments_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void getValuesFromBundle() {
        ARTICLE_ID = getArguments().getString(getString(R.string.article_id));
        API_KEY = getArguments().getString(getString(R.string.api_key));
        SECRET_KEY = getArguments().getString(getString(R.string.secret_key));
        TIME_ZONE = getArguments().getString(getString(R.string.time_zone));
        HOST = getArguments().getString(getString(R.string.host));
        URL = getArguments().getString(getString(R.string.url));
        TAGS = getArguments().getString(getString(R.string.tags));
        TITLE = getArguments().getString(getString(R.string.title));
        paginationToCount = getArguments().getInt(getString(R.string.pagination_to_count));
        isEmoteVisible = getArguments().getBoolean(getString(R.string.is_emote_visible));
        isSwipeToRefreshEnable = getArguments().getBoolean(getString(R.string.is_swipe_to_refresh_enable));
        addArticleWebView = getArguments().getBoolean(getString(R.string.addArticleWebView));
        addAdsBanner = getArguments().getBoolean(getString(R.string.addAdsBanner));
        addTopArticle = getArguments().getBoolean(getString(R.string.addTopArticle));
    }
}

