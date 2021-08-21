package com.taoweiji.kvstorage.mmkv;

import com.taoweiji.kvstorage.PreferencesAdapter;
import com.tencent.mmkv.MMKV;

public class MMKVPreferencesAdapter implements PreferencesAdapter {
    MMKV preferences;

    public MMKVPreferencesAdapter(String fileName) {
        preferences = MMKV.mmkvWithID(fileName);
    }

    @Override
    public float getFloat(String name, float def) {
        return preferences.getFloat(name, def);
    }

    @Override
    public boolean getBoolean(String name, boolean def) {
        return preferences.getBoolean(name, def);
    }

    @Override
    public long getLong(String name, long def) {
        return preferences.getLong(name, def);
    }

    @Override
    public int getInt(String name, int def) {
        return preferences.getInt(name, def);
    }

    @Override
    public String getString(String name, String def) {
        return preferences.getString(name, def);
    }

    @Override
    public void putString(String name, String value) {
        preferences.edit().putString(name, value).apply();
    }

    @Override
    public void putBoolean(String name, boolean value) {
        preferences.edit().putBoolean(name, value).apply();
    }

    @Override
    public void putFloat(String name, float value) {
        preferences.edit().putFloat(name, (float) value).apply();
    }

    @Override
    public void putLong(String name, long value) {
        preferences.edit().putLong(name, value).apply();
    }

    @Override
    public void putInt(String name, int value) {
        preferences.edit().putInt(name, value).apply();
    }

    @Override
    public void remove(String name) {
        preferences.edit().remove(name).apply();
    }

    @Override
    public void clear() {
        preferences.edit().clear().apply();
    }
}
