package com.fishapp.user;

import adapter.Useraddress_ListAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;

import data.User_Address_Feeditem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class User_Address extends AppCompatActivity {
    ImageView addaddress;
    public String addresssn;
    ImageView back;
    public String cart_fishid1="";
    public String cart_price1="";
    public String cart_qty1="";
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    Typeface face1;

    public List<User_Address_Feeditem> feedItems;
    ImageView heart;
    ListView list;

    public Useraddress_ListAdapter listAdapter;
    ImageView noaddress;
    ImageView nointernet;
    Button ordernow;
    ProgressDialog pd;
    TextView text;
    UserDatabaseHandler udb = new UserDatabaseHandler(this);
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_user__address);
        cd = new ConnectionDetecter(this);
        heart = (ImageView) findViewById(R.id.heart);
        text = (TextView) findViewById(R.id.text);
        back = (ImageView) findViewById(R.id.back);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        list = (ListView) findViewById(R.id.list);
         noaddress =  findViewById(R.id.noaddress);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        addaddress = (ImageView) findViewById(R.id.addaddress);
        ordernow = (Button) findViewById(R.id.ordernow);

        ordernow.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        text.setText("Address ");
        text.setTypeface(face);
        ordernow.setText("Order Now ");
        addaddress.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.isaddressedit = 0;
                startActivity(new Intent(getApplicationContext(), Add_Address.class));
            }
        });

        noaddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.isaddressedit = 0;
                startActivity(new Intent(getApplicationContext(), Add_Address.class));
            }
        });
        feedItems = new ArrayList();
        listAdapter = new Useraddress_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        ordernow.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    if (db.getselectaddress().equalsIgnoreCase("")) {
                        Temp.isaddressedit = 0;
                        startActivity(new Intent(getApplicationContext(), Add_Address.class));
                        return;
                    }
                    addresssn = db.getselectaddress();
                    ArrayList<String> id1 = db.getcart1();
                    String[] k = (String[]) id1.toArray(new String[id1.size()]);
                    int i = 0;
                    while (i < k.length) {
                        if (cart_fishid1.equalsIgnoreCase("")) {
                            cart_fishid1 = k[i];
                        } else {
                            cart_fishid1=cart_fishid1+":%"+k[i];
                        }
                        i = i + 1;
                        if (cart_qty1.equalsIgnoreCase("")) {
                            cart_qty1 = k[i];
                        } else {
                            cart_qty1=cart_qty1+":%"+k[i];
                        }
                        i = i + 1;
                        if (cart_price1.equalsIgnoreCase("")) {
                            cart_price1 = k[i];
                        } else {
                            cart_price1=cart_price1+":%"+k[i];
                        }
                        i = i + 1;
                    }
                    new placeorder().execute(new String[0]);
                    return;
                }
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    new loadaddress().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
            }
        });
        Glide.with(this).load(R.drawable.loading).into(heart);
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                new loadaddress().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public void uncheckall() {
        int itemsCount = list.getChildCount();
        for (int i = 0; i < itemsCount; i++) {
            ((RadioButton) list.getChildAt(i).findViewById(R.id.radio1)).setChecked(false);
        }
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void uploadsucess() {
        try {
            final Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(1);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog3.setContentView(R.layout.home_dlivery_ok);
            dialog3.setCancelable(false);
            TextView txtmsg = (TextView) dialog3.findViewById(R.id.txtmsg);
            txtmsg.setTypeface(face1);
            txtmsg.setText("Thank you ! We recived your order and we will call you for verification.");
            Button upload3 = (Button) dialog3.findViewById(R.id.upload);
            upload3.setTypeface(face1);
            upload3.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    finish();
                    dialog3.dismiss();
                }
            });
            dialog3.show();
        } catch (Exception e) {
        }
    }

    public class loadaddress extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            nointernet.setVisibility(View.GONE);
            ordernow.setVisibility(View.GONE);
            noaddress.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            uncheckall();
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"getuseraddress.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.get_userid(), "UTF-8");
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
                if (result.contains("%:ok")) {
                    feedItems.clear();
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 8;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        User_Address_Feeditem item = new User_Address_Feeditem();
                        m=m+1;
                        item.setsn(got[m]);
                        m=m+1;
                        item.setuser_name(got[m]);
                        m=m+1;
                        item.setuser_place(got[m]);
                        m=m+1;
                        item.setuser_mobile1(got[m]);
                        m=m+1;
                        item.setuser_mobile2(got[m]);
                        m=m+1;
                        item.setuser_address(got[m]);
                        m=m+1;
                        item.setuser_landmark(got[m]);
                        m=m+1;
                        item.setpincode(got[m]);
                        feedItems.add(item);
                    }
                    ordernow.setVisibility(View.VISIBLE);
                    heart.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    listAdapter.notifyDataSetChanged();
                    return;
                }

                ordernow.setVisibility(View.GONE);
                noaddress.setVisibility(View.VISIBLE);
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }

    public class placeorder extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please Wait.....");
            pd.setCancelable(false);
            pd.show();
            ordernow.setEnabled(false);
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"placeorder.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.get_userid()+"%%"+cart_fishid1+"%%"+cart_qty1+"%%"+cart_price1+"%%"+addresssn+"%%"+udb.getareaid(), "UTF-8");
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
                ordernow.setEnabled(true);
                pd.dismiss();
                if (result.contains("404")) {
                    Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                } else if (result.contains("ok")) {
                    db.deletecart();
                    uploadsucess();
                } else {
                    Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
}
