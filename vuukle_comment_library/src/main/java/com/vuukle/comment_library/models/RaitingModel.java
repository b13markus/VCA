package com.vuukle.comment_library.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Taras on 05.07.2016.
 */
public class RaitingModel {

    @SerializedName("host")
    private String host;
    @SerializedName("image")
    private String image;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("api_key")
    private  String apiKey;
    @SerializedName("article_id")
    private String articleId;
    @SerializedName("emotes")
    private Emotes emotes;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Emotes getEmotes() {
        return emotes;
    }

    public void setEmotes(Emotes emotes) {
        this.emotes = emotes;
    }
}
