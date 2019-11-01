package com.daydeal;

import adapter.Catogery_ListAdapter;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Catogery_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    TextView appname;
    ConnectionDetecter cd;
    Context context;
    DatabaseHandler db;
    /* access modifiers changed from: private */
    public List<Catogery_FeedItem> feedItems;
    View footerView;
    ImageView heart;
    /* access modifiers changed from: private */
    public Catogery_ListAdapter listAdapter;
    TextView location;
    ImageView locationicon;
    RelativeLayout lytrequestmoney;
    RelativeLayout lytshopping;
    RelativeLayout lytwallethistory;
    ImageView nointernet;
    HeaderAndFooterRecyclerView recylerview;
    TextView txtrequestmony;
    TextView txtshopping;
    TextView txtwallet;
    TextView txtwallethistory;
    TextView walletamount;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        appname = (TextView) view.findViewById(R.id.appname);
        txtwallet = (TextView) view.findViewById(R.id.txtwallet);
        walletamount = (TextView) view.findViewById(R.id.walletamount);
        txtwallethistory = (TextView) view.findViewById(R.id.txtwallethistory);
        txtshopping = (TextView) view.findViewById(R.id.txtshopping);
        txtrequestmony = (TextView) view.findViewById(R.id.txtrequestmony);
        location = (TextView) view.findViewById(R.id.location);
        lytrequestmoney = (RelativeLayout) view.findViewById(R.id.lytrequestmoney);
        lytshopping = (RelativeLayout) view.findViewById(R.id.lytshopping);
        lytwallethistory = (RelativeLayout) view.findViewById(R.id.lytwallethistory);
        recylerview = (HeaderAndFooterRecyclerView) view.findViewById(R.id.recylerview);
        nointernet = (ImageView) view.findViewById(R.id.nointernet);
        heart = (ImageView) view.findViewById(R.id.heart);
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        locationicon = (ImageView) view.findViewById(R.id.locationicon);
        feedItems = new ArrayList();
        listAdapter = new Catogery_ListAdapter(getActivity(), feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recylerview.setAdapter(listAdapter);
        footerView = LayoutInflater.from(context).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
        recylerview.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE);
        Glide.with(getContext()).load(Integer.valueOf(R.drawable.loading)).into(heart);
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
        locationicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(context, Pick_Location.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        return view;
    }

    public void setlocation() {
        location.setText(db.getaddress());
        if (cd.isConnectingToInternet()) {
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(0);
        }
    }
    public class loadstatus extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            recylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getcatogery_home_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(db.getlatitude()+":%"+db.getlongtitude(), "UTF-8");
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
                            Catogery_FeedItem item = new Catogery_FeedItem();
                            m=m+1;
                            item.setSn(got[m]);
                            m=m+1;
                            item.setCatname(got[m]);
                            m=m+1;
                            item.setImgsig(got[m]);
                            feedItems.add(item);
                        }
                    } catch (Exception a) {
                        // Toasty.info(context, Log.getStackTraceString(a), 1).show();
                    }
                    heart.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }
                recylerview.setVisibility(View.GONE);
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}
