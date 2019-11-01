package com.fishappadmin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    Button areamanagement;
    final DatabaseHandler db = new DatabaseHandler(this);
    Button delivery;
    Typeface face;
    Button neworder;
    Button outtodelivery;
    TextView text;
    UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        areamanagement = (Button) findViewById(R.id.areamanagement);
        neworder = (Button) findViewById(R.id.neworder);
        outtodelivery = (Button) findViewById(R.id.outtodelivery);
        delivery = (Button) findViewById(R.id.delivery);
        text = (TextView) findViewById(R.id.text);
        areamanagement.setTypeface(face);
        neworder.setTypeface(face);
        outtodelivery.setTypeface(face);
        delivery.setTypeface(face);
        text.setTypeface(face);

        if (udb.get_secretcode().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }
        if (db.getscreenwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            db.addscreenwidth(width+"");
        }
        areamanagement.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Area_Management.class));
            }
        });
        neworder.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.orderlisttype = 1;
                startActivity(new Intent(getApplicationContext(), OrderGroup.class));
            }
        });
        outtodelivery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.orderlisttype = 2;
                startActivity(new Intent(getApplicationContext(), OrderGroup.class));
            }
        });
        delivery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.orderlisttype = 3;
                startActivity(new Intent(getApplicationContext(), OrderGroup.class));
            }
        });
    }
}
