package com.appsbag_admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class App_Details extends AppCompatActivity {

    ImageView back;
    TextView text;
    TextView txtappname,txtappurl,txtphoto1,txtdiscsheader,txttitleeng,txttitlemal,txttitlehindi,txttitletamil,txttitletelugu,txtdiscsfooter,txtfootereng,txtfootermal,txtfooterhindi,txtfootertamil,txtfootertelugu;
    EditText appname,appurl;
    Button next;
    Typeface face;
    ImageView clearall;
    ConnectionDetecter cd;
    final DatabaseHandler db=new DatabaseHandler(this);
    public static String txt_appname="",txt_appurl="",txt_openhead="",txt_opendisc="";
    EditText titleeng,titlemal,titlehindi,titletamil,titletelugu,footereng,footermal,footerhindi,footertamil,footertelugu;
    String photopath1 = "none";
    ImageView photo1;
    ProgressBar pb1;
    Bitmap img;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__details);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        back=findViewById(R.id.back);
        photo1=findViewById(R.id.photo1);
        pb1=findViewById(R.id.pb1);
        text=findViewById(R.id.text);
        txtphoto1=findViewById(R.id.txtphoto1);
        txtappname=findViewById(R.id.txtappname);
        txtappurl=findViewById(R.id.txtappurl);
        appname=findViewById(R.id.appname);
        appurl=findViewById(R.id.appurl);
        clearall=findViewById(R.id.clearall);
        next=findViewById(R.id.next);
        cd=new ConnectionDetecter(this);


        txtdiscsheader=findViewById(R.id.txtdiscsheader);
        txttitleeng=findViewById(R.id.txttitleeng);
        txttitlemal=findViewById(R.id.txttitlemal);
        txttitlehindi=findViewById(R.id.txttitlehindi);
        txttitletamil=findViewById(R.id.txttitletamil);
        txttitletelugu=findViewById(R.id.txttitletelugu);
        txtdiscsfooter=findViewById(R.id.txtdiscsfooter);
        txtfootereng=findViewById(R.id.txtfootereng);
        txtfootermal=findViewById(R.id.txtfootermal);
        txtfooterhindi=findViewById(R.id.txtfooterhindi);
        txtfootertamil=findViewById(R.id.txtfootertamil);
        txtfootertelugu=findViewById(R.id.txtfootertelugu);
        titleeng=findViewById(R.id.titleeng);
        titlemal=findViewById(R.id.titlemal);
        titlehindi=findViewById(R.id.titlehindi);
        titletamil=findViewById(R.id.titletamil);
        titletelugu=findViewById(R.id.titletelugu);
        footereng=findViewById(R.id.footereng);
        footermal=findViewById(R.id.footermal);
        footerhindi=findViewById(R.id.footerhindi);
        footertamil=findViewById(R.id.footertamil);
        footertelugu=findViewById(R.id.footertelugu);

        text.setTypeface(face);
        txtphoto1.setTypeface(face);
        txtappname.setTypeface(face);
        txtappurl.setTypeface(face);
        appname.setTypeface(face);
        appurl.setTypeface(face);
        next.setTypeface(face);


        txtdiscsheader.setTypeface(face);
        txttitleeng.setTypeface(face);
        txttitlemal.setTypeface(face);
        txttitlehindi.setTypeface(face);
        txttitletamil.setTypeface(face);
        txttitletelugu.setTypeface(face);
        txtdiscsfooter.setTypeface(face);
        txtfootereng.setTypeface(face);
        txtfootermal.setTypeface(face);
        txtfooterhindi.setTypeface(face);
        txtfootertamil.setTypeface(face);
        txtfootertelugu.setTypeface(face);
        titleeng.setTypeface(face);
        titlemal.setTypeface(face);
        titlehindi.setTypeface(face);
        titletamil.setTypeface(face);
        titletelugu.setTypeface(face);
        footereng.setTypeface(face);
        footermal.setTypeface(face);
        footerhindi.setTypeface(face);
        footertamil.setTypeface(face);
        footertelugu.setTypeface(face);

        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!hasPermissions(App_Details.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(App_Details.this, PERMISSIONS, PERMISSION_ALL);
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

        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(App_Details.this);
                builder1.setMessage("Are you sure want to clear ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteappdetails();
                                db.deleteappview();
                                setprevious();
                                photopath1 = "NA";
                                photo1.setImageDrawable(getResources().getDrawable(R.drawable.nophoto));
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnectingToInternet())
                {
                    if(appname.getText().toString().equalsIgnoreCase(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please enter appname",Toast.LENGTH_SHORT).show();
                        appname.requestFocus();
                    }
                    else if(appurl.getText().toString().equalsIgnoreCase(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please enter app url",Toast.LENGTH_SHORT).show();
                        appurl.requestFocus();
                    }
                    else
                    {
                        txt_appname=appname.getText().toString();
                        txt_appurl=appurl.getText().toString();

                         if(titleeng.getText().toString().equalsIgnoreCase(""))
                         {
                             txt_openhead="NA";
                         }
                         else
                         {
                             txt_openhead=titleeng.getText().toString();
                         }

                        if(titlemal.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_openhead=txt_openhead+":%"+"NA";
                        }
                        else
                        {
                            txt_openhead=txt_openhead+":%"+titlemal.getText().toString();
                        }

                        if(titlehindi.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_openhead=txt_openhead+":%"+"NA";
                        }
                        else
                        {
                            txt_openhead=txt_openhead+":%"+titlehindi.getText().toString();
                        }
                        if(titletamil.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_openhead=txt_openhead+":%"+"NA";
                        }
                        else
                        {
                            txt_openhead=txt_openhead+":%"+titletamil.getText().toString();
                        }
                        if(titletelugu.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_openhead=txt_openhead+":%"+"NA";
                        }
                        else
                        {
                            txt_openhead=txt_openhead+":%"+titletelugu.getText().toString();
                        }

                        if(footereng.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_opendisc="NA";
                        }
                        else
                        {
                            txt_opendisc=footereng.getText().toString();
                        }

                        if(footermal.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_opendisc=txt_opendisc+":%"+"NA";
                        }
                        else
                        {
                            txt_opendisc=txt_opendisc+":%"+footermal.getText().toString();
                        }


                        if(footerhindi.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_opendisc=txt_opendisc+":%"+"NA";
                        }
                        else
                        {
                            txt_opendisc=txt_opendisc+":%"+footerhindi.getText().toString();
                        }

                        if(footertamil.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_opendisc=txt_opendisc+":%"+"NA";
                        }
                        else
                        {
                            txt_opendisc=txt_opendisc+":%"+footertamil.getText().toString();
                        }
                        if(footertelugu.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_opendisc=txt_opendisc+":%"+"NA";
                        }
                        else
                        {
                            txt_opendisc=txt_opendisc+":%"+footertelugu.getText().toString();
                        }
                        db.addappdetails(txt_appname,txt_appurl,txt_openhead,txt_opendisc,photopath1);
                        startActivity(new Intent(getApplicationContext(),Add_Apps.class));
                    }
                }
                else
                {
                    Toasty.info(getApplicationContext(),Temp.nointernet, Toast.LENGTH_SHORT).show();
                }

            }
        });


        try
        {
            setprevious();
        }
        catch (Exception a)
        {

        }

    }



    public void setprevious()
    {
        appname.setText(db.getappname());
        appurl.setText(db.getappurl());

        if(!db.gettitle().equalsIgnoreCase(""))
        {
            String[] p=db.gettitle().split(":%");
            if(p[0].equalsIgnoreCase("NA"))
            {
                titleeng.setText("");
            }
            else
           {
                    titleeng.setText(p[0]);
                }

            if(p[1].equalsIgnoreCase("NA"))
            {
                titlemal.setText("");
            }
            else
            {
                titlemal.setText(p[1]);
            }

            if(p[2].equalsIgnoreCase("NA"))
            {
                titlehindi.setText("");
            }
            else
            {
                titlehindi.setText(p[2]);
            }
            if(p[3].equalsIgnoreCase("NA"))
            {
                titletamil.setText("");
            }
            else
            {
                titletamil.setText(p[3]);
            }
            if(p[4].equalsIgnoreCase("NA"))
            {
                titletelugu.setText("");
            }
            else
            {
                titletelugu.setText(p[4]);
            }
        }
        else
        {
            titleeng.setText("");
            titletamil.setText("");
            titlehindi.setText("");
            titletamil.setText("");
            titletelugu.setText("");
        }

        if(!db.getfooter().equalsIgnoreCase(""))
        {
            String[] p=db.getfooter().split(":%");

            if(p[0].equalsIgnoreCase("NA"))
            {
                footereng.setText("");
            }
            else
            {
                footereng.setText(p[0]);
            }

            if(p[1].equalsIgnoreCase("NA"))
            {
                footermal.setText("");
            }
            else
            {
                footermal.setText(p[1]);
            }

            if(p[2].equalsIgnoreCase("NA"))
            {
                footerhindi.setText("");
            }
            else
            {
                footerhindi.setText(p[2]);
            }
            if(p[3].equalsIgnoreCase("NA"))
            {
                footertamil.setText("");
            }
            else
            {
                footertamil.setText(p[3]);
            }
            if(p[4].equalsIgnoreCase("NA"))
            {
                footertelugu.setText("");
            }
            else
            {
                footertelugu.setText(p[4]);
            }
        }
        else
        {
            footereng.setText("");
            footermal.setText("");
            footerhindi.setText("");
            footertamil.setText("");
            footertelugu.setText("");
        }

        try
        {
            if(Temp.appedit==1)
            {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.appimgsig)));
                        apply.load(Temp.weblink+"applogo/"+Temp.appeditsn+".png").into(new SimpleTarget<Bitmap>() {
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                photo1.setImageBitmap(bitmap);
                            }

                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                photo1.setImageResource(R.drawable.nophoto);
                            }
                        });
                    }
                });

            }
            else
            {
                if(!db.getphotopath().equalsIgnoreCase("") && !db.getphotopath().equalsIgnoreCase("NA") )
                {
                    File file = new File(db.getphotopath());
                    Uri imageUri = Uri.fromFile(file);
                    Glide.with(this).load(imageUri).transition(DrawableTransitionOptions.withCrossFade()).into(photo1);
                }
            }


        }
        catch (Exception a)
        {

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
    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("App Logo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(App_Details.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(App_Details.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Remove Photo")) {
                    photopath1 = "NA";
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
                File f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/"+System.currentTimeMillis()+"_productpic.jpg");
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
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4,4).start(App_Details.this);
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

}
