package com.taoweiji.kvstorage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class KVStorage {
    static Application sContext;
    static LruCache<String, GroupData> groupDataMap = new LruCache<>(10);
    static Interceptor sInterceptor;

    public static GroupData getRootGroupData(String name) {
        return getGroupData(name);
    }

    static GroupData getGroupData(String name) {
        String fileName = sInterceptor.customFileName(name);
        GroupData groupData = groupDataMap.get(fileName);
        if (groupData == null) {
            groupData = new GroupData(name, fileName);
            groupDataMap.put(fileName, groupData);
        }
        return groupData;
    }


    public static void flush() {

    }

    static Application getContext() {
        return sContext;
    }

    static <T> T parseObject(String text, Class<T> clazz) {
        return sInterceptor.parseObject(text, clazz);
    }

    static <T> List<T> parseArray(String text, Class<T> clazz) {
        return sInterceptor.parseArray(text, clazz);
    }

    static String toJSONString(Object object) {
        return sInterceptor.toJSONString(object);
    }

    static String encryption(String data) {
        return sInterceptor.encryption(data);
    }

    static String decryption(String data) {
        return sInterceptor.decryption(data);
    }

    public static void init(Context context) {
        init(context, null);
    }

    public static void init(Context context, Interceptor interceptor) {
        if (interceptor != null) {
            sInterceptor = interceptor;
        }
        if (sContext != null) {
            return;
        }
        sContext = (Application) context.getApplicationContext();
        sContext.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                // 自动保存
                KVStorage.flush();
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    static PreferencesAdapter createPreferencesAdapter(String fileName) {
        return sInterceptor.createPreferencesAdapter(fileName);
    }


    public interface Interceptor {

        default String encryption(@NonNull String data) {
            return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
        }

        default String decryption(@NonNull String data) {
            return new String(Base64.decode(data, Base64.DEFAULT));
        }

        /**
         * 可以实现多用户隔离数据
         */
        @NonNull
        default String customFileName(@NonNull String name) {
            // return "userId_" + name;
            return name;
        }

        default <T> T parseObject(@NonNull String text, Class<T> clazz) {
            return JSON.parseObject(text, clazz);
        }

        default <T> List<T> parseArray(@NonNull String text, Class<T> clazz) {
            return JSON.parseArray(text, clazz);
        }

        default String toJSONString(@NonNull Object object) {
            return JSON.toJSONString(object);
        }

        default PreferencesAdapter createPreferencesAdapter(String fileName) {
            return new SharedPreferencesAdapter(fileName);
        }
    }
}
