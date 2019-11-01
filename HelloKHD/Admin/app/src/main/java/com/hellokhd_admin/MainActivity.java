package com.hellokhd_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    UserDatabaseHandler udb = new UserDatabaseHandler(this);
    final DatabaseHandler db=new DatabaseHandler(this);
    ImageView advts,stages,markadmin,veriadmin,shops,tourspot,room,docters,video,announcment,news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        advts=findViewById(R.id.advts);
        stages=findViewById(R.id.stages);
        markadmin=findViewById(R.id.markadmin);
        veriadmin=findViewById(R.id.veriadmin);
        shops=findViewById(R.id.shops);
        tourspot=findViewById(R.id.tourspot);
        room=findViewById(R.id.room);
        docters=findViewById(R.id.docters);
        video=findViewById(R.id.video);
        announcment=findViewById(R.id.announcment);
        news=findViewById(R.id.news);

        if (db.getscreenwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            db.addscreenwidth(width+"");
        }

        if (udb.get_userid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
            return;
        }

        advts=findViewById(R.id.advts);
        stages=findViewById(R.id.stages);
        markadmin=findViewById(R.id.markadmin);
        veriadmin=findViewById(R.id.veriadmin);
        shops=findViewById(R.id.shops);
        tourspot=findViewById(R.id.tourspot);
        room=findViewById(R.id.room);
        docters=findViewById(R.id.docters);
        video=findViewById(R.id.video);
        announcment=findViewById(R.id.announcment);
        news=findViewById(R.id.news);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),VideoList.class);
                startActivity(i);
            }
        });

        announcment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Anouncments.class);
                startActivity(i);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),News_List.class);
                startActivity(i);
            }
        });
        advts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Advt_List.class);
                startActivity(i);

            }
        });

        stages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Stage_List.class);
                startActivity(i);
            }
        });

        markadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MarkAdminList.class);
                startActivity(i);
            }
        });

        veriadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Verification_Admin.class);
                startActivity(i);
            }
        });

        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="0";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        tourspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="1";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });
        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="2";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });

        docters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypes="3";
                Intent i=new Intent(getApplicationContext(),Shop_List.class);
                startActivity(i);
            }
        });


    }
}
