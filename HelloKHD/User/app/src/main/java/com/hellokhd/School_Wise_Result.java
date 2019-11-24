package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Timer;
import java.util.TimerTask;

import adapter.SchoolSearch_Adapter;
import adapter.StageList_Adapter;
import data.SchoolSearch_FeedItem;
import data.StageList_FeedItem;

public class School_Wise_Result extends AppCompatActivity {
    EditText search;
    ConnectionDetecter cd;
    public Timer timer = new Timer();
    RecyclerView searchrecyclerview;
    ImageView nointernet,heart,nodata,back;
    TextView text;
    public String txt_search="";
    public SchoolSearch_Adapter adapter;
    public List<SchoolSearch_FeedItem> feeditem;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school__wise__result);
        search=findViewById(R.id.search);
        cd=new ConnectionDetecter(this);
        searchrecyclerview=findViewById(R.id.searchrecyclerview);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        back=findViewById(R.id.back);
        text=findViewById(R.id.text);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        feeditem = new ArrayList();
        adapter = new SchoolSearch_Adapter(this, feeditem);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);

        text.setTypeface(face);
        searchrecyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        searchrecyclerview.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    searchrecyclerview.setVisibility(View.VISIBLE);
                                    txt_search = search.getText().toString();
                                    new loadsearch().execute(new String[0]);
                                }
                            });
                        }
                    }, 1000);
                }
                else
                {
                    searchrecyclerview.setVisibility(View.GONE);
                }
            }
        });

    }

    public class loadsearch extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            searchrecyclerview.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getschoolsearch_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txt_search, "UTF-8");
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
                                int k = (got.length - 1) /6;
                                int m = -1;
                                feeditem.clear();
                                for (int i = 1; i <= k; i++) {
                                    SchoolSearch_FeedItem item2 = new SchoolSearch_FeedItem();
                                    m=m+1;
                                    item2.setSn(got[m]);
                                    m=m+1;
                                    item2.setSchoolname(got[m]);
                                    m=m+1;
                                    item2.setHsgeneral(got[m]);
                                    m=m+1;
                                    item2.setHssgeneral(got[m]);
                                    m=m+1;
                                    item2.setHsarabic(got[m]);
                                    m=m+1;
                                    item2.setHssanskrit(got[m]);
                                    feeditem.add(item2);
                                }
                            }
                        });
                        nodata.setVisibility(View.GONE);
                        heart.setVisibility(View.GONE);
                        searchrecyclerview.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    nodata.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }
}
