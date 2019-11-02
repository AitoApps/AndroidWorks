package com.appsbag_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class Add_View extends AppCompatActivity {

    ImageView back;
    TextView text;
    TextView txtenglish,txtmalayalam,txthindi,txttamil,txttelugu,txtphoto1;
    Button update;
    ImageView photo1;
    ProgressBar pb1;
    Typeface face;
    EditText english,malayalam,hindi,tamil,telugu;
    String photopath1 = "none";
    Bitmap img;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};

    public static String txt_english="",txt_malayalam="",txt_hindi="",txt_tamil="",txt_telugu="";
    final DatabaseHandler db=new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__view);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        back=findViewById(R.id.back);
        photo1=findViewById(R.id.photo1);
        pb1=findViewById(R.id.pb1);

        text=findViewById(R.id.text);
        txtenglish=findViewById(R.id.txtenglish);
        txtmalayalam=findViewById(R.id.txtmalayalam);
        txthindi=findViewById(R.id.txthindi);
        txttamil=findViewById(R.id.txttamil);
        txttelugu=findViewById(R.id.txttelugu);
        txtphoto1=findViewById(R.id.txtphoto1);
        update=findViewById(R.id.update);
        english=findViewById(R.id.english);
        malayalam=findViewById(R.id.malayalam);
        hindi=findViewById(R.id.hindi);
        tamil=findViewById(R.id.tamil);
        telugu=findViewById(R.id.telugu);

        text.setTypeface(face);
        txtenglish.setTypeface(face);
        txtmalayalam.setTypeface(face);
        txthindi.setTypeface(face);
        txttamil.setTypeface(face);
        txttelugu.setTypeface(face);
        txtphoto1.setTypeface(face);
        update.setTypeface(face);
        english.setTypeface(face);
        malayalam.setTypeface(face);
        hindi.setTypeface(face);
        tamil.setTypeface(face);
        telugu.setTypeface(face);

        if(Temp.appviewedit==1)
        {
            english.setText(Temp.appview_english);
            malayalam.setText(Temp.appview_malayalam);
            hindi.setText(Temp.appview_hindi);
            tamil.setText(Temp.appview_tamil);
            telugu.setText(Temp.appview_telugu);
            photopath1=Temp.appview_photopath;
            if(Temp.appedit==1)
            {

                Glide.with(this).load(Temp.appview_fbpath).transition(DrawableTransitionOptions.withCrossFade()).into(photo1);
            }
            else
            {
                File file = new File(Temp.appview_photopath);
                Uri imageUri = Uri.fromFile(file);
                Glide.with(this).load(imageUri).transition(DrawableTransitionOptions.withCrossFade()).into(photo1);
            }


        }
        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!hasPermissions(Add_View.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_View.this, PERMISSIONS, PERMISSION_ALL);
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
            @Override
            public void onClick(View v) {

              if(english.getText().toString().equalsIgnoreCase(""))
                        {
                            txt_english="NA";
                        }
                        else
                        {
                            txt_english=english.getText().toString();
                        }

                if(malayalam.getText().toString().equalsIgnoreCase(""))
                {
                    txt_malayalam="NA";
                }
                else
                {
                    txt_malayalam=malayalam.getText().toString();
                }

                if(hindi.getText().toString().equalsIgnoreCase(""))
                {
                    txt_hindi="NA";
                }
                else
                {
                    txt_hindi=hindi.getText().toString();
                }


                if(tamil.getText().toString().equalsIgnoreCase(""))
                {
                    txt_tamil="NA";
                }
                else
                {
                    txt_tamil=tamil.getText().toString();
                }

                if(telugu.getText().toString().equalsIgnoreCase(""))
                {
                    txt_telugu="NA";
                }
                else
                {
                    txt_telugu=telugu.getText().toString();
                }

                if(Temp.appviewedit==1)
                {
                     db.addappview_update(Temp.appview_pkey,txt_english,txt_malayalam,txt_hindi,txt_tamil,txt_telugu,photopath1,Temp.appview_datasn);
                }
                else
                {
                    db.addappview(txt_english,txt_malayalam,txt_hindi,txt_tamil,txt_telugu,photopath1,"0","");
                }

                finish();



            }
        });
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Screenshot");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_View.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_View.this, 1);
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
                    options.setFreeStyleCropEnabled(true);
                    options.setToolbarColor(Color.parseColor("#205c14"));
                    options.setStatusBarColor(Color.parseColor("#2E7D32"));
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setCompressionQuality(80);
                    options.setToolbarTitle("Crop Image");
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).start(Add_View.this);
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
