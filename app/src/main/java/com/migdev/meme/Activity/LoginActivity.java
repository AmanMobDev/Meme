package com.migdev.meme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.migdev.meme.Controller.LoginController;
import com.migdev.meme.Model.UserDetailsModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

public class LoginActivity extends AppCompatActivity implements LoginController.LoginListener {

    TextView nextSignPage;
    Button login;
    EditText user,password;
    Button guestButton;
    UserDetailsModel userDetailsModels;
    ProgressBar progressBar;
    ImageView imageView;

    LoginController loginController;

    SessionManager sessionManager;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().hide();

        nextSignPage = findViewById(R.id.move_signpage);
        user = findViewById(R.id.loginusername);
        password = findViewById(R.id.loginuserpassword);
        login = findViewById(R.id.loginButton);
        guestButton = findViewById(R.id.guest_login);
        progressBar = findViewById(R.id.proBarlogin);

        sessionManager = new SessionManager(getApplicationContext());

        

        loginController = new LoginController(getApplicationContext(),this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = user.getText().toString();
                String pass = password.getText().toString();

                if (!name.isEmpty() || !pass.isEmpty()){

                    if (!name.isEmpty()){

                        if (!pass.isEmpty()){

                            loginController.checkConnection(name,pass);
                            login.setEnabled(false);
                            progressBar.setVisibility(View.VISIBLE);
                            closeKeyboard();

                        }else {
                            password.setError("Enter Password");
                        }

                    }else {
                        user.setError("Enter User Name");
                    }

                }else {
                    user.setError("Enter User Name");
                    password.setError("Enter Password");
                }


            }
        });




        nextSignPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
            }
        });


        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
       /* if (doubleBackToExitPressedOnce) {
            finish();
            doubleBackToExitPressedOnce=false;
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                //Toast.makeText(getApplicationContext(), "Please ", Toast.LENGTH_SHORT).show();
            }
        }, 2000);*/
       finish();

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

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDetailsFound(UserDetailsModel userDetailsModel) {
        this.userDetailsModels = userDetailsModel;
        Log.i("TAG", "onDetailsFound: "+userDetailsModel.toString());
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        this.finish();
        sessionManager.createLoginSession(userDetailsModel.get_id(),userDetailsModel.getUserName(),userDetailsModel.getPassword(),userDetailsModel.getEmail(),userDetailsModel.getProfileUrl(),userDetailsModel.getFollower());

    }

    @Override
    public void onError(boolean status,String msg) {
        Log.e("TAG", "onError: "+status );
        if (status){
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            login.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        }
    }
}