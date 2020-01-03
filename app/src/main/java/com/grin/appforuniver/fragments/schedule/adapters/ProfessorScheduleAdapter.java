package com.grin.appforuniver.fragments.schedule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType1Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardType4Holder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardTypeDaySeparatorHolder;
import com.grin.appforuniver.fragments.schedule.holders.ScheduleStandardTypeWeekendHolder;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(viewType, parent, false);
        if (viewType == R.layout.schedule_single_type_1) {
            return new ScheduleStandardType1Holder(view, context);
        }
        if (viewType == R.layout.schedule_single_type_4) {
            return new ScheduleStandardType4Holder(view, context);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_1) {
            ((ScheduleStandardType1Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_4) {
            ((ScheduleStandardType4Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
        }
        if (getItemViewType(holder.getAdapterPosition()) == R.layout.item_day_separator) {
            ((ScheduleStandardTypeDaySeparatorHolder) holder).bind(itemList.get(holder.getAdapterPosition()).place.toString());
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
}
