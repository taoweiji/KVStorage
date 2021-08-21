package com.taoweiji.kvstorage;

public class Metadata {
    protected final String name;
    private final boolean encrypt;
    protected final PreferencesAdapter preferences;
    protected EncryptMetadata encryptMetadata;

    protected Metadata(PreferencesAdapter preferences, String name, boolean encrypt) {
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
        preferences.putInt(name, value);
    }

    public void set(long value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putLong(name, value);
    }

    public void set(float value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putFloat(name, value);
    }

    public void set(double value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putFloat(name, (float) value);
    }

    public void set(boolean value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putBoolean(name, value);

    }

    public void set(String value) {
        if (encrypt) {
            encryptMetadata.set(value);
            return;
        }
        preferences.putString(name, value);
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
