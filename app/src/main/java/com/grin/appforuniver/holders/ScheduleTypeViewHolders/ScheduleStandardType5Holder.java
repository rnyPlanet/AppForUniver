package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;

public class ScheduleStandardType5Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View firstSubgroupBothWeek;
    private View secondSubgroupFirstWeek;
    private View secondSubgroupSecondWeek;

    public ScheduleStandardType5Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        firstSubgroupBothWeek = itemView.findViewById(R.id.first_subgroup_both_week);
        secondSubgroupFirstWeek = itemView.findViewById(R.id.second_subgroup_first_week);
        secondSubgroupSecondWeek = itemView.findViewById(R.id.second_subgroup_second_week);
    }

    @Override
    public void bindNumberPair(String numberPair) {
        this.numberPair.setText(numberPair);
    }

    @Override
    public void bindFirstSubgroupBothWeek(Classes classes) {
        bindCardSubject(firstSubgroupBothWeek, classes);
    }

    @Override
    public void bindSecondSubgroupFirstWeek(Classes classes) {
        bindCardSubject(secondSubgroupFirstWeek, classes);
    }

    @Override
    public void bindSecondSubgroupSecondWeek(Classes classes) {
        bindCardSubject(secondSubgroupSecondWeek, classes);
    }
}
