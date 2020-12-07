package com.migdev.meme.Model;

public class UserListForFollowModel {

    public String id;
    public String userName;
    public String profileUrl;

    public UserListForFollowModel(String id, String userName, String profileUrl) {
        this.id = id;
        this.userName = userName;
        this.profileUrl = profileUrl;
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

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
