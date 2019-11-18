package com.hellokhd_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import im.delight.android.location.SimpleLocation;
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

public class Add_Cinema extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private long FASTEST_INTERVAL = 5000;
    private long UPDATE_INTERVAL = 15000;
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    public Bitmap img;
    EditText lattiude;
    public SimpleLocation location;
    ImageView location1;
    EditText longtitude;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    private LocationRequest mLocationRequest;
    public float ogheight;
    EditText theatorname,filmname,showtime,discirption;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    ProgressBar prb1;
    public Button stop;
    TextView text;
    TextView txtphoto1,txtlongtitude,txtlattiude;
    Button update;
    Call call;
    boolean requestgoing=true;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};

    int PERMISSION_ALL2 = 99;
    String[] PERMISSIONS2 = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__cinema);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        pd = new ProgressDialog(this);
        theatorname=findViewById(R.id.theatorname);
        filmname=findViewById(R.id.filmname);
        showtime=findViewById(R.id.showtime);
        discirption=findViewById(R.id.discirption);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        photo1 = (ImageView) findViewById(R.id.photo1);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        update.setTypeface(face);
        location1 = (ImageView) findViewById(R.id.location);
        lattiude = (EditText) findViewById(R.id.lattiude);
        longtitude = (EditText) findViewById(R.id.longtitude);
        location = new SimpleLocation(this);
        txtlattiude = (TextView) findViewById(R.id.txtlattiude);
        txtlongtitude = (TextView) findViewById(R.id.txtlongtitude);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);

        lattiude.setTypeface(face);
        longtitude.setTypeface(face);



        theatorname.setTypeface(face);
        filmname.setTypeface(face);
        showtime.setTypeface(face);
        discirption.setTypeface(face);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });


        location1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (!hasPermissions(Add_Cinema.this, PERMISSIONS2)) {
                    ActivityCompat.requestPermissions(Add_Cinema.this, PERMISSIONS2, PERMISSION_ALL2);
                } else {
                    if (!location.hasLocationEnabled()) {
                        new EnableGPS(Add_Cinema.this, getApplicationContext()).mEnableGps();
                    } else {
                        pd.show();
                        pd.setMessage("Please wait...");
                        startgoogleconnect();
                    }
                }

            }
        });
        photo1.post(new Runnable() {
            public void run() {
                ogheight = Float.parseFloat(db.getscreenwidth()) / 4.0f;
                ogheight *= 3.0f;
                photo1.getLayoutParams().height = Math.round(ogheight);
            }
        });
        photo1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!hasPermissions(Add_Cinema.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Cinema.this, PERMISSIONS, PERMISSION_ALL);
                    } else {
                        File folder = new File(Environment.getExternalStorageDirectory() + "/" + Temp.foldername);
                        if (!folder.exists()) {
                            folder.mkdir();
                            try {
                                new File(Environment.getExternalStorageDirectory() + "/" + Temp.foldername + "/.nomedia").createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        selectImage();
                    }

                } catch (Exception e2) {
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {

                    if (theatorname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter theator name", Toast.LENGTH_SHORT).show();
                        theatorname.requestFocus();
                    }
                    else if (filmname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter filmname", Toast.LENGTH_SHORT).show();
                        filmname.requestFocus();
                    }
                    else if (showtime.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter showtime", Toast.LENGTH_SHORT).show();
                        showtime.requestFocus();
                    }
                    else if (discirption.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter discription", Toast.LENGTH_SHORT).show();
                        discirption.requestFocus();
                    }
                    else {

                        if (cd.isConnectingToInternet()) {
                            uploadingprogress();

                        } else {
                            Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                } catch (Exception a) {

                }
            }
        });


        if (Temp.cinmaedit == 1) {

            theatorname.setText(Temp.cini_name);
            filmname.setText(Temp.cini_filmname);
            showtime.setText(Temp.cini_showtime);
            discirption.setText(Temp.cini_discription);
            lattiude.setText(Temp.cini_latitude);
            longtitude.setText(Temp.cini_longtitude);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.cini_imgsig)));
                    apply.load(Temp.weblink + "cinima/" + Temp.cini_sn + ".jpg").into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            prb1.setVisibility(View.GONE);
                            photo1.setImageBitmap(bitmap);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            prb1.setVisibility(View.GONE);
                            photo1.setImageResource(R.drawable.nophoto);
                        }
                    });
                }
            });


        }
    }

    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Poster");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_Cinema.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_Cinema.this, 1);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        EasyImage.handleActivityResult(requestCode, resultCode, data2, this, new DefaultCallback() {
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            }

            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                File f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/productpic1.jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                }
                try {
                    Uri uri = Uri.fromFile(f);
                    UCrop.Options options = new UCrop.Options();
                    options.setFreeStyleCropEnabled(true);
                    options.setToolbarColor(Color.parseColor("#205c14"));
                    options.setStatusBarColor(Color.parseColor("#2E7D32"));
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setCompressionQuality(80);
                    options.setToolbarTitle("Crop Image");
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).start(Add_Cinema.this);
                } catch (Exception e2) {
                }
            }
        });
        if (requestCode == UCrop.REQUEST_CROP) {
            try {
                photopath1 = UCrop.getOutput(data2).getPath();
                img = BitmapFactory.decodeFile(photopath1);
                photo1.setImageBitmap(img);
            } catch (Exception e) {
            }
        }
    }

    public void startgoogleconnect() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        }
        mGoogleApiClient.connect();
    }

    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == 0 || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == 0) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                pd.dismiss();
                stopLocationUpdates();
                lattiude.setText(mLocation.getLatitude()+"");
                longtitude.setText(mLocation.getLongitude()+"");
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
        lattiude.setText(location2.getLatitude()+"");
        longtitude.setText(location2.getLongitude()+"");
    }
    public void startLocationUpdates() {
        pd.show();
        pd.setMessage("Please wait...");
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


    public void uploadingprogress() {
        try {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogupload);
            pb1 = (ProgressBar) dialog.findViewById(R.id.pb1);
            persentage = (TextView) dialog.findViewById(R.id.persentage);
            stop = (Button) dialog.findViewById(R.id.stop);
            uploadfiletoserver();
            stop.setOnClickListener(new View.OnClickListener() {
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

    public void uploadfiletoserver()
    {
        requestgoing=true;
        pb1.setVisibility(View.VISIBLE);
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
        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.stagedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.stagesn));
        bodyBuilder.addFormDataPart("theatorname", null,RequestBody.create(contentType,theatorname.getText().toString()));
        bodyBuilder.addFormDataPart("filmname", null,RequestBody.create(contentType, filmname.getText().toString()));
        bodyBuilder.addFormDataPart("showtime", null,RequestBody.create(contentType, showtime.getText().toString()));
        bodyBuilder.addFormDataPart("discirption", null,RequestBody.create(contentType, discirption.getText().toString()));
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
                .url(Temp.weblink+"addcinimaadmin.php")
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
                            Log.w("Resukttt",result);
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
                            } else if (result.contains("exit")) {
                                Toasty.info(getApplicationContext(), "Sorry ! This Stage is exist", Toast.LENGTH_SHORT).show();
                            } else {
                                Toasty.info(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception a)
                        {

                        }
                    }
                });
            }
        });
    }
}
