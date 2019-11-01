package com.sanji_shops;

import adapter.Searchproductlist_ListAdapter;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import data.Search_Productlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Search_Product_List extends AppCompatActivity {
    ImageView back;
    Spinner catogery;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    public List<Search_Productlist_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    ImageView heart;
    public int limit = 0;
    public Searchproductlist_ListAdapter listAdapter;
    List<String> lst_catid = new ArrayList();
    List<String> lst_catname = new ArrayList();
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    RecyclerView recylerview;
    EditText search;
    TextView text;
    public String txt_catid = "";
    TextView txtfilter;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Search_Product_List.nointernet.setVisibility(View.GONE);
            Search_Product_List.nodata.setVisibility(View.GONE);
            Search_Product_List.recylerview.setVisibility(View.GONE);
            Search_Product_List.heart.setVisibility(View.VISIBLE);
            Search_Product_List.limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("get_admin_productsearchlist.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Search_Product_List.txt_catid, "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb3.toString();
                    }
                    sb3.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            try {
                if (result.contains(":%ok")) {
                    Search_Product_List.feedItems.clear();
                    String[] got = result.split(":%");
                    int k = (got.length - 1) / 11;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        Search_Productlist_FeedItem item = new Search_Productlist_FeedItem();
                        int m2 = m + 1;
                        item.setsn(got[m2]);
                        int m3 = m2 + 1;
                        item.setitemname(got[m3]);
                        int m4 = m3 + 1;
                        item.setofferprice(got[m4]);
                        int m5 = m4 + 1;
                        item.setorginalprice(got[m5]);
                        int m6 = m5 + 1;
                        item.setstatus(got[m6]);
                        int m7 = m6 + 1;
                        item.setdiscription(got[m7]);
                        int m8 = m7 + 1;
                        item.setimgsig1(got[m8]);
                        int m9 = m8 + 1;
                        item.setcatid(got[m9]);
                        int m10 = m9 + 1;
                        item.setUnittype(got[m10]);
                        int m11 = m10 + 1;
                        item.setMinorder(got[m11]);
                        m = m11 + 1;
                        item.setItemkeyword(got[m]);
                        Search_Product_List.feedItems.add(item);
                    }
                    Search_Product_List.heart.setVisibility(View.GONE);
                    Search_Product_List.recylerview.setVisibility(View.VISIBLE);
                    Search_Product_List.listAdapter.notifyDataSetChanged();
                    return;
                }
                Search_Product_List.nodata.setVisibility(View.VISIBLE);
                Search_Product_List.heart.setVisibility(View.GONE);
                Search_Product_List.footerview.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_search__product__list);
        heart = (ImageView) findViewById(R.id.heart);
        search = (EditText) findViewById(R.id.search);
        pd = new ProgressDialog(this);
        catogery = (Spinner) findViewById(R.id.catogery);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        txtfilter = (TextView) findViewById(R.id.txtfilter);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Search_Product_List.onBackPressed();
            }
        });
        recylerview = (RecyclerView) findViewById(R.id.recylerview);
        nodata = (ImageView) findViewById(R.id.nodata);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        ArrayList<String> id1 = db.getcatid();
        String[] result = (String[]) id1.toArray(new String[id1.size()]);
        lst_catid.clear();
        lst_catname.clear();
        lst_catid.add("0");
        lst_catname.add("Select Product Catogery");
        int i = 0;
        while (i < result.length) {
            lst_catid.add(result[i]);
            int i2 = i + 1;
            lst_catname.add(result[i2]);
            i = i2 + 1;
        }
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, 17367048, lst_catname) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(Search_Product_List.face);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(Search_Product_List.face);
                return v;
            }
        };
        dataAdapter1.setDropDownViewResource(17367049);
        catogery.setAdapter(dataAdapter1);
        catogery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Search_Product_List search_Product_List = Search_Product_List.this;
                StringBuilder sb = new StringBuilder();
                sb.append((String) Search_Product_List.lst_catid.get(arg2));
                sb.append("");
                search_Product_List.txt_catid = sb.toString();
                if (!Search_Product_List.txt_catid.equalsIgnoreCase("0")) {
                    new loadstatus().execute(new String[0]);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        feedItems = new ArrayList();
        listAdapter = new Searchproductlist_ListAdapter(this, feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(this, 3));
        recylerview.setAdapter(listAdapter);
        text = (TextView) findViewById(R.id.text);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setText("Product List");
        text.setTypeface(face);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        txtfilter.setTypeface(face);
        search.setTypeface(face);
        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                Search_Product_List.filter(editable.toString());
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (Search_Product_List.cd.isConnectingToInternet()) {
                    Search_Product_List.nointernet.setVisibility(View.GONE);
                    Search_Product_List.limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Search_Product_List.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Search_Product_List.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void filter(String text2) {
        List<Search_Productlist_FeedItem> temp = new ArrayList<>();
        for (Search_Productlist_FeedItem d : feedItems) {
            if (text2.equalsIgnoreCase("")) {
                temp.add(d);
            } else if (d.getitemname().toLowerCase().contains(text2.toLowerCase())) {
                temp.add(d);
            }
        }
        listAdapter.updateList(temp);
    }
}
