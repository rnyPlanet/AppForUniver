package com.grin.appforuniver.fragments.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.ConsultationActivity;
import com.grin.appforuniver.data.WebServices.ConsultationInterface;
import com.grin.appforuniver.data.WebServices.RoomInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationUpdateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public final String TAG = ConsultationUpdateDialog.class.getSimpleName();
    private Unbinder mUnbinder;

    @BindView(R.id.dialog_consultation_create_spinner_til)
    TextInputLayout roomTIL;
    @BindView(R.id.dialog_consultation_create_spinner_et)
    AutoCompleteTextView roomField;

    @BindView(R.id.dialog_consultation_create_select_date_time_til)
    TextInputLayout selectDateTimeTIL;
    @BindView(R.id.dialog_consultation_create_select_date_time_et)
    AutoCompleteTextView selectDateTimeET;

    @BindView(R.id.dialog_consultation_create_description_til)
    TextInputLayout descriptionTIL;
    @BindView(R.id.dialog_consultation_create_description_et)
    AutoCompleteTextView descriptionET;

    private AlertDialog dialog;
    private Button positiveButton;

    private int day, month, year, hour, minute;
    private int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    private ArrayList mArrayRooms = new ArrayList();

    private int mIdConsultation;
    private Consultation mConsultation;

    public interface OnUpdateConsultation {
        void onUpdateConsultation(int id);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        mIdConsultation = Objects.requireNonNull(getArguments()).getInt(ConsultationActivity.key, 0);
        if(mIdConsultation != 0) getConsultationById();

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_consultation_create, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mUnbinder = ButterKnife.bind(this, view);

        builder.setTitle("Update consultation");
        builder.setView(view);
        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Сancel", (dialogInterface, i) -> {});

        getRooms();

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                mArrayRooms);

        roomField.setOnItemClickListener((adapterView, view1, i, l) -> {});
        roomField.setAdapter(arrayAdapter);
        roomField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) roomField.showDropDown();
        });
        roomField.setOnTouchListener((v, event) -> {
            roomField.showDropDown();
            return false;
        });

        roomField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    roomTIL.setError("Select room");
                } else {
                    roomTIL.setError(null);
                }
            }
        });

        dialog = builder.create();
        dialog.setOnShowListener(dialogInner -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (button != null) {
                positiveButton = button;
                positiveButton.setOnClickListener(view12 -> updateConsultation());
            }
        });

        return dialog;

    }

    private void getRooms() {
        RoomInterface roomInterface = ServiceGenerator.createService(RoomInterface.class);

        Call<List<Rooms>> call = roomInterface.getRooms();
        call.enqueue(new Callback<List<Rooms>>() {
            @Override
            public void onResponse(@NonNull Call<List<Rooms>> call, @NonNull Response<List<Rooms>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mArrayRooms.addAll(response.body());
                        setSelectionRoomField();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Rooms>> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }
    private void setSelectionRoomField() {
        Rooms room;
        for (int i = 0; i < mArrayRooms.size(); i++) {
            room = (Rooms) mArrayRooms.get(i);
            if(mConsultation.getRoom().getName().equals(room.getName())){
                roomField.setText(mConsultation.getRoom().getName());
            }
        }
    }

    private void getConsultationById() {
        ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

        Call<Consultation> call = consultationInterface.getConsultationById(mIdConsultation);
        call.enqueue(new Callback<Consultation>() {
            @Override
            public void onResponse(@NonNull Call<Consultation> call, @NonNull Response<Consultation> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mConsultation = response.body();
                        selectDateTimeET.setText(mConsultation.getDateAndTimeOfPassage());
                        descriptionET.setText(mConsultation.getDescription());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Consultation> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private String parseSelectedDate() {
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        output.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = null;
        try {
            String date = selectDateTimeET.getText().toString().replace("\n", " ");
            d = sdf.parse( date );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return output.format(Objects.requireNonNull(d));
    }

    private void updateConsultation() {

        int idSelectedRoom = 0;
        Rooms room;
        String nameRoom = roomField.getText().toString();
        for (int i = 0; i < mArrayRooms.size(); i++) {
            room = (Rooms) mArrayRooms.get(i);
            if (nameRoom.equals(room.getName())) {
                idSelectedRoom = room.getId();
            }
        }
        if (idSelectedRoom == 0) {
            roomTIL.setError("Select correct room");
            return;
        }

        ConsultationRequestDto consultationRequestDto = new ConsultationRequestDto(
                PreferenceUtils.getSaveUser().getId(),
                idSelectedRoom,
                parseSelectedDate(),
                (descriptionET.getText().toString().length() == 0) ? null : descriptionET.getText().toString());

        ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

        Call<Void> call = consultationInterface.updateConsultation(mConsultation.getId(), consultationRequestDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toasty.success(Objects.requireNonNull(getContext()), "Successful updated", Toast.LENGTH_SHORT, true).show();

                    OnUpdateConsultation activity = (OnUpdateConsultation) getActivity();
                    Objects.requireNonNull(activity).onUpdateConsultation(mConsultation.getId());

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @OnClick(R.id.dialog_consultation_create_select_date_time_et)
    void selectDateTime() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), ConsultationUpdateDialog.this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(getContext(), ConsultationUpdateDialog.this, hour, minute, true);
        timePickerDialog.show();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (!isValidTime(hourOfDay, minute)) {
            Toasty.info(Objects.requireNonNull(getContext()), "You need choose the correct time", Toast.LENGTH_SHORT, true).show();
            Calendar c = Calendar.getInstance();
            int currentHour = c.get(Calendar.HOUR_OF_DAY);
            int currentMinute = c.get(Calendar.MINUTE);
            timePickerDialog = new TimePickerDialog(getContext(),
                    ConsultationUpdateDialog.this, currentHour, currentMinute, true);
            timePickerDialog.show();
        } else {
            hourFinal = hourOfDay;
            minuteFinal = minute;

            selectDateTimeTIL.setError(null);
            selectDateTimeET.setText(
                    dayFinal + "." + monthFinal + "." + yearFinal + "\n" +
                            hourFinal + ":" + ((minuteFinal < 10) ? "0" + minuteFinal : minuteFinal)
            );
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}