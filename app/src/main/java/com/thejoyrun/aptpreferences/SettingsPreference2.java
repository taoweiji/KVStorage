package com.thejoyrun.aptpreferences;

import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

public final class SettingsPreference2 extends Settings {
    public static final Map<String, SettingsPreference2> sMap = new java.util.HashMap<>();
    private final String mName;

    SharedPreferences.Editor mEdit;

    SharedPreferences mPreferences;

    public SettingsPreference2(String name) {
        mPreferences = AptPreferencesManager.getContext().getSharedPreferences("Settings_" + name, 0);
        mEdit = mPreferences.edit();
        this.mName = name;
    }

    @Override
    public float getTmp() {
        return mPreferences.getFloat("tmp", super.getTmp());
    }

    @Override
    public void setTmp(float tmp) {
        mEdit.putFloat("tmp", tmp).apply();
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

    @Override
    public double getPrice() {
        return mPreferences.getFloat("price", (float)super.getPrice());
    }

    @Override
    public void setPrice(double price) {
        mEdit.putFloat("price", (float)price).apply();
    }

    public static SettingsPreference2 get(String name) {
        if (sMap.containsKey(name)) {
            return sMap.get(name);
        }
        synchronized (sMap) {
            if (!sMap.containsKey(name)) {
                SettingsPreference2 preference = new SettingsPreference2(name);
                sMap.put(name, preference);
            }
        }
        return sMap.get(name);
    }

    public static SettingsPreference2 get() {
        return get("");
    }


    public void clear() {
        mEdit.clear().commit();
        sMap.remove(mName);
    }

    public static void clearAll() {
        Set<String> keys = sMap.keySet();
        for (String key : keys){
            sMap.get(key).clear();
        }
    }

}
