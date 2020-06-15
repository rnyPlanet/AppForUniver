package com.grin.appforuniver.ui.consultationtabs;

public class SubscribeConsultationsFragment extends ConsultationListFragment {

    public SubscribeConsultationsFragment(OnRecyclerViewScrolled onRecyclerViewScrolled) {
        this.onRecyclerViewScrolled = onRecyclerViewScrolled;
    }

    @Override
    public TypeConsultationList getConsultations() {
        return TypeConsultationList.SUBSCRIBE;
    }
}