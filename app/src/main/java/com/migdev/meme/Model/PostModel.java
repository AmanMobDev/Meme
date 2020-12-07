package com.migdev.meme.Model;

public class PostModel {

    public String id;
    public String userName;
    public String imageUrl;
    public String profileUrl;
    public String poll;
    public String totalLikeDislike;
    public String like;
    public String disLike;
    public String likedByUser;
    public String disLikedByUser;
    public boolean likedFlag;
    public boolean disLikedFlag;


    public PostModel(String id, String userName, String imageUrl, String profileUrl, String poll, String totalLikeDislike, String like, String disLike, String likedByUser, String disLikedByUser, boolean likedFlag, boolean disLikedFlag) {
        this.id = id;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
        this.poll = poll;
        this.totalLikeDislike = totalLikeDislike;
        this.like = like;
        this.disLike = disLike;
        this.likedByUser = likedByUser;
        this.disLikedByUser = disLikedByUser;
        this.likedFlag = likedFlag;
        this.disLikedFlag = disLikedFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getPoll() {
        return poll;
    }

    public void setPoll(String poll) {
        this.poll = poll;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDisLike() {
        return disLike;
    }

    public void setDisLike(String disLike) {
        this.disLike = disLike;
    }

    public String getLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(String likedByUser) {
        this.likedByUser = likedByUser;
    }

    public String getDisLikedByUser() {
        return disLikedByUser;
    }

    public void setDisLikedByUser(String disLikedByUser) {
        this.disLikedByUser = disLikedByUser;
    }

    public String getTotalLikeDislike() {
        return totalLikeDislike;
    }

    public void setTotalLikeDislike(String totalLikeDislike) {
        this.totalLikeDislike = totalLikeDislike;
    }

    public boolean isLikedFlag() {
        return likedFlag;
    }

    public void setLikedFlag(boolean likedFlag) {
        this.likedFlag = likedFlag;
    }

    public boolean isDisLikedFlag() {
        return disLikedFlag;
    }

    public void setDisLikedFlag(boolean disLikedFlag) {
        this.disLikedFlag = disLikedFlag;
    }
}
