package com.taoweiji.kvstorage;

public interface PreferencesProvider {

    float getFloat(String key, float def);

    boolean getBoolean(String key, boolean def);

    long getLong(String key, long def);

    int getInt(String key, int def);

    String getString(String key, String def);

    void putString(String key, String value);

    void putBoolean(String key, boolean value);

    void putFloat(String key, float value);

    void putLong(String key, long value);

    void putInt(String key, int value);

    void remove(String key);

    void clear();
}
