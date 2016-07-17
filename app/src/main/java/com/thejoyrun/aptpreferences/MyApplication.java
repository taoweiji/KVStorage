package com.thejoyrun.aptpreferences;

import android.app.Application;

import com.alibaba.fastjson.JSON;

/**
 * Created by Wiki on 16/7/15.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AptPreferencesManager.init(this, new AptParser() {

            @Override
            public Object deserialize(Class clazz, String text) {
                return JSON.parseObject(text,clazz);
            }

            @Override
            public String serialize(Object object) {
                return JSON.toJSONString(object);
            }
        });
    }
}
