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

public class AllUserListAdapterForHome extends RecyclerView.Adapter<AllUserListAdapterForHome.MyViewHold> {


    Context context;
    AllUserListAdapterListenerHome allUserListAdapterListenerHome;
    List<UserListForFollowModel> userListForFollowModels;

    SessionManager sessionManager;
    String user_id;

    public AllUserListAdapterForHome(Context context, AllUserListAdapterListenerHome allUserListAdapterListenerHome, List<UserListForFollowModel> userListForFollowModels) {
        this.context = context;
        this.allUserListAdapterListenerHome = allUserListAdapterListenerHome;
        this.userListForFollowModels = userListForFollowModels;
    }

    public void setData (List<UserListForFollowModel> userListForFollowModel) {
        this.userListForFollowModels = userListForFollowModel;
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView name;
        public MyViewHold(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.userImage);
            name = itemView.findViewById(R.id.UserListName);
        }
    }



    @NonNull
    @Override
    public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.image_user_list,parent,false);
        MyViewHold myViewHold = new MyViewHold(viewGroup);
        return myViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {

        final  UserListForFollowModel userListForFollowModel = userListForFollowModels.get(position);

        holder.name.setText(userListForFollowModel.getUserName());


        Glide.with(context).load(userListForFollowModel.getProfileUrl()).placeholder(R.drawable.ic_userdummy).into(holder.profileImage);


    }

    @Override
    public int getItemCount() {
        if(userListForFollowModels!=null) {
            int r = userListForFollowModels.size();
            Log.e("UserListData",String.valueOf(r));
            return userListForFollowModels.size();
        } return 0;
    }


    public interface AllUserListAdapterListenerHome {
        void followRequest(String userId, String followId);
    }
}
