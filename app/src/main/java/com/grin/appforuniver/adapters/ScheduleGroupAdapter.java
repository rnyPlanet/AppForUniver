package com.grin.appforuniver.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.databinding.ScheduleDaySeparatorBinding;
import com.grin.appforuniver.databinding.ScheduleSingleType1Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType2Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType3Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType4Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType5Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType6Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType7Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType8Binding;
import com.grin.appforuniver.databinding.ScheduleSingleType9Binding;
import com.grin.appforuniver.databinding.ScheduleWeekendDayBinding;
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
        return ScheduleCellType.get(itemList.get(position)).getLayoutId();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ScheduleCellType.get(viewType).holder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
            return ScheduleCellType.get(itemList.get(itemPosition)).getLayoutId() == R.layout.schedule_day_separator;
        } else {
            return false;
        }
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        if (itemList.size() > 0) {
            return ScheduleCellType.get(itemList.get(headerPosition)).getLayoutId();
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
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_1;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType1Binding binding = ScheduleSingleType1Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType1Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType1Holder) holder).bind(item);
            }
        },
        TYPE_2 {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_2;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType2Binding binding = ScheduleSingleType2Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType2Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType2Holder) holder).bind(item);
            }
        },
        TYPE_3 {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_3;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType3Binding binding = ScheduleSingleType3Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType3Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType3Holder) holder).bind(item);
            }
        },
        TYPE_4 {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_4;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType4Binding binding = ScheduleSingleType4Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType4Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType4Holder) holder).bind(item);
            }
        },
        TYPE_5 {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_5;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType5Binding binding = ScheduleSingleType5Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType5Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType5Holder) holder).bind(item);
            }
        },
        TYPE_6 {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_6;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType6Binding binding = ScheduleSingleType6Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType6Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType6Holder) holder).bind(item);
            }
        },
        TYPE_7 {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_7;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType7Binding binding = ScheduleSingleType7Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType7Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType7Holder) holder).bind(item);
            }
        },
        TYPE_8 {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_8;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType8Binding binding = ScheduleSingleType8Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType8Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType8Holder) holder).bind(item);
            }
        },
        TYPE_9_MESSAGE {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_single_type_9;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleSingleType9Binding binding = ScheduleSingleType9Binding.inflate(inflater, parent, false);
                return new ScheduleStandardType9Holder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardType9Holder) holder).bind(String.valueOf(item.positionInDay), "Message");
            }
        },
        TYPE_DAY_SEPARATOR {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_day_separator;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleDaySeparatorBinding binding = ScheduleDaySeparatorBinding.inflate(inflater, parent, false);
                return new ScheduleStandardTypeDaySeparatorHolder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                ((ScheduleStandardTypeDaySeparatorHolder) holder).bind(item.place.toString());
            }
        },
        TYPE_DAY_WEEKEND {
            @LayoutRes
            @Override
            int getLayoutId() {
                return R.layout.schedule_weekend_day;
            }

            @Override
            RecyclerView.ViewHolder holder(ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ScheduleWeekendDayBinding binding = ScheduleWeekendDayBinding.inflate(inflater, parent, false);
                return new ScheduleStandardTypeWeekendHolder(binding);
            }

            @Override
            void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item) {
                //Nothing bind
            }
        };

        static ScheduleCellType get(ScheduleStandardTypeModel item) {
            for (ScheduleCellType cellType : ScheduleCellType.values()) {
                if (item.typeItem == cellType.getLayoutId()) {
                    return cellType;
                }
            }
            throw new RuntimeException();
        }

        static ScheduleCellType get(int viewType) {
            for (ScheduleCellType cellType : ScheduleCellType.values()) {
                if (cellType.getLayoutId() == viewType) {
                    return cellType;
                }
            }
            throw new RuntimeException();
        }

        @LayoutRes
        abstract int getLayoutId();

        abstract RecyclerView.ViewHolder holder(ViewGroup parent);

        abstract void bind(RecyclerView.ViewHolder holder, ScheduleStandardTypeModel item);
    }
}
