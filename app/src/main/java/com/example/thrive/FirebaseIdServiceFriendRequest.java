package com.example.thrive;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

public class FirebaseIdServiceFriendRequest extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s)
    {
        super.onNewToken(s);
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getUid();
        /*
        FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<GetTokenResult> task) {
                        if(task.isSuccessful()){
                            fStore.collection("User").document(userID).update("Token", task.getResult().getToken().toString());
                        }
                    }
                });*/


        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(task -> {
            // get token
            fStore.collection("User").document(userID).update("Token", task.getResult().getToken().toString());
            Log.d("TAG Login getToken", task.getResult().getToken().toString());
            Log.d("TAG Login token ", task.getResult().toString());
        });

    }
}
