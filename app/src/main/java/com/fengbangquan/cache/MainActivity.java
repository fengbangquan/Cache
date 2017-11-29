package com.fengbangquan.cache;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        System.out.println("the maxMemory is : " + maxMemory);
        try {
            File file = getDiskCacheDir("disk");
            System.out.println("file is : " + file.getPath());
            if (!file.exists()) {
                file.mkdirs();
            }
            Cache.open(file, 1, 1, 100*1024*1024/*100MB*/, (maxMemory / 8));
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
                long start = System.currentTimeMillis();
                Cache.putBitmap("bitmap", bitmap);
                System.out.println("time is : " + (System.currentTimeMillis() - start));
                byte[] bytes = {10,20,30,40,50};
                Cache.putBytes("byte", bytes);
                Cache.putInt("int", 1200);
                Cache.putString("string", "read the source code");
                Cache.putBoolean("boolean", true);
            }
        }).start();
    }

    private void getCache() {
        Bitmap bitmap = Cache.getBitmap("bitmap");
        ((ImageView) findViewById(R.id.image_view)).setImageBitmap(bitmap);

        System.out.println("bitmap size is : " + bitmap.getByteCount() / 1024);
        byte[] bytes = Cache.getBytes("byte");
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i]);
        }
        System.out.println(Cache.getInt("int"));
        System.out.println(Cache.getString("string"));
        System.out.println(Cache.getBoolean("boolean"));
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
                Cache.remove("int");
                Cache.remove("string");
                Cache.remove("boolean");
                Cache.remove("bitmap");
                Cache.remove("byte");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
