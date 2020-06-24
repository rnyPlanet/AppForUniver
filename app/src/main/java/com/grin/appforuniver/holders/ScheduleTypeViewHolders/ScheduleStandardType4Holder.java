package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType4Binding;

public class ScheduleStandardType4Holder extends ScheduleStandardTypeParentHolder {
    private ScheduleSingleType4Binding binding;

    public ScheduleStandardType4Holder(@NonNull ScheduleSingleType4Binding binding) {
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
    void bindBothSubgroupSecondWeek(Classes classes) {
        bindCardSubject(binding.bothSubgroupSecondWeek, classes);
    }
}
