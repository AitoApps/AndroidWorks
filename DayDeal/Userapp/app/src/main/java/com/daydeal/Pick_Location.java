package com.daydeal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Pick_Location extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private long FASTEST_INTERVAL = 5000;
    private long UPDATE_INTERVAL = 15000;
    ImageView back;
    ConnectionDetecter cd;
    Button confirmloc;
    TextView currentlocation;
    DatabaseHandler db;
    Typeface face;
    ImageView gpsicon;
    double lat = 0.0d;
    double lng = 0.0d;
    RelativeLayout lytusinggps;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    private LocationRequest mLocationRequest;
    ProgressDialog pd;
    EditText pincode;
    ImageView search;
    TextView text;
    TextView txtcurrentlocation;
    TextView txtor;
    TextView txtpincode;
    TextView usinggps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_pick__location);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        currentlocation = (TextView) findViewById(R.id.currentlocation);
        txtpincode = (TextView) findViewById(R.id.txtpincode);
        txtor = (TextView) findViewById(R.id.txtor);
        pincode = (EditText) findViewById(R.id.pincode);
        search = (ImageView) findViewById(R.id.search);
        lytusinggps = (RelativeLayout) findViewById(R.id.lytusinggps);
        gpsicon = (ImageView) findViewById(R.id.gpsicon);
        usinggps = (TextView) findViewById(R.id.usinggps);
        cd = new ConnectionDetecter(this);
        db = new DatabaseHandler(this);
        txtcurrentlocation = (TextView) findViewById(R.id.txtcurrentlocation);
        confirmloc = (Button) findViewById(R.id.confirmloc);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        search.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (pincode.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter pincode", 0).show();
                    pincode.requestFocus();
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            pincode.setEnabled(false);
                            pd.setMessage("Please wait...");
                            pd.setCancelable(false);
                            pd.show();
                            List<Address> addresses = new Geocoder(Pick_Location.this, Locale.getDefault()).getFromLocationName(pincode.getText().toString(), 5);
                            if (addresses.size() > 0) {
                                lat = ((Address) addresses.get(0)).getLatitude();
                                lng = ((Address) addresses.get(0)).getLongitude();
                                show_address(lat, lng);
                            } else {
                                currentlocation.setText("Please use pincode or your gps");
                            }
                            pd.dismiss();
                            pincode.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                            pd.dismiss();
                            pincode.setEnabled(true);
                        }
                    }
                });
            }
        });
        lytusinggps.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                pincode.setText("");
                if (ContextCompat.checkSelfPermission(Pick_Location.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != 0) {
                    ActivityCompat.requestPermissions(Pick_Location.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
                    return;
                }
                getlocation();
            }
        });
        confirmloc.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String locvia;
                if (currentlocation.getText().toString().equalsIgnoreCase("Please use pincode or your gps")) {
                    Toast.makeText(getApplicationContext(), "Please your location", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pincode.getText().toString().equalsIgnoreCase("")) {
                    locvia = "2";
                } else {
                    locvia = "1";
                }
                db.addlocation(lat+"", lng+"", currentlocation.getText().toString(), locvia, pincode.getText().toString());
                finish();
            }
        });
    }

    public void getlocation() {
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled("gps")) {
            new EnableGPS(this, getApplicationContext()).mEnableGps();
        }
        else
        {
            pd.setMessage("Please wait...");
            pd.show();
            startgoogleconnect();
        }

    }

    public void startgoogleconnect() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        }
        mGoogleApiClient.connect();
    }

    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == 0 || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == 0) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                pd.dismiss();
                stopLocationUpdates();
                lat = mLocation.getLatitude();
                lng = mLocation.getLongitude();
                show_address(lat, lng);
            } else {
                startLocationUpdates();
            }
        }
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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
    public void onResume() {
        super.onResume();
        if (db.getlocavia().equalsIgnoreCase("1")) {
            pincode.setText(db.getpincode());
            currentlocation.setText(db.getaddress());
            return;
        }
        String str = "";
        if (db.getlocavia().equalsIgnoreCase("2")) {
            pincode.setText(str);
            currentlocation.setText(db.getaddress());
            return;
        }
        pincode.setText(str);
        currentlocation.setText("Please use pincode or your gps");
    }

    public void show_address(double latitude, double longtitude) {
        pd.setMessage("Getting your address");
        pd.setCancelable(false);
        pd.show();
        final Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        final double d = latitude;
        final double d2 = longtitude;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Address> listAddresses = geocoder.getFromLocation(d, d2, 1);
                    if (listAddresses != null && listAddresses.size() > 0) {
                        currentlocation.setText(((Address) listAddresses.get(0)).getAddressLine(0));
                    }
                    pd.dismiss();
                } catch (IOException e) {
                    pd.dismiss();
                }
            }
        });
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            stopLocationUpdates();
            pd.dismiss();
            lat = location.getLatitude();
            lng = location.getLongitude();
            show_address(lat, lng);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 99 && grantResults.length > 0 && grantResults[0] == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            getlocation();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        if (requestCode == 1010 && resultCode == -1) {
            getlocation();
        }
    }
}
