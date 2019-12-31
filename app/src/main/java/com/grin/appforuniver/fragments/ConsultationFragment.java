package com.grin.appforuniver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.consultations.ConsultationsAllFragment;
import com.grin.appforuniver.fragments.consultations.ConsultationsMyFragment;
import com.grin.appforuniver.fragments.consultations.ConsultationsSubscribeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConsultationFragment extends Fragment {

    public final String TAG = ConsultationFragment.class.getSimpleName();

    private View mView;
    private Unbinder mUnbinder;

    @BindView(R.id.fragment_consultation_viewpager)
    ViewPager viewPager;
    @BindView(R.id.fragment_consultation_tabLayout)
    TabLayout tabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_consultations, container, false);
        Objects.requireNonNull(getActivity()).setTitle(R.string.menu_consultation);
        mUnbinder = ButterKnife.bind(this, mView);

        PagerAdapter mFragmentAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mFragmentAdapter);

        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);
        return mView;
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private List<String> titlePages;
        private List<Fragment> fragmentPages;

        private PagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);

            titlePages = new ArrayList<>();
            fragmentPages = new ArrayList<>();

            titlePages.add(getString(R.string.consultation_all));
            fragmentPages.add(new ConsultationsAllFragment());

            if (PreferenceUtils.getUserRoles().contains("ROLE_TEACHER")) {
                titlePages.add(getString(R.string.consultation_my));
                fragmentPages.add(new ConsultationsMyFragment());
            }

            titlePages.add(getString(R.string.consultation_subscribe));
            fragmentPages.add(new ConsultationsSubscribeFragment());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentPages.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titlePages.get(position);
        }

        @Override
        public int getCount() {
            return fragmentPages.size();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
