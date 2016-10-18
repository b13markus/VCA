package com.vuukle.comment_library.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Taras on 05.07.2016.
 */
public class Emotes {

    @SerializedName("first")
    private Integer first;
    @SerializedName("second")
    private Integer second;
    @SerializedName("third")
    private Integer third;
    @SerializedName("fourth")
    private Integer fourth;
    @SerializedName("fifth")
    private Integer fifth;
    @SerializedName("sixth")
    private Integer sixth;

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getThird() {
        return third;
    }

    public void setThird(Integer third) {
        this.third = third;
    }

    public Integer getFourth() {
        return fourth;
    }

    public void setFourth(Integer fourth) {
        this.fourth = fourth;
    }

    public Integer getFifth() {
        return fifth;
    }

    public void setFifth(Integer fifth) {
        this.fifth = fifth;
    }

    public Integer getSixth() {
        return sixth;
    }

    public void setSixth(Integer sixth) {
        this.sixth = sixth;
    }
}
