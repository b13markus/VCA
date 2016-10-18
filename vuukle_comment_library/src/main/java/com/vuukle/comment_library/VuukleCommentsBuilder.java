package com.vuukle.comment_library;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.vuukle.comments.vuuklecommentlibrary.R;
import com.vuukle.comment_library.exception.VuukleCommentsException;

public class VuukleCommentsBuilder {

    private String ARTICLE_ID;
    private String API_KEY;
    private String SECRET_KEY;
    private String HOST;
    private String TIME_ZONE;
    private String URL;
    private String TAGS = "Tag";
    private String TITLE;
    private int paginationToCount = 9;
    private Context mContext;
    private int mFragmentId;
    private boolean isEmoteVisible = true;
    private boolean isSwipeToRefreshEnable = false;
    private boolean addArticleWebView = false;
    private boolean addAdsBanner = true;

    public boolean isEmoteVisible() {
        return isEmoteVisible;
    }

    /**
     * Optional
     * <p> set EmoteRating visible or gone
     *
     * @param isEmoteVisible set true for visible emote rating
     *                       <p>
     *                       <p> default value true
     */
    public VuukleCommentsBuilder setEmoteVisible(boolean isEmoteVisible) {
        this.isEmoteVisible = isEmoteVisible;
        return this;
    }

    /**
     * Optional
     * <p> set swipeToRefresh enable true or false
     *
     * @param isSwipeToRefreshEnable set true for enable swipeToRefresh
     *                               <p>
     *                               <p> default value false
     */
    public VuukleCommentsBuilder setSwipeToRefreshEnable(boolean isSwipeToRefreshEnable) {
        this.isSwipeToRefreshEnable = isSwipeToRefreshEnable;
        return this;
    }

    public int getContainerId() {
        return mFragmentId;
    }

    /**
     * Required field
     * <p> Set fragment id from your .xml in which you want to put all comments
     * <p> Example : R.id.container
     */
    public VuukleCommentsBuilder setFragmentId(int fragmentId) {
        mFragmentId = fragmentId;
        return this;
    }

    /**
     * Required
     * <p> Set id of Article (for API)
     */
    public VuukleCommentsBuilder setVuukleArticleId(String articleId) {
        ARTICLE_ID = articleId;
        return this;
    }



    /**
     * Set time zone (for API)
     *
     * @param timeZone get timezone from this resource:
     *                 <p>
     *                 <url>https://en.wikipedia.org/wiki/List_of_tz_database_time_zones</url>
     */
    public VuukleCommentsBuilder setTimeZone(String timeZone) {
        TIME_ZONE = timeZone;
        return this;
    }



    /**
     * Required field
     * <p> URL will be unique for each page where comment box opens.
     * <p>For example:You are on the main app page with articles list -> you are choosing article -> it opens
     * and our library should have unique properties for each article like URL, TAGS, TITLE.
     * <p>These properties You need to fill by yourself.
     * <p>URL - will be article url from your domain/website to the article,
     * <p>Tags - You need to paste tags separated by comma for each article(like you have on website/domain),
     * same for TITLE - title of the article on which library is now.
     */
    public VuukleCommentsBuilder setArticleUrl(String url) {
        URL = url;
        return this;
    }

    /**
     * Required field
     * <p> TAG will be unique for each page where comment box opens.
     * <p>For example:You are on the main app page with articles list -> you are choosing article -> it opens
     * and our library should have unique properties for each article like URL, TAGS, TITLE.
     * <p>These properties You need to fill by yourself.
     * <p>URL - will be article url from your domain/website to the article,
     * <p>Tags - You need to paste tags separated by comma for each article(like you have on website/domain),
     * same for TITLE - title of the article on which library is now.
     */
    public VuukleCommentsBuilder setVuukleTags(String tags) {
        TAGS = tags;
        return this;
    }

    /**
     * Required field
     * <p> TITLE will be unique for each page where comment box opens.
     * <p>For example:You are on the main app page with articles list -> you are choosing article -> it opens
     * and our library should have unique properties for each article like URL, TAGS, TITLE.
     * <p>These properties You need to fill by yourself.
     * <p>URL - will be article url from your domain/website to the article,
     * <p>Tags - You need to paste tags separated by comma for each article(like you have on website/domain), same for
     * <p>TITLE - title of the article on which library is now.
     */
    public VuukleCommentsBuilder setVuukleTitle(String title) {
        TITLE = title;
        return this;
    }

