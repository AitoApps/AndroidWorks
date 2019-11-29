package com.hellokhd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.NowRunning_Adapter;
import adapter.StageList_Adapter;
import data.NowRunning_FeedItem;
import data.StageList_FeedItem;
import es.dmoral.toasty.Toasty;

public class Programs extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private long FASTEST_INTERVAL = 5000;
    private long UPDATE_INTERVAL = 15000;
    double lat = 0.0d;
    double lng = 0.0d;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    private LocationRequest mLocationRequest;
    int PERMISSION_ALL1 = 99;
    String[] PERMISSIONS2 = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    ImageView back,nointernet,heart,nodata;
    HeaderAndFooterRecyclerView recyclerview;
    final DatabaseHandler db=new DatabaseHandler(this);

    public NowRunning_Adapter adapter;
    public List<NowRunning_FeedItem> feeditem;
    Typeface face;
    TextView text;
    ImageView ads;
    String callmob="";
    final UserDatabaseHandler udb=new UserDatabaseHandler(this);
    public float ogheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);
        ads=findViewById(R.id.ads);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        back=findViewById(R.id.back);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        recyclerview=findViewById(R.id.recyclerview);
        text=findViewById(R.id.text);
        text.setTypeface(face);
        feeditem = new ArrayList();
        adapter = new NowRunning_Adapter(this, feeditem);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);

        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ogheight = Float.parseFloat(udb.getscreenwidth()) / 4.0f;
        ogheight *= 1.0f;
        ads.getLayoutParams().height = Math.round(ogheight);
        load_bannerad();

        if (!hasPermissions(Programs.this, PERMISSIONS2)) {
            ActivityCompat.requestPermissions(Programs.this, PERMISSIONS2, PERMISSION_ALL1);
        }
        else
        {
            getlocation();
        }
    }
    public void load_bannerad()
    {
        ArrayList<String> id1 = db.getbanner();
        // String[] k1= (String[]) id1.toArray(new String[id1.size()]);

        ArrayList<String> id2 = db.getaccomodation();
        //  String[] k2= (String[]) id2.toArray(new String[id2.size()]);

        ArrayList<String> id3 = db.getfood();
        //  String[] k3= (String[]) id3.toArray(new String[id3.size()]);

        ArrayList<String> id4 = db.getrooms();
        // String[] k4= (String[]) id4.toArray(new String[id4.size()]);

        ArrayList<String> id5 = db.gettourism();
        //String[] k5= (String[]) id5.toArray(new String[id5.size()]);

        ArrayList<String> id6 = db.getcinima();
        //String[] k6= (String[]) id6.toArray(new String[id6.size()]);

        ArrayList<String> arraylist=new ArrayList<String>();
        ArrayList<String> arraylist1=new ArrayList<String>();
        arraylist1.clear();
        arraylist1.add("1");
        arraylist1.add("2");
        arraylist1.add("3");
        arraylist1.add("4");
        arraylist1.add("5");
        arraylist1.add("6");
        Collections.shuffle(arraylist1);
        for(int i=0;i<arraylist1.size();i++)
        {
            if(arraylist1.get(i).equalsIgnoreCase("1"))
            {
                arraylist.addAll(id1);
            }
            else if(arraylist1.get(i).equalsIgnoreCase("2"))
            {
                arraylist.addAll(id2);
            }
            else if(arraylist1.get(i).equalsIgnoreCase("3"))
            {
                arraylist.addAll(id3);
            }
            else if(arraylist1.get(i).equalsIgnoreCase("4"))
            {
                arraylist.addAll(id4);
            }
            else if(arraylist1.get(i).equalsIgnoreCase("5"))
            {
                arraylist.addAll(id5);
            }
            else if(arraylist1.get(i).equalsIgnoreCase("6"))
            {
                arraylist.addAll(id6);
            }
        }


        String[] k= (String[]) arraylist.toArray(new String[arraylist.size()]);

        if(k.length>0)
        {
            String ad1="";
            int m=0;
            for(int i=0;i<1;i++)
            {
                m=m+1;
                int a1=i;
                i=i+1;
                int a2=i;
                i=i+1;
                int a3=i;
                i=i+1;
                int a4=i;

                ad1=k[a1]+":%"+k[a2]+":%"+k[a3]+":%"+k[a4];
            }

            if(!ad1.equalsIgnoreCase(""))
            {
                String[] p=ad1.split(":%");
                String adsn=p[0];
                String linktype=p[1];
                String refernce=p[2];
                String imgsig=p[3];
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(imgsig));
                Glide.with(this).load(Temp.weblink+"advt/"+adsn+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(ads);

                ads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openlinks(linktype,refernce);


                    }
                });

            }

        }
    }

    public void getlocation() {
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled("gps")) {
            new EnableGPS(this, getApplicationContext()).mEnableGps();
        }
        else
        {
            heart.setVisibility(View.VISIBLE);
            startgoogleconnect();
        }

    }
    public void startgoogleconnect() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        }
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == 0 || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == 0) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                stopLocationUpdates();
                lat = mLocation.getLatitude();
                lng = mLocation.getLongitude();
                aftergetgps(lat,lng);
            } else {
                startLocationUpdates();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            stopLocationUpdates();
            lat = location.getLatitude();
            lng = location.getLongitude();
            aftergetgps(lat,lng);
        }
    }

    public void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(100);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != 0) {
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ALL1 && grantResults.length > 0 && grantResults[0] == 0 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
            getlocation();
        }
        else if (requestCode==1)
        {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Programs.this, android.Manifest.permission.CALL_PHONE) != 0) {
                        ActivityCompat.requestPermissions(Programs.this, new String[]{ android.Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+callmob));
                        startActivity(callIntent);
                    }

                } else {

                }
                return;
            }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 1010 && resultCode == -1) {
            getlocation();
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

    public void aftergetgps(double lat,double lng)
    {
        db.addlocation(lat+"",lng+"");
        new loadstages().execute();
    }

    public class loadstages extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            heart.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"getprogramlist_bylocation.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(db.get_latitude()+":%"+db.get_longtitude(), "UTF-8");
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
                if (result.contains(":%ok")) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String[] got = result.split(":%");
                                int k = (got.length - 1) /8;
                                int m = -1;
                                for (int i = 1; i <= k; i++) {
                                    NowRunning_FeedItem item2 = new NowRunning_FeedItem();
                                    m=m+1;
                                    item2.setSn(got[m]);
                                    m=m+1;
                                    item2.setStagenumber(got[m]);
                                    m=m+1;
                                    item2.setStagename(got[m]);
                                    m=m+1;
                                    item2.setCurrentprogram(got[m]);
                                    m=m+1;
                                    item2.setProgramtime(got[m]);
                                    m=m+1;
                                    item2.setLatitude(got[m]);
                                    m=m+1;
                                    item2.setLongtitude(got[m]);
                                    m=m+1;
                                    item2.setDistance(String.format("%.2f", Float.parseFloat(got[m])));
                                    feeditem.add(item2);
                                }
                            }
                        });
                        heart.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                } else {
                    heart.setVisibility(View.GONE);
                }
            } catch (Exception e2) {
            }
        }
    }

    public void openlinks(String linktype,String refernce)
    {
        if(linktype.equalsIgnoreCase("1"))
        {
            call(refernce);
        }
        else if(linktype.equalsIgnoreCase("2"))
        {
            //App
            openapp(refernce);

        }
        else if(linktype.equalsIgnoreCase("3"))
        {
            openwebsite(refernce);

        }
        else if(linktype.equalsIgnoreCase("4"))
        {
            openyoutube(refernce);

        }
        else if(linktype.equalsIgnoreCase("5"))
        {
            openfacebook(refernce);

        }
        else if(linktype.equalsIgnoreCase("6"))
        {
            showmap(refernce);
        }

    }

    public void showmap(String gps) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:"+gps));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } catch (Exception e) {
            Toasty.error(getApplicationContext(), "Please install google map app", 0).show();
        }
    }


    public void openapp(String packagename)
    {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packagename)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packagename)));
        }
    }
    public void openwebsite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void openfacebook(String username) {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/"+username)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+username)));
        }
    }

    public void openinstagram(String username) {
        Intent likeIng = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/"+username));
        likeIng.setPackage("com.instagram.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+username)));
        }
    }

    public void openpinterest(String username) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("pinterest://www.pinterest.com/")));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pinterest.com/"+username)));
        }
    }

    public void openyoutube(String channelname) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/user/"+channelname));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent2 = new Intent(Intent.ACTION_VIEW);
            intent2.setData(Uri.parse("https://www.youtube.com/user/"+channelname));
            startActivity(intent2);
        }
    }


    public void call(final String mob) {
        try {
            final Dialog dialog3 = new Dialog(this);
            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog3.setContentView(R.layout.custom_call);
            dialog3.setCancelable(true);
            TextView title=dialog3.findViewById(R.id.title);
            Button no=dialog3.findViewById(R.id.no);
            Button yes=dialog3.findViewById(R.id.yes);


            title.setTypeface(face);
            no.setTypeface(face);
            yes.setTypeface(face);


            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog3.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(Programs.this, android.Manifest.permission.CALL_PHONE) != 0) {
                        callmob=mob;
                        ActivityCompat.requestPermissions(Programs.this, new String[]{ android.Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+mob));
                        startActivity(callIntent);
                    }
                    dialog3.dismiss();
                }
            });
            dialog3.show();
        } catch (Exception e) {
        }
    }
}
