package com.randomappsinc.foodjournal.utils;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.randomappsinc.foodjournal.api.RestClient;
import com.randomappsinc.foodjournal.persistence.PreferencesManager;

public final class MyApplication extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new IoniconsModule());
        instance = getApplicationContext();

        if (PreferencesManager.get().getBearerToken().isEmpty()) {
            RestClient.getInstance().refreshToken();
        }
    }

    public static Context getAppContext() {
        return instance;
    }
}
