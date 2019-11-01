package com.suhi_chintha;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.io.FileOutputStream;

public class Background_Image extends AppCompatActivity {
    public static int RESULT_LOAD_IMAGE = 1;
    public static String imgPath = "none";
    ImageView back;
    Image_Compressing cmp;
    Zoomable_image image;
    ProgressBar pb;
    ImageView save;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.bgimage_actvty);
        image = (Zoomable_image) findViewById(R.id.image);
        back = (ImageView) findViewById(R.id.moveback);
        save = (ImageView) findViewById(R.id.save);
        pb = (ProgressBar) findViewById(R.id.porgress_B);
        cmp = new Image_Compressing();
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        save.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (imgPath.equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Please select an image", Toast.LENGTH_SHORT).show();
                    return;
                }
                pb.setVisibility(View.VISIBLE);
                image.setDrawingCacheEnabled(true);
                image.buildDrawingCache();
                Bitmap cache = image.getDrawingCache();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+File.separator+Static_Variable.foldername+"/bg/bg.png"));
                    cache.compress(CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toasty.info(getApplicationContext(), (CharSequence) "Updated", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toasty.info(getApplicationContext(), (CharSequence) "Unable to update", Toast.LENGTH_SHORT).show();
                } catch (Throwable th) {
                    image.destroyDrawingCache();
                    throw th;
                }
                image.destroyDrawingCache();
                pb. setVisibility(View.GONE);
            }
        });
        new File(Environment.getExternalStorageDirectory()+File.separator+Static_Variable.foldername+"/bg/bg.png");
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
                } catch (Exception e) {
                }
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == -1 && data != null) {
            try {
                Uri selectedImage = data.getData();
                Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
                if (cursor == null) {
                    imgPath = selectedImage.getPath();
                } else {
                    cursor.moveToFirst();
                    imgPath = cursor.getString(cursor.getColumnIndex("_data"));
                }

                String str = imgPath;
                imgPath = cmp.compressImage(str, "/"+Static_Variable.foldername, "bg1.jpg");
                image.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                image.setScaleType(ScaleType.FIT_XY);
                image.setAdjustViewBounds(true);
            } catch (Exception e) {
            }
        }
    }
}
