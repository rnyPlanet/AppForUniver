package com.grin.appforuniver.ui.detailConsultation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.service.ConsultationService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class DetailConsultationViewModel extends ViewModel implements ConsultationService.OnRequestConsultationListener, ConsultationService.OnRequestStatusConsultationListener, ConsultationService.OnSetSubscribeStatusListener {
    private ConsultationService consultationService;

    private MutableLiveData<Integer> idConsultationLiveData;
    private MutableLiveData<Consultation> consultationLiveData;
    private MutableLiveData<Boolean> isSubscribeLiveData;
    private MutableLiveData<Boolean> isCanManageLiveData;
    private MutableLiveData<String> errorMessageLiveData;

    public DetailConsultationViewModel() {
        consultationService = ConsultationService.getService();

        idConsultationLiveData = new MutableLiveData<>();
        consultationLiveData = new MutableLiveData<>();
        isSubscribeLiveData = new MutableLiveData<>();
        isCanManageLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
    }

    public void requestConsultation(int consultationId) {
        consultationService.requestConsultationById(consultationId, this);
        consultationService.requestStatusConsultation(consultationId, this);
    }

    public void setIdConsultation(int idConsultation) {
        idConsultationLiveData.setValue(idConsultation);
    }

    public LiveData<Integer> getIdConsultation() {
        return idConsultationLiveData;
    }

    public LiveData<Consultation> getConsultation() {
        return consultationLiveData;
    }

    public LiveData<Boolean> isSubscribe() {
        return isSubscribeLiveData;
    }

    public LiveData<Boolean> isCanManage() {
        return isCanManageLiveData;
    }

    public void setErrorMessage(String value) {
        errorMessageLiveData.setValue(value);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public void subscribe() {
        consultationService.setSubscribeStatus(idConsultationLiveData.getValue(), false, this);
    }

    public void unSubscribe() {
        consultationService.setSubscribeStatus(idConsultationLiveData.getValue(), true, this);
    }

    public LiveData<Boolean> deleteConsultation(int id) {
        MutableLiveData<Boolean> isDeleteConsultation = new MutableLiveData<>();
        consultationService.deleteConsultation(id, new ConsultationService.OnDeleteConsultationListener() {
            @Override
            public void onDeleteConsultationSuccess(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isDeleteConsultation.setValue(true);
                } else {
                    isDeleteConsultation.setValue(false);
                }
            }

            @Override
            public void onDeleteConsultationFailed(Call<Void> call, Throwable t) {
                isDeleteConsultation.setValue(false);
                errorMessageLiveData.setValue(t.getMessage());
            }
        });
        return isDeleteConsultation;
    }

    @Override
    public void onRequestConsultationSuccess(Call<Consultation> call, Response<Consultation> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                consultationLiveData.setValue(response.body());
            }
        }
    }

    @Override
    public void onRequestConsultationFailed(Call<Consultation> call, Throwable t) {
        errorMessageLiveData.setValue(t.getMessage());
    }

    @Override
    public void onRequestStatusConsultationSuccess(Call<Map<Object, Boolean>> call, Response<Map<Object, Boolean>> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                Map<Object, Boolean> map = response.body();
                if (map.containsKey("isCanManage")) {
                    isCanManageLiveData.setValue(map.get("isCanManage"));
                }
                if (map.containsKey("isSubscribed")) {
                    isSubscribeLiveData.setValue(map.get("isSubscribed"));
                }
            }
        }
    }

    @Override
    public void onRequestStatusConsultationFailed(Call<Map<Object, Boolean>> call, Throwable t) {
        errorMessageLiveData.setValue(t.getMessage());
    }

    @Override
    public void onSetSubscribeConsultationSuccess(boolean statusSubscribe, Call<Void> call, Response<Void> response) {
        isSubscribeLiveData.setValue(statusSubscribe);
    }

    @Override
    public void onSetSubscribeConsultationFailed(boolean statusSubscribe, Call<Void> call, Throwable t) {
        errorMessageLiveData.setValue(t.getMessage());
    }
}
