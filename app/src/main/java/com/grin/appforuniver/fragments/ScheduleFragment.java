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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ProfessorInterface;
import com.grin.appforuniver.data.WebServices.ScheduleInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.utils.Constants;
import com.grin.appforuniver.fragments.dialogs.SearchableDialog;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;
import com.grin.appforuniver.fragments.schedule.adapters.ProfessorScheduleAdapter;
import com.grin.appforuniver.fragments.schedule.adapters.ScheduleGroupAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    private View mView;
    private RecyclerView recyclerView;
    private ConstraintLayout searchProf;
    private ScheduleGroupAdapter adapter;
    private TextView labelSearchedProfessor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        getActivity().setTitle(R.string.menu_schedule);
        recyclerView = mView.findViewById(R.id.recyclerView);
        searchProf = mView.findViewById(R.id.search_teacher);
        labelSearchedProfessor = mView.findViewById(R.id.searched_teacher);
        searchProf.setOnClickListener(view -> dialogSearchProfessor(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScheduleGroupAdapter(getContext());
        getScheduleGroup();
        return mView;
    }

    private void getScheduleGroup() {
        ScheduleInterface scheduleInterface = ServiceGenerator.createService(ScheduleInterface.class);
        Call<List<Classes>> list = scheduleInterface.getScheduleAll();
        list.enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(@NonNull Call<List<Classes>> call, @NonNull Response<List<Classes>> response) {
                if (response.body() != null) {
                    List<Classes> listClasses = new ArrayList<>(response.body());
                    List<ScheduleStandardTypeModel> schedulePairs = new ArrayList<>();
                    for (Constants.Place place : Constants.Place.values()) {
                        if (place == Constants.Place.POOL) continue;
                        schedulePairs.add(new ScheduleStandardTypeModel(R.layout.item_day_separator,
                                place, -1, null));
                        boolean isWeekendDay = false;
                        for (int i = 0; i <= 6; i++) {
                            List<Classes> classesInsidePairs = new ArrayList<>();
                            for (Classes classes : listClasses) {
                                if (classes.getPlace() == place && classes.getIndexInDay() == i) {
                                    classesInsidePairs.add(classes);
                                }
                            }
                            if (classesInsidePairs.size() > 0) {
                                isWeekendDay = true;
                                schedulePairs.add(new ScheduleStandardTypeModel(setDataInLayout(classesInsidePairs),
                                        place, i + 1, classesInsidePairs));
                            }
                        }
                        if (!isWeekendDay) {
                            schedulePairs.add(new ScheduleStandardTypeModel(R.layout.schedule_weekend_day,
                                    place, -1, null));
                        }
                    }

                    adapter.setClasses(schedulePairs);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Classes>> call, @NonNull Throwable t) {

            }
        });

    }

    private void dialogSearchProfessor(Context context) {
        ProfessorInterface scheduleInterface = ServiceGenerator.createService(ProfessorInterface.class);
        Call<List<Professors>> list = scheduleInterface.getProfessors();
        list.enqueue(new Callback<List<Professors>>() {
            @Override
            public void onResponse(@NonNull Call<List<Professors>> call, @NonNull Response<List<Professors>> response) {
                if (response.body() != null) {
                    List<Professors> listProfessors = new ArrayList<>(response.body());
                    Log.d(TAG, "onResponseGroups: " + listProfessors);

                    SearchableDialog dialogFragment = new SearchableDialog<>(context, listProfessors);
                    SearchableDialog.OnFiltration filter = (SearchableDialog.OnFiltration<Professors>) (filter1, sourceList) -> {
                        List<Professors> filteredList = new ArrayList<>();
                        for (Professors item : sourceList) {
                            if (item.toString().toLowerCase().contains(filter1.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                        return filteredList;
                    };

                    dialogFragment.setOnSelectListener(callback);
                    dialogFragment.setOnFiltration(filter);
                    dialogFragment.show(getChildFragmentManager(), "select_group");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Professors>> call, @NonNull Throwable t) {

            }
        });
    }

    private void getScheduleProfessor(Professors professors) {
        ProfessorInterface professorInterface = ServiceGenerator.createService(ProfessorInterface.class);
        Call<List<Classes>> list = professorInterface.getProfessorSchedule(professors.getmId());
        list.enqueue(new Callback<List<Classes>>() {

            @Override
            public void onResponse(@NonNull Call<List<Classes>> call, @NonNull Response<List<Classes>> response) {
                if (response.body() != null) {
                    List<Classes> listProfessorsClasses = new ArrayList<>(response.body());
                    List<ScheduleStandardTypeModel> schedulePairs = new ArrayList<>();
                    for (Constants.Place place : Constants.Place.values()) {
                        if (place == Constants.Place.POOL) continue;
                        schedulePairs.add(new ScheduleStandardTypeModel(R.layout.item_day_separator,
                                place, -1, null));
                        boolean isWeekendDay = false;
                        for (int i = 0; i <= 6; i++) {
                            List<Classes> classesInsidePairs = new ArrayList<>();
                            for (Classes classes : listProfessorsClasses) {
                                if (classes.getPlace() == place && classes.getIndexInDay() == i) {
                                    classesInsidePairs.add(classes);
                                    Log.d(TAG, "onResponse: " + classes);
                                }
                            }

                            if (classesInsidePairs.size() > 0) {
                                isWeekendDay = true;
                                schedulePairs.add(new ScheduleStandardTypeModel(setDataInLayoutProfessors(classesInsidePairs),
                                        place, i + 1, classesInsidePairs));
                            }
                        }
                        if (!isWeekendDay) {
                            schedulePairs.add(new ScheduleStandardTypeModel(R.layout.schedule_weekend_day,
                                    place, -1, null));
                        }
                    }
                    for (ScheduleStandardTypeModel model : schedulePairs) {
                        Log.d(TAG, "onResponse: " + model);
                    }
                    ProfessorScheduleAdapter adapter = new ProfessorScheduleAdapter(getContext());
                    adapter.setClasses(schedulePairs);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Classes>> call, @NonNull Throwable t) {

            }
        });
    }

    private SearchableDialog.OnSelectListener callback = new SearchableDialog.OnSelectListener<Professors>() {
        @Override
        public void onSelected(Professors selectedItem) {
            if (selectedItem != null) {
                labelSearchedProfessor.setText(selectedItem.toString());
                getScheduleProfessor(selectedItem);
            } else {
                labelSearchedProfessor.setText(null);
                getScheduleGroup();
            }
        }
    };

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

    private int setDataInLayoutProfessors(List<Classes> mListClasses) {
        firstSubgroup_firstWeek = false;
        firstSubgroup_secondWeek = false;
        firstSubgroup_bothWeek = false;
        secondSubgroup_firstWeek = false;
        secondSubgroup_secondWeek = false;
        secondSubgroup_bothWeek = false;
        //
        bothSubgroup_firstWeek = false;
        bothSubgroup_secondWeek = false;
        bothSubgroup_bothWeek = false;
        for (Classes classes : mListClasses) {
            if (classes.getWeek() == Constants.Week.FIRST || classes.getWeek() == Constants.Week.SECOND) {
                bothSubgroup_firstWeek = true;
                bothSubgroup_secondWeek = true;
            }
            if (classes.getWeek() == Constants.Week.BOTH) {
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

