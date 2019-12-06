package com.dailybill_admin;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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

public class Add_New_Product extends AppCompatActivity {
    ImageView back;
    Spinner catogery;
    ConnectionDetecter cd;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    EditText itemkeywords;
    EditText itemname;
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    public Bitmap img;
    ImageView photo1;
    public String photopath1 = "none";
    public Button stop;
    TextView text;
    ProgressBar prb1;
    public float ogheight;
    final DatabaseHandler db = new DatabaseHandler(this);
    public String txt_unit = "";
    Spinner unit;
    Button update;
    Call call;
    boolean requestgoing=true;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};

    ArrayList<String> lst_catogeryid = new ArrayList<>();
    List<String> lst_catogery = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__new__product);
        face = Typeface.createFromAsset(getAssets(), "heading.otf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        unit = (Spinner) findViewById(R.id.unit);
        catogery = (Spinner) findViewById(R.id.catogery);
        itemname = (EditText) findViewById(R.id.itemname);
        itemkeywords = (EditText) findViewById(R.id.itemkeywords);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        photo1 = (ImageView) findViewById(R.id.photo1);
        face1 = Typeface.createFromAsset(getAssets(), "proximanormal.ttf");
        text.setTypeface(face1);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });


        lst_catogery.clear();
        lst_catogery.add("Select Catogery");
        lst_catogery.add("Grocery");
        lst_catogery.add("Vegitables");
        lst_catogery.add("Fruits");
        lst_catogery.add("Food");
        lst_catogery.add("Backery");

        lst_catogeryid.clear();
        lst_catogeryid.add("0");
        lst_catogeryid.add("1");
        lst_catogeryid.add("2");
        lst_catogeryid.add("3");
        lst_catogeryid.add("4");
        lst_catogeryid.add("5");

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst_catogery) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }
        };
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catogery.setAdapter(dataAdapter4);
        catogery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        final List<String> lst_unittypes = new ArrayList<>();
        lst_unittypes.add("");
        lst_unittypes.add("Kg"); //1
        lst_unittypes.add("Nos"); //2
        lst_unittypes.add("Ltr"); //3
        lst_unittypes.add("Portion"); //4
        lst_unittypes.add("Box"); //5
        lst_unittypes.add("Pair");//6
        lst_unittypes.add("Set");//7
        lst_unittypes.add("Bottle");//8
        lst_unittypes.add("Meter");//9
        lst_unittypes.add("Weight");//10


        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst_unittypes) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#000000"));
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#000000"));
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }
        };
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(dataAdapter3);
        unit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (itemname.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Please enter item name", Toast.LENGTH_SHORT).show();
                        itemname.requestFocus();
                    } else if (itemkeywords.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Please enter item keywords", Toast.LENGTH_SHORT).show();
                        itemkeywords.requestFocus();
                    } else if (unit.getSelectedItemPosition() <= 0) {
                        Toast.makeText(getApplicationContext(), "Please select catogery", Toast.LENGTH_SHORT).show();
                    } else if (unit.getSelectedItemPosition() <= 0) {
                        Toast.makeText(getApplicationContext(), "Please select unit", Toast.LENGTH_SHORT).show();
                    }else if (cd.isConnectingToInternet()) {
                        uploadingprogress();
                    } else {
                        Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.isproductedit == 1) {
            itemname.setText(Temp.itemname);
            itemkeywords.setText(Temp.itemkeywords);
            unit.setSelection(Integer.parseInt(Temp.unit));
            catogery.setSelection(Integer.parseInt(Temp.catogery));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.editsale_imgsig)));
                    apply.load(Temp.weblink+"productpic/"+Temp.editsale_id+".jpg").into(new SimpleTarget<Bitmap>() {
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
                    if (!hasPermissions(Add_New_Product.this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_New_Product.this, PERMISSIONS, PERMISSION_ALL);
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
            bodyBuilder.addFormDataPart("photo1", sourceFile.getName(), RequestBody.create(MediaType.parse("image/jpg"), sourceFile));
        }

        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.isproductedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.productsn));
        bodyBuilder.addFormDataPart("itemname", null,RequestBody.create(contentType, itemname.getText().toString()));
        bodyBuilder.addFormDataPart("itemkeywords", null,RequestBody.create(contentType, itemkeywords.getText().toString()));
        bodyBuilder.addFormDataPart("catogery", null,RequestBody.create(contentType, lst_catogeryid.get(catogery.getSelectedItemPosition())));
        bodyBuilder.addFormDataPart("unit", null,RequestBody.create(contentType, unit.getSelectedItemPosition()+""));

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
                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                            Toast.makeText(getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception a)
                        {

                        }
                    }
                });
            }
        });



    }

    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Shop Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(Add_New_Product.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(Add_New_Product.this, 1);
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
                    UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4.0f, 3.0f).start(Add_New_Product.this);
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