    public VuukleCommentsBuilder addAdsBanner(boolean addAdsBanner){
        this.addAdsBanner = addAdsBanner;
        return this;
    }


    /**
     * Optional
     * <p>
     * This method gives the number of comments that will be loaded from the server
     * <p>
     * Default value is 9
     */
    public VuukleCommentsBuilder setPaginationToCount(int paginationToCount) {
        this.paginationToCount = paginationToCount;
        return this;
    }

    public VuukleCommentsBuilder addArticleWebView(boolean addArticleWebView){
        this.addArticleWebView = addArticleWebView;
        return this;
    }

    public VuukleCommentsBuilder setContext(Context context) {
        mContext = context;
        return this;
    }

    private void checkForException(String parameter, int stringExceptionId) throws VuukleCommentsException {
        if (TextUtils.isEmpty(parameter)) {
            throw new VuukleCommentsException(mContext.getString(stringExceptionId));
        }
    }

    private void checkAllParametersForException() throws VuukleCommentsException {
        checkAPISettings();
        checkForException(ARTICLE_ID, R.string.article_id);
        checkForException(TIME_ZONE, R.string.time_zone);
        checkForException(URL, R.string.url);
        checkForException(TITLE, R.string.title);
        checkForException(mFragmentId + "", R.string.container_id);
    }

    private void checkAPISettings() throws VuukleCommentsException {
        SharedPreferences sPrefs = mContext.getSharedPreferences(mContext.getString(R.string.vuukle),Context.MODE_PRIVATE);
        SECRET_KEY = sPrefs.getString(mContext.getString(R.string.secret_key),"");
        API_KEY = sPrefs.getString(mContext.getString(R.string.api_key),"");
        HOST = sPrefs.getString(mContext.getString(R.string.host),"");
        checkForException(SECRET_KEY, R.string.secret_key);
        checkForException(API_KEY, R.string.api_key);
        checkForException(HOST, R.string.host);

    }

    /**
     * For calling this method you must
     * <p>
     * add to your gradle retrolambda package and
     * <p>
     * set required parameters :
     * <ul>
     * <li>ARTICLE_ID  {@link VuukleCommentsBuilder#setVuukleArticleId(String)}
     * <li>TIME_ZONE   {@link VuukleCommentsBuilder#setTimeZone(String)}
     * <li>URL         {@link VuukleCommentsBuilder#setArticleUrl(String)}
     * <li>TAGS        {@link VuukleCommentsBuilder#setVuukleTags(String)}
     * <li>TITLE       {@link VuukleCommentsBuilder#setVuukleTitle(String)}
     * <li>Context     {@link VuukleCommentsBuilder#setContext(Context)}
     * <li>ContainerId {@link VuukleCommentsBuilder#setFragmentId(int)}
     * </ul>
     * Optional parameters:
     * <ul>
     * <li>paginationToCount {@link VuukleCommentsBuilder#setPaginationToCount(int)}
     * <li>isEmoteVisible {@link VuukleCommentsBuilder#setEmoteVisible(boolean)}
     * <li>isSwipeToRefreshEnable {@link VuukleCommentsBuilder#setSwipeToRefreshEnable(boolean)}
     * </ul>
     *
     * @return Intent with extras inside if throw exception return null
     * @throws VuukleCommentsException if required parameters is invalid or not initialized
     */
    public Fragment build() {
        try {
            checkAllParametersForException();
            final FragmentManager fm = ((Activity) mContext).getFragmentManager();
            final FragmentTransaction ft = fm.beginTransaction();
            final CommentsListFragment fragment = CommentsListFragment.newInstance(ARTICLE_ID,
                    API_KEY,
                    SECRET_KEY,
                    TIME_ZONE,
                    HOST,
                    URL,
                    TAGS,
                    TITLE,
                    paginationToCount,
                    mContext,
                    isEmoteVisible,
                    isSwipeToRefreshEnable,
                    addArticleWebView,
                    addAdsBanner);
            ft.add(getContainerId(), fragment);
            ft.commit();
            return fragment;
        } catch (VuukleCommentsException e) {
            Log.e("df", "VUUKLE COMMENT EXCEPTION", e);
            return null;
        }
    }
}