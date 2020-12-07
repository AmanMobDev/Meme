package com.migdev.meme.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.migdev.meme.Controller.DisLikeController;
import com.migdev.meme.Controller.LikeController;
import com.migdev.meme.Fragments.HomeFragment;
import com.migdev.meme.Fragments.NotificationFragment;
import com.migdev.meme.Fragments.ProfileFragment;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;


public class HomeActivity extends AppCompatActivity{


    DisLikeController disLikeController;
    LikeController likeController;
    CardView addPostButton, goForLogin;
    FrameLayout frameLayout;
    //ProgressBar progressBar;

    private ActionBar toolbar;

    SessionManager sessionManager;

    private FirebaseAnalytics mFirebaseAnalytics;

    boolean doubleBackToExitPressedOnce = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        sessionManager = new SessionManager(getApplicationContext());

        frameLayout = findViewById(R.id.frame);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        HomeFragment fragment = new HomeFragment();
        loadFragment(fragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.profile:
                        //startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                        sessionManager.checkLogin();
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                       break;
                    case R.id.add_post:
                        sessionManager.checkLogin();
                        Intent intent = new Intent(HomeActivity.this,CreatePostActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.notification:
                        Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();
                         fragment = new NotificationFragment();
                        loadFragment(fragment);
                        break;
                }
                return false;
            }
        });

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (doubleBackToExitPressedOnce) {
                    doubleBackToExitPressedOnce=false;
                }else{
                    super.onBackPressed();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 1000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
