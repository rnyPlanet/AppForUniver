package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.consultations.AllConsultationsFragment;
import com.grin.appforuniver.fragments.consultations.ConsultationListFragment;
import com.grin.appforuniver.fragments.consultations.MyConsultationsFragment;
import com.grin.appforuniver.fragments.consultations.SubscribeConsultationsFragment;
import com.grin.appforuniver.fragments.dialogs.ConsultationActionsDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

public class ConsultationFragment extends Fragment implements ConsultationListFragment.OnRecyclerViewScrolled,
        ConsultationActionsDialog.OnCreate {

    public final String TAG = ConsultationFragment.class.getSimpleName();

    private View mView;
    private Unbinder mUnbinder;

    @BindView(R.id.fragment_consultation_viewpager)
    ViewPager viewPager;
    @BindView(R.id.fragment_consultation_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.fragment_consultations_fab)
    FloatingActionButton floatingActionButton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_consultations, container, false);
        Objects.requireNonNull(getActivity()).setTitle(R.string.menu_consultation);
        mUnbinder = ButterKnife.bind(this, mView);

        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);
        if (PreferenceUtils.getUserRoles().contains("ROLE_TEACHER")) {
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
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private class ItemViewPager {
            String title;
            Fragment fragment;

            public ItemViewPager(String title, Fragment fragment) {
                this.title = title;
                this.fragment = fragment;
            }
        }

        private List<ItemViewPager> items;

        private PagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            items = new ArrayList<>();
            items.add(new ItemViewPager(getString(R.string.consultation_all), new AllConsultationsFragment(ConsultationFragment.this)));
            if (PreferenceUtils.getUserRoles().contains("ROLE_TEACHER")) {
                items.add(new ItemViewPager(getString(R.string.consultation_my), new MyConsultationsFragment(ConsultationFragment.this)));
            }
            items.add(new ItemViewPager(getString(R.string.consultation_subscribe), new SubscribeConsultationsFragment(ConsultationFragment.this)));
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position).fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).title;
        }

        @Override
        public int getCount() {
            return items.size();
        }
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
