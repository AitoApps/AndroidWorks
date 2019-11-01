package com.dialybill_shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.Productlist_Adapter;
import data.Deliveryboys_FeedItem;
import data.Productlist_FeedItem;
import data.Productlist_admin_FeedItem;

public class Product_Management extends AppCompatActivity {

    ImageView back;
    TextView text;
    RecyclerView recylerview;
    ImageView nointernet,heart,nodata,addnew;
    ConnectionDetecter cd;
    public List<Productlist_FeedItem> feedItems;
    public Productlist_Adapter listAdapter;
    final ProductDatabase pdb=new ProductDatabase(this);
    ProgressDialog pd;
    final UserDatabaseHandler udb=new UserDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__management);
        addnew=findViewById(R.id.addnew);
        back=findViewById(R.id.back);
        text=findViewById(R.id.text);
        recylerview=findViewById(R.id.recylerview);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        cd=new ConnectionDetecter(this);
        pd=new ProgressDialog(this);
        feedItems = new ArrayList();
        listAdapter = new Productlist_Adapter(this, feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        recylerview.setAdapter(listAdapter);

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Add_AdminProducts.class));

            }
        });

    }
    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }
    @Override
    protected void onResume() {


        if(pdb.getproductcount()>0)
        {
            loadmaindata();
        }
        else
        {
            if(cd.isConnectingToInternet())
            {
                new loadstatus().execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(),Temp.nointernet,Toast.LENGTH_SHORT).show();
            }
        }

        super.onResume();
    }


    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getproductlistforshop.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.get_shopid(), "UTF-8");
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
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 9;
                        int m = -1;
                        pdb.deleteproduct();
                        for (int i = 1; i <= k; i++) {
                            m=m+1;
                            int a1=m;
                            m=m+1;
                            int a2=m;
                            m=m+1;
                            int a3=m;
                            m=m+1;
                            int a4=m;
                            m=m+1;
                            int a5=m;
                            m=m+1;
                            int a6=m;
                            m=m+1;
                            int a7=m;
                            m=m+1;
                            int a8=m;
                            m=m+1;
                            int a9=m;
                            pdb.addproduct(got[a1],got[a2],got[a3],got[a4],got[a5],got[a6],got[a7],got[a8],got[a9]);
                        }
                    } catch (Exception e2) {
                    }

                    pd.dismiss();

                    loadmaindata();
                }
                else
                {
                    pd.dismiss();
                }

            } catch (Exception e3) {
            }
        }
    }

    public void changeitem_base(int position, String sn1, String price1,String minimum1,String minimumunit1) {
        try {

            Productlist_FeedItem item1 = (Productlist_FeedItem)feedItems.get(position);
            Productlist_FeedItem item = new Productlist_FeedItem();
            item.setSn(item1.getSn());
            item.setItemid(item1.getItemid());
            item.setItemname(item1.getItemname());
            item.setPrice(price1);
            item.setMinimum(minimum1);
            item.setMinimumunit(minimumunit1);
            item.setImgisg(item1.getImgisg());
            item.setStatus(item1.getStatus());
            item.setOgunit(item1.getOgunit());
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            feedItems.add(position, item);

        } catch (Exception e) {
        }
    }

    public void changeitem(int position, String sn1, String status1) {
        try {

            Productlist_FeedItem item1 = (Productlist_FeedItem)feedItems.get(position);
            Productlist_FeedItem item = new Productlist_FeedItem();
            item.setSn(item1.getSn());
            item.setItemid(item1.getItemid());
            item.setItemname(item1.getItemname());
            item.setPrice(item1.getPrice());
            item.setMinimum(item1.getMinimum());
            item.setMinimumunit(item1.getMinimumunit());
            item.setImgisg(item1.getImgisg());
            item.setStatus(status1);
            item.setOgunit(item1.getOgunit());
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            feedItems.add(position, item);

        } catch (Exception e) {
        }
    }

    public void loadmaindata() {
        heart.setVisibility(View.VISIBLE);
        nodata.setVisibility(View.GONE);
        feedItems.clear();

        ArrayList<String> id1 = pdb.getproducts();
        String[] k = (String[]) id1.toArray(new String[id1.size()]);


        for(int i=0;i<k.length;i++)
        {
            Productlist_FeedItem item=new Productlist_FeedItem();
            item.setSn(k[i]);
            i++;
            item.setItemid(k[i]);
            i++;
            item.setItemname(k[i]);
            i++;
            item.setPrice(k[i]);
            i++;
            item.setMinimum(k[i]);
            i++;
            item.setMinimumunit(k[i]);
            i++;
            item.setImgisg(k[i]);
            i++;
            item.setStatus(k[i]);
            i++;
            item.setOgunit(k[i]);
            feedItems.add(item);
        }

        if(k.length<=0)
        {
            nodata.setVisibility(View.VISIBLE);
        }
        heart.setVisibility(View.GONE);
        recylerview.setVisibility(View.VISIBLE);
        listAdapter.notifyDataSetChanged();

    }
}
