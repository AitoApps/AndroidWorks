package com.downly_app;

import Downly_Data.Feed_DownloadList;
import Downly_adapter.Adapter_DownloadList;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import es.dmoral.toasty.Toasty;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadsList extends AppCompatActivity {
    ImageView back;
    InternetConncetivity cd;
    final DataBase db = new DataBase(this);
    Typeface face;
    private List<Feed_DownloadList> feedItems;
    ListView list;
    private Adapter_DownloadList listAdapter;
    ImageView nodata;
    ProgressBar pb;
    ProgressDialog pd;
    TextView title;

    public InterstitialAd interstitial;
    AdRequest adRequest;
    AdRequest adRequest1;
    public AdView adView1;
    int count = 0;
    int intcount = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_listofdownloads);
        try {
            adView1 = (AdView) findViewById(R.id.adView1);
            title = (TextView) findViewById(R.id.title);
            list = (ListView) findViewById(R.id.list);
            cd = new InternetConncetivity(this);
            pd = new ProgressDialog(this);
            pb = (ProgressBar) findViewById(R.id.pb);
            back = (ImageView) findViewById(R.id.back);
            nodata = (ImageView) findViewById(R.id.nodownloads);
            feedItems = new ArrayList();
            listAdapter = new Adapter_DownloadList(this, feedItems);
            list.setAdapter(listAdapter);
            face = Typeface.createFromAsset(getAssets(), "commonfont.otf");
            title.setTypeface(face);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId("ca-app-pub-5517777745693327/1464133446");
            adRequest = new Builder().build();
            adRequest1 = new Builder().build();
            try {
                adView1.setAdListener(new AdListener() {
                    public void onAdFailedToLoad(int errorCode) {
                        try {
                            if (count <= 10) {
                                adView1.loadAd(adRequest);
                                count++;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                interstitial.setAdListener(new AdListener() {
                    public void onAdFailedToLoad(int errorCode) {
                        if (intcount <= 10) {
                            interstitial.loadAd(adRequest1);
                            intcount++;
                        }
                    }
                });
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
    }
    public void onResume() {
        super.onResume();
        try {
            count = 0;
            intcount = 0;
            adView1.loadAd(adRequest);
            interstitial.loadAd(adRequest1);
        } catch (Exception e) {
        }
        try {
            loaddata();
        } catch (Exception e2) {
        }
    }

    public void loaddata() {
        try {
            nodata.setVisibility(View.GONE);
            feedItems.clear();
            listAdapter.notifyDataSetChanged();
            ArrayList<String> id1 = db.get_ListofDownloads();
            String[] k = (String[]) id1.toArray(new String[id1.size()]);
            for(int i=0;i<k.length;i++)
            {
                Feed_DownloadList item = new Feed_DownloadList();
                item.setPkey(k[i]);
                i=i+1;
                item.setDownid(k[i]);
                i=i+1;
                item.setDowntype(k[i]);
                i=i+1;
                item.setDownname(k[i]);
                String downname=k[i];
                i=i+1;
                item.setDownpath(k[i]);
                String exist = k[i];
                i=i+1;
                item.setDownurl(k[i]);
                i=i+1;
                item.setNewname(k[i]);
                i=i+1;
                String ishsow=k[i];
                if(ishsow.equalsIgnoreCase("1"))
                {
                    if (new File(exist.replace("file://", "")).exists()) {
                        feedItems.add(item);
                    }
                }

            }

            if (k.length > 0) {
                pb.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                listAdapter.notifyDataSetChanged();
                return;
            }
            pb.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            listAdapter.notifyDataSetChanged();
            nodata.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    public void opendownfile(String path) {
        try {

            String mimtype="";

            if(path.contains(".jpg") || path.contains(".JPG") || path.contains(".jpeg") || path.contains(".JPEG") || path.contains(".png") || path.contains(".PNG") || path.contains(".gif") || path.contains(".GIF"))
            {
                mimtype="image/*";
            }
            else if(path.contains(".mp4") || path.contains(".MP4"))
            {
                mimtype="video/*";
            }
            else if(path.contains(".mp3") || path.contains(".MP3"))
            {
                mimtype="audio/*";
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

    public void removeitem(int position) {
        try {
            feedItems.remove(position);
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }
}
