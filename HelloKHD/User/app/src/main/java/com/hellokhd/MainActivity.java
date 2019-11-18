package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String uri = "https://www.google.com/maps/dir/?api=1&origin=25.362110,88.412811&destination=10.766800,75.926003&travelmode=walking&dir_action=navigate";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);*/

        /*Uri gmmIntentUri = Uri.parse("google.navigation:q=12.868000,74.842690&title=hello&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/

        //Intent(android.content.Intent.ACTION_VIEW,Uri.parse("));




    }
}
