package com.sanji_admin;

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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_admin.AndroidMultiPartEntity.ProgressListener;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource;

public class Add_Product extends AppCompatActivity {
    final String[] PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    final int PERMISSION_ALL = 1;
    ImageView back;
    Spinner catogery;
    ConnectionDetecter cd;
    final DatabaseHandler db = new DatabaseHandler(this);
    public Dialog dialog;
    EditText discription;
    Typeface face;
    Typeface face1;
    HttpClient httpclient;
    HttpPost httppost = null;
    public Bitmap img;
    EditText itemkeywords;
    EditText itemname;
    List<String> lst_catid = new ArrayList();
    List<String> lst_catname = new ArrayList();
    List<String> lst_unittype = new ArrayList();
    EditText minimum;
    EditText offerprice;
    public float ogheight;
    EditText orginalprice;
    public ProgressBar pb1;
    ProgressBar pbr2;
    ProgressBar pbr3;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    ImageView photo2;
    ImageView photo3;
    public String photopath1 = "none";
    public String photopath2 = "none";
    public String photopath3 = "none";
    ProgressBar prb1;
    public int productpic = 0;
    public Button stop;
    TextView text;
    long totalSize = 0;
    public String txt_catid = "";
    public String txt_unit;
    TextView txtcatogery;
    TextView txtdiscription;
    TextView txtitemkeywords;
    TextView txtitemname;
    TextView txtminimum;
    TextView txtoffferprice;
    TextView txtorginalprice;
    TextView txtphoto1;
    TextView txtphoto2;
    TextView txtphoto3;
    TextView txtunit;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Spinner unit;
    Button update;

    public class UploadFileToServer extends AsyncTask<String, Integer, String> {
        public UploadFileToServer() {
        }
        public void onPreExecute() {
            Add_Product.pb1.setVisibility(View.VISIBLE);
            Add_Product.dialog.setCancelable(false);
            Add_Product.persentage.setVisibility(View.VISIBLE);
            Add_Product.update.setEnabled(false);
            super.onPreExecute();
        }
        public void onProgressUpdate(Integer... progress) {
            try {
                TextView textView = Add_Product.persentage;
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(progress[0]));
                sb.append("%");
                textView.setText(sb.toString());
            } catch (Exception e) {
            }
        }
        public String doInBackground(String... params) {
            return uploadFile();
        }

