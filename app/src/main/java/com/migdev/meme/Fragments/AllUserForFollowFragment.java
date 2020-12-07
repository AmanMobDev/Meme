package com.migdev.meme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.migdev.meme.Adapter.AllUserListAdapter;
import com.migdev.meme.Controller.UserFollowController;
import com.migdev.meme.Controller.UserListForFollowController;
import com.migdev.meme.Model.UserListForFollowModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AllUserForFollowFragment extends Fragment implements AllUserListAdapter.AllUserListAdapterListener,
        UserListForFollowController.UserListForFollowListenere, UserFollowController.UserFollowListenere {

    RecyclerView recyclerView;
    AllUserListAdapter allUserListAdapter;
    UserListForFollowController userListForFollowController;
    List<UserListForFollowModel> userListForFollowModelList;
    UserFollowController userFollowController;

    SessionManager sessionManager;

    String followerId;


    public AllUserForFollowFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AllUserForFollowFragment newInstance(String param1, String param2) {
        AllUserForFollowFragment fragment = new AllUserForFollowFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_all_user_for_follow, container, false);

        recyclerView = view.findViewById(R.id.userFollowList);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();
        HashMap<String, String > user = sessionManager.getUserDetails();

        String user_name  = user.get(sessionManager.KEY_NAME);

        userListForFollowController = new UserListForFollowController(getActivity(),this);
        userListForFollowController.checkNetwork(user_name);

        userFollowController = new UserFollowController(getActivity(),this);


        userListForFollowModelList = new ArrayList<>();
        allUserListAdapter = new AllUserListAdapter(getActivity(),this,userListForFollowModelList);


        final RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setFocusable(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(allUserListAdapter);

        return view;
    }

    @Override
    public void followRequest(String userId, String followId) {

        Log.e(TAG, "followRequest: "+userId+followId );

        userFollowController.checkNetwork(userId,followId);

    }

    @Override
    public void getUserList(List<UserListForFollowModel> list) {
        this.userListForFollowModelList = list;

        Log.e(TAG, "getUserList: "+userListForFollowModelList );

        if(!list.isEmpty()){
            Log.e(TAG, "getUserList: "+list );
            allUserListAdapter.setData(list);
            allUserListAdapter.notifyDataSetChanged();
            allUserListAdapter.notifyItemRemoved(list.size());
        }

    }
}