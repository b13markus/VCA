package com.vuukle.comment_library.models;

public class HeaderModel {

    int commentCount = 0;

    public HeaderModel(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
