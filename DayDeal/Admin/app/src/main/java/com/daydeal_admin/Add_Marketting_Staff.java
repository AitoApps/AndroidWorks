package com.daydeal_admin;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.io.IOException;
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

public class Add_Marketting_Staff extends AppCompatActivity {
     
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    String imagetype = "0";
    public Bitmap img;
    EditText mobile1;
    EditText mobile2;
    EditText name;
    public float ogheight;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    ImageView photo2;
    public String photopath1="none";
    EditText place;
    ProgressBar prb1;
    ProgressBar prb2;
    public String proofphotpath="none";
    public Button stop;
    TextView text;
    TextView txtmobile1;
    TextView txtmobile2;
    TextView txtname;
    TextView txtpersonalinfo;
    TextView txtphoto1;
    TextView txtphoto2;
    TextView txtphotos;
    TextView txtplace;
    Button update;
    Call call;
    boolean requestgoing=true;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__marketting__staff);
        name = (EditText) findViewById(R.id.name);
        place = (EditText) findViewById(R.id.place);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        prb2 = (ProgressBar) findViewById(R.id.pb2);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        photo1 = (ImageView) findViewById(R.id.photo1);
        txtpersonalinfo = (TextView) findViewById(R.id.txtpersonalinfo);
        txtname = (TextView) findViewById(R.id.txtname);
        txtplace = (TextView) findViewById(R.id.txtplace);
        txtmobile1 = (TextView) findViewById(R.id.txtmobile1);
        txtmobile2 = (TextView) findViewById(R.id.txtmobile2);
        txtphotos = (TextView) findViewById(R.id.txtphotos);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);
        txtphoto2 = (TextView) findViewById(R.id.txtphoto2);
        mobile1 = (EditText) findViewById(R.id.mobile1);
        mobile2 = (EditText) findViewById(R.id.mobile2);
        photo2 = (ImageView) findViewById(R.id.photo2);
        name.setTypeface(face);
        place.setTypeface(face);
        mobile1.setTypeface(face);
        mobile2.setTypeface(face);
        txtphoto1.setTypeface(face1);
        txtpersonalinfo.setTypeface(face1);
        txtname.setTypeface(face1);
        txtplace.setTypeface(face1);
        txtmobile1.setTypeface(face1);
        txtmobile2.setTypeface(face1);
        txtphotos.setTypeface(face1);
        txtphoto1.setTypeface(face1);
        txtphoto2.setTypeface(face1);
        text.setTypeface(face);
        update.setTypeface(face);
        proofphotpath = "none";
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        photo1.post(new Runnable() {
            public void run() {
                ogheight = Float.parseFloat(db.getscreenwidth()) / 4.0f;
                ogheight *= 4.0f;
                photo1.getLayoutParams().height = Math.round(ogheight);
            }
        });
        photo2.post(new Runnable() {
            public void run() {
                ogheight = Float.parseFloat(db.getscreenwidth()) / 4.0f;
                ogheight *= 4.0f;
                photo2.getLayoutParams().height = Math.round(ogheight);
            }
        });
        photo1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!hasPermissions(Add_Marketting_Staff.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Marketting_Staff.this, PERMISSIONS, PERMISSION_ALL);
                        return;
                    }
                    File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                    if (!folder.exists()) {
                        folder.mkdir();
                        try {
                            new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/"+"/.nomedia").createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    imagetype = "1";
                    selectImage();
                } catch (Exception e2) {
                }
            }
        });
        photo2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!hasPermissions(Add_Marketting_Staff.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Marketting_Staff.this, PERMISSIONS, PERMISSION_ALL);
                        return;
                    }
                    File folder = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername);
                    if (!folder.exists()) {
                        folder.mkdir();
                        try {
                            new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/"+"/.nomedia").createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    imagetype = "2";
                    selectImage();
                } catch (Exception e2) {
                }
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                try {
                    if (name.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (place.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter place", Toast.LENGTH_SHORT).show();
                        place.requestFocus();
                    } else if (mobile1.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter mobile 1", Toast.LENGTH_SHORT).show();
                        mobile1.requestFocus();
                    } else if (mobile2.getText().toString().equalsIgnoreCase(str)) {
                        Toasty.info(getApplicationContext(), "Please enter mobile 2", Toast.LENGTH_SHORT).show();
                        mobile2.requestFocus();
                    } else if (Temp.markettingstaffedit == 0 && (proofphotpath.equalsIgnoreCase("none") || proofphotpath.equalsIgnoreCase("removed"))) {
                        Toasty.info(getApplicationContext(), "Please pick proof photo", Toast.LENGTH_SHORT).show();
                    } else if (cd.isConnectingToInternet()) {
                        uploadingprogress();
                    } else {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.markettingstaffedit == 1) {
            name.setText(Temp.name);
            place.setText(Temp.place);
            mobile1.setText(Temp.mobile1);
            mobile2.setText(Temp.mobile2);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.proofimgsig)));
                    apply.load(Temp.weblink+"staffproof/"+Temp.mrktid+".jpg").into(new SimpleTarget<Bitmap>() {
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
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb2.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.proofimgsig)));
                    apply.load(Temp.weblink+"staffphoto/"+Temp.mrktid+".jpg").into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            prb2.setVisibility(View.GONE);
                            photo2.setImageBitmap(bitmap);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            prb2.setVisibility(View.GONE);
                            photo2.setImageResource(R.drawable.nophoto);
                        }
                    });
                }
            });
        }
    }


    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(this);
        if (imagetype.equalsIgnoreCase("1")) {
            builder.setTitle("Pick Proof Photo");
        } else if (imagetype.equalsIgnoreCase("2")) {
            builder.setTitle("Pick Profile Photo");
        }
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_Marketting_Staff.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_Marketting_Staff.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Remove Photo")) {
                    if (imagetype.equalsIgnoreCase("1")) {
                        proofphotpath =  "removed";
                        photo2.setImageDrawable(getResources().getDrawable(R.drawable.nophoto));
                    } else if (imagetype.equalsIgnoreCase("2")) {
                        photopath1 =  "removed";
                        photo1.setImageDrawable(getResources().getDrawable(R.drawable.nophoto));
                    }
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

                File f = null;
                if (imagetype.equalsIgnoreCase("1")) {
                    f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/proofpic.jpg");
                } else if (imagetype.equalsIgnoreCase("2")) {
                    f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/profilepic.jpg");
                }
                try {
                    f.createNewFile();
                } catch (IOException e) {
                }
                if (f != null) {
                    try {
                        Uri uri = Uri.fromFile(f);
                        Options options = new Options();
                        options.setFreeStyleCropEnabled(true);
                        options.setToolbarColor(Color.parseColor("#30be76"));
                        options.setStatusBarColor(Color.parseColor("#30be76"));
                        options.setCompressionFormat(CompressFormat.JPEG);
                        options.setCompressionQuality(80);
                        options.setToolbarTitle("Crop Image");
                        UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(1.0f, 1.0f).start(Add_Marketting_Staff.this);
                    } catch (Exception e2) {
                    }
                }
            }
        });
        if (requestCode == 69) {
            try {
                if (imagetype.equalsIgnoreCase("1")) {
                    proofphotpath = UCrop.getOutput(data2).getPath();
                    img = BitmapFactory.decodeFile(proofphotpath);
                    photo1.setImageBitmap(img);
                } else if (imagetype.equalsIgnoreCase("2")) {
                    photopath1 = UCrop.getOutput(data2).getPath();
                    img = BitmapFactory.decodeFile(photopath1);
                    photo2.setImageBitmap(img);
                }
            } catch (Exception e) {
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

        if (proofphotpath.equalsIgnoreCase("none")) {
            bodyBuilder.addFormDataPart("image2","none");
        } else if (proofphotpath.equalsIgnoreCase("removed")) {
            bodyBuilder.addFormDataPart("image2","removed");
        } else {
            bodyBuilder.addFormDataPart("image2","filled");
            File sourceFile = new File(proofphotpath);
            bodyBuilder.addFormDataPart("photo2", sourceFile.getName(), RequestBody.create(MediaType.parse("image/jpg"), sourceFile));
        }

        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.markettingstaffedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType,Temp.mrktid));
        bodyBuilder.addFormDataPart("name", null,RequestBody.create(contentType, name.getText().toString()));
        bodyBuilder.addFormDataPart("place", null,RequestBody.create(contentType, place.getText().toString()));
        bodyBuilder.addFormDataPart("mobile1", null,RequestBody.create(contentType, mobile1.getText().toString()));
        bodyBuilder.addFormDataPart("mobile2", null,RequestBody.create(contentType,mobile2.getText().toString()));
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
                .url(Temp.weblink+"admin_addmarketingstaff.php")
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
                                File file2 = new File(proofphotpath);
                                if (file1.exists()) {
                                    file1.delete();
                                }
                                if (file2.exists()) {
                                    file2.delete();
                                }
                                Toasty.info(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (result.contains("exist")) {
                                Toasty.info(getApplicationContext(), "Sorry ! Mobile number is Already Exist", Toast.LENGTH_SHORT).show();
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
