package com.grin.appforuniver.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ScheduleInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.schedule.ScheduleBothSubgroupClassesModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    private View mView;


    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        getActivity().setTitle(R.string.menu_schedule);
        recyclerView = mView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScheduleAdapter(getContext());
        getSchedule(mView.getContext(), inflater);
        return mView;
    }

    private void getSchedule(Context context, LayoutInflater inflater) {
        ScheduleInterface scheduleInterface = ServiceGenerator.createService(ScheduleInterface.class);
        Call<List<Classes>> list = scheduleInterface.getScheduleAll(PreferenceUtils.getUserToken());
        list.enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                if (response.body() != null) {
                    List<Classes> lllist = new ArrayList<>(response.body());
                    List<ScheduleBothSubgroupClassesModel> schedulePairs = new ArrayList<>();
                    for (Constants.Place place : Constants.Place.values()) {
                        if (place == Constants.Place.POOL) continue;
                        schedulePairs.add(new ScheduleBothSubgroupClassesModel(R.layout.item_day_separator,
                                place, -1, null));
                        boolean isWeekendDay = false;
                        for (int i = 0; i <= 6; i++) {
                            List<Classes> classesInsidePairs = new ArrayList<>();
                            for (Classes classes : lllist) {
                                if (classes.getPlace() == place && classes.getIndexInDay() == i) {
                                    classesInsidePairs.add(classes);
                                }
                            }
                            if (classesInsidePairs.size() > 0) {
                                isWeekendDay = true;
                                schedulePairs.add(new ScheduleBothSubgroupClassesModel(setDataInLayout(classesInsidePairs),
                                        place, i + 1, classesInsidePairs));
                            }
                        }
                        if (!isWeekendDay) {
                            schedulePairs.add(new ScheduleBothSubgroupClassesModel(R.layout.schedule_weekend_day,
                                    place, -1, null));
                        }
                    }

                    adapter.setClasses(schedulePairs);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {

            }
        });

    }


    public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<ScheduleBothSubgroupClassesModel> itemList = new ArrayList<>();
        private Context context;

        public ScheduleAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            return itemList.get(position).typeItem;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(viewType, parent, false);
            if (viewType == R.layout.schedule_single_type_1) {
                return new ScheduleSingleType1Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_2) {
                return new ScheduleSingleType2Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_3) {
                return new ScheduleSingleType3Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_4) {
                return new ScheduleSingleType4Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_5) {
                return new ScheduleSingleType5Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_6) {
                return new ScheduleSingleType6Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_7) {
                return new ScheduleSingleType7Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_8) {
                return new ScheduleSingleType8Holder(view);
            }
            if (viewType == R.layout.schedule_single_type_9) {
                return new ScheduleSingleType9Holder(view);
            }
            if (viewType == R.layout.item_day_separator) {
                return new ScheduleSingleTypeDaySeparatorHolder(view);
            }
            if (viewType == R.layout.schedule_weekend_day) {
                return new ScheduleSingleTypeWeekendHolder(view);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_1) {
                ((ScheduleSingleType1Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_2) {
                ((ScheduleSingleType2Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_3) {
                ((ScheduleSingleType3Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_4) {
                ((ScheduleSingleType4Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_5) {
                ((ScheduleSingleType5Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_6) {
                ((ScheduleSingleType6Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_7) {
                ((ScheduleSingleType7Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_8) {
                ((ScheduleSingleType8Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_single_type_9) {
                ((ScheduleSingleType9Holder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
            if (getItemViewType(holder.getAdapterPosition()) == R.layout.item_day_separator) {
                ((ScheduleSingleTypeDaySeparatorHolder) holder).bind(itemList.get(holder.getAdapterPosition()));
            }
//            if (getItemViewType(holder.getAdapterPosition()) == R.layout.schedule_weekend_day) {
//                ((ScheduleSingleTypeWeekendHolder) holder).bind();
//            }
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        void setClasses(List<ScheduleBothSubgroupClassesModel> itemList) {
            this.itemList.clear();
            this.itemList.addAll(itemList);
            notifyDataSetChanged();
        }
    }

    public class ScheduleSingleType1Holder extends ClassesHolder {

        ScheduleSingleType1Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            bothSubgroup_bothWeek = itemView.findViewById(R.id.both_subgroup_both_week);
        }
    }

    public class ScheduleSingleType2Holder extends ClassesHolder {

        ScheduleSingleType2Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            firstSubgroup_bothWeek = itemView.findViewById(R.id.first_subgroup_both_week);
            secondSubgroup_bothWeek = itemView.findViewById(R.id.second_subgroup_both_week);

        }
    }

    public class ScheduleSingleType3Holder extends ClassesHolder {

        ScheduleSingleType3Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            firstSubgroup_firstWeek = itemView.findViewById(R.id.first_subgroup_first_week);
            secondSubgroup_firstWeek = itemView.findViewById(R.id.second_subgroup_first_week);
            firstSubgroup_secondWeek = itemView.findViewById(R.id.first_subgroup_second_week);
            secondSubgroup_secondWeek = itemView.findViewById(R.id.second_subgroup_second_week);
        }
    }

    public class ScheduleSingleType4Holder extends ClassesHolder {

        ScheduleSingleType4Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            bothSubgroup_firstWeek = itemView.findViewById(R.id.both_subgroup_first_week);
            bothSubgroup_secondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
        }
    }

    public class ScheduleSingleType5Holder extends ClassesHolder {

        ScheduleSingleType5Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            firstSubgroup_bothWeek = itemView.findViewById(R.id.first_subgroup_both_week);
            secondSubgroup_firstWeek = itemView.findViewById(R.id.second_subgroup_first_week);
            secondSubgroup_secondWeek = itemView.findViewById(R.id.second_subgroup_second_week);
        }
    }

    public class ScheduleSingleType6Holder extends ClassesHolder {

        ScheduleSingleType6Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            firstSubgroup_firstWeek = itemView.findViewById(R.id.first_subgroup_first_week);
            firstSubgroup_secondWeek = itemView.findViewById(R.id.first_subgroup_second_week);
            secondSubgroup_bothWeek = itemView.findViewById(R.id.second_subgroup_both_week);
        }
    }

    public class ScheduleSingleType7Holder extends ClassesHolder {

        ScheduleSingleType7Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            bothSubgroup_firstWeek = itemView.findViewById(R.id.both_subgroup_first_week);
            firstSubgroup_secondWeek = itemView.findViewById(R.id.first_subgroup_second_week);
            secondSubgroup_secondWeek = itemView.findViewById(R.id.second_subgroup_second_week);
        }
    }

    public class ScheduleSingleType8Holder extends ClassesHolder {

        ScheduleSingleType8Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            firstSubgroup_firstWeek = itemView.findViewById(R.id.first_subgroup_first_week);
            secondSubgroup_firstWeek = itemView.findViewById(R.id.second_subgroup_first_week);
            bothSubgroup_secondWeek = itemView.findViewById(R.id.both_subgroup_second_week);
        }
    }

    public class ScheduleSingleType9Holder extends ClassesHolder {

        ScheduleSingleType9Holder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
        }
    }

    public class ScheduleSingleTypeDaySeparatorHolder extends ClassesHolder {
        TextView day;

        ScheduleSingleTypeDaySeparatorHolder(@NonNull View itemView) {
            super(itemView);
            numberPair = itemView.findViewById(R.id.number_pair);
            day = itemView.findViewById(R.id.text_day);
        }

        void bind(ScheduleBothSubgroupClassesModel schedulePair) {
            day.setText(schedulePair.place.toString());
        }
    }

    public class ScheduleSingleTypeWeekendHolder extends ClassesHolder {
//        TextView day;

        ScheduleSingleTypeWeekendHolder(@NonNull View itemView) {
            super(itemView);
//            numberPair = itemView.findViewById(R.id.number_pair);
//            day = itemView.findViewById(R.id.text_day);
        }


    }


    public class ClassesHolder extends RecyclerView.ViewHolder {
        public TextView numberPair;
        public TextView firstSubgroup_firstWeek;
        public TextView firstSubgroup_secondWeek;
        public TextView firstSubgroup_bothWeek;
        public TextView secondSubgroup_firstWeek;
        public TextView secondSubgroup_secondWeek;
        public TextView secondSubgroup_bothWeek;
        public TextView bothSubgroup_firstWeek;
        public TextView bothSubgroup_secondWeek;
        public TextView bothSubgroup_bothWeek;

        public ClassesHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(ScheduleBothSubgroupClassesModel schedulePair) {
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

    private boolean bothSubgroup_firstWeek;
    private boolean bothSubgroup_secondWeek;
    private boolean bothSubgroup_bothWeek;
    private boolean firstSubgroup_firstWeek;
    private boolean firstSubgroup_secondWeek;
    private boolean firstSubgroup_bothWeek;
    private boolean secondSubgroup_firstWeek;
    private boolean secondSubgroup_secondWeek;
    private boolean secondSubgroup_bothWeek;

    private int setDataInLayout(List<Classes> mListClasses) {
        firstSubgroup_firstWeek = false;
        firstSubgroup_secondWeek = false;
        firstSubgroup_bothWeek = false;
        secondSubgroup_firstWeek = false;
        secondSubgroup_secondWeek = false;
        secondSubgroup_bothWeek = false;
        bothSubgroup_firstWeek = false;
        bothSubgroup_secondWeek = false;
        bothSubgroup_bothWeek = false;
        for (Classes classes : mListClasses) {
            if (classes.getSubgroup() == Constants.Subgroup.FIRST && classes.getWeek() == Constants.Week.FIRST) {
                firstSubgroup_firstWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.FIRST && classes.getWeek() == Constants.Week.SECOND) {
                firstSubgroup_secondWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.FIRST && classes.getWeek() == Constants.Week.BOTH) {
                firstSubgroup_bothWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.SECOND && classes.getWeek() == Constants.Week.FIRST) {
                secondSubgroup_firstWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.SECOND && classes.getWeek() == Constants.Week.SECOND) {
                secondSubgroup_secondWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.SECOND && classes.getWeek() == Constants.Week.BOTH) {
                secondSubgroup_bothWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.BOTH && classes.getWeek() == Constants.Week.FIRST) {
                bothSubgroup_firstWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.BOTH && classes.getWeek() == Constants.Week.SECOND) {
                bothSubgroup_secondWeek = true;
            }
            if (classes.getSubgroup() == Constants.Subgroup.BOTH && classes.getWeek() == Constants.Week.BOTH) {
                bothSubgroup_bothWeek = true;
            }
        }

        return getLayoutId();
    }

    private int getLayoutId() {
        if (bothSubgroup_bothWeek) {
            return R.layout.schedule_single_type_1;
        }
        // столбец 2*1
        if (firstSubgroup_bothWeek) {
            // layout 2,5
            if (secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_2;
            } else {
                return R.layout.schedule_single_type_5;
            }
        }
        if (secondSubgroup_bothWeek) {
            // layout 2,6
            if (firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_2;
            } else {
                return R.layout.schedule_single_type_6;
            }
        }
        //строка 1*2
        if (bothSubgroup_firstWeek) {
            // layout 4,7
            if (bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_4;
            } else {
                return R.layout.schedule_single_type_7;
            }
        }
        if (bothSubgroup_secondWeek) {
            // layout 4,8
            if (bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_4;
            } else {
                return R.layout.schedule_single_type_8;
            }
        }

        if (firstSubgroup_firstWeek) {
            // layout 3,6,8
            if (firstSubgroup_secondWeek || secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_firstWeek || bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_8;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        if (firstSubgroup_secondWeek) {
            // layout 3,6,7
            if (firstSubgroup_firstWeek || secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_secondWeek || bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_7;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }

        if (secondSubgroup_firstWeek) {
            // layout 3,5,8
            if (secondSubgroup_secondWeek || firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_firstWeek || bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_8;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        if (secondSubgroup_secondWeek) {
            // layout 3,5,7
            if (secondSubgroup_firstWeek || firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_secondWeek || bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_7;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        return R.layout.schedule_single_type_9;
    }
}

