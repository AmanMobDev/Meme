package com.migdev.meme.Session;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.migdev.meme.Activity.LoginActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERID = "_id";
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "userName";
    public static final String KEY_PASSWORD = "password";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PROFILEURL = "profileUrl";
    public static final String KEY_FOLLOWER = "follower";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public static String getKeyUserid() {
        return KEY_USERID;
    }

    public static String getKeyName() {
        return KEY_NAME;
    }

    public static String getKeyPassword() {
        return KEY_PASSWORD;
    }

    public static String getKeyEmail() {
        return KEY_EMAIL;
    }

    public static String getKeyProfileurl() {
        return KEY_PROFILEURL;
    }

    public static String getKeyFollower() {
        return KEY_FOLLOWER;
    }

    public void createLoginSession(String _id, String userName, String password, String email, String profileUrl, String follower){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERID, _id);
        editor.putString(KEY_NAME, userName);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PROFILEURL, profileUrl);
        editor.putString(KEY_FOLLOWER, follower);

        // commit changes
        editor.commit();
    }

    public void updateDetails(String _id, String userName, String password, String email, String profileUrl, String follower){

        editor.putString(KEY_USERID, _id);
        editor.putString(KEY_NAME, userName);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PROFILEURL, profileUrl);
        editor.putString(KEY_FOLLOWER, follower);
        editor.commit();
    }

    public void createSignupSession(String _id, String userName, String password, String email, String profileUrl, String follower){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERID, _id);
        // Storing name in pref
        editor.putString(KEY_NAME, userName);
        // Storing email in pref
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PROFILEURL, profileUrl);
        editor.putString(KEY_FOLLOWER, follower);
        // commit changes
        editor.commit();
    }


    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return false;
        }
        return true;
    }




    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PROFILEURL, pref.getString(KEY_PROFILEURL, null));
        user.put(KEY_FOLLOWER, pref.getString(KEY_FOLLOWER, null));

        return user;
    }


    public boolean logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        return false;
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}