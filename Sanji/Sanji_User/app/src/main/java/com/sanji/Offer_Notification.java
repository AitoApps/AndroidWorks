package com.sanji;

import android.app.AlertDialog.Builder;
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
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;
import data.Product_List_FeedItem;
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

public class Offer_Notification extends AppCompatActivity {
    ImageView back;
    TextView cartcount;
    ConnectionDetecter cd;
    RelativeLayout content;
    public DatabaseHandler db;
    public Dialog dialog;
    Typeface face;
    Typeface face1;

    public List<Product_List_FeedItem> feedItems;
    ImageView heart;
    String imgid;
    String imgsig;
    public int limit = 0;

    public Offer_List_ListAdapter listAdapter;
    RelativeLayout lytcart;
    RelativeLayout lytmsg;
    private MediaPlayer mediaPlayer;
    TextView msg;
    ImageView nodata;
    ImageView nointernet;
    ProgressDialog pd;
    public HeaderAndFooterRecyclerView recylerview;
    TextView text;
    public UserDatabaseHandler udb;

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
                sb.append(Offer_Notification.imgid);
                sb.append("_1.jpg");
                viewHolder.bindImageSlide(sb.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append(str);
                sb2.append(Offer_Notification.imgid);
                sb2.append("_2.jpg");
                viewHolder.bindImageSlide(sb2.toString(), R.drawable.placeholder, R.drawable.placeholder);
            } else if (position == 2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.weblink);
                sb3.append(str);
                sb3.append(Offer_Notification.imgid);
                sb3.append("_3.jpg");
                viewHolder.bindImageSlide(sb3.toString(), R.drawable.placeholder, R.drawable.placeholder);
            }
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Offer_Notification.nodata.setVisibility(View.GONE);
            Offer_Notification.recylerview.setVisibility(View.GONE);
            Offer_Notification.heart.setVisibility(View.VISIBLE);
            Offer_Notification.limit = 0;
        }
        public String doInBackground(String... arg0) {
            String str = "UTF-8";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getproductlist_offeruser.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", str));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Offer_Notification.db.getoffer_id(), str));
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
                        Offer_Notification.feedItems.clear();
                        String[] got = result.split(":%");
                        int k = (got.length - 1) / 19;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            Product_List_FeedItem item = new Product_List_FeedItem();
                            int m2 = m + 1;
                            item.settype(got[m2]);
                            int type = Integer.parseInt(got[m2]);
                            int m3 = m2 + 1;
                            item.setsn(got[m3]);
                            int m4 = m3 + 1;
                            item.setdeli_type(got[m4]);
                            int m5 = m4 + 1;
                            item.setproductcat(got[m5]);
                            int m6 = m5 + 1;
                            item.setshopid(got[m6]);
                            int m7 = m6 + 1;
                            item.setitemname(got[m7]);
                            int m8 = m7 + 1;
                            item.setprice(got[m8]);
                            int m9 = m8 + 1;
                            item.setogprice(got[m9]);
                            int m10 = m9 + 1;
                            item.setmaxorder(got[m10]);
                            int m11 = m10 + 1;
                            item.setdlcharge(got[m11]);
                            int m12 = m11 + 1;
                            item.setddsic(got[m12]);
                            int m13 = m12 + 1;
                            item.setitemdiscription(got[m13]);
                            int m14 = m13 + 1;
                            item.setimgsig1(got[m14]);
                            int m15 = m14 + 1;
                            item.setshopname(got[m15]);
                            int m16 = m15 + 1;
                            item.setshopplace(got[m16]);
                            int m17 = m16 + 1;
                            item.setshopmobile(got[m17]);
                            int m18 = m17 + 1;
                            item.setshoptime(got[m18]);
                            int m19 = m18 + 1;
                            item.setlocation(got[m19]);
                            m = m19 + 1;
                            Offer_Notification.feedItems.add(item);
                        }
                    } catch (Exception e) {
                    }
                    Offer_Notification.nodata.setVisibility(View.GONE);
                    Offer_Notification.heart.setVisibility(View.GONE);
                    Offer_Notification.recylerview.setVisibility(View.VISIBLE);
                    Offer_Notification.listAdapter.notifyDataSetChanged();
                    return;
                }
                Offer_Notification.nodata.setVisibility(View.VISIBLE);
                Offer_Notification.recylerview.setVisibility(View.GONE);
                Offer_Notification.heart.setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public Offer_Notification() {
        String str = "";
        imgid = str;
        imgsig = str;
    }
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0157 A[Catch:{ Exception -> 0x0186 }] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0169 A[Catch:{ Exception -> 0x0186 }] */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_offer__notification);
        try {
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            db = new DatabaseHandler(this);
            cd = new ConnectionDetecter(this);
            udb = new UserDatabaseHandler(this);
            pd = new ProgressDialog(this);
            nodata = (ImageView) findViewById(R.id.nodata);
            lytmsg = (RelativeLayout) findViewById(R.id.lytmsg);
            msg = (TextView) findViewById(R.id.msg);
            text = (TextView) findViewById(R.id.text);
            content = (RelativeLayout) findViewById(R.id.content);
            recylerview = (HeaderAndFooterRecyclerView) findViewById(R.id.recylerview);
            heart = (ImageView) findViewById(R.id.heart);
            back = (ImageView) findViewById(R.id.back);
            nointernet = (ImageView) findViewById(R.id.nointernet);
            lytcart = (RelativeLayout) findViewById(R.id.lytcart);
            cartcount = (TextView) findViewById(R.id.cartcount);
            feedItems = new ArrayList();
            listAdapter = new Offer_List_ListAdapter(this, feedItems);
            recylerview.setLayoutManager(new GridLayoutManager(this, 1));
            recylerview.setAdapter(listAdapter);
            text.setText(db.getoffer_title());
            text.setTypeface(face1);
            if (!db.getoffer_messaage().equalsIgnoreCase("NA")) {
                if (!db.getoffer_messaage().equalsIgnoreCase("")) {
                    lytmsg.setVisibility(View.VISIBLE);
                    msg.setTypeface(face);
                    msg.setText(db.getoffer_messaage());
                    back.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(Offer_Notification.getApplicationContext(), MainActivity.class);
                                intent.addFlags(67108864);
                                Offer_Notification.startActivity(intent);
                            } catch (Exception e) {
                            }
                        }
                    });
                    nointernet.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (Offer_Notification.cd.isConnectingToInternet()) {
                                Offer_Notification.nointernet.setVisibility(View.GONE);
                                Offer_Notification offer_Notification = Offer_Notification.this;
                                offer_Notification.limit = 0;
                                new loadstatus().execute(new String[0]);
                                return;
                            }
                            Offer_Notification.nointernet.setVisibility(View.VISIBLE);
                            Toasty.info(Offer_Notification.getApplicationContext(), Temp.nointernet, 0).show();
                        }
                    });
                    Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
                    if (!cd.isConnectingToInternet()) {
                        nointernet.setVisibility(View.GONE);
                        limit = 0;
                        new loadstatus().execute(new String[0]);
                    } else {
                        nointernet.setVisibility(View.VISIBLE);
                        Toasty.info(getApplicationContext(), Temp.nointernet, 0).show();
                    }
                    lytcart.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            Offer_Notification.startActivity(new Intent(Offer_Notification.getApplicationContext(), Cart.class));
                        }
                    });
                }
            }
            lytmsg.setVisibility(View.GONE);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(Offer_Notification.getApplicationContext(), MainActivity.class);
                        intent.addFlags(67108864);
                        Offer_Notification.startActivity(intent);
                    } catch (Exception e) {
                    }
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (Offer_Notification.cd.isConnectingToInternet()) {
                        Offer_Notification.nointernet.setVisibility(View.GONE);
                        Offer_Notification offer_Notification = Offer_Notification.this;
                        offer_Notification.limit = 0;
                        new loadstatus().execute(new String[0]);
                        return;
                    }
                    Offer_Notification.nointernet.setVisibility(View.VISIBLE);
                    Toasty.info(Offer_Notification.getApplicationContext(), Temp.nointernet, 0).show();
                }
            });
            Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
            if (!cd.isConnectingToInternet()) {
            }
            lytcart.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Offer_Notification.startActivity(new Intent(Offer_Notification.getApplicationContext(), Cart.class));
                }
            });
        } catch (Exception e) {
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
                Glide.with(Offer_Notification.getApplicationContext()).load(url).into(imageView);
            }

            public void loadImage(int resource, ImageView imageView) {
                Glide.with(Offer_Notification.getApplicationContext()).load(Integer.valueOf(resource)).into(imageView);
            }

            public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(placeHolder);
                requestOptions.error(errorDrawable);
                requestOptions.signature(new ObjectKey(imgsigs));
                Glide.with(Offer_Notification.getApplicationContext()).load(url).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
            }
        });
        ((Slider) dialog.findViewById(R.id.banner_slider1)).setAdapter(new MainSliderAdapter());
        dialog.show();
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

    public void showalertcall(String message, final String mob) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Offer_Notification.call(mob);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalertweb(String message, final String link) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Offer_Notification.web(link);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalertapp(String message, final String link) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Offer_Notification.download(link);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }

    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(67108864);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    public void showaddcart(String cart_shopid1, String cart_productid1, String cart_productname1, String cart_price1, String cart_qty1, String cart_totalprice1, String cart_imgsig1, String cart_maxqty1) {
        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.addtocart);
            mediaPlayer.setVolume(0.1f, 0.1f);
            mediaPlayer.start();
        } catch (Exception e) {
        }
        refreshcart();
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
}
