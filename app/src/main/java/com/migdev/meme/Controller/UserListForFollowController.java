package com.migdev.meme.Controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.migdev.meme.Api.ApiUrls;
import com.migdev.meme.Api.NetworkCheck;
import com.migdev.meme.Model.PostModel;
import com.migdev.meme.Model.UserListForFollowModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.ContentValues.TAG;

public class UserListForFollowController extends Application {
    Context context;
    UserListForFollowListenere userListForFollowListenere;

    String user_name;

    public UserListForFollowController(Context context, UserListForFollowListenere userListForFollowListenere) {
        this.context = context;
        this.userListForFollowListenere = userListForFollowListenere;
    }


    public void checkNetwork(String user_name){
        this.user_name = user_name;
        if (NetworkCheck.isNetworkConnected(context)){
            callUserListApi();
        }else {
            Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void callUserListApi(){
        final String url = ApiUrls.UserListForFollow+user_name;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: "+url );

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("userProfile");

                    Log.e(TAG, "onResponse: "+jsonArray );

                    if (jsonArray.length() > 0){
                        Log.e(TAG, "UserList "+jsonArray);
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<UserListForFollowModel>>(){}.getType();
                        List<UserListForFollowModel> list = gson.fromJson(jsonArray.toString(), type);

                        userListForFollowListenere.getUserList(list);

                        //Log.e(TAG, "onResponse: "+list );
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
        requestQueue.add(stringRequest);
    }

    public interface UserListForFollowListenere {
        void getUserList(List<UserListForFollowModel> list);
    }
}
