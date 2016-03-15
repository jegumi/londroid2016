package com.jegumi.remotecontrol;

import android.app.Application;

import com.squareup.otto.Bus;

public class RemoteControlApplication extends Application {

    private static MainThreadBus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
        mBus = new MainThreadBus();
    }

    public static MainThreadBus getBus() {
        return mBus;
    }
}
