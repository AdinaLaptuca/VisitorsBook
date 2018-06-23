package com.adinalaptuca.visitorsbook.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

public class ImageUtils {
    public static Bitmap decodeFile(File fileToDecode) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        try {
            FileInputStream fis = new FileInputStream(fileToDecode);

            try {
                BitmapFactory.decodeStream(fis, null, o);
            }
            finally {
                fis.close();
            }

            int scale = 1;
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(fileToDecode);

            try {
                return BitmapFactory.decodeStream(fis, null, o);
            }
            finally {
                fis.close();
            }
        }
        catch (Exception e) {
            Log.e("ImageUtils", "error: " + e.getMessage());
        }

        return null;
    }
}
