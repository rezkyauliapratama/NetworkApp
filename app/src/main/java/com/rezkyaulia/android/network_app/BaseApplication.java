package com.rezkyaulia.android.network_app;

import android.app.Application;

import com.rezkyaulia.android.light_optimization_data.NetworkClient;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/5/2017.
 */

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        NetworkClient.getInstance().client(getApplicationContext());

    }
}
