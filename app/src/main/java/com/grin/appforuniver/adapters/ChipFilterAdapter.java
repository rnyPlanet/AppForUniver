package com.grin.appforuniver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;

import java.util.ArrayList;
import java.util.List;

public class ChipFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ChipFilterAdapter";
    private static final int TYPE_PROFESSOR = 0;
    private static final int TYPE_ROOM = 1;
    private static final int TYPE_GROUP = 2;
    private static final int TYPE_STRING = 3;
    private Context context;
    private List<Object> listFilterItems;
    private OnRemovedFilterItem onRemovedFilterItem;

    public ChipFilterAdapter(Context context, OnRemovedFilterItem onRemovedFilterItem) {
        this.context = context;
        this.onRemovedFilterItem = onRemovedFilterItem;
        this.listFilterItems = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = listFilterItems.get(position);
        if (item instanceof Professors) {
            return TYPE_PROFESSOR;
        } else if (item instanceof Rooms) {
            return TYPE_ROOM;
        } else if (item instanceof Groups) {
            return TYPE_GROUP;
        } else if (item instanceof String) {
            return TYPE_STRING;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_PROFESSOR) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new ProfessorViewHolder(view);
        } else if (viewType == TYPE_ROOM) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new RoomViewHolder(view);
        } else if (viewType == TYPE_GROUP) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new GroupViewHolder(view);
        } else if (viewType == TYPE_STRING) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProfessorViewHolder) {
            ((ProfessorViewHolder) holder).bind((Professors) listFilterItems.get(position));
        } else if (holder instanceof RoomViewHolder) {
            ((RoomViewHolder) holder).bind((Rooms) listFilterItems.get(position));
        } else if (holder instanceof GroupViewHolder) {
            ((GroupViewHolder) holder).bind((Groups) listFilterItems.get(position));
        } else if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bind((String) listFilterItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listFilterItems.size();
    }

    public void setItemsFilter(List<Object> listFilterItems) {
        this.listFilterItems = listFilterItems;
        if (this.listFilterItems.size() == 0) {
            this.listFilterItems.add(context.getString(R.string.your_schedule));
        }
        notifyDataSetChanged();
    }

    private void deleteItemFilter(int position) {
        listFilterItems.remove(position);
        if (onRemovedFilterItem != null)
            onRemovedFilterItem.onRemovedFilterItem(listFilterItems);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(String text) {
            chip.setText(text);
        }

    }

    public class ProfessorViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(Professors professors) {
            String text = context.getString(R.string.chip_adapter_professor) + professors.getUser().getShortFIO();
            chip.setText(text);
            chip.setOnCloseIconClickListener(view -> deleteItemFilter(getAdapterPosition()));
        }
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(Rooms rooms) {
            String text = context.getString(R.string.chip_adapter_room) + rooms.getName();
            chip.setText(text);
            chip.setOnCloseIconClickListener(view -> deleteItemFilter(getAdapterPosition()));
        }
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(Groups group) {
            String text = context.getString(R.string.chip_adapter_group) + group.getmName();
            chip.setText(text);
            chip.setOnCloseIconClickListener(view -> deleteItemFilter(getAdapterPosition()));
        }
    }

    public interface OnRemovedFilterItem {
        void onRemovedFilterItem(List<Object> listFilterItems);
    }
}
