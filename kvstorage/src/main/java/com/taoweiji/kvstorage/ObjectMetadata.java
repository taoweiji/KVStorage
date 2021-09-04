package com.taoweiji.kvstorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 如果操作 json 修改字段，框架会自动检查内容是否发生了改变，检查Activity的变化情况自动保存
 */
public class ObjectMetadata {

    private final Metadata metadata;
    private JSONObject json;

    ObjectMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public boolean exists() {
        return getData().length() > 0;
    }

    @NonNull
    public JSONObject getData() {
        if (json == null) {
            String str = metadata.getString("{}");
            try {
                json = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
                json = new JSONObject();
            }
        }
        return json;
    }

    public void setData(JSONObject json) {
        this.json = json;
        flush();
    }

    public void setObject(Object object) {
        json = null;
        metadata.set(KVStorage.toJSONString(object));
    }

    public void flush() {
        metadata.set(getData().toString());
    }

    public void clear() {
        json = null;
        flush();
    }


    @NonNull
    public ObjectMetadata set(@NonNull String name, Object value) {
        try {
            getData().putOpt(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        flush();
        return this;
    }

    @Nullable
    public Object remove(@Nullable String name) {
        return getData().remove(name);
    }

    public int getInt(@NonNull String name, int def) {
        return getData().optInt(name, def);
    }

    public long getLong(@NonNull String name, long def) {
        return getData().optLong(name, def);
    }

    public double getDouble(@NonNull String name, double def) {
        return getData().optDouble(name, def);
    }

    public boolean getBool(@NonNull String name, boolean def) {
        return getData().optBoolean(name, def);
    }

    public String getString(@NonNull String name, String def) {
        return getData().optString(name, def);
    }

    @Nullable
    public JSONArray getJSONArray(@NonNull String name) {
        return getData().optJSONArray(name);
    }

    @Nullable
    public JSONObject getJSONObject(@NonNull String name) {
        return getData().optJSONObject(name);
    }

    @Nullable
    public <T> T convert(Class<T> clazz) {
        if (!exists()) {
            return null;
        }
        return KVStorage.parseObject(getData().toString(), clazz);
    }
}