package com.vuukle.comment_library.adapter;


public class CommentTag {

    private int level;
    private boolean isShow;

    public CommentTag(int level, boolean isShow) {
        this.level = level;
        this.isShow = isShow;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
