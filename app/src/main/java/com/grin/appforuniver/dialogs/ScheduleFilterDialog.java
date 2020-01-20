package com.grin.appforuniver.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.schedule.Subject;
import com.grin.appforuniver.data.model.schedule.TypeClasses;
import com.grin.appforuniver.data.service.GroupService;
import com.grin.appforuniver.data.service.ProfessorService;
import com.grin.appforuniver.data.service.RoomService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleFilterDialog extends DialogFragment {
    private GroupService mGroupService;
    private ProfessorService mProfessorService;
    private RoomService mRoomService;
    private Context mContext;
    private Unbinder mUnbinder;

    @BindView(R.id.dialog_filtration_schedule_spinner_professor_til)
    TextInputLayout professorTIL;
    @BindView(R.id.dialog_filtration_schedule_spinner_professor_et)
    AutoCompleteTextView professorField;

    @BindView(R.id.dialog_filtration_schedule_spinner_room_til)
    TextInputLayout roomTIL;
    @BindView(R.id.dialog_filtration_schedule_spinner_room_et)
    AutoCompleteTextView roomField;

    @BindView(R.id.dialog_filtration_schedule_spinner_group_til)
    TextInputLayout groupTIL;
    @BindView(R.id.dialog_filtration_schedule_spinner_group_et)
    AutoCompleteTextView groupField;
    private ArrayList<Professors> mArrayProfessors = new ArrayList<>();
    private ArrayList<Rooms> mArrayRooms = new ArrayList<>();
    private ArrayList<Groups> mArrayGroups = new ArrayList<>();
    Professors selectedProfessors = null;
    Rooms selectedRooms = null;
    Groups selectedGroups = null;

    private OnSelectListener onFilterParameter;

    public ScheduleFilterDialog(Context mContext) {
        this.mContext = mContext;
        mGroupService = GroupService.getService();
        mProfessorService = ProfessorService.getService();
        mRoomService = RoomService.getService();
    }

    public void setOnSelectListener(OnSelectListener onFilterParameter) {
        this.onFilterParameter = onFilterParameter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.schedule_filtration_dialog, null);
        mUnbinder = ButterKnife.bind(this, rootView);
        mProfessorService.requestAllProfessors(new ProfessorService.OnRequestProfessorListListener() {
            @Override
            public void onRequestProfessorListSuccess(Call<List<Professors>> call, Response<List<Professors>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mArrayProfessors.clear();
                        mArrayProfessors.addAll(response.body());
                        Collections.sort(mArrayProfessors, (professors, t1) -> String.CASE_INSENSITIVE_ORDER.compare(professors.getUser().getShortFIO(), t1.getUser().getShortFIO()));
                    }
                }
            }

            @Override
            public void onRequestProfessorListFailed(Call<List<Professors>> call, Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
        mRoomService.requestAllRooms(new RoomService.OnRequestRoomListListener() {
            @Override
            public void onRequestRoomListSuccess(Call<List<Rooms>> call, Response<List<Rooms>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mArrayRooms.clear();
                        mArrayRooms.addAll(response.body());
                        Collections.sort(mArrayRooms, (rooms, t1) -> String.CASE_INSENSITIVE_ORDER.compare(rooms.getName(), t1.getName()));
                    }
                }
            }

            @Override
            public void onRequestRoomListFailed(Call<List<Rooms>> call, Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
        mGroupService.requestAllGroups(new GroupService.OnRequestGroupListListener() {
            @Override
            public void onRequestGroupListSuccess(Call<List<Groups>> call, Response<List<Groups>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mArrayGroups.clear();
                        mArrayGroups.addAll(response.body());
                        Collections.sort(mArrayGroups, (groups, t1) -> String.CASE_INSENSITIVE_ORDER.compare(groups.getmName(), t1.getmName()));
                    }
                }
            }

            @Override
            public void onRequestGroupListFailed(Call<List<Groups>> call, Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(rootView);
        builder.setTitle(R.string.filter);
        builder.setPositiveButton(R.string.filtration, (dialogInterface, i) -> {
            if (onFilterParameter != null) {
                onFilterParameter.onSelectedParameter(null, null, selectedProfessors, selectedRooms, selectedGroups, null, null);
            }
        });
        builder.setNegativeButton(R.string.clear, (dialogInterface, i) -> {
            if (onFilterParameter != null) {
                onFilterParameter.onSelectedParameter(null, null, null, null, null, null, null);
            }
        });


        ArrayAdapter<Professors> arrayProfessorsAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                mArrayProfessors);
        ArrayAdapter<Rooms> arrayRoomsAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                mArrayRooms);
        ArrayAdapter<Groups> arrayGroupsAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                mArrayGroups);

        professorField.setAdapter(arrayProfessorsAdapter);
        roomField.setAdapter(arrayRoomsAdapter);
        groupField.setAdapter(arrayGroupsAdapter);

        initializeAutoCompleteSpinners(professorTIL, professorField, getString(R.string.select_professor));
        initializeAutoCompleteSpinners(roomTIL, roomField, getString(R.string.select_room));
        initializeAutoCompleteSpinners(groupTIL, groupField, getString(R.string.select_group));

        professorField.setOnItemClickListener((adapterView, view, i, l) -> selectedProfessors = arrayProfessorsAdapter.getItem(i));
        roomField.setOnItemClickListener((adapterView, view, i, l) -> selectedRooms = arrayRoomsAdapter.getItem(i));
        groupField.setOnItemClickListener((adapterView, view, i, l) -> selectedGroups = arrayGroupsAdapter.getItem(i));

        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    private void initializeAutoCompleteSpinners(TextInputLayout textInputLayout, AutoCompleteTextView autoCompleteTextView, String errorText) {
        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) autoCompleteTextView.showDropDown();
        });
        autoCompleteTextView.setOnTouchListener((v, event) -> {
            autoCompleteTextView.showDropDown();
            return false;
        });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    textInputLayout.setError(errorText);
                } else {
                    textInputLayout.setError(null);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public interface OnSelectListener {
        void onSelectedParameter(Subject subject, TypeClasses type, Professors professor, Rooms room, Groups group, Place place, Week week);
    }
}