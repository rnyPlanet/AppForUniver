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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activities.ConsultationActivity;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.service.ConsultationService;
import com.grin.appforuniver.data.service.RoomService;
import com.grin.appforuniver.data.tools.AuthManager;

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
import retrofit2.Response;

public class ConsultationActionsDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, ConsultationService.OnRequestConsultationListener, RoomService.OnRequestRoomListListener {
    private static final String TAG = "ConsultationActionsDialog";
    private ConsultationService mConsultationService;
    private RoomService mRoomService;
    private Bundle mBundleArguments;
    private Context mContext;
    private Unbinder mUnbinder;



    private View mRootView;
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

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private int daySelected, monthSelected, yearSelected, hourSelected, minuteSelected;

    private ArrayList<Rooms> mArrayRooms = new ArrayList<>();

    private boolean isDateAndTimeChoosed = false;
    private Consultation mConsultation = null;

    private OnCreate onCreate;
    private OnUpdate onUpdate;

    public ConsultationActionsDialog(Context context) {
        this.mContext = context;
        mConsultationService = ConsultationService.getService();
        mRoomService = RoomService.getService();
    }

    public ConsultationActionsDialog(Context context, OnCreate onCreate) {
        this(context);
        this.onCreate = onCreate;
    }

    public ConsultationActionsDialog(Context context, OnUpdate onUpdate) {
        this(context);
        this.onUpdate = onUpdate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        mRootView = inflater.inflate(R.layout.dialog_consultation_create, null);
        mUnbinder = ButterKnife.bind(this, mRootView);

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
        builder.setView(mRootView);
        builder.setPositiveButton(titlePositiveButton, (dialogInterface, i) -> {
            submitConsultation();
        });
        builder.setNegativeButton(R.string.cancel, null);

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                mArrayRooms);

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
                    roomTIL.setError(getString(R.string.select_room));
                } else {
                    roomTIL.setError(null);
                }
            }
        });


        dialog = builder.create();
        return dialog;
    }

    private void setSelectionRoomField() {
        Rooms room;
        for (int i = 0; i < mArrayRooms.size(); i++) {
            room = mArrayRooms.get(i);
            if (mConsultation.getRoom().getName().equals(room.getName())) {
                roomField.setText(mConsultation.getRoom().getName(), false);
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
            String date = selectDateTimeET.getText().toString().replace("\n", " ");
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return output.format(Objects.requireNonNull(d));
    }

    private void submitConsultation() {
        int idSelectedRoom = -1;
        for (int i = 0; i < mArrayRooms.size(); i++) {
            String nameRoom = roomField.getText().toString();
            Rooms room = mArrayRooms.get(i);
            if (nameRoom.equals(room.getName())) {
                idSelectedRoom = room.getId();
            }
        }
        if (idSelectedRoom == -1 || !isDateAndTimeChoosed) {
            if (idSelectedRoom == -1) roomTIL.setError(getString(R.string.select_сorrect_room));
            if (!isDateAndTimeChoosed)
                selectDateTimeTIL.setError(getResources().getString(R.string.dialog_consultation_create_select_date_and_time));
            return;
        }

        ConsultationRequestDto consultationRequestDto = new ConsultationRequestDto(
                AuthManager.getInstance().getId(),
                idSelectedRoom,
                parseSelectedDate(),
                (descriptionET.getText().toString().length() == 0) ? null : descriptionET.getText().toString());

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
                    Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
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
                    Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
                }
            });
        }
    }

    @OnClick(R.id.dialog_consultation_create_select_date_time_et)
    void selectDateTime() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), ConsultationActionsDialog.this, year, month, day);
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
            Toasty.info(Objects.requireNonNull(getContext()), getString(R.string.choose_correct_time), Toast.LENGTH_SHORT, true).show();
            Calendar c = Calendar.getInstance();
            int currentHour = c.get(Calendar.HOUR_OF_DAY);
            int currentMinute = c.get(Calendar.MINUTE);
            timePickerDialog = new TimePickerDialog(getContext(),
                    ConsultationActionsDialog.this, currentHour, currentMinute, true);
            timePickerDialog.show();
        } else {
            hourSelected = hourOfDay;
            minuteSelected = minute;

            selectDateTimeTIL.setError(null);
            selectDateTimeET.setText(
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
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onRequestConsultationSuccess(Call<Consultation> call, Response<Consultation> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                mConsultation = response.body();
                selectDateTimeET.setText(mConsultation.getDateAndTimeOfPassage());
                descriptionET.setText(mConsultation.getDescription());
                mRoomService.requestAllRooms(this);
            }
        }
    }

    @Override
    public void onRequestConsultationFailed(Call<Consultation> call, Throwable t) {
        Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();

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
        Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
    }


    public interface OnCreate {
        void onCreated();
    }

    public interface OnUpdate {
        void onUpdated(int idConsultation);
    }
}