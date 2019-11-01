package com.sanji;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location_Picker extends AppCompatActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageView back;
    TextView curlocation;
    final DatabaseHandler db = new DatabaseHandler(this);
    Typeface face;
    Typeface face1;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    public GoogleMap mMap;
    SupportMapFragment mapFragment;
    double oldlat = 0.0d;
    double oldlng = 0.0d;
    ProgressBar pb;
    TextView title;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_location__picker);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        final RadioButton radio1 = (RadioButton) findViewById(R.id.radio1);
        final EditText pincode = (EditText) findViewById(R.id.pincode);
        final RadioButton radio2 = (RadioButton) findViewById(R.id.radio2);
        TextView textView = (TextView) findViewById(R.id.gps);
        TextView textView2 = (TextView) findViewById(R.id.txtsetdelivery);
        pb = (ProgressBar) findViewById(R.id.pb);
        TextView textView3 = (TextView) findViewById(R.id.txtlocation);
        curlocation = (TextView) findViewById(R.id.location);
        Button confirmloc = (Button) findViewById(R.id.confirmloc);
        ImageView search = (ImageView) findViewById(R.id.search);
        ImageView imageView = (ImageView) findViewById(R.id.mapicon);
        radio2.setChecked(true);
        radio1.setChecked(false);
        pincode.setEnabled(false);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Location_Picker.onBackPressed();
            }
        });
        title.setTypeface(face);
        confirmloc.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "Please pick your locality";
                if (Location_Picker.curlocation.getText().toString().equalsIgnoreCase("Identifying Your Location...")) {
                    Toasty.info(Location_Picker.getApplicationContext(), str, 0).show();
                } else if (Location_Picker.curlocation.getText().toString().equalsIgnoreCase("Unable to get your location...")) {
                    Toasty.info(Location_Picker.getApplicationContext(), str, 0).show();
                } else {
                    DatabaseHandler databaseHandler = Location_Picker.db;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Location_Picker.oldlat);
                    String str2 = "";
                    sb.append(str2);
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(Location_Picker.oldlng);
                    sb3.append(str2);
                    databaseHandler.addlocation(sb2, sb3.toString(), Location_Picker.curlocation.getText().toString());
                    Intent intent = new Intent(Location_Picker.getApplicationContext(), Control_Panel.class);
                    intent.addFlags(67108864);
                    Location_Picker.startActivity(intent);
                }
            }
        });
        pincode.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                radio1.setChecked(true);
                radio2.setChecked(false);
                pincode.setEnabled(true);
            }
        });
        radio2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                radio2.setChecked(true);
                radio1.setChecked(false);
                pincode.setEnabled(false);
                Location_Picker.load_current();
            }
        });
        radio1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                radio1.setChecked(true);
                radio2.setChecked(false);
                pincode.setEnabled(true);
            }
        });
        search.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (pincode.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Location_Picker.getApplicationContext(), "Please enter pincode", 0).show();
                    pincode.requestFocus();
                    return;
                }
                Location_Picker.pb.setVisibility(View.VISIBLE);
                Location_Picker.curlocation.setText("Identifying Your Location...");
                pincode.setEnabled(false);
                Location_Picker.runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            List<Address> addresses = new Geocoder(Location_Picker.this, Locale.getDefault()).getFromLocationName(pincode.getText().toString(), 5);
                            if (addresses.size() > 0) {
                                double lat = ((Address) addresses.get(0)).getLatitude();
                                double lng = ((Address) addresses.get(0)).getLongitude();
                                Location_Picker.oldlat = lat;
                                Location_Picker.oldlng = lng;
                                Location_Picker.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Location_Picker.oldlat, Location_Picker.oldlng), 13.0f));
                                Location_Picker.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(Location_Picker.oldlat, Location_Picker.oldlng)).zoom(17.0f).bearing(90.0f).tilt(40.0f).build()));
                                Location_Picker.show_address(Location_Picker.oldlat, Location_Picker.oldlng);
                            } else {
                                Location_Picker.curlocation.setText("Unable to get your location...");
                            }
                            Location_Picker.pb.setVisibility(View.GONE);
                            pincode.setEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        pincode.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != 0 || keyCode != 66) {
                    return false;
                }
                if (pincode.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Location_Picker.getApplicationContext(), "Please enter pincode", 0).show();
                    pincode.requestFocus();
                } else {
                    Location_Picker.pb.setVisibility(View.VISIBLE);
                    Location_Picker.curlocation.setText("Identifying Your Location...");
                    pincode.setEnabled(false);
                    Location_Picker.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                List<Address> addresses = new Geocoder(Location_Picker.this, Locale.getDefault()).getFromLocationName(pincode.getText().toString(), 5);
                                if (addresses.size() > 0) {
                                    double lat = ((Address) addresses.get(0)).getLatitude();
                                    double lng = ((Address) addresses.get(0)).getLongitude();
                                    Location_Picker.oldlat = lat;
                                    Location_Picker.oldlng = lng;
                                    Location_Picker.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Location_Picker.oldlat, Location_Picker.oldlng), 13.0f));
                                    Location_Picker.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(Location_Picker.oldlat, Location_Picker.oldlng)).zoom(17.0f).bearing(90.0f).tilt(40.0f).build()));
                                    Location_Picker.show_address(Location_Picker.oldlat, Location_Picker.oldlng);
                                } else {
                                    Location_Picker.curlocation.setText("Unable to get your location...");
                                }
                                Location_Picker.pb.setVisibility(View.GONE);
                                pincode.setEnabled(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                return true;
            }
        });
        if (!((LocationManager) getSystemService("location")).isProviderEnabled("gps")) {
            new EnableGPS(this, getApplicationContext()).mEnableGps();
        } else if (checkLocationPermission()) {
            load_current();
        } else {
            checkLocationPermission();
        }
    }

    public void load_current() {
        pb.setVisibility(View.VISIBLE);
        curlocation.setText("Identifying Your Location...");
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(1);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(11.874477d, 75.370369d));
        mMap.moveCamera(point);
        mMap.animateCamera(point);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.setOnCameraIdleListener(new OnCameraIdleListener() {
            public void onCameraIdle() {
                double lat = Location_Picker.mMap.getCameraPosition().target.latitude;
                double lng = Location_Picker.mMap.getCameraPosition().target.longitude;
                if (Location_Picker.oldlat != lat && Location_Picker.oldlng != lng) {
                    Location_Picker location_Picker = Location_Picker.this;
                    location_Picker.oldlat = lat;
                    location_Picker.oldlng = lng;
                    location_Picker.show_address(location_Picker.oldlat, Location_Picker.oldlng);
                }
            }
        });
        if (VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    public synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
            mGoogleApiClient.connect();
        } catch (Exception e) {
        }
    }

    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(102);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
        }
    }

    public void onConnectionSuspended(int i) {
    }

    public void onLocationChanged(Location location) {
        mLastLocation = location;
        oldlat = location.getLatitude();
        oldlng = location.getLongitude();
        find_Address(location.getLatitude(), location.getLongitude());
    }

    public void find_Address(double latitude, double longtitude) {
        pb.setVisibility(View.VISIBLE);
        String bestProvider = ((LocationManager) getSystemService("location")).getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longtitude), 13.0f));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(latitude, longtitude)).zoom(17.0f).bearing(90.0f).tilt(40.0f).build()));
            try {
                if (mGoogleApiClient != null) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
                }
            } catch (Exception e) {
            }
        }
    }

    public void show_address(double latitude, double longtitude) {
        pb.setVisibility(View.VISIBLE);
        final Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        final double d = latitude;
        final double d2 = longtitude;
        AnonymousClass9 r2 = new Runnable() {
            public void run() {
                try {
                    List<Address> listAddresses = geocoder.getFromLocation(d, d2, 1);
                    if (listAddresses != null && listAddresses.size() > 0) {
                        Location_Picker.curlocation.setText(((Address) listAddresses.get(0)).getAddressLine(0));
                        Location_Picker.pb.setVisibility(View.GONE);
                    }
                } catch (IOException e) {
                }
            }
        };
        runOnUiThread(r2);
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public boolean checkLocationPermission() {
        String str = "android.permission.ACCESS_FINE_LOCATION";
        if (ContextCompat.checkSelfPermission(this, str) == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
            ActivityCompat.requestPermissions(this, new String[]{str}, 99);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{str}, 99);
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 99 && grantResults.length > 0 && grantResults[0] == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            load_current();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        if (requestCode != 1010 || resultCode != -1) {
            return;
        }
        if (checkLocationPermission()) {
            load_current();
        } else {
            checkLocationPermission();
        }
    }
}