        private String uploadFile() {
            Add_Product.httpclient = new DefaultHttpClient();
            Add_Product add_Product = Add_Product.this;
            StringBuilder sb = new StringBuilder();
            sb.append(Temp.weblink);
            sb.append("addproductbyadmin.php");
            add_Product.httppost = new HttpPost(sb.toString());
            try {
                ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Charset.forName("utf-8"));
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
                    public void transferred(long num) {
                        UploadFileToServer.publishProgress(new Integer[]{Integer.valueOf((int) ((((float) num) / ((float) Add_Product.totalSize)) * 100.0f))});
                    }
                });
                if (Add_Product.photopath1.equalsIgnoreCase("none")) {
                    entity.addPart("image1", new StringBody("none", contentType));
                } else if (Add_Product.photopath1.equalsIgnoreCase("removed")) {
                    entity.addPart("image1", new StringBody("removed", contentType));
                } else {
                    File sourceFile = new File(Add_Product.photopath1);
                    entity.addPart("image1", new StringBody("filled", contentType));
                    entity.addPart("photo1", new FileBody(sourceFile));
                }
                if (Add_Product.photopath2.equalsIgnoreCase("none")) {
                    entity.addPart("image2", new StringBody("none", contentType));
                } else if (Add_Product.photopath2.equalsIgnoreCase("removed")) {
                    entity.addPart("image2", new StringBody("removed", contentType));
                } else {
                    File sourceFile2 = new File(Add_Product.photopath2);
                    entity.addPart("image2", new StringBody("filled", contentType));
                    entity.addPart("photo2", new FileBody(sourceFile2));
                }
                if (Add_Product.photopath3.equalsIgnoreCase("none")) {
                    entity.addPart("image3", new StringBody("none", contentType));
                } else if (Add_Product.photopath3.equalsIgnoreCase("removed")) {
                    entity.addPart("image3", new StringBody("removed", contentType));
                } else {
                    File sourceFile3 = new File(Add_Product.photopath3);
                    entity.addPart("image3", new StringBody("filled", contentType));
                    entity.addPart("photo3", new FileBody(sourceFile3));
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.isproductedit);
                sb2.append("");
                entity.addPart("isedit", new StringBody(sb2.toString(), contentType));
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Temp.edit_productid);
                sb3.append("");
                entity.addPart("editsn", new StringBody(sb3.toString(), contentType));
                entity.addPart("catid", new StringBody(Add_Product.txt_catid, contentType));
                entity.addPart("itemname", new StringBody(Add_Product.itemname.getText().toString(), contentType));
                entity.addPart("offerprice", new StringBody(Add_Product.offerprice.getText().toString(), contentType));
                entity.addPart("orginalprice", new StringBody(Add_Product.orginalprice.getText().toString(), contentType));
                entity.addPart("discription", new StringBody(Add_Product.discription.getText().toString(), contentType));
                entity.addPart("unittype", new StringBody(Add_Product.txt_unit, contentType));
                entity.addPart("minimum", new StringBody(Add_Product.minimum.getText().toString(), contentType));
                entity.addPart("itemkeyword", new StringBody(Add_Product.itemkeywords.getText().toString(), contentType));
                Add_Product.totalSize = entity.getContentLength();
                Add_Product.httppost.setEntity(entity);
                HttpResponse response = Add_Product.httpclient.execute(Add_Product.httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(r_entity);
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Error occurred! Http Status Code: ");
                sb4.append(statusCode);
                return sb4.toString();
            } catch (ClientProtocolException e) {
                return e.toString();
            } catch (IOException e2) {
                return e2.toString();
            }
        }
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Add_Product.pb1.setVisibility(View.GONE);
            Add_Product.persentage.setVisibility(View.GONE);
            Add_Product.update.setEnabled(true);
            Add_Product.dialog.dismiss();
            if (result.contains("ok")) {
                File file1 = new File(Add_Product.photopath1);
                File file2 = new File(Add_Product.photopath2);
                File file3 = new File(Add_Product.photopath3);
                if (file1.exists()) {
                    file1.delete();
                }
                if (file2.exists()) {
                    file2.delete();
                }
                if (file3.exists()) {
                    file3.delete();
                }
                Add_Product.finish();
            } else if (result.contains("notpermit")) {
                Toasty.info(Add_Product.getApplicationContext(), "ക്ഷമിക്കണം ! കാലാവധി കഴിഞ്ഞിരിക്കുന്നു.ദയവായി അഡ്മിനുമായി ബന്ധപ്പെടുക ", Toast.LENGTH_SHORT).show();
                Add_Product.finish();
            } else if (result.contains("deleted")) {
                Toasty.info(Add_Product.getApplicationContext(), "ക്ഷമിക്കണം ! താങ്കള്‍ക്ക് പ്രൊഡക്ട് ചേര്‍ക്കാന്‍ കഴിയില്ല ", Toast.LENGTH_SHORT).show();
                Add_Product.finish();
            } else if (result.contains("exceeed")) {
                String[] s = result.split(",");
                Context applicationContext = Add_Product.getApplicationContext();
                StringBuilder sb = new StringBuilder();
                sb.append("ക്ഷമിക്കണം ! താങ്കള്‍ക്ക് പരമാവധി ");
                sb.append(s[1]);
                sb.append(" ഉല്‍പ്പന്നങ്ങള്‍ മാത്രമേ ഒരേ സമയം നല്‍കുവാന്‍ കഴിയുകയൊള്ളൂ ");
                Toasty.info(applicationContext, sb.toString(), Toast.LENGTH_SHORT).show();
                Add_Product.finish();
            } else {
                Toasty.info(Add_Product.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__product);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        pd = new ProgressDialog(this);
        cd = new ConnectionDetecter(this);
        back = (ImageView) findViewById(R.id.back);
        prb1 = (ProgressBar) findViewById(R.id.pb1);
        pbr2 = (ProgressBar) findViewById(R.id.pb2);
        pbr3 = (ProgressBar) findViewById(R.id.pb3);
        itemkeywords = (EditText) findViewById(R.id.itemkeywords);
        txtminimum = (TextView) findViewById(R.id.txtminimum);
        catogery = (Spinner) findViewById(R.id.catogery);
        minimum = (EditText) findViewById(R.id.minimum);
        unit = (Spinner) findViewById(R.id.unit);
        txtunit = (TextView) findViewById(R.id.txtunit);
        txtitemkeywords = (TextView) findViewById(R.id.txtitemkeywords);
        itemname = (EditText) findViewById(R.id.itemname);
        offerprice = (EditText) findViewById(R.id.offerprice);
        orginalprice = (EditText) findViewById(R.id.orginalprice);
        discription = (EditText) findViewById(R.id.discription);
        photo1 = (ImageView) findViewById(R.id.photo1);
        photo2 = (ImageView) findViewById(R.id.photo2);
        photo3 = (ImageView) findViewById(R.id.photo3);
        update = (Button) findViewById(R.id.update);
        text = (TextView) findViewById(R.id.text);
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        text.setTypeface(face);
        update.setTypeface(face);
        txtcatogery = (TextView) findViewById(R.id.txtcatogery);
        txtitemname = (TextView) findViewById(R.id.txtitemname);
        txtorginalprice = (TextView) findViewById(R.id.txtorginalprice);
        txtoffferprice = (TextView) findViewById(R.id.txtoffferprice);
        txtdiscription = (TextView) findViewById(R.id.txtdiscription);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);
        txtphoto2 = (TextView) findViewById(R.id.txtphoto2);
        txtphoto3 = (TextView) findViewById(R.id.txtphoto3);
        txtcatogery.setTypeface(face1);
        txtitemname.setTypeface(face1);
        txtorginalprice.setTypeface(face1);
        txtoffferprice.setTypeface(face1);
        txtdiscription.setTypeface(face1);
        txtphoto1.setTypeface(face1);
        txtphoto2.setTypeface(face1);
        txtphoto3.setTypeface(face1);
        txtminimum.setTypeface(face1);
        txtunit.setTypeface(face1);
        txtitemkeywords.setTypeface(face1);
        itemkeywords.setTypeface(face);
        itemname.setTypeface(face);
        offerprice.setTypeface(face);
        orginalprice.setTypeface(face);
        discription.setTypeface(face);
        minimum.setTypeface(face);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Add_Product.onBackPressed();
            }
        });
        ArrayList<String> id1 = db.getcatid();
        String[] result = (String[]) id1.toArray(new String[id1.size()]);
        lst_catid.clear();
        lst_catname.clear();
        lst_catid.add("0");
        lst_catname.add("Select Product Catogery");
        int i = 0;
        while (i < result.length) {
            lst_catid.add(result[i]);
            int i2 = i + 1;
            lst_catname.add(result[i2]);
            i = i2 + 1;
        }
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, 17367048, lst_catname) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(Add_Product.face);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(Add_Product.face);
                return v;
            }
        };
        dataAdapter1.setDropDownViewResource(17367049);
        catogery.setAdapter(dataAdapter1);
        catogery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Add_Product add_Product = Add_Product.this;
                StringBuilder sb = new StringBuilder();
                sb.append((String) Add_Product.lst_catid.get(arg2));
                sb.append("");
                add_Product.txt_catid = sb.toString();
                DatabaseHandler databaseHandler = Add_Product.db;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(arg2);
                sb2.append("");
                databaseHandler.add_lastpcat(sb2.toString());
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (!db.lastpcat().equalsIgnoreCase("")) {
            catogery.setSelection(Integer.parseInt(db.lastpcat()));
        }
        lst_unittype.clear();
        lst_unittype.add("Select Unit Type");
        lst_unittype.add("ഗ്രാം");
        lst_unittype.add("കിലോ");
        lst_unittype.add("എണ്ണം");
        lst_unittype.add("ലിറ്റര്‍");
        lst_unittype.add("മില്ലി ലിറ്റര്‍");
        lst_unittype.add("പാക്കറ്റ്‌");
        lst_unittype.add("തൂക്കം");
        lst_unittype.add("ബോക്‌സ്‌");
        lst_unittype.add("ജോടി");
        lst_unittype.add("സെറ്റ്");
        lst_unittype.add("ബോട്ടില്‍");
        lst_unittype.add("മീറ്റര്‍");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, 17367048, lst_unittype) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(Add_Product.face);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(Add_Product.face);
                return v;
            }
        };
        dataAdapter2.setDropDownViewResource(17367049);
        unit.setAdapter(dataAdapter2);
        unit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Add_Product add_Product = Add_Product.this;
                StringBuilder sb = new StringBuilder();
                sb.append(arg2);
                sb.append("");
                add_Product.txt_unit = sb.toString();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        photo1.post(new Runnable() {
            public void run() {
                Add_Product.ogheight = Float.parseFloat(Add_Product.db.getscreenwidth()) / 4.0f;
                Add_Product.ogheight *= 3.0f;
                Add_Product.photo1.getLayoutParams().height = Math.round(Add_Product.ogheight);
                Add_Product.photo2.getLayoutParams().height = Math.round(Add_Product.ogheight);
                Add_Product.photo3.getLayoutParams().height = Math.round(Add_Product.ogheight);
            }
        });
        photo1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!Add_Product.hasPermissions(Add_Product.this, Add_Product.PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Product.this, Add_Product.PERMISSIONS, 1);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory());
                    sb.append("/");
                    sb.append(Temp.foldername);
                    File folder = new File(sb.toString());
                    if (!folder.exists()) {
                        folder.mkdir();
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Environment.getExternalStorageDirectory());
                        sb2.append("/");
                        sb2.append(Temp.foldername);
                        sb2.append("/.nomedia");
                        try {
                            new File(sb2.toString()).createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Add_Product.productpic = 1;
                    Add_Product.selectImage();
                } catch (Exception e2) {
                }
            }
        });
        photo2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!Add_Product.hasPermissions(Add_Product.this, Add_Product.PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Add_Product.this, Add_Product.PERMISSIONS, 1);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                sb.append("/");
                sb.append(Temp.foldername);
                File folder = new File(sb.toString());
                if (!folder.exists()) {
                    folder.mkdir();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Environment.getExternalStorageDirectory());
                    sb2.append("/");
                    sb2.append(Temp.foldername);
                    sb2.append("/.nomedia");
                    try {
                        new File(sb2.toString()).createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Add_Product.productpic = 2;
                Add_Product.selectImage();
            }
        });
        photo3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (!Add_Product.hasPermissions(Add_Product.this, Add_Product.PERMISSIONS)) {
                        ActivityCompat.requestPermissions(Add_Product.this, Add_Product.PERMISSIONS, 1);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory());
                    sb.append("/");
                    sb.append(Temp.foldername);
                    File folder = new File(sb.toString());
                    if (!folder.exists()) {
                        folder.mkdir();
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Environment.getExternalStorageDirectory());
                        sb2.append("/");
                        sb2.append(Temp.foldername);
                        sb2.append("/.nomedia");
                        try {
                            new File(sb2.toString()).createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Add_Product.productpic = 3;
                    Add_Product.selectImage();
                } catch (Exception e2) {
                }
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (Add_Product.catogery.getSelectedItemPosition() == 0) {
                        Toasty.info(Add_Product.getApplicationContext(), "Please select catogery name", Toast.LENGTH_SHORT).show();
                        Add_Product.catogery.requestFocus();
                    } else if (Add_Product.itemname.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(Add_Product.getApplicationContext(), "Please enter item name", Toast.LENGTH_SHORT).show();
                        Add_Product.itemname.requestFocus();
                    } else if (Add_Product.unit.getSelectedItemPosition() <= 0) {
                        Toasty.info(Add_Product.getApplicationContext(), "Please select uni type", Toast.LENGTH_SHORT).show();
                    } else if (Add_Product.minimum.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(Add_Product.getApplicationContext(), "Please enter minimum price", Toast.LENGTH_SHORT).show();
                        Add_Product.minimum.requestFocus();
                    } else if (Add_Product.offerprice.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(Add_Product.getApplicationContext(), "Please enter offerprice", Toast.LENGTH_SHORT).show();
                        Add_Product.offerprice.requestFocus();
                    } else if (Add_Product.orginalprice.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(Add_Product.getApplicationContext(), "Please enter orginalprice", Toast.LENGTH_SHORT).show();
                        Add_Product.orginalprice.requestFocus();
                    } else if (Add_Product.cd.isConnectingToInternet()) {
                        Add_Product.uploadingprogress();
                    } else {
                        Toasty.info(Add_Product.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
        if (Temp.isproductedit == 1) {
            catogery.setSelection(lst_catid.indexOf(Temp.edit_catid.trim()));
            itemname.setText(Temp.edit_itemname);
            offerprice.setText(Temp.edit_offerprice);
            orginalprice.setText(Temp.edit_orginalprice);
            discription.setText(Temp.edit_dscription);
            minimum.setText(Temp.edit_minimum);
            itemkeywords.setText(Temp.edit_itemkeywords);
            unit.setSelection(Integer.parseInt(Temp.edit_unit));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Add_Product.prb1.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(Add_Product.getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.edit_imgsig1)));
                    StringBuilder sb = new StringBuilder();
                    sb.append(Temp.weblink);
                    sb.append("productpics_admin/");
                    sb.append(Temp.edit_productid);
                    sb.append("_1.jpg");
                    apply.load(sb.toString()).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            Add_Product.prb1.setVisibility(View.GONE);
                            Add_Product.photo1.setImageBitmap(bitmap);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            Add_Product.prb1.setVisibility(View.GONE);
                            Add_Product.photo1.setImageResource(R.drawable.nophoto);
                        }
                    });
                }
            });
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Add_Product.pbr2.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(Add_Product.getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.edit_imgsig2)));
                    StringBuilder sb = new StringBuilder();
                    sb.append(Temp.weblink);
                    sb.append("productpics/");
                    sb.append(Temp.edit_productid);
                    sb.append("_2.jpg");
                    apply.load(sb.toString()).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            Add_Product.pbr2.setVisibility(View.GONE);
                            Add_Product.photo2.setImageBitmap(bitmap);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            Add_Product.pbr2.setVisibility(View.GONE);
                            Add_Product.photo2.setImageResource(R.drawable.nophoto);
                        }
                    });
                }
            });
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Add_Product.pbr3.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(Add_Product.getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.edit_imgsig3)));
                    StringBuilder sb = new StringBuilder();
                    sb.append(Temp.weblink);
                    sb.append("productpics/");
                    sb.append(Temp.edit_productid);
                    sb.append("_3.jpg");
                    apply.load(sb.toString()).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            Add_Product.pbr3.setVisibility(View.GONE);
                            Add_Product.photo3.setImageBitmap(bitmap);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            Add_Product.pbr3.setVisibility(View.GONE);
                            Add_Product.photo3.setImageResource(R.drawable.nophoto);
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
                    EasyImage.openCamera((Activity) Add_Product.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery((Activity) Add_Product.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (!options[item].equals("Remove Photo")) {
                } else {
                    if (Add_Product.productpic == 1) {
                        Add_Product.photopath1 = "removed";
                        Add_Product.photo1.setImageDrawable(Add_Product.getResources().getDrawable(R.drawable.nophoto));
                    } else if (Add_Product.productpic == 2) {
                        Add_Product.photopath2 = "removed";
                        Add_Product.photo2.setImageDrawable(Add_Product.getResources().getDrawable(R.drawable.nophoto));
                    } else if (Add_Product.productpic == 3) {
                        Add_Product.photopath3 = "removed";
                        Add_Product.photo3.setImageDrawable(Add_Product.getResources().getDrawable(R.drawable.nophoto));
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
                if (Add_Product.productpic == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory());
                    sb.append("/");
                    sb.append(Temp.foldername);
                    sb.append("/productpic1.jpg");
                    f = new File(sb.toString());
                    try {
                        f.createNewFile();
                    } catch (IOException e) {
                    }
                } else if (Add_Product.productpic == 2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Environment.getExternalStorageDirectory());
                    sb2.append("/");
                    sb2.append(Temp.foldername);
                    sb2.append("/productpic2.jpg");
                    f = new File(sb2.toString());
                    try {
                        f.createNewFile();
                    } catch (IOException e2) {
                    }
                } else if (Add_Product.productpic == 3) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(Environment.getExternalStorageDirectory());
                    sb3.append("/");
                    sb3.append(Temp.foldername);
                    sb3.append("/productpic3.jpg");
                    f = new File(sb3.toString());
                    try {
                        f.createNewFile();
                    } catch (IOException e3) {
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
                        UCrop.of(Uri.fromFile(imageFile), uri).withOptions(options).withAspectRatio(4.0f, 3.0f).start(Add_Product.this);
                    } catch (Exception e4) {
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
                } else if (productpic == 2) {
                    photopath2 = UCrop.getOutput(data2).getPath();
                    img = BitmapFactory.decodeFile(photopath2);
                    photo2.setImageBitmap(img);
                } else if (productpic == 3) {
                    photopath3 = UCrop.getOutput(data2).getPath();
                    img = BitmapFactory.decodeFile(photopath3);
                    photo3.setImageBitmap(img);
                }
            } catch (Exception a) {
                Toasty.info(getApplicationContext(), Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
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
            new UploadFileToServer().execute(new String[0]);
            stop.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        Add_Product.httppost.abort();
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
}
