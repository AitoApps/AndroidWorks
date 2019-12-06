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

public class Add_Fish extends AppCompatActivity {
    ImageView back;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public Dialog dialog;
    EditText discription;
    Typeface face;
    EditText fishname;
    public Bitmap img;
    List<String> lst_unittypes = new ArrayList();
    public float ogheight;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public String photopath1 = "none";
    ProgressBar prb1;
    EditText price;
    public int productpic = 0;
    EditText qty;
    public Button stop;
    TextView text;
    public String txt_unittypes = "";
    Spinner unit;
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
        setContentView((int) R.layout.activity_add__fish);
        face = Typeface.createFromAsset(getAssets(), "proxibold.otf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        unit = (Spinner) findViewById(R.id.unit);
        discription = (EditText) findViewById(R.id.discription);
        qty = (EditText) findViewById(R.id.qty);
        fishname = (EditText) findViewById(R.id.fishname);
        price = (EditText) findViewById(R.id.price);
        photo1 = (ImageView) findViewById(R.id.photo1);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        text.setTypeface(face);
        qty.setTypeface(face);
        fishname.setTypeface(face);
        price.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lst_unittypes.clear();
        lst_unittypes.add("");
        lst_unittypes.add("gm");
        lst_unittypes.add("kg");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, 17367048, lst_unittypes) {
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
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(dataAdapter2);
        unit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                txt_unittypes = (String) lst_unittypes.get(arg2);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
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
                    if (!hasPermissions(Add_Fish.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Fish.this, PERMISSIONS, PERMISSION_ALL);
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
                        productpic = 1;
                        selectImage();
                    }

                } catch (Exception e2) {
                }

            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                try {
                    if (fishname.getText().toString().equalsIgnoreCase(str)) {
                        Toast.makeText(getApplicationContext(), "Please enter fish name", Toast.LENGTH_SHORT).show();
                        fishname.requestFocus();
                    } else if (qty.getText().toString().equalsIgnoreCase(str)) {
                        Toast.makeText(getApplicationContext(), "Please enter qty", Toast.LENGTH_SHORT).show();
                        qty.requestFocus();
                    } else if (unit.getSelectedItemPosition() <= 0) {
                        Toast.makeText(getApplicationContext(), "Please select unit", Toast.LENGTH_SHORT).show();
                    } else if (price.getText().toString().equalsIgnoreCase(str)) {
                        Toast.makeText(getApplicationContext(), "Please enter price", Toast.LENGTH_SHORT).show();
                        price.requestFocus();
                    } else if (cd.isConnectingToInternet()) {
                        uploadingprogress();
                    } else {
                        Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.fishedit == 1) {
            fishname.setText(Temp.fishname);
            qty.setText(Temp.fishqty);
            unit.setSelection(lst_unittypes.indexOf(Temp.fishunit));
            price.setText(Temp.fishprice);
            discription.setText(Temp.fishdiscription);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.fishimgsig)));
                    apply.load(Temp.weblink+"fishpic/"+Temp.fishsn+".jpg").into(new SimpleTarget<Bitmap>() {
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
        builder.setTitle("Fish Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_Fish.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_Fish.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Remove Photo") && productpic == 1) {
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
                File f = null;
                if (productpic == 1) {
                    f = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/productpic1.jpg");
                    try {
                        f.createNewFile();
                    } catch (IOException e) {
                    }
                }
                if (f != null) {
                    try {
                        Uri uri = Uri.fromFile(f);
                        Options options = new Options();
                        options.setFreeStyleCropEnabled(true);
                        options.setToolbarColor(Color.parseColor("#205c14"));
                        options.setStatusBarColor(Color.parseColor("#2E7D32"));
                        options.setCompressionFormat(CompressFormat.JPEG);
                        options.setCompressionQuality(80);
                        options.setToolbarTitle("Crop Image");
                        UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4.0f, 3.0f).start(Add_Fish.this);
                    } catch (Exception e2) {
                    }
                }
            }
        });
        if (requestCode == 69) {
            try {
                if (productpic == 1) {
                    photopath1 = UCrop.getOutput(data2).getPath();
                    img = BitmapFactory.decodeFile(photopath1);
                    photo1.setImageBitmap(img);
                }
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
        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.fishedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.fishsn));
        bodyBuilder.addFormDataPart("areaid", null,RequestBody.create(contentType, Temp.areaid));
        bodyBuilder.addFormDataPart("catid", null,RequestBody.create(contentType, Temp.clientcatsn));
        bodyBuilder.addFormDataPart("fishname", null,RequestBody.create(contentType, fishname.getText().toString()));
        bodyBuilder.addFormDataPart("qty", null,RequestBody.create(contentType, qty.getText().toString()));
        bodyBuilder.addFormDataPart("unit", null,RequestBody.create(contentType, unit.getSelectedItem().toString()));
        bodyBuilder.addFormDataPart("price", null,RequestBody.create(contentType, price.getText().toString()));
        bodyBuilder.addFormDataPart("discription", null,RequestBody.create(contentType, discription.getText().toString()));

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
                .url(Temp.weblink+"addfish_byadmin.php")
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
                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                            else
                            {
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
