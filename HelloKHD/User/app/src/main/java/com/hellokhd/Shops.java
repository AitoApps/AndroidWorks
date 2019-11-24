package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.ShopsList_Adapter;
import adapter.StageList_Adapter;
import data.ShopsList_FeedItem;
import data.StageList_FeedItem;
import es.dmoral.toasty.Toasty;

public class Shops extends AppCompatActivity {

    ImageView back,nointernet,heart,nodata;
    HeaderAndFooterRecyclerView recyclerview;
    public ShopsList_Adapter adapter;
    public List<ShopsList_FeedItem> feeditem;
    TextView text;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        back=findViewById(R.id.back);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        text=findViewById(R.id.text);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        nodata=findViewById(R.id.nodata);
        recyclerview=findViewById(R.id.recyclerview);

        text.setTypeface(face);
        feeditem = new ArrayList();
        adapter = new ShopsList_Adapter(this, feeditem);

        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        text.setText(Temp.shoptypetext);
        new loadstages().execute();
    }


    public class loadstages extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recyclerview.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getshoplist_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.shoptype+"", "UTF-8");
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
        public void onPostExecute(final String result) {
            try {
                if (result.contains(":%ok")) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) /7;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    ShopsList_FeedItem item = new ShopsList_FeedItem();
                                    m=m+1;
                                    item.setSn(got[m]);
                                    m=m+1;
                                    item.setShopname(got[m]);
                                    m=m+1;
                                    item.setDiscription(got[m]);
                                    m=m+1;
                                    item.setContact(got[m]);
                                    m=m+1;
                                    item.setLatitude(got[m]);
                                    m=m+1;
                                    item.setLongtitude(got[m]);
                                    m=m+1;
                                    item.setImgsig(got[m]);
                                    feeditem.add(item);
                                }
                            }
                        });
                        heart.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.w("Pokso",Log.getStackTraceString(e));
                    }
                } else {
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
                Log.w("Pokso",Log.getStackTraceString(e2));
            }
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

    public void call(final String mob) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure want to call to ? "+mob).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (ContextCompat.checkSelfPermission(Shops.this, android.Manifest.permission.CALL_PHONE) != 0) {
                        ActivityCompat.requestPermissions(Shops.this, new String[]{ android.Manifest.permission.CALL_PHONE}, 1);
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

}
