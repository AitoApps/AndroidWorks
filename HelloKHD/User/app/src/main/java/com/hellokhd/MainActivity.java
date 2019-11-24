package com.hellokhd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.DistricHome_Adapter;
import adapter.FeaturedAds_Adapter;
import adapter.SchoolHome_Adapter;
import data.DistricResult_FeedItem;
import data.FeaturedAds_FeedItem;
import data.SchoolResult_FeedItem;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout startupadlyt;
    ImageView startupads,fulscreenad;
    TextView adsskip;
    final DatabaseHandler db=new DatabaseHandler(this);
    final UserDatabaseHandler udb=new UserDatabaseHandler(this);
    ImageView ads1,ads2,ads3,ads4,ads5;
    String callmob="";
   RelativeLayout lytads;
    public float ogheight;
    ImageView findstage,nowrunning,findyourresult,hospital,ambulance,docters,atm,accomodation,bus,petrol,train,cinema,tourism,food,helpdesk;
    TextView txtfindsatge,txtnowrunning,txtfindyourresult,txthospital,txtambulance,txtdocters,txtatm,txtaccomodation,txtbus,txtpetrol,txttrain,txtcinema,txttourism,txtfood,txthelpdesk;
    TextView txtdistricwise,txtschoolwise,txtlatestinfo,latestinfo;
    RelativeLayout lytlatest,districwise,schoolwise;
    ProgressBar progressschool,progressdistric,progresslatestinfo;
    RecyclerView districrecyclerview,schoolrecyclerview;
    public DistricHome_Adapter distadapter;
    public List<DistricResult_FeedItem> distfeeditem;
    public SchoolHome_Adapter schladapter;
    public List<SchoolResult_FeedItem> schlfeeditem;
    RelativeLayout navigationbar;
    ImageView option;
    Typeface face;
    ViewFlipper flipper;
    public GestureDetector mDetector;
    int clickid = -1;
    List<String> lst_adid = new ArrayList();
    List<String> lst_adlinktype = new ArrayList();
    List<String> lst_adrefernce = new ArrayList();

    TextView schoolviewall,dsitricviewall;
    TextView txttransportation,txtroom,txtnews,txtvideo,txtaboutus;
    ImageView transportation,room,news,video,aboutus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        face=Typeface.createFromAsset(getAssets(), "proxibold.otf");
        startupadlyt=findViewById(R.id.startupadlyt);
        startupads=findViewById(R.id.startupads);
        adsskip=findViewById(R.id.adsskip);
        fulscreenad=findViewById(R.id.fulscreenad);
        schoolviewall=findViewById(R.id.schoolviewall);
        dsitricviewall=findViewById(R.id.dsitricviewall);
        ads1=findViewById(R.id.ads1);
        ads2=findViewById(R.id.ads2);
        ads3=findViewById(R.id.ads3);
        ads4=findViewById(R.id.ads4);
        ads5=findViewById(R.id.ads5);
        flipper = (ViewFlipper)findViewById(R.id.flipper1);
        findstage=findViewById(R.id.findstage);
        nowrunning=findViewById(R.id.nowrunning);
        findyourresult=findViewById(R.id.findyourresult);
        hospital=findViewById(R.id.hospital);
        ambulance=findViewById(R.id.ambulance);
        docters=findViewById(R.id.docters);
        atm=findViewById(R.id.atm);
        accomodation=findViewById(R.id.accomodation);
        bus=findViewById(R.id.bus);
        petrol=findViewById(R.id.petrol);
        option=findViewById(R.id.option);
        train=findViewById(R.id.train);
        cinema=findViewById(R.id.cinema);
        tourism=findViewById(R.id.tourism);
        food=findViewById(R.id.food);
        helpdesk=findViewById(R.id.helpdesk);
        txtfindsatge=findViewById(R.id.txtfindsatge);
        txtnowrunning=findViewById(R.id.txtnowrunning);
        txtfindyourresult=findViewById(R.id.txtfindyourresult);
        txthospital=findViewById(R.id.txthospital);
        txtambulance=findViewById(R.id.txtambulance);
        txtdocters=findViewById(R.id.txtdocters);
        txtatm=findViewById(R.id.txtatm);
        txtaccomodation=findViewById(R.id.txtaccomodation);
        txtbus=findViewById(R.id.txtbus);
        txtpetrol=findViewById(R.id.txtpetrol);
        txttrain=findViewById(R.id.txttrain);
        txtcinema=findViewById(R.id.txtcinema);
        txttourism=findViewById(R.id.txttourism);
        txtfood=findViewById(R.id.txtfood);
        txthelpdesk=findViewById(R.id.txthelpdesk);

        txttransportation=findViewById(R.id.txttransportation);
        txtroom=findViewById(R.id.txtroom);
        txtnews=findViewById(R.id.txtnews);
        txtvideo=findViewById(R.id.txtvideo);
        txtaboutus=findViewById(R.id.txtaboutus);

        txtdistricwise=findViewById(R.id.txtdistricwise);
        txtschoolwise=findViewById(R.id.txtschoolwise);
        txtlatestinfo=findViewById(R.id.txtlatestinfo);
        latestinfo=findViewById(R.id.latestinfo);
        lytlatest=findViewById(R.id.lytlatest);
        districwise=findViewById(R.id.districwise);
        schoolwise=findViewById(R.id.schoolwise);
        progressschool=findViewById(R.id.progressschool);
        progressdistric=findViewById(R.id.progressdistric);
        progresslatestinfo=findViewById(R.id.progresslatestinfo);
        districrecyclerview=findViewById(R.id.districrecyclerview);
        schoolrecyclerview=findViewById(R.id.schoolrecyclerview);
        lytads=findViewById(R.id.lytads);
        navigationbar = (RelativeLayout) findViewById(R.id.navigationbar);

        transportation=findViewById(R.id.transportation);
        room=findViewById(R.id.room);
        news=findViewById(R.id.news);
        video=findViewById(R.id.video);
        aboutus=findViewById(R.id.aboutus);

        txtaccomodation.setText(Html.fromHtml("Free&nbsp;&nbsp;&nbsp;<br>Accommodation"));
        latestinfo.setSelected(true);
        mDetector = new GestureDetector(this, new MyGestureListener());
        flipper.setOnTouchListener(touchListener);

        if (udb.getscreenwidth().equalsIgnoreCase("")) {
            int width = getResources().getDisplayMetrics().widthPixels;
            udb.addscreenwidth(width+"");
        }

        if (udb.get_userid().equalsIgnoreCase("")) {
            startActivity(new Intent(getApplicationContext(), Primary_Registration.class));
            finish();
            return;
        }


        lytlatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Announcement_List.class));
            }
        });

        dsitricviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Distric_Wise_Result.class));
            }
        });

        schoolviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),School_Wise_Result.class));
            }
        });

        schlfeeditem = new ArrayList();
        schladapter = new SchoolHome_Adapter(this, schlfeeditem);

        distfeeditem = new ArrayList();
        distadapter = new DistricHome_Adapter(this, distfeeditem);

        districrecyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        districrecyclerview.setAdapter(distadapter);
        schoolrecyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        schoolrecyclerview.setAdapter(schladapter);

        ogheight = Float.parseFloat(udb.getscreenwidth()) / 4.0f;
        ogheight *= 1.0f;
        ads1.getLayoutParams().height = Math.round(ogheight);
        ads2.getLayoutParams().height = Math.round(ogheight);
        ads3.getLayoutParams().height = Math.round(ogheight);
        ads4.getLayoutParams().height = Math.round(ogheight);
        ads5.getLayoutParams().height = Math.round(ogheight);

        ogheight = Float.parseFloat(udb.getscreenwidth()) / 4.0f;
        ogheight *= 2.0f;
        lytads.getLayoutParams().height=Math.round(ogheight);

        dsitricviewall.setTypeface(face);
        schoolviewall.setTypeface(face);
        txtdistricwise.setTypeface(face);
        txtschoolwise.setTypeface(face);

        txtfindsatge.setTypeface(face);
        txtnowrunning.setTypeface(face);
        txtfindyourresult.setTypeface(face);
        txthelpdesk.setTypeface(face);
        txtambulance.setTypeface(face);
        txtdocters.setTypeface(face);
        txtatm.setTypeface(face);
        txtaccomodation.setTypeface(face);
        txtbus.setTypeface(face);
        txtpetrol.setTypeface(face);
        txttrain.setTypeface(face);
        txtcinema.setTypeface(face);
        txttourism.setTypeface(face);
        txtfood.setTypeface(face);
        txthelpdesk.setTypeface(face);
        txthospital.setTypeface(face);
        txttransportation.setTypeface(face);
        txtroom.setTypeface(face);
        txtnews.setTypeface(face);
        txtvideo.setTypeface(face);
        txtaboutus.setTypeface(face);

        dsitricviewall.setPaintFlags(dsitricviewall.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        schoolviewall.setPaintFlags(schoolviewall.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),News.class));
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Video_List.class));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),About_Us.class));
            }
        });
        findyourresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Indivudual_Result.class));
            }
        });
        nowrunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Programs.class));
            }
        });

        findstage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Stages.class));
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypetext="Hospital";
                Temp.shoptype=6;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.ambtranstype=1;
                Temp.ambtranstext="Ambulance";
                startActivity(new Intent(getApplicationContext(),Amb_Transportation.class));
            }
        });

        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.ambtranstype=2;
                Temp.ambtranstext="Transportation";
                startActivity(new Intent(getApplicationContext(),Amb_Transportation.class));
            }
        });

        docters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypetext="Docters";
                Temp.shoptype=3;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });

        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp.shoptypetext="ATM";
                Temp.shoptype=4;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });
        accomodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypetext="Free Accommodation";
                Temp.shoptype=7;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp.busortraintype=1;
                Temp.busortraintext="Bus";
                startActivity(new Intent(getApplicationContext(),Bus_Train.class));

            }
        });
        petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypetext="Petrol Pumbs";
                Temp.shoptype=5;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.busortraintype=2;
                Temp.busortraintext="Train";
                startActivity(new Intent(getApplicationContext(),Bus_Train.class));
            }
        });



        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Cinima.class));
            }
        });
        tourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypetext="Tourism";
                Temp.shoptype=1;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypetext="Rooms";
                Temp.shoptype=2;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.shoptypetext="Food";
                Temp.shoptype=8;
                startActivity(new Intent(getApplicationContext(),Shops.class));
            }
        });
        helpdesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp.ambtranstype=3;
                Temp.ambtranstext="Help Desk";
                startActivity(new Intent(getApplicationContext(),Amb_Transportation.class));
            }
        });


        /*String uri = "https://www.google.com/maps/dir/?api=1&origin=25.362110,88.412811&destination=10.766800,75.926003&travelmode=walking&dir_action=navigate";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);*/

        /*Uri gmmIntentUri = Uri.parse("google.navigation:q=12.868000,74.842690&title=hello&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/

        //Intent(android.content.Intent.ACTION_VIEW,Uri.parse("));

        load_startupads();
        load_bannerad();
        load_featuredad();

        new getstartupads().execute();
        new getresults().execute();


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigview);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer((int) GravityCompat.START);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.yourresult)
        {
            startActivity(new Intent(getApplicationContext(),Indivudual_Result.class));
        }
        else if(id==R.id.nowrunning)
        {
            startActivity(new Intent(getApplicationContext(),Programs.class));
        }
        else if(id==R.id.findstage)
        {
            startActivity(new Intent(getApplicationContext(),Stages.class));
        }
        else if(id==R.id.food)
        {
            Temp.shoptypetext="Food";
            Temp.shoptype=8;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.hospital)
        {
            Temp.shoptypetext="Hospital";
            Temp.shoptype=6;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.ambulance)
        {
            Temp.ambtranstype=1;
            Temp.ambtranstext="Ambulance";
            startActivity(new Intent(getApplicationContext(),Amb_Transportation.class));
        }
        else if(id==R.id.docters)
        {
            Temp.shoptypetext="Docters";
            Temp.shoptype=3;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.atm)
        {
            Temp.shoptypetext="ATM";
            Temp.shoptype=4;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.accomodation)
        {
            Temp.shoptypetext="Free Accomodation";
            Temp.shoptype=7;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.bus)
        {
            Temp.busortraintype=1;
            Temp.busortraintext="Bus";
            startActivity(new Intent(getApplicationContext(),Bus_Train.class));
        }
        else if(id==R.id.petrol)
        {
            Temp.shoptypetext="Petrol Pumbs";
            Temp.shoptype=5;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.cinema)
        {
            startActivity(new Intent(getApplicationContext(), Cinima.class));
        }
        else if(id==R.id.tourism)
        {
            Temp.shoptypetext="Tourism";
            Temp.shoptype=1;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.helpdesk)
        {
            Temp.ambtranstype=3;
            Temp.ambtranstext="Help Desk";
            startActivity(new Intent(getApplicationContext(),Amb_Transportation.class));
        }
        else if(id==R.id.shareapp)
        {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody ="60th കലോത്സവ വിശേഷങ്ങള്\u200D തല്\u200Dസമയമറിയുവാന്\u200D ഇപ്പോള്\u200D തന്നെ ഡൗണ്\u200Dലോഡ് ചെയ്യൂ..Hello KHD App - https://play.google.com/store/apps/details?id=com.hellokhd";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share App");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        else if(id==R.id.about)
        {
            startActivity(new Intent(getApplicationContext(), About_Us.class));
        }

        else if(id==R.id.transportation)
        {
            Temp.ambtranstype=2;
            Temp.ambtranstext="Transportation";
            startActivity(new Intent(getApplicationContext(),Amb_Transportation.class));
        }
        else if(id==R.id.room)
        {
            Temp.shoptypetext="Rooms";
            Temp.shoptype=2;
            startActivity(new Intent(getApplicationContext(),Shops.class));
        }
        else if(id==R.id.video)
        {
            startActivity(new Intent(getApplicationContext(), Video_List.class));
        }
        else if(id==R.id.news)
        {
            startActivity(new Intent(getApplicationContext(), News.class));
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    public void load_startupads()
    {
        ArrayList<String> id1 = db.getstartupads();
        String[] k = (String[]) id1.toArray(new String[id1.size()]);
        if(k.length>0)
        {
            String completePath =Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/startupads.jpg";
            File file = new File(completePath);
            if(file.exists())
            {
               startupadlyt.setVisibility(View.VISIBLE);
                Uri imageUri = Uri.fromFile(file);
                Glide.with(this)
                        .load(imageUri)
                        .into(startupads);

                startupadlyt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openlinks(k[1],k[2]);
                    }
                });
                new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        adsskip.setText((millisUntilFinished / 1000)+"");
                    }

                    public void onFinish() {
                        startupadlyt.setVisibility(View.GONE);
                    }

                }.start();
            }

        }

        ArrayList<String> id2 = db.getfullscreen();
        String[] k1 = (String[]) id2.toArray(new String[id2.size()]);
        if(k1.length>0)
        {

            String completePath =Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/fullscreen.jpg";
            File file = new File(completePath);
            if(file.exists())
            {

                Uri imageUri = Uri.fromFile(file);
                Glide.with(this)
                        .load(imageUri)
                        .into(fulscreenad);


                fulscreenad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openlinks(k[1],k[2]);
                    }
                });
            }

        }


    }

    @Override
    public void onBackPressed() {

        if(startupadlyt.getVisibility()!=View.VISIBLE)
        {
            ArrayList<String> id1 = db.getfullscreen();
            String[] k= (String[]) id1.toArray(new String[id1.size()]);
            if(k.length>0)
            {

                if(fulscreenad.getVisibility()==View.VISIBLE)
                {
                    super.onBackPressed();
                }
                else
                {
                    fulscreenad.setVisibility(View.VISIBLE);
                }


            }
            else
            {
                super.onBackPressed();
            }
        }
    }

    public class getresults extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            progressdistric.setVisibility(View.VISIBLE);
            progresslatestinfo.setVisibility(View.VISIBLE);
            progressschool.setVisibility(View.VISIBLE);
            latestinfo.setText("Updating...");

        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"getresultforhomepage.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }
        public void onPostExecute(String result) {
            try {
                String[] p = result.split("###");
                String announce = p[0];
                String distric = p[1];
                String schools = p[2];

                String anouncments="";
                if (!announce.equalsIgnoreCase("")){

                    String[] got=announce.split(":%");
                    for(int i=0;i<got.length;i++)
                    {
                        if(anouncments=="")
                        {
                            anouncments=got[i];
                        }
                        else
                        {
                            anouncments=anouncments+" * "+got[i];
                        }
                    }
                    latestinfo.setText(anouncments);
                }

                if(!distric.equalsIgnoreCase(""))
                {
                    String[] got=distric.split(":%");
                    distfeeditem.clear();
                    for(int i=0;i<got.length;i++)
                    {
                         DistricResult_FeedItem item=new DistricResult_FeedItem();
                         item.setDistric(got[i]);
                         i=i+1;
                         item.setMark(got[i]);
                         distfeeditem.add(item);
                    }
                    distadapter.notifyDataSetChanged();
                }

                if(!schools.equalsIgnoreCase(""))
                {
                    String[] got=schools.split(":%");
                    schlfeeditem.clear();
                    for(int i=0;i<got.length;i++)
                    {
                        SchoolResult_FeedItem item=new SchoolResult_FeedItem();
                        item.setSchool(got[i]);
                        i=i+1;
                        item.setMark(got[i]);
                        schlfeeditem.add(item);
                    }
                    schladapter.notifyDataSetChanged();
                }

                progressdistric.setVisibility(View.GONE);
                progresslatestinfo.setVisibility(View.GONE);
                progressschool.setVisibility(View.GONE);

            } catch (Exception e) {
            }
        }
    }


    public class getstartupads extends AsyncTask<String, Void, String> {
        public void onPreExecute() {

        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"getstartupads_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode("", "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }
        public void onPostExecute(String result) {
            try {
                String[] p=result.split("###");

                String startup=p[0];
                String fullscreen=p[1];
                String featured=p[2];
                String banner=p[3];
                if(!startup.trim().equalsIgnoreCase("noadvt"))
                {
                    String[] got = startup.trim().split(":%");
                    RequestOptions rep = new RequestOptions().signature(new ObjectKey(got[3]));
                    Glide.with(MainActivity.this).asBitmap()
                            .load(Temp.weblink+"advt/"+got[0]+".jpg")
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                                    db.addstartupads(got[0],got[1],got[2],got[3]);

                                    String filename=Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/startupads.jpg";
                                    write(filename,resource);
                                }
                            });
                }
                if(!fullscreen.trim().equalsIgnoreCase("noadvt"))
                {
                    String[] got = fullscreen.trim().split(":%");

                    Glide.with(MainActivity.this).asBitmap()
                            .load(Temp.weblink+"advt/"+got[0]+".jpg")
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                                    db.addfullscreen(got[0],got[1],got[2],got[3]);

                                    String filename=Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/fullscreen.jpg";
                                    write(filename,resource);
                                }
                            });
                }
                if(!featured.trim().equalsIgnoreCase("noadvt"))
                {
                    db.deletefeatured();
                    String[] got = featured.trim().split(":%");
                    for(int i=0;i<got.length;i++)
                    {
                        int a1=i;
                        i++;
                        int a2=i;
                        i++;
                        int a3=i;
                        i++;
                        int a4=i;
                        db.addfeatured(got[a1],got[a2],got[a3],got[a4]);
                    }
                    load_featuredad();
                }
                if(!banner.trim().equalsIgnoreCase("noadvt"))
                {
                    db.deletebanner();
                    String[] got = banner.trim().split(":%");
                    for(int i=0;i<got.length;i++)
                    {
                        int a1=i;
                        i++;
                        int a2=i;
                        i++;
                        int a3=i;
                        i++;
                        int a4=i;
                        db.addbanner(got[a1],got[a2],got[a3],got[a4]);
                    }

                    load_bannerad();
                }


            } catch (Exception e) {
            }
        }
    }

    public void write(String fileName, Bitmap bitmap) {
        FileOutputStream outputStream;
        try {
            OutputStream fOut = null;
            File file = new File(fileName); // the File to save to
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush();
            fOut.close(); // do not forget to close the stream
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void load_bannerad()
    {
        ArrayList<String> id1 = db.getbanner();
        String[] k= (String[]) id1.toArray(new String[id1.size()]);
        if(k.length>0)
        {
            String ad1="",ad2="",ad3="",ad4="",ad5="";
            int m=0;
            for(int i=0;i<k.length;i++)
            {
                  m=m+1;
                  int a1=i;
                  i=i+1;
                  int a2=i;
                  i=i+1;
                  int a3=i;
                  i=i+1;
                  int a4=i;

                  if(m==1)
                  {
                      ad1=k[a1]+":%"+k[a2]+":%"+k[a3]+":%"+k[a4];
                  }
                 else if(m==2)
                 {
                    ad2=k[a1]+":%"+k[a2]+":%"+k[a3]+":%"+k[a4];
                 }
                  else if(m==3)
                  {
                      ad3=k[a1]+":%"+k[a2]+":%"+k[a3]+":%"+k[a4];
                  }
                  else if(m==4)
                  {
                      ad4=k[a1]+":%"+k[a2]+":%"+k[a3]+":%"+k[a4];
                  }
                  else if(m==5)
                  {
                      ad5=k[a1]+":%"+k[a2]+":%"+k[a3]+":%"+k[a4];
                  }

            }

            if(!ad1.equalsIgnoreCase(""))
            {
                String[] p=ad1.split(":%");
                String adsn=p[0];
                String linktype=p[1];
                String refernce=p[2];
                String imgsig=p[3];
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(imgsig));
                Glide.with(this).load(Temp.weblink+"advt/"+adsn+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(ads1);

                ads1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       openlinks(linktype,refernce);


                    }
                });

            }

            if(!ad2.equalsIgnoreCase(""))
            {
                String[] p=ad2.split(":%");
                String adsn=p[0];
                String linktype=p[1];
                String refernce=p[2];
                String imgsig=p[3];
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(imgsig));
                Glide.with(this).load(Temp.weblink+"advt/"+adsn+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(ads2);

                ads2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openlinks(linktype,refernce);


                    }
                });

            }

            if(!ad3.equalsIgnoreCase(""))
            {
                String[] p=ad3.split(":%");
                String adsn=p[0];
                String linktype=p[1];
                String refernce=p[2];
                String imgsig=p[3];
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(imgsig));
                Glide.with(this).load(Temp.weblink+"advt/"+adsn+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(ads3);

                ads3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openlinks(linktype,refernce);


                    }
                });

            }

            if(!ad4.equalsIgnoreCase(""))
            {
                String[] p=ad4.split(":%");
                String adsn=p[0];
                String linktype=p[1];
                String refernce=p[2];
                String imgsig=p[3];
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(imgsig));
                Glide.with(this).load(Temp.weblink+"advt/"+adsn+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(ads4);

                ads4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openlinks(linktype,refernce);


                    }
                });

            }

            if(!ad5.equalsIgnoreCase(""))
            {
                String[] p=ad5.split(":%");
                String adsn=p[0];
                String linktype=p[1];
                String refernce=p[2];
                String imgsig=p[3];
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(imgsig));
                Glide.with(this).load(Temp.weblink+"advt/"+adsn+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(ads5);

                ads5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openlinks(linktype,refernce);


                    }
                });

            }

        }
    }

    public void load_featuredad()
    {
        ArrayList<String> id1 = db.getfeatured();
        String[] k= (String[]) id1.toArray(new String[id1.size()]);
        if(k.length>0)
        {int n=0;
            for(int i=0;i<k.length;i++)
            {
                int a = i;
                lst_adid.add(k[i]);
                i++;
                lst_adlinktype.add(k[i]);
                i++;
                lst_adrefernce.add(k[i]);
                i++;
                String imgsig=k[i];
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setId(n);
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(imgsig));
                Glide.with(getApplicationContext()).load(Temp.weblink+"advt/"+k[a].trim()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
                imageView.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View arg0, MotionEvent event) {
                        clickid = arg0.getId();
                        return false;
                    }
                });
                flipper.addView(imageView);
                n++;
            }

            flipper.startFlipping();
            clickid = -1;

            }
    }


    public void openlinks(String linktype,String refernce)
    {
        if(linktype.equalsIgnoreCase("1"))
        {
            call(refernce);
        }
        else if(linktype.equalsIgnoreCase("2"))
        {
            //App
            openapp(refernce);

        }
        else if(linktype.equalsIgnoreCase("3"))
        {
            openwebsite(refernce);

        }
        else if(linktype.equalsIgnoreCase("4"))
        {
           openyoutube(refernce);

        }
        else if(linktype.equalsIgnoreCase("5"))
        {
           openfacebook(refernce);

        }
        else if(linktype.equalsIgnoreCase("6"))
        {
            showmap(refernce);
        }

    }

    public void showmap(String gps) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:"+gps));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } catch (Exception e) {
            Toasty.error(getApplicationContext(), "Please install google map app", 0).show();
        }
    }


    public void openapp(String packagename)
    {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packagename)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packagename)));
        }
    }
    public void openwebsite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openfacebook(String username) {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/"+username)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+username)));
        }
    }

    public void openinstagram(String username) {
        Intent likeIng = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/"+username));
        likeIng.setPackage("com.instagram.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+username)));
        }
    }

    public void openpinterest(String username) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("pinterest://www.pinterest.com/")));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pinterest.com/"+username)));
        }
    }

    public void openyoutube(String channelname) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/user/"+channelname));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent2 = new Intent(Intent.ACTION_VIEW);
            intent2.setData(Uri.parse("https://www.youtube.com/user/"+channelname));
            startActivity(intent2);
        }
    }


    public void call(final String mob) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to call to ? "+mob).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != 0) {
                        callmob=mob;
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{ android.Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+mob));
                        startActivity(callIntent);
                    }
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != 0) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{ android.Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+callmob));
                        startActivity(callIntent);
                    }

                } else {

                }
                return;
            }
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return mDetector.onTouchEvent(ev);
    }

View.OnTouchListener touchListener = new View.OnTouchListener() {
    public boolean onTouch(View v, MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }
};
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        MyGestureListener() {
        }

        public boolean onDown(MotionEvent event) {
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            try {
                if (clickid != -1) {
                    openlinks(lst_adlinktype.get(clickid),lst_adrefernce.get(clickid));
                }
            } catch (Exception e2) {
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() > e2.getX()) {
                flipper.setInAnimation(MainActivity.this, R.anim.left_in);
                flipper.setOutAnimation(MainActivity.this, R.anim.left_out);
                flipper.showNext();
                flipper.setFlipInterval(8000);
            }
            if (e1.getX() < e2.getX()) {
                flipper.setInAnimation(MainActivity.this, R.anim.right_in);
                flipper.setOutAnimation(MainActivity.this, R.anim.right_out);
                flipper.showPrevious();
                flipper.setFlipInterval(8000);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
