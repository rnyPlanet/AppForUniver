package com.grin.appforuniver.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.adapters.ChipFilterAdapter;
import com.grin.appforuniver.adapters.ProfessorScheduleAdapter;
import com.grin.appforuniver.adapters.ScheduleGroupAdapter;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.schedule.Subject;
import com.grin.appforuniver.data.model.schedule.TypeClasses;
import com.grin.appforuniver.data.service.ProfessorService;
import com.grin.appforuniver.data.service.ScheduleService;
import com.grin.appforuniver.dialogs.ScheduleFilterDialog;
import com.grin.appforuniver.dialogs.SearchableDialog;
import com.grin.appforuniver.fragments.schedule.ScheduleFiltrationManager;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Subgroup;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleFragment extends Fragment implements ScheduleFilterDialog.OnSelectListener {
    private static final String TAG = "ScheduleFragment";
    private View mView;
    private ProfessorService mProfessorService;
    private RecyclerView recyclerViewSchedule;
    private ScheduleGroupAdapter scheduleAdapter;
    private RecyclerView recyclerViewFiltration;
    private ScheduleFiltrationManager scheduleFiltrationManager;

    private ChipFilterAdapter chipFilterAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mProfessorService = ProfessorService.getService();
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        getActivity().setTitle(R.string.menu_schedule);
        recyclerViewSchedule = mView.findViewById(R.id.recyclerView);
        recyclerViewFiltration = mView.findViewById(R.id.recyclerViewFiltration);
        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFiltration.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        chipFilterAdapter = new ChipFilterAdapter(getContext(), listFilterItems -> {
            ScheduleFiltrationManager.Builder scheduleFilters = new ScheduleFiltrationManager.Builder(mListenerSchedule);
            for (Object object : listFilterItems) {
                if (object instanceof Subject)
                    scheduleFilters.filtrationBySubject((Subject) object);
                if (object instanceof TypeClasses)
                    scheduleFilters.filtrationByType((TypeClasses) object);
                if (object instanceof Professors)
                    scheduleFilters.filtrationByProfessor((Professors) object);
                if (object instanceof Rooms)
                    scheduleFilters.filtrationByRoom((Rooms) object);
                if (object instanceof Groups)
                    scheduleFilters.filtrationByGroup((Groups) object);
                if (object instanceof Place)
                    scheduleFilters.filtrationByPlace((Place) object);
                if (object instanceof Week)
                    scheduleFilters.filtrationByWeek((Week) object);
            }
            scheduleFiltrationManager = scheduleFilters.build();
            scheduleFiltrationManager.getSchedule();
            chipFilterAdapter.setItemsFilter(scheduleFiltrationManager.getFilterItems());
        });
        recyclerViewFiltration.setAdapter(chipFilterAdapter);
        scheduleAdapter = new ScheduleGroupAdapter(getContext());

        scheduleFiltrationManager = new ScheduleFiltrationManager.Builder(mListenerSchedule).build();
        scheduleFiltrationManager.getSchedule();
        chipFilterAdapter.setItemsFilter(scheduleFiltrationManager.getFilterItems());
        return mView;
    }

    private void dialogSearchProfessor(Context context) {
        mProfessorService.requestAllProfessors(new ProfessorService.OnRequestProfessorListListener() {
            @Override
            public void onRequestProfessorListSuccess(Call<List<Professors>> call, Response<List<Professors>> response) {
                if (response.body() != null) {
                    List<Professors> listProfessors = new ArrayList<>(response.body());
                    Log.d(TAG, "onResponseGroups: " + listProfessors);
                    Collections.sort(listProfessors, (professors, t1) -> String.CASE_INSENSITIVE_ORDER.compare(professors.getUser().getShortFIO(), t1.getUser().getShortFIO()));

                    SearchableDialog dialogFragment = new SearchableDialog<>(context, listProfessors);
                    SearchableDialog.OnFiltration<Professors> filter = (filter1, sourceList) -> {
                        List<Professors> filteredList = new ArrayList<>();
                        for (Professors item : sourceList) {
                            if (item.toString().toLowerCase().contains(filter1.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                        return filteredList;
                    };
                    SearchableDialog.OnBindItem<Professors> binder = item -> item.getUser().getShortFIO();


                    dialogFragment.setOnSelectListener(callback);
                    dialogFragment.setOnFiltration(filter);
                    dialogFragment.setOnBindItem(binder);
                    dialogFragment.show(getChildFragmentManager(), "select_group");
                }
            }

            @Override
            public void onRequestProfessorListFailed(Call<List<Professors>> call, Throwable t) {

            }
        });
    }

    private void dialogFiltrationSchedule(Context context) {
        ScheduleFilterDialog scheduleFilterDialog = new ScheduleFilterDialog(context);
        scheduleFilterDialog.setOnSelectListener(this);
        scheduleFilterDialog.show(getChildFragmentManager(), "filtration_dialog");
    }

    private ScheduleService.OnRequestScheduleListener mListenerSchedule = new ScheduleService.OnRequestScheduleListener() {
        @Override
        public void onRequestScheduleSuccess(Call<List<Classes>> call, Response<List<Classes>> response) {
            if (response.body() != null) {
                List<Classes> listClasses = new ArrayList<>(response.body());
                Log.d(TAG, "onResponse: " + listClasses);
                List<ScheduleStandardTypeModel> schedulePairs = new ArrayList<>();
                for (Place place : Place.values()) {
                    if (place == Place.POOL) continue;
                    schedulePairs.add(new ScheduleStandardTypeModel(R.layout.schedule_day_separator,
                            place, -1, null));
                    boolean isWeekendDay = false;
                    for (int i = 1; i <= 6; i++) {
                        List<Classes> classesInsidePairs = new ArrayList<>();
                        for (Classes classes : listClasses) {
                            if (classes.getPlace() == place && classes.getPositionInDay().getId() == i) {
                                if (scheduleFiltrationManager.isProfessorsSchedule()) {
                                    //Требуется для корректного отображения предметов преподавателя
                                    classes.setSubgroup(Subgroup.BOTH);
                                }
                                classesInsidePairs.add(classes);
                            }
                        }
                        if (classesInsidePairs.size() > 0) {
                            isWeekendDay = true;
                            int typeView;
                            if (scheduleFiltrationManager.isProfessorsSchedule()) {
                                typeView = setDataInLayoutProfessors(classesInsidePairs);
                            } else {
                                typeView = setDataInLayout(classesInsidePairs);
                            }
                            schedulePairs.add(new ScheduleStandardTypeModel(typeView,
                                    place, i, classesInsidePairs));
                        }
                    }
                    if (!isWeekendDay) {
                        schedulePairs.add(new ScheduleStandardTypeModel(R.layout.schedule_weekend_day,
                                place, -1, null));
                    }
                }
                if (scheduleFiltrationManager.isProfessorsSchedule()) {
                    ProfessorScheduleAdapter adapter = new ProfessorScheduleAdapter(getContext());
                    adapter.setClasses(schedulePairs);
                    recyclerViewSchedule.setAdapter(adapter);
                } else {
                    scheduleAdapter.setClasses(schedulePairs);
                    recyclerViewSchedule.setAdapter(scheduleAdapter);
                }
            }
        }

        @Override
        public void onRequestScheduleFailed(Call<List<Classes>> call, Throwable t) {
            Log.d(TAG, "displayedSchedule: " + t.getMessage());
        }
    };

    private SearchableDialog.OnSelectListener<Professors> callback = selectedItem -> {
        if (selectedItem != null) {
            scheduleFiltrationManager = new ScheduleFiltrationManager.Builder(mListenerSchedule)
                    .filtrationByProfessor(selectedItem)
                    .build();
        } else {
            scheduleFiltrationManager = new ScheduleFiltrationManager.Builder(mListenerSchedule)
                    .build();
        }
        scheduleFiltrationManager.getSchedule();
        chipFilterAdapter.setItemsFilter(scheduleFiltrationManager.getFilterItems());
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
            if (classes.getSubgroup() == Subgroup.FIRST && classes.getWeek() == Week.FIRST) {
                firstSubgroup_firstWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.FIRST && classes.getWeek() == Week.SECOND) {
                firstSubgroup_secondWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.FIRST && classes.getWeek() == Week.BOTH) {
                firstSubgroup_bothWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.SECOND && classes.getWeek() == Week.FIRST) {
                secondSubgroup_firstWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.SECOND && classes.getWeek() == Week.SECOND) {
                secondSubgroup_secondWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.SECOND && classes.getWeek() == Week.BOTH) {
                secondSubgroup_bothWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.BOTH && classes.getWeek() == Week.FIRST) {
                bothSubgroup_firstWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.BOTH && classes.getWeek() == Week.SECOND) {
                bothSubgroup_secondWeek = true;
            }
            if (classes.getSubgroup() == Subgroup.BOTH && classes.getWeek() == Week.BOTH) {
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
            if (classes.getWeek() == Week.FIRST || classes.getWeek() == Week.SECOND) {
                bothSubgroup_firstWeek = true;
                bothSubgroup_secondWeek = true;
            }
            if (classes.getWeek() == Week.BOTH) {
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
            if (firstSubgroup_secondWeek & secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_firstWeek & bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_8;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        if (firstSubgroup_secondWeek) {
            // layout 3,6,7
            if (firstSubgroup_firstWeek & secondSubgroup_bothWeek) {
                return R.layout.schedule_single_type_6;
            } else if (secondSubgroup_secondWeek & bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_7;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }

        if (secondSubgroup_firstWeek) {
            // layout 3,5,8
            if (secondSubgroup_secondWeek & firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_firstWeek & bothSubgroup_secondWeek) {
                return R.layout.schedule_single_type_8;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        if (secondSubgroup_secondWeek) {
            // layout 3,5,7
            if (secondSubgroup_firstWeek & firstSubgroup_bothWeek) {
                return R.layout.schedule_single_type_5;
            } else if (firstSubgroup_secondWeek & bothSubgroup_firstWeek) {
                return R.layout.schedule_single_type_7;
            } else {
                return R.layout.schedule_single_type_3;
            }
        }
        return R.layout.schedule_single_type_9;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.schedule_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                dialogFiltrationSchedule(getContext());
                return true;
            case R.id.action_search_professor:
                dialogSearchProfessor(getContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelectedParameter(Subject subject, TypeClasses type, Professors professor, Rooms room, Groups group, Place place, Week week) {
        scheduleFiltrationManager = new ScheduleFiltrationManager.Builder(mListenerSchedule)
                .filtrationBySubject(subject)
                .filtrationByType(type)
                .filtrationByProfessor(professor)
                .filtrationByRoom(room)
                .filtrationByGroup(group)
                .filtrationByPlace(place)
                .filtrationByWeek(week)
                .build();
        scheduleFiltrationManager.getSchedule();
        chipFilterAdapter.setItemsFilter(scheduleFiltrationManager.getFilterItems());
    }
}

