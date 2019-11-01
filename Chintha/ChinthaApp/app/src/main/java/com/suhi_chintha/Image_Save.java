package com.suhi_chintha;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Image_Save {
    public boolean Imagesave(View view, Context cntx) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        Canvas c = new Canvas(bmp);
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(File.separator);
            sb.append(Static_Variable.folder_img);
            sb.append("/pic_");
            sb.append(timeStamp);
            sb.append(".png");
            File f = new File(sb.toString());
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            c.drawBitmap(bmp, 0.0f, 0.0f, null);
            bmp.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            ContentValues values = new ContentValues();
            values.put("_data", f.getAbsolutePath());
            values.put("mime_type", "image/jpg");
            cntx.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
        }
    }
}
