package com.example.thrive;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderGoalsFriend extends RecyclerView.ViewHolder {

    public ImageView friendProfilePic;
    public TextView friendName,friendGoalName;

    public ViewHolderGoalsFriend(@NonNull View itemView) {
        super(itemView);

        friendProfilePic = itemView.findViewById(R.id.friendAvatar);
        friendName = itemView.findViewById(R.id.friendName);
        friendGoalName = itemView.findViewById(R.id.friendGoalName);
    }
}
