package com.thejoyrun.aptpreferences;

import android.content.SharedPreferences;

import java.util.Map;

public final class SettingsPreference2 extends Settings {
    public static final Map sMap = new java.util.HashMap<String, Settings>();

    SharedPreferences.Editor mEdit;

    SharedPreferences mPreferences;

    public SettingsPreference2(String name) {
        mPreferences = AptPreferencesManager.getContext().getSharedPreferences("Settings_" + name, 0);
        mEdit = mPreferences.edit();
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
        mEdit.putString("push", push).apply();
    }

    @Override
    public int getLastUseVersion() {
        return mPreferences.getInt("lastUseVersion", super.getLastUseVersion());
    }

    @Override
    public void setLastUseVersion(int lastUseVersion) {
        mEdit.putInt("lastUseVersion", lastUseVersion).apply();
    }

    @Override
    public long getTime() {
        return mPreferences.getLong("time", super.getTime());
    }

    @Override
    public void setTime(long time) {
        mEdit.putLong("time", time).apply();
    }

    @Override
    public boolean isLogin() {
        return mPreferences.getBoolean("login", super.isLogin());
    }

    @Override
    public void setLogin(boolean login) {
        mEdit.putBoolean("login", login).apply();
    }

    @Override
    public float getPrice2() {
        return mPreferences.getFloat("price2", super.getPrice2());
    }

    @Override
    public void setPrice2(float price2) {
        mEdit.putFloat("price2", price2).apply();
    }

    public static Settings get(String name) {
        if (sMap.containsKey(name)) {
            return (SettingsPreference2) sMap.get(name);
        }
        synchronized (sMap) {
            if (!sMap.containsKey(name)) {
                SettingsPreference2 preference = new SettingsPreference2(name);
                sMap.put(name, preference);
            }
        }
        return (SettingsPreference2) sMap.get(name);
    }

    public static Settings get() {
        return get("");
    }

    /**
     * 设置整块内容,通常用于从网络请求到的json然后设置进来
     */
    public void set(Settings settings) {
        setFirstUse(settings.getFirstUse());
        setLastUseVersion(settings.getLastUseVersion());
        setLogin(settings.isLogin());
    }

}
