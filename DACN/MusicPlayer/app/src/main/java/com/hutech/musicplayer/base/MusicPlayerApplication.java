package com.hutech.musicplayer.base;

import android.app.Application;

public class MusicPlayerApplication extends Application{
    public static Application instance;
    public static Application getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
