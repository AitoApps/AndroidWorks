package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Advertisments extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisments);
        image=findViewById(R.id.image);
        Glide.with(this)
                .load(R.drawable.adsimage)
                .into(image);
    }
}
