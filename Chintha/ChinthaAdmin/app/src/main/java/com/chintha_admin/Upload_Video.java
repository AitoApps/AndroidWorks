package com.chintha_admin;

import adapter.VideoListAdapter;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import data.Video_FeedItemItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Upload_Video extends AppCompatActivity {
    Button Send;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public List<Video_FeedItemItem> feedItems;
    ListView list;
    public VideoListAdapter listAdapter;
    EditText message;
    ImageView nointernet;
    ProgressBar pb;
    ProgressDialog pd;
    public String txtmessage = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_upload__video);
        list = (ListView) findViewById(R.id.list);
        pb = (ProgressBar) findViewById(R.id.pb);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        LayoutInflater layoutInflater = getLayoutInflater();
        cd = new ConnectionDetecter(this);
        feedItems = new ArrayList();
        listAdapter = new VideoListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        pd = new ProgressDialog(this);
        message = (EditText) findViewById(R.id.message);
        Send = (Button) findViewById(R.id.Send);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    new loaduser().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        Send.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (message.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please eneter message", Toast.LENGTH_SHORT).show();
                    message.requestFocus();
                    return;
                }
                txtmessage = message.getText().toString();
                new sendvideo().execute(new String[0]);
            }
        });
    }
    public void onResume() {
        super.onResume();
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            new loaduser().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
    }

    public void removeitem(int position) {
        feedItems.remove(position);
        listAdapter.notifyDataSetChanged();
    }

    public class loaduser extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            list.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            nointernet.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"admin_getvideos.php";
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
                if (result.contains(",ok")) {
                    feedItems.clear();
                    String[] got = result.split(",");
                    for (int i = 0; i < got.length - 1; i++) {
                        Video_FeedItemItem item = new Video_FeedItemItem();
                        item.setfilename(got[i]);
                        feedItems.add(item);
                    }
                    pb.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                pb.setVisibility(View.GONE);
            } catch (Exception a) {
                Toast.makeText(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class sendvideo extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait....");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"uploadvideosendtoall1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtmessage, "UTF-8");
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
                pd.dismiss();
                if (result.contains("ok")) {
                    message.setText("");
                    Toast.makeText(getApplicationContext(), "Sened", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception a) {
                Toast.makeText(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }
}
