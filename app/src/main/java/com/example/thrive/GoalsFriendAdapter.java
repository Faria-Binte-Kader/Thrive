package com.example.thrive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GoalsFriendAdapter extends RecyclerView.Adapter<ViewHolderGoalsFriend> implements AdapterView.OnItemSelectedListener {

    Friends friendsActivity;
    ArrayList<GoalsFriend> goalsFriendArrayList;

    String imageURL;

    public GoalsFriendAdapter(Friends friendsActivity, ArrayList<GoalsFriend> goalsFriendArrayList){
        this.friendsActivity = friendsActivity;
        this.goalsFriendArrayList = goalsFriendArrayList;
    }

    @NonNull
    @Override
    public ViewHolderGoalsFriend onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(friendsActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.goal_friend_list,parent,false);

        return new ViewHolderGoalsFriend(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGoalsFriend holder, int position) {
        holder.friendName.setText(goalsFriendArrayList.get(position).getUserID());
        //holder.friendName.setText(goalsFriendArrayList.get(position).getUserName());
        holder.friendGoalName.setText(goalsFriendArrayList.get(position).getName());

        /*
        imageURL = goalsFriendArrayList.get(position).getUserProPicURL();

        if (imageURL.isEmpty()) {
            holder.friendProfilePic.setImageResource(R.drawable.default_profile_picture);
        } else {
            Picasso.get().load(imageURL).into(holder.friendProfilePic);
        }

         */

    }

    @Override
    public int getItemCount() {
        return goalsFriendArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
