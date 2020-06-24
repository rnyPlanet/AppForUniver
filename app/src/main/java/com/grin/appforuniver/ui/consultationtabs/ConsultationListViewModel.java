package com.grin.appforuniver.ui.consultationtabs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grin.appforuniver.data.models.Consultation;
import com.grin.appforuniver.data.service.ConsultationService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ConsultationListViewModel extends ViewModel implements ConsultationService.OnRequestConsultationListListener {

    private ConsultationService mService;

    private MutableLiveData<List<Consultation>> consultationsLiveData;
    private MutableLiveData<Boolean> isGettingData;
    private MutableLiveData<String> errorMessageLiveData;

    public ConsultationListViewModel() {
        mService = ConsultationService.getService();

        consultationsLiveData = new MutableLiveData<>();
        isGettingData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Consultation>> getConsultations() {
        return consultationsLiveData;
    }

    public MutableLiveData<Boolean> getIsGettingData() {
        return isGettingData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public void requestAllConsultation() {
        mService.requestAllConsultation(this);
    }

    public void requestMyConsultations() {
        mService.requestMyConsultations(this);
    }

    public void requestSubscribedConsultations() {
        mService.requestSubscribedConsultations(this);
    }

    @Override
    public void onRequestConsultationListSuccess(Call<List<Consultation>> call, Response<List<Consultation>> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                consultationsLiveData.setValue(response.body());
                isGettingData.setValue(true);
            } else {
                isGettingData.setValue(false);
            }
        } else {
            isGettingData.setValue(false);
        }

    }

    @Override
    public void onRequestConsultationListFailed(Call<List<Consultation>> call, Throwable t) {
        errorMessageLiveData.setValue(t.getMessage());
        isGettingData.setValue(false);
    }
}
