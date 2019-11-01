package com.appsbag_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import adapter.Customview_ListAdapter;
import data.CustomViewlist_FeedItem;
import es.dmoral.toasty.Toasty;
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

public class Add_Apps extends AppCompatActivity {

    ImageView back;
    TextView text;
    ImageView addproduct;
    ListView list;
    ImageView heart;
    ConnectionDetecter cd;
    final DatabaseHandler db=new DatabaseHandler(this);
    private List<CustomViewlist_FeedItem> feedItems;
    private Customview_ListAdapter listAdapter;
    Typeface face;
    Button update;
    Call call;
    boolean requestgoing=true;
    Dialog dialog;
    public ProgressBar pb1;
    public TextView persentage;
    public Button stop;

    public static String txt_english="",txt_malayalam="",txt_hindi="",txt_tamil="",txt_telugu="",txt_photopath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__apps);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        back=findViewById(R.id.back);
        text=findViewById(R.id.text);
        addproduct=findViewById(R.id.addproduct);
        list=findViewById(R.id.list);
        update=findViewById(R.id.update);
        heart=findViewById(R.id.heart);
        cd=new ConnectionDetecter(this);
        feedItems = new ArrayList();
        listAdapter = new Customview_ListAdapter(this, feedItems);
        list.setAdapter(listAdapter);

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Temp.appviewedit=0;
                startActivity(new Intent(getApplicationContext(),Add_View.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cd.isConnectingToInternet())
                {
                    ArrayList<String> id1 = db.getappview1();
                    String[] k = (String[]) id1.toArray(new String[id1.size()]);

                    for(int i=0;i<k.length;i++)
                    {
                        if(txt_english.equalsIgnoreCase(""))
                        {
                            txt_english=k[i];
                        }
                        else
                        {
                            txt_english=txt_english+":%"+k[i];
                        }
                        i=i+1;
                        if(txt_malayalam.equalsIgnoreCase(""))
                        {
                            txt_malayalam=k[i];
                        }
                        else
                        {
                            txt_malayalam=txt_malayalam+":%"+k[i];
                        }
                        i=i+1;
                        if(txt_hindi.equalsIgnoreCase(""))
                        {
                            txt_hindi=k[i];
                        }
                        else
                        {
                            txt_hindi=txt_hindi+":%"+k[i];
                        }

                        i=i+1;
                        if(txt_tamil.equalsIgnoreCase(""))
                        {
                            txt_tamil=k[i];
                        }
                        else
                        {
                            txt_tamil=txt_tamil+":%"+k[i];
                        }

                        i=i+1;
                        if(txt_telugu.equalsIgnoreCase(""))
                        {
                            txt_telugu=k[i];
                        }
                        else
                        {
                            txt_telugu=txt_telugu+":%"+k[i];
                        }

                        i=i+1;
                        if(txt_photopath.equalsIgnoreCase(""))
                        {
                            txt_photopath=k[i];
                        }
                        else
                        {
                            txt_photopath=txt_photopath+":%"+k[i];
                        }
                    }

                    uploadingprogress();
                }
                else
                {
                    Toasty.info(getApplicationContext(),Temp.nointernet, Toast.LENGTH_SHORT).show();
                }




            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        try
        {
               loaddata();
        }
        catch (Exception a)
        {

        }
    }

    public void loaddata() {
        feedItems.clear();
        ArrayList<String> id1 = db.getappview();
        String[] k = (String[]) id1.toArray(new String[id1.size()]);
        for(int i=0;i<k.length;i++)
        {
            CustomViewlist_FeedItem item = new CustomViewlist_FeedItem();
            item.setPkey(k[i]);
            i++;
            item.setEnglish(k[i]);
            i++;
            item.setMalayalam(k[i]);
            i++;
            item.setHindi(k[i]);
            i++;
            item.setTamil(k[i]);
            i++;
            item.setTelugu(k[i]);
            i++;
            item.setPhotopath(k[i]);
            feedItems.add(item);
        }

        list.setVisibility(View.VISIBLE);
        listAdapter.notifyDataSetChanged();
    }

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
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
            stop.setOnClickListener(new View.OnClickListener() {
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

        if(!db.getphotopath().equalsIgnoreCase("NA") && !db.getphotopath().equalsIgnoreCase(""))
        {
            File sourceFile = new File(db.getphotopath());
            bodyBuilder.addFormDataPart("applogo",sourceFile.getName(),RequestBody.create(MediaType.parse("image/png"), sourceFile));
        }

        if(txt_photopath.contains(":%"))
        {
            String[] p=txt_photopath.split(":%");
            for(int j=0;j<p.length;j++)
            {
                if(p[j].equalsIgnoreCase("NA") || p[j].equalsIgnoreCase(""))
                {
                    bodyBuilder.addFormDataPart("photo"+(j+1)+"exist", null,RequestBody.create(contentType,"NA"));
                }
                else
                {
                    File sourceFile = new File(p[j]);
                    bodyBuilder.addFormDataPart("photo"+(j+1)+"exist", null,RequestBody.create(contentType,"exit"));
                    bodyBuilder.addFormDataPart("photo"+(j+1),sourceFile.getName(),RequestBody.create(MediaType.parse("image/jpg"), sourceFile));
                }

            }

        }
        else if(!txt_photopath.equalsIgnoreCase(""))
        {
            if(txt_photopath.equalsIgnoreCase("NA"))
            {
                bodyBuilder.addFormDataPart("photo1exist", null,RequestBody.create(contentType,"NA"));
            }
            else
            {
                File sourceFile = new File(txt_photopath);
                bodyBuilder.addFormDataPart("photo1exist", null,RequestBody.create(contentType,"exit"));
                bodyBuilder.addFormDataPart("photo1",sourceFile.getName(),RequestBody.create(MediaType.parse("image/jpg"), sourceFile));
            }

        }

        bodyBuilder.addFormDataPart("isedit", null,RequestBody.create(contentType, Temp.appedit+""));
        bodyBuilder.addFormDataPart("editsn", null,RequestBody.create(contentType, Temp.appeditsn));
        bodyBuilder.addFormDataPart("types", null,RequestBody.create(contentType,Temp.appcatogery));

        bodyBuilder.addFormDataPart("appname", null,RequestBody.create(contentType,db.getappname()));
        bodyBuilder.addFormDataPart("appurl", null,RequestBody.create(contentType, db.getappurl()));
        bodyBuilder.addFormDataPart("opentitle", null,RequestBody.create(contentType, db.getopentitle()));
        bodyBuilder.addFormDataPart("opendisc", null,RequestBody.create(contentType, db.getopendisc()));
        bodyBuilder.addFormDataPart("disctitle", null,RequestBody.create(contentType, db.getdisctitle()));
        bodyBuilder.addFormDataPart("discfooter", null,RequestBody.create(contentType, db.getdiscfooter()));

        bodyBuilder.addFormDataPart("english", null,RequestBody.create(contentType,txt_english));
        bodyBuilder.addFormDataPart("malayalam", null,RequestBody.create(contentType, txt_malayalam));
        bodyBuilder.addFormDataPart("hindi", null,RequestBody.create(contentType, txt_hindi));
        bodyBuilder.addFormDataPart("tamil", null,RequestBody.create(contentType, txt_tamil));
        bodyBuilder.addFormDataPart("telugu", null,RequestBody.create(contentType, txt_telugu));
        bodyBuilder.addFormDataPart("photopath", null,RequestBody.create(contentType, txt_photopath));


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
                .url(Temp.weblink+"addappsbyadmin.php")
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
//                                File file1 = new File(photopath1);
//                                if (file1.exists()) {
//                                    file1.delete();
//                                }
                                Toasty.info(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (result.contains("exit")) {
                                Toasty.info(getApplicationContext(), "Sorry ! This Stage is exist", Toast.LENGTH_SHORT).show();
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
