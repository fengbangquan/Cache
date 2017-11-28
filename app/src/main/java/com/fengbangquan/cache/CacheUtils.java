package com.fengbangquan.cache;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * created by Feng Bangquan on 17-11-11
 */
public interface CacheUtils {

    void put(String key, Object value);

    void remove(String key) throws IOException;

    void clear() throws IOException;

    Object getObject(String key);

    Integer getInt(String key);

    Long getLong(String key);

    Double getDouble(String key);

    Float getFloat(String key);

    Boolean getBoolean(String key);

    Bitmap getBitmap(String key);

    String getString(String key);

    byte[] getBytes(String key);



}
