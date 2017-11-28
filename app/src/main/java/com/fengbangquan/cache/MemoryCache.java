package com.fengbangquan.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * created by Feng Bangquan on 17-11-11
 */
public class MemoryCache implements CacheUtils {
    private LruCache<Object, Object> mLruCache;
    public MemoryCache(){
        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        mLruCache = new LruCache<>(maxMemorySize);
    }
    @Override
    public void put(String key, Object value) {
        mLruCache.put(key, value);
    }

    @Override
    public void remove(String key) {
        mLruCache.remove(key);
    }

    @Override
    public void clear() {
        mLruCache.evictAll();
    }

    @Override
    public Object getObject(String key) {
        return mLruCache.get(key);
    }

    @Override
    public Integer getInt(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (Integer) object;
    }

    @Override
    public Long getLong(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (Long) object;
    }

    @Override
    public Double getDouble(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (Double) object;
    }

    @Override
    public Float getFloat(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (Float) object;
    }

    @Override
    public Boolean getBoolean(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (Boolean) object;
    }

    @Override
    public Bitmap getBitmap(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (Bitmap) object;
    }

    @Override
    public String getString(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : String.valueOf(object);
    }

    @Override
    public byte[] getBytes(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (byte[]) object;
    }
}
