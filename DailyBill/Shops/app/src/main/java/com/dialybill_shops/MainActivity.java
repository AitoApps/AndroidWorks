package com.dialybill_shops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView productmanagment,delvieryboy;
    final UserDatabaseHandler udb=new UserDatabaseHandler(this);
    final DatabaseHandler db=new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productmanagment=findViewById(R.id.productmanagment);
        delvieryboy=findViewById(R.id.delvieryboy);

        try {
            if (db.getscreenwidth().equalsIgnoreCase("")) {
                int width = getResources().getDisplayMetrics().widthPixels;
                db.addscreenwidth(width+"");
            }
        } catch (Exception e) {
        }
        if (udb.get_shopid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }


        productmanagment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Product_Management.class);
                startActivity(i);
            }
        });

        delvieryboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Delivery_Boy.class));
            }
        });

    }
}
