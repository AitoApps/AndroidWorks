package com.daydeal_admin;

import adapter.Offerlist_Shops_ListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import data.Offerlist_Shops_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OfferList_Shops extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    Typeface face;

    public List<Offerlist_Shops_FeedItem> feedItems;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    ListView list;

    public Offerlist_Shops_ListAdapter listAdapter;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_offer_list__shops);
        heart = (ImageView) findViewById(R.id.heart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        list = (ListView) findViewById(R.id.list);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
        list.addFooterView(footerview);
        footerview.setVisibility(View.GONE);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setText("Offers List");
        text.setTypeface(face);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        feedItems = new ArrayList();
        listAdapter = new Offerlist_Shops_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
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
    }


    public void onResume() {
        super.onResume();
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void changeitem(int position, String sn1, String itemname1, String offerprice1, String orginalprice1, String discription1, String imgsig1, String status1, String itemkeyword1, String cashback1) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            Offerlist_Shops_FeedItem item = new Offerlist_Shops_FeedItem();
            item.setsn(sn1);
            item.setitemname(itemname1);
            item.setofferprice(offerprice1);
            item.setorginalprice(orginalprice1);
            item.setstatus(status1);
            item.setdiscription(discription1);
            item.setimgsig1(imgsig1);
            item.setCashback(cashback1);
            item.setItemkeyword(itemkeyword1);
            feedItems.add(position, item);
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

                String link= Temp.weblink +"getproductlist_byshopid_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+Temp.edit_shopid, "UTF-8");
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
                    int k = (got.length - 1) / 9;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Offerlist_Shops_FeedItem item = new Offerlist_Shops_FeedItem();
                        m=m+1;
                        item.setsn(got[m]);
                        m=m+1;
                        item.setitemname(got[m]);
                        m=m+1;
                        item.setofferprice(got[m]);
                        m=m+1;
                        item.setorginalprice(got[m]);
                        m=m+1;
                        item.setstatus(got[m]);
                        m=m+1;
                        item.setdiscription(got[m]);
                        m=m+1;
                        item.setimgsig1(got[m]);
                        m=m+1;
                        item.setCashback(got[m]);
                        m=m+1;
                        item.setItemkeyword(got[m]);
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
