package com.grin.appforuniver.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType1Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType2Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType3Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType4Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType5Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType6Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType7Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType8Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardType9Holder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardTypeDaySeparatorHolder;
import com.grin.appforuniver.holders.ScheduleTypeViewHolders.ScheduleStandardTypeWeekendHolder;
import com.grin.appforuniver.utils.StickHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickHeaderItemDecoration.StickyHeaderInterface {
    private static final String TAG = "ScheduleGroupAdapter";

    private List<ScheduleStandardTypeModel> itemList = new ArrayList<>();

    public ScheduleGroupAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        return ScheduleCellType.get(itemList.get(position)).type();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ScheduleCellType.get(viewType).holder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScheduleStandardTypeModel item = itemList.get(position);
        ScheduleCellType.get(item).bind(holder, item);
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

    @Override
    public boolean isHeader(int itemPosition) {
        if (itemList.size() > 0) {
            return ScheduleCellType.get(itemList.get(itemPosition)).type() == R.layout.schedule_day_separator;
        } else {
            return false;
        }
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        if (itemList.size() > 0) {
            return ScheduleCellType.get(itemList.get(headerPosition)).type();
        } else {
            return 0;
        }
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        // binding our header data here
        ((TextView) header.findViewById(R.id.text_day)).setText(itemList.get(headerPosition).place.name());
    }

    enum ScheduleCellType {
        TYPE_1 {
            @Override
            int type() {
                return R.layout.schedule_single_type_1;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType1Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType1Holder) holder).bind(item);
            }
        },
        TYPE_2 {
            @Override
            int type() {
                return R.layout.schedule_single_type_2;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType2Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType2Holder) holder).bind(item);
            }
        },
        TYPE_3 {
            @Override
            int type() {
                return R.layout.schedule_single_type_3;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType3Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType3Holder) holder).bind(item);
            }
        },
        TYPE_4 {
            @Override
            int type() {
                return R.layout.schedule_single_type_4;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType4Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType4Holder) holder).bind(item);
            }
        },
        TYPE_5 {
            @Override
            int type() {
                return R.layout.schedule_single_type_5;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType5Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType5Holder) holder).bind(item);
            }
        },
        TYPE_6 {
            @Override
            int type() {
                return R.layout.schedule_single_type_6;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType6Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType6Holder) holder).bind(item);
            }
        },
        TYPE_7 {
            @Override
            int type() {
                return R.layout.schedule_single_type_7;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType7Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType7Holder) holder).bind(item);
            }
        },
        TYPE_8 {
            @Override
            int type() {
                return R.layout.schedule_single_type_8;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType8Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType8Holder) holder).bind(item);
            }
        },
        TYPE_9_MESSAGE {
            @Override
            int type() {
                return R.layout.schedule_single_type_9;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardType9Holder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType9Holder) holder).bind(String.valueOf(item.positionInDay), "Message");
            }
        },
        TYPE_DAY_SEPARATOR {
            @Override
            int type() {
                return R.layout.schedule_day_separator;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardTypeDaySeparatorHolder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardTypeDaySeparatorHolder) holder).bind(item.place.toString());
            }
        },
        TYPE_DAY_WEEKEND {
            @Override
            int type() {
                return R.layout.schedule_weekend_day;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(type(), parent, false);
                return new ScheduleStandardTypeWeekendHolder(view);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                //Nothing bind
            }
        };

        static ScheduleCellType get(ScheduleStandardTypeModel item) {
            for (ScheduleCellType cellType : ScheduleCellType.values()) {
                if (item.typeItem == cellType.type()) {
                    return cellType;
                }
            }
            throw new RuntimeException();
        }

        static ScheduleCellType get(int viewType) {
            for (ScheduleCellType cellType : ScheduleCellType.values()) {
                if (cellType.type() == viewType) {
                    return cellType;
                }
            }
            throw new RuntimeException();
        }

        abstract int type();

        abstract RecyclerView.ViewHolder holder(ViewGroup parent);

        abstract void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item);
    }
}
