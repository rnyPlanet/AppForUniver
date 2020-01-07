package com.grin.appforuniver.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.utils.LocaleUtils;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Init toolbar
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        // Back btn pressed
        toolbar.setNavigationOnClickListener(v -> finish());

        // Add data about application version
        setAppVersion();

        // Change application theme
        Spinner appThemeSpinner = findViewById(R.id.theme_spinner);

        appThemeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String theme = appThemeSpinner.getSelectedItem().toString();
                activateTheme(theme);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Change application language
        LocaleUtils.updateConfig(this);

    }

    public void setAppVersion() {
        String version = "";
        TextView appVersion = findViewById(R.id.app_version);

        // Get app version
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Set new data
        appVersion.setText(version);
    }

    public void activateTheme(String theme) {
        switch (theme) {
            case "Dark":
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );
                break;
            case "Light":
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );
                break;
            case "System":
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                );
                break;
        }
    }

}



