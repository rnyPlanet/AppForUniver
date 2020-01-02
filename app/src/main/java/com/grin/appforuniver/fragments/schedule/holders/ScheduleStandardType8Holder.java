package com.grin.appforuniver.fragments.schedule.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.utils.Functions;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;

import java.util.List;

import static com.grin.appforuniver.data.utils.Constants.Subgroup;
import static com.grin.appforuniver.data.utils.Constants.Week;
import static com.grin.appforuniver.data.utils.Functions.Schedule.compareSubgroupAndWeek;

public class ScheduleStandardType8Holder extends RecyclerView.ViewHolder {
    private TextView numberPair;
    private View firstSubgroupFirstWeek;
    private View secondSubgroupFirstWeek;
    private View bothSubgroupSecondWeek;

    public ScheduleStandardType8Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        firstSubgroupFirstWeek = itemView.findViewById(R.id.first_subgroup_first_week);
        secondSubgroupFirstWeek = itemView.findViewById(R.id.second_subgroup_first_week);
        bothSubgroupSecondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            TextView subject = null;
            TextView audienceRoom = null;
            if (compareSubgroupAndWeek(classes, Subgroup.FIRST, Week.FIRST)) {
                subject = firstSubgroupFirstWeek.findViewById(R.id.subject);
                audienceRoom = firstSubgroupFirstWeek.findViewById(R.id.audience_room);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.SECOND, Week.FIRST)) {
                subject = secondSubgroupFirstWeek.findViewById(R.id.subject);
                audienceRoom = secondSubgroupFirstWeek.findViewById(R.id.audience_room);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.BOTH, Week.SECOND)) {
                subject = bothSubgroupSecondWeek.findViewById(R.id.subject);
                audienceRoom = bothSubgroupSecondWeek.findViewById(R.id.audience_room);
            }

            if (subject != null) subject.setText(classes.getSubject());
            if (audienceRoom != null) audienceRoom.setText(classes.getRoomID().getName());
        }
    }

}
