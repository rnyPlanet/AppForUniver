package com.grin.appforuniver.fragments.schedule.holders;

import android.content.Context;
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

public class ScheduleStandardType2Holder extends ScheduleStandardTypeParentHolder {
    private Context context;
    private TextView numberPair;
    private View firstSubgroupBothWeek;
    private View secondSubgroupBothWeek;

    public ScheduleStandardType2Holder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        numberPair = itemView.findViewById(R.id.number_pair);
        firstSubgroupBothWeek = itemView.findViewById(R.id.first_subgroup_both_week);
        secondSubgroupBothWeek = itemView.findViewById(R.id.second_subgroup_both_week);

    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            if (compareSubgroupAndWeek(classes, Subgroup.FIRST, Week.BOTH)) {
                initializeCardSubject(firstSubgroupBothWeek, classes, context);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.SECOND, Week.BOTH)) {
                initializeCardSubject(secondSubgroupBothWeek, classes, context);
            }
        }
    }
}
