package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class FriendProfile extends AppCompatActivity {

    TextView textViewFriendName;
    ImageView imageViewFriendAvatar;
    Button seePublicGoals;
    Button friendRequest;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID, senderName;
    String senderid, receiverid, response;
    int flag = 0;

    String TAG = "TAG FriendProfile";

    public static final String EXTRA_TEXT_ID_PUBLIC_GOAL = "com.example.application.example.EXTRA_TEXT_ID_PUBLIC_GOAL";
    public static final String EXTRA_TEXT_NAME_PUBLIC_GOAL = "com.example.application.example.EXTRA_TEXT_NAME_PUBLIC_GOAL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        Intent intent = getIntent();
        final String friendID = intent.getStringExtra(Friends.EXTRA_TEXT_ID);
        final String friendName = intent.getStringExtra(Friends.EXTRA_TEXT_Name);
        final String friendProPicURL = intent.getStringExtra(Friends.EXTRA_TEXT_ProPicURL);

        textViewFriendName = findViewById(R.id.friendNameFriendProfile);
        imageViewFriendAvatar = findViewById(R.id.friendAvatarFriendProfile);
        seePublicGoals = findViewById(R.id.seePublicGoalsFriendProfile);
        friendRequest = findViewById(R.id.requestFriendProfile);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        textViewFriendName.setText(friendName);

        if (friendProPicURL.isEmpty()) {
            imageViewFriendAvatar.setImageResource(R.drawable.default_profile_picture);
        } else {
            Picasso.get().load(friendProPicURL).into(imageViewFriendAvatar);
        }

        seePublicGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFrienPublicGoals(friendID, friendName);
            }
        });
        DocumentReference documentReference = fStore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    senderName = value.getString("Name");
                }
            }
        });
        friendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("FriendRequests")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    senderid = querySnapshot.getString("SenderID");
                                    receiverid = querySnapshot.getString("ReceiverID");
                                    response = querySnapshot.getString("Response");

                                    if (userID.equals(senderid) && friendID.equals(receiverid) && response.equals("0")) {
                                        Toast.makeText(FriendProfile.this, "Already sent a request", Toast.LENGTH_SHORT).show();
                                        flag = 1;
                                    } else if (userID.equals(senderid) && friendID.equals(receiverid) && response.equals("1")) {
                                        Toast.makeText(FriendProfile.this, "You are already friends!", Toast.LENGTH_SHORT).show();
                                        flag = 1;
                                    } else if (userID.equals(receiverid) && friendID.equals(senderid) && response.equals("1")) {
                                        Toast.makeText(FriendProfile.this, "You are already friends!", Toast.LENGTH_SHORT).show();
                                        flag = 1;
                                    } else if (userID.equals(friendID)) {
                                        Toast.makeText(FriendProfile.this, "Sorry, even though you can be your own friend, but here you cannot send yourself a friend request!", Toast.LENGTH_SHORT).show();
                                        flag = 1;
                                    }

                                }
                                if (flag == 0) {
                                    DocumentReference documentReference_frndreq = fStore.collection("FriendRequests").document();
                                    Map<String, Object> friend_req = new HashMap<>();
                                    friend_req.put("SenderID", userID);
                                    friend_req.put("SenderName", senderName);
                                    friend_req.put("ReceiverID", friendID);
                                    friend_req.put("ReceiverName", friendName);
                                    friend_req.put("Response", "0"); //if 0,not responded yet, if 1, then friends
                                    friend_req.put("DocumentID", documentReference_frndreq.getId());

                                    documentReference_frndreq.set(friend_req).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(FriendProfile.this, "Friend Request Sent", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    //updateToken();
                                    sendFriendRequestNotification(friendID);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FriendProfile.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                Log.v("hoitessenare", e.getMessage());
                            }
                        });

            }
        });
    }

    public void goToFrienPublicGoals(String friendID, String friendName) {
        Intent intent = new Intent(FriendProfile.this, FriendPublicGoal.class);
        intent.putExtra(EXTRA_TEXT_ID_PUBLIC_GOAL, friendID);
        intent.putExtra(EXTRA_TEXT_NAME_PUBLIC_GOAL, friendName);
        startActivity(intent);
    }


    public void updateToken() {
        String userID = FirebaseAuth.getInstance().getUid();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                fStore.collection("User").document(userID).update("Token", task.toString());
            }
        });
    }

    public void sendFriendRequestNotification(String receiverID) {
        String userID = FirebaseAuth.getInstance().getUid();
        fStore.collection("User").document(userID)
                .addSnapshotListener(FriendProfile.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valueSender, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (valueSender != null) {
                            fStore.collection("User").document(receiverID)
                                    .addSnapshotListener(FriendProfile.this, new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valueReceiver, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                                            if (valueReceiver != null) {
                                                String receiverToken = valueReceiver.getString("Token") + "";

                                                NotificationSenderFriendRequest notificationSender = new NotificationSenderFriendRequest(
                                                        receiverToken + "",
                                                        "Friend Request",
                                                        valueSender.getString("Name") + " sent you a friend request",
                                                        getApplicationContext(), FriendProfile.this);

                                                notificationSender.sendNotification();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void sendNotificationToAll(String title) {

        NotificationSenderFriendRequest notificationSender2 = new NotificationSenderFriendRequest(
                "/topics/all", title, "Someone has sent a friend request", getApplicationContext(), FriendProfile.this);

        notificationSender2.sendNotification();
    }

}