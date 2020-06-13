package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;

public class ScheduleStandardType4Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View bothSubgroupFirstWeek;
    private View bothSubgroupSecondWeek;

    public ScheduleStandardType4Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        bothSubgroupFirstWeek = itemView.findViewById(R.id.both_subgroup_first_week);
        bothSubgroupSecondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
    }

    @Override
    void bindNumberPair(String numberPair) {
        this.numberPair.setText(numberPair);
    }

    @Override
    void bindBothSubgroupFirstWeek(Classes classes) {
        bindCardSubject(bothSubgroupFirstWeek, classes);
    }

    @Override
    void bindBothSubgroupSecondWeek(Classes classes) {
        bindCardSubject(bothSubgroupSecondWeek, classes);
    }
}
