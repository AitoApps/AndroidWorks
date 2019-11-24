package com.hellokhd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.List;

import adapter.DistricHome_Adapter;
import adapter.FeaturedAds_Adapter;
import adapter.StageList_Adapter;
import data.DistricResult_FeedItem;
import data.StageList_FeedItem;
import es.dmoral.toasty.Toasty;

public class Stages extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
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

    public StageList_Adapter adapter;
    public List<StageList_FeedItem> feeditem;
    Typeface face;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stages);
        face= Typeface.createFromAsset(getAssets(), "proxibold.otf");
        back=findViewById(R.id.back);
        nointernet=findViewById(R.id.nointernet);
        heart=findViewById(R.id.heart);
        nodata=findViewById(R.id.nodata);
        recyclerview=findViewById(R.id.recyclerview);
        text=findViewById(R.id.text);
        text.setTypeface(face);
        feeditem = new ArrayList();
        adapter = new StageList_Adapter(this, feeditem);

        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(adapter);

        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!hasPermissions(Stages.this, PERMISSIONS2)) {
            ActivityCompat.requestPermissions(Stages.this, PERMISSIONS2, PERMISSION_ALL1);
        }
        else
        {
            getlocation();
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
                String link=Temp.weblink+"getstagelist_bylocation.php";
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
                                    StageList_FeedItem  item2 = new StageList_FeedItem ();
                                    m=m+1;
                                    item2.setSn(got[m]);
                                    m=m+1;
                                    item2.setStagenumber(got[m]);
                                    m=m+1;
                                    item2.setStagename(got[m]);
                                    m=m+1;
                                    item2.setPlace(got[m]);
                                    m=m+1;
                                    item2.setLatitude(got[m]);
                                    m=m+1;
                                    item2.setLongtitude(got[m]);
                                    m=m+1;
                                    item2.setImgsig(got[m]);
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
}
