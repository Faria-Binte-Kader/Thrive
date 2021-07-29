package com.example.thrive;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarHolder> {
    ChangeAvatar changeAvatar;
    ArrayList<Avatar>avatars;
    FirebaseFirestore fStore;

    public AvatarAdapter(ChangeAvatar changeAvatar,ArrayList<Avatar>avatars){
        this.changeAvatar=changeAvatar;
        this.avatars=avatars;
    }
    @NonNull
    @Override
    public AvatarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(changeAvatar.getBaseContext());
        View view = layoutInflater.inflate(R.layout.avatar_list, null, false);
        return new AvatarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarHolder holder, int position) {
        String imguri =avatars.get(position).getDownloadlink();
        Picasso.get().load(imguri).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore = FirebaseFirestore.getInstance();
                if(imguri!=null){
                fStore.collection("User").document(avatars.get(position).getUserId())
                        .update("ProPicUrl", imguri)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(changeAvatar.getBaseContext(), "Avatar Updated!", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Updated!");
                            }
                        });
            }}
        });

    }

    @Override
    public int getItemCount() {
        return avatars.size();
    }
}
