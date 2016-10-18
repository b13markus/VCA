package com.vuukle.comment_library.models;



public class ArticleWebViewModel {

    private String url;

    public ArticleWebViewModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
