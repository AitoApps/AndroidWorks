package com.mal_suthra;

import adapter.Position_Adapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import data.Position_Feed;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;

public class Act_Pos_Subcat extends AppCompatActivity {
    ImageView bookmark;
    final DataBase dataBase = new DataBase(this);
    Typeface face;
    ImageView favourits;
    private List<Position_Feed> feedItems;
    private Position_Adapter listAdapter;
    ListView listview;
    ImageView move_back;
    ProgressBar pb;
    TextView text;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvty_possubcat);
        try {
            favourits = (ImageView) findViewById(R.id.fvrts);
            pb = (ProgressBar) findViewById(R.id.pb);
            text = (TextView) findViewById(R.id.text);
            move_back = (ImageView) findViewById(R.id.moveback);
            bookmark = (ImageView) findViewById(R.id.bookmark);
            listview = (ListView) findViewById(R.id.listview);
            face = Typeface.createFromAsset(getAssets(), "app_fonts/malfont.ttf");
            text.setTypeface(face);
            text.setText(Static_Veriable.positions);
            feedItems = new ArrayList();
            listAdapter = new Position_Adapter(this, feedItems);
            listview.setAdapter(listAdapter);

            move_back.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });
            favourits.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    startActivity(new Intent(getApplicationContext(), Fvrt_POS.class));
                }
            });
            bookmark.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (dataBase.get_bokmark().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Sorry ! No bookmark found", 1).show();
                        return;
                    }
                    Static_Veriable.picid = Integer.parseInt(dataBase.get_bokmark());
                    startActivity(new Intent(getApplicationContext(), Position_View.class));
                }
            });
            data_loading();
        } catch (Exception e) {

           // Toasty.info(getApplicationContext(), Log.getStackTraceString(e), Toast.LENGTH_LONG).show();
        }
    }

    public void data_loading() {
        pb.setVisibility(View.VISIBLE);
        listview.setVisibility(View.INVISIBLE);
        int a = File_Positions.positionlist.length;
        for (int j = 1; j < a; j++) {
            Position_Feed item = new Position_Feed();
            item.settitle(File_Positions.positionlist[j]);
            feedItems.add(item);
        }
        pb.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
        listAdapter.notifyDataSetChanged();
    }


}
