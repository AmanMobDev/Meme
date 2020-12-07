package com.migdev.meme.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.migdev.meme.Api.ApiUrls;
import com.migdev.meme.Api.NetworkCheck;
import com.migdev.meme.Model.UserDetailsModel;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class SignUpController {

    Context context;
    SignUpLisetener signUpLisetener;

    String name,email,password;

    public SignUpController(Context context, SignUpLisetener signUpLisetener) {
        this.context = context;
        this.signUpLisetener = signUpLisetener;
    }

    public void fillData(String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;

        if (NetworkCheck.isNetworkConnected(context)){
            callApi();
        }else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
     }

     public void callApi(){
         RequestQueue requestQueue = Volley.newRequestQueue(context);
         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.SignUp, jsonMethod(), new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {



                 try{
                         JSONObject userobject = new JSONObject(String.valueOf(response));
                     JSONObject jsonObject1 = new JSONObject(userobject.getString("msg"));
                         String msg = userobject.getString("msg");
                     //Log.e(TAG, "onResponse: "+msg.toString() );
                     UserDetailsModel userModel = new UserDetailsModel(jsonObject1.getString("_id"),jsonObject1.getString("userName"),jsonObject1.getString("password"),jsonObject1.getString("email"),
                             jsonObject1.getString("profileUrl"),jsonObject1.getString("follower"));


                     Log.e(TAG, "onResponse: "+userModel._id );

                         //Log.e("User","response2:" + message);
                         signUpLisetener.onSignUpComplete(userModel,msg);
                     } catch (Exception e){
                     Log.e("User","response2:"+e.toString());
                     //signUpLisetener.onSignUpComplete(false);
                 }

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.e("User","volley:"+error.toString());
                 //signUpLisetener.onSignUpComplete(false);
             }
         });
         requestQueue.add(jsonObjectRequest);
     }


     private JSONObject jsonMethod(){
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject();
            jsonObject.put("userName", name);
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            Log.e(TAG, "jsonMethod: "+jsonObject.toString() );
        }catch (Exception e){
            e.printStackTrace();
        }


        return jsonObject;
     }

    public interface SignUpLisetener {
        void onSignUpComplete(UserDetailsModel userDetailsModel,String msg);
    }
}
