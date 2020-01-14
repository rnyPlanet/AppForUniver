package com.grin.appforuniver.fragments.consultations;

import com.grin.appforuniver.data.service.ConsultationService;

public class SubscribeConsultationsFragment extends ConsultationListFragment {

    public SubscribeConsultationsFragment(OnRecyclerViewScrolled onRecyclerViewScrolled) {
        this.onRecyclerViewScrolled = onRecyclerViewScrolled;
    }

    @Override
    public void getConsultations(ConsultationService.OnRequestConsultationListListener l) {
        mService.requestSubscribedConsultations(l);
    }
}