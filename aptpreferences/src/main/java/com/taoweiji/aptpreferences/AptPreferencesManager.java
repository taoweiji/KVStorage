package com.taoweiji.aptpreferences;

import android.content.Context;

import androidx.annotation.Keep;

public final class AptPreferencesManager {
    private static Context sContext;

    private static AptParser sAptParser;

    private static String sGroupId;

    public static void init(Context context, AptParser aptParser) {
        sContext = context.getApplicationContext();
        sAptParser = aptParser;
    }

    public static Context getContext() {
        return sContext;
    }

    public static AptParser getAptParser() {
        return sAptParser;
    }

    /**
     * 用于实现不同登录的用户使用不同的存储，避免混淆
     */
    public static void setGroupId(String groupId) {
        sGroupId = groupId;
    }

    @Keep
    public static String getGroupId() {
        return sGroupId;
    }

}
