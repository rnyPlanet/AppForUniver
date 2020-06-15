package com.grin.appforuniver.ui.consultationtabs;

public class AllConsultationsFragment extends ConsultationListFragment {

    public AllConsultationsFragment(OnRecyclerViewScrolled onRecyclerViewScrolled) {
        this.onRecyclerViewScrolled = onRecyclerViewScrolled;
    }

    @Override
    public TypeConsultationList getConsultations() {
        return TypeConsultationList.ALL;
    }
}