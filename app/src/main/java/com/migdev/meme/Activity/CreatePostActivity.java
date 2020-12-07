package com.migdev.meme.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.migdev.meme.Api.ApiUrls;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CreatePostActivity extends AppCompatActivity {



    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 999;

    public static final String TAG = "Post";

    private File destination = null;
    private InputStream inputStreamImg;


    Bitmap bitmap;
    ImageView image,addimage;
    CardView post_upload;
    EditText pollText;
    ProgressBar progressBar;
    LinearLayout upl,iml;

    SessionManager sessionManager;

    boolean doubleBackToExitPressedOnce = false;


    private Handler hdlr = new Handler();

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        sessionManager = new SessionManager(getApplicationContext());


        HashMap<String,String> value = sessionManager.getUserDetails();
        final String uName = value.get(sessionManager.KEY_NAME);
        final String uProfile = value.get(sessionManager.KEY_PROFILEURL);


        if (sessionManager.checkLogin()){
            checkPermission(WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
            setImage();
        }




        AndroidNetworking.initialize(getApplicationContext());



        image = findViewById(R.id.imageView);
        addimage = findViewById(R.id.addimageView);
        post_upload = findViewById(R.id.upload);
        pollText = findViewById(R.id.caption);
        progressBar = findViewById(R.id.pBar);
        upl = findViewById(R.id.uploadLayout);


        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.checkLogin()){
                    checkPermission(WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
                    setImage();
                }
            }
        });



        post_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "onClick: Post");

                String imageCaption = pollText.getText().toString();


                String  imageData = String.valueOf(destination);

                Log.e(TAG, "onClick: "+imageData.toString() );

                upl.setVisibility(View.VISIBLE);
                closeKeyboard();

                AndroidNetworking.upload(ApiUrls.PostMeme)
                        .addMultipartFile("file", new File(imageData))
                        .addMultipartParameter("userName",uName)
                        .addMultipartParameter("profileUrl",uProfile)
                        .addMultipartParameter("poll",imageCaption)
                        .setTag("uploadTest")
                        .setPriority( Priority.HIGH)
                        .build()
                        .setUploadProgressListener(new UploadProgressListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onProgress(long bytesUploaded,  long totalBytes) {
                                // do anything with progress
                                int pro = (int) bytesUploaded;
                                int totalpro = (int) totalBytes;

                                for (int i = pro; i < totalpro; i++ ){

                                    progressBar.setProgress(pro);
                                    Log.e(TAG, "onProgress: "+ pro );

                                }

                            }
                        })
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                try {
                                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                                    Log.e(TAG, "onResponse: "+jsonObject.toString() );
                                    String msg = jsonObject.getString( "msg" );

                                    if (msg != null){
                                        startActivity(new Intent(CreatePostActivity.this,HomeActivity.class));
                                        finish();
                                    }else{

                                    }
                                    Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT ).show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Log.e( TAG, "onError: ", error);
                            }
                        });

            }
        });
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


    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(CreatePostActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            CreatePostActivity.this,
                            new String[] { permission },
                            requestCode);

        }
        else {

        }
    }


    public void setImage(){
        try {
            PackageManager pm = getPackageManager();
            int hasPerm1 = pm.checkPermission( WRITE_EXTERNAL_STORAGE, getPackageName() );
            if (hasPerm1 == PackageManager.PERMISSION_GRANTED){
                Intent pickPhoto = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                pickPhoto.setType("image/*");
                startActivityForResult( pickPhoto, PICK_IMAGE_GALLERY );
            } else
                Toast.makeText( getApplicationContext(), "Storage Permission error", Toast.LENGTH_SHORT ).show();
        }catch (Exception e) {
            //Toast.makeText( getApplicationContext(), "Camera Permission error", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        inputStreamImg = null;
        if (data != null){
            if (requestCode == PICK_IMAGE_GALLERY) {
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap( getApplicationContext().getContentResolver(), selectedImage );
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress( Bitmap.CompressFormat.JPEG, 50, bytes );
                    Log.e( "Activity", "Pick from Gallery::>>> " );

                    imgPath = getRealPathFromURI( selectedImage );
                    destination = new File( imgPath.toString() );
                    image.setImageBitmap( bitmap );
                    //textView.setText( imgPath );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {

        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery( contentUri, proj, null, null, null );
        int column_index = cursor.getColumnIndexOrThrow( MediaStore.Audio.Media.DATA );
        cursor.moveToFirst();
        return cursor.getString( column_index );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText( getApplicationContext(),
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT )
                        .show();
                setImage();
            } else {
                Toast.makeText( getApplicationContext(),
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT )
                        .show();
            }
        }
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }

}