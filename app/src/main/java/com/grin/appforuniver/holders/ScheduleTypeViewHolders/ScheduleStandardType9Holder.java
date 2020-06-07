package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;

public class ScheduleStandardType9Holder extends RecyclerView.ViewHolder {
    private Context context;
    private TextView numberPair;
    private TextView message;

    public ScheduleStandardType9Holder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        numberPair = itemView.findViewById(R.id.number_pair);
        message = itemView.findViewById(R.id.message);
    }

    public void bind(String numberPair, String message) {
        this.numberPair.setText(numberPair);
        this.message.setText(message);
    }
}
