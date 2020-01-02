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
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType1Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType2Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType3Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType4Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType5Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType6Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType7Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType8Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType9Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardTypeDaySeparatorHolder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardTypeWeekendHolder;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ScheduleGroupAdapter";

    private List<ScheduleStandardTypeModel> itemList = new ArrayList<>();
    private Context context;

    public ScheduleGroupAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).typeItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(viewType, parent, false);
        if (viewType == R.layout.schedule_single_type_1) {
            return new ScheduleStandardType1Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_2) {
            return new ScheduleStandardType2Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_3) {
            return new ScheduleStandardType3Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_4) {
            return new ScheduleStandardType4Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_5) {
            return new ScheduleStandardType5Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_6) {
            return new ScheduleStandardType6Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_7) {
            return new ScheduleStandardType7Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_8) {
            return new ScheduleStandardType8Holder(view);
        }
        if (viewType == R.layout.schedule_single_type_9) {
            return new ScheduleStandardType9Holder(view);
        }
        if (viewType == R.layout.item_day_separator) {
            return new ScheduleStandardTypeDaySeparatorHolder(view);
        }
        if (viewType == R.layout.schedule_weekend_day) {
            return new ScheduleStandardTypeWeekendHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_1) {
            ((ScheduleStandardType1Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_2) {
            ((ScheduleStandardType2Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_3) {
            ((ScheduleStandardType3Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_4) {
            ((ScheduleStandardType4Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_5) {
            ((ScheduleStandardType5Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_6) {
            ((ScheduleStandardType6Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_7) {
            ((ScheduleStandardType7Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_8) {
            ((ScheduleStandardType8Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_9) {
            ((ScheduleStandardType9Holder) holder).bind(
                    String.valueOf(itemList.get(holder.getAdapterPosition()).positionInDay),
                    "Message");
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.item_day_separator) {
            ((ScheduleStandardTypeDaySeparatorHolder) holder).bind(itemList.get(holder.getAdapterPosition()).place.toString());
        }
//            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_weekend_day) {
//                ((ScheduleStandardTypeWeekendHolder) holder).bind();
//            }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setClasses(List<ScheduleStandardTypeModel> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    class ClassesHolder extends RecyclerView.ViewHolder {
        TextView numberPair;
        TextView firstSubgroup_firstWeek;
        TextView firstSubgroup_secondWeek;
        TextView firstSubgroup_bothWeek;
        TextView secondSubgroup_firstWeek;
        TextView secondSubgroup_secondWeek;
        TextView secondSubgroup_bothWeek;
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
                if (classes.getSubgroup() == Constants.Subgroup.FIRST && classes.getWeek() == Constants.Week.FIRST) {
                    firstSubgroup_firstWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.FIRST && classes.getWeek() == Constants.Week.SECOND) {
                    firstSubgroup_secondWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.FIRST && classes.getWeek() == Constants.Week.BOTH) {
                    firstSubgroup_bothWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.SECOND && classes.getWeek() == Constants.Week.FIRST) {
                    secondSubgroup_firstWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.SECOND && classes.getWeek() == Constants.Week.SECOND) {
                    secondSubgroup_secondWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.SECOND && classes.getWeek() == Constants.Week.BOTH) {
                    secondSubgroup_bothWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.BOTH && classes.getWeek() == Constants.Week.FIRST) {
                    bothSubgroup_firstWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.BOTH && classes.getWeek() == Constants.Week.SECOND) {
                    bothSubgroup_secondWeek.setText(classes.getSubject());
                }
                if (classes.getSubgroup() == Constants.Subgroup.BOTH && classes.getWeek() == Constants.Week.BOTH) {
                    bothSubgroup_bothWeek.setText(classes.getSubject());
                }
            }
        }
    }
}
