package com.taoweiji.kvstorage;

import android.util.Log;
import android.util.LruCache;

public class GroupData {
    final String name;
    private final PreferencesProvider preferences;
    private final LruCache<String, Metadata> children = new LruCache<>(100);
    private final LruCache<String, ListMetadata> listMetadataLruCache = new LruCache<>(100);
    private final LruCache<String, SetMetadata> setMetadataLruCache = new LruCache<>(100);
    private final LruCache<String, ObjectMetadata> objectMetadataLruCache = new LruCache<>(100);

    GroupData(String name, String fileName) {
        this.name = name;
        preferences = KVStorage.createPreferencesAdapter(fileName);
    }

    Metadata get(String key, boolean encrypt) {
        Metadata result = children.get(key);
        if (result == null) {
            result = new Metadata(preferences, key, encrypt);
            children.put(key, result);
        }
        return result;
    }

    <T> ListMetadata<T> getListMetadata(String key, Class<T> type, boolean encrypt) {
        ListMetadata<T> result = listMetadataLruCache.get(key);
        if (result == null) {
            result = new ListMetadata<>(get(key, encrypt), type);
            listMetadataLruCache.put(key, result);
        }
        return result;
    }

    <T> SetMetadata<T> getSetMetadata(String key, Class<T> type, boolean encrypt) {
        SetMetadata<T> result = setMetadataLruCache.get(key);
        if (result == null) {
            result = new SetMetadata<>(get(key, encrypt), type);
            setMetadataLruCache.put(key, result);
        }
        return result;
    }


    ObjectMetadata getObjectMetadata(String key, boolean encrypt) {
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

    public ObjectListMetadata getObjectListMetadata(String key, boolean encrypt) {
        Object result = listMetadataLruCache.get(key);
        if (result instanceof ObjectListMetadata) {
            return (ObjectListMetadata) result;
        }
        ObjectListMetadata objectListMetadata = new ObjectListMetadata(get(key, encrypt));
        listMetadataLruCache.put(key, objectListMetadata);
        return objectListMetadata;
    }
}