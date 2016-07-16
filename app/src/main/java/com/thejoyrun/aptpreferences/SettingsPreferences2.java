package com.thejoyrun.aptpreferences;

import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

public final class SettingsPreferences2 extends Settings {
    public static final Map<String, SettingsPreferences2> sMap = new java.util.HashMap<>();
    private final String mName;

    SharedPreferences.Editor mEdit;

    SharedPreferences mPreferences;

    public SettingsPreferences2(String name) {
        mPreferences = AptPreferencesManager.getContext().getSharedPreferences("Settings_" + name, 0);
        mEdit = mPreferences.edit();
        this.mName = name;
        this.setRun(new RunPreferences());
    }

    @Override
    public LoginUser getLoginUser() {
        String text = mPreferences.getString("loginUser", null);
        Object object = null;
        if (text != null){
            object = AptPreferencesManager.getAptParser().deserialize(LoginUser.class,text);
        }
        if (object != null){
            return (LoginUser) object;
        }
        return super.getLoginUser();
    }

    @Override
    public void setLoginUser(LoginUser loginUser) {
        mEdit.putString("loginUser", AptPreferencesManager.getAptParser().serialize(loginUser)).apply();
    }

    @Override
    public void setRun(Run run) {
        super.setRun(run);
    }

    @Override
    public Run getRun() {
        return super.getRun();
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
        return mPreferences.getFloat("price", (float) super.getPrice());
    }

    @Override
    public void setPrice(double price) {
        mEdit.putFloat("price", (float) price).apply();
    }

    public static SettingsPreferences2 get(String name) {
        if (sMap.containsKey(name)) {
            return sMap.get(name);
        }
        synchronized (sMap) {
            if (!sMap.containsKey(name)) {
                SettingsPreferences2 preference = new SettingsPreferences2(name);
                sMap.put(name, preference);
            }
        }
        return sMap.get(name);
    }

    public static SettingsPreferences2 get() {
        return get("");
    }


    public void clear() {
        mEdit.clear().commit();
        sMap.remove(mName);
    }

    public static void clearAll() {
        Set<String> keys = sMap.keySet();
        for (String key : keys) {
            sMap.get(key).clear();
        }
    }

    private class RunPreferences extends Run {

        @Override
        public boolean isAutoPause() {
            return mPreferences.getBoolean("run.autoPause", super.isAutoPause());
        }

        @Override
        public void setAutoPause(boolean autoPause) {
            mEdit.putBoolean("run.autoPause", autoPause).apply();
        }

        @Override
        public boolean isOpenVoice() {
            return mPreferences.getBoolean("run.openVoice", super.isOpenVoice());
        }

        @Override
        public void setOpenVoice(boolean openVoice) {
            mEdit.putBoolean("run.openVoice", openVoice).apply();
        }

        @Override
        public String getVoiceName() {
            return mPreferences.getString("run.voiceName", super.getVoiceName());
        }

        @Override
        public void setVoiceName(String voiceName) {
            mEdit.putString("run.voiceName", voiceName).apply();
        }
    }

}
