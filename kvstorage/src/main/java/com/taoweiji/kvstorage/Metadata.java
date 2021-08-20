package com.taoweiji.kvstorage;

import android.content.SharedPreferences;

public class Metadata {
    protected final String name;
    private final boolean encrypt;
    protected final SharedPreferences preferences;
    protected EncryptMetadata encryptMetadata;

    protected Metadata(SharedPreferences preferences, String name, boolean encrypt) {
        this.preferences = preferences;
        this.name = name;
        this.encrypt = encrypt;
        if (encrypt) {
            encryptMetadata = new EncryptMetadata(preferences, name);
        }
    }

    public void set(int value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.edit().putInt(name, value).apply();
    }

    public void set(long value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.edit().putLong(name, value).apply();
    }

    public void set(float value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.edit().putFloat(name, value).apply();
    }

    public void set(double value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.edit().putFloat(name, (float) value).apply();
    }

    public void set(boolean value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.edit().putBoolean(name, value).apply();
    }

    public void set(String value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.edit().putString(name, value).apply();
    }

    public String getString(String def) {
        if (encrypt) {
            return encryptMetadata.getString(def);
        }
        try {
            return preferences.getString(name, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public int getInt(int def) {
        if (encrypt) {
            return encryptMetadata.getInt(def);
        }
        try {
            return preferences.getInt(name, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public long getLong(long def) {
        if (encrypt) {
            return encryptMetadata.getLong(def);
        }
        try {
            return preferences.getLong(name, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public boolean getBool(boolean def) {
        if (encrypt) {
            return encryptMetadata.getBool(def);
        }
        try {
            return preferences.getBoolean(name, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public float getFloat(float def) {
        if (encrypt) {
            return encryptMetadata.getFloat(def);
        }
        try {
            return preferences.getFloat(name, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }
}
