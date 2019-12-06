package com.sanji_shops;

import adapter.Delicharge_ListAdapter;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sanji_shop.AndroidMultiPartEntity.ProgressListener;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
import data.DelichargeList_FeedItem;
import es.dmoral.toasty.Toasty;
import im.delight.android.location.SimpleLocation;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource;

public class Shop_Details extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, OnTimeSetListener {
    final int[] DAY_BUTTON_IDS = {R.id.sunday, R.id.monday, R.id.tuesday, R.id.wendesday, R.id.thursday, R.id.friday, R.id.satrdy};
    private long FASTEST_INTERVAL = 5000;
    final String[] PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    final int PERMISSION_ALL = 1;
    private long UPDATE_INTERVAL = 15000;
    ImageView back;
    public String buttonids = "";
    ConnectionDetecter cd;
    EditText charge;
    Button chargeadd;
    EditText close;
    final DatabaseHandler db = new DatabaseHandler(this);
    String delicharge = "0";
    String deliinfo = "NA";
    EditText deliinkm;
    EditText deliverydisc;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    EditText facebook;
    public List<DelichargeList_FeedItem> feedItems;
    Button friday;
    ImageView heart;
    HttpClient httpclient;
    HttpPost httppost = null;
    public Bitmap img;
    EditText instagram;
    EditText lattiude;
    ScrollView layout;
    ListView list;
    public Delicharge_ListAdapter listAdapter;
    public SimpleLocation location;
    ImageView location1;
    EditText longtitude;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    private LocationRequest mLocationRequest;
    String micharge = "0";
    EditText minimumcharge;
    EditText mobile1;
    EditText mobile2;
    Button monday;
    ImageView nointernet;
    public float ogheight;
    EditText open;
    EditText ownername;
    ProgressBar pb1;
    public ProgressBar pb2;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    EditText pinterest;
    EditText place;
    Button satrdy;
    EditText shopname;
    public Button stop;
    Button sunday;
    TextView text;
    Button thursday;
    EditText toamt;
    long totalSize = 0;
    Button tuesday;
    TextView txtdeli;
    TextView txtdeliinkm;
    TextView txtfacebook;
    TextView txtinstagram;
    TextView txtlattiude;
    TextView txtlongtitude;
    TextView txtlytclosing;
    TextView txtminimcharge;
    TextView txtminimumcharge;
    TextView txtmobile1;
    TextView txtmobile2;
    TextView txtownername;
    TextView txtphoto1;
    TextView txtpinterest;
    TextView txtplace;
    TextView txtshopname;
    public String txtsn = "";
    TextView txtsocialinfo;
    TextView txtwebsite;
    TextView txtyoutube;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button update;
    EditText website;
    Button wendesday;
    int whichclick = 0;
    EditText youtube;

    public class UploadFileToServer extends AsyncTask<String, Integer, String> {
        public UploadFileToServer() {
        }
        public void onPreExecute() {
            Shop_Details.pb2.setVisibility(View.VISIBLE);
            Shop_Details.dialog.setCancelable(false);
            Shop_Details.persentage.setVisibility(View.VISIBLE);
            Shop_Details.update.setEnabled(false);
            super.onPreExecute();
        }
        public void onProgressUpdate(Integer... progress) {
            try {
                TextView textView = Shop_Details.persentage;
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(progress[0]));
                sb.append("%");
                textView.setText(sb.toString());
            } catch (Exception e) {
            }
        }
        public String doInBackground(String... params) {
            return uploadFile();
        }

