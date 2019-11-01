package com.sanji_admin;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_admin.AndroidMultiPartEntity.ProgressListener;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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

public class Add_ProductCat extends AppCompatActivity {
    ImageView back;
    EditText catogeryname;
    ConnectionDetecter cd;
    public Dialog dialog;
    Typeface face;
    Typeface face1;
    HttpPost httppost = null;
    public String imgPath = "none";
    public ProgressBar pb1;
    ProgressDialog pd;
    public TextView persentage;
    ImageView photo1;
    public Button stop;
    TextView text;
    public long totalSize = 0;
    TextView txtphoto1;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    Button update;

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        private UploadFileToServer() {
        }
        public void onPreExecute() {
            Add_ProductCat.pb1.setVisibility(View.VISIBLE);
            Add_ProductCat.dialog.setCancelable(false);
            Add_ProductCat.persentage.setVisibility(View.VISIBLE);
            Add_ProductCat.update.setEnabled(false);
            super.onPreExecute();
        }
        public void onProgressUpdate(Integer... progress) {
            try {
                TextView textView = Add_ProductCat.persentage;
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(progress[0]));
                sb.append("%");
                textView.setText(sb.toString());
            } catch (Exception e) {
            }
        }
        public String doInBackground(Void... params) {
            return uploadFile();
        }

        private String uploadFile() {
            HttpClient httpclient = new DefaultHttpClient();
            StringBuilder sb = new StringBuilder();
            sb.append(Temp.weblink);
            sb.append("addproductcatogery.php");
            HttpPost httppost = new HttpPost(sb.toString());
            try {
                ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Charset.forName("utf-8"));
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
                    public void transferred(long num) {
                        UploadFileToServer.publishProgress(new Integer[]{Integer.valueOf((int) ((((float) num) / ((float) Add_ProductCat.totalSize)) * 100.0f))});
                    }
                });
                if (Add_ProductCat.imgPath.equalsIgnoreCase("none")) {
                    entity.addPart("photo", new StringBody("none"));
                } else if (Add_ProductCat.imgPath.equalsIgnoreCase("removed")) {
                    entity.addPart("photo", new StringBody("removed"));
                } else if (Add_ProductCat.imgPath.equalsIgnoreCase("edit")) {
                    entity.addPart("photo", new StringBody("edit"));
                } else {
                    entity.addPart("photo1", new FileBody(new File(Add_ProductCat.imgPath)));
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.productcatedit);
                sb2.append("");
                entity.addPart("isedit", new StringBody(sb2.toString(), contentType));
                entity.addPart("id", new StringBody(Temp.productcateditsn, contentType));
                entity.addPart("catogeryname", new StringBody(Add_ProductCat.catogeryname.getText().toString(), contentType));
                Add_ProductCat.totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(r_entity);
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error occurred! Http Status Code: ");
                sb3.append(statusCode);
                return sb3.toString();
            } catch (ClientProtocolException e) {
                return e.toString();
            } catch (IOException e2) {
                return e2.toString();
            }
        }
        public void onPostExecute(String result) {
            Add_ProductCat.pb1.setVisibility(View.GONE);
            Add_ProductCat.persentage.setVisibility(View.GONE);
            Add_ProductCat.dialog.dismiss();
            if (result.contains("ok")) {
                try {
                    Toasty.info(Add_ProductCat.getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Add_ProductCat.finish();
                } catch (Exception e) {
                }
            } else {
                Toasty.info(Add_ProductCat.getApplicationContext(), Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
            Add_ProductCat.update.setEnabled(true);
            super.onPostExecute(result);
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_add__product_cat);
        back = (ImageView) findViewById(R.id.back);
        text = (TextView) findViewById(R.id.text);
        txtphoto1 = (TextView) findViewById(R.id.txtphoto1);
        catogeryname = (EditText) findViewById(R.id.catogeryname);
        photo1 = (ImageView) findViewById(R.id.photo1);
        update = (Button) findViewById(R.id.update);
        cd = new ConnectionDetecter(this);
        pd = new ProgressDialog(this);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
        text.setTypeface(face);
        txtphoto1.setTypeface(face1);
        update.setTypeface(face);
        catogeryname.setTypeface(face);
        imgPath = "none";
        photo1.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    if (ContextCompat.checkSelfPermission(Add_ProductCat.this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        ActivityCompat.requestPermissions(Add_ProductCat.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    } else if (ContextCompat.checkSelfPermission(Add_ProductCat.this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                        ActivityCompat.requestPermissions(Add_ProductCat.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
                    } else {
                        Add_ProductCat.selectImage();
                    }
                } catch (Exception e) {
                }
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!Add_ProductCat.cd.isConnectingToInternet()) {
                    Toasty.info(Add_ProductCat.getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                } else if (Add_ProductCat.catogeryname.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Add_ProductCat.getApplicationContext(), "Please enter catogery name", Toast.LENGTH_SHORT).show();
                } else {
                    Add_ProductCat.updatetoserver();
                }
            }
        });
        if (Temp.productcatedit == 1) {
            catogeryname.setText(Temp.edit_catogeryname);
            catogeryname.setSelection(catogeryname.getText().length());
            imgPath = "edit";
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    RequestBuilder apply = Glide.with(Add_ProductCat.getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(Temp.edit_imgsig)));
                    StringBuilder sb = new StringBuilder();
                    sb.append(Temp.weblink);
                    sb.append("productcatogery/");
                    sb.append(Temp.productcateditsn);
                    sb.append(".png");
                    apply.load(sb.toString()).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            Add_ProductCat.photo1.setImageBitmap(bitmap);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            Add_ProductCat.photo1.setImageResource(R.drawable.noproductphoto);
                        }
                    });
                }
            });
        }
    }
    public void selectImage() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(this);
        builder.setTitle("Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera((Activity) Add_ProductCat.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery((Activity) Add_ProductCat.this, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Remove Photo")) {
                    Add_ProductCat.imgPath = "removed";
                    Add_ProductCat.photo1.setImageResource(R.drawable.noproductphoto);
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
                try {
                    Add_ProductCat.imgPath = imageFile.getPath();
                    Add_ProductCat.photo1.setImageBitmap(BitmapFactory.decodeFile(Add_ProductCat.imgPath));
                } catch (Exception e) {
                }
            }
        });
        if (requestCode == 69) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append("/");
            sb.append(Temp.foldername);
            sb.append("/productcat.jpg");
            imgPath = sb.toString();
            photo1.setImageBitmap(BitmapFactory.decodeFile(imgPath));
        }
    }

    public void updatetoserver() {
        try {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogupload);
            pb1 = (ProgressBar) dialog.findViewById(R.id.pb1);
            persentage = (TextView) dialog.findViewById(R.id.persentage);
            stop = (Button) dialog.findViewById(R.id.stop);
            new UploadFileToServer().execute(new Void[0]);
            stop.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        Add_ProductCat.httppost.abort();
                    } catch (Exception e) {
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
        }
    }
}
