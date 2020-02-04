package com.grin.appforuniver;

import android.app.Application;
import android.content.res.Configuration;

import com.grin.appforuniver.utils.LocaleUtils;

import java.util.Locale;

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        LocaleUtils.setLocale(new Locale("uk"));
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }
}
