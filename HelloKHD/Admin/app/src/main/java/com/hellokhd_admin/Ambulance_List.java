package com.hellokhd_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.Amb_trans_ListAdapter;
import adapter.VideoList_ListAdapter;
import data.Amb_trans_FeedItem;
import data.VideoList_FeedItem;

public class Ambulance_List extends AppCompatActivity {
    Button addproduct;
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;
    public List<Amb_trans_FeedItem> feedItems;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;
    public Amb_trans_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance__list);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        addproduct = findViewById(R.id.addproduct);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        addproduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Temp.amb_trasnedit = 0;
                startActivity(new Intent(getApplicationContext(), Add_Ambulance.class));
            }
        });

        if(Temp.is_amb_trans.equalsIgnoreCase("1"))
        {
            text.setText("Ambulance");
            addproduct.setText("Add Ambulance");
        }
        else if(Temp.is_amb_trans.equalsIgnoreCase("2"))
        {
            text.setText("Transportation");
            addproduct.setText("Add Transportation");
        }

        text.setTypeface(face);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        heart = (ImageView) findViewById(R.id.heart);
        cd = new ConnectionDetecter(this);
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        list.addFooterView(footerview);
        footerview.setVisibility(View.GONE);
        feedItems = new ArrayList();
        listAdapter = new Amb_trans_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);

        Glide.with(this).load(R.drawable.loading).into(heart);
        nointernet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadstatus().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {


        public void onPreExecute() {
            nointernet.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }

        public String doInBackground(String... arg0) {

            try {
                String link=Temp.weblink +"getamulanace_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.is_amb_trans, "UTF-8");
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
                if (result.trim().contains(":%ok")) {
                    feedItems.clear();
                    String[] got = result.trim().split(":%");
                    int k = (got.length - 1) / 3;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Amb_trans_FeedItem item = new  Amb_trans_FeedItem();
                        m=m+1;
                        item.setSn(got[m].trim());
                        m=m+1;
                        item.setTitle(got[m]);
                        m=m+1;
                        item.setContact(got[m]);
                        feedItems.add(item);
                    }
                    heart.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                nodata.setVisibility(View.VISIBLE);
                heart.setVisibility(View.GONE);
                footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

}