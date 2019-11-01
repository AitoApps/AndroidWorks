package com.suthra_malayalam_web;

import adapter.Data_Adapter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import data.Data_Feed;
import java.util.ArrayList;
import java.util.List;

public class ReadView_Subcatogery extends AppCompatActivity {
    AdRequest adreq1;
    AdView adview;
    ImageView back;
    public String[] datalist;
    final DataBase db = new DataBase(this);
    Typeface face;
    private List<Data_Feed> feed;
    private Data_Adapter listAdapter;
    ListView listview;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvty_subcatogery);
        adreq1 = new Builder().build();
        text = (TextView) findViewById(R.id.text);
        adview = (AdView) findViewById(R.id.adView1);
        back = (ImageView) findViewById(R.id.moveback);
        listview = (ListView) findViewById(R.id.listview);
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        text.setText(File_Positions.catogery[Integer.parseInt(db.get_cat()) - 1]);
        text.setTypeface(face);
        feed = new ArrayList();
        listAdapter = new Data_Adapter(this, feed);
        listview.setAdapter(listAdapter);
        if (db.get_purchase().equalsIgnoreCase("")) {
            adview.setVisibility(View.VISIBLE);
            adview.loadAd(adreq1);
            adview.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    adview.loadAd(adreq1);
                }
            });
        } else {
            adview.setVisibility(View.GONE);
        }
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (db.get_cat().equalsIgnoreCase("1")) {
            datalist = File_Positions.motherhood;
            loading_data();
        } else if (db.get_cat().equalsIgnoreCase("3")) {
            datalist = File_Positions.asugangal;
            loading_data();
        } else if (db.get_cat().equalsIgnoreCase("4")) {
            datalist = File_Positions.food;
            loading_data();
        } else if (db.get_cat().equalsIgnoreCase("2")) {
            datalist = File_Positions.knowledge;
            loading_data();
        }
    }

    public void onBackPressed() {
        try {
            adview = null;
        } catch (Exception e) {
        }
        super.onBackPressed();
    }

    public void loading_data() {
        listview.setVisibility(View.INVISIBLE);
        try {
            feed.clear();
            int a = datalist.length / 2;
            int m = 0;
            for (int j = 1; j <= a; j++) {
                Data_Feed item = new Data_Feed();
                item.setid(datalist[m]);
                m=m+1;
                item.settitle(datalist[m]);
                m=m+1;
                feed.add(item);
            }
            listview.setVisibility(View.VISIBLE);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }
}
