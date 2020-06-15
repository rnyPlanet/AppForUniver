package com.grin.appforuniver.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

import com.grin.appforuniver.R;
import com.grin.appforuniver.activities.ConsultationActivity;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.service.ConsultationService;
import com.grin.appforuniver.data.service.RoomService;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.databinding.DialogConsultationCreateBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

public class ConsultationActionsDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, ConsultationService.OnRequestConsultationListener, RoomService.OnRequestRoomListListener {
    private static final String TAG = "ConsultationActionsDialog";
    private ConsultationService mConsultationService;
    private RoomService mRoomService;
    private Bundle mBundleArguments;

    private DialogConsultationCreateBinding binding;


    private AlertDialog dialog;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private int daySelected, monthSelected, yearSelected, hourSelected, minuteSelected;

    private ArrayList<Rooms> mArrayRooms = new ArrayList<>();

    private boolean isDateAndTimeChoosed = false;
    private Consultation mConsultation = null;

    private OnCreate onCreate;
    private OnUpdate onUpdate;

    public ConsultationActionsDialog() {
        mConsultationService = ConsultationService.getService();
        mRoomService = RoomService.getService();
    }

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_consultation_create, null);
        binding = DialogConsultationCreateBinding.bind(rootView);

        mBundleArguments = getArguments();
        String titleDialog = null;
        String titlePositiveButton = null;
        if (mBundleArguments != null) {
            int mIdConsultation = mBundleArguments.getInt(ConsultationActivity.KEY, -1);
            if (mIdConsultation != -1) {
                mConsultationService.requestConsultationById(mIdConsultation, this);
                titleDialog = getString(R.string.update_consultation);
                titlePositiveButton = getString(R.string.update);
                isDateAndTimeChoosed = true;
            }
        } else {
            titleDialog = getString(R.string.сreate_сonsultation);
            titlePositiveButton = getString(R.string.create);
            mRoomService.requestAllRooms(this);
        }

        builder.setTitle(titleDialog);
        builder.setView(rootView);
        builder.setPositiveButton(titlePositiveButton, (dialogInterface, i) -> {
            submitConsultation();
        });
        builder.setNegativeButton(R.string.cancel, null);

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                mArrayRooms);

        binding.createSpinnerEt.setAdapter(arrayAdapter);
        binding.createSpinnerEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) binding.createSpinnerEt.showDropDown();
        });
        binding.createSpinnerEt.setOnTouchListener((v, event) -> {
            binding.createSpinnerEt.showDropDown();
            return false;
        });

        binding.createSpinnerEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    binding.createSpinnerTil.setError(getString(R.string.select_room));
                } else {
                    binding.createSpinnerTil.setError(null);
                }
            }
        });
        binding.createSelectDateTimeEt.setOnClickListener(view -> selectDateTime());

        dialog = builder.create();
        return dialog;
    }

    private void setSelectionRoomField() {
        Rooms room;
        for (int i = 0; i < mArrayRooms.size(); i++) {
            room = mArrayRooms.get(i);
            if (mConsultation.getRoom().getName().equals(room.getName())) {
                binding.createSpinnerEt.setText(mConsultation.getRoom().getName(), false);
            }
        }
    }

    private String parseSelectedDate() {
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat input = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        output.setTimeZone(TimeZone.getTimeZone("GMT"));
        input.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = null;
        try {
            String date = binding.createSelectDateTimeEt.getText().toString().replace("\n", " ");
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return output.format(Objects.requireNonNull(d));
    }

    private void submitConsultation() {
        int idSelectedRoom = -1;
        for (int i = 0; i < mArrayRooms.size(); i++) {
            String nameRoom = binding.createSpinnerEt.getText().toString();
            Rooms room = mArrayRooms.get(i);
            if (nameRoom.equals(room.getName())) {
                idSelectedRoom = room.getId();
            }
        }
        if (idSelectedRoom == -1 || !isDateAndTimeChoosed) {
            if (idSelectedRoom == -1)
                binding.createSpinnerTil.setError(getString(R.string.select_сorrect_room));
            if (!isDateAndTimeChoosed)
                binding.createSelectDateTimeTil.setError(getResources().getString(R.string.dialog_consultation_create_select_date_and_time));
            return;
        }

        ConsultationRequestDto consultationRequestDto = new ConsultationRequestDto(
                AuthManager.getInstance().getId(),
                idSelectedRoom,
                parseSelectedDate(),
                (binding.createDescriptionEt.getText().toString().length() == 0) ? null : binding.createDescriptionEt.getText().toString());

        if (mConsultation == null) {
            mConsultationService.createConsultation(consultationRequestDto, new ConsultationService.OnCreateConsultationListener() {
                @Override
                public void onCreateConsultationSuccess(Call<Consultation> call, Response<Consultation> response) {
                    if (response.isSuccessful()) {
                        if (onCreate != null) onCreate.onCreated();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCreateConsultationFailed(Call<Consultation> call, Throwable t) {
                    Toasty.error(requireContext(), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
                }
            });
        } else {
            mConsultationService.updateConsultation(mConsultation.getId(), consultationRequestDto, new ConsultationService.OnUpdateConsultationListener() {
                @Override
                public void onUpdateConsultationSuccess(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        if (onUpdate != null) onUpdate.onUpdated(mConsultation.getId());
                        dialog.dismiss();
                    }
                }

                @Override
                public void onUpdateConsultationFailed(Call<Void> call, Throwable t) {
                    Toasty.error(requireContext(), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
                }
            });
        }
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

        timePickerDialog = new TimePickerDialog(getContext(), ConsultationActionsDialog.this, hour, minute, true);
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

            binding.createSelectDateTimeTil.setError(null);
            binding.createSelectDateTimeEt.setText(
                    returnStringOfDate(yearSelected, monthSelected, daySelected, hourSelected, minuteSelected));
            isDateAndTimeChoosed = true;
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

    @Override
    public void onRequestConsultationSuccess(Call<Consultation> call, Response<Consultation> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                mConsultation = response.body();
                binding.createSelectDateTimeEt.setText(mConsultation.getDateAndTimeOfPassage());
                binding.createDescriptionEt.setText(mConsultation.getDescription());
                mRoomService.requestAllRooms(this);
            }
        }
    }

    @Override
    public void onRequestConsultationFailed(Call<Consultation> call, Throwable t) {
        Toasty.error(requireContext(), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void onRequestRoomListSuccess(Call<List<Rooms>> call, Response<List<Rooms>> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                mArrayRooms.clear();
                mArrayRooms.addAll(response.body());
                if (mBundleArguments != null) {
                    setSelectionRoomField();
                }
            }
        }
    }

    @Override
    public void onRequestRoomListFailed(Call<List<Rooms>> call, Throwable t) {
        Toasty.error(requireContext(), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
    }


    public interface OnCreate {
        void onCreated();
    }

    public interface OnUpdate {
        void onUpdated(int idConsultation);
    }
}