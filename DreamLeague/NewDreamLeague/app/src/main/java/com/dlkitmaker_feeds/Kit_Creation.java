package com.dlkitmaker_feeds;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Kit_Creation extends AppCompatActivity {
    ImageView image,save;
    ProgressDialog pd;
    final DB db=new DB(this);
    RelativeLayout lytimage;
    Internet_Connectivity cd;
    Typeface face;
    ImageView filter_add, text_add,addbrush,addimage;

    ImageView moveback;
    TextView text;

    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvty_createkit);
        try {
            save=findViewById(R.id.save);
            filter_add =findViewById(R.id.addfilter);
            text_add =findViewById(R.id.addtext);
            lytimage=findViewById(R.id.lytimage);
            moveback =findViewById(R.id.back);
            text=findViewById(R.id.text);
            pd=new ProgressDialog(this);
            addbrush=findViewById(R.id.addbrush);
            addimage=findViewById(R.id.addimage);
            image=findViewById(R.id.image);
            cd=new Internet_Connectivity(this);
            face= Typeface.createFromAsset(getAssets(), "fonts/heading.otf");
            text.setTypeface(face);

            adView1=findViewById(R.id.adView1);
            intestrial = new InterstitialAd(Kit_Creation.this);
            intestrial.setAdUnitId("ca-app-pub-5517777745693327/8411597433");
            adreq = new AdRequest.Builder().build();
            adreq1 = new AdRequest.Builder().build();



            try
            {
                adView1.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        try
                        {
                            if(count<=20)
                            {
                                adView1.loadAd(adreq1);
                                count++;
                            }


                        }
                        catch (Exception a)
                        {

                        }

                    }
                });

                intestrial.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {

                        if(intcount<=20) {
                            intestrial.loadAd(adreq);
                            intcount++;
                        }

                    }
                });

            }
            catch (Exception a)
            {

            }
            filter_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getApplicationContext(), FilterEdit.class);
                    startActivity(i);


                }
            });
            moveback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onBackPressed();
                }
            });
            text_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i=new Intent(getApplicationContext(), Edit_Text.class);
                    startActivity(i);
                }
            });

            addimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getApplicationContext(), Image_Add.class);
                    startActivity(i);
                }
            });

            addbrush.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getApplicationContext(), BrushEdit.class);
                    startActivity(i);
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try
                    {
                        image.buildDrawingCache();
                        Bitmap bm = image.getDrawingCache();
                        try {

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String timeStamp = dateFormat.format(new Date());

                            File file = new File(Environment.getExternalStorageDirectory()+"/"+ Temp.foldername+"/", timeStamp+".png");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            Bitmap resized = Bitmap.createScaledBitmap(bm, 512, 512, true);
                            resized.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();

                            upload_alert("Are you sure want to upload this kit ?",file,"DLKITS","DLKITS","DLKITS","DLKITS","DLKITS");


                        } catch (Exception e) {


                        } finally {

                        }


                    }
                    catch (Exception a)
                    {

                    }
                }
            });
        }
        catch (Exception a)
        {

        }

    }
    public void upload_alert(String message, final File image, final String imageName, final String name, final String title, final String up_team, final String up_kittype) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(true)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {

                        image_uploadin(image,imageName,name,title,up_team,up_kittype);
                        dialog.dismiss();
                    }


                });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {


                dialog.dismiss();
            }


        });
        AlertDialog alert = builder.create();
        alert.show();
        try
        {
            TextView textView = alert.findViewById(android.R.id.message);
            textView.setTypeface(face);
        }
        catch(Exception a)
        {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try
        {

            count=0;
            intcount=0;
            try
            {
                adView1.loadAd(adreq1);
                intestrial.loadAd(adreq);
            }
            catch (Exception a)
            {

            }

            try
            {
                if(!db.get_kittheme().equalsIgnoreCase(""))
                {
                    File file = new File(Environment.getExternalStorageDirectory()+"/"+Temp.foldername+"/", "mykit.png");
                    if(file.exists())
                    {
                        float ogwidth=(Float.valueOf(db.getscreenwidth()));
                        image.getLayoutParams().height=Math.round(ogwidth);

                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        image.setImageBitmap(bitmap);
                    }
                    else
                    {
                        Toasty.info(getApplicationContext(),"Sorry ! Please try later",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else
                {
                    Toasty.info(getApplicationContext(),"Sorry ! Please try later",Toast.LENGTH_SHORT).show();
                    finish();
                }



            }
            catch (Exception a)
            {

                finish();
            }




        }
        catch (Exception a)
        {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(intestrial.isLoaded())
        {
            intestrial.show();
        }
    }

    public void image_uploadin(File image, String imageName, String name, String title, final String up_team, final String up_kittype)
    {
        try {
            pd.setMessage("Uploading...");
            pd.setCancelable(false);
            pd.show();
            String[] clientids={"7e3d274f938081c","8fb4756d0008e62","be7a1d9406d2e32","dd350dd49873dc4","07e146dd96e235e","cd3c078944500af"};
            String randomStr = clientids[new Random().nextInt(clientids.length)];
            String IMGUR_CLIENT_ID=randomStr;
            OkHttpClient client = new OkHttpClient();
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image", imageName, RequestBody.create(MEDIA_TYPE_PNG, image))
                    .addFormDataPart("name",name)
                    .addFormDataPart("title",title)
                    .build();

            Request request = new Request.Builder().header("Authorization", "Client-ID " + IMGUR_CLIENT_ID).url("https://api.imgur.com/3/upload")
                    .post(requestBody).build();

            client.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(final Call call, IOException e) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    Toasty.info(getApplicationContext(),"Unable to upload",Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            final String res = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    try
                                    {
                                        JSONObject mainObject = new JSONObject(res);
                                        JSONObject uniObject = mainObject.getJSONObject("data");
                                        if(mainObject.getString("success").equalsIgnoreCase("true"))
                                        {
                                            final String  http = uniObject.getString("link");
                                            db.add_mykits(http);
                                            Intent i=new Intent(getApplicationContext(), My_Kits.class);
                                            startActivity(i);
                                        }
                                        else
                                        {
                                            Toasty.info(getApplicationContext(),"Unable to Upload ! Please try later",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    catch (Exception a)
                                    {
                                        Toasty.info(getApplicationContext(),"Sorry ! Please try later",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });

        }
        catch (Exception a)
        {
            Toasty.info(getApplicationContext(),"Sorry ! Please try later",Toast.LENGTH_SHORT).show();

        }


    }




}