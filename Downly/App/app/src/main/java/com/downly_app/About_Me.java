package com.downly_app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About_Me extends AppCompatActivity {
    ImageView back;
    TextView contact1;
    TextView copyright;
    TextView developed;
    TextView dvlpdby;
    Typeface face;
    RelativeLayout lytcontact;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_about_me);
        back = (ImageView) findViewById(R.id.back);
        lytcontact = (RelativeLayout) findViewById(R.id.lytcontact);
        copyright = (TextView) findViewById(R.id.cpyright);
        dvlpdby = (TextView) findViewById(R.id.dvlpdby);
        developed = (TextView) findViewById(R.id.developed);
        contact1 = (TextView) findViewById(R.id.contact1);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "commonfont.otf");
        text.setText("Downly");
        text.setTypeface(face);
        copyright.setTypeface(face);
        dvlpdby.setTypeface(face);
        dvlpdby.setText("Salman Ponnani");
        developed.setTypeface(face);
        contact1.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                onBackPressed();
            }
        });
    }
}
