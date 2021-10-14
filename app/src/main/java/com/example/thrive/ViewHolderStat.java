package com.example.thrive;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;

import org.jetbrains.annotations.NotNull;

public class ViewHolderStat extends RecyclerView.ViewHolder {

    public BarChart barChart;
    public TextView goalName;

    public ViewHolderStat(@NonNull @NotNull View itemView) {
        super(itemView);

        barChart = itemView.findViewById(R.id.statBarChart);
        goalName = itemView.findViewById(R.id.statGoalName);

    }
}
