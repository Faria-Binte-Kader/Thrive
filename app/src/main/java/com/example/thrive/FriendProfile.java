package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FriendProfile extends AppCompatActivity {

    TextView textViewFriendName;
    ImageView imageViewFriendAvatar;
    Button seePublicGoals;

    public static final String EXTRA_TEXT_ID_PUBLIC_GOAL = "com.example.application.example.EXTRA_TEXT_ID_PUBLIC_GOAL";
    public static final String EXTRA_TEXT_NAME_PUBLIC_GOAL = "com.example.application.example.EXTRA_TEXT_NAME_PUBLIC_GOAL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        Intent intent = getIntent();
        final String friendID = intent.getStringExtra(Friends.EXTRA_TEXT_ID);
        final String friendName = intent.getStringExtra(Friends.EXTRA_TEXT_Name);
        final String friendProPicURL = intent.getStringExtra(Friends.EXTRA_TEXT_ProPicURL);

        textViewFriendName = findViewById(R.id.friendNameFriendProfile);
        imageViewFriendAvatar = findViewById(R.id.friendAvatarFriendProfile);
        seePublicGoals = findViewById(R.id.seePublicGoalsFriendProfile);

        textViewFriendName.setText(friendName);

        if (friendProPicURL.isEmpty()) {
            imageViewFriendAvatar.setImageResource(R.drawable.default_profile_picture);
        } else {
            Picasso.get().load(friendProPicURL).into(imageViewFriendAvatar);
        }

        seePublicGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFrienPublicGoals(friendID,friendName);
            }
        });
    }

    public void goToFrienPublicGoals(String friendID, String friendName){
        Intent intent = new Intent(FriendProfile.this, FriendPublicGoal.class);
        intent.putExtra(EXTRA_TEXT_ID_PUBLIC_GOAL, friendID);
        intent.putExtra(EXTRA_TEXT_NAME_PUBLIC_GOAL, friendName);
        startActivity(intent);
    }

}