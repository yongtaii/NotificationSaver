package com.rnd.jyong.notificationsaver.base;

import android.app.Application;


public class BaseApplication extends Application {

    private static BaseApplication app;

    public BaseApplication() {
        super();
        app = this;
    }

    public static BaseApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
