package com.vuukle.comment_library.models;

import com.google.gson.annotations.SerializedName;

public class PostCommentResultModel {

    @SerializedName("result")
    public String result;
    @SerializedName("comment_id")
    public String commentId;
    @SerializedName("isModeration")
    public String isModeration;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getIsModeration() {
        return isModeration;
    }

    public void setIsModeration(String isModeration) {
        this.isModeration = isModeration;
    }
}
