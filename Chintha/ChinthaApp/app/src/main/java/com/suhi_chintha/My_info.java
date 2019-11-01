package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.yalantis.ucrop.UCrop;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
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

public class My_info extends AppCompatActivity {
    private static final int CAMERA_IMAGE_REQUEST = 1;
    public static int RESULT_LOAD_IMAGE = 2;
    public static String imgPath = "none";
    final String[] PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    final int PERMISSION_ALL = 1;
    ImageView back;
    public Bitmap bitmap;
    NetConnection cd;
    String checkstatus = "0";
    Image_Compressing cmp;
    ScrollView content;
    final DataDb dataDb = new DataDb(this);
    final DataDB1 dataDb1 = new DataDB1(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    Typeface face;
    public String id = "";
    public Bitmap img;
    TextView mobile;
    EditText name;
    ProgressBar pb;
    ProgressDialog progressPD;
    TextView text;
    Button update;
    final User_DataDB userDataDB = new User_DataDB(this);
    ImageView userphoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.userdetails_actvty);
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        } catch (Exception e) {
        }
        try {
            File f = new File(Environment.getExternalStorageDirectory()+File.separator+Static_Variable.foldername+"/bg/bg.png");
            if (f.exists()) {
                try {
                    Glide.with(getApplicationContext()).asBitmap().load(f).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            content.setBackground(new BitmapDrawable(getResources(), bitmap));
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                        }
                    });
                } catch (Exception e4) {
                }
            }
        } catch (Exception e3) {
        }
        try {
            progressPD = new ProgressDialog(this);
            userphoto = (ImageView) findViewById(R.id.userphoto);
            update = (Button) findViewById(R.id.submit);
            name = (EditText) findViewById(R.id.name);
            mobile = (TextView) findViewById(R.id.mobile);
            content = (ScrollView) findViewById(R.id.content);
            back = (ImageView) findViewById(R.id.moveback);
            pb = (ProgressBar) findViewById(R.id.porgress_B);
            text = (TextView) findViewById(R.id.text);
            cmp = new Image_Compressing();
            cd = new NetConnection(this);
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            text.setText(Static_Variable.text_editedtails1);
            text.setTypeface(face);
            text.setSelected(true);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });
            userphoto.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        if (!hasPermissions(My_info.this, PERMISSIONS)) {
                            ActivityCompat.requestPermissions(My_info.this, PERMISSIONS, PERMISSION_ALL);
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(Environment.getExternalStorageDirectory());
                        sb.append("/");
                        sb.append(Static_Variable.foldername);
                        File folder = new File(sb.toString());
                        if (!folder.exists()) {
                            folder.mkdir();
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(Environment.getExternalStorageDirectory());
                            sb2.append("/");
                            sb2.append(Static_Variable.foldername);
                            sb2.append("/.nomedia");
                            try {
                                new File(sb2.toString()).createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        ImagePic();
                    } catch (Exception e2) {
                    }
                }
            });
            ArrayList<String> id1 = userDataDB.get_user();
            String[] c = (String[]) id1.toArray(new String[id1.size()]);
            if (c.length > 0) {
                name.setText(c[1]);
                name.setSelection(name.getText().length());
                mobile.setText(c[2]);
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    pb.setVisibility(View.VISIBLE);
                    RequestBuilder apply = Glide.with(getApplicationContext()).asBitmap().apply(new RequestOptions().signature(new ObjectKey(userDataDB.get_imgsig())));
                    StringBuilder sb = new StringBuilder();
                    sb.append(Static_Variable.entypoint1);
                    sb.append("userphotosmall/");
                    sb.append(userDataDB.get_userid());
                    sb.append(".jpg");
                    apply.load(sb.toString()).into(new SimpleTarget<Bitmap>() {
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            pb. setVisibility(View.GONE);
                            userphoto.setImageBitmap(bitmap);
                        }

                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            pb. setVisibility(View.GONE);
                            userphoto.setImageResource(R.drawable.img_noimage);
                        }
                    });
                }
            });
            update.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (!cd.isConnectingToInternet()) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    } else if (name.getText().toString().equalsIgnoreCase("")) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nameofyou, Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (name.getText().toString().length() >= 35) {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.namelength, Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    } else if (cd.isConnectingToInternet()) {
                        new updateuser().execute(new String[0]);
                    } else {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception a) {
            Context applicationContext = getApplicationContext();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Log.getStackTraceString(a));
            sb2.append("");
            Toasty.info(applicationContext, (CharSequence) sb2.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public class updateuser extends AsyncTask<String, Void, String> {
        public String name1 = "";

        public void onPreExecute() {
            progressPD.setMessage("Please wait...");
            progressPD.setCancelable(false);
            progressPD.show();
            name1 = name.getText().toString();
            timerUpdate(50000, progressPD);
        }
        public String doInBackground(String... arg0) {
            try {
                id = userDataDB.get_userid();
                String link= Static_Variable.entypoint1 +"edituserdetails.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(userDataDB.get_userid()+"%:"+name1+"%:"+checkstatus, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }
        public void onPostExecute(String result) {
            if (progressPD != null || progressPD.isShowing()) {
                progressPD.dismiss();
                if (result.contains("ok")) {
                    try {
                        Toasty.info(getApplicationContext(), (CharSequence) "Updated", Toast.LENGTH_SHORT).show();
                        userDataDB.update_user(id, name.getText().toString(), checkstatus);
                    } catch (Exception e) {
                    }
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void ImagePic() {
        final CharSequence[] options = {"Remove Photo", "Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(this);
        builder.setTitle("Profile Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    EasyImage.openCamera(My_info.this, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    EasyImage.openGallery(My_info.this, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Remove Photo")) {
                    imgPath = "removed";
                    if (cd.isConnectingToInternet()) {
                        serverToUpload();
                    } else {
                        Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            }
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                File f=new File(Environment.getExternalStorageDirectory()+"/"+ Static_Variable.foldername+"/userphoto1.jpg");
                try {
                    f.createNewFile();
                } catch (IOException ex) {

                }
                try
                {
                    Uri uri = Uri.fromFile(f);
                    imgPath = imageFile.getPath();
                    UCrop.Options options = new UCrop.Options();
                    options.setToolbarColor(Color.parseColor("#205c14"));
                    options.setStatusBarColor(Color.parseColor("#2E7D32"));
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setToolbarTitle("Crop Image");
                    UCrop.of(Uri.fromFile(imageFile), uri)
                            .withAspectRatio(1, 1)
                            .withOptions(options)
                            .start(My_info.this);
                }
                catch (Exception a)
                {

                }

            }
        });
        if (requestCode == UCrop.REQUEST_CROP) {
            try
            {
                imgPath=UCrop.getOutput(data).getPath();
                img = BitmapFactory.decodeFile(imgPath);
                userphoto.setTag(imgPath);
                userphoto.setImageBitmap(img);
                if(cd.isConnectingToInternet())
                {
                    serverToUpload();
                }
                else
                {
                    Toasty.info(getApplicationContext(), Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception a)
            {

            }


        } else if (resultCode == UCrop.RESULT_ERROR) {

        }

    }

    public void serverToUpload() {
        try {
            pb.setVisibility(View.VISIBLE);
            update.setEnabled(false);
            OkHttpClient client = new OkHttpClient();
            String photo = "";
            File sourceFile = null;
            if (imgPath.equalsIgnoreCase("none")) {
                photo = "none";
            } else if (imgPath.equalsIgnoreCase("removed")) {
                photo = "removed";
            } else {
                sourceFile = new File(imgPath);
            }
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("photo", imgPath, RequestBody.create(MediaType.parse("image/jpg"), sourceFile)).addFormDataPart("photo1", photo).addFormDataPart("id", userDataDB.get_userid()).build();
            Request.Builder builder = new Request.Builder();
            client.newCall(builder.url(Static_Variable.entypoint1+"userimage1.php").post(body).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pb. setVisibility(View.GONE);
                            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                public void onResponse(Call call, final Response response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                String result = response.body().string();
                                pb. setVisibility(View.GONE);
                                if (result.contains("ok%:")) {
                                    if (imgPath.equalsIgnoreCase("removed")) {
                                        userphoto.setImageDrawable(getResources().getDrawable(R.drawable.img_noimage));
                                        File f = new File(Environment.getExternalStorageDirectory()+"/"+Static_Variable.foldername+"/userphoto1.jpg");
                                        if (f.exists()) {
                                            f.delete();
                                        }
                                    } else {
                                        userphoto.setImageBitmap(img);
                                    }
                                    Static_Variable.picchanged = 1;
                                    try {
                                        userDataDB.update_imgsig(result.split("%:")[1]);
                                        Toasty.info(getApplicationContext(), (CharSequence) "Updated", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        userDataDB.update_imgsig("00000");
                                    }
                                } else {
                                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                                }
                                update.setEnabled(true);
                            } catch (Exception e2) {
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
        }
    }

    public void timerUpdate(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
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
