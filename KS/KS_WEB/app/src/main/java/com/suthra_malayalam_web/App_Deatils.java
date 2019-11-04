package com.suthra_malayalam_web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.AppHelplist_Adapter;
import adapter.Applist_Adapter;
import data.AppHelplist_Feed;
import data.Applist_Feed;

public class App_Deatils extends AppCompatActivity {

    ImageView moveback, heart;
    TextView text, txtapplink, headertext, footertext;
    Button applink;
    ListView listview;
    Typeface face;
    NetConnect ic;
    private List<AppHelplist_Feed> feeds;
    private AppHelplist_Adapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__deatils);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        moveback = findViewById(R.id.moveback);
        heart = findViewById(R.id.heart);
        text = findViewById(R.id.text);
        txtapplink = findViewById(R.id.txtapplink);
        headertext = findViewById(R.id.headertext);
        footertext = findViewById(R.id.footertext);
        applink = findViewById(R.id.applink);
        listview = findViewById(R.id.listview);
        ic = new NetConnect(this);

        text.setTypeface(face);
        text.setText("ടാസ്\u200Cക്കുകള്\u200D ");
        txtapplink.setTypeface(face);
        headertext.setTypeface(face);
        footertext.setTypeface(face);
        applink.setTypeface(face);

        txtapplink.setText("ഈ ടാസ്\u200Cക് എങ്ങിനെ പൂര്\u200Dത്തിയാക്കാം എന്നത് താഴെ നല്\u200Dകിയ സ്\u200Cക്രീന്\u200Dഷോട്ടില്\u200D നിന്നും മനസ്സിലാക്കി അതുപോലെ മാത്രം ചെയ്യുക");

        headertext.setText(Static_Veriable.task_appheader);
        footertext.setText(Static_Veriable.task_appfooter);

        moveback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        applink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Static_Veriable.task_appurl));
                startActivity(browserIntent);
            }
        });

        Glide.with(this).load(R.drawable.loading).into(heart);
        feeds = new ArrayList();
        listAdapter = new AppHelplist_Adapter(this, feeds);
        listview.setAdapter(listAdapter);

        try {
            if (ic.isConnectingToInternet()) {
                new loadstatus().execute(new String[0]);
                return;
            } else {
                Toast.makeText(getApplicationContext(), Static_Veriable.nonet, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }

    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            listview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... arg0) {
            try {

                String link = Static_Veriable.weblink + "getapphelplist.php";
                String data = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Veriable.appid, "UTF-8");
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
                while ((line = reader.readLine()) != null) {
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
                    int k = (got.length - 1) / 4;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        AppHelplist_Feed item = new AppHelplist_Feed();
                        m = m + 1;
                        item.setSn(got[m].trim());
                        m = m + 1;
                        item.setTitle(got[m]);
                        m = m + 1;
                        item.setImagedim(got[m]);
                        m = m + 1;
                        item.setImagesrc(got[m]);
                        feeds.add(item);
                    }
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
