package com.taoweiji.kvstorage;

public class Metadata {
    protected final String key;
    private final boolean encrypt;
    protected final PreferencesProvider preferences;
    protected EncryptMetadata encryptMetadata;

    protected Metadata(PreferencesProvider preferences, String key, boolean encrypt) {
        this.preferences = preferences;
        this.key = key;
        this.encrypt = encrypt;
        if (encrypt) {
            encryptMetadata = new EncryptMetadata(preferences, key);
        }
    }

    public void set(int value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putInt(key, value);
    }

    public void set(long value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putLong(key, value);
    }

    public void set(float value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putFloat(key, value);
    }

    public void set(double value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putFloat(key, (float) value);
    }

    public void set(boolean value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putBoolean(key, value);

    }

    public void set(String value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putString(key, value);
    }

    public String getString(String def) {
        if (encrypt) {
            return encryptMetadata.getString(def);
        }
        try {
            return preferences.getString(key, def);
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
            return preferences.getInt(key, def);
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
            return preferences.getLong(key, def);
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
            return preferences.getBoolean(key, def);
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
            return preferences.getFloat(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }
}
