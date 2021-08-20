package com.taoweiji.kvstorage;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListMetadata<T> {

    private final Metadata metadata;
    private final Class<T> type;
    private List<T> data;

    ListMetadata(Metadata metadata, Class<T> type) {
        this.metadata = metadata;
        this.type = type;
        String text = metadata.getString(null);
        data = new ArrayList<>();
        if (!TextUtils.isEmpty(text)) {
            try {
                JSONArray array = new JSONArray(text);
                for (int i = 0; i < array.length(); i++) {
                    if (type == Integer.class) {
                        data.add((T) Integer.valueOf(array.getInt(i)));
                    } else if (type == Long.class) {
                        data.add((T) Long.valueOf(array.getLong(i)));
                    } else if (type == Float.class) {
                        data.add((T) Float.valueOf((float) array.getDouble(i)));
                    } else if (type == Boolean.class) {
                        data.add((T) Boolean.valueOf(array.getBoolean(i)));
                    } else if (type == String.class) {
                        data.add((T) array.getString(i));
                    } else if (type == JSONObject.class) {
                        data.add((T) array.getJSONObject(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        flush();
    }

    //    public JSONArray getRawData() {
//        return jsonArray;
//    }
//
//    public void setRawData(JSONArray jsonArray) {
//        this.jsonArray = jsonArray;
//    }
    public void flush() {
        if (type == JSONObject.class) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < getData().size(); i++) {
                JSONObject item = (JSONObject) getData().get(i);
                array.put(item);
            }
            metadata.set(array.toString());
        } else {
            metadata.set(KVStorage.toJSONString(getData()));
        }
    }

    public int size() {
        return getData().size();
    }

    public boolean isEmpty() {
        return getData().isEmpty();
    }

    public boolean contains(T value) {
        return getData().contains(value);
    }

    public Object[] toArray() {
        return getData().toArray();
    }

    public boolean add(T value) {
        boolean res = getData().add(value);
        flush();
        return res;
    }

    public boolean remove(T value) {
        return getData().remove(value);
    }

    public boolean addAll(Collection<T> c) {
        boolean res = getData().addAll(c);
        flush();
        return res;
    }

    public boolean addAll(int index, Collection<T> c) {
        boolean res = getData().addAll(index, c);
        flush();
        return res;
    }

    public void clear() {
        getData().clear();
        flush();
    }

    public T get(int index) {
        return getData().get(index);
    }

    public T set(int index, T element) {
        T res = getData().set(index, element);
        flush();
        return res;
    }

    public void add(int index, T element) {
        getData().add(index, element);
        flush();
    }

    public T remove(int index) {
        T res = getData().remove(index);
        flush();
        return res;
    }

    @Nullable
    public <P> List<P> convert(Class<P> clazz) {
        return KVStorage.parseArray(metadata.getString("[]"), clazz);
    }
}