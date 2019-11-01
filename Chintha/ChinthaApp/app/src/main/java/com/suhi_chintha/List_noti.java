package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import chintha_adapter.NotiAdapter;
import chintha_data.NotiFeed;

public class List_noti extends AppCompatActivity {
    final DataDB4 dataDb4 =new DataDB4(this);
    ListView listview;
    ImageView emptdata;
    TextView text;
    Typeface face;
    ImageView moveback;
    RelativeLayout content;
    public int limit=0;
    RelativeLayout footview;
    boolean flag=false;
    private NotiAdapter apater;
    private List<NotiFeed> feed;
    ImageView readall,deleteall;
    public static String comments_txt ="";
    ProgressDialog pd1;
    NetConnection cd;
    final DataDB1 dataDb1 =new DataDB1(this);
    final DataDb dataDb =new DataDb(this);
    final User_DataDB userDataDB =new User_DataDB(this);
    ProgressDialog pd;
    public Dialog dialog;
    public AdView adView1;
    AdRequest adreq1;
    int count=0;
    int count1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_list_actvty);
        try
        {
            adView1=findViewById(R.id.adView1);
            adreq1 = new AdRequest.Builder().build();
          pd1=new ProgressDialog(this);
            listview = findViewById(R.id.listview);
            emptdata = findViewById(R.id.emptydata);
            moveback = findViewById(R.id.moveback);
            content= findViewById(R.id.content);
            pd1.setMessage("Please wait..loading ads");
            pd1.setCancelable(true);
            cd=new NetConnection(this);
            readall= findViewById(R.id.readall);
            deleteall= findViewById(R.id.deleteall);

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
            pd=new ProgressDialog(this);

            moveback.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    onBackPressed();

                }
            });
            listview.setVisibility(View.GONE);
            emptdata.setVisibility(View.GONE);
            text= findViewById(R.id.text);
            face=Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            text.setText(Static_Variable.text_noties);
            text.setTypeface(face);
            text.setSelected(true);
            LayoutInflater inflater=this.getLayoutInflater();
            footview =(RelativeLayout) inflater.inflate(R.layout.bottomview, null);
            listview.addFooterView(footview);
            footview.setVisibility(View.GONE);
            feed = new ArrayList<NotiFeed>();
            apater = new NotiAdapter(this, feed);
            listview.setAdapter(apater);
            listview.setOnScrollListener(new OnScrollListener(){

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
            readall.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    alertreadall(Static_Variable.noti_readall);
                }
            });
             deleteall.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View view) {
                       alert_all_delete(Static_Variable.delete_allnoti);
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


    @Override
    public void onBackPressed() {

        if(isTaskRoot())
        {
            Intent intent = new Intent(getApplicationContext(), HeartOf_App.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    public void onResume()
    {
        try
        {
            listview.setVisibility(View.GONE);
            emptdata.setVisibility(View.GONE);
            feed.clear();
            limit=0;
            data_disply();
        }
        catch(Exception a)
        {

        }

        count=0;
        count1=0;
        try
        {
            adView1.loadAd(adreq1);

        }
        catch (Exception a)
        {

        }
        super.onResume();
    }
    public void alert_all_delete(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dataDb1.deletenotilist();
                        limit=0;
                        feed.clear();
                        data_disply();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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
    public void alertreadall(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dataDb1.updatereadstatusall();
                        limit=0;
                        feed.clear();
                        data_disply();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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


    public void data_disply()
    {
        ArrayList<String> id1=	dataDb1.get_notilist(limit+"");
        String[] c=id1.toArray(new String[id1.size()]);
        if(c.length>0)
        {
            int a=c.length/15;
            int m=-1;
            for(int j=1;j<=a;j++)
            {
                NotiFeed item=new NotiFeed();
                m++;
                item.set_ogId(c[m]);
                m++;
                item.settype(c[m]);
                m++;
                item.setlastname(c[m]);
                m++;
                item.setcount(c[m]);
                m++;
                item.settext(c[m]);
                m++;
                item.set_createddate(c[m]);
                m++;
                item.setuserid(c[m]);
                m++;
                item.set_isread(c[m]);
                m++;
                item.setpkey(c[m]);
                m++;
                item.set_chinthauserid(c[m]);
                m++;
                item.set_chintha_username(c[m]);
                m++;
                item.set_imgsig(c[m]);
                m++;
                item.setstatustype(c[m]);
                m++;
                item.set_picurl(c[m]);
                m++;
                item.set_photodim(c[m]);
                feed.add(item);
            }
            listview.setVisibility(View.VISIBLE);
            emptdata.setVisibility(View.GONE);
            apater.notifyDataSetChanged();
            footview.setVisibility(View.GONE);
        }
        else
        {
            if(feed.size()==0)
            {
                footview.setVisibility(View.GONE);
                emptdata.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
            }
            else
            {
                footview.setVisibility(View.GONE);
            }
        }
    }
}
