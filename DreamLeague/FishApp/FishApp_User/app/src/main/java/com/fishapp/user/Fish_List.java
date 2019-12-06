package com.fishapp.user;

import adapter.FishList_Adapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import adapter.Fishlist_HeadCatogery_Adapter;
import data.Fishlist_Feeditems;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Fish_List extends AppCompatActivity {
    
    public FishList_Adapter adapter;
    public Fishlist_HeadCatogery_Adapter catadapter;
    ImageView back;
    ImageView carticon;
    RelativeLayout cartlyt;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    public List<Fishlist_Feeditems> feeditem;
    RecyclerView fishcatlist,fishlist;
    ImageView heart;
    TextView itemcount;
    public int limit = 0;
    ImageView nodata;
    ImageView nointernet;
    TextView text;
    String rupee = "";
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    TextView viewcart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_fish__list);
        heart = (ImageView) findViewById(R.id.heart);
        rupee = getResources().getString(R.string.Rs);
        nodata = (ImageView) findViewById(R.id.nodata);
        cd = new ConnectionDetecter(this);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        fishcatlist=findViewById(R.id.fishcatlist);
        fishlist = (RecyclerView) findViewById(R.id.fishlist);
        itemcount = (TextView) findViewById(R.id.itemcount);
        viewcart = (TextView) findViewById(R.id.viewcart);
        carticon = (ImageView) findViewById(R.id.carticon);
        cartlyt = (RelativeLayout) findViewById(R.id.cartlyt);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        back = (ImageView) findViewById(R.id.back);
        text.setTypeface(face);
        itemcount.setTypeface(face);
        viewcart.setTypeface(face);
        text.setText(Temp.clientcatname);
        feeditem = new ArrayList();
        adapter = new FishList_Adapter(this, feeditem);
        catadapter = new Fishlist_HeadCatogery_Adapter(this, Temp.feeditem);

        fishlist.setLayoutManager(new GridLayoutManager(this, 1));
        fishlist.setAdapter(adapter);

        fishcatlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fishcatlist.setAdapter(catadapter);
        catadapter.notifyDataSetChanged();
        fishcatlist.getLayoutManager().scrollToPosition(Temp.movepos);

        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }
        viewcart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
            }
        });
        carticon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
            }
        });
        cartlyt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
            }
        });
    }

    public void add_tocart(String cart_fishid1, String cart_fishname1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_ogunit1, String cart_ogqty1, String cart_ogprice1) {
        if (!db.get_cartexist(cart_fishid1).equalsIgnoreCase("")) {
            db.addcart_existupdate(cart_fishid1, cart_qty1, cart_totalprice1);
        } else {
            db.addcart(cart_fishid1, cart_fishname1, cart_qty1, cart_totalprice1, cart_imgsig1, cart_ogunit1, cart_ogqty1, cart_ogprice1);
        }
        refreshcart();
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshcart();
    }

    public void refreshcart() {
        float gtotal = db.get_cartgrandtotal();

        if (gtotal > 0.0f) {
            cartlyt.setVisibility(View.VISIBLE);
            itemcount.setText(db.getcartcount()+" Fish | "+rupee+String.format("%.2f", Float.valueOf(gtotal)));
        }
        else
        {
             cartlyt.setVisibility(View.GONE);
        }

    }

    public void reload()
    {
        try
        {
            fishcatlist.setAdapter(catadapter);
            catadapter.notifyDataSetChanged();
            fishcatlist.getLayoutManager().scrollToPosition(Temp.movepos);
        }
        catch (Exception a)
        {

        }
        text.setText(Temp.clientcatname);
        new loadstatus().execute();

    }
    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            fishlist.setVisibility(View.INVISIBLE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"getfishlist_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.getareaid()+":%"+Temp.clientcatid, "UTF-8");
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
                if (result.trim().contains(":%ok")) {
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    feeditem.clear();
                    for (int i = 1; i <= k; i++) {
                        Fishlist_Feeditems item = new Fishlist_Feeditems();
                        m=m+1;
                        item.setSn(got[m].trim());
                        m=m+1;
                        item.setFishname(got[m]);
                        m=m+1;
                        item.setDiscription(got[m]);
                        m=m+1;
                        item.setQty(got[m]);
                        m=m+1;
                        item.setUnit(got[m]);
                        m=m+1;
                        item.setPrice(got[m]);
                        m=m+1;
                        item.setStock(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
                        feeditem.add(item);
                    }
                    fishlist.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    return;
                }
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}
