package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType3Binding;

public class ScheduleStandardType3Holder extends ScheduleStandardTypeParentHolder {
    private ScheduleSingleType3Binding binding;

    public ScheduleStandardType3Holder(@NonNull ScheduleSingleType3Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    void bindNumberPair(String numberPair) {
        binding.numberPair.setText(numberPair);
    }

    @Override
    void bindFirstSubgroupFirstWeek(Classes classes) {
        bindCardSubject(binding.firstSubgroupFirstWeek, classes);
    }

    @Override
    void bindSecondSubgroupFirstWeek(Classes classes) {
        bindCardSubject(binding.secondSubgroupFirstWeek, classes);
    }

    @Override
    void bindFirstSubgroupSecondWeek(Classes classes) {
        bindCardSubject(binding.firstSubgroupSecondWeek, classes);
    }

    @Override
    void bindSecondSubgroupSecondWeek(Classes classes) {
        bindCardSubject(binding.secondSubgroupSecondWeek, classes);
    }
}
