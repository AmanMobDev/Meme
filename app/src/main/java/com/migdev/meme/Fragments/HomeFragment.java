package com.migdev.meme.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.migdev.meme.Adapter.AllUserListAdapterForHome;
import com.migdev.meme.Adapter.PostAdapter;
import com.migdev.meme.Controller.AllMemesController;
import com.migdev.meme.Controller.DisLikeController;
import com.migdev.meme.Controller.LikeController;
import com.migdev.meme.Controller.UserListForFollowController;
import com.migdev.meme.Model.PostModel;
import com.migdev.meme.Model.UserListForFollowModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment implements PostAdapter.PostAdapterListener,
        AllMemesController.AllMemesListenere, DisLikeController.DisLikeLisetener, LikeController.LikeLisetener,
        AllUserListAdapterForHome.AllUserListAdapterListenerHome, UserListForFollowController.UserListForFollowListenere {

    PostAdapter postAdapter;
    AllUserListAdapterForHome allUserListAdapterForHome;
    List<PostModel> postModels;
    AllMemesController allMemeController;
    UserListForFollowController userListForFollowController;
    List<UserListForFollowModel> userListForFollowModelList;

    RecyclerView recyclerView ,recyclerUserList;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar_;

    DisLikeController disLikeController;
    LikeController likeController;

    SessionManager sessionManager;


    boolean isScrolling = false;
    int currentItems,totalItems,scrolloutitems;
    String like_,unLike_;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_popst);
        recyclerUserList = view.findViewById(R.id.recycler_userList);
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        progressBar_ = view.findViewById(R.id.proBar_);

        sessionManager = new SessionManager(getContext());
        HashMap<String,String> details = sessionManager.getUserDetails();
        final String user = details.get(sessionManager.KEY_NAME);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.blinking_animation);
        recyclerView.startAnimation(animation);

        allMemeController = new AllMemesController(getActivity(),this);
        allMemeController.CheckNetwork(user);
        userListForFollowController = new UserListForFollowController(getActivity(),this);
        userListForFollowController.checkNetwork(user);

        userListForFollowModelList = new ArrayList<>();
        allUserListAdapterForHome = new AllUserListAdapterForHome(getActivity(),this,userListForFollowModelList);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerUserList.setLayoutManager(layoutManager);
        recyclerUserList.setFocusable(false);
        recyclerUserList.setItemAnimator(new DefaultItemAnimator());
        recyclerUserList.setAdapter(allUserListAdapterForHome);

        disLikeController = new DisLikeController(getContext(),this);
        likeController = new LikeController(getContext(),this);

        postModels = new ArrayList<>( );
        postAdapter = new PostAdapter(getContext(),postModels,this);

        final RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setFocusable(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                allMemeController.CheckNetwork(user);
                userListForFollowController.checkNetwork(user);
                postAdapter.notifyDataSetChanged();
                allUserListAdapterForHome.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                currentItems = layoutManager1.getChildCount();
                totalItems = layoutManager1.getItemCount();

                scrolloutitems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrolloutitems == totalItems)){

                    isScrolling = false;

                }
            }
        });

        return view;
    }

    @Override
    public void getLikes(String id, String likes) {
        if (sessionManager.checkLogin()){

            likeController.checkInternet(id,likes);
            Log.e("TAG", "getDiLikes: "+id.toString()+" "+likes.toString() );

        }
    }

    @Override
    public void getDiLikes(String id, String dislikes) {
        if (sessionManager.checkLogin()){

            disLikeController.checkInternet(id,dislikes);
            Log.e("TAG", "getDiLikes: "+id.toString()+" "+dislikes.toString() );

        }
    }

    @Override
    public void getMemes(List<PostModel> list) {

        this.postModels = list ;

        if (list != null){
            Log.e(TAG, "getMemes: "+list.toString() );
            progressBar_.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            postAdapter.setData(postModels);
            postAdapter.notifyDataSetChanged();
        }else {
            progressBar_.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }

    }

    @Override
    public void followRequest(String userId, String followId) {

    }

    @Override
    public void getUserList(List<UserListForFollowModel> list) {
        this.userListForFollowModelList = list;

        Log.e(TAG, "getUserList: "+userListForFollowModelList );

        if(!list.isEmpty()){
            Log.e(TAG, "getUserList: "+list );
            recyclerUserList.setVisibility(View.VISIBLE);
            allUserListAdapterForHome.setData(list);
            allUserListAdapterForHome.notifyDataSetChanged();
        }
    }
}