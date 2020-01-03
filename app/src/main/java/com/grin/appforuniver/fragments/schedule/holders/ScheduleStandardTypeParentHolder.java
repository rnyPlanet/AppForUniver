package com.grin.appforuniver.fragments.schedule.holders;

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

    void initializeCardSubject(View parentView, Classes classes) {
        //initialize subject
        ((TextView) parentView.findViewById(R.id.subject)).setText(classes.getSubject());
        //initialize audience room
        ((TextView) parentView.findViewById(R.id.audience_room)).setText(classes.getRoom().getName());
        //initialize professor name
        String professorsName = classes.getProfessor().getUser().getLastName()
                + " " + classes.getProfessor().getUser().getShortFormFirstName()
                + " " + classes.getProfessor().getUser().getShortFormPatronymic();
        ((TextView) parentView.findViewById(R.id.professor)).setText(professorsName);
    }
}
