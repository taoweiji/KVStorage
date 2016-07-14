package cn.taoweiji.aptpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by Wiki on 16/7/15.
 */
public class SettingsPreference2 extends Settings {
    private final SharedPreferences.Editor mEdit;
    private SharedPreferences mPreferences = null;

    private static Map<String, Settings> sMap = new ArrayMap<>();

    public SettingsPreference2(Context context, String name) {
        mPreferences = context.getSharedPreferences(name, 0);
        mEdit = mPreferences.edit();
    }

    public static Settings get(Context context, String name) {
        if (sMap.containsKey(name)) {
            return sMap.get(name);
        }
        synchronized (sMap) {
            if (!sMap.containsKey(name)) {
                SettingsPreference2 sharedPresUtil = new SettingsPreference2(context, name);
                sMap.put(name, sharedPresUtil);
            }
        }
        return sMap.get(name);
    }

    @Override
    public String getFirstUse() {
        return mPreferences.getString("firstUse", super.getFirstUse());
    }

    @Override
    public void setFirstUse(String firstUse) {
        mEdit.putString("firstUse", firstUse).apply();
    }

    @Override
    public String getPush() {
        return mPreferences.getString("push", super.getPush());
    }

    @Override
    public void setPush(String push) {
        // 对这个方法进行测试
        mPreferences.edit().putString("push", push).apply();
    }

    @Override
    public int getLastUseVersion() {
        return mPreferences.getInt("lastUseVersion", super.getLastUseVersion());
    }

    @Override
    public void setLastUseVersion(int lastUseVersion) {
        mEdit.putInt("lastUseVersion", lastUseVersion).apply();
    }
}
