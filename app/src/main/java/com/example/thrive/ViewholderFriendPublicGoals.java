package com.example.thrive;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewholderFriendPublicGoals extends RecyclerView.ViewHolder {

    public TextView category,subcategory, goalName;

    public ViewholderFriendPublicGoals(@NonNull View itemView) {
        super(itemView);

        category = itemView.findViewById(R.id.categoryFriendPublicGoal);
        subcategory = itemView.findViewById(R.id.subcategoryFriendPublicGoal);
        goalName = itemView.findViewById(R.id.goalNameFriendPublicGoal);
    }
}
