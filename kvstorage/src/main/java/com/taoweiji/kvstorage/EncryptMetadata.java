package com.taoweiji.kvstorage;

import android.content.SharedPreferences;
import android.text.TextUtils;

public class EncryptMetadata extends Metadata {

    protected EncryptMetadata(SharedPreferences preferences, String name) {
        super(preferences, name, false);
    }

    private void setEncryptData(Object value) {
        if (value == null) {
            preferences.edit().remove(name).apply();
            return;
        }
        String data = value.toString();
        String after = KVStorage.encryption(data);
        preferences.edit().putString(name, after).apply();
    }

    private String getDecryption() {
        try {
            String data = preferences.getString(name, null);
            if (TextUtils.isEmpty(data)) return null;
            return KVStorage.decryption(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set(int value) {
        setEncryptData(value);
    }

    @Override
    public void set(long value) {
        setEncryptData(value);
    }

    @Override
    public void set(float value) {
        setEncryptData(value);
    }

    @Override
    public void set(double value) {
        setEncryptData(value);
    }

    @Override
    public void set(boolean value) {
        setEncryptData(value);
    }

    @Override
    public void set(String value) {
        setEncryptData(value);
    }

    @Override
    public String getString(String def) {
        String res = getDecryption();
        if (res == null) {
            return def;
        } else {
            return res;
        }
    }

    @Override
    public int getInt(int def) {
        String res = getDecryption();
        if (res == null) {
            return def;
        }
        try {
            return Integer.parseInt(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    @Override
    public long getLong(long def) {
        String res = getDecryption();
        if (res == null) {
            return def;
        }
        try {
            return Long.parseLong(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    @Override
    public boolean getBool(boolean def) {
        String res = getDecryption();
        if (res == null) {
            return def;
        }
        try {
            return Boolean.parseBoolean(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    @Override
    public float getFloat(float def) {
        String res = getDecryption();
        if (res == null) {
            return def;
        }
        try {
            return Float.parseFloat(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }
}
