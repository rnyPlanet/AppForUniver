package com.grin.appforuniver.fragments.schedule.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;

import java.util.ArrayList;
import java.util.List;

public class ProfessorScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ProfessorScheduleAdapter";
    private Context context;
    private List<ScheduleStandardTypeModel> itemList = new ArrayList<>();

    public ProfessorScheduleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).typeItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(viewType, parent, false);
        if (viewType == R.layout.schedule_single_type_1) {
            return new ScheduleProfessorType1Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_4) {
            return new ScheduleProfessorType4Holder(view);
        }
        if (viewType == R.layout.item_day_separator) {
            return new ScheduleProfessorTypeDaySeparatorHolder(view);
        }
        if (viewType == R.layout.schedule_weekend_day) {
            return new ScheduleProfessorTypeWeekendHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_1) {
            ((ScheduleProfessorType1Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_4) {
            ((ScheduleProfessorType4Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.item_day_separator) {
            ((ScheduleProfessorTypeDaySeparatorHolder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
    }

    public void setClasses(List<ScheduleStandardTypeModel> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ScheduleProfessorType1Holder extends ClassesHolder {

        ScheduleProfessorType1Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            bothSubgroup_bothWeek = itemView.findViewById(R.id.both_subgroup_both_week);
        }
    }

    class ScheduleProfessorType4Holder extends ClassesHolder {

        ScheduleProfessorType4Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            bothSubgroup_firstWeek = itemView.findViewById(R.id.both_subgroup_first_week);
            bothSubgroup_secondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
        }
    }

    class ScheduleProfessorTypeDaySeparatorHolder extends ClassesHolder {
        TextView day;

        ScheduleProfessorTypeDaySeparatorHolder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            day = itemView.findViewById(R.id.text_day);
        }

        void bind(ScheduleStandardTypeModel schedulePair) {
            day.setText(schedulePair.place.toString());
        }
    }

    class ScheduleProfessorTypeWeekendHolder extends ClassesHolder {

        ScheduleProfessorTypeWeekendHolder(@NonNull View itemView) {
            super(itemView);
        }


    }

    class ClassesHolder extends RecyclerView.ViewHolder {
        TextView numberPair;
        TextView bothSubgroup_firstWeek;
        TextView bothSubgroup_secondWeek;
        TextView bothSubgroup_bothWeek;

        ClassesHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(ScheduleStandardTypeModel schedulePair) {
            List<Classes> mListClasses = schedulePair.classes;
            try {
                numberPair.setText(String.valueOf(schedulePair.positionInDay));
            } catch (Exception e) {
                Log.d(TAG, "EXCEPTION TYPE 5 " + schedulePair);
            }
            for (Classes classes : mListClasses) {
                String text = classes.getAssignedGroupID().getmName() + " " + classes.getSubject();
                if (classes.getWeek() == Constants.Week.FIRST) {
                    bothSubgroup_firstWeek.setText(text);
                }
                if (classes.getWeek() == Constants.Week.SECOND) {
                    bothSubgroup_secondWeek.setText(text);
                }
                if (classes.getWeek() == Constants.Week.BOTH) {
                    bothSubgroup_bothWeek.setText(text);
                }
            }
        }
    }
}
