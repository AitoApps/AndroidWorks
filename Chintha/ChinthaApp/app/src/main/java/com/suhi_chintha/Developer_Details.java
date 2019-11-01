package com.suhi_chintha;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class Developer_Details extends AppCompatActivity {
    TextView contact1;
    TextView cpyrigt;
    TextView dvpdby;
    Typeface face;
    ImageView fblogo;
    RelativeLayout lytcontact;
    ImageView moveback;
    TextView text;
    ImageView wplogo;
    ImageView ytlogo;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.about_actvty);
        fblogo = (ImageView) findViewById(R.id.fblogo);
        ytlogo = (ImageView) findViewById(R.id.ytlogo);
        dvpdby = (TextView) findViewById(R.id.makername);
        cpyrigt = (TextView) findViewById(R.id.copyrite);
        contact1 = (TextView) findViewById(R.id.contact1);
        lytcontact = (RelativeLayout) findViewById(R.id.cntct_layout);
        moveback = (ImageView) findViewById(R.id.moveback);
        text = (TextView) findViewById(R.id.text);
        wplogo = (ImageView) findViewById(R.id.wplogo);
        face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
        text.setText(Static_Variable.application_title);
        text.setTypeface(face);
        cpyrigt.setText(Static_Variable.cpyright_app);
        cpyrigt.setTypeface(face);
        dvpdby.setText(Static_Variable.whomakethis);
        dvpdby.setTypeface(face);
        contact1.setTypeface(face);
        contact1.setText("അഭിപ്രായം പറയാം : ");
        moveback.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        wplogo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.savednumber, Toast.LENGTH_LONG).show();
            }
        });
        ytlogo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse("http://www.youtube.com/channel/UC5U7L03gtvrnu7-Ctnq-3EA"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/channel/UC5U7L03gtvrnu7-Ctnq-3EA")));
                }
            }
        });
        fblogo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("fb://page/153834575056875")));
                } catch (Exception e) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/statusappkal")));
                }
            }
        });
    }
}
