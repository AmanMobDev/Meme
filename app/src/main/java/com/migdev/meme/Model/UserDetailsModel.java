package com.migdev.meme.Model;

public class UserDetailsModel {
    public String  _id;
    public String userName;
    public String password;
    public String email;
    public String profileUrl;
    public String follower;



    public UserDetailsModel(String _id, String userName, String password, String email, String profileUrl, String follower) {
        this._id = _id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.profileUrl = profileUrl;
        this.follower = follower;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

}
