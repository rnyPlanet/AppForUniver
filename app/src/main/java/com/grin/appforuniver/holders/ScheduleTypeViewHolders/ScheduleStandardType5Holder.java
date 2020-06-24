package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType5Binding;

public class ScheduleStandardType5Holder extends ScheduleStandardTypeParentHolder {
    private ScheduleSingleType5Binding binding;

    public ScheduleStandardType5Holder(@NonNull ScheduleSingleType5Binding binding) {
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
    void bindSecondSubgroupFirstWeek(Classes classes) {
        bindCardSubject(binding.secondSubgroupFirstWeek, classes);
    }

    @Override
    void bindSecondSubgroupSecondWeek(Classes classes) {
        bindCardSubject(binding.secondSubgroupSecondWeek, classes);
    }
}
