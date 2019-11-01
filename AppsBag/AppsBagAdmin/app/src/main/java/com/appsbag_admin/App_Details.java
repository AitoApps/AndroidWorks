package com.appsbag_admin;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class App_Details extends AppCompatActivity {

    ImageView back;
    TextView text;
    TextView txtappname,txtappurl,txtopenhead,txtopendisc,txtdisctitle,txtdiscfooter,txtphoto1;
    EditText appname,appurl,openhead,opendisc,disctitle,discfooter;
    Button next;
    Typeface face;
    ImageView clearall;
    ConnectionDetecter cd;
    final DatabaseHandler db=new DatabaseHandler(this);
    public static String txt_appname="",txt_appurl="",txt_openhead="",txt_opendisc="",txt_disctitle="",txt_discfooter="";
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
        txtopenhead=findViewById(R.id.txtopenhead);
        txtopendisc=findViewById(R.id.txtopendisc);
        txtdisctitle=findViewById(R.id.txtdisctitle);
        txtdiscfooter=findViewById(R.id.txtdiscfooter);
        appname=findViewById(R.id.appname);
        appurl=findViewById(R.id.appurl);
        openhead=findViewById(R.id.openhead);
        opendisc=findViewById(R.id.opendisc);
        disctitle=findViewById(R.id.disctitle);
        clearall=findViewById(R.id.clearall);
        discfooter=findViewById(R.id.discfooter);
        next=findViewById(R.id.next);
        cd=new ConnectionDetecter(this);

        text.setTypeface(face);
        txtphoto1.setTypeface(face);
        txtappname.setTypeface(face);
        txtappurl.setTypeface(face);
        txtopenhead.setTypeface(face);
        txtopendisc.setTypeface(face);
        txtdisctitle.setTypeface(face);
        txtdiscfooter.setTypeface(face);
        appname.setTypeface(face);
        appurl.setTypeface(face);
        openhead.setTypeface(face);
        opendisc.setTypeface(face);
        disctitle.setTypeface(face);
        discfooter.setTypeface(face);
        next.setTypeface(face);


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

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
                builder1.setMessage("Are you sure want to clear ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteappdetails();
                                db.deleteappview();
                                setprevious();
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

                         if(openhead.getText().toString().equalsIgnoreCase(""))
                         {
                             txt_openhead="NA";
                         }
                         else
                         {
                             txt_openhead=openhead.getText().toString();
                         }

                        if(opendisc.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_opendisc="NA";
                        }
                        else
                        {
                            txt_opendisc=opendisc.getText().toString();
                        }

                        if(disctitle.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_disctitle="NA";
                        }
                        else
                        {
                            txt_disctitle=disctitle.getText().toString();
                        }

                        if(discfooter.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_discfooter="NA";
                        }
                        else
                        {
                            txt_discfooter=discfooter.getText().toString();
                        }

                        db.addappdetails(txt_appname,txt_appurl,txt_openhead,txt_opendisc,txt_disctitle,txt_discfooter,photopath1);
                        startActivity(new Intent(getApplicationContext(),Add_Apps.class));
                    }
                }
                else
                {
                    Toasty.info(getApplicationContext(),Temp.nointernet, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

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
        openhead.setText(db.getopentitle());
        opendisc.setText(db.getopendisc());
        disctitle.setText(db.getdisctitle());
        discfooter.setText(db.getdiscfooter());

        try
        {
            File file = new File(db.getphotopath());
            Uri imageUri = Uri.fromFile(file);
            Glide.with(this).load(imageUri).transition(DrawableTransitionOptions.withCrossFade()).into(photo1);
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
                Log.w("Result",Log.getStackTraceString(e));
            }
        }
    }

}
