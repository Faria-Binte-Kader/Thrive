package com.example.thrive;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewholderNotification extends RecyclerView.ViewHolder {

    public TextView goaltext,texttype, name;
    public Button acceptbtn,rejectbtn;
    public ImageView propicture;
    public ViewholderNotification(@NonNull View itemView) {
        super(itemView);

        texttype = itemView.findViewById(R.id.notificationText);
        name = itemView.findViewById(R.id.friendNamenotification);
        goaltext= itemView.findViewById(R.id.TeamGoalText);
        propicture=itemView.findViewById(R.id.friendAvatarNotification);
        acceptbtn = itemView.findViewById(R.id.acceptbtn);
        rejectbtn = itemView.findViewById(R.id.rejectbtn);
    }
}