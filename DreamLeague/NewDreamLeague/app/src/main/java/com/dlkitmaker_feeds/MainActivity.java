package com.dlkitmaker_feeds;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_KitHead;
import data.Feed_KitHeads;

public class MainActivity extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    List<Feed_KitHeads> items;
    RecyclerView recylerview;
    ImageView nointernet;
    ProgressDialog pd;
    final DB db=new DB(this);
    Button create,mykits;
    Dialog dialog;

    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-5517777745693327~8323967529");
        pd=new ProgressDialog(this);

        String[] PERMISSIONS = {
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_NETWORK_STATE,

        };
        if (!ispermitted(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        create=findViewById(R.id.create);
        mykits=findViewById(R.id.mykits);


        items = new ArrayList<>();

        items.add(new Feed_KitHeads(R.drawable.kuchalana));
        items.add(new Feed_KitHeads(R.drawable.dlscenter));
        items.add(new Feed_KitHeads(R.drawable.dlsvn));
        items.add(new Feed_KitHeads(R.drawable.dlssoccer));
        items.add(new Feed_KitHeads(R.drawable.dlssoccer2));
        items.add(new Feed_KitHeads(R.drawable.domnmatelink));

        adView1=findViewById(R.id.adView1);
        intestrial = new InterstitialAd(MainActivity.this);
        intestrial.setAdUnitId("ca-app-pub-5517777745693327/8411597433");
        adreq = new AdRequest.Builder().build();
        adreq1 = new AdRequest.Builder().build();

        nointernet=findViewById(R.id.nointernet);
        recylerview=findViewById(R.id.recylerview);

        recylerview.setAdapter(new Adapter_KitHead(MainActivity.this,items, this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recylerview.setLayoutManager(gridLayoutManager);


        try
        {
            adView1.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    try
                    {
                        if(count<=20)
                        {
                            adView1.loadAd(adreq1);
                            count++;
                        }


                    }
                    catch (Exception a)
                    {

                    }

                }
            });

            intestrial.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {

                    if(intcount<=20) {
                        intestrial.loadAd(adreq);
                        intcount++;
                    }

                }
            });

        }
        catch (Exception a)
        {

        }

        ArrayList<String> id1=db.get_teamlist1();
        String[] c1=id1.toArray(new String[id1.size()]);

        if(c1.length<=0)
        {
            new teamlistload().execute();
        }

        File folder=new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername);
        if(!folder.exists())
        {
            folder.mkdir();
            File f1 = new File(Environment.getExternalStorageDirectory() + "/"+ Temp.foldername+"/" + ".nomedia");
            try {
                f1.createNewFile();
            } catch (IOException e) {

            }

        }

        if(db.getscreenwidth().equalsIgnoreCase(""))
        {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            db.addscreenwidth(width+"");
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                show_kitcat();
            }
        });

        mykits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),My_Kits.class);
                startActivity(i);
            }
        });

    }

    public void show_kitcat()
    {
        dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.catogerykits);
        final TextView txttitle=dialog.findViewById(R.id.txttitle);
        final Spinner spinner=dialog.findViewById(R.id.spinner);

        String[] p={"Choose","Normal","Goal Keeper"};
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, p){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView)v).setTextColor(Color.BLACK);
                ((TextView)v).setTextSize(16);
                //((TextView)v).setTypeface(face);
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView)v).setTextColor(Color.BLACK);
                ((TextView)v).setTextSize(16);
              //  ((TextView)v).setTypeface(face);
                return v;
            }
        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==0)
                {

                }
                else
                {
                    try
                    {

                        db.add_kittheme(arg2+"");
                        exittotheme();
                    }
                    catch (Exception a)
                    {

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialog.show();

    }


    public void exittotheme()
    {


        Intent i=new Intent(getApplicationContext(), Theme_Selection.class);
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dialog!=null && dialog.isShowing())
        {
           dialog.dismiss();

        }
        count=0;
        intcount=0;
        try
        {
            adView1.loadAd(adreq1);
            intestrial.loadAd(adreq);
        }
        catch (Exception a)
        {

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(intestrial.isLoaded())
        {
            intestrial.show();
        }
    }

    public class teamlistload extends AsyncTask<String,Void,String> {

        protected void onPreExecute(){
            pd.setMessage("Loading Team List...");
            pd.setCancelable(false);
            pd.show();

        }
        @Override
        protected String doInBackground(String... arg0) {

            try{

                for(int i = 0; i< Kit_Data.kitdata.length; i++)
                {
                    int a=i;
                    i++;
                    int a1=i;
                    i++;
                    int a2=i;

                    db.add_teamlist(Kit_Data.kitdata[a], Kit_Data.kitdata[a1], Kit_Data.kitdata[a2]);

                }

                return "ok";

            }

            catch(Exception e) {

                return new String("Exception: " + e.getMessage());



            }
        }

        @Override
        protected void onPostExecute(String result){

            ArrayList<String> id1=db.get_teamlist1();
            String[] c1=id1.toArray(new String[id1.size()]);

            pd.dismiss();

        }
    }


    public static boolean ispermitted(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


}
