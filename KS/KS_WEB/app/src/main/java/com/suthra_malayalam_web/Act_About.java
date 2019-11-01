package com.suthra_malayalam_web;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Act_About extends AppCompatActivity {
    ImageView back;
    TextView cpyright;
    TextView dvpdby;
    Typeface face;
    RelativeLayout layout;
    NetConnect nc;
    TextView text;
    TextView ver;
    TextView wponly;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.avty_about);
        wponly = (TextView) findViewById(R.id.wponly);
        layout = (RelativeLayout) findViewById(R.id.lytcontact);
        text = (TextView) findViewById(R.id.text);
        dvpdby = (TextView) findViewById(R.id.devpedby);
        cpyright = (TextView) findViewById(R.id.copyright);
        ver = (TextView) findViewById(R.id.version);
        back = (ImageView) findViewById(R.id.moveback);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        text.setText(Static_Veriable.appname);
        text.setTypeface(face);
        cpyright.setText(Static_Veriable.aboutus_copyright);
        cpyright.setTypeface(face);
        dvpdby.setText(Static_Veriable.developername);
        dvpdby.setTypeface(face);
        wponly.setText(Static_Veriable.whatsapponly);
        wponly.setTypeface(face);
        nc = new NetConnect(this);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
    }
}
