package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType8Binding;

public class ScheduleStandardType8Holder extends ScheduleStandardTypeParentHolder {
    private ScheduleSingleType8Binding binding;

    public ScheduleStandardType8Holder(@NonNull ScheduleSingleType8Binding binding) {
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
     void bindBothSubgroupSecondWeek(Classes classes) {
        bindCardSubject(binding.bothSubgroupSecondWeek, classes);
    }
}
