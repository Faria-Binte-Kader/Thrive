package com.example.thrive;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewholderReminder extends RecyclerView.ViewHolder {

    public TextView name,typetext,duration,date;
    public Button editbtn,deletebtn;
    public ViewholderReminder(@NonNull View itemView) {
        super(itemView);

        typetext = itemView.findViewById(R.id.intervaltype);
        name = itemView.findViewById(R.id.remindernamenotify);
        duration= itemView.findViewById(R.id.durationremindernotify);
        date=itemView.findViewById(R.id.setdate);
        editbtn = itemView.findViewById(R.id.editreminderbtn);
        deletebtn = itemView.findViewById(R.id.deletereminderbtn);
    }
}