package com.suhi_chintha;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import chintha_adapter.FvrtsLitsAdapter;
import chintha_data.ChinthaFeed;
import es.dmoral.toasty.Toasty;

public class FvrtChinthakal_List extends AppCompatActivity {

    final DataDb dataDb =new DataDb(this);

    final DataDB4 dataDb4 =new DataDB4(this);
    final DataDB1 dataDb1 =new DataDB1(this);
    final DataDB2 dataDb2 =new DataDB2(this);
    ListView list;
    ImageView emptydata;
    TextView text;
    Typeface face;
    ImageView moveback;
    RelativeLayout content;
    public int limit=0;
    RelativeLayout footview;
    boolean flag=false;
    private FvrtsLitsAdapter apater;
    private List<ChinthaFeed> feed;
    public AdView adView1;
    AdRequest adreq1;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fvrtlist_actvty);

        try
        {
            list= findViewById(R.id.listview);
            emptydata = findViewById(R.id.emptydata);
            adView1=findViewById(R.id.adView1);
            adreq1 = new AdRequest.Builder().build();
            LayoutInflater inflater=this.getLayoutInflater();
            footview =(RelativeLayout) inflater.inflate(R.layout.bottomview, null);
            list.addFooterView(footview);
            footview.setVisibility(View.GONE);
            moveback = findViewById(R.id.moveback);
            content= findViewById(R.id.content);

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
            }
            catch (Exception a)
            {

            }
            moveback.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    onBackPressed();

                }
            });
            list.setVisibility(View.GONE);
            emptydata.setVisibility(View.GONE);
            text= findViewById(R.id.text);
            face=Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            text.setText(Static_Variable.textlist_chintha);
            text.setTypeface(face);
            text.setSelected(true);
            feed = new ArrayList<ChinthaFeed>();
            apater = new FvrtsLitsAdapter(this, feed);
            list.setAdapter(apater);
            list.setOnScrollListener(new OnScrollListener(){

                @Override
                public void onScroll(AbsListView view,
                                     int firstVisibleItem, int visibleItemCount,
                                     int totalItemCount) {

                    if ((visibleItemCount == (totalItemCount - firstVisibleItem))
                            && flag) {
                        flag = false;

                        if(footview.getVisibility()==View.VISIBLE)
                        {

                        }
                        else
                        {
                            footview.setVisibility(View.VISIBLE);
                            limit=limit+30;
                            data_disply();
                        }
                    }
                }
                @Override
                public void onScrollStateChanged(AbsListView arg0, int arg1) {
                    if (arg1== 2)
                        flag = true;
                }
            });
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + Static_Variable.foldername+"/bg/bg.png");
            if(f.exists())
            {
                try
                {
                    Glide.with(getApplicationContext()).asBitmap().load(f).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            content.setBackground(drawable);
                        }
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);

                        }
                    });
                }
                catch(Exception a)
                {
                }
            }
        }
        catch(Exception a)
        {
        }
    }
    public void data_disply()
    {
        try
        {
            ArrayList<String> id1=	dataDb2.get_fvrt1(limit+"");
            String[] c=id1.toArray(new String[id1.size()]);
            if(c.length>0)
            {
                int a=c.length/9;
                int m=-1;
                for(int j=1;j<=a;j++)
                {
                    ChinthaFeed item = new ChinthaFeed();
                    m++;
                    item.setstatusid(c[m]);
                    m++;
                    item.setuserid(c[m]);
                    item.setProfilePic(Static_Variable.entypoint1 +"userphotosmall/"+c[m]+".jpg");
                    m++;
                    item.setname(c[m]);
                    m++;
                    item.setmobile(c[m]);
                    m++;
                    item.setstatus(c[m]);
                    m++;
                    item.setshowmobile(c[m]);
                    m++;
                    item.setstatustype(c[m]);
                    m++;
                    item.setphotourl(c[m]);
                    m++;
                    item.setphotodimension(c[m]);
                    feed.add(item);
                }
                list.setVisibility(View.VISIBLE);
                emptydata.setVisibility(View.GONE);
                apater.notifyDataSetChanged();
                footview.setVisibility(View.GONE);
            }
            else
            {
                if(feed.size()==0)
                {
                    footview.setVisibility(View.GONE);
                    emptydata.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                }
                else
                {
                    footview.setVisibility(View.GONE);
                }

            }
        }
        catch(Exception a)
        {

        }
    }
    public void clearitem(int position)
    {
        feed.remove(position);
        apater.notifyDataSetChanged();
    }
    @Override
    public void onResume()
    {
        try
        {
            list.setVisibility(View.GONE);
            emptydata.setVisibility(View.GONE);
            feed.clear();
            limit=0;
            data_disply();


            count=0;
            try
            {
                adView1.loadAd(adreq1);

            }
            catch (Exception a)
            {

            }

        }
        catch(Exception a)
        {

        }
        super.onResume();
    }
    public void status_share(String status)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Status");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, status +"\n "+ Static_Variable.appshare_link);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void sharetoall(Bitmap bitmap) {
        try {
            String filename = "Status_Chinthakal_" + System.currentTimeMillis() + ".png";
            File file = new File(getCacheDir(), filename);
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/*");
            startActivity(intent);
        } catch (Exception e) {
            Toasty.info(getApplicationContext(), "Unable to img_share", Toast.LENGTH_SHORT).show();
        }
    }
}
