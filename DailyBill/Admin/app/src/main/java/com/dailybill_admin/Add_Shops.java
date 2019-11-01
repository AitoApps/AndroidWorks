package com.dailybill_admin;

import adapter.Delicharge_ListAdapter;

import android.Manifest;
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
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
import data.DelichargeList_FeedItem;
import es.dmoral.toasty.Toasty;
import im.delight.android.location.SimpleLocation;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
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


public class Add_Shops extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, TimePickerDialog.OnTimeSetListener {
    final int[] DAY_BUTTON_IDS = {R.id.sunday, R.id.monday, R.id.tuesday, R.id.wendesday, R.id.thursday, R.id.friday, R.id.satrdy};
    private long FASTEST_INTERVAL = 5000;

    private long UPDATE_INTERVAL = 15000;
    ImageView back;
    public String buttonids = "",txt_homedelivery="";
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
    public List<DelichargeList_FeedItem> feedItems;
    Button friday;
    public Bitmap img;
    EditText lattiude;
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
    public float ogheight;
    EditText open;
    EditText ownername;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    EditText place;
    ProgressBar prb1;
    Button satrdy;
    EditText shopname;
    public Button stop;
    Button sunday;
    TextView text,txtcatogery;
    MultiSelectSpinner catogery;
    String txt_catogeries="";
    Button thursday;
    EditText toamt;
    Button tuesday;
    TextView txthomedelivery,txtdeli,txtdeliinkm,txtlattiude,txtlongtitude,txtlytclosing,txtminimcharge,txtminimumcharge,txtmobile1,txtmobile2,txtownername,txtphoto1,txtplace,txtshopname;
    CheckBox homedelivery;
    Button update;
    Button wendesday;
    int whichclick = 0;
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
        txthomedelivery=findViewById(R.id.txthomedelivery);
        homedelivery=findViewById(R.id.homedelivery);
        cd = new ConnectionDetecter(this);
        txtcatogery=findViewById(R.id.txtcatogery);
        catogery= (MultiSelectSpinner)findViewById(R.id.catogery);
        toamt = (EditText) findViewById(R.id.toamt);
        charge = (EditText) findViewById(R.id.charge);
        chargeadd = (Button) findViewById(R.id.chargeadd);
        list = (ListView) findViewById(R.id.list);
        deliverydisc = (EditText) findViewById(R.id.deliverydisc);
        deliinkm = (EditText) findViewById(R.id.deliinkm);
        txtdeli = (TextView) findViewById(R.id.txtdeli);
        txtdeliinkm = (TextView) findViewById(R.id.txtdeliinkm);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        photo1 = (ImageView) findViewById(R.id.photo1);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        update.setTypeface(face);
        txtlytclosing = (TextView) findViewById(R.id.txtlytclosing);
        location1 = (ImageView) findViewById(R.id.location);
        shopname = (EditText) findViewById(R.id.shopname);
        ownername = (EditText) findViewById(R.id.ownername);
        mobile1 = (EditText) findViewById(R.id.mobile1);
        mobile2 = (EditText) findViewById(R.id.mobile2);
        place = (EditText) findViewById(R.id.place);
        lattiude = (EditText) findViewById(R.id.lattiude);
        longtitude = (EditText) findViewById(R.id.longtitude);
        location = new SimpleLocation(this);
        txtshopname = (TextView) findViewById(R.id.txtshopname);
        txtownername = (TextView) findViewById(R.id.txtownername);
        txtmobile1 = (TextView) findViewById(R.id.txtmobile1);
        txtmobile2 = (TextView) findViewById(R.id.txtmobile2);
        txtplace = (TextView) findViewById(R.id.txtplace);
        txtlattiude = (TextView) findViewById(R.id.txtlattiude);
        txtlongtitude = (TextView) findViewById(R.id.txtlongtitude);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);
        txtminimcharge = (TextView) findViewById(R.id.txtminimcharge);
        txtminimumcharge = (TextView) findViewById(R.id.txtminimumcharge);
        minimumcharge = (EditText) findViewById(R.id.minimumcharge);
        open = (EditText) findViewById(R.id.open);
        close = (EditText) findViewById(R.id.close);
        sunday = (Button) findViewById(R.id.sunday);
        monday = (Button) findViewById(R.id.monday);
        tuesday = (Button) findViewById(R.id.tuesday);
        wendesday = (Button) findViewById(R.id.wendesday);
        thursday = (Button) findViewById(R.id.thursday);
        friday = (Button) findViewById(R.id.friday);
        satrdy = (Button) findViewById(R.id.satrdy);
        shopname.setTypeface(face);
        ownername.setTypeface(face);
        mobile1.setTypeface(face);
        mobile2.setTypeface(face);
        place.setTypeface(face);
        lattiude.setTypeface(face);
        longtitude.setTypeface(face);
        txtlytclosing.setTypeface(face);
        open.setTypeface(face);
        close.setTypeface(face);
        deliinkm.setTypeface(face);
        txtdeli.setTypeface(face1);
        txtdeliinkm.setTypeface(face1);
        txtshopname.setTypeface(face1);
        txtownername.setTypeface(face1);
        txtmobile1.setTypeface(face1);
        txtmobile2.setTypeface(face1);
        txtcatogery.setTypeface(face1);
        txthomedelivery.setTypeface(face1);
        txtplace.setTypeface(face1);
        txtlattiude.setTypeface(face1);
        txtlongtitude.setTypeface(face1);
        txtphoto1.setTypeface(face1);
        txtminimcharge.setTypeface(face1);
        txtminimumcharge.setTypeface(face1);
        minimumcharge.setTypeface(face);
        homedelivery.setTypeface(face);
        toamt.setTypeface(face);
        charge.setTypeface(face);
        deliverydisc.setTypeface(face);
        feedItems = new ArrayList();
        listAdapter = new Delicharge_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        chargeadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (toamt.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), "Please enter to amount", Toast.LENGTH_SHORT).show();
                    toamt.requestFocus();
                } else if (charge.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(getApplicationContext(), "Please enter delivery charge", Toast.LENGTH_SHORT).show();
                    charge.requestFocus();
                } else {
                    DelichargeList_FeedItem item = new DelichargeList_FeedItem();
                    item.setToamt(toamt.getText().toString());
                    item.setCharge(charge.getText().toString());
                    feedItems.add(item);
                    listAdapter.notifyDataSetChanged();
                    toamt.setText("");
                    charge.setText("");
                    toamt.requestFocus();
                    setListViewHeightBasedOnItems(list);
                }
            }
        });
        ArrayList<String> lst_catogery = new ArrayList<>();
        lst_catogery.add("Grocery");
        lst_catogery.add("Vegitables");
        lst_catogery.add("Fruits");
        lst_catogery.add("Food");
        lst_catogery.add("Backery");


        ArrayList<String> lst_catogeryid = new ArrayList<>();
        lst_catogeryid.add("1");
        lst_catogeryid.add("2");
        lst_catogeryid.add("3");
        lst_catogeryid.add("4");
        lst_catogeryid.add("5");

        catogery.setItems(lst_catogery).setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {
                        txt_catogeries="";
                        for(int i=0; i<selected.length; i++) {
                            if(selected[i]) {
                                if(txt_catogeries.equalsIgnoreCase(""))
                                {
                                    txt_catogeries=lst_catogeryid.get(i);
                                }
                                else
                                {
                                    txt_catogeries=txt_catogeries+","+lst_catogeryid.get(i);
                                }
                            }
                        }

                    }
                })
                .setAllCheckedText("All Catogery").setAllUncheckedText("Select Catogery");


        location1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (!hasPermissions(Add_Shops.this, PERMISSIONS2)) {
                    ActivityCompat.requestPermissions(Add_Shops.this, PERMISSIONS2, PERMISSION_ALL2);
                }
                else
                {
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
                    }
                    else
                    {
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
                    }

                } catch (Exception e2) {
                }
            }
        });
        sunday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                changeback(sunday);
            }
        });
        monday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                changeback(monday);
            }
        });
        tuesday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                changeback(tuesday);
            }
        });
        wendesday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                changeback(wendesday);
            }
        });
        thursday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                changeback(thursday);
            }
        });
        friday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                changeback(friday);
            }
        });
        satrdy.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                changeback(satrdy);
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    int i = 0;
                    if(txt_catogeries.equalsIgnoreCase(""))
                    {
                        Toasty.info(getApplicationContext(),"Please select shop catogery",Toast.LENGTH_SHORT).show();
                    }
                    else if (shopname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter shop name", Toast.LENGTH_SHORT).show();
                        shopname.requestFocus();
                    } else if (shopname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter shopname", Toast.LENGTH_SHORT).show();
                    } else if (mobile1.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter mobile 1", Toast.LENGTH_SHORT).show();
                        mobile1.requestFocus();
                    } else if (place.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter place", Toast.LENGTH_SHORT).show();
                        place.requestFocus();
                    } else {
                        if (!open.getText().toString().equalsIgnoreCase("")) {
                            if (!close.getText().toString().equalsIgnoreCase("")) {
                                if (deliinkm.getText().toString().equalsIgnoreCase("")) {
                                    Toasty.info(getApplicationContext(), "Please enter km", Toast.LENGTH_SHORT).show();
                                    deliinkm.requestFocus();
                                    return;
                                } else if (!checkdayclicked()) {
                                    Toasty.info(getApplicationContext(), "Please select your Shop Days", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (cd.isConnectingToInternet()) {
                                    get_clickedbutnid();
                                    if (deliverydisc.getText().toString().equalsIgnoreCase("")) {
                                        deliinfo = "NA";
                                    } else {
                                        deliinfo = deliverydisc.getText().toString();
                                    }
                                    delicharge = "0";
                                    while (true) {
                                        int i2 = i;
                                        if (i2 >= feedItems.size()) {
                                            break;
                                        }
                                        DelichargeList_FeedItem item = (DelichargeList_FeedItem) feedItems.get(i2);
                                        if (delicharge == "0") {
                                            delicharge = item.getToamt()+"-"+item.getCharge();
                                        } else {
                                            delicharge = delicharge+"#"+item.getToamt()+"-"+item.getCharge();
                                        }
                                        i = i2 + 1;
                                    }
                                    if (minimumcharge.getText().toString().equalsIgnoreCase("")) {
                                        micharge = "0";
                                    } else {
                                        micharge = minimumcharge.getText().toString();
                                    }

                                    if(homedelivery.isChecked())
                                    {
                                        txt_homedelivery="1";
                                    }
                                    else
                                    {
                                        txt_homedelivery="0";
                                    }

                                    uploadingprogress();
                                    return;
                                } else {
                                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        Toasty.info(getApplicationContext(), "Please select open and closing time", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.isshopedit == 1) {
            shopname.setText(Temp.edit_shopname);
            ownername.setText(Temp.edit_ownername);
            mobile1.setText(Temp.edit_mobile1);
            mobile2.setText(Temp.edit_mobile2);
            place.setText(Temp.edit_place);
            lattiude.setText(Temp.edit_latitude);
            longtitude.setText(Temp.edit_longitude);
            deliinkm.setText(Temp.edit_delinkm);
            minimumcharge.setText(Temp.edit_minorderamt);
            if(Temp.editshop_homedelivery.equalsIgnoreCase("1"))
            {
                homedelivery.setChecked(true);
            }
            else
            {
                homedelivery.setChecked(false);
            }

            if(Temp.edit_shopcatogery.contains(","))
            {
                String[] m=Temp.edit_shopcatogery.split(",");
                for(int i=0;i<m.length;i++)
                {
                    if(txt_catogeries.equalsIgnoreCase(""))
                    {
                        txt_catogeries=m[i];
                    }
                    else
                    {
                        txt_catogeries=txt_catogeries+","+m[i];
                    }
                    catogery.selectItem(lst_catogeryid.indexOf(m[i]),true);
                }
            }
            else
            {
                txt_catogeries=Temp.edit_shopcatogery;
                catogery.selectItem(lst_catogeryid.indexOf(Temp.edit_shopcatogery),true);
            }

            String[] k = Temp.edit_shopdays.split("##");
            String[] k1 = k[0].split("-");
            open.setText(k1[0]);
            close.setText(k1[1]);
            checktrue_daysbutton(k[1]);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.edit_imgsig1)));
                    apply.load(Temp.weblink+"shoppics/"+Temp.edit_shopid+".jpg").into(new SimpleTarget<Bitmap>() {
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

            if (!Temp.edit_delichrge.equalsIgnoreCase("0") && !Temp.edit_delichrge.equalsIgnoreCase("NA")) {
                if (Temp.edit_delichrge.contains("#")) {
                    String[] p = Temp.edit_delichrge.split("#");
                    for (String split : p) {
                        String[] c = split.split("-");
                        DelichargeList_FeedItem item = new DelichargeList_FeedItem();
                        item.setToamt(c[0]);
                        item.setCharge(c[1]);
                        feedItems.add(item);
                    }
                    listAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnItems(list);
                } else {
                    String[] c2 = Temp.edit_delichrge.split("-");
                    DelichargeList_FeedItem item2 = new DelichargeList_FeedItem();
                    item2.setToamt(c2[0]);
                    item2.setCharge(c2[1]);
                    feedItems.add(item2);
                    listAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnItems(list);
                }
            }
            deliverydisc.setText(Temp.edit_delidisc);
        }
        open.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                whichclick = 1;
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(Add_Shops.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
                dpd.setVersion(Version.VERSION_1);
                dpd.show(getSupportFragmentManager(), "Select Open Time");
            }
        });
        close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                whichclick = 2;
                Calendar now = Calendar.getInstance();
                TimePickerDialog.newInstance(Add_Shops.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true).show(getSupportFragmentManager(), "Select Open Time");
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
            public void onImagePickerError(Exception e, ImageSource source, int type) {
            }

            public void onImagePicked(File imageFile, ImageSource source, int type) {
                File f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/productpic1.jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                }
                try {
                    Uri uri = Uri.fromFile(f);
                    Options options = new Options();
                    options.setFreeStyleCropEnabled(false);
                    options.setToolbarColor(Color.parseColor("#205c14"));
                    options.setStatusBarColor(Color.parseColor("#2E7D32"));
                    options.setCompressionFormat(CompressFormat.JPEG);
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
                    buttonids = cnt+"";
                } else {
                    buttonids =buttonids+","+cnt;
                }
            }
        }
        buttonids = open.getText().toString()+"-"+close.getText().toString()+"##"+buttonids;
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

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (whichclick == 1) {
            open.setText(hourOfDay+":"+minute);
        } else if (whichclick == 2) {
            close.setText(hourOfDay+":"+minute);
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


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            int topPAdding = listView.getPaddingTop();
            int bottomPadding = listView.getPaddingBottom();
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + topPAdding + bottomPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

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
            bodyBuilder.addFormDataPart("photo1", sourceFile.getName(), RequestBody.create(MediaType.parse("image/png"), sourceFile));
        }
        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.isshopedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.edit_shopid));
        bodyBuilder.addFormDataPart("shopname", null,RequestBody.create(contentType, shopname.getText().toString()));
        bodyBuilder.addFormDataPart("ownername", null,RequestBody.create(contentType, ownername.getText().toString()));
        bodyBuilder.addFormDataPart("mobile1", null,RequestBody.create(contentType, mobile1.getText().toString()));
        bodyBuilder.addFormDataPart("mobile2", null,RequestBody.create(contentType, mobile2.getText().toString()));
        bodyBuilder.addFormDataPart("place", null,RequestBody.create(contentType, place.getText().toString()));;
        bodyBuilder.addFormDataPart("latitude", null,RequestBody.create(contentType, lattiude.getText().toString()));
        bodyBuilder.addFormDataPart("longtitude", null,RequestBody.create(contentType, longtitude.getText().toString()));
        bodyBuilder.addFormDataPart("shoptime", null,RequestBody.create(contentType,buttonids));
        bodyBuilder.addFormDataPart("delicharge", null,RequestBody.create(contentType,delicharge));
        bodyBuilder.addFormDataPart("deliinfo", null,RequestBody.create(contentType, deliinfo));
        bodyBuilder.addFormDataPart("deliinkm", null,RequestBody.create(contentType, deliinkm.getText().toString()));
        bodyBuilder.addFormDataPart("minordramt", null,RequestBody.create(contentType, micharge));
        bodyBuilder.addFormDataPart("catogery", null,RequestBody.create(contentType, txt_catogeries));
        bodyBuilder.addFormDataPart("homedelivery", null,RequestBody.create(contentType,txt_homedelivery));

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
                                Toasty.info(getApplicationContext(), "Sorry ! The Mobile number is exist", Toast.LENGTH_SHORT).show();
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