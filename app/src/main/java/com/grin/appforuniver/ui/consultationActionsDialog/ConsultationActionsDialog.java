package com.grin.appforuniver.ui.consultationActionsDialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.models.Rooms;
import com.grin.appforuniver.databinding.DialogConsultationCreateBinding;
import com.grin.appforuniver.ui.detailConsultation.DetailConsultationActivity;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class ConsultationActionsDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "ConsultationActionsDialog";

    private DialogConsultationCreateBinding binding;
    private ConsultationActionsDialogViewModel viewModel;

    private AlertDialog dialog;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private int daySelected, monthSelected, yearSelected, hourSelected, minuteSelected;

    private ArrayAdapter<Rooms> arrayAdapter;

    private OnCreate onCreate;
    private OnUpdate onUpdate;

    public ConsultationActionsDialog(OnCreate onCreate) {
        this.onCreate = onCreate;
    }

    public ConsultationActionsDialog(OnUpdate onUpdate) {
        this.onUpdate = onUpdate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View rootView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_consultation_create, null);
        binding = DialogConsultationCreateBinding.bind(rootView);
        viewModel = ViewModelProviders.of(this).get(ConsultationActionsDialogViewModel.class);
        viewModel.getRooms().observe(requireActivity(), rooms -> {
            arrayAdapter = new ArrayAdapter<>(requireActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    rooms);
            binding.roomsSpinnerEt.setAdapter(arrayAdapter);

        });
        viewModel.getSelectedRoom().observe(requireActivity(), room -> binding.roomsSpinnerEt.setText(room.getName(), false));
        viewModel.getSelectedDateAndTime().observe(requireActivity(), s -> binding.selectDateTimeEt.setText(s));
        viewModel.getDescription().observe(requireActivity(), s -> binding.descriptionEt.setText(s));
        viewModel.getErrorMessage().observe(requireActivity(), errorMessage ->
                Toasty.error(requireContext(), errorMessage, Toast.LENGTH_SHORT, true).show());

        viewModel.getErrorMessageRoom().observe(requireActivity(), s -> binding.roomSpinnerTil.setError(s));
        viewModel.getErrorMessageDateAndTime().observe(requireActivity(), s -> binding.selectDateTimeTil.setError(s));

        Bundle mBundleArguments = getArguments();
        String titleDialog = null;
        String titlePositiveButton = null;
        if (mBundleArguments != null) {
            int mIdConsultation = mBundleArguments.getInt(DetailConsultationActivity.KEY, -1);
            if (mIdConsultation != -1) {
                viewModel.setIdConsultation(mIdConsultation);
                viewModel.requestConsultation(mIdConsultation);
                titleDialog = getString(R.string.update_consultation);
                titlePositiveButton = getString(R.string.update);
                viewModel.isUpdated().observe(requireActivity(), aBoolean -> {
                    if (onUpdate != null) onUpdate.onUpdated(mIdConsultation);
                    dialog.dismiss();
                });
            }
        } else {
            titleDialog = getString(R.string.сreate_сonsultation);
            titlePositiveButton = getString(R.string.create);
            viewModel.isCreated().observe(requireActivity(), aBoolean -> {
                if (onCreate != null) onCreate.onCreated();
                dialog.dismiss();
            });
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titleDialog);
        builder.setView(rootView);
        builder.setPositiveButton(titlePositiveButton, (dialogInterface, i) -> {
            viewModel.setDescription(binding.descriptionEt.getText().toString());
            viewModel.submitConsultation();
        });
        builder.setNegativeButton(R.string.cancel, null);


        binding.roomsSpinnerEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) binding.roomsSpinnerEt.showDropDown();
        });
        binding.roomsSpinnerEt.setOnTouchListener((v, event) -> {
            binding.roomsSpinnerEt.showDropDown();
            return false;
        });

        binding.roomsSpinnerEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    binding.roomSpinnerTil.setError(getString(R.string.select_room));
                } else {
                    binding.roomSpinnerTil.setError(null);
                }
            }
        });
        binding.roomsSpinnerEt.setOnItemClickListener((adapterView, view, i, l) -> viewModel.setSelectedRoom(arrayAdapter.getItem(i)));

        binding.selectDateTimeEt.setOnClickListener(view -> selectDateTime());

        dialog = builder.create();
        return dialog;
    }


    void selectDateTime() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(requireContext(), ConsultationActionsDialog.this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearSelected = year;
        monthSelected = month + 1;
        daySelected = dayOfMonth;

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(requireContext(), ConsultationActionsDialog.this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (!isValidTime(hourOfDay, minute)) {
            Toasty.info(requireContext(), getString(R.string.choose_correct_time), Toast.LENGTH_SHORT, true).show();
            Calendar c = Calendar.getInstance();
            int currentHour = c.get(Calendar.HOUR_OF_DAY);
            int currentMinute = c.get(Calendar.MINUTE);
            timePickerDialog = new TimePickerDialog(getContext(),
                    ConsultationActionsDialog.this, currentHour, currentMinute, true);
            timePickerDialog.show();
        } else {
            hourSelected = hourOfDay;
            minuteSelected = minute;

            binding.selectDateTimeTil.setError(null);
            viewModel.setSelectedDateAndTime(
                    returnStringOfDate(yearSelected, monthSelected, daySelected, hourSelected, minuteSelected)
            );
        }
    }

    private boolean isValidTime(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);
        if (hourOfDay < currentHour) {
            return false;
        } else {
            return !(hourOfDay == currentHour & minute < currentMinute);
        }

    }

    private String returnStringOfDate(int year, int month, int dayOfMonth, int hour,
                                      int minute) {
        return validateValuesForStringDate(dayOfMonth) + "." +
                validateValuesForStringDate(month) + "." +
                validateValuesForStringDate(year) + " " +
                validateValuesForStringDate(hour) + ":" +
                validateValuesForStringDate(minute);
    }

    private String validateValuesForStringDate(int value) {
        return (value < 10) ? "0" + value : String.valueOf(value);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }


    public interface OnCreate {
        void onCreated();
    }

    public interface OnUpdate {
        void onUpdated(int idConsultation);
    }
}