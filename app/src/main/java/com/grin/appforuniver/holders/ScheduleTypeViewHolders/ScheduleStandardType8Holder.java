package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;

public class ScheduleStandardType8Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View firstSubgroupFirstWeek;
    private View secondSubgroupFirstWeek;
    private View bothSubgroupSecondWeek;

    public ScheduleStandardType8Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        firstSubgroupFirstWeek = itemView.findViewById(R.id.first_subgroup_first_week);
        secondSubgroupFirstWeek = itemView.findViewById(R.id.second_subgroup_first_week);
        bothSubgroupSecondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
    }

    @Override
    public void bindNumberPair(String numberPair) {
        this.numberPair.setText(numberPair);
    }

    @Override
    public void bindFirstSubgroupFirstWeek(Classes classes) {
        bindCardSubject(firstSubgroupFirstWeek, classes);
    }

    @Override
    public void bindSecondSubgroupFirstWeek(Classes classes) {
        bindCardSubject(secondSubgroupFirstWeek, classes);
    }

    @Override
    public void bindBothSubgroupSecondWeek(Classes classes) {
        bindCardSubject(bothSubgroupSecondWeek, classes);
    }
}
