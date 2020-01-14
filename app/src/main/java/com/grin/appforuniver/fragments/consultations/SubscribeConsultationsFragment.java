package com.grin.appforuniver.fragments.consultations;

import com.grin.appforuniver.data.api.ConsultationApi;
import com.grin.appforuniver.data.model.consultation.Consultation;

import java.util.List;

import retrofit2.Call;

public class SubscribeConsultationsFragment extends ConsultationListFragment {

    public SubscribeConsultationsFragment(OnRecyclerViewScrolled onRecyclerViewScrolled) {
        this.onRecyclerViewScrolled = onRecyclerViewScrolled;
    }

    @Override
    public Call<List<Consultation>> getWhatConsultations(ConsultationApi consultationApi) {
        return consultationApi.mySubscriptions();
    }
}