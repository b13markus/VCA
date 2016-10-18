package com.vuukle.comment_library.network;

import com.vuukle.comment_library.models.CommentFeedModel;
import com.vuukle.comment_library.models.PostCommentResultModel;
import com.vuukle.comment_library.models.RaitingModel;
import com.vuukle.comment_library.models.ReplyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface Api {

    String BASE_URL = "https://vuukle.com/api.asmx/";


    @GET("getCommentFeed")
    Call<CommentFeedModel> getCommentsFeed(@Query("host") String host,
                                           @Query("article_id") String articleId,
                                           @Query("api_key") String apiKey,
                                           @Query("secret_key") String secretKey,
                                           @Query("time_zone") String timeZone,
                                           @Query("from_count") Integer fromCount,
                                           @Query("to_count") Integer toCount);

    @GET("getReplyFeed")
    Call<List<ReplyModel>> getReplyFeed(@Query("comment_id") String commentId,
                                        @Query("host") String host,
                                        @Query("article_id") String articleId,
                                        @Query("api_key") String apiKey,
                                        @Query("secret_key") String secretKey,
                                        @Query("time_zone") String timeZone,
                                        @Query("parent_id") String parentId);

    //TODO what is tag and what is title
    @GET("postComment")
    Call<PostCommentResultModel> postComment(@Query("name") String name,
                                             @Query("email") String email,
                                             @Query("comment") String comment,
                                             @Query("url") String url,
                                             @Query("host") String host,
                                             @Query("article_id") String articleId,
                                             @Query("api_key") String apiKey,
                                             @Query("secret_key") String secretKey,
                                             @Query("time_zone") String timeZone,
                                             @Query("parent_id") String parentId,
                                             @Query("tags") String tags,
                                             @Query("title") String title);

    //// TODO: what is resource_id
    @GET("postReply")
    Call<Object> postReply(@Query("name") String name,
                           @Query("email") String email,
                           @Query("comment") String comment,
                           @Query("host") String host,
                           @Query("article_id") String articleId,
                           @Query("api_key") String apiKey,
                           @Query("secret_key") String secretKey,
                           @Query("comment_id") String comment_id,
                           @Query("resource_id") int resource_id);

    @GET("setCommentVote")
    Call<Object> setUpDownVotes(@Query("host") String host,
                                @Query("article_id") String articleId,
                                @Query("api_key") String apiKey,
                                @Query("secret_key") String secretKey,
                                @Query("comment_id") String commentId,
                                @Query("up_down") String upOrDown,
                                @Query("name") String name,
                                @Query("email") String email);

    @GET("getEmoteRating")
    Call<RaitingModel> getEmoteRating(@Query("host") String host,
                                      @Query("api_key") String apiKey,
                                      @Query("article_id") String articleId);

    @GET("setEmoteRating")
    Call<Object> setEmoteRating(@Query("host") String host,
                                @Query("api_key") String apiKey,
                                @Query("article_id") String articleId,
                                @Query("url") String url,
                                @Query("article_title") String articleTitle,
                                @Query("article_image") String article_image,
                                @Query("emote") Integer emote);
}
