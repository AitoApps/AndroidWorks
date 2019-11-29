package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class About_Us extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);
        image=findViewById(R.id.image);
        Glide.with(this)
                .load(R.drawable.aboutimage)
                .into(image);

    }
}
