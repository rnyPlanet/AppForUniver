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

public class ScheduleStandardType4Holder extends ScheduleStandardTypeParentHolder {
    private Context context;
    private TextView numberPair;
    private View bothSubgroupFirstWeek;
    private View bothSubgroupSecondWeek;

    public ScheduleStandardType4Holder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        numberPair = itemView.findViewById(R.id.number_pair);
        bothSubgroupFirstWeek = itemView.findViewById(R.id.both_subgroup_first_week);
        bothSubgroupSecondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            if (compareSubgroupAndWeek(classes, Subgroup.BOTH, Week.FIRST)) {
                initializeCardSubject(bothSubgroupFirstWeek, classes, context);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.BOTH, Week.SECOND)) {
                initializeCardSubject(bothSubgroupSecondWeek, classes, context);
            }
        }
    }
}
