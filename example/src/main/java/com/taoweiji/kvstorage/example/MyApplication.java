package com.taoweiji.kvstorage.example;

import android.app.Application;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taoweiji.kvstorage.KVStorage;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KVStorage.init(this, new KVStorage.Interceptor() {
            @NonNull
            @Override
            public String customFileName(@NonNull String name) {
                if (name.equals("storage.global")) {
                    return name;
                }
                return "1_" + name;
            }

            @Override
            public String decryption(@NonNull String data) {
                return new String(Base64.decode(data, Base64.DEFAULT));
            }

            @Override
            public String encryption(@NonNull String data) {
                return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
            }
        });
    }
}
