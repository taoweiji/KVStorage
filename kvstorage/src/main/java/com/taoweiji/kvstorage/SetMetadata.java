package com.taoweiji.kvstorage;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class SetMetadata<T> {

    final Metadata metadata;
    private final Class<T> type;
    private Set<T> data;

    SetMetadata(Metadata metadata, Class<T> type) {
        this.metadata = metadata;
        this.type = type;
        String text = metadata.getString(null);
        data = new HashSet<>();
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

    public Set<T> getData() {
        if (data == null) {
            data = new HashSet<>();
        }
        return data;
    }

    public void setData(Set<T> data) {
        this.data = data;
        flush();
    }

    public void flush() {
        if (type == JSONObject.class) {
            JSONArray array = new JSONArray();
            Iterator<JSONObject> it = (Iterator<JSONObject>) getData().iterator();
            while (it.hasNext()) {
                array.put(it.next());
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
        boolean res = getData().remove(value);
        flush();
        return res;
    }


    public void clear() {
        getData().clear();
        flush();
    }

    @NonNull
    public Iterator<T> iterator() {
        return getData().iterator();
    }

    @NonNull
    public <T1> T1[] toArray(@NonNull T1[] a) {
        return getData().toArray(a);
    }

    public boolean containsAll(@NonNull Collection<T> c) {
        return getData().containsAll(c);
    }

    public boolean addAll(@NonNull Collection<? extends T> c) {
        boolean res = getData().addAll(c);
        flush();
        return res;
    }

    public boolean removeAll(@NonNull Collection<T> c) {
        boolean res = getData().removeAll(c);
        flush();
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void forEach(@NonNull Consumer<? super T> action) {
        getData().forEach(action);
    }
}