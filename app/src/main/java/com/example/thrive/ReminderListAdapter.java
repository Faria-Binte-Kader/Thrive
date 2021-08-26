package com.example.thrive;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;

public class ReminderListAdapter extends RecyclerView.Adapter<ViewholderReminder> implements AdapterView.OnItemSelectedListener {

    AllReminders allReminderss;
    ArrayList<ModelReminder> modelReminderArrayList;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    String imageURL;

    public ReminderListAdapter(AllReminders allReminders, ArrayList<ModelReminder> modelReminderArrayList){
        this.allReminderss=allReminders;
        this.modelReminderArrayList=modelReminderArrayList;
    }

    @NonNull
    @Override
    public ViewholderReminder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(allReminderss.getBaseContext());
        View view = layoutInflater.inflate(R.layout.reminder_list,parent,false);

        return new ViewholderReminder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderReminder holder, int position) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        holder.name.setText(modelReminderArrayList.get(position).getReminderName());
        holder.typetext.setText(modelReminderArrayList.get(position).getType());
        holder.duration.setText(modelReminderArrayList.get(position).getDuration());
        holder.date.setText(modelReminderArrayList.get(position).getDate());

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Edit option is in progress. Apologies ;-;", Toast.LENGTH_SHORT).show();
            }
        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) allReminderss.getBaseContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent= new Intent(allReminderss.getBaseContext(),ReminderGoalsAlertReceiver.class);
                intent.setData(Uri.parse(modelReminderArrayList.get(position).getIntentID()));
                intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION);
                PendingIntent pendingIntent= PendingIntent.getBroadcast(allReminderss.getBaseContext(),0,intent,0);
                alarmManager.cancel(pendingIntent);

                fStore.collection("Alarms").document(userID).collection("PersonalAlarms").document(modelReminderArrayList.get(position).getDocumentID())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.v("---I---", "deleted");
                                Toast.makeText(v.getContext(), "Alarm Deleted.", Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelReminderArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
