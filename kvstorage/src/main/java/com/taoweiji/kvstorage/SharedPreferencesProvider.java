package com.taoweiji.kvstorage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesProvider implements PreferencesProvider {
    private final SharedPreferences preferences;

    public SharedPreferencesProvider(String fileName) {
        preferences = KVStorage.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public float getFloat(String key, float def) {
        return preferences.getFloat(key, def);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }

    @Override
    public long getLong(String key, long def) {
        return preferences.getLong(key, def);
    }

    @Override
    public int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    @Override
    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    @Override
    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    @Override
    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public void putFloat(String key, float value) {
        preferences.edit().putFloat(key, (float) value).apply();
    }

    @Override
    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    @Override
    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    @Override
    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    @Override
    public void clear() {
        preferences.edit().clear().apply();
    }
}
