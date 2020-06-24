package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.databinding.ScheduleSingleType9Binding;

public class ScheduleStandardType9Holder extends RecyclerView.ViewHolder {
    private ScheduleSingleType9Binding binding;

    public ScheduleStandardType9Holder(@NonNull ScheduleSingleType9Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String numberPair, String message) {
        binding.numberPair.setText(numberPair);
        binding.message.setText(message);
    }
}
