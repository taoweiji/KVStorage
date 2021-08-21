package com.taoweiji.kvstorage;

public interface PreferencesAdapter {

    float getFloat(String name, float def);

    boolean getBoolean(String name, boolean def);

    long getLong(String name, long def);

    int getInt(String name, int def);

    String getString(String name, String def);

    void putString(String name, String value);

    void putBoolean(String name, boolean value);

    void putFloat(String name, float value);

    void putLong(String name, long value);

    void putInt(String name, int value);

    void remove(String name);

    void clear();
}
