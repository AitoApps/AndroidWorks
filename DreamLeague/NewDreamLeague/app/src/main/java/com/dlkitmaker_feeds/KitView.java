package com.dlkitmaker_feeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_Kitview;
import data.Feed_KitView;

public class KitView extends AppCompatActivity {
    RecyclerView recylerview;
    final DB db=new DB(this);
    private Adapter_Kitview listAdapter;
    private List<Feed_KitView> feedItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_kitview);
        recylerview=(RecyclerView)findViewById(R.id.recylerview);
        feedItems = new ArrayList<Feed_KitView>();
        listAdapter = new Adapter_Kitview(this,feedItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerview.setLayoutManager(linearLayoutManager);
        recylerview.setAdapter(listAdapter);
        loaddata();
    }
    public void loaddata()
    {

        feedItems.clear();
        ArrayList<String> id1=db.get_teamlist(Temp.teamid);
        String[] got=id1.toArray(new String[id1.size()]);
        int k=(got.length)/2;
        int m=-1;
        for(int i=1;i<=k;i++)
        {
            Feed_KitView item = new Feed_KitView();
            m++;
            item.setkitname(got[m]);
            m++;
            item.setimgurl(got[m]);
            feedItems.add(item);
        }
        listAdapter.notifyDataSetChanged();
    }
}
