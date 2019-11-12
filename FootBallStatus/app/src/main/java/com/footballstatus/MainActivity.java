package com.footballstatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    final UserDatabaseHandler udb=new UserDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (udb.getscreenwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            udb.addscreenwidth(width+"");
        }
        if(udb.get_name().equalsIgnoreCase(""))
        {
            Intent i=new Intent(getApplicationContext(),Registration.class);
            startActivity(i);
            finish();
            return;
        }


    }
}
