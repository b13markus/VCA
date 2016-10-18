package com.vuukle.comment_library.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentFeedModel {


    @SerializedName("resource_id")
    @Expose
    public Integer resourceId;
    @SerializedName("article_id")
    @Expose
    public String articleId;
    @SerializedName("comments")
    @Expose
    public Integer comments;
    @SerializedName("host")
    @Expose
    public String host;
    @SerializedName("comment_feed")
    @Expose
    public ArrayList<CommentModel> commentFeed = new ArrayList<>();


    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ArrayList<CommentModel> getCommentFeed() {
        return commentFeed;
    }

    public void setCommentFeed(ArrayList<CommentModel> commentFeed) {
        this.commentFeed = commentFeed;
    }
}
