package com.fishapp.user;

import adapter.Clients_Adapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Clients_FeedItem;
import es.dmoral.toasty.Toasty;
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

public class Client_List extends AppCompatActivity {
    private final long DELAY = 1000;
    ImageView back;
    ConnectionDetecter cd;
    public DatabaseHandler db=new DatabaseHandler(this);
    Typeface face;
    Typeface face1;
    public List<Clients_FeedItem> feedItems;
    View footerView;
    ImageView heart;
    public int limit = 0;
    public Clients_Adapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    EditText search;
    public HeaderAndFooterRecyclerView searchrecylerview;
    TextView text;
    public Timer timer = new Timer();
    public String txt_search = "";
    public UserDatabaseHandler udb=new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_client__list);
        try {
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            search = (EditText) findViewById(R.id.search);
            searchrecylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.searchrecylerview);
            text = (TextView) findViewById(R.id.text);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            feedItems = new ArrayList();
            listAdapter = new Clients_Adapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 3));
            recylerview.setAdapter(listAdapter);
            footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
            ((TextView) footerView.findViewById(R.id.next)).setTypeface(face);
            recylerview.addFooterView(footerView);
            footerView.setVisibility(View.INVISIBLE);
            text.setText(Temp.clientcatname);
            text.setTypeface(face);
            search.setTypeface(face);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
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
                                        searchrecylerview.setVisibility(View.VISIBLE);
                                        recylerview.setVisibility(View.GONE);
                                        txt_search = search.getText().toString();
                                        Temp.isclientsearch = 1;
                                        new loadsearch().execute(new String[0]);
                                    }
                                });
                            }
                        }, 1000);
                        return;
                    }
                    searchrecylerview.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    Temp.isclientsearch = 0;
                    new loadstatus().execute(new String[0]);
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nointernet.setVisibility(View.GONE);
                        limit = 0;
                        new loadstatus().execute(new String[0]);
                        return;
                    }
                    nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            });
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadstatus().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    public void loadmore() {
        if (footerView.getVisibility() == 0) {
            return;
        }
        if (cd.isConnectingToInternet()) {
            new loadstatus1().execute(new String[0]);
        } else {
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }
    }

    public void loadmore_search() {
        if (footerView.getVisibility() == 0) {
            return;
        }
        if (cd.isConnectingToInternet()) {
            new loadsearch1().execute(new String[0]);
        } else {
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }
    }
    public class loadsearch extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            nodata.setVisibility(View.GONE);
            recylerview.setVisibility(View.GONE);
            searchrecylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getclientlist_user_search.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.clientcatid+":%"+limit+":%"+txt_search, "UTF-8");
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
                    try {
                        feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Clients_FeedItem item = new Clients_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setName(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    nodata.setVisibility(View.GONE);
                    heart.setVisibility(View.GONE);
                    searchrecylerview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                nodata.setVisibility(View.VISIBLE);
                searchrecylerview.setVisibility(View.GONE);
                heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public class loadsearch1 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            limit += 50;
            footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"getclientlist_user_search.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.clientcatid+":%"+limit+":%"+txt_search, "UTF-8");
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
                    try {
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Clients_FeedItem item = new Clients_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setName(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    footerView.setVisibility(View.INVISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                footerView.setVisibility(View.INVISIBLE);
                heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            nodata.setVisibility(View.GONE);
            recylerview.setVisibility(View.GONE);
            searchrecylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"getclientlist_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.clientcatid+":%"+limit, "UTF-8");
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
                    try {
                        feedItems.clear();
                        String[] got = result.trim().split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Clients_FeedItem item = new Clients_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setName(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    nodata.setVisibility(View.GONE);
                    heart.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                nodata.setVisibility(View.VISIBLE);
                recylerview.setVisibility(View.GONE);
                heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public class loadstatus1 extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            limit += 50;
            footerView.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getclientlist_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.clientcatid+":%"+limit, "UTF-8");
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
                    try {
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Clients_FeedItem item = new Clients_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setName(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    footerView.setVisibility(View.INVISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                footerView.setVisibility(View.INVISIBLE);
                heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }
}
