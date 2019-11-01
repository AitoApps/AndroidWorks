package com.dailybill;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity{
    Button newbill;
    ConnectionDetecter cd;
    TextView loctext;
    final DatabaseHandler db=new DatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cd=new ConnectionDetecter(this);
        newbill=findViewById(R.id.newbill);
        loctext = (TextView) findViewById(R.id.loctext);

        loctext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Location_Picker.class));
            }
        });
        newbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewBill.class));
            }
        });

    }

    @Override
    protected void onResume() {

        if(db.getaddress().equalsIgnoreCase(""))
        {
            startActivity(new Intent(getApplicationContext(),Location_Picker.class));
        }
        else
        {
            loctext.setText(db.getaddress());
        }


        super.onResume();
    }
}
