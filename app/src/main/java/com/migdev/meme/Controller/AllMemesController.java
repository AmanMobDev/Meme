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

public class AllMemesController extends Application {
    Context context;
    AllMemesListenere allMemesListenere;

    String name_;


    public AllMemesController(Context context, AllMemesListenere allMemesListenere) {
        this.context = context;
        this.allMemesListenere = allMemesListenere;
    }

    public void CheckNetwork(String name){
        this.name_ = name;
        if (NetworkCheck.isNetworkConnected(context)){
            callMemeApi();
        }else {
            Toast.makeText(context, "Please Check Your Internet", Toast.LENGTH_SHORT).show();
        }
    }





    public void callMemeApi(){
        Log.e(TAG, "callMemeApi: "+name_ );
        final String url = ApiUrls.AllMeme+name_;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: "+url );

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("allmeme");

                    if (jsonArray.length()> 0){
                        Log.e("Post","found successFully");
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PostModel>>(){}.getType();
                        List<PostModel> list = gson.fromJson(jsonArray.toString(), type);

                        allMemesListenere.getMemes(list);

                        /*Log.e(TAG, "onResponse: "+list.toString() );

                        for (int i = 0; i<list.size(); i++){
                            //list.get(i);
                            Log.e(TAG, "onResponse: "+list.get(i).isLikedFlag());
                            Log.e(TAG, "onResponse: "+list.get(i).isDisLikedFlag());
                        }*/
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




                param.put("userName",name);



                return param;
            }
        }*/;
        requestQueue.add(stringRequest);
    }

    public interface AllMemesListenere {
        void getMemes(List<PostModel> list);
    }
}
