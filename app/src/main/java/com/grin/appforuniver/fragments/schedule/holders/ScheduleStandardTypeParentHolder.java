package com.grin.appforuniver.fragments.schedule.holders;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;

class ScheduleStandardTypeParentHolder extends RecyclerView.ViewHolder {
    ScheduleStandardTypeParentHolder(@NonNull View itemView) {
        super(itemView);
    }

    void initializeCardSubject(View parentView, Classes classes, Context context) {
        //initialize subject
        ((TextView) parentView.findViewById(R.id.subject)).setText(classes.getSubject());
        //initialize audience room
        ((TextView) parentView.findViewById(R.id.audience_room)).setText(classes.getRoom().getName());
        //initialize professor name
        ((TextView) parentView.findViewById(R.id.professor)).setText(classes.getProfessor().getUser().getShortFIO());
        parentView.setOnClickListener(view -> dialogMoreDetailsSchedule(classes, context).show());
    }

    private AlertDialog dialogMoreDetailsSchedule(Classes classes, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.dialog_more_details_schedule, null);
        TextView textClasses = rootView.findViewById(R.id.text_classes);
        textClasses.setText(classes.toString());
        builder.setTitle(classes.getSubject());
        builder.setView(rootView);
        builder.setPositiveButton("close", (dialogInterface, i) -> {
        });
        return builder.create();
    }
}
