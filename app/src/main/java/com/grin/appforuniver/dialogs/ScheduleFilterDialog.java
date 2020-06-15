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
import com.grin.appforuniver.databinding.ScheduleFiltrationDialogBinding;

import es.dmoral.toasty.Toasty;

import static com.grin.appforuniver.utils.Constants.Place;
import static com.grin.appforuniver.utils.Constants.Week;

public class ScheduleFilterDialog extends DialogFragment {

    private ScheduleFilterDialogViewModel viewModel;
    private ScheduleFiltrationDialogBinding binding;
    private ArrayAdapter<Professors> arrayProfessorsAdapter;
    private ArrayAdapter<Rooms> arrayRoomsAdapter;
    private ArrayAdapter<Groups> arrayGroupsAdapter;
    Professors selectedProfessors = null;
    Rooms selectedRooms = null;
    Groups selectedGroups = null;

    private OnSelectListener onFilterParameter;

    public ScheduleFilterDialog() {
    }

    public void setOnSelectListener(OnSelectListener onFilterParameter) {
        this.onFilterParameter = onFilterParameter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View rootView = requireActivity().getLayoutInflater().inflate(R.layout.schedule_filtration_dialog, null);
        binding = ScheduleFiltrationDialogBinding.bind(rootView);
        viewModel = ViewModelProviders.of(this).get(ScheduleFilterDialogViewModel.class);
        viewModel.getProfessorsLiveData().observe(requireActivity(), professors -> {
            arrayProfessorsAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    professors);
            binding.spinnerProfessorEt.setAdapter(arrayProfessorsAdapter);
        });
        viewModel.getRoomsLiveData().observe(requireActivity(), rooms -> {
            arrayRoomsAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    rooms);
            binding.spinnerRoomEt.setAdapter(arrayRoomsAdapter);
        });
        viewModel.getGroupsLiveData().observe(requireActivity(), groups -> {
            arrayGroupsAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    groups);
            binding.spinnerGroupEt.setAdapter(arrayGroupsAdapter);
        });
        viewModel.getErrorMessageLiveData().observe(requireActivity(), errorMessage ->
                Toasty.error(requireContext(), errorMessage, Toast.LENGTH_SHORT, true).show());
        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
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

        initializeAutoCompleteSpinners(binding.spinnerProfessorTil, binding.spinnerProfessorEt, getString(R.string.select_professor));
        initializeAutoCompleteSpinners(binding.spinnerRoomTil, binding.spinnerRoomEt, getString(R.string.select_room));
        initializeAutoCompleteSpinners(binding.spinnerGroupTil, binding.spinnerGroupEt, getString(R.string.select_group));

        binding.spinnerProfessorEt.setOnItemClickListener((adapterView, view, i, l) -> selectedProfessors = arrayProfessorsAdapter.getItem(i));
        binding.spinnerRoomEt.setOnItemClickListener((adapterView, view, i, l) -> selectedRooms = arrayRoomsAdapter.getItem(i));
        binding.spinnerGroupEt.setOnItemClickListener((adapterView, view, i, l) -> selectedGroups = arrayGroupsAdapter.getItem(i));

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
        binding = null;
        super.onDestroyView();
    }

    public interface OnSelectListener {
        void onSelectedParameter(Subject subject, TypeClasses type, Professors professor, Rooms room, Groups group, Place place, Week week);
    }
}