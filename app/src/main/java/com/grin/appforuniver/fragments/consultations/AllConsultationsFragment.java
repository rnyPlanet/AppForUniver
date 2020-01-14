package com.grin.appforuniver.fragments.consultations;

import com.grin.appforuniver.data.service.ConsultationService;

public class AllConsultationsFragment extends ConsultationListFragment {

    public AllConsultationsFragment(OnRecyclerViewScrolled onRecyclerViewScrolled) {
        this.onRecyclerViewScrolled = onRecyclerViewScrolled;
    }

    @Override
    public void getConsultations(ConsultationService.OnRequestConsultationListListener l) {
        mService.requestAllConsultation(l);
    }
}