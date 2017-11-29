package com.fengbangquan.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * created by Feng Bangquan on 17-11-11
 */
public class DiskCache implements CacheInterface {

    private DiskLruCache mDiskLruCache;
    public DiskCache(File directory, int appVersion, int valueCount, long maxSize) throws IOException {
            mDiskLruCache = DiskLruCache.open(directory, appVersion, valueCount, maxSize);
    }
    @Override
    public void put(String key, Object value) {
        try {
            DiskLruCache.Editor editor  = mDiskLruCache.edit(key);
            OutputStream outputStream = editor.newOutputStream(0);
            ObjectOutputStream objectOutputStream;
            if (value instanceof Bitmap) {
                outputStream.write(bitmapToBytes((Bitmap)value));
            } else if (value instanceof byte[]) {
                outputStream.write((byte[]) value);
            } else {
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(value);
                objectOutputStream.close();
            }
            outputStream.close();
            editor.commit();
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String key) throws IOException {
        mDiskLruCache.remove(key);
    }

    @Override
    public void clear() throws IOException {
        mDiskLruCache.delete();
    }

    @Override
    public Object getObject(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                ObjectInputStream objectInputStream = new ObjectInputStream(snapshot.getInputStream(0));
                return objectInputStream.readObject();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getInt(String key) {
        Object object = getObject(key);
        return object == null ? null : (Integer) object;
    }

    @Override
    public long getLong(String key) {
        Object object = getObject(key);
        return object == null ? null : (Long) object;
    }

    @Override
    public double getDouble(String key) {
        Object object = getObject(key);
        return object == null ? null : (Double) object;
    }

    @Override
    public float getFloat(String key) {
        Object object = getObject(key);
        return object == null ? null : (Float) object;
    }

    @Override
    public boolean getBoolean(String key) {
        Object object = getObject(key);
        return object == null ? null : (Boolean) object;
    }

    @Override
    public Bitmap getBitmap(String key) {
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            InputStream inputStream = snapShot.getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap == null ? null : bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getString(String key) {
        Object object = getObject(key);
        return object == null ? null : (String)object;
    }

    @Override
    public byte[] getBytes(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            return snapshot == null ? null : streamToBytes(snapshot.getInputStream(0));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        try {
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            if (!bitmap.isRecycled()){
                bitmap.recycle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] streamToBytes(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int length;
        byte[] bytes = new byte[1024];
        try {
            while ((length = in.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return out.toByteArray();
    }
}
