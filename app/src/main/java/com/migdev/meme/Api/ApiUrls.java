package com.migdev.meme.Api;

public class ApiUrls {


    public static String MainUrl = "http://api.lememer.com/";
    public static String AllMemeGuest = MainUrl+"getAllMeme";
    public static String AllMeme = MainUrl+"getMeme?userName=";
    public static String UserPost = MainUrl+"getUserPost?userName=";
    public static String PostMeme =  MainUrl+"storage/uploadFile";
    public static String SignUp =  MainUrl+"signup";
    public static String Login =  MainUrl+"login";
    public static String LikeUrl =  MainUrl+"like";
    public static String DisLikeUrl =  MainUrl+"dislike";
    public static String FollowUrl =  MainUrl+"follow";
    public static String UserListForFollow =  MainUrl+"userList?userName";

}
