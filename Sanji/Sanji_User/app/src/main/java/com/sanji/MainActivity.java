package com.sanji;

import adapter.Home_Product_List_ListAdapter;
import adapter.Productcat_ListAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.firebase.FirebaseApp;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Home_Product_List_FeedItem;
import data.Productcat_Feeditem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainActivity extends AppCompatActivity implements OnConnectionFailedListener {

    /* renamed from: adapter reason: collision with root package name */
    CustomListAdapter f16adapter;
    TextView cartcount;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public Dialog dialog;
    Typeface face;
    Typeface face1;

    public List<Home_Product_List_FeedItem> feedItems;
    private List<Productcat_Feeditem> feedItems1;
    View footerView;
    View footerView1;
    GeoDataClient gdataclient;
    ImageView heart;
    ImageView heart1;
    String imgid;
    String imgsig;
    int limit = 0;

    public Home_Product_List_ListAdapter listAdapter;
    private Productcat_ListAdapter listAdapter1;
    RelativeLayout lytcart;
    RelativeLayout lytsearch;
    RelativeLayout lytvouchers;

    public MediaPlayer mediaPlayer;
    final Notification_Databasehandler ndb = new Notification_Databasehandler(this);
    ImageView nointernet;
    ImageView nointernet1;
    TextView noticount;
    RelativeLayout notilyt;
    ProgressDialog pd;
    AutoCompleteTextView place;
    List<String> placeList;
    HeaderAndFooterRecyclerView recylerview;
    HeaderAndFooterRecyclerView recylerview1;
    TextView text;
    TextView txtcartcount;
    TextView txtnoticount;
    TextView txtsearch;
    TextView txtvouchers;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    ImageView vouchers;

    public class MainSliderAdapter extends SliderAdapter {
        public MainSliderAdapter() {
        }

        public int getItemCount() {
            return 3;
        }

        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            String str = "productpics/";
            if (position == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append(str);
                sb.append(MainActivity.imgid);
                sb.append("_1.jpg");
                viewHolder.bindImageSlide(sb.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append(str);
                sb2.append(MainActivity.imgid);
                sb2.append("_2.jpg");
                viewHolder.bindImageSlide(sb2.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.weblink);
                sb3.append(str);
                sb3.append(MainActivity.imgid);
                sb3.append("_3.jpg");
                viewHolder.bindImageSlide(sb3.toString(), R.drawable.placeholder, R.drawable.placeholder);
            }
        }
    }

    public class loadcatogery extends AsyncTask<String, Void, String> {
        public loadcatogery() {
        }
        public void onPreExecute() {
            MainActivity.recylerview1.setVisibility(View.GONE);
            MainActivity.heart1.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductcatgory_user.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                sb2.append(URLEncoder.encode("", str));
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
                    try {
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        MainActivity.db.deleteproductcat();
                        for (int i = 1; i <= k; i++) {
                            int m2 = m + 1;
                            int a = m2;
                            int m3 = m2 + 1;
                            int a1 = m3;
                            m = m3 + 1;
                            MainActivity.db.addproductcatlist(got[a].trim(), got[a1], got[m]);
                        }
                        MainActivity.loadcatogertlist();
                    } catch (Exception e) {
                    }
                } else {
                    MainActivity.heart1.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            MainActivity.recylerview.setVisibility(View.GONE);
            MainActivity.heart.setVisibility(View.VISIBLE);
            MainActivity.limit = 0;
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshops_home.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                sb2.append(URLEncoder.encode("", str));
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
                return new String(Log.getStackTraceString(e));
            }
        }
        public void onPostExecute(String result) {
            try {
                if (result.contains(":%ok")) {
                    try {
                        MainActivity.feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 3;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Home_Product_List_FeedItem item = new Home_Product_List_FeedItem();
                            int m2 = m + 1;
                            item.setcatid(got[m2]);
                            int m3 = m2 + 1;
                            item.setcatname(got[m3]);
                            m = m3 + 1;
                            item.setshops(got[m]);
                            MainActivity.feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    MainActivity.heart.setVisibility(View.GONE);
                    MainActivity.recylerview.setVisibility(View.VISIBLE);
                    MainActivity.listAdapter.notifyDataSetChanged();
                    return;
                }
                MainActivity.recylerview.setVisibility(View.GONE);
                MainActivity.heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public MainActivity() {
        String str = "";
        imgid = str;
        imgsig = str;
        placeList = new ArrayList();
    }
    public void onCreate(Bundle savedInstanceState) {
        String str = "";
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        text = (TextView) findViewById(R.id.text);
        cartcount = (TextView) findViewById(R.id.cartcount);
        noticount = (TextView) findViewById(R.id.noticount);
        txtcartcount = (TextView) findViewById(R.id.txtcartcount);
        notilyt = (RelativeLayout) findViewById(R.id.notilyt);
        lytcart = (RelativeLayout) findViewById(R.id.lytcart);
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        heart = (ImageView) findViewById(R.id.heart);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        vouchers = (ImageView) findViewById(R.id.vouchers);
        text.setTypeface(face);
        noticount.setTypeface(face);
        lytsearch = (RelativeLayout) findViewById(R.id.lytsearch);
        txtnoticount = (TextView) findViewById(R.id.txtnoticount);
        txtvouchers = (TextView) findViewById(R.id.txtvouchers);
        lytvouchers = (RelativeLayout) findViewById(R.id.lytvouchers);
        txtsearch = (TextView) findViewById(R.id.txtsearch);
        txtnoticount.setTypeface(face);
        txtvouchers.setTypeface(face);
        txtcartcount.setTypeface(face);
        cartcount.setTypeface(face);
        noticount.setTypeface(face);
        txtsearch.setTypeface(face);
        gdataclient = Places.getGeoDataClient(getApplicationContext());

        notilyt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    MainActivity.ndb.deletecount();
                    MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Notifications.class));
                } catch (Exception e) {
                }
            }
        });
        feedItems = new ArrayList();
        listAdapter = new Home_Product_List_ListAdapter(this, feedItems);
        recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        recylerview.setAdapter(listAdapter);
        footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview.getFooterContainer(), false);
        recylerview.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE)
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.cd.isConnectingToInternet()) {
                    MainActivity.nointernet.setVisibility(View.GONE);
                    new loadstatus().execute(new String[0]);
                    return;
                }
                MainActivity.nointernet.setVisibility(View.VISIBLE);
            }
        });
        if (cd.isConnectingToInternet()) {
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(View.VISIBLE);
        }
        lytvouchers.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.udb.get_userid().equalsIgnoreCase("")) {
                    MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Registration.class));
                    return;
                }
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), My_Orders.class));
            }
        });
        lytsearch.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.show_search();
            }
        });
        lytcart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Cart.class));
            }
        });
        setlocation();
    }
    public void onResume() {
        super.onResume();
        try {
            if (ndb.get_count().equalsIgnoreCase("")) {
                noticount.setText("0");
            } else {
                noticount.setText(ndb.get_count());
            }
            refreshcart();
        } catch (Exception e) {
        }
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void showdiscription(String txtdiscription, String deli_type, String dlcharge, String ddsic) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.home_productdiscription);
        TextView discription = (TextView) dialog.findViewById(R.id.discription);
        RelativeLayout lytdeliverytype = (RelativeLayout) dialog.findViewById(R.id.lytdeliverytype);
        TextView deliverytype = (TextView) dialog.findViewById(R.id.deliverytype);
        TextView txtdeliverycharge = (TextView) dialog.findViewById(R.id.txtdeliverycharge);
        TextView deliverycharge = (TextView) dialog.findViewById(R.id.deliverycharge);
        TextView deliverydic = (TextView) dialog.findViewById(R.id.deliverydic);
        discription.setTypeface(face1);
        deliverytype.setTypeface(face);
        deliverycharge.setTypeface(face);
        deliverydic.setTypeface(face);
        txtdeliverycharge.setTypeface(face);
        discription.setText(fromHtml(txtdiscription));
        String rupee = getResources().getString(R.string.Rs);
        if (deli_type.equalsIgnoreCase("2")) {
            lytdeliverytype.setVisibility(View.VISIBLE);
            String str = "";
            if (dlcharge.equalsIgnoreCase(str) || dlcharge.equalsIgnoreCase("0")) {
                deliverycharge.setVisibility(View.INVISIBLE)
                txtdeliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setText("No Delivery Charge");
            } else {
                deliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setVisibility(View.VISIBLE);
                txtdeliverycharge.setText("Delivery Charge : ");
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(dlcharge);
                deliverycharge.setText(sb.toString());
            }
            if (ddsic.equalsIgnoreCase(str)) {
                deliverydic.setVisibility(View.GONE);
            } else {
                deliverydic.setVisibility(View.VISIBLE);
                deliverydic.setText(ddsic);
            }
        } else {
            lytdeliverytype.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void photoview(final String imgsigs, String imgids) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.photoviews);
        imgid = imgids;
        imgsig = imgsigs;
        Slider.init(new ImageLoadingService() {
            public void loadImage(String url, ImageView imageView) {
                Glide.with(MainActivity.getApplicationContext()).load(url).into(imageView);
            }

            public void loadImage(int resource, ImageView imageView) {
                Glide.with(MainActivity.getApplicationContext()).load(Integer.valueOf(resource)).into(imageView);
            }

            public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(placeHolder);
                requestOptions.error(errorDrawable);
                requestOptions.signature(new ObjectKey(imgsigs));
                Glide.with(MainActivity.getApplicationContext()).load(url).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            }
        });
        ((Slider) dialog.findViewById(R.id.banner_slider1)).setAdapter(new MainSliderAdapter());
        dialog.show();
    }

    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }

    public void showalertcall(String message, final String mob) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.call(mob);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public void call(String mob) {
        String str = "android.permission.CALL_PHONE";
        try {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{str}, 1);
                return;
            }
            Intent callIntent = new Intent("android.intent.action.CALL");
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(mob);
            callIntent.setData(Uri.parse(sb.toString()));
            startActivity(callIntent);
        } catch (Exception e) {
        }
    }

    public void show_search() {
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.searchview);
        ImageView searhicon = (ImageView) dialog1.findViewById(R.id.searhicon);
        final EditText search = (EditText) dialog1.findViewById(R.id.search);
        recylerview1 = (HeaderAndFooterRecyclerView) dialog1.findViewById(R.id.recylerview);
        nointernet1 = (ImageView) dialog1.findViewById(R.id.nointernet1);
        heart1 = (ImageView) dialog1.findViewById(R.id.heart1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart1);
        feedItems1 = new ArrayList();
        listAdapter1 = new Productcat_ListAdapter(this, feedItems1);
        recylerview1.setLayoutManager(new GridLayoutManager(this, 1));
        recylerview1.setAdapter(listAdapter1);
        footerView1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.footerview, recylerview1.getFooterContainer(), false);
        recylerview1.addFooterView(footerView1);
        footerView1.setVisibility(View.INVISIBLE)
        search.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != 0 || keyCode != 66) {
                    return false;
                }
                if (search.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(MainActivity.getApplicationContext(), "ദയവായി തിരയുവാനുള്ള എന്തെങ്കിലും ടൈപ്പ് ചെയ്യൂ..", 0).show();
                } else {
                    Temp.catogeryname = search.getText().toString();
                    Product_List.searchtype = "2";
                    Product_List.searchstring = search.getText().toString();
                    MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Product_List.class));
                    dialog1.dismiss();
                }
                return true;
            }
        });
        searhicon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (search.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(MainActivity.getApplicationContext(), "ദയവായി തിരയുവാനുള്ള എന്തെങ്കിലും ടൈപ്പ് ചെയ്യൂ..", 0).show();
                    return;
                }
                Temp.catogeryname = search.getText().toString();
                Product_List.searchtype = "2";
                Product_List.searchstring = search.getText().toString();
                MainActivity.startActivity(new Intent(MainActivity.getApplicationContext(), Product_List.class));
                dialog1.dismiss();
            }
        });
        ArrayList<String> id6 = db.getproductcatlist();
        if (((String[]) id6.toArray(new String[id6.size()])).length > 0) {
            loadcatogertlist();
        } else {
            new loadcatogery().execute(new String[0]);
        }
        dialog1.show();
    }

    public void loadcatogertlist() {
        recylerview1.setVisibility(View.GONE);
        heart1.setVisibility(View.VISIBLE);
        ArrayList<String> id6 = db.getproductcatlist();
        String[] c6 = (String[]) id6.toArray(new String[id6.size()]);
        feedItems1.clear();
        if (c6.length > 0) {
            int a = c6.length / 3;
            int m = -1;
            for (int j = 1; j <= a; j++) {
                Productcat_Feeditem item = new Productcat_Feeditem();
                int m2 = m + 1;
                item.setsn(c6[m2]);
                int m3 = m2 + 1;
                item.setproductname(c6[m3]);
                m = m3 + 1;
                item.setimgsig(c6[m]);
                feedItems1.add(item);
            }
        }
        heart1.setVisibility(View.GONE);
        recylerview1.setVisibility(View.VISIBLE);
        listAdapter1.notifyDataSetChanged();
    }

    public void showmap(String gps, String shopname) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("http://maps.google.com/maps?q=loc:");
            sb.append(gps);
            sb.append(" (");
            sb.append(shopname);
            sb.append(")");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(sb.toString()));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } catch (Exception e) {
            Toasty.error(getApplicationContext(), "ദയവായി താങ്കളുടെ ഫോണില്‍ ഗൂഗിള്‍ മാപ്പ് ഇന്‍സ്റ്റാള്‍ ചെയ്യുക ", 0).show();
        }
    }

    public void showaddcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_imgsig1, String cart_minqty1, String cart_unittype1, String cart_shopname1, String cart_delicharge1, String cart_delidisc1, String minordramt, String imgsig1) {
        String str = cart_shopid1;
        String str2 = cart_productid1;
        final String str3 = cart_productname1;
        final String str4 = cart_price1;
        final String str5 = cart_imgsig1;
        final String str6 = cart_minqty1;
        final String str7 = cart_unittype1;
        final String str8 = cart_shopname1;
        final String str9 = cart_delicharge1;
        final String str10 = cart_delidisc1;
        final String str11 = minordramt;
        final String str12 = imgsig1;
        Dialog dialog1 = new Dialog(this);
        final Dialog dialog2 = dialog1;
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.addtocart);
        TextView itemname = (TextView) dialog1.findViewById(R.id.itemname);
        EditText qty = (EditText) dialog1.findViewById(R.id.qty);
        EditText editText = qty;
        TextView unittype = (TextView) dialog1.findViewById(R.id.unittype);
        TextView price = (TextView) dialog1.findViewById(R.id.price);
        final TextView textView = price;
        Button buy = (Button) dialog1.findViewById(R.id.buy);
        ImageView adminclose = (ImageView) dialog1.findViewById(R.id.adminclose);
        String rupee = getResources().getString(R.string.Rs);
        final String str13 = rupee;
        itemname.setText(cart_productname1);
        qty.setText(cart_minqty1);
        Button buy2 = buy;
        qty.setSelection(cart_minqty1.length());
        TextView textView2 = itemname;
        unittype.setText((CharSequence) Temp.lst_unittype.get(Integer.parseInt(cart_unittype1)));
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        sb.append(cart_price1);
        price.setText(sb.toString());
        ImageView adminclose2 = adminclose;
        Button buy3 = buy2;
        String str14 = str2;
        AnonymousClass11 r9 = r0;
        TextView price2 = price;
        final EditText editText2 = qty;
        TextView textView3 = unittype;
        final String str15 = cart_minqty1;
        String str16 = str;
        EditText qty2 = qty;
        final TextView textView4 = price2;
        final String str17 = rupee;
        EditText editText3 = editText;
        final Dialog dialog12 = dialog1;
        final String str18 = cart_price1;
        AnonymousClass11 r0 = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                String str = "";
                if (!editText2.getText().toString().equalsIgnoreCase(str) && Float.valueOf(editText2.getText().toString()).floatValue() >= 0.0f) {
                    float qty1 = Float.parseFloat(editText2.getText().toString()) / Float.parseFloat(str15);
                    TextView textView = textView4;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str17);
                    sb.append(Float.parseFloat(str18) * qty1);
                    sb.append(str);
                    textView.setText(sb.toString());
                }
            }
        };
        qty2.addTextChangedListener(r9);
        adminclose2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog12.dismiss();
            }
        });
        ImageView imageView = adminclose2;
        EditText editText4 = qty2;
        Dialog dialog13 = dialog12;
        final String str19 = str14;
        final String str20 = str16;
        final EditText editText5 = editText3;
        AnonymousClass13 r7 = new OnClickListener(this) {
            final /* synthetic */ MainActivity this$0;

            {
                this$0 = this$0;
            }

            public void onClick(View view) {
                String str = "";
                if (!this$0.db.getcartexist(str19, str20).equalsIgnoreCase(str)) {
                    this$0.db.addcart_exitupdate(editText5.getText().toString(), textView.getText().toString().replace(str13, str), str19);
                    Toasty.info(this$0.getApplicationContext(), "Cart Updated", 0).show();
                } else {
                    this$0.db.addcart(str20, str19, str3, str4, editText5.getText().toString(), textView.getText().toString().replace(str13, str), str5, str6, str7, str8, str9, str10, "0", str11, str12);
                }
                try {
                    this$0.mediaPlayer = MediaPlayer.create(this$0.getApplicationContext(), R.raw.addtocart);
                    this$0.mediaPlayer.setVolume(0.1f, 0.1f);
                    this$0.mediaPlayer.start();
                } catch (Exception e) {
                }
                dialog2.dismiss();
                this$0.refreshcart();
            }
        };
        buy3.setOnClickListener(r7);
        dialog13.show();
    }

    public void refreshcart() {
        try {
            long cartcounts = db.getcartcount();
            TextView textView = cartcount;
            StringBuilder sb = new StringBuilder();
            sb.append(cartcounts);
            sb.append("");
            textView.setText(sb.toString());
            lytcart.startAnimation(AnimationUtils.loadAnimation(this, R.anim.expand_in));
        } catch (Exception e) {
        }
    }

    public void setlocation() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.select_location_dialog);
        dialog.show();
    }
}
