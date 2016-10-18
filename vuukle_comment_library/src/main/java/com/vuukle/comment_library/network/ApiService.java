package com.vuukle.comment_library.network;

import android.app.Activity;

import com.vuukle.comments.vuuklecommentlibrary.R;
import com.vuukle.comment_library.models.User;

 public class ApiService {

    private static final String PARENT_ID = "-1";
    private static final String UP_VOTES = "1";
    private static final String DOWN_VOTES = "-1";

    public static void getAllComments(Integer fromCount,
                                      Integer toCount,
                                      CancelableCallback callback,
                                      String apiKey,
                                      String secretKey,
                                      String timeZone,
                                      String articleId,
                                      String host) {
        RetrofitBuilder.getApi()
                .getCommentsFeed(host
                        , articleId
                        , apiKey
                        , secretKey
                        , timeZone
                        , fromCount
                        , toCount).enqueue(callback);
    }

    public static void getCommentReplies(String commentId,
                                         String parentId,
                                         CancelableCallback callback,
                                         String host,
                                         String apiKey,
                                         String secretKey,
                                         String timeZone,
                                         String articleId) {
        RetrofitBuilder.getApi()
                .getReplyFeed(commentId
                        , host
                        , articleId
                        , apiKey
                        , secretKey
                        , timeZone
                        , parentId).enqueue(callback);
    }

    public static void postComment(String name,
                                   String email,
                                   String comment,
                                   CancelableCallback callback,
                                   String apiKey,
                                   String secretKey,
                                   String timeZone,
                                   String host,
                                   String url,
                                   String articleId,
                                   String tags,
                                   String title) {
        RetrofitBuilder.getApi()
                .postComment(name,
                        email,
                        comment,
                        url,
                        host,
                        articleId,
                        apiKey,
                        secretKey,
                        timeZone,
                        PARENT_ID,
                        tags,
                        title).enqueue(callback);
    }

    public static void postReply(String name,
                                 String email,
                                 String comment,
                                 String commentID,
                                 CancelableCallback callback,
                                 String host,
                                 String articleId,
                                 String apiKey,
                                 String secretKey,
                                 int resourceId) {
        RetrofitBuilder.getApi()
                .postReply(name,
                        email,
                        comment,
                        host,
                        articleId,
                        apiKey,
                        secretKey,
                        commentID,
                        resourceId).enqueue(callback);
    }

    public static void setVotes(Activity activity,
                                int viewId,
                                String commentId,
                                CancelableCallback callback,
                                String host,
                                String articleId,
                                String apiKey,
                                String secretKey) {
        RetrofitBuilder.getApi().setUpDownVotes(host,
                articleId,
                apiKey,
                secretKey,
                commentId,
                checkLikeOrDislike(viewId),
                User.getUserName(activity),
                User.getUserEmail(activity)).enqueue(callback);
    }

    public static void getEmoteRating(String host,
                                      String articleId,
                                      String apiKey,
                                      CancelableCallback callback) {
        RetrofitBuilder.getApi().getEmoteRating(host,
                apiKey,
                articleId).enqueue(callback);
    }

    public static void setEmoteRating(String host,
                                      String articleId,
                                      String apiKey,
                                      String url,
                                      String articleTitle,
                                      String articleImage,
                                      Integer emote,
                                      CancelableCallback callback) {
        RetrofitBuilder.getApi().setEmoteRating(host,
                apiKey,
                articleId,
                url,
                articleTitle,
                articleImage,
                emote).enqueue(callback);
    }

    private static String checkLikeOrDislike(int viewId) {
        if (viewId == R.id.like) {
            return UP_VOTES;
        } else {
            return DOWN_VOTES;
        }
    }
}
