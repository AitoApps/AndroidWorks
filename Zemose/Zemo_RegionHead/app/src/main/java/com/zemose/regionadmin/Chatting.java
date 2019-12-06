package com.zemose.regionadmin;

import adapter.ChatHeads_ListAdapter;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import data.ChatHeads_FeedItem;
import java.util.ArrayList;

public class Chatting extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    final ChatDB cdb = new ChatDB(this);
    private ArrayList<ChatHeads_FeedItem> feedItems;
    public int limit = 0;
    private ChatHeads_ListAdapter listAdapter;
    ImageView loading;
    ImageView nodata;
    ProgressDialog pd;
    RecyclerView recylerview;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_chatting);
        this.pd = new ProgressDialog(this);
        this.cd = new ConnectionDetecter(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.feedItems = new ArrayList<>();
        this.listAdapter = new ChatHeads_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Chatting.this.onBackPressed();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.limit = 0;
        this.feedItems.clear();
        loaddata();
    }

    public void loaddata() {
        ChatDB chatDB = this.cdb;
        StringBuilder sb = new StringBuilder();
        sb.append(this.limit);
        sb.append("");
        ArrayList<String> id1 = chatDB.get_chatheads(sb.toString());
        String[] c = (String[]) id1.toArray(new String[id1.size()]);
        if (c.length > 0) {
            int a = c.length / 5;
            int m = -1;
            for (int j = 1; j <= a; j++) {
                ChatHeads_FeedItem item = new ChatHeads_FeedItem();
                int m2 = m + 1;
                item.setpkey(c[m2]);
                int m3 = m2 + 1;
                item.setchattime(c[m3]);
                int m4 = m3 + 1;
                item.setuserid(c[m4]);
                int m5 = m4 + 1;
                item.setusername(c[m5]);
                m = m5 + 1;
                item.setissupplier(c[m]);
                this.feedItems.add(item);
            }
            this.limit += 100;
            this.recylerview.setVisibility(View.VISIBLE);
            this.listAdapter.notifyDataSetChanged();
            this.nodata.setVisibility(View.GONE);
        } else if (this.feedItems.size() == 0) {
            this.nodata.setVisibility(View.VISIBLE);
            this.recylerview.setVisibility(View.GONE);
        }
    }
}
