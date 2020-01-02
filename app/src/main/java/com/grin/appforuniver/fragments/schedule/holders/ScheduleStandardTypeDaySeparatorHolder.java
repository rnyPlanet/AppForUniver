package com.grin.appforuniver.fragments.schedule.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;

public class ScheduleStandardTypeDaySeparatorHolder extends RecyclerView.ViewHolder {
    private TextView numberPair;
    private TextView day;

    public ScheduleStandardTypeDaySeparatorHolder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        day = itemView.findViewById(R.id.text_day);
    }

    public void bind(String day) {
        this.day.setText(day);
    }
}
