package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyNotifications extends AppCompatActivity {
    FirebaseFirestore fStore;

    RecyclerView notificationRecyclerView;
    ArrayList<Notification> notificationArrayList;
    NotificationAdapter adapter;
    private String userID,imageURI;
    String senderid,receiverid,response;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifications);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        notificationArrayList = new ArrayList<>();
        setUpRecyclerView();
        loadUserFromFirebase();
    }


    private void setUpRecyclerView() {
        notificationRecyclerView= findViewById(R.id.recyclerViewNotification);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadUserFromFirebase() {
        if (notificationArrayList.size() > 0)
            notificationArrayList.clear();

        fStore.collection("FriendRequests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    senderid=querySnapshot.getString("SenderID");
                                    receiverid=querySnapshot.getString("ReceiverID");
                                    response=querySnapshot.getString("Response");

                                if(userID.equals(receiverid)&& response.equals("0"))
                                {
                                    DocumentReference documentReference = fStore.collection("User").document(userID);
                                    documentReference.addSnapshotListener(MyNotifications.this, new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (value != null) {
                                                imageURI = value.getString("ProPicUrl");
                                            }
                                        }
                                    });
                                    Notification notification = new Notification(querySnapshot.getString("ReceiverName"),
                                            "sent you a friend request",
                                            querySnapshot.getString("SenderName"),
                                            querySnapshot.getString("SenderID"),
                                            imageURI,querySnapshot.getString("DocumentID"));
                                    notificationArrayList.add(notification);
                                }


                        }
                        adapter = new NotificationAdapter(MyNotifications.this, notificationArrayList);
                        notificationRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyNotifications.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }
}