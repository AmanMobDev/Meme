package com.migdev.meme.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.migdev.meme.Model.UserListForFollowModel;
import com.migdev.meme.R;
import com.migdev.meme.Session.SessionManager;

import java.util.HashMap;
import java.util.List;

public class AllUserListAdapter extends RecyclerView.Adapter<AllUserListAdapter.MyViewHold> {


    Context context;
    AllUserListAdapterListener allUserListAdapterListener;
    List<UserListForFollowModel> userListForFollowModels;

    SessionManager sessionManager;
    String user_id;

    public AllUserListAdapter(Context context, AllUserListAdapterListener allUserListAdapterListener, List<UserListForFollowModel> userListForFollowModels) {
        this.context = context;
        this.allUserListAdapterListener = allUserListAdapterListener;
        this.userListForFollowModels = userListForFollowModels;
    }

    public void setData (List<UserListForFollowModel> userListForFollowModel) {
        this.userListForFollowModels = userListForFollowModel;
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView name,followButton;
        public MyViewHold(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.user_list_image);
            name = itemView.findViewById(R.id.user_list_name);
            followButton = itemView.findViewById(R.id.followUser);

            sessionManager = new SessionManager(context);
            HashMap<String,String > userId = sessionManager.getUserDetails();

            user_id = userId.get(sessionManager.KEY_NAME);
        }
    }



    @NonNull
    @Override
    public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.user_list_layout,parent,false);
        MyViewHold myViewHold = new MyViewHold(viewGroup);
        return myViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {

        final  UserListForFollowModel userListForFollowModel = userListForFollowModels.get(position);

        holder.name.setText(userListForFollowModel.getUserName());

        Glide.with(context).load(userListForFollowModel.getProfileUrl()).placeholder(R.drawable.ic_userdummy).into(holder.profileImage);

        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allUserListAdapterListener.followRequest(user_id,userListForFollowModel.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        if(userListForFollowModels!=null) {
            int r = userListForFollowModels.size();
            Log.e("data",String.valueOf(r));
            return userListForFollowModels.size();
        } return 0;
    }


    public interface AllUserListAdapterListener {
        void followRequest(String userId, String followId);
    }
}
