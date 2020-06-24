package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType2Binding;

public class ScheduleStandardType2Holder extends ScheduleStandardTypeParentHolder {
    private ScheduleSingleType2Binding binding;

    public ScheduleStandardType2Holder(@NonNull ScheduleSingleType2Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    void bindNumberPair(String numberPair) {
        binding.numberPair.setText(numberPair);
    }

    @Override
    void bindFirstSubgroupBothWeek(Classes classes) {
        bindCardSubject(binding.firstSubgroupBothWeek, classes);
    }

    @Override
    void bindSecondSubgroupBothWeek(Classes classes) {
        bindCardSubject(binding.secondSubgroupBothWeek, classes);
    }
}
