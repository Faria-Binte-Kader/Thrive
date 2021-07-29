package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChangeAvatar extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    RecyclerView recyclerView;
    ArrayList<Avatar>avatarArrayList;
    AvatarAdapter avatarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        avatarArrayList = new ArrayList<>();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        recyclerView = findViewById(R.id.avatar_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChangeAvatar.this));
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        loadDataFromFirebase();
    }

    private void loadDataFromFirebase(){
        if (avatarArrayList.size() > 0)
            avatarArrayList.clear();
        fStore.collection("UserAvatarInfo")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link,uid;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            uid = querySnapshot.getString("userID");
                            link = querySnapshot.getString("DownloadLink");

                            Avatar avatar = new Avatar(uid,link);
                            avatarArrayList.add(avatar);
                        }
                        avatarAdapter = new AvatarAdapter(ChangeAvatar.this, avatarArrayList);
                        GridLayoutManager glm=new GridLayoutManager(ChangeAvatar.this,3,GridLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(glm);
                        recyclerView.setAdapter(avatarAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangeAvatar.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangeAvatar.this, Profile.class);
        startActivity(intent);
    }
}