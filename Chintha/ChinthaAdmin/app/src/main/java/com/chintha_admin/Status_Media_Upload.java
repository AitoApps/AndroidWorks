package com.chintha_admin;

import adapter.StatusMediaListAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import data.StatusMedia_Feeditem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Status_Media_Upload extends AppCompatActivity {
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public List<StatusMedia_Feeditem> feedItems;
    ListView list;
    public StatusMediaListAdapter listAdapter;
    ImageView nointernet;
    ProgressBar pb;
    ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_status__media__upload);
        list = (ListView) findViewById(R.id.list);
        pb = (ProgressBar) findViewById(R.id.pb);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        LayoutInflater layoutInflater = getLayoutInflater();
        cd = new ConnectionDetecter(this);
        feedItems = new ArrayList();
        listAdapter = new StatusMediaListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        pd = new ProgressDialog(this);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    new loadreport().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onResume() {
        super.onResume();
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            new loadreport().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
    }

    public void removeitem(int position) {
        feedItems.remove(position);
        listAdapter.notifyDataSetChanged();
    }
    public class loadreport extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            list.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            nointernet.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"gettempstatusmedia.php";
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
                if (result.contains(":%ok")) {
                    feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 6;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        StatusMedia_Feeditem item = new StatusMedia_Feeditem();
                        m=m+1;
                        item.setSn(got[m].trim());
                        m=m+1;
                        item.setRegdate(got[m]);
                        m=m+1;
                        item.setUserid(got[m]);
                        m=m+1;
                        item.setTitle(got[m]);
                        m=m+1;
                        item.setMediatype(got[m]);
                        m=m+1;
                        item.setPath(got[m]);
                        feedItems.add(item);
                    }
                    pb.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                pb.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}
