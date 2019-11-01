package com.suhi_chintha;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import androidx.appcompat.app.AppCompatActivity;

import chintha_adapter.FvrtChinthakarAdapter;
import chintha_data.ChinthakarFeed;

public class Favourite_UsersList extends AppCompatActivity {
    RelativeLayout layout2;
    ListView listview;
    ImageView emptdata, nonet;
    final DataDB4 dataDb4 =new DataDB4(this);
    final DataDb dataDb =new DataDb(this);
    final DataDB1 dataDb1 =new DataDB1(this);
    private FvrtChinthakarAdapter apater;
    private List<ChinthakarFeed> feed;
    LottieAnimationView load_icon;
    ImageView moveback;
    TextView text,text2;
    Typeface face;
    RelativeLayout content;
    public int limit=0;
    RelativeLayout footview;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fvrtusers_actvty);
        try
        {
            load_icon =findViewById(R.id.lotty_loadin);
            text= findViewById(R.id.text);
            text2= findViewById(R.id.text2);
            content= findViewById(R.id.content);
            face=Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            text.setText(Static_Variable.chinthers_1);
            text.setTypeface(face);

            layout2= findViewById(R.id.layout2);
            layout2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Search_Result.txtsearch="";
                    Intent i=new Intent(getApplicationContext(), Search_Result.class);
                    startActivity(i);

                }
            });
            text.setSelected(true);
            text2.setText(Static_Variable.chinthers_text2);
            text2.setTypeface(face);
            moveback = findViewById(R.id.moveback);
            moveback.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    onBackPressed();

                }
            });
            listview = findViewById(R.id.listview);
            emptdata = findViewById(R.id.emptydata);
            nonet = findViewById(R.id.nonets);
            LayoutInflater inflater=this.getLayoutInflater();
            footview =(RelativeLayout) inflater.inflate(R.layout.bottomview, null);
            listview.addFooterView(footview);
            footview.setVisibility(View.GONE);
            feed = new ArrayList<ChinthakarFeed>();
            apater = new FvrtChinthakarAdapter(this, feed);
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
    public void onResume()
    {
        try
        {
            load_icon.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
            emptdata.setVisibility(View.GONE);
            feed.clear();
            limit=0;
            data_disply();
        }
        catch(Exception a)
        {

        }

        super.onResume();
    }
    public void data_disply()
    {

        ArrayList<String> id1=	dataDb.get_fvrtusr1(limit+"");
        String[] c=id1.toArray(new String[id1.size()]);
        if(c.length>0)
        {
            int a=c.length/2;

            int m=-1;
            for(int j=1;j<=a;j++)
            {

                ChinthakarFeed item = new ChinthakarFeed();
                m++;
                item.setuserid(c[m]);
                item.setDppic(Static_Variable.entypoint1 +"userphotosmall/"+c[m]+".jpg");
                m++;
                item.setName(c[m]);

                feed.add(item);

            }
            load_icon.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            emptdata.setVisibility(View.GONE);
            apater.notifyDataSetChanged();
            footview.setVisibility(View.GONE);

        }
        else
        {
            if(feed.size()==0)
            {
                load_icon.setVisibility(View.GONE);
                footview.setVisibility(View.GONE);
                emptdata.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
            }
            else
            {
                load_icon.setVisibility(View.GONE);
                footview.setVisibility(View.GONE);
            }

        }


    }
    public void clearitem(int position)
    {
        feed.remove(position);
        apater.notifyDataSetChanged();
    }


}