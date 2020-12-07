package com.migdev.meme.Model;

import android.widget.ListAdapter;

import java.util.ArrayList;

public class ProfileAllPostModel  {

    public String id;
    public String imageUrl;
    public String email;
    public String profileUrl;
    public String follower;

    public ProfileAllPostModel(String id, String imageUrl, String email, String profileUrl, String follower) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.email = email;
        this.profileUrl = profileUrl;
        this.follower = follower;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
