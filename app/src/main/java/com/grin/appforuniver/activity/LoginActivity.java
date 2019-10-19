package com.grin.appforuniver.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.fragments.AdminFragment;
import com.grin.appforuniver.fragments.auth.LoginFragment;
import com.grin.appforuniver.fragments.auth.RegFragment;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        TabLayout tabLayout = findViewById(R.id.tabs_log_reg_activity);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar_log_reg);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class Adapter extends FragmentPagerAdapter {
        int items = 2;

        Adapter(androidx.fragment.app.FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new LoginFragment();
                case 1:
                    return new RegFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Login";
                case 1:
                    return "Registr";
            }
            return null;
        }
    }
}
