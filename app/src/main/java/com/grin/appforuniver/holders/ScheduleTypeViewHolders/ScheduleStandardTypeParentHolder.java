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
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;
import com.grin.appforuniver.utils.Constants;

import java.util.List;

abstract class ScheduleStandardTypeParentHolder extends RecyclerView.ViewHolder {
    Context context;

    ScheduleStandardTypeParentHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }

    void bindCardSubject(View parentView, Classes classes) {
        parentView.setVisibility(View.VISIBLE);
        //initialize subject
        ((TextView) parentView.findViewById(R.id.subject)).setText(classes.getSubject().getShortname());
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

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        bindNumberPair(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.BOTH, Constants.Week.BOTH)) {
                bindBothSubgroupBothWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.FIRST, Constants.Week.BOTH)) {
                bindFirstSubgroupBothWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.SECOND, Constants.Week.BOTH)) {
                bindSecondSubgroupBothWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.FIRST, Constants.Week.FIRST)) {
                bindFirstSubgroupFirstWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.SECOND, Constants.Week.FIRST)) {
                bindSecondSubgroupFirstWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.FIRST, Constants.Week.SECOND)) {
                bindFirstSubgroupSecondWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.SECOND, Constants.Week.SECOND)) {
                bindSecondSubgroupSecondWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.BOTH, Constants.Week.FIRST)) {
                bindBothSubgroupFirstWeek(classes);
            }
            if (classes.compareToSubgroupAndWeek(Constants.Subgroup.BOTH, Constants.Week.SECOND)) {
                bindBothSubgroupSecondWeek(classes);
            }
        }
    }

    public abstract void bindNumberPair(String numberPair);

    public void bindBothSubgroupBothWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindFirstSubgroupBothWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindSecondSubgroupBothWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindFirstSubgroupFirstWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindSecondSubgroupFirstWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindFirstSubgroupSecondWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindSecondSubgroupSecondWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindBothSubgroupFirstWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

    public void bindBothSubgroupSecondWeek(Classes classes) {
        throw new RuntimeException("Stub! " + classes.getSubgroup() + " " + classes.getWeek());
    }

}
