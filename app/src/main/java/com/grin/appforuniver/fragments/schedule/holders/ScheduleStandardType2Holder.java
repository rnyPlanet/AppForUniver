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

public class ScheduleStandardType2Holder extends RecyclerView.ViewHolder {
    private TextView numberPair;
    private View firstSubgroupBothWeek;
    private View secondSubgroupBothWeek;

    public ScheduleStandardType2Holder(@NonNull View itemView) {
        super(itemView);
        numberPair = itemView.findViewById(R.id.number_pair);
        firstSubgroupBothWeek = itemView.findViewById(R.id.first_subgroup_both_week);
        secondSubgroupBothWeek = itemView.findViewById(R.id.second_subgroup_both_week);

    }

    public void bind(ScheduleStandardTypeModel schedulePair) {
        List<Classes> mListClasses = schedulePair.classes;
        numberPair.setText(String.valueOf(schedulePair.positionInDay));

        for (Classes classes : mListClasses) {
            TextView subject = null;
            TextView audienceRoom = null;
            if (compareSubgroupAndWeek(classes, Subgroup.FIRST, Week.BOTH)) {
                subject = firstSubgroupBothWeek.findViewById(R.id.subject);
                audienceRoom = firstSubgroupBothWeek.findViewById(R.id.audience_room);
            }
            if (compareSubgroupAndWeek(classes, Subgroup.SECOND, Week.BOTH)) {
                subject = secondSubgroupBothWeek.findViewById(R.id.subject);
                audienceRoom = secondSubgroupBothWeek.findViewById(R.id.audience_room);
            }

            if (subject != null) subject.setText(classes.getSubject());
            if (audienceRoom != null) audienceRoom.setText(classes.getRoomID().getName());
        }
    }
}
