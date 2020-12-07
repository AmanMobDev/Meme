package com.migdev.meme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.migdev.meme.Controller.AllUserPostController;
import com.migdev.meme.Model.ProfileAllPostModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements AllUserPostController.AllUserPostListener {

    SessionManager sessionManager;

    ImageView logoutButton;
    CircleImageView circleImageView;
    TextView textView;
    boolean doubleBackToExitPressedOnce = false;

    GridView gridView;
    List<ProfileAllPostModel> profileAllPostModels;
    AllUserPostController allUserPostController;
    public static  String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();


    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        super.onBackPressed();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                // Toast.makeText(getApplicationContext(), "Please ", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                doubleBackToExitPressedOnce=false;

            }else {
                onBackPressed();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=true;
                }
            }, 1000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getMemes(List<ProfileAllPostModel> list) {
        this.profileAllPostModels = list;

        Log.e("TAG", "getMemes: "+list.toString() );
    }
}