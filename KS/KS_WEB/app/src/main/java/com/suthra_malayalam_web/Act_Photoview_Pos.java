package com.suthra_malayalam_web;

import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Act_Photoview_Pos extends AppCompatActivity {
    ImageView back;
    final DataBase db = new DataBase(this);
    Typeface face;
    DataBase_POS helperDB;
    Zoomable_ImageView image;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvty_imgview);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        } catch (Exception e) {
        }
        image = (Zoomable_ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        back = (ImageView) findViewById(R.id.moveback);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        text.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        helperDB = new DataBase_POS(this, "chithram.sqlite");
        text.setText(File_Positions.positionlist[Static_Veriable.picid]);
        text.setTypeface(face);
        try {
            byte[] decodedString = Base64.decode(helperDB.getpic(Static_Veriable.picid+""), 0);
            image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        } catch (Exception e2) {
        }
    }
}
