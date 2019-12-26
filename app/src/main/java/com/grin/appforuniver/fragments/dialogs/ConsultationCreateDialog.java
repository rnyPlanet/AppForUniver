package com.grin.appforuniver.fragments.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.grin.appforuniver.fragments.AdminFragment;
import com.grin.appforuniver.fragments.consultations.ConsultationsMyFragment;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationCreateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public final String TAG = ConsultationCreateDialog.class.getSimpleName();
    private Unbinder mUnbinder;

    @BindView(R.id.dialog_consultation_create_til)
    TextInputLayout roomTIL;
    @BindView(R.id.dialog_consultation_create_spinner)
    AutoCompleteTextView roomField;
    private AlertDialog dialog;
    private Button positiveButton;
    private int selectedRoom;

    @BindView(R.id.dialog_consultation_create_select_date_time_btn)
    Button selectDateTimeBTN;

    @BindView(R.id.dialog_consultation_create_date_time_tv)
    TextView selectedDateTimeTV;

    @BindView(R.id.dialog_consultation_create_description_et)
    EditText descriptionET;

    private int day, month, year, hour, minute;
    private int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    private ArrayList mArrayRooms = new ArrayList();

    private boolean isDateAndTimeChoosed;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        PreferenceUtils.context = getContext();

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_consultation_create, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mUnbinder = ButterKnife.bind(this, view);

        isDateAndTimeChoosed = false;
        builder.setTitle("Add new counter");
        builder.setView(view);
        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Сancel", (dialogInterface, i) -> {
        });

        getRooms();

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                mArrayRooms);

        roomField.setOnItemClickListener((adapterView, view1, i, l) -> selectedRoom = i);
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
                positiveButton.setOnClickListener(view12 -> cteateConsultation());
            }
        });
        return dialog;

    }

    private void getRooms() {

        RoomInterface consultationInterface = ServiceGenerator.createService(RoomInterface.class);

        Call<List<Rooms>> call = consultationInterface.getRooms();
        call.enqueue(new Callback<List<Rooms>>() {
            @Override
            public void onResponse(@NonNull Call<List<Rooms>> call, @NonNull Response<List<Rooms>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mArrayRooms.addAll(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Rooms>> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }



    private void cteateConsultation() {

        int idSelectedRoom = 0;
        String nameRoom = roomField.getText().toString();
        for(int i = 0; i < mArrayRooms.size(); i++) {
            Rooms room = (Rooms)mArrayRooms.get(i);
            if(nameRoom.equals(room.getName())) {
                idSelectedRoom = room.getId();
            }
        }
        if(idSelectedRoom == 0) {
            roomTIL.setError("Select correct room");
            return;
        }

        if(!isDateAndTimeChoosed) {
            selectedDateTimeTV.setText("Choose date and time");
            selectedDateTimeTV.setTextColor(getResources().getColor(R.color.errorColor));
            selectedDateTimeTV.setVisibility(View.VISIBLE);
            return;
        }

        String date = yearFinal + "-" + monthFinal + "-" + dayFinal + "T" + hourFinal + ":" + minuteFinal + ":" + "00.000+02:00";
        ConsultationRequestDto consultationRequestDto = new ConsultationRequestDto(
                PreferenceUtils.getSaveUser().getId(),
                idSelectedRoom,
                date,
                (descriptionET.getText().toString().length() == 0) ? null : descriptionET.getText().toString() );

        ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

        Call<Consultation> call = consultationInterface.createConsultation(consultationRequestDto);
        call.enqueue(new Callback<Consultation>() {
            @Override
            public void onResponse(@NonNull Call<Consultation> call, @NonNull Response<Consultation> response) {
                if (response.isSuccessful()) {
                    Toasty.success(Objects.requireNonNull(getContext()), "Successful created", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();

                    List<Fragment> fragments  = getFragmentManager().getFragments();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    for(Fragment fragment : fragments) {
                        ft.detach(fragment).attach(fragment);
                    }
                    ft.commit();

                }
            }

            @Override
            public void onFailure(@NonNull Call<Consultation> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }



    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @OnClick(R.id.dialog_consultation_create_select_date_time_btn)
    void selectDateTime() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), ConsultationCreateDialog.this, year, month, day);
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

        timePickerDialog = new TimePickerDialog(getContext(), ConsultationCreateDialog.this, hour, minute, true);
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (!isValidTime(hourOfDay, minute)) {
            Toasty.info(Objects.requireNonNull(getContext()), "You need choose the correct time", Toast.LENGTH_SHORT, true).show();
            Calendar c = Calendar.getInstance();
            int currentHour = c.get(Calendar.HOUR_OF_DAY);
            int currentMinute = c.get(Calendar.MINUTE);
            timePickerDialog = new TimePickerDialog(getContext(),
                    ConsultationCreateDialog.this, currentHour, currentMinute, true);
            timePickerDialog.show();
        } else {
            hourFinal = hourOfDay;
            minuteFinal = minute;

            selectedDateTimeTV.setText(null);
            selectedDateTimeTV.setText(
                    dayFinal + "." + monthFinal + "." + yearFinal + "\n" +
                    hourFinal + ":" + minuteFinal
            );
            selectedDateTimeTV.setTextColor(getResources().getColor(R.color.accent));

            selectedDateTimeTV.setVisibility(View.VISIBLE);
            isDateAndTimeChoosed = true;
        }
    }
}