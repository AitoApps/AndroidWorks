package com.dlkitmaker_feeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_Sublist;
import data.Feed_Sublist;

public class Subkitlist extends AppCompatActivity {
    private Adapter_Sublist listAdapter;
    private List<Feed_Sublist> feedItems;
    RecyclerView recylerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_subkits);
        recylerview=(RecyclerView)findViewById(R.id.recylerview);


        feedItems = new ArrayList<Feed_Sublist>();
        listAdapter = new Adapter_Sublist(this,feedItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerview.setLayoutManager(linearLayoutManager);
        recylerview.setAdapter(listAdapter);

        loaddata();

    }

    public void loaddata()
    {

        feedItems.clear();
        String[] teams={};
        if(Temp.whichteam==1)
        {
            teams= TeamDB.one;
        }
        else if(Temp.whichteam==2)
        {
            teams= TeamDB.two;
        }
        else if(Temp.whichteam==3)
        {
            teams= TeamDB.three;
        }
        else if(Temp.whichteam==4)
        {
            teams= TeamDB.four;
        }
        else if(Temp.whichteam==5)
        {
            teams= TeamDB.five;
        }
        else if(Temp.whichteam==6)
        {
            teams= TeamDB.six;
        }
        else if(Temp.whichteam==7)
        {
            teams= TeamDB.seven;
        }
        else if(Temp.whichteam==8)
        {
            teams= TeamDB.eight;
        }
        else if(Temp.whichteam==9)
        {
            teams= TeamDB.nine;
        }
        else if(Temp.whichteam==10)
        {
            teams= TeamDB.ten;
        }
        else if(Temp.whichteam==11)
        {
            teams= TeamDB.eleven;
        }
        else if(Temp.whichteam==12)
        {
            teams= TeamDB.twlv;
        }
        else if(Temp.whichteam==13)
        {
            teams= TeamDB.thirteen;
        }

        for(int i=0;i<teams.length;i++)
        {

            Feed_Sublist item = new Feed_Sublist();
            item.setteamid(teams[i]);
            i++;
            item.setteamname(teams[i]);
            feedItems.add(item);
        }
        listAdapter.notifyDataSetChanged();
    }

}
