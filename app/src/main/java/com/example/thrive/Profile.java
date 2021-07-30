package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    Button updateProfileBtn,changeAvatarBtn;

    private TextView name, description, email, age, height, weight;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,imageuri;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        description = findViewById(R.id.profileDescription);
        age = findViewById(R.id.profileAge);
        height = findViewById(R.id.profileHeight);
        weight = findViewById(R.id.profileWeight);
        imageView=findViewById(R.id.myavatar);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        BottomNavigationView nav= findViewById(R.id.bottomnavview2);
        findViewById(R.id.bottomnavview2).setBackground(null);
        nav.setSelectedItemId(R.id.profile);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(Profile.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        break;
                    case R.id.shop:
                        Intent intent2 = new Intent(Profile.this, Shop.class);
                        startActivity(intent2);
                        break;
                    case R.id.stat:
                        Intent intent3 = new Intent(Profile.this, Stat.class);
                        startActivity(intent3);
                        break;
                    case R.id.friends:
                        Intent intent4 = new Intent(Profile.this, Friends.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });

        updateProfileBtn = findViewById(R.id.updatePersonalInfo);
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPersonalInfo();
            }
        });

        changeAvatarBtn=findViewById(R.id.changeavatarbtn);
        changeAvatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoChangeAvatar();
            }
        });

        DocumentReference documentReference = fStore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name.setText(value.getString("Name"));
                    email.setText(value.getString("Email"));
                    description.setText(value.getString("Description"));
                    age.setText(value.getString("Age"));
                    height.setText(new StringBuilder().append(value.getString("Height")).append(" cm").toString());
                    weight.setText(new StringBuilder().append(value.getString("Weight")).append(" kg").toString());
                    imageuri=value.getString("ProPicUrl");
                     //if(imageuri!=null)
                   // { Picasso.get().load(imageuri).into(imageView);}
                    if (imageuri.isEmpty()) {
                        imageView.setImageResource(R.drawable.default_profile_picture);
                    } else{
                        Picasso.get().load(imageuri).into(imageView);
                    }
                }
                //Toast.makeText(Profile.this, "Password Changed ", Toast.LENGTH_SHORT).show();
               // if(imageuri!=null)
                //{ Picasso.get().load(imageuri).into(imageView);}
            }
        });

    }

    public void gotoPersonalInfo(){
        Intent intent = new Intent(Profile.this, PersonalInfo.class);
        startActivity(intent);
    }

    public void gotoChangeAvatar(){
        Intent intent = new Intent(Profile.this, ChangeAvatar.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
    }
}