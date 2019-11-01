package com.down_mate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import Adapter_Class.Downloads_Adapter;
import Feed_Data.Downloads_Feed;
import es.dmoral.toasty.Toasty;

public class VideoDownloads extends AppCompatActivity {
    TextView title;
    ListView listview;
    NetworkConnections cd;
    ProgressDialog pd;
    private Downloads_Adapter adapter;
    private List<Downloads_Feed> dataitems;
    ImageView back,emptydata;
    final BackEnd_DB db=new BackEnd_DB(this);
    Typeface face;
    ProgressBar pb;
    public AdView adView1;
    AdRequest adreq1;
    AdRequest adreq;
    private InterstitialAd intestrial;
    int count=0;
    int intcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloads_activity);
        try
        {

            pb=(ProgressBar)findViewById(R.id.pb);

            cd=new NetworkConnections(this);
            pd=new ProgressDialog(this);
            back=(ImageView)findViewById(R.id.back);
            title=(TextView)findViewById(R.id.viewtitle);
            listview =(ListView)findViewById(R.id.listview);
            emptydata =(ImageView)findViewById(R.id.emptydownloads);
            dataitems = new ArrayList<Downloads_Feed>();
            adapter = new Downloads_Adapter(this, dataitems);
            listview.setAdapter(adapter);
            adView1=findViewById(R.id.adView1);

            face= Typeface.createFromAsset(getAssets(), "font/fonts.otf");
            title.setTypeface(face);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            intestrial = new InterstitialAd(VideoDownloads.this);
            intestrial.setAdUnitId("ca-app-pub-2432830627480060/3287035679");
            adreq = new AdRequest.Builder().build();
            adreq1 = new AdRequest.Builder().build();


            try
            {
                adView1.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        try
                        {
                            if(count<=10)
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

                        if(intcount<=10) {
                            intestrial.loadAd(adreq);
                            intcount++;
                        }

                    }
                });

            }
            catch (Exception a)
            {

            }

        }
        catch (Exception a)
        {

        }

    }

    @Override
    public void onBackPressed() {
        if(intestrial.isLoaded())
        {
            intestrial.show();
        }

        super.onBackPressed();
    }


    public void dataloading()
    {
        try
        {
            emptydata.setVisibility(View.GONE);
            dataitems.clear();
            adapter.notifyDataSetChanged();
            ArrayList<String> id1 = db.get_downloadlist();
            String k[] = id1.toArray(new String[id1.size()]);
            String exist="";
            for(int i=0;i<k.length;i++)
            {

                Downloads_Feed item=new Downloads_Feed();
                item.set_pkey(k[i]);
                i++;
                item.setdown_id(k[i]);
                i++;
                item.setdown_type(k[i]);
                i++;
                item.set_down_name(k[i]);
                i++;
                item.setdown_path(k[i]);
                exist=k[i];
                i++;
                item.setdown_url(k[i]);
                i++;
                item.setnew_name(k[i]);
                File f=new File(exist.replace("file://",""));
                if(f.exists())
                {
                    dataitems.add(item);
                }

            }
            if(k.length>0)
            {
                pb.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
            else
            {
                pb.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                emptydata.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception a)
        {

        }


    }
    public void removeitem(int position)
    {
        try
        {
            dataitems.remove(position);
            adapter.notifyDataSetChanged();
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
            adView1.loadAd(adreq1);
            intestrial.loadAd(adreq);
        }
        catch (Exception a)
        {

        }


        try
        {
            dataloading();
        }
        catch(Exception a)
        {
        }
    }


    public void openfile(String path)
    {
        try {

            String mimtype="";

            if(path.contains(".jpg"))
            {
                mimtype="image/*";
            }
            else if(path.contains(".mp4"))
            {
                mimtype="video/*";
            }
            File f=new File(path);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkURI = FileProvider.getUriForFile(
                    getApplicationContext(),
                    this.getApplicationContext()
                            .getPackageName() + ".provider", f);
            intent.setDataAndType(apkURI, mimtype);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            Toasty.error(getApplicationContext(), "Sorry ! Unable to play", Toast.LENGTH_SHORT, true).show();

        }
    }

}


