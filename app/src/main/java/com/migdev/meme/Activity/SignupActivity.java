package com.migdev.meme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.migdev.meme.Controller.SignUpController;
import com.migdev.meme.Model.UserDetailsModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

public class SignupActivity extends AppCompatActivity implements SignUpController.SignUpLisetener {

    EditText name,email,password,recheckpassword;
    Button signUpButton;
    TextView loginText;
    ProgressBar progressBar;
    ImageView imageView;
    SessionManager sessionManager;

    SignUpController signUpController;

    boolean doubleBackToExitPressedOnce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        sessionManager = new SessionManager(getApplicationContext());

       // sessionManager.checkLogin();

        name = findViewById(R.id.username);
        email = findViewById(R.id.useremail);
        password = findViewById(R.id.userpassword);
        recheckpassword = findViewById(R.id.match_password);
        signUpButton = findViewById(R.id.signUp);
        loginText = findViewById(R.id.move_loginpage);
        progressBar = findViewById(R.id.proBarSignup);
        //imageView = findViewById(R.id.loginIcon);

        signUpController = new SignUpController(getApplicationContext(),this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_ = name.getText().toString();
                String email_ = email.getText().toString();
                String password_ = password.getText().toString();
                String repassword = recheckpassword.getText().toString();



                if (!name_.isEmpty() || !email_.isEmpty() || !password_.isEmpty()){

                    if (!name_.isEmpty()){

                        if (!email_.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email_).matches()){

                            if (!password_.isEmpty()){

                                if (!repassword.isEmpty() && password_.equalsIgnoreCase(repassword)){

                                    signUpButton.setEnabled(false);
                                    signUpController.fillData(name_,email_,password_);
                                    progressBar.setVisibility(View.VISIBLE);
                                    closeKeyboard();

                                }else {
                                    recheckpassword.setError("Password is not match");
                                }


                            }else {
                                password.setError("Enter Password");
                            }

                        }else {
                            email.setError("E-mail Invalid");
                        }

                    }else {
                        name.setError("Fill Name");
                    }

                }else {
                    name.setError("Fill Name");
                    email.setError("Fill E-mail");
                    password.setError("Fill Password");
                }


            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            doubleBackToExitPressedOnce=false;

        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=true;
                // Toast.makeText(getApplicationContext(), "Please ", Toast.LENGTH_SHORT).show();
            }
        }, 1000);

        super.onBackPressed();
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
    public void onSignUpComplete(UserDetailsModel userDetailsModel,String msg) {

        if (msg != null){
            name.setText("");
            email.setText("");
            password.setText("");
            recheckpassword.setText("");
            signUpButton.setEnabled(false);
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(SignupActivity.this,HomeActivity.class));
            sessionManager.createSignupSession(userDetailsModel.get_id(),userDetailsModel.getUserName(),userDetailsModel.getPassword(),userDetailsModel.getEmail(),userDetailsModel.getProfileUrl(),userDetailsModel.getFollower());
            finish();
        }

    }
}