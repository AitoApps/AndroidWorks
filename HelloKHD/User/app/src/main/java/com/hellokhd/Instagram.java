package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import adapter.Instagram_List_Adapter;
import data.Instagram_Feed;
import es.dmoral.toasty.Toasty;

public class Instagram extends AppCompatActivity {
    ConnectionDetecter cd;
    Typeface face;
    public List<Instagram_Feed> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView list;
    public Instagram_List_Adapter listAdapter;
    ShimmerFrameLayout shimmer_view_container;
    ImageView nointernet;
    ImageView moveback;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram__feed);
     moveback=findViewById(R.id.back);
     cd=new ConnectionDetecter(this);
     shimmer_view_container=findViewById(R.id.shimmer_view_container);
     text=findViewById(R.id.text);
        try {
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        nointernet = (ImageView)findViewById(R.id.nonet);
        list = (ListView) findViewById(R.id.listview);
        cd = new ConnectionDetecter(this);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.bottomview, null);
        list.addFooterView(footerview);
        footerview. setVisibility(View.GONE);
        feedItems = new ArrayList();
        listAdapter = new Instagram_List_Adapter(this, feedItems);
        list.setAdapter(listAdapter);
        text.setTypeface(face);
        moveback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                    flag = false;
                    if (!cd.isConnectingToInternet()) {
                        Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    } else if (footerview.getVisibility() != View.VISIBLE) {
                        limit += 30;
                        new getyoutube1().execute(new String[0]);
                    }
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (arg1 == 2) {
                    flag = true;
                }
            }
        });
        nointernet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet. setVisibility(View.GONE);
                    limit = 0;
                    new getyoutube().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });

        Temp.isvideo=0;
    } catch (Exception e) {

            Log.w("RRRRRR",Log.getStackTraceString(e));
    }

}

    @Override
    protected void onResume() {
        super.onResume();
        if(Temp.isvideo==0)
        {
            refresh();
        }
        else
        {
            Temp.isvideo=0;
        }

    }

    public void refresh() {
        try {
            if (cd.isConnectingToInternet()) {
                nointernet. setVisibility(View.GONE);
                limit = 0;
                new getyoutube().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        } catch (Exception a) {
            Toasty.info(getApplicationContext(), (CharSequence) Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
    }

public class getyoutube extends AsyncTask<String, Void, String> {
    public void onPreExecute() {
        feedItems.clear();
        nointernet. setVisibility(View.GONE);
        list. setVisibility(View.GONE);
        shimmer_view_container.setVisibility(View.VISIBLE);
        shimmer_view_container.startShimmerAnimation();
        limit = 0;
    }
    public String doInBackground(String... arg0) {
        try {

            String link= Temp.weblink +"getinstagrampost.php";
            String data  = URLEncoder.encode("item", "UTF-8")
                    + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
            shimmer_view_container.setVisibility(View.GONE);
            if (result.contains(":%ok")) {
                String[] got = result.split(":%");
                int k = (got.length - 1) / 5;
                int m = -1;
                for (int i = 1; i <= k; i++) {
                    Instagram_Feed item = new Instagram_Feed();
                    m=m+1;
                    item.setMediatype(got[m]);
                    m=m+1;
                    item.setUrl(got[m]);
                    m=m+1;
                    item.setTitle(got[m]);
                    m=m+1;
                    item.setDim(got[m]);
                    m=m+1;
                    item.setVideosrc(got[m]);
                    feedItems.add(item);
                }
                listAdapter.notifyDataSetChanged();
                list.setVisibility(View.VISIBLE);
                shimmer_view_container.stopShimmerAnimation();
                shimmer_view_container.setVisibility(View.GONE);
                return;
            }
            shimmer_view_container.stopShimmerAnimation();
            shimmer_view_container.setVisibility(View.GONE);
            footerview. setVisibility(View.GONE);
        } catch (Exception e2) {

           // Log.w("EEEEEEE",Log.getStackTraceString(e2));
        }
    }
}

public class getyoutube1 extends AsyncTask<String, Void, String> {

    public void onPreExecute() {
        footerview.setVisibility(View.VISIBLE);
    }
    public String doInBackground(String... arg0) {
        try {

            String link= Temp.weblink +"getinstagrampost.php";
            String data  = URLEncoder.encode("item", "UTF-8")
                    + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
            if (result.contains("%:ok")) {
                String[] got = result.split("%:");
                int k = (got.length - 1) / 5;
                int m = -1;
                for (int i = 1; i <= k; i++) {
                    Instagram_Feed item = new Instagram_Feed();
                    m=m+1;
                    item.setMediatype(got[m]);
                    m=m+1;
                    item.setUrl(got[m]);
                    m=m+1;
                    item.setTitle(got[m]);
                    m=m+1;
                    item.setDim(got[m]);
                    m=m+1;
                    item.setVideosrc(got[m]);
                    feedItems.add(item);
                }
                return;
            }
            shimmer_view_container.stopShimmerAnimation();
            shimmer_view_container.setVisibility(View.GONE);
            footerview. setVisibility(View.GONE);
        } catch (Exception e2) {
        }
    }
}

}