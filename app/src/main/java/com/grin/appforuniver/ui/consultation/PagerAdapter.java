package com.grin.appforuniver.ui.consultation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.grin.appforuniver.App;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.fragments.consultations.AllConsultationsFragment;
import com.grin.appforuniver.fragments.consultations.ConsultationListFragment;
import com.grin.appforuniver.fragments.consultations.MyConsultationsFragment;
import com.grin.appforuniver.fragments.consultations.SubscribeConsultationsFragment;

import java.util.ArrayList;
import java.util.List;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

class PagerAdapter extends FragmentPagerAdapter {
    public static class ItemViewPager {
        String title;
        Fragment fragment;

        public ItemViewPager(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }

    private List<ItemViewPager> tabs;

    PagerAdapter(FragmentManager supportFragmentManager, ConsultationListFragment.OnRecyclerViewScrolled onRecyclerViewScrolled) {
        super(supportFragmentManager);
        tabs = new ArrayList<>();
        tabs.add(new ItemViewPager(
                App.getInstance().getApplicationContext().getString(R.string.consultation_all),
                new AllConsultationsFragment(onRecyclerViewScrolled)
        ));
        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            tabs.add(new ItemViewPager(
                    App.getInstance().getApplicationContext().getString(R.string.consultation_my),
                    new MyConsultationsFragment(onRecyclerViewScrolled)
            ));
        }
        tabs.add(new ItemViewPager(
                App.getInstance().getApplicationContext().getString(R.string.consultation_subscribe),
                new SubscribeConsultationsFragment(onRecyclerViewScrolled)
        ));


    }

    public void addAll(List<ItemViewPager> tabs) {
        this.tabs.addAll(tabs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).title;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
