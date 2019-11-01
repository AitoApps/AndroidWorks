package com.dailybill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapter.Product_List_ListAdapter;
import data.Productlist_FeedItem;
import es.dmoral.toasty.Toasty;

public class ProductList extends AppCompatActivity {
    private final long DELAY = 1000;
    ImageView back;
    ImageView carticon;
    RelativeLayout cartlyt;
    ConnectionDetecter cd;
    RelativeLayout content;
    public DatabaseHandler db;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    public List<Productlist_FeedItem> feedItems;
    View headerview;
    ImageView heart;
    String imgid="",imgsig="";
    TextView itemcount;
    public int limit = 0;
    public Product_List_ListAdapter listAdapter;
    public MediaPlayer mediaPlayer;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    EditText search;
    public Timer timer = new Timer();
    public String txt_search;
    TextView viewcart;
    ImageView voicesearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        try {
            face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            itemcount = (TextView) findViewById(R.id.itemcount);
            viewcart = (TextView) findViewById(R.id.viewcart);
            carticon = (ImageView) findViewById(R.id.carticon);
            search = (EditText) findViewById(R.id.search);
            voicesearch = (ImageView) findViewById(R.id.voicesearch);
            cartlyt = (RelativeLayout) findViewById(R.id.cartlyt);
            content = (RelativeLayout) findViewById(R.id.content);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            itemcount.setTypeface(face);
            viewcart.setTypeface(face);
            feedItems = new ArrayList();
            listAdapter = new Product_List_ListAdapter(this, feedItems);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            recylerview.setLayoutManager(gridLayoutManager);
            recylerview.setAdapter(listAdapter);
            headerview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.headerview, recylerview.getHeaderContainer(), false);
            ImageView shopimg = (ImageView) headerview.findViewById(R.id.shopimg);
            TextView shopname = (TextView) headerview.findViewById(R.id.shopname);
            TextView placename = (TextView) headerview.findViewById(R.id.placename);
            TextView km = (TextView) headerview.findViewById(R.id.km);
            ImageView location = (ImageView) headerview.findViewById(R.id.location);
            recylerview.addHeaderView(headerview);
            shopname.setTypeface(face);
            placename.setTypeface(face1);
            km.setTypeface(face1);

           /* float ogheight = Float.valueOf(db.getscreenwidth()).floatValue();
            float calheight = 0.75f * ogheight;
            GridLayoutManager gridLayoutManager2 = gridLayoutManager;
            float f = ogheight;
            shopimg.getLayoutParams().height = Math.round(calheight);
            float f2 = calheight;
           */


