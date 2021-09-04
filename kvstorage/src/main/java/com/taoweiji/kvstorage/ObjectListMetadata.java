package com.taoweiji.kvstorage;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObjectListMetadata extends ListMetadata<JSONObject> {
    ObjectListMetadata(Metadata metadata) {
        super(metadata, JSONObject.class);
    }

    public void addObject(Object element) {
        if (element == null) {
            return;
        }
        try {
            add(new JSONObject(KVStorage.toJSONString(element)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public <P> List<P> convert(Class<P> clazz) {
        return KVStorage.parseArray(metadata.getString("[]"), clazz);
    }

    public JSONArray findByProperty(String propertyName, Object value) {
        JSONArray result = new JSONArray();
        List<JSONObject> data = getData();
        for (int i = 0; i < data.size(); i++) {
            JSONObject item = data.get(i);
            try {
                if (value.equals(item.get(propertyName))) {
                    result.put(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<JSONObject> removeByProperty(String propertyName, Object value) {
        List<JSONObject> result = new ArrayList<>();
        List<JSONObject> data = getData();
        Iterator<JSONObject> iterator = data.iterator();
        while (iterator.hasNext()) {
            JSONObject item = iterator.next();
            try {
                if (value.equals(item.get(propertyName))) {
                    result.add(item);
                    iterator.remove();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        flush();
        return result;
    }

    boolean updateByProperty(JSONObject value) {
        return false;
    }
}
