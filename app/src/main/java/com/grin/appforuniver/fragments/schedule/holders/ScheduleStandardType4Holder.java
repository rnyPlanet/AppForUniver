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

public class ScheduleStandardType4Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View bothSubgroupFirstWeek;
    private View bothSubgroupSecondWeek;

    public ScheduleStandardType4Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        bothSubgroupFirstWeek = itemView.findViewById(R.id.both_subgroup_first_week);
        bothSubgroupSecondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            if (compareSubgroupAndWeek(classes, Subgroup.BOTH, Week.FIRST)) {
                initializeCardSubject(bothSubgroupFirstWeek, classes);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.BOTH, Week.SECOND)) {
                initializeCardSubject(bothSubgroupSecondWeek, classes);
            }
        }
    }
}
