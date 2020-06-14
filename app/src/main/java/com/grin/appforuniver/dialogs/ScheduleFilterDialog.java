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
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.schedule.Subject;
import com.grin.appforuniver.data.model.schedule.TypeClasses;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleFilterDialog extends DialogFragment {

    private Context mContext;
    private Unbinder mUnbinder;

    private ScheduleFilterDialogViewModel viewModel;
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
    private ArrayAdapter<Professors> arrayProfessorsAdapter;
    private ArrayAdapter<Rooms> arrayRoomsAdapter;
    private ArrayAdapter<Groups> arrayGroupsAdapter;
    Professors selectedProfessors = null;
    Rooms selectedRooms = null;
    Groups selectedGroups = null;

    private OnSelectListener onFilterParameter;

    public ScheduleFilterDialog(Context mContext) {
        this.mContext = mContext;
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
        viewModel = ViewModelProviders.of(this).get(ScheduleFilterDialogViewModel.class);
        viewModel.getProfessorsLiveData().observe(requireActivity(), professors -> {
            arrayProfessorsAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    professors);
            professorField.setAdapter(arrayProfessorsAdapter);
        });
        viewModel.getRoomsLiveData().observe(requireActivity(), rooms -> {
            arrayRoomsAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    rooms);
            roomField.setAdapter(arrayRoomsAdapter);
        });
        viewModel.getGroupsLiveData().observe(requireActivity(), groups -> {
            arrayGroupsAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    groups);
            groupField.setAdapter(arrayGroupsAdapter);
        });
        viewModel.getErrorMessageLiveData().observe(requireActivity(), errorMessage ->
                Toasty.error(requireContext(), errorMessage, Toast.LENGTH_SHORT, true).show());
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

        initializeAutoCompleteSpinners(professorTIL, professorField, getString(R.string.select_professor));
        initializeAutoCompleteSpinners(roomTIL, roomField, getString(R.string.select_room));
        initializeAutoCompleteSpinners(groupTIL, groupField, getString(R.string.select_group));

        professorField.setOnItemClickListener((adapterView, view, i, l) -> selectedProfessors = arrayProfessorsAdapter.getItem(i));
        roomField.setOnItemClickListener((adapterView, view, i, l) -> selectedRooms = arrayRoomsAdapter.getItem(i));
        groupField.setOnItemClickListener((adapterView, view, i, l) -> selectedGroups = arrayGroupsAdapter.getItem(i));

        return builder.create();
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