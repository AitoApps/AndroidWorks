package com.daydeal_admin;

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

public class Image_Viewer extends AppCompatActivity {
    ImageView back;
    Typeface face1;
    ImageView heart;
    TouchImageView image;
    TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_image__viewer);
        face1 = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        image = (TouchImageView) findViewById(R.id.image);
        heart = (ImageView) findViewById(R.id.heart);
        text.setText(Temp.img_title);
        text.setTypeface(face1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                heart.setVisibility(View.VISIBLE);
                image.setVisibility(View.INVISIBLE);
                Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.img_imgsig))).load(Temp.img_link).into(new SimpleTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        heart.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        image.setImageBitmap(bitmap);
                        image.setZoom(1.0f);
                    }

                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        heart.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        image.setImageResource(R.drawable.nophoto);
                    }
                });
            }
        });
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
