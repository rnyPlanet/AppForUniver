package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;

import com.grin.appforuniver.data.models.Classes;
import com.grin.appforuniver.databinding.ScheduleSingleType1Binding;

public class ScheduleStandardType1Holder extends ScheduleStandardTypeParentHolder {
   private ScheduleSingleType1Binding binding;

    public ScheduleStandardType1Holder(@NonNull ScheduleSingleType1Binding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    @Override
    void bindNumberPair(String numberPair) {
        binding.numberPair.setText(numberPair);
    }

    @Override
    void bindBothSubgroupBothWeek(Classes classes) {
        bindCardSubject(binding.bothSubgroupBothWeek, classes);
    }
}
