package com.taoweiji.kvstorage.example;

import android.app.Application;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.taoweiji.kvstorage.KVStorage;
import com.taoweiji.kvstorage.PreferencesProvider;
import com.taoweiji.kvstorage.mmkv.MMKVPreferencesProvider;
import com.tencent.mmkv.MMKV;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
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

            @Override
            public PreferencesProvider createPreferencesProvider(String fileName) {
                return new MMKVPreferencesProvider(fileName);
            }
        });
        KVStorage.setReadOnlyConfigProvider(readOnlyConfigProvider);
    }


    KVStorage.ReadOnlyConfigProvider readOnlyConfigProvider = new KVStorage.ReadOnlyConfigProvider() {

        @Override
        public int getInt(String groupName, String name, int def) {
            return def;
        }

        @Override
        public float getFloat(String groupName, String name, float def) {
            return def;
        }

        @Override
        public long getLong(String groupName, String name, long def) {
            return def;
        }

        @Override
        public boolean getBool(String groupName, String name, boolean def) {
            return def;
        }

        @Override
        public String getString(String groupName, String name, String def) {
            return def;
        }
    };
}
