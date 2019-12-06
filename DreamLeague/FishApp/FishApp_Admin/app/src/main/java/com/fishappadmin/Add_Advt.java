package com.fishappadmin;

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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class Add_Advt extends AppCompatActivity {
    Spinner adtype;
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public Dialog dialog;
    Typeface face;
    public Bitmap img;
    List<String> lst_advtname = new ArrayList();
    public float ogheight;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    ProgressBar prb1;
    EditText referance;
    public Button stop;
    TextView text;
    public String txt_adtype = "";
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
        setContentView((int) R.layout.activity_add__advt);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        adtype = (Spinner) findViewById(R.id.adtype);
        update = (Button) findViewById(R.id.update);
        cd = new ConnectionDetecter(this);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        photo1 = (ImageView) findViewById(R.id.photo1);
        pd = new ProgressDialog(this);
        referance = (EditText) findViewById(R.id.referance);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        text.setTypeface(face);
        referance.setTypeface(face);
        update.setTypeface(face);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lst_advtname.add("Select Ad Type");
        lst_advtname.add("Fish");
        lst_advtname.add("Call");
        lst_advtname.add("Web");
        lst_advtname.add("Nothig");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, 17367048, lst_advtname) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }
        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adtype.setAdapter(dataAdapter1);
        adtype.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                txt_adtype = arg2+"";
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        photo1.post(new Runnable() {
            public void run() {
                ogheight = Float.parseFloat(db.getscreenwidth()) / 4.0f;
                ogheight *= 2.0f;
                photo1.getLayoutParams().height = Math.round(ogheight);
            }
        });
        photo1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                try {
                    if (!hasPermissions(Add_Advt.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Advt.this, PERMISSIONS, PERMISSION_ALL);
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
        if (Temp.adedit == 1) {
            referance.setText(Temp.ad_refrence);
            adtype.setSelection(Integer.parseInt(Temp.ad_types));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.ad_imgsig)));
                    apply.load(Temp.weblink+"advt/"+Temp.ad_sn+".png").into(new SimpleTarget<Bitmap>() {
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
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (adtype.getSelectedItemPosition() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please select adtype", Toast.LENGTH_SHORT).show();
                } else if (adtype.getSelectedItemPosition() == 5 || !referance.getText().toString().equalsIgnoreCase("")) {
                    uploadingprogress();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter refrence", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(this);
        builder.setTitle("Client Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_Advt.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_Advt.this, 1);
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
                    options.setFreeStyleCropEnabled(true);
                    options.setToolbarColor(Color.parseColor("#205c14"));
                    options.setStatusBarColor(Color.parseColor("#2E7D32"));
                    options.setCompressionFormat(CompressFormat.JPEG);
                    options.setCompressionQuality(80);
                    options.setToolbarTitle("Crop Image");
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4.0f, 2.0f).start(Add_Advt.this);
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
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
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
            bodyBuilder.addFormDataPart("photo1", sourceFile.getName(), RequestBody.create(MediaType.parse("image/png"), sourceFile));
        }
        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.adedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.ad_sn));
        bodyBuilder.addFormDataPart("areaid", null,RequestBody.create(contentType, Temp.areaid));
        bodyBuilder.addFormDataPart("adtype", null,RequestBody.create(contentType, adtype.getSelectedItemPosition()+""));
        bodyBuilder.addFormDataPart("refernce", null,RequestBody.create(contentType, referance.getText().toString()));
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
                .url(Temp.weblink+"addnewadvtadmin.php")
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
                            pd.dismiss();
                            if (result.contains("ok")) {
                                File file1 = new File(photopath1);
                                if (file1.exists()) {
                                    file1.delete();
                                }
                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                            if (result.contains("exist")) {
                                Toast.makeText(getApplicationContext(), "Sorry ! This catogery already exist", Toast.LENGTH_SHORT).show();
                            } else if (result.contains("userexi")) {
                                Toast.makeText(getApplicationContext(), "Sorry ! This catogery already exist", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
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
