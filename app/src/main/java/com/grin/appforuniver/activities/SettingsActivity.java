package com.grin.appforuniver.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.grin.appforuniver.App;
import com.grin.appforuniver.R;
import com.grin.appforuniver.utils.LocaleUtils;
import com.grin.appforuniver.utils.ThemeUtils;

public class SettingsActivity extends AppCompatActivity {
    public static final String PREFERENCE_NAME_SETTINGS = "settings";
    private static boolean settingChanged = false;
    private static boolean activityRestarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switch (ThemeUtils.getTheme()) {
            case ThemeUtils.Theme.SYSTEM:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case ThemeUtils.Theme.LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case ThemeUtils.Theme.DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
        super.onCreate(savedInstanceState);
        LocaleUtils.loadLocale(this);
        setContentView(R.layout.activity_settings);

        // Init toolbar
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        // Back btn pressed
        toolbar.setNavigationOnClickListener(v -> {
            if (settingChanged) {
                NavUtils.navigateUpFromSameTask(this);
            } else {
                onBackPressed();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.pref_content, new SettingsFragment()).commit();

        if (!activityRestarted) {
            settingChanged = false;
        }
        activityRestarted = false;
    }

    @Override
    public void onBackPressed() {
        if (settingChanged) {
            NavUtils.navigateUpFromSameTask(this);
        } else {
            super.onBackPressed();
        }
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesName(PREFERENCE_NAME_SETTINGS);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onResume() {
            super.onResume();
            App.getInstance().getSharedPreferences(PREFERENCE_NAME_SETTINGS, Context.MODE_PRIVATE)
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            App.getInstance().getSharedPreferences(PREFERENCE_NAME_SETTINGS, Context.MODE_PRIVATE)
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            settingChanged = true;
            LocaleUtils.loadLocale(getActivity().getBaseContext());

            if (key.equals("language")) {
                restartActivity();
                LocaleUtils.loadLocale(getActivity().getBaseContext());
            }

            if (key.equals("theme")) {
                restartActivity();
            }
        }

        private void restartActivity() {
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
            activityRestarted = true;
        }
    }
}
