package com.dailybill_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    ImageView shopmanagement,productlist,markettingstaff;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    final DatabaseHandler db=new DatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        try {
            if (db.getscreenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                db.addscreenwidth(width+"");
            }
        } catch (Exception e) {
        }

        shopmanagement = (ImageView) findViewById(R.id.shopmanagement);
        productlist=findViewById(R.id.productlist);
        markettingstaff=findViewById(R.id.markettingstaff);
        if (udb.get_userid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Register.class));
            finish();
            return;
        }
        shopmanagement.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), ShopManagement.class));
            }
        });

        productlist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Product_List.class));
            }
        });
        markettingstaff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Marketting_Staff.class));

            }
        });
    }
}
