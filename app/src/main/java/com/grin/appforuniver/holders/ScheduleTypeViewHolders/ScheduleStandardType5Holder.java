package com.grin.appforuniver.holders.ScheduleTypeViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;

import java.util.List;

import static com.grin.appforuniver.utils.Constants.Subgroup;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleStandardType5Holder extends ScheduleStandardTypeParentHolder {
    private Context context;
    private TextView numberPair;
    private View firstSubgroupBothWeek;
    private View secondSubgroupFirstWeek;
    private View secondSubgroupSecondWeek;

    public ScheduleStandardType5Holder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        numberPair = itemView.findViewById(R.id.number_pair);
        firstSubgroupBothWeek = itemView.findViewById(R.id.first_subgroup_both_week);
        secondSubgroupFirstWeek = itemView.findViewById(R.id.second_subgroup_first_week);
        secondSubgroupSecondWeek = itemView.findViewById(R.id.second_subgroup_second_week);
    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            if (compareSubgroupAndWeek(classes, Subgroup.FIRST, Week.BOTH)) {
                initializeCardSubject(firstSubgroupBothWeek, classes, context);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.SECOND, Week.FIRST)) {
                initializeCardSubject(secondSubgroupFirstWeek, classes, context);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.SECOND, Week.SECOND)) {
                initializeCardSubject(secondSubgroupSecondWeek, classes, context);
            }
        }
    }
}
