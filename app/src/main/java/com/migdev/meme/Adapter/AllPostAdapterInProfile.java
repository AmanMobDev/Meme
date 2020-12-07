package com.migdev.meme.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.migdev.meme.Model.ProfileAllPostModel;
import com.migdev.meme.R;

import java.util.List;

public class AllPostAdapterInProfile extends RecyclerView.Adapter<AllPostAdapterInProfile.MyViewHold> {

    Context context;
    AllPostAdapterInProfileListenere allPostAdapterInProfileListenere;
    List<ProfileAllPostModel> profileAllPostModels;

    public AllPostAdapterInProfile(Context context, AllPostAdapterInProfileListenere allPostAdapterInProfileListenere, List<ProfileAllPostModel> profileAllPostModels) {
        this.context = context;
        this.allPostAdapterInProfileListenere = allPostAdapterInProfileListenere;
        this.profileAllPostModels = profileAllPostModels;
    }

    public void setData(List<ProfileAllPostModel> profileAllPostModels){
        this.profileAllPostModels = profileAllPostModels;
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHold(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPost);
        }
    }



    @NonNull
    @Override
    public AllPostAdapterInProfile.MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.all_post,parent,false);
        MyViewHold myViewHold = new MyViewHold(viewGroup);
        return myViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull AllPostAdapterInProfile.MyViewHold holder, int position) {

        final ProfileAllPostModel profileAllPostModel = profileAllPostModels.get(position);

        Glide.with(context).load(profileAllPostModel.getImageUrl()).placeholder(R.drawable.ic_place_bg).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if(profileAllPostModels!=null) {
            int r = profileAllPostModels.size();
            Log.e("data",String.valueOf(r));
            return profileAllPostModels.size();
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

    public interface AllPostAdapterInProfileListenere {
    }


}
