package com.vuukle.comment_library.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopArticleModel {

    @SerializedName("acticleId")
    @Expose
    private String acticleId;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("emotes")
    @Expose
    private Object emotes;
    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("seoURL")
    @Expose
    private String seoURL;

    public String getActicleId() {
        return acticleId;
    }

    public void setActicleId(String acticleId) {
        this.acticleId = acticleId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public Object getEmotes() {
        return emotes;
    }

    public void setEmotes(Object emotes) {
        this.emotes = emotes;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSeoURL() {
        return seoURL;
    }

    public void setSeoURL(String seoURL) {
        this.seoURL = seoURL;
    }
}
