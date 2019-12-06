package com.fishapp.user;

import adapter.FishCatogery_Adapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import data.FishCatogery_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Fish_Catogery extends AppCompatActivity {
    public FishCatogery_Adapter adapter;
    RelativeLayout adlyt;
    ImageView back;
    ConnectionDetecter cd;
    int clickid = -1;
    Typeface face;
    HeaderAndFooterRecyclerView fishcats;
    ViewFlipper flipper;
    ImageView heart;
    public int limit = 0;
    List<String> lst_fishid = new ArrayList();
    List<String> lst_fishrefernce = new ArrayList();
    List<String> lst_fishtype = new ArrayList();
    public GestureDetector mDetector;
    ImageView nodata;
    ImageView nointernet;
    public float ogheight;
    TextView text;
    View headerView;
    final UserDatabaseHandler udb = new UserDatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_fish__catogery);
        heart = (ImageView) findViewById(R.id.heart);

        nodata = (ImageView) findViewById(R.id.nodata);
        cd = new ConnectionDetecter(this);
        nointernet = (ImageView) findViewById(R.id.nointernet);
        text = (TextView) findViewById(R.id.text);
        fishcats = (HeaderAndFooterRecyclerView) findViewById(R.id.fishcats);
        back = (ImageView) findViewById(R.id.back);
        Glide.with(this).load(Integer.valueOf(R.drawable.loading)).into(heart);
        face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
        text.setTypeface(face);

        headerView = LayoutInflater.from(this).inflate(R.layout.advtview, fishcats.getFooterContainer(), false);
        fishcats.addHeaderView(headerView);


        flipper = (ViewFlipper) headerView.findViewById(R.id.flipper1);
        adlyt = (RelativeLayout) headerView.findViewById(R.id.adlyt);

        mDetector = new GestureDetector(this, new MyGestureListener());
        flipper.setOnTouchListener(touchListener);
        adapter = new FishCatogery_Adapter(this, Temp.feeditem);




        adlyt.post(new Runnable() {
            public void run() {
                ogheight = Float.parseFloat(udb.getscreenwidth()) / 4.0f;
                ogheight *= 2.0f;
                adlyt.getLayoutParams().height = Math.round(ogheight);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fishcats.setLayoutManager(new GridLayoutManager(this, 3));
        fishcats.setAdapter(adapter);
        nointernet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (cd.isConnectingToInternet()) {
                    nointernet.setVisibility(View.GONE);
                    limit = 0;
                    new loadstatus().execute(new String[0]);
                    return;
                }
                nointernet.setVisibility(View.VISIBLE);
            }
        });
        if (cd.isConnectingToInternet()) {
            nointernet.setVisibility(View.GONE);
            limit = 0;
            new loadstatus().execute(new String[0]);
            return;
        }
        nointernet.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
    }
    public void call(String mob) {
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            Intent callIntent = new Intent("android.intent.action.CALL");
            callIntent.setData(Uri.parse("tel:"+mob));
            startActivity(callIntent);
        } catch (Exception e) {
        }
    }
    public void web(String web) {
        Intent pagIntent = new Intent("android.intent.action.VIEW");
        pagIntent.setData(Uri.parse(web));
        try {
            startActivity(pagIntent);
        } catch (Exception e) {
        }
    }

    public class loadstatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            heart.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"getfishcatogery_user.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.getareaid(), "UTF-8");
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
            try {
                if (result.trim().contains(":%ok#####")) {
                    String[] got1 = result.trim().split(":%ok#####");
                    String[] got = got1[0].split(":%");
                    int k = got.length / 3;
                    int m = -1;
                    for (int i2 = 1; i2 <= k; i2++) {
                        FishCatogery_FeedItem item = new FishCatogery_FeedItem();
                        m=m+1;
                        item.setSn(got[m]);
                        m=m+1;
                        item.setCatogery(got[m]);
                        m=m+1;
                        item.setImgsig(got[m]);
                        Temp.feeditem.add(item);
                    }
                    if (got1[1].contains(":%%#")) {
                        adlyt.setVisibility(View.VISIBLE);
                        got1[1].replace(":%%#", "");
                        String[] got2 = got1[1].split(":%");
                        int k1 = got2.length / 4;
                        int m1 = -1;
                        int n=0;
                        for(int i=1;i<=k1;i++)
                        {
                            m1=m1+1;
                            int a = m1;
                            lst_fishid.add(got2[a]);
                            m1=m1+1;
                            int a1 = m1;
                            lst_fishtype.add(got2[a1]);
                            m1=m1+1;
                            int a2 = m1;
                            lst_fishrefernce.add(got2[a2]);
                            m1=m1+1;
                            int a3 = m1;
                            ImageView imageView = new ImageView(Fish_Catogery.this);
                            imageView.setScaleType(ScaleType.FIT_XY);
                            imageView.setId(n);
                            RequestOptions rep = new RequestOptions().signature(new ObjectKey(got2[a3]));
                            Glide.with(getApplicationContext()).load(Temp.weblink+"advt/"+got2[a].trim()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
                            imageView.setOnTouchListener(new OnTouchListener() {
                                public boolean onTouch(View arg0, MotionEvent event) {
                                    clickid = arg0.getId();
                                    return false;
                                }
                            });
                            flipper.addView(imageView);
                            n++;
                        }

                        flipper.startFlipping();
                        clickid = -1;
                    } else {
                        adlyt.setVisibility(View.GONE);
                    }
                    heart.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    return;
                }
                heart.setVisibility(View.GONE);
            } catch (Exception e) {
                Log.w("SAma",Log.getStackTraceString(e));
            }
        }
    }

    OnTouchListener touchListener = new OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            return mDetector.onTouchEvent(event);
        }
    };
    class MyGestureListener extends SimpleOnGestureListener {
        MyGestureListener() {
        }

        public boolean onDown(MotionEvent event) {
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            try {
                if (clickid != -1) {
                    if (((String) lst_fishtype.get(clickid)).toString().equalsIgnoreCase("1")) {
                        Temp.isfromad = 1;
                        Temp.fish_sn = (String) lst_fishrefernce.get(clickid);
                        startActivity(new Intent(getApplicationContext(), Fish_Details.class));
                    } else if (((String) lst_fishtype.get(clickid)).equalsIgnoreCase("2")) {
                        call((String) lst_fishrefernce.get(clickid));
                    } else if (((String) lst_fishtype.get(clickid)).equalsIgnoreCase("3")) {
                        web((String) lst_fishrefernce.get(clickid));
                    }
                }
            } catch (Exception e2) {
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() > e2.getX()) {
                flipper.setInAnimation(Fish_Catogery.this, R.anim.left_in);
                flipper.setOutAnimation(Fish_Catogery.this, R.anim.left_out);
                flipper.showNext();
                flipper.setFlipInterval(8000);
            }
            if (e1.getX() < e2.getX()) {
                flipper.setInAnimation(Fish_Catogery.this, R.anim.right_in);
                flipper.setOutAnimation(Fish_Catogery.this, R.anim.right_out);
                flipper.showPrevious();
                flipper.setFlipInterval(8000);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
