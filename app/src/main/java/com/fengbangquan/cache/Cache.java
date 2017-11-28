package com.fengbangquan.cache;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

/**
 * created by Feng Bangquan on 17-11-11
 */
public class Cache {
    private static MemoryCache mMemoryCache;
    private static DiskCache mDiskCache;

    /**
     * Opens the cache in {@code directory}, creating a cache if none exists
     * there.
     *
     * @param directory a writable directory
     * @param appVersion the value must be positive
     * @param valueCount the number of values per cache entry. Must be positive.
     * @param maxDiskSize the maximum number of bytes this cache should use to store
     * @throws IOException if reading or writing the cache directory fails
     */
    public static void open(File directory, int appVersion, int valueCount, long maxDiskSize) throws IOException {
        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache(directory, appVersion, valueCount, maxDiskSize);
    }

    /**
     * Removes the entry for {@code key} if it exists.
     * @throws IOException
     */
    public static void remove(String key) throws IOException {
        mMemoryCache.remove(key);
        mDiskCache.remove(key);
    }

    /**
     * Closes the cache and deletes all of its stored values. This will delete
     * all files in the diskCache directory including files that weren't created by
     * the cache.
     * @throws IOException
     */
    public static void clear() throws IOException {
        mMemoryCache.clear();
        mDiskCache.clear();
    }

    /**
     * @param key the value of key can not contain (" ") , ("\n"), ("\r")
     * @param value it does not support generic types
     */
    public static void put(String key, Object value) {
        mMemoryCache.put(key, value);
        mDiskCache.put(key, value);
    }

    public static void putString(String key, String value){
        put(key, value);
    }

    public static void putInt(String key, int value) {
        put(key, value);
    }

    public static void putFloat(String key, float value) {
        put(key, value);
    }

    public static void putDouble(String key, double value){
        put(key, value);
    }

    public static void putBytes(String key, byte[] value) {
        put(key, value);
    }

    public static void putBoolean(String key, boolean value) {
        put(key, value);
    }

    public static void putBitmap(String key, Bitmap value) {
        put(key, value);
    }

    public static Object getObject(String key) {
        if (mMemoryCache.getObject(key) != null) {
            return mMemoryCache.getObject(key);
        } else {
            return mDiskCache.getObject(key);
        }
    }

    public static Integer getInt(String key) {
        if (mMemoryCache.getObject(key) != null) {
            return mMemoryCache.getInt(key);
        } else {
            return mDiskCache.getInt(key);
        }
    }


    public static Long getLong(String key) {
        if (mMemoryCache.getObject(key) != null) {
            return mMemoryCache.getLong(key);
        } else {
            return mDiskCache.getLong(key);
        }
    }


    public static Double getDouble(String key) {
        if (mMemoryCache.getDouble(key) != null) {
            return mMemoryCache.getDouble(key);
        } else {
            return mDiskCache.getDouble(key);
        }
    }


    public static Float getFloat(String key) {
        if (mMemoryCache.getFloat(key) != null) {
            return mMemoryCache.getFloat(key);
        } else {
            return mDiskCache.getFloat(key);
        }
    }


    public static Boolean getBoolean(String key) {
        if (mMemoryCache.getBoolean(key) != null) {
            return mMemoryCache.getBoolean(key);
        } else {
            return mDiskCache.getBoolean(key);
        }
    }

    public static Bitmap getBitmap(String key) {
        Bitmap bitmap = mMemoryCache.getBitmap(key);
        if (bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        } else {
            return mDiskCache.getBitmap(key);
        }
    }


    public static String getString(String key) {
        if (mMemoryCache.getString(key) != null) {
            return mMemoryCache.getString(key);
        } else {
            return mDiskCache.getString(key);
        }
    }


    public byte[] getBytes(String key) {
        if (mMemoryCache.getBytes(key) != null) {
            return mMemoryCache.getBytes(key);
        } else {
            return mDiskCache.getBytes(key);
        }
    }
}
