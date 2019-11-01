package com.sanji;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;

public class Image_Viewer_Single extends AppCompatActivity {
    ImageView back;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face1;
    ImageView heart;
    TouchImageView image;
    TextView text;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_image__viewer__single);
        face1 = Typeface.createFromAsset(getAssets(), "font/heading.otf");
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        image = (TouchImageView) findViewById(R.id.image);
        heart = (ImageView) findViewById(R.id.heart);
        text.setText(Temp.img_title);
        text.setTypeface(face1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Image_Viewer_Single.heart.setVisibility(View.VISIBLE);
                Image_Viewer_Single.image.setVisibility(View.INVISIBLE)
                Glide.with(Image_Viewer_Single.getApplicationContext()).asBitmap().load(Temp.img_link).apply(new RequestOptions().signature(new ObjectKey(Temp.imgsig))).into(new SimpleTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        Image_Viewer_Single.heart.setVisibility(View.GONE);
                        Image_Viewer_Single.image.setVisibility(View.VISIBLE);
                        Image_Viewer_Single.image.setImageBitmap(bitmap);
                        Image_Viewer_Single.image.setZoom(1.0f);
                    }

                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Image_Viewer_Single.heart.setVisibility(View.GONE);
                        Image_Viewer_Single.image.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Image_Viewer_Single.onBackPressed();
            }
        });
    }
}
