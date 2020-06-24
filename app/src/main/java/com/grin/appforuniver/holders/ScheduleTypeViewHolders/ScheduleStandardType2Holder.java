package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.models.Classes;

public class ScheduleStandardType2Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View firstSubgroupBothWeek;
    private View secondSubgroupBothWeek;

    public ScheduleStandardType2Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        firstSubgroupBothWeek = itemView.findViewById(R.id.first_subgroup_both_week);
        secondSubgroupBothWeek = itemView.findViewById(R.id.second_subgroup_both_week);

    }

    @Override
    void bindNumberPair(String numberPair) {
        this.numberPair.setText(numberPair);
    }

    @Override
    void bindFirstSubgroupBothWeek(Classes classes) {
        bindCardSubject(firstSubgroupBothWeek, classes);
    }

    @Override
    void bindSecondSubgroupBothWeek(Classes classes) {
        bindCardSubject(secondSubgroupBothWeek, classes);
    }
}
