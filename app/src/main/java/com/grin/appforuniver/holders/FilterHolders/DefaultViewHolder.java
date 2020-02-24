package com.grin.appforuniver.holders.FilterHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

public class DefaultViewHolder extends RecyclerView.ViewHolder {
    private Chip chip;

    public DefaultViewHolder(@NonNull View itemView) {
        super(itemView);
        chip = (Chip) itemView;
    }

    public void bind(String text) {
        chip.setText(text);
    }

}
