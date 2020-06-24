package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType6Binding;

public class ScheduleStandardType6Holder extends ScheduleStandardTypeParentHolder {
    private ScheduleSingleType6Binding binding;

    public ScheduleStandardType6Holder(@NonNull ScheduleSingleType6Binding binding) {
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
     void bindFirstSubgroupSecondWeek(Classes classes) {
        bindCardSubject(binding.firstSubgroupSecondWeek, classes);
    }

    @Override
     void bindSecondSubgroupBothWeek(Classes classes) {
        bindCardSubject(binding.secondSubgroupBothWeek, classes);
    }
}
