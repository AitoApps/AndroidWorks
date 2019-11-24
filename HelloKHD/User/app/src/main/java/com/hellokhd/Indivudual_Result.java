package com.hellokhd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.Individual_Result_Adapter;
import adapter.StageList_Adapter;
import data.IndividualResult_FeedItem;
import data.StageList_FeedItem;
import es.dmoral.toasty.Toasty;

public class Indivudual_Result extends AppCompatActivity {
    ImageView back,nointernet,heart,nodata;
    HeaderAndFooterRecyclerView recyclerview;
    public Individual_Result_Adapter adapter;
    public List<IndividualResult_FeedItem> feeditem;
    EditText pid;
    Button btn;
    ConnectionDetecter cd;
    public String txt_pid="";
    Typeface face;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indivudual__result);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        pid=findViewById(R.id.pid);
        btn=findViewById(R.id.btn);
        back=findViewById(R.id.back);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        text=findViewById(R.id.text);
        recyclerview=findViewById(R.id.recyclerview);
        cd=new ConnectionDetecter(this);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feeditem = new ArrayList();
        adapter = new Individual_Result_Adapter(this, feeditem);

        text.setTypeface(face);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet())
                {
                    if(pid.getText().toString().equalsIgnoreCase(""))
                    {
                        Toasty.info(getApplicationContext(),"Please enter your Permanent Id",Toast.LENGTH_SHORT).show();
                        pid.requestFocus();
                    }
                    else
                    {
                        txt_pid=pid.getText().toString();
                        new loadstages().execute();
                    }
                }
                else
                {
                    Toasty.info(getApplicationContext(),Temp.nointernet,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public class loadstages extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            btn.setEnabled(false);
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getindividualresult_bypid.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txt_pid, "UTF-8");
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
                btn.setEnabled(true);
                if (result.contains(":%ok")) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) /3;
                                int m = -1;
                                feeditem.clear();
                                for (int i = 1; i <= k; i++) {
                                   IndividualResult_FeedItem item = new IndividualResult_FeedItem();
                                   m=m+1;
                                   item.setStudentname(got[m]);
                                    m=m+1;
                                    item.setItemname(got[m]);
                                    m=m+1;
                                    item.setMark(got[m]);
                                    feeditem.add(item);
                                }
                            }
                        });
                        heart.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }
}

