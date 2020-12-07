package com.migdev.meme.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.migdev.meme.Activity.HomeActivity;
import com.migdev.meme.Activity.LoginActivity;
import com.migdev.meme.Activity.MainActivity;
import com.migdev.meme.Adapter.AllPostAdapterInProfile;
import com.migdev.meme.Controller.AllUserPostController;
import com.migdev.meme.Model.ProfileAllPostModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements AllPostAdapterInProfile.AllPostAdapterInProfileListenere,
        AllUserPostController.AllUserPostListener {

    SessionManager sessionManager;

    ImageView logoutButton,listFollower;
    CircleImageView circleImageView;
    TextView textView;
    boolean doubleBackToExitPressedOnce = true;

    GridView gridView;
    RecyclerView recyclerView;


    List<ProfileAllPostModel> profileAllPostModels;

    AllUserPostController allUserPostController;
    AllPostAdapterInProfile postAdapterInProfile;

    TextView total_post,total_follower,total_following;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getActivity());
        HashMap<String,String> userDetails = sessionManager.getUserDetails();
        String user_ = userDetails.get(sessionManager.KEY_NAME);
        String user_image = userDetails.get(sessionManager.KEY_PROFILEURL);


        logoutButton = view.findViewById(R.id.logoutbutton);
        circleImageView = view.findViewById(R.id.profile_image);
        listFollower = view.findViewById(R.id.followerList);
        textView = view.findViewById(R.id.name_user);
        recyclerView = view.findViewById(R.id.allPostRecycler);
        total_post = view.findViewById(R.id.TotalPost);
        total_follower = view.findViewById(R.id.TotalFollower);
        total_following = view.findViewById(R.id.TotalFollowing);



        Log.e("TAG", "onCreate: "+user_image );
        Glide.with(getActivity()).load(user_image).into(circleImageView);
        textView.setText(user_);


        profileAllPostModels = new ArrayList<>();

        postAdapterInProfile = new AllPostAdapterInProfile(getActivity(),this,profileAllPostModels);


        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4, LinearLayoutManager.VERTICAL,false));
        recyclerView.setFocusable(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postAdapterInProfile);



        allUserPostController = new AllUserPostController(getActivity(),this);
        allUserPostController.callApi(user_);


        listFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllUserForFollowFragment allUserForFollowFragment = new AllUserForFollowFragment();
               loadFragment(allUserForFollowFragment);
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("Are You Sure ?").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.logoutUser();
                        Log.e("Session", "onClick: ClearSession");
                        //HomeActivity.super.onBackPressed();
                        Toast.makeText(getActivity(), "Successfully Signed Out!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog= builder.create();
                alertDialog.show();
            }
        });

        return view;
    }



    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame,fragment).addToBackStack("").commit();
    }


    @Override
    public void getMemes(List<ProfileAllPostModel> list) {
        this.profileAllPostModels = list;

        if (!list.isEmpty()){
            postAdapterInProfile.setData(list);
            postAdapterInProfile.notifyDataSetChanged();
        }else {

        }

        Log.e("TAG", "getMemes: "+list.toString() );
    }
}