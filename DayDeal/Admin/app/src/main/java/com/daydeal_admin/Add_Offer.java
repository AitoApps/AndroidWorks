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

public class Add_Offer extends AppCompatActivity {
    ImageView back;
    EditText cashback;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    EditText deliverycharge;
    public Dialog dialog;
    EditText discription;
    Typeface face;
    Typeface face1;
    public Bitmap img;
    EditText itemkeywords;
    EditText itemname;
    EditText offerprice;
    public float ogheight;
    EditText orginalprice;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    ProgressBar prb1;
    public Button stop;
    TextView text;
    TextView txtcashback;
    TextView txtdeliverycharge;
    TextView txtdiscription;
    TextView txtitemkeywords;
    TextView txtitemname;
    TextView txtoffferprice;
    TextView txtorginalprice;
    TextView txtphoto1;
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
        setContentView((int) R.layout.activity_add__offer);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        itemkeywords = (EditText) findViewById(R.id.itemkeywords);
        cashback = (EditText) findViewById(R.id.cashback);
        txtcashback = (TextView) findViewById(R.id.txtcashback);
        txtitemkeywords = (TextView) findViewById(R.id.txtitemkeywords);
        itemname = (EditText) findViewById(R.id.itemname);
        offerprice = (EditText) findViewById(R.id.offerprice);
        orginalprice = (EditText) findViewById(R.id.orginalprice);
        discription = (EditText) findViewById(R.id.discription);
        photo1 = (ImageView) findViewById(R.id.photo1);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        deliverycharge = (EditText) findViewById(R.id.deliverycharge);
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        text.setTypeface(face);
        update.setTypeface(face);
        txtitemname = (TextView) findViewById(R.id.txtitemname);
        txtorginalprice = (TextView) findViewById(R.id.txtorginalprice);
        txtoffferprice = (TextView) findViewById(R.id.txtoffferprice);
        txtdiscription = (TextView) findViewById(R.id.txtdiscription);
        txtdeliverycharge = (TextView) findViewById(R.id.txtdeliverycharge);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);
        txtitemname.setTypeface(face1);
        txtorginalprice.setTypeface(face1);
        txtoffferprice.setTypeface(face1);
        txtdiscription.setTypeface(face1);
        txtdeliverycharge.setTypeface(face1);
        txtphoto1.setTypeface(face1);
        txtitemkeywords.setTypeface(face1);
        itemkeywords.setTypeface(face);
        itemname.setTypeface(face);
        offerprice.setTypeface(face);
        orginalprice.setTypeface(face);
        discription.setTypeface(face);
        deliverycharge.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        photo1.post(new Runnable() {
            public void run() {
                ogheight = Float.parseFloat(db.getscreenwidth()) / 4.0f;
                ogheight *= 3.0f;
                photo1.getLayoutParams().height = Math.round(ogheight);
            }
        });
        photo1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                try {
                    if (!hasPermissions(Add_Offer.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Offer.this, PERMISSIONS, PERMISSION_ALL);
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
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (itemname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter item name", Toast.LENGTH_SHORT).show();
                        itemname.requestFocus();
                    } else if (offerprice.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter offerprice", Toast.LENGTH_SHORT).show();
                        offerprice.requestFocus();
                    } else if (orginalprice.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter orginalprice", Toast.LENGTH_SHORT).show();
                        orginalprice.requestFocus();
                    } else if (cashback.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter cashback", Toast.LENGTH_SHORT).show();
                        cashback.requestFocus();
                    } else if (Integer.parseInt(cashback.getText().toString()) < 0) {
                        Toasty.info(getApplicationContext(), "Cashback must be greater than or equal 0", Toast.LENGTH_SHORT).show();
                        cashback.requestFocus();
                    } else if (deliverycharge.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), "Please enter deliverycharge", Toast.LENGTH_SHORT).show();
                        deliverycharge.requestFocus();
                    } else if (Integer.parseInt(deliverycharge.getText().toString()) < 0) {
                        Toasty.info(getApplicationContext(), "deliverycharge must be greater than or equal 0", Toast.LENGTH_SHORT).show();
                        deliverycharge.requestFocus();
                    } else if (cd.isConnectingToInternet()) {
                        uploadingprogress();
                    } else {
                        Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.isproductedit == 1) {
            itemname.setText(Temp.edit_itemname);
            offerprice.setText(Temp.edit_offerprice);
            orginalprice.setText(Temp.edit_orginalprice);
            discription.setText(Temp.edit_dscription);
            itemkeywords.setText(Temp.edit_itemkeywords);
            cashback.setText(Temp.edit_cashback);
            deliverycharge.setText(Temp.edit_deliverycharge);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.edit_imgsig1)));
                    apply.load(Temp.weblink+"adminproduct/"+Temp.edit_productid+".jpg").into(new SimpleTarget<Bitmap>() {
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
        Builder builder = new Builder(this);
        builder.setTitle("Product Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_Offer.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_Offer.this, 1);
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
                File f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/productpic1.jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                }
                try {
                    Uri uri = Uri.fromFile(f);
                    Options options = new Options();
                    options.setFreeStyleCropEnabled(true);
                    options.setToolbarColor(Color.parseColor("#30be76"));
                    options.setStatusBarColor(Color.parseColor("#30be76"));
                    options.setCompressionFormat(CompressFormat.JPEG);
                    options.setCompressionQuality(80);
                    options.setToolbarTitle("Crop Image");
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4.0f, 3.0f).start(Add_Offer.this);
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
        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.isproductedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType,Temp.edit_productid));
        bodyBuilder.addFormDataPart("itemname", null,RequestBody.create(contentType, itemname.getText().toString()));
        bodyBuilder.addFormDataPart("offerprice", null,RequestBody.create(contentType, offerprice.getText().toString()));
        bodyBuilder.addFormDataPart("orginalprice", null,RequestBody.create(contentType,orginalprice.getText().toString()));
        bodyBuilder.addFormDataPart("discription", null,RequestBody.create(contentType,discription.getText().toString()));
        bodyBuilder.addFormDataPart("itemkeyword", null,RequestBody.create(contentType,itemkeywords.getText().toString()));
        bodyBuilder.addFormDataPart("cashback", null,RequestBody.create(contentType,cashback.getText().toString()));
        bodyBuilder.addFormDataPart("deliverycharge", null,RequestBody.create(contentType,deliverycharge.getText().toString()));

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
                .url(Temp.weblink+"addproductbyadmin.php")
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
                                finish();
                                return;
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
