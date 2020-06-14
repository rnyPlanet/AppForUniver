package com.grin.appforuniver.ui.consultation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.dialogs.ConsultationActionsDialog;
import com.grin.appforuniver.fragments.consultations.ConsultationListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

public class ConsultationFragment extends Fragment implements ConsultationListFragment.OnRecyclerViewScrolled,
        ConsultationActionsDialog.OnCreate {

    public final String TAG = ConsultationFragment.class.getSimpleName();

    private View mView;
    private ConsultationViewModel viewModel;
    private Unbinder mUnbinder;

    @BindView(R.id.fragment_consultation_viewpager)
    ViewPager viewPager;
    @BindView(R.id.fragment_consultation_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.fragment_consultations_fab)
    FloatingActionButton floatingActionButton;
    PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(ConsultationViewModel.class);
        mView = inflater.inflate(R.layout.fragment_consultations, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);
        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            floatingActionButton.show();
        }
        return mView;
    }

    @OnClick(R.id.fragment_consultations_fab)
    void create() {
        ConsultationActionsDialog consultationCreateDialog = new ConsultationActionsDialog(getContext(), this);
        consultationCreateDialog.show(getFragmentManager(), "consultationCreateDialog");
    }

    @Override
    public void onCreated() {
        Toasty.success(getContext(), getString(R.string.successful_created), Toast.LENGTH_SHORT, true).show();
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
            floatingActionButton.hide();
        } else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
            floatingActionButton.show();
        }
    }
}
