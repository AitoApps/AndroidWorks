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

public class Add_Shops extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

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
    EditText shopname,discription,contact;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    ProgressBar prb1;
    public Button stop;
    TextView text;
    TextView txtphoto1,txtlongtitude,txtlattiude,txtshopname,txtdiscription;
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
        setContentView(R.layout.activity_add__shops);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        pd = new ProgressDialog(this);
        txtshopname = findViewById(R.id.txtshopname);
        shopname = findViewById(R.id.shopname);
        txtdiscription=findViewById(R.id.txtdiscription);
        discription=findViewById(R.id.discription);
        contact=findViewById(R.id.contact);
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

        txtlattiude.setTypeface(face1);
        txtlongtitude.setTypeface(face1);
        txtphoto1.setTypeface(face1);

        if(Temp.shoptypes.equalsIgnoreCase("0"))
        {
            txtshopname.setText("Shop Name");
            text.setText("Add / Edit Shops");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("1"))
        {
            txtshopname.setText("Spot Name");
            text.setText("Add / Edit Tourist Spots");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("2"))
        {
            txtshopname.setText("Room Name");
            text.setText("Add / Edit Room");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("3"))
        {
            txtshopname.setText("Docter Name");
            text.setText("Add / Edit Docters");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("4"))
        {
            txtshopname.setText("ATM Name");
            text.setText("Add / Edit ATM");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("5"))
        {
            txtshopname.setText("Pumb Name");
            text.setText("Add / Edit Petrol Pumb");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("6"))
        {
            txtshopname.setText("Hospital");
            text.setText("Add / Edit Hospital");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("7"))
        {
            txtshopname.setText("Accomodation");
            text.setText("Add / Edit Accomodation");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("8"))
        {
            txtshopname.setText("Food");
            text.setText("Add / Edit Food");
        }
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });


        location1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (!hasPermissions(Add_Shops.this, PERMISSIONS2)) {
                    ActivityCompat.requestPermissions(Add_Shops.this, PERMISSIONS2, PERMISSION_ALL2);
                } else {
                    if (!location.hasLocationEnabled()) {
                        new EnableGPS(Add_Shops.this, getApplicationContext()).mEnableGps();
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
                    if (!hasPermissions(Add_Shops.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Shops.this, PERMISSIONS, PERMISSION_ALL);
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

                    if (shopname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter shopname", Toast.LENGTH_SHORT).show();
                        shopname.requestFocus();
                    }
                    else if (contact.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter contact", Toast.LENGTH_SHORT).show();
                        contact.requestFocus();
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


        if (Temp.shopedit == 1) {
            shopname.setText(Temp.shopname);
            contact.setText(Temp.shopcontact);
            discription.setText(Temp.shopdisc);

            if (Temp.shoplocation.contains(",")) {
                String[] s = Temp.shoplocation.split(",");
                lattiude.setText(s[0]);
                longtitude.setText(s[1]);

            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.shopimgsig)));
                    apply.load(Temp.weblink + "shop/" + Temp.shopsn + ".jpg").into(new SimpleTarget<Bitmap>() {
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
        if(Temp.shoptypes.equalsIgnoreCase("0"))
        {
            builder.setTitle("Shop Photo");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("1"))
        {
            builder.setTitle("Place Photo");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("2"))
        {
            builder.setTitle("Room Photo");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("3"))
        {
            builder.setTitle("Docters Photo");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("4"))
        {
            builder.setTitle("ATM Photo");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("5"))
        {
            builder.setTitle("Pumb Photo");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("6"))
        {
            builder.setTitle("Hospital");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("7"))
        {
            builder.setTitle("Accomodation");
        }
        else if(Temp.shoptypes.equalsIgnoreCase("8"))
        {
            builder.setTitle("Food");
        }
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_Shops.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_Shops.this, 1);
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
                    options.setFreeStyleCropEnabled(false);
                    options.setToolbarColor(Color.parseColor("#205c14"));
                    options.setStatusBarColor(Color.parseColor("#2E7D32"));
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setCompressionQuality(80);
                    options.setToolbarTitle("Crop Image");
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4.0f, 3.0f).start(Add_Shops.this);
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
        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.shopedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.shopsn));
        bodyBuilder.addFormDataPart("shoptypes", null,RequestBody.create(contentType,Temp.shoptypes));
        bodyBuilder.addFormDataPart("shopname", null,RequestBody.create(contentType, shopname.getText().toString()));
        bodyBuilder.addFormDataPart("discription", null,RequestBody.create(contentType, discription.getText().toString()));
        bodyBuilder.addFormDataPart("contact", null,RequestBody.create(contentType, contact.getText().toString()));
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
                .url(Temp.weblink+"addshopbyadmin.php")
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
                            } else if (result.contains("exit")) {
                                Toasty.info(getApplicationContext(), "Sorry ! This Shop is exist", Toast.LENGTH_SHORT).show();
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

