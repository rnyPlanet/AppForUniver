package com.grin.appforuniver.fragments.schedule.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;

import java.util.List;

import static com.grin.appforuniver.data.utils.Constants.Subgroup;
import static com.grin.appforuniver.data.utils.Constants.Week;
import static com.grin.appforuniver.data.utils.Functions.Schedule.compareSubgroupAndWeek;

public class ScheduleStandardType1Holder extends ScheduleStandardTypeParentHolder {
    private TextView numberPair;
    private View bothSubgroupBothWeek;

    public ScheduleStandardType1Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        bothSubgroupBothWeek = itemView.findViewById(R.id.both_subgroup_both_week);
    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            if (compareSubgroupAndWeek(classes, Subgroup.BOTH, Week.BOTH)) {
                initializeCardSubject(bothSubgroupBothWeek, classes);
            }
        }
    }
}
