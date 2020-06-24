package com.grin.appforuniver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.models.Groups;
import com.grin.appforuniver.data.models.Professors;
import com.grin.appforuniver.data.models.Rooms;
import com.grin.appforuniver.holders.FilterHolders.DefaultViewHolder;
import com.grin.appforuniver.holders.FilterHolders.GroupViewHolder;
import com.grin.appforuniver.holders.FilterHolders.ProfessorViewHolder;
import com.grin.appforuniver.holders.FilterHolders.RoomViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ChipFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ChipFilterAdapter";
    private static final int TYPE_DEFAULT_VIEW = 0;
    private static final int TYPE_PROFESSOR_VIEW = 1;
    private static final int TYPE_ROOM_VIEW = 2;
    private static final int TYPE_GROUP_VIEW = 3;
    private Context context;
    private List<Object> listFilterItems;
    private OnRemovedFilterItem onRemovedFilterItem;

    public ChipFilterAdapter(OnRemovedFilterItem onRemovedFilterItem) {
        this.onRemovedFilterItem = onRemovedFilterItem;
        this.listFilterItems = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return FilterCellType.get(listFilterItems.get(position)).type();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FilterCellType.get(viewType).holder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View.OnClickListener deleteItemListener = view -> deleteItemFilter(holder.getAdapterPosition());
        Object filter = listFilterItems.get(holder.getAdapterPosition());
        FilterCellType.get(filter).bind(holder, filter, deleteItemListener);
    }

    @Override
    public int getItemCount() {
        return listFilterItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
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

    enum FilterCellType {
        TYPE_DEFAULT {
            @Override
            boolean is(Object item) {
                return item instanceof String;
            }

            @Override
            int type() {
                return TYPE_DEFAULT_VIEW;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.chip_item, parent, false);
                return new DefaultViewHolder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, Object item, View.OnClickListener deleteItemListener) {
                try {
                    String text = (String) item;
                    DefaultViewHolder defaultViewHolder = (DefaultViewHolder) holder;
                    defaultViewHolder.bind(text);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        },
        TYPE_PROFESSOR {
            @Override
            boolean is(Object item) {
                return item instanceof Professors;
            }

            @Override
            int type() {
                return TYPE_PROFESSOR_VIEW;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.chip_item_choice, parent, false);
                return new ProfessorViewHolder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, Object item, View.OnClickListener deleteItemListener) {
                try {
                    Professors professors = (Professors) item;
                    ProfessorViewHolder professorViewHolder = (ProfessorViewHolder) holder;
                    professorViewHolder.bind(professors, deleteItemListener);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        },
        TYPE_ROOM {
            @Override
            boolean is(Object item) {
                return item instanceof Rooms;
            }

            @Override
            int type() {
                return TYPE_ROOM_VIEW;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.chip_item_choice, parent, false);
                return new RoomViewHolder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, Object item, View.OnClickListener deleteItemListener) {
                try {
                    Rooms rooms = (Rooms) item;
                    RoomViewHolder roomViewHolder = (RoomViewHolder) holder;
                    roomViewHolder.bind(rooms, deleteItemListener);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        },
        TYPE_GROUP {
            @Override
            boolean is(Object item) {
                return item instanceof Groups;
            }

            @Override
            int type() {
                return TYPE_GROUP_VIEW;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.chip_item_choice, parent, false);
                return new GroupViewHolder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, Object item, View.OnClickListener deleteItemListener) {
                try {
                    Groups group = (Groups) item;
                    GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                    groupViewHolder.bind(group, deleteItemListener);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        };

        static FilterCellType get(Object item) {
            for (FilterCellType cellType : FilterCellType.values()) {
                if (cellType.is(item)) {
                    return cellType;
                }
            }
            throw new RuntimeException();
        }

        static FilterCellType get(int viewType) {
            for (FilterCellType cellType : FilterCellType.values()) {
                if (cellType.type() == viewType) {
                    return cellType;
                }
            }
            throw new RuntimeException();
        }

        abstract boolean is(Object item);

        abstract int type();

        abstract RecyclerView.ViewHolder holder(ViewGroup parent);

        abstract void bind(RecyclerView.ViewHolder holder, Object item, View.OnClickListener deleteItemListener);
    }

    public interface OnRemovedFilterItem {
        void onRemovedFilterItem(List<Object> listFilterItems);
    }
}
