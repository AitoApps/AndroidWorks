package com.sanji;

import adapter.Home_Product_List_ListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Home_Product_List_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    ConnectionDetecter cd;
    Context context;
    public DatabaseHandler db;

    public List<Home_Product_List_FeedItem> feedItems;
    View footerView;
    ImageView heart;
    int limit = 0;

    public Home_Product_List_ListAdapter listAdapter;
    TextView loctext;
    ImageView nointernet;
    HeaderAndFooterRecyclerView recylerview;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        loctext = (TextView) view.findViewById(R.id.loctext);
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        if (db.get_locationaddress().equalsIgnoreCase("")) {
            change_location("Select Your Locality");
        } else {
            change_location(db.get_locationaddress());
        }
        heart = (ImageView) view.findViewById(R.id.heart);
        nointernet = (ImageView) view.findViewById(R.id.nointernet);
        recylerview = (HeaderAndFooterRecyclerView) view.findViewById(R.id.recylerview);
        loctext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Location_Picker.class));
            }
        });
        feedItems = new ArrayList();
        listAdapter = new Home_Product_List_ListAdapter(getActivity(), feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recylerview.setAdapter(listAdapter);
        footerView = LayoutInflater.from(context).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
        recylerview.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE)
        Glide.with((Fragment) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
            }
        });
        if (cd.isConnectingToInternet()) {
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void change_location(String location) {
        loctext.setText(location);
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getshops_home.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(db.get_latitude()+","+db.get_longtitude(), "UTF-8");
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
                    try {
                        feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Home_Product_List_FeedItem item = new Home_Product_List_FeedItem();
                            m=m+1;
                            item.setcatid(got[m]);
                            m=m+1;
                            item.setcatname(got[m]);
                            m=m+1;
                            item.setshops(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    heart.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                recylerview.setVisibility(View.GONE);
                heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }
}
