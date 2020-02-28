package com.grin.appforuniver.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringDef;

import com.grin.appforuniver.App;
import com.grin.appforuniver.activities.SettingsActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ThemeUtils {

    private final static String TAG = "ThemeUtils";

    public @StringDef({Theme.SYSTEM, Theme.LIGHT, Theme.DARK})
    @Retention(RetentionPolicy.SOURCE)
    @interface Theme {
        String SYSTEM = "System";
        String LIGHT = "Light";
        String DARK = "Dark";
    }

    public static @Theme
    String getTheme() {
        SharedPreferences sharedPreferences = App.getInstance()
                .getSharedPreferences(SettingsActivity.PREFERENCE_NAME_SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("theme", Theme.LIGHT);
    }

}
