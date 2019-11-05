package com.mal_suthra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import adapter.Applist_Adapter;
import data.Applist_Feed;

public class Complete_Task extends AppCompatActivity {
    ImageView back;
    Typeface face;
    private List<Applist_Feed> feeds;
    private Applist_Adapter listAdapter;
    ListView listview;
    ImageView heart;
    TextView text;
    NetConnect cd;
    int limit=0;
    TextView tasktext;
    Button completetask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete__task);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        back = (ImageView) findViewById(R.id.moveback);
        text = (TextView) findViewById(R.id.text);
        tasktext=findViewById(R.id.tasktext);
        listview=findViewById(R.id.listview);
        completetask=findViewById(R.id.completetask);
        cd=new NetConnect(this);
        heart=findViewById(R.id.heart);
        text.setTypeface(face);
        text.setText("ടാസ്\u200Cക്കുകള്\u200D ");
        completetask.setTypeface(face);
        completetask.setText("കംപ്ലീറ്റ് ടാസ്\u200Cക് ");
        tasktext.setText("താഴെയുള്ള ആപ്പുകള്\u200D ഇന്\u200Dസ്റ്റാള്\u200D ചെയ്യുക.ആപ്പുകള്\u200D ഇന്\u200Dസ്റ്റാള്\u200D ചെയ്ത് ടാസ്\u200Cക്കുകള്\u200D പൂര്\u200Dത്തിയാക്കിയതിന് ശേഷം കംപ്ലീറ്റ് ടാസ്\u200Cക് എന്ന ബട്ടണ്\u200D പ്രസ്സ് ചെയ്യുക");
        tasktext.setTypeface(face);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Glide.with(this).load(R.drawable.loading).into(heart);
        feeds = new ArrayList();
        listAdapter = new Applist_Adapter(this, feeds);
        listview.setAdapter(listAdapter);

        completetask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Complete_Task_Confirm.class));
            }
        });
        try {
            if (cd.isConnectingToInternet()) {
                limit = 0;
                new loadstatus().execute(new String[0]);
                return;
            }
            else
            {
                Toast.makeText(getApplicationContext(), Static_Veriable.nonet, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
        }

    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            listview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            tasktext.setVisibility(View.INVISIBLE);
            completetask.setVisibility(View.INVISIBLE);
            limit = 0;
        }


        public String doInBackground(String... arg0) {
            try {

                String link= Static_Veriable.weblink +"getapplist.php";
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
                    feeds.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 6;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Applist_Feed item = new Applist_Feed();
                        m=m+1;
                        item.setSn(got[m].trim());
                        m=m+1;
                        item.setAppname(got[m]);
                        m=m+1;
                        item.setApplink(got[m]);
                        m=m+1;
                        item.setHeader(got[m]);
                        m=m+1;
                        item.setFooter(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
                        feeds.add(item);
                    }
                    tasktext.setVisibility(View.VISIBLE);
                    completetask.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}