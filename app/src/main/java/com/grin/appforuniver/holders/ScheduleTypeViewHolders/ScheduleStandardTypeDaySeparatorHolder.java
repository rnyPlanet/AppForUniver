package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.databinding.ScheduleDaySeparatorBinding;

public class ScheduleStandardTypeDaySeparatorHolder extends RecyclerView.ViewHolder {
    private ScheduleDaySeparatorBinding binding;

    public ScheduleStandardTypeDaySeparatorHolder(@NonNull ScheduleDaySeparatorBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String day) {
        binding.textDay.setText(day);
    }
}
