package com.fengbangquan.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * A demo activity to guide how to use Cache to store data
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String KEY_INT = "int";
    private static final String KEY_STRING = "string";
    private static final String KEY_BOOLEAN  = "boolean";
    private static final String KEY_BITMAP = "bitmap";
    private static final String KEY_BYTES = "bytes";
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 10; // 100MB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        try {
            File cacheDir = getDiskCacheDir("diskCache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            Cache.open(cacheDir, 1, 1, DISK_CACHE_SIZE, (maxMemorySize / 8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        (findViewById(R.id.put)).setOnClickListener(this);
        (findViewById(R.id.get)).setOnClickListener(this);
        (findViewById(R.id.remove)).setOnClickListener(this);
        (findViewById(R.id.clear)).setOnClickListener(this);
    }

    public File getDiskCacheDir(String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = getExternalCacheDir().getPath();
        } else {
            cachePath = getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private void putCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.picture, options);
                Cache.putBitmap(KEY_BITMAP, bitmap);
                byte[] bytes = {1, 2, 3, 4, 5};
                Cache.putBytes(KEY_BYTES, bytes);
                Cache.putInt(KEY_INT, 1024);
                Cache.putString(KEY_STRING, "read the source code");
                Cache.putBoolean(KEY_BOOLEAN, true);
            }
        }).start();
    }

    private void getCache() {
        Bitmap bitmap = Cache.getBitmap(KEY_BITMAP);
        ((ImageView) findViewById(R.id.image_view)).setImageBitmap(bitmap);
        byte[] bytes = Cache.getBytes(KEY_BYTES);
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
        System.out.println(Cache.getInt(KEY_INT));
        System.out.println(Cache.getString(KEY_STRING));
        System.out.println(Cache.getBoolean(KEY_BOOLEAN));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.put) {
            putCache();
        }
        if (v.getId() == R.id.get) {
            getCache();
        }
        if (v.getId() == R.id.clear) {
            try {
                Cache.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == R.id.remove) {
            try {
                Cache.remove(KEY_INT);
                Cache.remove(KEY_BYTES);
                Cache.remove(KEY_BOOLEAN);
                Cache.remove(KEY_BITMAP);
                Cache.remove(KEY_BYTES);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
