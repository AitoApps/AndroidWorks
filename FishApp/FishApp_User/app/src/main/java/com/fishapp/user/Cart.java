package com.fishapp.user;

import adapter.Cart_ListAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import data.Cart_FeedItem;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    private List<Cart_FeedItem> feedItems;
    ListView list;
    private Cart_ListAdapter listAdapter;
    ImageView nodata;
    Button ordernow;
    ProgressBar pb;
    ProgressDialog pd;
    TextView title,txttotal;
    TextView totalcount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_cart);
        back = (ImageView) findViewById(R.id.back);
        nodata = (ImageView) findViewById(R.id.nodata);
        title = (TextView) findViewById(R.id.title);
        txttotal=findViewById(R.id.txttotal);
        totalcount = (TextView) findViewById(R.id.totalcount);
        list = (ListView) findViewById(R.id.list);
        ordernow = (Button) findViewById(R.id.ordernow);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        pb = (ProgressBar) findViewById(R.id.pb);
        feedItems = new ArrayList();
        listAdapter = new Cart_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        title.setText("Cart ");
        txttotal.setTypeface(face);
        title.setTypeface(face);
        ordernow.setText("Order Now ");
        ordernow.setTypeface(face);
        totalcount.setTypeface(face);
        ordernow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_Address.class));
            }
        });
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            loaddata();
        } catch (Exception e) {
        }
    }

    public void loaddata() {
        nodata.setVisibility(View.GONE);
        feedItems.clear();
        ArrayList<String> id1 = db.getcart();
        String[] k = (String[]) id1.toArray(new String[id1.size()]);
        int i = 0;
        while (i < k.length) {
            Cart_FeedItem item = new Cart_FeedItem();
            item.setPkey(k[i]);
            i++;
            item.setFishid(k[i]);
            i++;
            item.setFishname(k[i]);
            i++;
            item.setQty(k[i]);
            i++;
            item.setTotalprice(k[i]);
            i++;
            item.setImgsig(k[i]);
            i++;
            item.setOgunit(k[i]);
            i++;
            item.setOgqty(k[i]);
            i++;
            item.setOgprice(k[i]);
            feedItems.add(item);
            i++;
        }
        if (k.length > 0) {
            pb.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            listAdapter.notifyDataSetChanged();
        } else {
            nodata.setVisibility(View.VISIBLE);
        }
        calculatetotal();
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void showalertsucuss(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(R.id.text)).setTypeface(face);
        } catch (Exception e) {
        }
    }

    public void changeitem(int position, String pkey1) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            ArrayList<String> id1 = db.getcart_pkey(pkey1);
            String[] k1 = (String[]) id1.toArray(new String[id1.size()]);
            Cart_FeedItem item = new Cart_FeedItem();
            item.setPkey(k1[0]);
            item.setFishid(k1[1]);
            item.setFishname(k1[2]);
            item.setQty(k1[3]);
            item.setTotalprice(k1[4]);
            item.setImgsig(k1[5]);
            item.setOgunit(k1[6]);
            item.setOgqty(k1[7]);
            item.setOgprice(k1[8]);
            feedItems.add(position, item);
            calculatetotal();
        } catch (Exception e) {
        }
    }

    public void calculatetotal() {
        String rupee = getResources().getString(R.string.Rs);
        float gtotal = db.get_cartgrandtotal();
        if (gtotal > 0.0f) {
            txttotal.setVisibility(View.VISIBLE);
            ordernow.setVisibility(View.VISIBLE);
            totalcount.setVisibility(View.VISIBLE);
            totalcount.setText(db.getcartcount()+" Fish | "+rupee+String.format("%.2f", Float.valueOf(gtotal)));
        }
        else
        {
            ordernow.setVisibility(View.GONE);
            txttotal.setVisibility(View.GONE);
            totalcount.setVisibility(View.GONE);
        }

    }
}
