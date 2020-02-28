package com.grin.appforuniver;

import android.app.Application;

import com.grin.appforuniver.utils.LocaleUtils;

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        LocaleUtils.loadLocale(this);
    }
}
