package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType7Binding;

public class ScheduleStandardType7Holder extends ScheduleStandardTypeParentHolder {
    private ScheduleSingleType7Binding binding;

    public ScheduleStandardType7Holder(@NonNull ScheduleSingleType7Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    void bindNumberPair(String numberPair) {
        binding.numberPair.setText(numberPair);
    }

    @Override
    void bindBothSubgroupFirstWeek(Classes classes) {
        bindCardSubject(binding.bothSubgroupFirstWeek, classes);
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
