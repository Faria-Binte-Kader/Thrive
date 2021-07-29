package com.example.thrive;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class AvatarHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    public AvatarHolder(@NonNull View itemView) {

        super(itemView);
        imageView=itemView.findViewById(R.id.iv_post_ind);
    }

}
