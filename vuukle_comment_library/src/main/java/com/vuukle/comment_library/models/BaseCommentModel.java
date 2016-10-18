package com.vuukle.comment_library.models;

import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class BaseCommentModel {


    @SerializedName("comment")
    private String comment;
    @SerializedName("name")
    private String name;
    @SerializedName("ts")
    private String ts;
    @SerializedName("up_votes")
    private Integer upVotes;
    @SerializedName("down_votes")
    private Integer downVotes;
    @SerializedName("comment_id")
    public String commentId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("replies")
    private Integer repliesCount;
    @SerializedName("user_points")
    private Integer userPoints;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("parent_id")
    private String parentId;
    @SerializedName("email")
    private String email;
    private boolean isReplyShow = false;
    private int commentLevel = 0;

    public BaseCommentModel(String comment, String name, String avatarUrl) {
        this.comment = comment;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public BaseCommentModel(String comment, String name) {
        this.comment = comment;
        this.name = name;
    }

    public BaseCommentModel() {
    }

    public String getComment() {
        return decodeString(comment);
    }

    public void setComment(String comment) {
        this.comment = encodeString(comment);
    }

    public String getName() {
        return decodeString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Integer getUpVotes() {
        if(upVotes == null){
            return 0;
        }
        return upVotes;
    }

    public void setUpVotes(Integer upVotes) {
        this.upVotes = upVotes;
    }

    public Integer getDownVotes() {
        if(downVotes == null){
            downVotes = 0;
        }
        return downVotes;
    }

    public void setDownVotes(Integer downVotes) {
        this.downVotes = downVotes;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRepliesCount() {
        if(repliesCount == null){
            return 0;
        }
        return repliesCount;
    }

    public void setRepliesCount(Integer repliesCount) {
        this.repliesCount = repliesCount;
    }

    public Integer getUserPoints() {
        if(userPoints == null){
            return 0;
        }
        return userPoints;
    }

    public void setUserPoints(Integer userPoints) {
        this.userPoints = userPoints;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInitials() {
        String initials = "";
        for (int y = 0; y < getName().length(); y++) {
            if (Character.isUpperCase(getName().charAt(y))) {
                char uppercaseChar = getName().charAt(y);
                initials = initials + uppercaseChar;
            }
        }
        if (initials.length() > 2) {
            return initials.substring(0, 2);
        }else {
            return initials;
        }
    }

    private String decodeString(String inputString){
        String decodedString = "";
        try {
            decodedString =  URLDecoder.decode(inputString ,"UTF-8");
            if (decodedString.contains("<br/>")){
                decodedString = decodedString.replace("<br/>", "\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedString;
    }

    private String encodeString(String inputString){
        String encodedString = "";
        try {
            encodedString =  URLEncoder.encode(inputString ,"UTF-8");
            if (encodedString.contains("<br/>")){
                encodedString = encodedString.replace("<br/>", "\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedString;
    }

    public boolean isReplyShow() {
        return isReplyShow;
    }

    public void setReplyShow(boolean replyShow) {
        isReplyShow = replyShow;
    }

    public int getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(int commentLevel) {
        this.commentLevel = commentLevel;
    }
}