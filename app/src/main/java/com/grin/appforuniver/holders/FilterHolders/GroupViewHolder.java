package com.grin.appforuniver.holders.FilterHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Groups;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    private Chip chip;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        chip = (Chip) itemView;
    }

    public void bind(Groups group, View.OnClickListener deleteItemListener) {
        String text = chip.getContext().getString(R.string.chip_adapter_group) + group.getmName();
        chip.setText(text);
        chip.setOnCloseIconClickListener(deleteItemListener);
    }
}
