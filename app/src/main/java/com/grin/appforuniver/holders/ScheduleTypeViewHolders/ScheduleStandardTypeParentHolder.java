package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.utils.Constants;

abstract class ScheduleStandardTypeParentHolder extends RecyclerView.ViewHolder {
    ScheduleStandardTypeParentHolder(@NonNull View itemView) {
        super(itemView);
    }

    void initializeCardSubject(View parentView, Classes classes, Context context) {
        parentView.setVisibility(View.VISIBLE);
        //initialize subject
        ((TextView) parentView.findViewById(R.id.subject)).setText(classes.getSubject().getShortname());
        //initialize audience room
        ((TextView) parentView.findViewById(R.id.audience_room)).setText(classes.getRoom().getName());
        //initialize professor name
        ((TextView) parentView.findViewById(R.id.professor)).setText(classes.getProfessor().getUser().getShortFIO());
        parentView.setOnClickListener(view -> dialogMoreDetailsSchedule(classes, context).show());
    }

    boolean compareSubgroupAndWeek(Classes classes, Constants.Subgroup subgroup, Constants.Week week) {
        return classes.getSubgroup() == subgroup && classes.getWeek() == week;
    }

    private AlertDialog dialogMoreDetailsSchedule(Classes classes, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.dialog_more_details_schedule, null);

        TextView nameSubject = rootView.findViewById(R.id.name_subject);
        TextView nameProf = rootView.findViewById(R.id.name_prof);
        TextView nameProfPosada = rootView.findViewById(R.id.name_prof_posada);
        TextView nameGroup = rootView.findViewById(R.id.name_group);
        TextView typeSubject = rootView.findViewById(R.id.name_subject_type);
        nameSubject.setText(classes.getSubject().getFullName());
        nameProf.setText(classes.getProfessor().getUser().getFullFIO());
        nameProfPosada.setText(classes.getProfessor().getPosada().getFullPostProfessor());
        nameGroup.setText(classes.getAssignedGroup().getmName());
        typeSubject.setText(classes.getType().getType());
        builder.setTitle(classes.getSubject().getShortname());
        builder.setView(rootView);
        builder.setPositiveButton(R.string.hide_description, (dialogInterface, i) -> {
        });
        return builder.create();
    }
}
