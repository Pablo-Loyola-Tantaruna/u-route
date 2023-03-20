package com.zea.company.route_u;

import android.app.Application;

import timber.log.Timber;

public class InitApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Iniciamos el timber
        Timber.plant(new Timber.DebugTree());
    }
}
