package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;

public class ScheduleStandardType1Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View bothSubgroupBothWeek;

    public ScheduleStandardType1Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        bothSubgroupBothWeek = itemView.findViewById(R.id.both_subgroup_both_week);
    }

    @Override
    void bindNumberPair(String numberPair) {
        this.numberPair.setText(numberPair);
    }

    @Override
    void bindBothSubgroupBothWeek(Classes classes) {
        bindCardSubject(bothSubgroupBothWeek, classes);
    }
}
