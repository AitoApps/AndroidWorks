package com.daydeal_shop;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
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
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
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
import java.util.concurrent.TimeUnit;
import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource;

public class Shop_Details extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private long FASTEST_INTERVAL = 5000;

    private long UPDATE_INTERVAL = 15000;
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    ImageView heart;
    public Bitmap img;
    EditText lattiude;
    ScrollView layout;
    public SimpleLocation location;
    ImageView location1;
    EditText longtitude;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    private LocationRequest mLocationRequest;
    EditText mobile1;
    EditText mobile2;
    ImageView nointernet;
    public float ogheight;
    EditText ownername;
    ProgressBar pb1;
    public ProgressBar pb2;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    EditText place;
    EditText shopname;
    public Button stop;
    TextView text;
    TextView txtlattiude;
    TextView txtlongtitude;
    TextView txtmobile1;
    TextView txtmobile2;
    TextView txtownername;
    TextView txtphoto1;
    TextView txtplace;
    TextView txtshopname;
    public String txtsn = "";
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button update;
    int whichclick = 0;
    Call call;
    boolean requestgoing=true;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_shop__details);
        shopname = (EditText) findViewById(R.id.shopname);
        ownername = (EditText) findViewById(R.id.ownername);
        mobile1 = (EditText) findViewById(R.id.mobile1);
        mobile2 = (EditText) findViewById(R.id.mobile2);
        place = (EditText) findViewById(R.id.place);
        lattiude = (EditText) findViewById(R.id.lattiude);
        longtitude = (EditText) findViewById(R.id.longtitude);
        location1 = (ImageView) findViewById(R.id.location);
        heart = (ImageView) findViewById(R.id.heart);
        update = (Button) findViewById(R.id.update);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        pb1 = (ProgressBar) findViewById(R.id.pb1);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        location = new SimpleLocation(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        layout = (ScrollView) findViewById(R.id.layout);
        pd = new ProgressDialog(this);
        photo1 = (ImageView) findViewById(R.id.photo1);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
               onBackPressed();
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
        txtshopname.setTypeface(face1);
        txtownername.setTypeface(face1);
        txtmobile1.setTypeface(face1);
        txtmobile2.setTypeface(face1);
        txtplace.setTypeface(face1);
        txtlattiude.setTypeface(face1);
        txtlongtitude.setTypeface(face1);
        txtphoto1.setTypeface(face1);
        update.setTypeface(face);
        photo1.post(new Runnable() {
            public void run() {
               ogheight = Float.parseFloat(db.getscreenwidth()) / 4.0f;
               ogheight *= 3.0f;
               photo1.getLayoutParams().height = Math.round(ogheight);
            }
        });
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                   nointernet.setVisibility(View.GONE);
                    new loadstatus().execute(new String[0]);
                    return;
                }
               nointernet.setVisibility(View.VISIBLE);
                Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        photo1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!hasPermissions(Shop_Details.this,PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Shop_Details.this,PERMISSIONS, PERMISSION_ALL);
                        return;
                    }
                    File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                    if (!folder.exists()) {
                        folder.mkdir();
                        try {
                            new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/.nomedia").createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                   selectImage();
                } catch (Exception e2) {
                }
            }
        });
        location1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                if (ContextCompat.checkSelfPermission(Shop_Details.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != 0) {
                    ActivityCompat.requestPermissions(Shop_Details.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else if (!location.hasLocationEnabled()) {
                    new EnableGPS(Shop_Details.this,getApplicationContext()).mEnableGps();
                } else {
                   pd.show();
                   startgoogleconnect();
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
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                try {
                    if (lattiude.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter latitude", Toast.LENGTH_SHORT).show();
                       lattiude.requestFocus();
                    } else if (longtitude.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter longtitude", Toast.LENGTH_SHORT).show();
                       longtitude.requestFocus();
                    } else if (cd.isConnectingToInternet()) {
                       uploadingprogress();
                    } else {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
    }


    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(this);
        builder.setTitle("Shop Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Shop_Details.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Shop_Details.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Remove Photo")) {
                   photopath1 = "removed";
                   photo1.setImageDrawable(getResources().getDrawable(R.drawable.nophoto));
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
                File f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/shoppic.jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                }
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
        });
        if (requestCode == UCrop.REQUEST_CROP) {
            try {
                photopath1 = UCrop.getOutput(data2).getPath();
                img = BitmapFactory.decodeFile(photopath1);
                photo1.setImageBitmap(img);
            } catch (Exception a) {
                Toasty.info(getApplicationContext(), Log.getStackTraceString(a), 1).show();
            }
        }
    }

    public void uploadingprogress() {
        try {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogupload);
            pb1 = (ProgressBar) dialog.findViewById(R.id.pb1);
            persentage = (TextView) dialog.findViewById(R.id.persentage);
            stop = (Button) dialog.findViewById(R.id.stop);
            uploadfiletoserver();
            stop.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        requestgoing=false;
                        call.cancel();
                        dialog.dismiss();
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
        GoogleApiClient googleApiClient = mGoogleApiClient;
        if (googleApiClient != null) {
            googleApiClient.connect();
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
                String str = "";
                sb.append(str);
                editText.setText(sb.toString());
                EditText editText2 = longtitude;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(mLocation.getLongitude());
                sb2.append(str);
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
        String str = "";
        sb.append(str);
        editText.setText(sb.toString());
        EditText editText2 = longtitude;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(location2.getLongitude());
        sb2.append(str);
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
    public void uploadfiletoserver()
    {
        requestgoing=true;
        pb2.setVisibility(View.VISIBLE);
        dialog.setCancelable(false);
        persentage.setVisibility(View.VISIBLE);
        update.setEnabled(false);

        MediaType contentType=MediaType.parse("text/plain; charset=utf-8");
        OkHttpClient client;
        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.connectTimeout(5, TimeUnit.MINUTES);
        client1.readTimeout(5,TimeUnit.MINUTES);
        client1.writeTimeout(5,TimeUnit.MINUTES);

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        if (photopath1.equalsIgnoreCase("none")) {
            bodyBuilder.addFormDataPart("image1","none");
        } else if (photopath1.equalsIgnoreCase("removed")) {
            bodyBuilder.addFormDataPart("image1","removed");
        } else {
            bodyBuilder.addFormDataPart("image1","filled");
            File sourceFile = new File(photopath1);
            bodyBuilder.addFormDataPart("photo1", sourceFile.getName(), RequestBody.create(MediaType.parse("image/jpg"), sourceFile));
        }
        bodyBuilder.addFormDataPart("shopid", null,RequestBody.create(contentType,udb.get_shopid()));
        bodyBuilder.addFormDataPart("latitude", null,RequestBody.create(contentType, lattiude.getText().toString()));
        bodyBuilder.addFormDataPart("longtitude", null,RequestBody.create(contentType, longtitude.getText().toString()));

        MultipartBody body = bodyBuilder.build();

        RequestBody requestBody = ProgressHelper.withProgress(body, new ProgressUIListener() {

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressStart(long totalBytes) {
                super.onUIProgressStart(totalBytes);

            }

            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                persentage.setText((int) (100 * percent)+"%");
                //progress.setText("numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + "  MB/秒");
            }
            @Override
            public void onUIProgressFinish() {
                super.onUIProgressFinish();

            }

        });
        Request request = new Request.Builder()
                .url(Temp.weblink+"updateshopbyshop.php")
                .post(requestBody)
                .build();
        client = client1.build();
        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb1.setVisibility(View.GONE);
                        persentage.setVisibility(View.GONE);
                        update.setEnabled(true);
                        dialog.dismiss();
                        pd.dismiss();
                        if(requestgoing==true)
                        {
                            Toast.makeText(getApplicationContext(),"Please try later",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String result=response.body().string();
                            pb1.setVisibility(View.GONE);
                            persentage.setVisibility(View.GONE);
                            update.setEnabled(true);
                            dialog.dismiss();
                            if (result.contains("ok")) {
                                File file1 = new File(photopath1);
                                if (file1.exists()) {
                                    file1.delete();
                                }
                                Toasty.info(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                            Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception a)
                        {

                        }
                    }
                });
            }
        });
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            layout.setVisibility(View.GONE);
            heart.setVisibility(View.VISIBLE);
            nointernet.setVisibility(View.GONE);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"getshopdetails.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.get_shopid(), "UTF-8");
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
            heart.setVisibility(View.GONE);
            if (result.contains(":%ok")) {
                layout.setVisibility(View.VISIBLE);
                final String[] s = result.split(":%");
                txtsn = s[0];
                shopname.setText(s[1]);
                ownername.setText(s[2]);
                mobile1.setText(s[3]);
                mobile2.setText(s[4]);
                place.setText(s[5]);
                lattiude.setText(s[6]);
                longtitude.setText(s[7]);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        pb1.setVisibility(View.VISIBLE);
                        RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(s[8])));
                        apply.load(Temp.weblink+"shoppics/"+txtsn+".jpg").into(new SimpleTarget<Bitmap>() {
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                pb1.setVisibility(View.GONE);
                                photo1.setImageBitmap(bitmap);
                            }

                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                pb1.setVisibility(View.GONE);
                                photo1.setImageResource(R.drawable.nophoto);
                            }
                        });
                    }
                });
                return;
            }
            Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
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
}
