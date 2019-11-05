package com.mal_suthra;

import adapter.Fvrt_Adapter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import data.Fvrt_Feed;
import java.util.ArrayList;
import java.util.List;

public class Fvrt_POS extends AppCompatActivity {
    ImageView back;
    final DataBase dataBase = new DataBase(this);
    Typeface face;
    private List<Fvrt_Feed> feeds;
    private Fvrt_Adapter listAdapter;
    ListView listview;
    ImageView nodata;
    ProgressBar pb;
    TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvy_fvrtposition);
        nodata = (ImageView) findViewById(R.id.emptydata);
        back = (ImageView) findViewById(R.id.moveback);
        listview = (ListView) findViewById(R.id.listview);
        text = (TextView) findViewById(R.id.text);
        pb = (ProgressBar) findViewById(R.id.pb);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
        text.setTypeface(face);
        text.setText(Static_Veriable.fvrts);
        feeds = new ArrayList();
        listAdapter = new Fvrt_Adapter(this, feeds);
        listview.setAdapter(listAdapter);
    }
   @Override
    public void onResume() {
        fetching_data();
        super.onResume();
    }

    public void fetching_data() {
        pb.setVisibility(View.VISIBLE);
        listview.setVisibility(View.INVISIBLE);
        try {
            feeds.clear();
            ArrayList<String> id1 = dataBase.getfvrt();
            String[] c = (String[]) id1.toArray(new String[id1.size()]);
            if (c.length > 0) {
                for (int j = 0; j < c.length; j++) {
                    Fvrt_Feed item = new Fvrt_Feed();
                    item.setid(c[j]);
                    item.settitle(File_Positions.positionlist[Integer.parseInt(c[j])]);
                    feeds.add(item);
                }
                pb.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
                return;
            }
            nodata.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    public void removeitem(int position) {
        feeds.remove(position);
        listAdapter.notifyDataSetChanged();
        try {
            if (feeds.size() == 0) {
                nodata.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }
    }
}
