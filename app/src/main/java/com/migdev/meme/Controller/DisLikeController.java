package com.migdev.meme.Controller;

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
import com.migdev.meme.Api.ApiUrls;
import com.migdev.meme.Api.NetworkCheck;
import com.migdev.meme.Session.SessionManager;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class DisLikeController {
    Context context;
    DisLikeLisetener disLikeLisetener;

    String userLikedBy,postId;

    public DisLikeController(Context context, DisLikeLisetener disLikeLisetener) {
        this.context = context;
        this.disLikeLisetener = disLikeLisetener;
    }

    public void checkInternet( String postId, String userLikedBy) {
        this.postId = postId;
        this.userLikedBy = userLikedBy;
        if (NetworkCheck.isNetworkConnected(context)){
            callDisLikeApi();
        }else {
            Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void callDisLikeApi(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiUrls.DisLikeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String, String>();

                param.put("id",postId);
                param.put("disLikedByUser",userLikedBy);

                Log.e(TAG, "getParams: "+param.toString() );
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public interface DisLikeLisetener {
    }
}
