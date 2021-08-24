package com.example.thrive;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdapter extends RecyclerView.Adapter<ViewholderNotification> implements AdapterView.OnItemSelectedListener {

    MyNotifications myNotifications;
    ArrayList<Notification> notificationArrayList;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,imguri;

    String imageURL;

    public NotificationAdapter(MyNotifications myNotifications, ArrayList<Notification> notificationArrayList){
        this.myNotifications = myNotifications;
        this.notificationArrayList = notificationArrayList;
    }

    @NonNull
    @Override
    public ViewholderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(myNotifications.getBaseContext());
        View view = layoutInflater.inflate(R.layout.notification_list,parent,false);

        return new ViewholderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderNotification holder, int position) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        holder.name.setText(notificationArrayList.get(position).getSenderName());
        holder.texttype.setText("sent you a friend request!");
        imageURL = notificationArrayList.get(position).getSenderProPicURL();

        if (imageURL==null) {
            holder.propicture.setImageResource(R.drawable.default_profile_picture);
        } else {
            Picasso.get().load(imageURL).into(holder.propicture);
        }
        holder.acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.texttype.setText("You have a new friend, "+holder.name.getText());
                fStore.collection("FriendRequests").document(notificationArrayList.get(position).getDocumentID())
                        .update("Response", "1")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.v("---I---", "Response changed");
                            }
                        });
                DocumentReference documentReference_frnd= fStore.collection("Friends").document(userID).collection(notificationArrayList.get(position).getUserName()+"Friends").document();
                Map<String, Object> friend = new HashMap<>();
                friend.put("FriendID", notificationArrayList.get(position).getSenderID());
                friend.put("FriendName", notificationArrayList.get(position).getSenderName());

                documentReference_frnd.set(friend).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v("---I---", "my friend added");
                    }
                });


                DocumentReference documentReference_frnd2= fStore.collection("Friends").document(notificationArrayList.get(position).getSenderID()).collection(notificationArrayList.get(position).getSenderName()+"Friends").document();
                Map<String, Object> friend2 = new HashMap<>();
                friend2.put("FriendID", userID);
                friend2.put("FriendName", notificationArrayList.get(position).getUserName());

                documentReference_frnd2.set(friend2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v("---I---", "Friend's friend added");
                    }
                });
            }
        });

        holder.rejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.texttype.setText("You removed friend request from "+holder.name.getText());
                fStore.collection("FriendRequests").document(notificationArrayList.get(position).getDocumentID())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.v("---I---", "deleted");
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
