package com.taoweiji.aptpreferences;

/**
 * Created by Wiki on 16/7/16.
 */
public interface AptParser {
    Object deserialize(Class clazz, String text);

    String serialize(Object object);
}
