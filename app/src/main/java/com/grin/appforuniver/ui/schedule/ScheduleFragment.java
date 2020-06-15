package com.grin.appforuniver.ui.schedule;

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
import com.grin.appforuniver.adapters.ScheduleGroupAdapter;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.schedule.Subject;
import com.grin.appforuniver.data.model.schedule.TypeClasses;
import com.grin.appforuniver.data.service.ProfessorService;
import com.grin.appforuniver.data.service.ScheduleService;
import com.grin.appforuniver.data.tools.ScheduleParser;
import com.grin.appforuniver.dialogs.ScheduleFilterDialog;
import com.grin.appforuniver.dialogs.SearchableDialog;
import com.grin.appforuniver.fragments.schedule.ScheduleFiltrationManager;
import com.grin.appforuniver.fragments.schedule.ScheduleStandardTypeModel;
import com.grin.appforuniver.utils.StickHeaderItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleFragment extends Fragment implements ScheduleFilterDialog.OnSelectListener {
    private static final String TAG = "ScheduleFragment";
    private View mView;
    private ProfessorService mProfessorService;
    private RecyclerView recyclerViewSchedule;

    private RecyclerView recyclerViewFiltration;
    private ScheduleFiltrationManager scheduleFiltrationManager;

    private ChipFilterAdapter chipFilterAdapter;
    private ScheduleGroupAdapter scheduleAdapter;
    private StickHeaderItemDecoration stickHeaderItemDecoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mProfessorService = ProfessorService.getService();
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerViewSchedule = mView.findViewById(R.id.recyclerView);
        recyclerViewFiltration = mView.findViewById(R.id.recyclerViewFiltration);
        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFiltration.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        chipFilterAdapter = new ChipFilterAdapter(listFilterItems -> {
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

    private void dialogFiltrationSchedule() {
        ScheduleFilterDialog scheduleFilterDialog = new ScheduleFilterDialog();
        scheduleFilterDialog.setOnSelectListener(this);
        scheduleFilterDialog.show(getChildFragmentManager(), "filtration_dialog");
    }

    private ScheduleService.OnRequestScheduleListener mListenerSchedule = new ScheduleService.OnRequestScheduleListener() {
        @Override
        public void onRequestScheduleSuccess(Call<List<Classes>> call, Response<List<Classes>> response) {
            if (response.body() != null) {
                List<ScheduleStandardTypeModel> schedulePairs = new ScheduleParser(response.body(), scheduleFiltrationManager.isProfessorsSchedule())
                        .parse()
                        .getParsedSchedule();
                scheduleAdapter = new ScheduleGroupAdapter();
                scheduleAdapter.setClasses(schedulePairs);
                stickHeaderItemDecoration = new StickHeaderItemDecoration(scheduleAdapter);
                recyclerViewSchedule.setAdapter(scheduleAdapter);
                recyclerViewSchedule.addItemDecoration(stickHeaderItemDecoration);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.schedule_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                dialogFiltrationSchedule();
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