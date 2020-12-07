package com.migdev.meme.Adapter;


import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.migdev.meme.Model.PostModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHold> {

    List<PostModel> postModels;
    Context context;
    PostAdapterListener postAdapterListener;
    int totalLIKE,totalDISLIKE;


    SessionManager sessionManager;
    String user;


    public PostAdapter(Context context,List<PostModel> postModel, PostAdapterListener postAdapterListener) {
        this.context = context;
        this.postModels = postModel;
        this.postAdapterListener = postAdapterListener;
    }

    public void setData(List<PostModel> items){
        this.postModels = items;
    }

    public class MyViewHold extends RecyclerView.ViewHolder {

        ImageView profile_Image,post_Image,likeButton, unlikeButton,likeImageShow;
        TextView profile_Name,profile_Follower,_comment,likes,unlikes,totalReact_;

        public MyViewHold(@NonNull View itemView) {
            super(itemView);

            profile_Image = itemView.findViewById(R.id.profile_image);
            post_Image = itemView.findViewById(R.id.post_image);
            profile_Name = itemView.findViewById(R.id.profile_name);
            likeButton = itemView.findViewById(R.id.like);
            unlikeButton = itemView.findViewById(R.id.unlike);
            _comment = itemView.findViewById(R.id.pollText);
            totalReact_ = itemView.findViewById(R.id.totalReact);
            likes = itemView.findViewById(R.id.total_like);
            unlikes = itemView.findViewById(R.id.total_unlike);
            likeImageShow = itemView.findViewById(R.id.likeImageShowOnPost);

            sessionManager = new SessionManager(context);
            HashMap<String,String > details = sessionManager.getUserDetails();
            user = details.get(sessionManager.KEY_NAME);

        }
    }


    @NonNull
    @Override
    public PostAdapter.MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.post_layout,parent,false);
        MyViewHold myViewHold = new MyViewHold(viewGroup);
        return myViewHold;

    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.MyViewHold holder, int position) {

        final PostModel postModel_ = postModels.get(position);


            holder.profile_Name.setText("@"+postModel_.getUserName());


            holder._comment.setText(postModel_.getPoll());


            Glide.with(context).load(postModel_.getImageUrl()).placeholder(R.drawable.ic_place_bg).into(holder.post_Image);


            Glide.with(context).load(postModel_.getProfileUrl()).placeholder(R.drawable.ic_userdummy).into(holder.profile_Image);


        boolean checkStatus = postModel_.isLikedFlag();
        //Log.e(TAG, "onClick: "+checkStatus );
        if (checkStatus){
            holder.likeButton.setImageResource(R.drawable.ic_likefil);
        }else {
            holder.likeButton.setImageResource(R.drawable.ic_like);
        }


        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.checkLogin()){

                    holder.likeButton.setImageResource(R.drawable.ic_likefil);

                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.blinking_animation);
                    //holder.likeImageShow.setVisibility(View.VISIBLE);
                    holder.likeButton.startAnimation(animation);

                    totalLIKE = Integer.parseInt(postModel_.getTotalLikeDislike()+1);

                    postAdapterListener.getLikes(postModel_.getId(),user);

                }



            }
        });

        boolean checkDislikeStatus = postModel_.isDisLikedFlag();
        //Log.e(TAG, "onClick: "+checkDislikeStatus );

        if (checkDislikeStatus){
            holder.unlikeButton.setImageResource(R.drawable.ic_dislikefill);
        }else {
            holder.unlikeButton.setImageResource(R.drawable.ic_dislike);
        }

        holder.unlikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.checkLogin()){

                    holder.unlikeButton.setImageResource(R.drawable.ic_dislikefill);

                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.blinking_animation);
                    holder.unlikeButton.startAnimation(animation);

                    totalLIKE = Integer.parseInt(postModel_.getTotalLikeDislike()+1);

                    postAdapterListener.getDiLikes(postModel_.getId(),user);

                }




            }
        });


        holder.totalReact_.setText("All"+" "+postModel_.getTotalLikeDislike()+" "+"and others");

    }

    @Override
    public int getItemCount() {
        if(postModels!=null) {
            int r = postModels.size();
            Log.e("data",String.valueOf(r));
            return postModels.size();
        } return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType( position );
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId( position );
    }


    public interface PostAdapterListener {
        void getLikes(String id,String likes);
        void getDiLikes(String id,String dislikes);
    }
}
