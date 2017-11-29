package com.fengbangquan.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * created by Feng Bangquan on 17-11-11
 */
public class MemoryCache implements CacheInterface {
    private LruCache<Object, Object> mLruCache;
    public MemoryCache(int maxMemorySize){
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
    public int getInt(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (int) object;
    }

    @Override
    public long getLong(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (long) object;
    }

    @Override
    public double getDouble(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (double) object;
    }

    @Override
    public float getFloat(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (float) object;
    }

    @Override
    public boolean getBoolean(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (boolean) object;
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
