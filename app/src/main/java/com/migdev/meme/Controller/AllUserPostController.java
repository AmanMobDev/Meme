package com.migdev.meme.Controller;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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
import com.migdev.meme.Model.ProfileAllPostModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.migdev.meme.Activity.CreatePostActivity.TAG;

public class AllUserPostController extends Application {

   Context context;
   AllUserPostListener allUserPostListener;

   String user;

    public AllUserPostController(Context context, AllUserPostListener allUserPostListener) {
        this.context = context;
        this.allUserPostListener = allUserPostListener;
    }

    public void callApi(String user){
        this.user = user;
        if (NetworkCheck.isNetworkConnected(context)){
            callMemeApi();
        }else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }




    public void callMemeApi(){
        final String url = ApiUrls.UserPost+user;
        Log.e(TAG, "callMemeApi: "+url.toString() );

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                Log.e("onResponse", "onResponse: "+ url.toString());

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("allmeme");

                    if(jsonArray.length()>0){
                        Log.e("Post","found successFully");
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<ProfileAllPostModel>>(){}.getType();
                        List<ProfileAllPostModel> list = gson.fromJson(jsonArray.toString(), type);

                        allUserPostListener.getMemes(list);
                    }

                }catch (Exception e){
                    Log.e("catch", "onResponse: "+ e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", "onErrorResponse: "+error.toString());
            }

        });/*{
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String , String > param = new HashMap<String,String>();
                //Log.e(VolleyLog.TAG, "getParams: "+ limit);
                param.put("userName", user);
                return param;
            }
        };*/
        requestQueue.add(stringRequest);
    }

    public interface AllUserPostListener {

        void getMemes(List<ProfileAllPostModel> list);
    }
}
