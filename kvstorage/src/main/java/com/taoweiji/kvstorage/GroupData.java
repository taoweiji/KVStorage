package com.taoweiji.kvstorage;

import android.util.Log;
import android.util.LruCache;

public class GroupData {
    private final PreferencesAdapter preferences;
    final String name;
    private final LruCache<String, Metadata> children = new LruCache<>(100);
    private final LruCache<String, ListMetadata> listMetadataLruCache = new LruCache<>(100);
    private final LruCache<String, ObjectMetadata> objectMetadataLruCache = new LruCache<>(100);
    private final String fileName;

    GroupData(String name,String fileName) {
        this.name = name;
        this.fileName = fileName;
        preferences = KVStorage.createPreferencesAdapter(fileName);
    }

    protected Metadata get(String key, boolean encrypt) {
        Metadata result = children.get(key);
        if (result == null) {
            result = new Metadata(preferences, key, encrypt);
            children.put(key, result);
        }
        return result;
    }

    public <T> ListMetadata<T> getListMetadata(String key, Class<T> type, boolean encrypt) {
        ListMetadata<T> result = listMetadataLruCache.get(key);
        if (result == null) {
            result = new ListMetadata<>(get(key, encrypt), type);
            listMetadataLruCache.put(key, result);
        }
        return result;
    }

    public ObjectMetadata getObjectMetadata(String key, boolean encrypt) {
        ObjectMetadata result = objectMetadataLruCache.get(key);
        if (result == null) {
            result = new ObjectMetadata(get(key, encrypt));
            objectMetadataLruCache.put(key, result);
        }
        return result;
    }

    public void clear() {
        Log.e("KVStorage", name + " clear");
        preferences.clear();
    }
}