        private String uploadFile() {
            Shop_Details.httpclient = new DefaultHttpClient();
            Shop_Details shop_Details = Shop_Details.this;
            StringBuilder sb = new StringBuilder();
            sb.append(Temp.weblink);
            sb.append("updateshopbyshop.php");
            shop_Details.httppost = new HttpPost(sb.toString());
            try {
                ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Charset.forName("utf-8"));
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
                    public void transferred(long num) {
                        UploadFileToServer.publishProgress(new Integer[]{Integer.valueOf((int) ((((float) num) / ((float) Shop_Details.totalSize)) * 100.0f))});
                    }
                });
                if (Shop_Details.photopath1.equalsIgnoreCase("none")) {
                    entity.addPart("image1", new StringBody("none", contentType));
                } else if (Shop_Details.photopath1.equalsIgnoreCase("removed")) {
                    entity.addPart("image1", new StringBody("removed", contentType));
                } else {
                    File sourceFile = new File(Shop_Details.photopath1);
                    entity.addPart("image1", new StringBody("filled", contentType));
                    entity.addPart("photo1", new FileBody(sourceFile));
                }
                entity.addPart("shopid", new StringBody(Shop_Details.udb.get_shopid(), contentType));
                entity.addPart("latitude", new StringBody(Shop_Details.lattiude.getText().toString(), contentType));
                entity.addPart("longtitude", new StringBody(Shop_Details.longtitude.getText().toString(), contentType));
                entity.addPart("shoptime", new StringBody(Shop_Details.buttonids, contentType));
                entity.addPart("delicharge", new StringBody(Shop_Details.delicharge));
                entity.addPart("deliinfo", new StringBody(Shop_Details.deliinfo));
                entity.addPart("minordramt", new StringBody(Shop_Details.micharge));
                entity.addPart("website", new StringBody(Shop_Details.website.getText().toString()));
                entity.addPart("instagram", new StringBody(Shop_Details.instagram.getText().toString()));
                entity.addPart("facebook", new StringBody(Shop_Details.facebook.getText().toString()));
                entity.addPart("pinterest", new StringBody(Shop_Details.pinterest.getText().toString()));
                entity.addPart("youtube", new StringBody(Shop_Details.youtube.getText().toString()));
                Shop_Details.totalSize = entity.getContentLength();
                Shop_Details.httppost.setEntity(entity);
                HttpResponse response = Shop_Details.httpclient.execute(Shop_Details.httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(r_entity);
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error occurred! Http Status Code: ");
                sb2.append(statusCode);
                return sb2.toString();
            } catch (ClientProtocolException e) {
                return e.toString();
            } catch (IOException e2) {
                return e2.toString();
            }
        }
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Shop_Details.pb2.setVisibility(View.GONE);
            Shop_Details.persentage.setVisibility(View.GONE);
            Shop_Details.update.setEnabled(true);
            Shop_Details.dialog.dismiss();
            if (result.contains("ok")) {
                File file1 = new File(Shop_Details.photopath1);
                if (file1.exists()) {
                    file1.delete();
                }
                Toasty.info(Shop_Details.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                Shop_Details.finish();
                return;
            }
            Toasty.info(Shop_Details.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public loadstatus() {
        }
        public void onPreExecute() {
            Shop_Details.layout.setVisibility(View.GONE);
            Shop_Details.heart.setVisibility(View.VISIBLE);
            Shop_Details.nointernet.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("getshopdetails.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(Shop_Details.udb.get_shopid(), "UTF-8"));
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
            Shop_Details.heart.setVisibility(View.GONE);
            if (result.contains(":%ok")) {
                Shop_Details.layout.setVisibility(View.VISIBLE);
                final String[] s = result.split(":%");
                Shop_Details.txtsn = s[0];
                Shop_Details.shopname.setText(s[1]);
                Shop_Details.ownername.setText(s[2]);
                Shop_Details.mobile1.setText(s[3]);
                Shop_Details.mobile2.setText(s[4]);
                Shop_Details.place.setText(s[5]);
                Shop_Details.lattiude.setText(s[6]);
                Shop_Details.longtitude.setText(s[7]);
                String[] k = s[9].split("##");
                String[] k1 = k[0].split("-");
                Shop_Details.open.setText(k1[0]);
                Shop_Details.close.setText(k1[1]);
                Shop_Details.checktrue_daysbutton(k[1]);
                if (!s[10].equalsIgnoreCase("0") && !s[10].equalsIgnoreCase("NA")) {
                    if (s[10].contains("#")) {
                        String[] p = s[10].split("#");
                        for (String split : p) {
                            String[] c = split.split("-");
                            DelichargeList_FeedItem item = new DelichargeList_FeedItem();
                            item.setToamt(c[0]);
                            item.setCharge(c[1]);
                            Shop_Details.feedItems.add(item);
                        }
                        Shop_Details.listAdapter.notifyDataSetChanged();
                        Shop_Details.setListViewHeightBasedOnItems(Shop_Details.list);
                    } else {
                        String[] c2 = s[10].split("-");
                        DelichargeList_FeedItem item2 = new DelichargeList_FeedItem();
                        item2.setToamt(c2[0]);
                        item2.setCharge(c2[1]);
                        Shop_Details.feedItems.add(item2);
                        Shop_Details.listAdapter.notifyDataSetChanged();
                        Shop_Details.setListViewHeightBasedOnItems(Shop_Details.list);
                    }
                }
                Shop_Details.deliverydisc.setText(s[11]);
                Shop_Details.deliinkm.setText(s[12]);
                Shop_Details.minimumcharge.setText(s[13]);
                Shop_Details.website.setText(s[14]);
                Shop_Details.instagram.setText(s[15]);
                Shop_Details.facebook.setText(s[16]);
                Shop_Details.pinterest.setText(s[17]);
                Shop_Details.youtube.setText(s[18]);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        Shop_Details.pb1.setVisibility(View.VISIBLE);
                        RequestBuilder apply = Glide.with(Shop_Details.getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(s[8])));
                        StringBuilder sb = new StringBuilder();
                        sb.append(Temp.weblink);
                        sb.append("shoppics/");
                        sb.append(Shop_Details.txtsn);
                        sb.append(".jpg");
                        apply.load(sb.toString()).into(new SimpleTarget<Bitmap>() {
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                Shop_Details.pb1.setVisibility(View.GONE);
                                Shop_Details.photo1.setImageBitmap(bitmap);
                            }

                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                Shop_Details.pb1.setVisibility(View.GONE);
                                Shop_Details.photo1.setImageResource(R.drawable.nophoto);
                            }
                        });
                    }
                });
                return;
            }
            Toasty.info(Shop_Details.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_shop__details);
        shopname = (EditText) findViewById(R.id.shopname);
        ownername = (EditText) findViewById(R.id.ownername);
        mobile1 = (EditText) findViewById(R.id.mobile1);
        mobile2 = (EditText) findViewById(R.id.mobile2);
        place = (EditText) findViewById(R.id.place);
        lattiude = (EditText) findViewById(R.id.lattiude);
        txtminimcharge = (TextView) findViewById(R.id.txtminimcharge);
        txtminimumcharge = (TextView) findViewById(R.id.txtminimumcharge);
        minimumcharge = (EditText) findViewById(R.id.minimumcharge);
        toamt = (EditText) findViewById(R.id.toamt);
        charge = (EditText) findViewById(R.id.charge);
        chargeadd = (Button) findViewById(R.id.chargeadd);
        list = (ListView) findViewById(R.id.list);
        deliverydisc = (EditText) findViewById(R.id.deliverydisc);
        deliinkm = (EditText) findViewById(R.id.deliinkm);
        txtdeli = (TextView) findViewById(R.id.txtdeli);
        txtdeliinkm = (TextView) findViewById(R.id.txtdeliinkm);
        longtitude = (EditText) findViewById(R.id.longtitude);
        location1 = (ImageView) findViewById(R.id.location);
        heart = (ImageView) findViewById(R.id.heart);
        update = (Button) findViewById(R.id.update);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        pb1 = (ProgressBar) findViewById(R.id.pb1);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        location = new SimpleLocation(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        layout = (ScrollView) findViewById(R.id.layout);
        txtlytclosing = (TextView) findViewById(R.id.txtlytclosing);
        pd = new ProgressDialog(this);
        photo1 = (ImageView) findViewById(R.id.photo1);
        txtsocialinfo = (TextView) findViewById(R.id.txtsocialinfo);
        txtwebsite = (TextView) findViewById(R.id.txtwebsite);
        txtinstagram = (TextView) findViewById(R.id.txtinstagram);
        txtfacebook = (TextView) findViewById(R.id.txtfacebook);
        txtpinterest = (TextView) findViewById(R.id.txtpinterest);
        txtyoutube = (TextView) findViewById(R.id.txtyoutube);
        website = (EditText) findViewById(R.id.website);
        instagram = (EditText) findViewById(R.id.instagram);
        facebook = (EditText) findViewById(R.id.facebook);
        pinterest = (EditText) findViewById(R.id.pinterest);
        youtube = (EditText) findViewById(R.id.youtube);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.onBackPressed();
            }
        });
        feedItems = new ArrayList();
        listAdapter = new Delicharge_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        txtminimcharge.setTypeface(face1);
        txtminimumcharge.setTypeface(face1);
        minimumcharge.setTypeface(face);
        chargeadd.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Shop_Details.toamt.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Shop_Details.getApplicationContext(), "Please enter to amount", Toast.LENGTH_SHORT).show();
                    Shop_Details.toamt.requestFocus();
                } else if (Shop_Details.charge.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Shop_Details.getApplicationContext(), "Please enter delivery charge", Toast.LENGTH_SHORT).show();
                    Shop_Details.charge.requestFocus();
                } else {
                    DelichargeList_FeedItem item = new DelichargeList_FeedItem();
                    item.setToamt(Shop_Details.toamt.getText().toString());
                    item.setCharge(Shop_Details.charge.getText().toString());
                    Shop_Details.feedItems.add(item);
                    Shop_Details.listAdapter.notifyDataSetChanged();
                    Shop_Details.toamt.setText("");
                    Shop_Details.charge.setText("");
                    Shop_Details.toamt.requestFocus();
                    Shop_Details.setListViewHeightBasedOnItems(Shop_Details.list);
                }
            }
        });
        txtshopname = (TextView) findViewById(R.id.txtshopname);
        txtownername = (TextView) findViewById(R.id.txtownername);
        txtmobile1 = (TextView) findViewById(R.id.txtmobile1);
        txtmobile2 = (TextView) findViewById(R.id.txtmobile2);
        txtplace = (TextView) findViewById(R.id.txtplace);
        txtlattiude = (TextView) findViewById(R.id.txtlattiude);
        txtlongtitude = (TextView) findViewById(R.id.txtlongtitude);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);
        shopname.setTypeface(face);
        ownername.setTypeface(face);
        mobile1.setTypeface(face);
        mobile2.setTypeface(face);
        place.setTypeface(face);
        lattiude.setTypeface(face);
        longtitude.setTypeface(face);
        txtlytclosing.setTypeface(face);
        deliinkm.setTypeface(face);
        txtdeliinkm.setTypeface(face1);
        txtdeli.setTypeface(face1);
        txtsocialinfo.setTypeface(face1);
        txtwebsite.setTypeface(face1);
        txtinstagram.setTypeface(face1);
        txtfacebook.setTypeface(face1);
        txtpinterest.setTypeface(face1);
        txtyoutube.setTypeface(face1);
        website.setTypeface(face);
        instagram.setTypeface(face);
        facebook.setTypeface(face);
        pinterest.setTypeface(face);
        youtube.setTypeface(face);
        txtshopname.setTypeface(face1);
        txtownername.setTypeface(face1);
        txtmobile1.setTypeface(face1);
        txtmobile2.setTypeface(face1);
        txtplace.setTypeface(face1);
        txtlattiude.setTypeface(face1);
        txtlongtitude.setTypeface(face1);
        txtphoto1.setTypeface(face1);
        update.setTypeface(face);
        open = (EditText) findViewById(R.id.open);
        close = (EditText) findViewById(R.id.close);
        sunday = (Button) findViewById(R.id.sunday);
        monday = (Button) findViewById(R.id.monday);
        tuesday = (Button) findViewById(R.id.tuesday);
        wendesday = (Button) findViewById(R.id.wendesday);
        thursday = (Button) findViewById(R.id.thursday);
        friday = (Button) findViewById(R.id.friday);
        satrdy = (Button) findViewById(R.id.satrdy);
        open.setTypeface(face);
        close.setTypeface(face);
        sunday.setTypeface(face);
        monday.setTypeface(face);
        tuesday.setTypeface(face);
        wendesday.setTypeface(face);
        thursday.setTypeface(face);
        friday.setTypeface(face);
        satrdy.setTypeface(face);
        toamt.setTypeface(face);
        charge.setTypeface(face);
        deliverydisc.setTypeface(face);
        photo1.post(new Runnable() {
            public void run() {
                Shop_Details.ogheight = Float.parseFloat(Shop_Details.db.getscreenwidth()) / 4.0f;
                Shop_Details.ogheight *= 3.0f;
                Shop_Details.photo1.getLayoutParams().height = Math.round(Shop_Details.ogheight);
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Shop_Details.cd.isConnectingToInternet()) {
                    Shop_Details.nointernet.setVisibility(View.GONE);
                    new loadstatus().execute(new String[0]);
                    return;
                }
                Shop_Details.nointernet.setVisibility(View.VISIBLE);
                Toasty.info(Shop_Details.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        photo1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!Shop_Details.hasPermissions(Shop_Details.this, Shop_Details.PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Shop_Details.this, Shop_Details.PERMISSIONS, 1);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory());
                    sb.append("/");
                    sb.append(Temp.foldername);
                    File folder = new File(sb.toString());
                    if (!folder.exists()) {
                        folder.mkdir();
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Environment.getExternalStorageDirectory());
                        sb2.append("/");
                        sb2.append(Temp.foldername);
                        sb2.append("/.nomedia");
                        try {
                            new File(sb2.toString()).createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Shop_Details.selectImage();
                } catch (Exception e2) {
                }
            }
        });
        location1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Shop_Details.this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                    ActivityCompat.requestPermissions(Shop_Details.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
                } else if (!Shop_Details.location.hasLocationEnabled()) {
                    new EnableGPS(Shop_Details.this, Shop_Details.getApplicationContext()).mEnableGps();
                } else {
                    Shop_Details.pd.show();
                    Shop_Details.startgoogleconnect();
                }
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            new loadstatus().execute(new String[0]);
        } else {
            nointernet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
        }
        sunday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.changeback(Shop_Details.sunday);
            }
        });
        monday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.changeback(Shop_Details.monday);
            }
        });
        tuesday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.changeback(Shop_Details.tuesday);
            }
        });
        wendesday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.changeback(Shop_Details.wendesday);
            }
        });
        thursday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.changeback(Shop_Details.thursday);
            }
        });
        friday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.changeback(Shop_Details.friday);
            }
        });
        satrdy.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.changeback(Shop_Details.satrdy);
            }
        });
        open.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.whichclick = 1;
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(Shop_Details.this, now.get(10), now.get(12), now.get(13), true);
                dpd.setVersion(Version.VERSION_1);
                dpd.show(Shop_Details.getFragmentManager(), "Select Open Time");
            }
        });
        close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shop_Details.whichclick = 2;
                Calendar now = Calendar.getInstance();
                TimePickerDialog.newInstance(Shop_Details.this, now.get(10), now.get(12), now.get(13), true).show(Shop_Details.getFragmentManager(), "Select Open Time");
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    int i = 0;
                    if (!Shop_Details.open.getText().toString().equalsIgnoreCase("")) {
                        if (!Shop_Details.close.getText().toString().equalsIgnoreCase("")) {
                            if (Shop_Details.deliinkm.getText().toString().equalsIgnoreCase("")) {
                                Toasty.info(Shop_Details.getApplicationContext(), "Please enter km", Toast.LENGTH_SHORT).show();
                                Shop_Details.deliinkm.requestFocus();
                                return;
                            } else if (!Shop_Details.checkdayclicked()) {
                                Toasty.info(Shop_Details.getApplicationContext(), "Please select your Shop Days", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (Shop_Details.lattiude.getText().toString().equalsIgnoreCase("")) {
                                Toasty.info(Shop_Details.getApplicationContext(), "Please enter latitude", Toast.LENGTH_SHORT).show();
                                Shop_Details.lattiude.requestFocus();
                                return;
                            } else if (Shop_Details.longtitude.getText().toString().equalsIgnoreCase("")) {
                                Toasty.info(Shop_Details.getApplicationContext(), "Please enter longtitude", Toast.LENGTH_SHORT).show();
                                Shop_Details.longtitude.requestFocus();
                                return;
                            } else if (Shop_Details.cd.isConnectingToInternet()) {
                                Shop_Details.get_clickedbutnid();
                                if (Shop_Details.deliverydisc.getText().toString().equalsIgnoreCase("")) {
                                    Shop_Details.deliinfo = "NA";
                                } else {
                                    Shop_Details.deliinfo = Shop_Details.deliverydisc.getText().toString();
                                }
                                Shop_Details.delicharge = "0";
                                while (true) {
                                    int i2 = i;
                                    if (i2 >= Shop_Details.feedItems.size()) {
                                        break;
                                    }
                                    DelichargeList_FeedItem item = (DelichargeList_FeedItem) Shop_Details.feedItems.get(i2);
                                    if (Shop_Details.delicharge == "0") {
                                        Shop_Details shop_Details = Shop_Details.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(item.getToamt());
                                        sb.append("-");
                                        sb.append(item.getCharge());
                                        shop_Details.delicharge = sb.toString();
                                    } else {
                                        Shop_Details shop_Details2 = Shop_Details.this;
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(Shop_Details.delicharge);
                                        sb2.append("#");
                                        sb2.append(item.getToamt());
                                        sb2.append("-");
                                        sb2.append(item.getCharge());
                                        shop_Details2.delicharge = sb2.toString();
                                    }
                                    i = i2 + 1;
                                }
                                if (Shop_Details.minimumcharge.getText().toString().equalsIgnoreCase("")) {
                                    Shop_Details.micharge = "0";
                                } else {
                                    Shop_Details.micharge = Shop_Details.minimumcharge.getText().toString();
                                }
                                Shop_Details.uploadingprogress();
                                return;
                            } else {
                                Toasty.info(Shop_Details.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    Toasty.info(Shop_Details.getApplicationContext(), "Please select open and closing time", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnItems(list);
        } catch (Exception e) {
        }
    }
    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(this);
        builder.setTitle("Shop Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera((Activity) Shop_Details.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery((Activity) Shop_Details.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Remove Photo")) {
                    Shop_Details.photopath1 = "removed";
                    Shop_Details.photo1.setImageDrawable(Shop_Details.getResources().getDrawable(R.drawable.nophoto));
                }
            }
        });
        builder.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        EasyImage.handleActivityResult(requestCode, resultCode, data2, this, new DefaultCallback() {
            public void onImagePickerError(Exception e, ImageSource source, int type) {
            }

            public void onImagePicked(File imageFile, ImageSource source, int type) {
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                sb.append("/");
                sb.append(Temp.foldername);
                sb.append("/shoppic.jpg");
                File f = new File(sb.toString());
                try {
                    f.createNewFile();
                } catch (IOException e) {
                }
                if (f != null) {
                    try {
                        Uri uri = Uri.fromFile(f);
                        Options options = new Options();
                        options.setFreeStyleCropEnabled(true);
                        options.setToolbarColor(Color.parseColor("#205c14"));
                        options.setStatusBarColor(Color.parseColor("#2E7D32"));
                        options.setCompressionFormat(CompressFormat.JPEG);
                        options.setCompressionQuality(80);
                        options.setToolbarTitle("Crop Image");
                        UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4.0f, 3.0f).start(Shop_Details.this);
                    } catch (Exception e2) {
                    }
                }
            }
        });
        if (requestCode == 69) {
            try {
                photopath1 = UCrop.getOutput(data2).getPath();
                img = BitmapFactory.decodeFile(photopath1);
                photo1.setImageBitmap(img);
            } catch (Exception a) {
                Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void uploadingprogress() {
        try {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogupload);
            pb2 = (ProgressBar) dialog.findViewById(R.id.pb1);
            persentage = (TextView) dialog.findViewById(R.id.persentage);
            stop = (Button) dialog.findViewById(R.id.stop);
            new UploadFileToServer().execute(new String[0]);
            stop.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        Shop_Details.httppost.abort();
                    } catch (Exception e) {
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
        }
    }
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void startgoogleconnect() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        }
        mGoogleApiClient.connect();
    }

    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                pd.dismiss();
                stopLocationUpdates();
                EditText editText = lattiude;
                StringBuilder sb = new StringBuilder();
                sb.append(mLocation.getLatitude());
                sb.append("");
                editText.setText(sb.toString());
                EditText editText2 = longtitude;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(mLocation.getLongitude());
                sb2.append("");
                editText2.setText(sb2.toString());
            } else {
                startLocationUpdates();
            }
        }
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void onLocationChanged(Location location2) {
        if (location2 != null) {
            stopLocationUpdates();
        }
        pd.dismiss();
        EditText editText = lattiude;
        StringBuilder sb = new StringBuilder();
        sb.append(location2.getLatitude());
        sb.append("");
        editText.setText(sb.toString());
        EditText editText2 = longtitude;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(location2.getLongitude());
        sb2.append("");
        editText2.setText(sb2.toString());
    }
    public void startLocationUpdates() {
        pd.show();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(100);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION");
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }
    public void onDestroy() {
        super.onDestroy();
        try {
            stopLocationUpdates();
        } catch (Exception e) {
        }
    }

    public void stopLocationUpdates() {
        try {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
                mGoogleApiClient.disconnect();
            }
        } catch (Exception e) {
        }
    }

    public void changeback(Button view) {
        if (view.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.roundedcornerbutton).getConstantState())) {
            view.setBackground(getResources().getDrawable(R.drawable.roundedcornerspecial));
        } else {
            view.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        }
    }

    public boolean checkdayclicked() {
        for (int id : DAY_BUTTON_IDS) {
            if (!((Button) findViewById(id)).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.roundedcornerspecial).getConstantState())) {
                return true;
            }
        }
        return false;
    }

    public void get_clickedbutnid() {
        buttonids = "";
        int cnt = 0;
        for (int id : DAY_BUTTON_IDS) {
            cnt++;
            if (((Button) findViewById(id)).getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.roundedcornerbutton).getConstantState())) {
                if (buttonids.equalsIgnoreCase("")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cnt);
                    sb.append("");
                    buttonids = sb.toString();
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(buttonids);
                    sb2.append(",");
                    sb2.append(cnt);
                    buttonids = sb2.toString();
                }
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(open.getText().toString());
        sb3.append("-");
        sb3.append(close.getText().toString());
        sb3.append("##");
        sb3.append(buttonids);
        buttonids = sb3.toString();
    }

    public void checktrue_daysbutton(String id) {
        if (id.contains(",")) {
            String[] k = id.split(",");
            for (int j = 0; j < k.length; j++) {
                if (k[j].equalsIgnoreCase("1")) {
                    sunday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
                } else if (k[j].equalsIgnoreCase("2")) {
                    monday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
                } else if (k[j].equalsIgnoreCase("3")) {
                    tuesday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
                } else if (k[j].equalsIgnoreCase("4")) {
                    wendesday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
                } else if (k[j].equalsIgnoreCase("5")) {
                    thursday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
                } else if (k[j].equalsIgnoreCase("6")) {
                    friday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
                } else if (k[j].equalsIgnoreCase("7")) {
                    satrdy.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
                }
            }
        } else if (id.equalsIgnoreCase("1")) {
            sunday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        } else if (id.equalsIgnoreCase("2")) {
            monday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        } else if (id.equalsIgnoreCase("3")) {
            tuesday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        } else if (id.equalsIgnoreCase("4")) {
            wendesday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        } else if (id.equalsIgnoreCase("5")) {
            thursday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        } else if (id.equalsIgnoreCase("6")) {
            friday.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        } else if (id.equalsIgnoreCase("7")) {
            satrdy.setBackground(getResources().getDrawable(R.drawable.roundedcornerbutton));
        }
    }

    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (whichclick == 1) {
            EditText editText = open;
            StringBuilder sb = new StringBuilder();
            sb.append(hourOfDay);
            sb.append(":");
            sb.append(minute);
            editText.setText(sb.toString());
        } else if (whichclick == 2) {
            EditText editText2 = close;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(hourOfDay);
            sb2.append(":");
            sb2.append(minute);
            editText2.setText(sb2.toString());
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
