package com.migdev.meme.Controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.migdev.meme.Api.ApiUrls;
import com.migdev.meme.Api.NetworkCheck;
import com.migdev.meme.Model.PostModel;
import com.migdev.meme.Session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AllMemesForGuestController extends Application {
    Context context;
    AllMemesForGuestListenre allMemesForGuestListenre;

    String user;


    public AllMemesForGuestController(Context context, AllMemesForGuestListenre allMemesForGuestListenre) {
        this.context = context;
        this.allMemesForGuestListenre = allMemesForGuestListenre;
    }

    public void CheckNetwork(){
        //this.user = user;
        if (NetworkCheck.isNetworkConnected(context)){
            callMemeApi();
        }else {
            Toast.makeText(context, "Please Check Your Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void callMemeApi(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrls.AllMemeGuest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: "+ApiUrls.AllMemeGuest );

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("allmeme");

                    if (jsonArray.length()> 0){
                        Log.e("Post","found successFully");
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PostModel>>(){}.getType();
                        List<PostModel> list = gson.fromJson(jsonArray.toString(), type);

                        allMemesForGuestListenre.getMemesGuest(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString() );
            }
        })/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();

               // SessionManager sessionManager = new SessionManager(context);



                param.put("userName",user);

                Log.e(TAG, "getParams: "+param.toString() );

                return param;
            }
        }*/;
        requestQueue.add(stringRequest);
    }

    public interface AllMemesForGuestListenre {
        void getMemesGuest(List<PostModel> list);
    }
}
