package com.down_mate;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class About extends AppCompatActivity {
    ImageView back;
    TextView text, developer,copyright,developed, mobile;
    Typeface face;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        back=(ImageView)findViewById(R.id.back);
        layout =(RelativeLayout)findViewById(R.id.contactlayout);
        developed=findViewById(R.id.developed);
        copyright=(TextView)findViewById(R.id.copyright);
        developer =(TextView)findViewById(R.id.develpdby);
        mobile =findViewById(R.id.mobile1);
        text=(TextView)findViewById(R.id.text);
        face= Typeface.createFromAsset(getAssets(), "font/fonts.otf");
        text.setText("DownMate");
        text.setTypeface(face);
        developer.setText("Sayed Salman Ponnani");
        developed.setTypeface(face);
        mobile.setTypeface(face);
        copyright.setTypeface(face);
        developer.setTypeface(face);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });




    }


}


