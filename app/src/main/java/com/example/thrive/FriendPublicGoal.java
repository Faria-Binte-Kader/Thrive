package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class FriendPublicGoal extends AppCompatActivity {

    public static final String TAG = "TAG FriendPublicGoal";

    FirebaseFirestore fStore;
    RecyclerView friendPublicGoalRecyclerView;
    ArrayList<GoalsFriend> friendPublicGoalArrayList;
    FriendPublicGoalAdapter adapter;

    TextView textViewFriendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_public_goal);

        Intent intent = getIntent();
        final String friendID = intent.getStringExtra(FriendProfile.EXTRA_TEXT_ID_PUBLIC_GOAL);
        final String friendName = intent.getStringExtra(FriendProfile.EXTRA_TEXT_NAME_PUBLIC_GOAL);

        textViewFriendName = findViewById(R.id.friendNameFriendPublicGoal);
        textViewFriendName.setText(friendName);

        friendPublicGoalArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadUserFromFirebase(friendID, friendName);
    }

    private void loadUserFromFirebase(String userID, String userName) {
        if (friendPublicGoalArrayList.size() > 0)
            friendPublicGoalArrayList.clear();

        Log.d(TAG, "loading goals for " + userID);

        fStore.collection("UserGoalInfo")
                .document(userID).collection("Goals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Log.d(TAG, "traversing goals of " + userID + "with privacy" + querySnapshot.getString("Privacy"));
                            GoalsFriend goalsFriend = new GoalsFriend(querySnapshot.getString("id"),
                                    querySnapshot.getString("Name"),
                                    querySnapshot.getString("Category"),
                                    querySnapshot.getString("Subcategory"),
                                    userID,
                                    userName,
                                    querySnapshot.getString("UserProPicURL"));
                            friendPublicGoalArrayList.add(goalsFriend);
                            String dummy1= goalsFriend.getName();
                            String dummy2 = goalsFriend.getSubcategory();
                            Log.d(TAG, dummy1+dummy2);
                        }
                        adapter = new FriendPublicGoalAdapter(FriendPublicGoal.this, friendPublicGoalArrayList);
                        friendPublicGoalRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(FriendPublicGoal.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });

    }

    private void setUpFirebase() {
        fStore = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        friendPublicGoalRecyclerView = findViewById(R.id.recyclerViewFriendPublicGoal);
        friendPublicGoalRecyclerView.setHasFixedSize(true);
        friendPublicGoalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}