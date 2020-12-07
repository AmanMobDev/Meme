package com.migdev.meme.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.migdev.meme.Api.ApiUrls;
import com.migdev.meme.Api.NetworkCheck;
import com.migdev.meme.Model.UserDetailsModel;
import com.migdev.meme.Session.SessionManager;

import org.json.JSONObject;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class LoginController {

    Context context;
    LoginListener loginListener;

    SessionManager sessionManager;

    String name,password,msg;


    public LoginController(Context context, LoginListener loginListener) {
        this.context = context;
        this.loginListener = loginListener;
    }

    public void checkConnection(String name,String password){
        this.name = name;
        this.password = password;
        if (NetworkCheck.isNetworkConnected(context)){
            callApi();
        }else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void  callApi(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.Login, jsonObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try{
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("msg"));

                    msg = jsonObject.getString("msg");

                    UserDetailsModel userModel = new UserDetailsModel(jsonObject1.getString("_id"),jsonObject1.getString("userName"),jsonObject1.getString("password"),jsonObject1.getString("email"),
                            jsonObject1.getString("profileUrl"),jsonObject1.getString("follower"));

                    loginListener.onDetailsFound(userModel);


                    Log.i("jsonObject",jsonObject1.toString());
                    Log.i(TAG, "onResponse: "+userModel.toString() );

                    /*if (jsonObject.getString("errorCode").equals(false)){
                        Log.e(TAG, ": "+jsonObject );

                    }else {
                        Toast.makeText(context, "User Name And Password Is Not Correct", Toast.LENGTH_SHORT).show();
                    }*/


                }catch (Exception e){
                    Log.e(VolleyLog.TAG, "onResponse:E "+ response.toString() );
                    Log.e(VolleyLog.TAG, "onResponse:E "+ e.toString() );
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString() );
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public JSONObject jsonObject(){
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject();
            jsonObject.put("userName",name);
            jsonObject.put("password",password);
            Log.e(TAG, "jsonObject: "+jsonObject );
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonObject;
    }

    public interface LoginListener {
        void onDetailsFound(UserDetailsModel userDetailsModel);
        void onError(boolean status,String msg);
    }
}
