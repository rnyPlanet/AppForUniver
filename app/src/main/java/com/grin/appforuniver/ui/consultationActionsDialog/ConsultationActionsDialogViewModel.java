package com.grin.appforuniver.ui.consultationActionsDialog;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grin.appforuniver.App;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.service.ConsultationService;
import com.grin.appforuniver.data.service.RoomService;
import com.grin.appforuniver.data.tools.AuthManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Response;

public class ConsultationActionsDialogViewModel extends ViewModel implements ConsultationService.OnRequestConsultationListener, RoomService.OnRequestRoomListListener {
    private ConsultationService consultationService;
    private RoomService roomService;
    private Consultation consultation;

    private MutableLiveData<Integer> idConsultationLiveData;
    private MutableLiveData<List<Rooms>> roomsLiveData;
    private MutableLiveData<Rooms> selectedRoomLiveData;

    private MutableLiveData<String> selectedDateAndTimeLiveData;
    private MutableLiveData<String> descriptionLiveData;

    private MutableLiveData<String> errorMessageLiveData;
    private MutableLiveData<Boolean> isCreatedLiveData;
    private MutableLiveData<Boolean> isUpdatedLiveData;

    private MutableLiveData<String> errorMessageRoomLiveData;
    private MutableLiveData<String> errorMessageDateAndTimeLiveData;

    public ConsultationActionsDialogViewModel() {
        consultationService = ConsultationService.getService();
        roomService = RoomService.getService();

        idConsultationLiveData = new MutableLiveData<>();
        roomsLiveData = new MutableLiveData<>();
        selectedRoomLiveData = new MutableLiveData<>();
        selectedDateAndTimeLiveData = new MutableLiveData<>();
        descriptionLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
        isCreatedLiveData = new MutableLiveData<>();
        isUpdatedLiveData = new MutableLiveData<>();

        errorMessageRoomLiveData = new MutableLiveData<>();
        errorMessageDateAndTimeLiveData = new MutableLiveData<>();
        roomService.requestAllRooms(this);
    }

    public void requestConsultation(int consultationId) {
        consultationService.requestConsultationById(consultationId, this);
    }

    public void setIdConsultation(int idConsultation) {
        idConsultationLiveData.setValue(idConsultation);
    }

    public LiveData<Integer> getIdConsultation() {
        return idConsultationLiveData;
    }

    public LiveData<List<Rooms>> getRooms() {
        return roomsLiveData;
    }

    public void setSelectedRoom(Rooms value) {
        selectedRoomLiveData.setValue(value);
    }

    public LiveData<Rooms> getSelectedRoom() {
        return selectedRoomLiveData;
    }

    public void setSelectedDateAndTime(String value) {
        selectedDateAndTimeLiveData.setValue(value);
    }

    public LiveData<String> getSelectedDateAndTime() {
        return selectedDateAndTimeLiveData;
    }

    public void setDescription(String s) {
        descriptionLiveData.setValue(s);
    }

    public LiveData<String> getDescription() {
        return descriptionLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public LiveData<Boolean> isCreated() {
        return isCreatedLiveData;
    }

    public LiveData<Boolean> isUpdated() {
        return isUpdatedLiveData;
    }

    public LiveData<String> getErrorMessageRoom() {
        return errorMessageRoomLiveData;
    }

    public LiveData<String> getErrorMessageDateAndTime() {
        return errorMessageDateAndTimeLiveData;
    }

    public void submitConsultation() {
        if (selectedRoomLiveData.getValue() == null || selectedDateAndTimeLiveData.getValue() == null) {
            if (selectedRoomLiveData.getValue() == null)
                errorMessageRoomLiveData.setValue(App.getInstance().getApplicationContext().getString(R.string.select_сorrect_room));
            if (selectedDateAndTimeLiveData.getValue() == null)
                errorMessageDateAndTimeLiveData.setValue(App.getInstance().getApplicationContext().getString(R.string.dialog_consultation_create_select_date_and_time));
            return;
        }

        ConsultationRequestDto consultationRequestDto = new ConsultationRequestDto(
                AuthManager.getInstance().getId(),
                selectedRoomLiveData.getValue().getId(),
                parseSelectedDate(),
                descriptionLiveData.getValue() == null || descriptionLiveData.getValue().length() == 0 ? null : descriptionLiveData.getValue()
        );

        if (consultation == null) {
            consultationService.createConsultation(consultationRequestDto, new ConsultationService.OnCreateConsultationListener() {
                @Override
                public void onCreateConsultationSuccess(Call<Consultation> call, Response<Consultation> response) {
                    if (response.isSuccessful()) {
                        isCreatedLiveData.setValue(true);
                    }
                }

                @Override
                public void onCreateConsultationFailed(Call<Consultation> call, Throwable t) {
                    errorMessageLiveData.setValue(t.getMessage());
                }
            });
        } else {
            consultationService.updateConsultation(consultation.getId(), consultationRequestDto, new ConsultationService.OnUpdateConsultationListener() {
                @Override
                public void onUpdateConsultationSuccess(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        isUpdatedLiveData.setValue(true);
                    }
                }

                @Override
                public void onUpdateConsultationFailed(Call<Void> call, Throwable t) {
                    errorMessageLiveData.setValue(t.getMessage());
                }
            });
        }
    }

    private String parseSelectedDate() {
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat input = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        output.setTimeZone(TimeZone.getTimeZone("GMT"));
        input.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = null;
        try {
            String date = selectedDateAndTimeLiveData.getValue().replace("\n", " ");
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return output.format(Objects.requireNonNull(d));
    }

    @Override
    public void onRequestConsultationSuccess(Call<Consultation> call, Response<Consultation> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                consultation = response.body();
                selectedDateAndTimeLiveData.setValue(consultation.getDateAndTimeOfPassage());
                descriptionLiveData.setValue(consultation.getDescription());
                if (idConsultationLiveData.getValue() != null) {
                    setSelectedRoom(consultation.getRoom());
                }
            }
        }
    }

    @Override
    public void onRequestConsultationFailed(Call<Consultation> call, Throwable t) {
        errorMessageLiveData.setValue(t.getMessage());
    }

    @Override
    public void onRequestRoomListSuccess(Call<List<Rooms>> call, Response<List<Rooms>> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                roomsLiveData.setValue(response.body());
            }
        }
    }


    @Override
    public void onRequestRoomListFailed(Call<List<Rooms>> call, Throwable t) {
        errorMessageLiveData.setValue(t.getMessage());
    }
}
