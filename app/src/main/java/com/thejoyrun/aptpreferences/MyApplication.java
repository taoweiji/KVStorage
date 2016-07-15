package com.thejoyrun.aptpreferences;

import android.app.Application;

/**
 * Created by Wiki on 16/7/15.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AptPreferencesManager.init(this);
    }
}
