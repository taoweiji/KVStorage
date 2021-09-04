package com.taoweiji.kvstorage;

public class ReadOnlyMetadata {
    private final String groupName;
    private final String key;

    public ReadOnlyMetadata(String groupName, String key) {
        this.groupName = groupName;
        this.key = key;
    }

    public String getString(String def) {
        return KVStorage.getReadOnlyConfigProvider().getString(groupName, key, def);
    }

    public int getInt(int def) {
        return KVStorage.getReadOnlyConfigProvider().getInt(groupName, key, def);
    }

    public long getLong(long def) {
        return KVStorage.getReadOnlyConfigProvider().getLong(groupName, key, def);
    }

    public float getFloat(float def) {
        return KVStorage.getReadOnlyConfigProvider().getFloat(groupName, key, def);
    }

    public boolean getBool(boolean def) {
        return KVStorage.getReadOnlyConfigProvider().getBool(groupName, key, def);
    }
}
