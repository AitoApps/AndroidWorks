package com.kamanamnew;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import adapter.Position_Adapter;
import data.Position_Feed;

public class Position_List extends AppCompatActivity {
    Typeface face;
    private List<Position_Feed> feedItems;
    private Position_Adapter listAdapter;
    ListView listview;
    ImageView move_back;
    ProgressBar pb;
    TextView text;
    AdView adView1;
    AdRequest adreq1;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position__list);
        try {
            pb = (ProgressBar) findViewById(R.id.pb);
            text = (TextView) findViewById(R.id.text);
            move_back = (ImageView) findViewById(R.id.moveback);
            listview = (ListView) findViewById(R.id.listview);
            face = Typeface.createFromAsset(getAssets(), "fonts/malayalayam.ttf");
            text.setTypeface(this.face);
            text.setText("പൊസിഷനുകള്\u200D ");
            feedItems = new ArrayList();
            listAdapter = new Position_Adapter(this, this.feedItems);
            listview.setAdapter(this.listAdapter);

            adView1=findViewById(R.id.adView1);
            adreq1 = new AdRequest.Builder().build();
            move_back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                     onBackPressed();
                }
            });

            try {
                adView1.setAdListener(new AdListener() {
                    public void onAdFailedToLoad(int errorCode) {
                        try {
                            if (count <= 20) {
                                adView1.loadAd(adreq1);
                                count++;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            } catch (Exception e) {
            }

            loading();
        } catch (Exception e) {


        }
    }

    public void onResume() {
        super.onResume();
        count = 0;
        try {
            adView1.loadAd(adreq1);
        } catch (Exception e) {
        }
    }

    public void loading() {
        this.pb.setVisibility(View.VISIBLE);
        this.listview.setVisibility(View.INVISIBLE);
        int a = PositionTitlls.positionlist.length;
        for (int j = 1; j < a; j++) {
            Position_Feed item = new Position_Feed();
            item.settitle(PositionTitlls.positionlist[j]);
            this.feedItems.add(item);
        }
        this.pb.setVisibility(View.GONE);
        this.listview.setVisibility(View.VISIBLE);
        this.listAdapter.notifyDataSetChanged();
    }
}

