package com.migdev.meme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

public class MainActivity extends AppCompatActivity {

    public static final int SPLASH_TIME = 3000;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(getApplicationContext());



        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                if (sessionManager.checkLogin()){
                    Intent intent = new Intent( MainActivity.this,HomeActivity.class );
                    startActivity( intent );
                    finish();
                }else {
                    Intent intent = new Intent( MainActivity.this,LoginActivity.class );
                    startActivity( intent );
                    finish();
                }
            }
        },SPLASH_TIME);


    }
}
