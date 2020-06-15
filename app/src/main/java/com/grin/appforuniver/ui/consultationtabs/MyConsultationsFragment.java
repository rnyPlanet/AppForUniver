package com.grin.appforuniver.ui.consultationtabs;

public class MyConsultationsFragment extends ConsultationListFragment {

    public MyConsultationsFragment(OnRecyclerViewScrolled onRecyclerViewScrolled) {
        this.onRecyclerViewScrolled = onRecyclerViewScrolled;
    }

    @Override
    public TypeConsultationList getConsultations() {
        return TypeConsultationList.MY;
    }
}
