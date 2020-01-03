package com.grin.appforuniver.fragments.schedule.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;

import java.util.List;

import static com.grin.appforuniver.data.utils.Constants.Subgroup;
import static com.grin.appforuniver.data.utils.Constants.Week;
import static com.grin.appforuniver.data.utils.Functions.Schedule.compareSubgroupAndWeek;

public class ScheduleStandardType7Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View bothSubgroupFirstWeek;
    private View firstSubgroupSecondWeek;
    private View secondSubgroupSecondWeek;

    public ScheduleStandardType7Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        bothSubgroupFirstWeek = itemView.findViewById(R.id.both_subgroup_first_week);
        firstSubgroupSecondWeek = itemView.findViewById(R.id.first_subgroup_second_week);
        secondSubgroupSecondWeek = itemView.findViewById(R.id.second_subgroup_second_week);
    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            if (compareSubgroupAndWeek(classes, Subgroup.BOTH, Week.FIRST)) {
                initializeCardSubject(bothSubgroupFirstWeek, classes);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.FIRST, Week.SECOND)) {
                initializeCardSubject(firstSubgroupSecondWeek, classes);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.SECOND, Week.SECOND)) {
                initializeCardSubject(secondSubgroupSecondWeek, classes);
            }
        }
    }
}
