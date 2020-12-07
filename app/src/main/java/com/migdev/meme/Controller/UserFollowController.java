package com.migdev.meme.Controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.migdev.meme.Api.ApiUrls;
import com.migdev.meme.Api.NetworkCheck;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class UserFollowController extends Application {
    Context context;
    UserFollowListenere userFollowListenere;
    String followid,followerID;

    public UserFollowController(Context context, UserFollowListenere userFollowListenere) {
        this.context = context;
        this.userFollowListenere = userFollowListenere;
    }


    public  void checkNetwork(String userID,String followid){
        this.followid = userID;
        this.followerID = followid;
        if (NetworkCheck.isNetworkConnected(context)){
            callUserFollowApi();
        }else {
            Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public JSONObject jsonObject(){
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject();
            jsonObject.put("userId",followid);
            jsonObject.put("followerId",followerID);

            Log.e(TAG, "jsonObject: "+jsonObject );
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void callUserFollowApi(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.FollowUrl, jsonObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    String jsonObject1 = jsonObject.getString("msg");
                    Log.e(TAG, "onResponse: "+ApiUrls.FollowUrl );
                    Log.e(TAG, "onResponse: "+jsonObject1 );
                    if (jsonObject.getString("errorCode").equals(false)){
                        Log.e(TAG, "onResponse: "+jsonObject1 );
                        Log.e(TAG, "onResponse: "+ApiUrls.FollowUrl );
                    }else {
                        Log.e(TAG, "Else: "+response );
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error );
            }
        });
        requestQueue.add(jsonObjectRequest);
    }



    public interface UserFollowListenere {
    }
}
