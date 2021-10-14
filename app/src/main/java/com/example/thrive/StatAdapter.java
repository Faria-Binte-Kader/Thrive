package com.example.thrive;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StatAdapter extends RecyclerView.Adapter<ViewHolderStat> implements AdapterView.OnItemSelectedListener {

    Stat statActivity;
    ArrayList<Statistics> statisticsArrayList;

    public StatAdapter(Stat statActivity, ArrayList<Statistics> statisticsArrayList){
        this.statActivity = statActivity;
        this.statisticsArrayList = statisticsArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderStat onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(statActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.stat_list,parent,false);

        return new ViewHolderStat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderStat holder, int position) {
        holder.goalName.setText(statisticsArrayList.get(position).getName());

        ArrayList xValues = new ArrayList<>();
        xValues.add("Jan");
        xValues.add("Feb");
        xValues.add("Mar");
        xValues.add("Apr");
        xValues.add("May");
        xValues.add("Jun");
        xValues.add("Jul");
        xValues.add("Aug");
        xValues.add("Sep");
        xValues.add("Oct");
        xValues.add("Nov");
        xValues.add("Dec");



        ArrayList<BarEntry> yValues = new ArrayList<>();

        //get y values from statisticsArrayList

        List<String> monthStat = statisticsArrayList.get(position).getMonthStat();


        yValues.add(new BarEntry(1, Float.parseFloat(monthStat.get(0))));
        yValues.add(new BarEntry(2, Float.parseFloat(monthStat.get(1))));
        yValues.add(new BarEntry(3, Float.parseFloat(monthStat.get(2))));
        yValues.add(new BarEntry(4, Float.parseFloat(monthStat.get(3))));
        yValues.add(new BarEntry(5, Float.parseFloat(monthStat.get(4))));
        yValues.add(new BarEntry(6, Float.parseFloat(monthStat.get(5))));
        yValues.add(new BarEntry(7, Float.parseFloat(monthStat.get(6))));
        yValues.add(new BarEntry(8, Float.parseFloat(monthStat.get(7))));
        yValues.add(new BarEntry(9, Float.parseFloat(monthStat.get(8))));
        yValues.add(new BarEntry(10, Float.parseFloat(monthStat.get(9))));
        yValues.add(new BarEntry(11, Float.parseFloat(monthStat.get(10))));
        yValues.add(new BarEntry(12, Float.parseFloat(monthStat.get(11))));

        /*
        yValues.add(new BarEntry(1, Float.parseFloat(monthStat.get(0))));
        yValues.add(new BarEntry(2, 28));
        yValues.add(new BarEntry(3, 30));
        yValues.add(new BarEntry(4, 30));
        yValues.add(new BarEntry(5, 30));
        yValues.add(new BarEntry(6, 30));
        yValues.add(new BarEntry(7, 30));
        yValues.add(new BarEntry(8, 28));
        yValues.add(new BarEntry(9, 30));
        yValues.add(new BarEntry(10, 13));
        yValues.add(new BarEntry(11, 0));
        yValues.add(new BarEntry(12, 0));
         */

        String goalName = statisticsArrayList.get(position).getName();

        BarDataSet barDataSet = new BarDataSet(yValues, goalName);
        barDataSet.setColors(ColorTemplate.getHoloBlue());
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        BarData barData = new BarData(barDataSet);
        barData.setValueFormatter(new LargeValueFormatter());

        //holder.barChart.setFitBars(true);
        holder.barChart.setData(barData);
        holder.barChart.animateY(2000);
        holder.barChart.getXAxis().setAxisMinimum(0);
        holder.barChart.setMaxVisibleValueCount(31);
        holder.barChart.setDescription(null);

        XAxis xAxis = holder.barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        //xAxis.setAxisMinimum(1);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public int getItemCount() {
        return statisticsArrayList.size();
    }
}