            RequestOptions rep = new RequestOptions().signature(new ObjectKey(Temp.shopimgsig));
            Glide.with(this).load(Temp.weblink+"shoppics/"+Temp.shopid+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(shopimg);
            shopname.setText(Temp.shopname);
            placename.setText(Temp.shopplace);
            km.setText(" , "+Temp.shopkm);


            location.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    showmap(Temp.shoplatitude+","+Temp.shoplongtitude, Temp.shopname);
                }
            });
            search.setTypeface(face1);
            feedItems = new ArrayList();
            listAdapter = new Product_List_ListAdapter(this, feedItems);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 1);
            recylerview.setLayoutManager(gridLayoutManager1);
            recylerview.setAdapter(listAdapter);

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            nointernet.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nointernet.setVisibility(View.GONE);
                        limit = 0;
                        new loadproductsearch().execute();

                    }
                    else
                    {
                        nointernet.setVisibility(View.VISIBLE);
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }

                }
            });
            viewcart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //startActivity(new Intent(getApplicationContext(), Cart.class));
                }
            });
            carticon.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                   // startActivity(new Intent(getApplicationContext(), Cart.class));
                }
            });
            cartlyt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                   // startActivity(new Intent(getApplicationContext(), Cart.class));
                }
            });
            Glide.with(this).load(R.drawable.loading).into(heart);

            search.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (timer != null) {
                        timer.cancel();
                    }
                }

                public void afterTextChanged(Editable editable) {
                    if (editable.length() >= 3) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        recylerview.setVisibility(View.VISIBLE);
                                        recylerview.setVisibility(View.GONE);
                                        txt_search = search.getText().toString();
                                        new loadproductsearch().execute(new String[0]);
                                    }
                                });
                            }
                        }, 1000);
                        return;
                    }
                    recylerview.setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                }
            });
            voicesearch.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                    intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                    intent.putExtra("android.speech.extra.PROMPT", "Voice searching...");
                    try {
                        startActivityForResult(intent, 1400);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Oops! Your device doesn't support voice search", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            search.setTypeface(face1);
            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });


            if (cd.isConnectingToInternet()) {
                nointernet.setVisibility(View.GONE);
                limit = 0;
                new loadproductsearch().execute();
            } else {
                nointernet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {


            Toast.makeText(getApplicationContext(),Log.getStackTraceString(e),Toast.LENGTH_LONG).show();

        }
    }

    public class loadproductsearch extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            recylerview.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getproduct_list_byuser.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Temp.shopid+":%"+db.getlatitude()+":%"+db.getlongtitude()+":%"+limit, "UTF-8");
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
        public void onPostExecute(final String result) {
            try {

                Log.w("Result",result);
                if (result.contains(":%ok")) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) / 20;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    Productlist_FeedItem item2 = new Productlist_FeedItem();
                                    m=m+1;
                                    item2.setSn(got[m]);
                                    m=m+1;
                                    item2.setPrice(got[m]);
                                    m=m+1;
                                    item2.setMinorder(got[m]);
                                    m=m+1;
                                    item2.setMinorderunittype(got[m]);
                                    m=m+1;
                                    item2.setOutofstock(got[m]);
                                    m=m+1;
                                    item2.setItemid(got[m]);
                                    m=m+1;
                                    item2.setItemname(got[m]);
                                    m=m+1;
                                    item2.setOgunittype(got[m]);
                                    m=m+1;
                                    item2.setImgsig1(got[m]);
                                    m=m+1;
                                    item2.setShopid(got[m]);
                                    m=m+1;
                                    item2.setShopimgsig(got[m]);
                                    m=m+1;
                                    item2.setShopmobile(got[m]);
                                    m=m+1;
                                    item2.setShopname(got[m]);
                                    m=m+1;
                                    item2.setShopplace(got[m]);
                                    m=m+1;
                                    item2.setShoptime(got[m]);
                                    m=m+1;
                                    item2.setLocation(got[m]);
                                    m=m+1;
                                    item2.setMinordramt(got[m]);
                                    m=m+1;
                                    item2.setDelicharge(got[m]);
                                    m=m+1;
                                    item2.setDelidisc(got[m]);
                                    m=m+1;
                                    item2.setDistance(got[m]);
                                    feedItems.add(item2);
                                }
                            }
                        });
                        heart.setVisibility(View.GONE);
                        recylerview.setVisibility(View.VISIBLE);
                        listAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }

    public void onBackPressed() {
        if (recylerview.getVisibility() != View.VISIBLE) {
            recylerview.setVisibility(View.VISIBLE);
            search.setText("");
            return;
        }
        super.onBackPressed();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 1400 && resultCode == -1 && data2 != null) {
            String str = "android.speech.extra.RESULTS";
            if (data2.getStringArrayListExtra(str).size() > 0) {
                search.setText((CharSequence) data2.getStringArrayListExtra(str).get(0));
                limit = 0;
                txt_search = search.getText().toString();
                EditText editText = search;
                editText.setSelection(editText.getText().toString().length());
                new loadproductsearch().execute(new String[0]);
            }
        }
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
    public void web(String web) {
        Intent pagIntent = new Intent("android.intent.action.VIEW");
        pagIntent.setData(Uri.parse(web));
        try {
            startActivity(pagIntent);
        } catch (Exception e) {
        }
    }
    public void download(String link) {
        String str = "android.intent.action.VIEW";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("market://details?id=");
            sb.append(link);
            startActivity(new Intent(str, Uri.parse(sb.toString())));
        } catch (ActivityNotFoundException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("https://play.google.com/store/apps/details?id=");
            sb2.append(link);
            startActivity(new Intent(str, Uri.parse(sb2.toString())));
        }
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

    public void showaddcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_maxqty1) {
        try {
            //mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.addtocart);
            //mediaPlayer.setVolume(0.1f, 0.1f);
            //mediaPlayer.start();
        } catch (Exception e) {
        }
    }
    public void onResume() {
        super.onResume();
        refreshcart();
    }

    public void refreshcart() {
       /* String rupee = getResources().getString(R.string.Rs);
        float gtotal = db.get_cartgrandtotal();
        if (gtotal > 0.0f) {
            TextView textView = itemcount;
            StringBuilder sb = new StringBuilder();
            sb.append(db.get_totalqty());
            sb.append(" Items | ");
            sb.append(rupee);
            sb.append(String.format("%.2f", new Object[]{Float.valueOf(gtotal)}));
            textView.setText(sb.toString());
            return;
        }
        TextView textView2 = itemcount;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(db.get_totalqty());
        sb2.append(" items | ");
        sb2.append(rupee);
        sb2.append("0.00");
        textView2.setText(sb2.toString());*/
    }
}
