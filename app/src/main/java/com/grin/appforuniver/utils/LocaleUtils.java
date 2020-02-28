package com.grin.appforuniver.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.grin.appforuniver.App;
import com.grin.appforuniver.activities.SettingsActivity;

import java.util.Locale;

import static com.grin.appforuniver.data.tools.AuthManager.PREFERENCE_NAME;

public class LocaleUtils {

    public static void loadLocale(Context context) {
        SharedPreferences sharedPreferences = App.getInstance()
                .getSharedPreferences(SettingsActivity.PREFERENCE_NAME_SETTINGS, Context.MODE_PRIVATE);
        Locale locale = new Locale(sharedPreferences.getString("language", "en"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public static Locale getLocale(Context context) {
        SharedPreferences sharedPreferences = App.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return new Locale(sharedPreferences.getString("language", "en"));
    }
}