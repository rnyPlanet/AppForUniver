package com.grin.appforuniver.fragments.consultations;

import com.grin.appforuniver.data.service.ConsultationService;

public class MyConsultationsFragment extends ConsultationListFragment {

    public MyConsultationsFragment(OnRecyclerViewScrolled onRecyclerViewScrolled) {
        this.onRecyclerViewScrolled = onRecyclerViewScrolled;
    }

    @Override
    public void getConsultations(ConsultationService.OnRequestConsultationListListener l) {
        mService.requestMyConsultations(l);
    }
}
