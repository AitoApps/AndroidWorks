package com.daydeal_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button addoffer;
    Button catogery;
    final DatabaseHandler db = new DatabaseHandler(this);
    Button marketting;
    Button productverification;
    Button shops;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        marketting = (Button) findViewById(R.id.marketting);
        catogery = (Button) findViewById(R.id.catogery);
        shops = (Button) findViewById(R.id.shops);
        addoffer = (Button) findViewById(R.id.addoffer);
        productverification = (Button) findViewById(R.id.productverification);
        if (udb.get_userid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }
        try {
            if (db.getscreenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                db.addscreenwidth(width+"");
            }
        } catch (Exception e) {
        }
        marketting.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Marketting_Staff.class));
            }
        });
        shops.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), ShopList.class));
            }
        });
        catogery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Catogery_List.class));
            }
        });
        addoffer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), Offer_Management.class));
            }
        });
        productverification.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), Product_Verification.class));
            }
        });
    }
}
