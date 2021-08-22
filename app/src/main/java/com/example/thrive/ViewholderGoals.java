package com.example.thrive;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewholderGoals extends RecyclerView.ViewHolder {

    public TextView progress, name;
    public ProgressBar progressBar;
    public Button updateprogress;
    public ImageView goalpicture;
    public ViewholderGoals(@NonNull View itemView) {
        super(itemView);

        progress = itemView.findViewById(R.id.progress);
        name = itemView.findViewById(R.id.progressgoalname);
        progressBar=itemView.findViewById(R.id.progress_bar);
        goalpicture=itemView.findViewById(R.id.goalpicture);
        updateprogress = itemView.findViewById(R.id.updateprogress_btn);
    }
}