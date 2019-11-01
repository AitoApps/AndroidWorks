package com.chintha_admin;

import adapter.UploadtoYoutubeListAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import data.UploadtoYoutube_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class InstagramToYoutube extends AppCompatActivity {
    ConnectionDetecter cd;
    public List<UploadtoYoutube_FeedItem> feedItems;
    ListView list;
    public UploadtoYoutubeListAdapter listAdapter;
    ImageView nointernet;
    ProgressBar pb;
    ProgressDialog pd;
    int position = 0;
    WebView web;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_instagram_to_youtube);
        list = (ListView) findViewById(R.id.list);
        pb = (ProgressBar) findViewById(R.id.pb);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        LayoutInflater layoutInflater = getLayoutInflater();
        cd = new ConnectionDetecter(this);
        feedItems = new ArrayList();
        listAdapter = new UploadtoYoutubeListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        pd = new ProgressDialog(this);
        web = (WebView) findViewById(R.id.web);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        CookieSyncManager.createInstance(this);
        web.setWebViewClient(new MyBrowser());
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.getSettings().setUserAgentString("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
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

    public void removeitem(int position2) {
        feedItems.remove(position2);
        listAdapter.notifyDataSetChanged();
    }

    public void openweb(int pos, String id) {
        position = pos;
        web.setVisibility(View.VISIBLE);
        WebView webView = web;
        webView.loadUrl(Tempvariable.weblink+"getyoutube_authrization.php?sn="+id);
    }

    public void onBackPressed() {
        if (web.getVisibility() == 0) {
            web.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }
    public class loadreport extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            web.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            nointernet.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"getvideoforyoutubeupload.php";
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
                    int k = (got.length - 1) / 3;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        UploadtoYoutube_FeedItem item = new UploadtoYoutube_FeedItem();
                        m=m+1;
                        item.setSn(got[m].trim());
                        m=m+1;
                        item.setTitle(got[m]);
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
    private class MyBrowser extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(24)
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.contains("videouploaded.php")) {
                web.setVisibility(View.INVISIBLE);
                feedItems.remove(position);
                listAdapter.notifyDataSetChanged();
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    }
}
