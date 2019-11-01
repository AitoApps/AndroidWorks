package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import chintha_adapter.BlckdAdapter;
import chintha_data.LikeFeed;
import com.airbnb.lottie.LottieAnimationView;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chintha_bloked extends AppCompatActivity {

    public BlckdAdapter apater;
    ImageView back;
    NetConnection cd;
    RelativeLayout content;
    final DataDB1 dataDb1 = new DataDB1(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    ImageView emptydata;
    Typeface face;
    public List<LikeFeed> feed;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView listview;
    LottieAnimationView load_icons;
    ImageView nonet;
    ProgressDialog pd;
    TextView text;
    final User_DataDB userDataDB = new User_DataDB(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.blockinglists_actvty);
        try {
            load_icons = (LottieAnimationView) findViewById(R.id.lotty_loadin);
            text = (TextView) findViewById(R.id.text);
            pd = new ProgressDialog(this);
            cd = new NetConnection(this);
            content = (RelativeLayout) findViewById(R.id.content);
            back = (ImageView) findViewById(R.id.moveback);
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            text.setText("ബ്ലോക്ക്ഡ് ലിസ്റ്റ്‌ ");
            text.setTypeface(face);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    onBackPressed();
                }
            });
            text.setSelected(true);
            listview = (ListView) findViewById(R.id.listview);
            emptydata = (ImageView) findViewById(R.id.emptydata);
            nonet = (ImageView) findViewById(R.id.nonets);
            footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.bottomview, null);
            listview.addFooterView(footerview);
            footerview. setVisibility(View.GONE);
            feed = new ArrayList();
            apater = new BlckdAdapter(this, feed);
            listview.setAdapter(apater);
            nonet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nonet. setVisibility(View.GONE);
                        limit = 0;
                        new getdatas().execute(new String[0]);
                        return;
                    }
                    nonet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            if (cd.isConnectingToInternet()) {
                nonet. setVisibility(View.GONE);
                new getdatas().execute(new String[0]);
                return;
            }
            nonet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    public void update_Timer(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void removeitem(int position) {
        feed.remove(position);
        apater.notifyDataSetChanged();
    }
    public class getdatas extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            listview. setVisibility(View.GONE);
            load_icons.setVisibility(View.VISIBLE);
            nonet. setVisibility(View.GONE);
            emptydata. setVisibility(View.GONE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"getblockeduser.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(userDataDB.get_userid(), "UTF-8");
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
                if (result.contains("%:ok")) {
                    feed.clear();
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 3;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        LikeFeed item = new LikeFeed();
                        m = m + 1;
                        item.set_userid(got[m]);
                        item.set_dppic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                        m=m+1;
                        item.setName(got[m]);
                        m = m + 1;
                        item.set_imgsig(got[m]);
                        feed.add(item);
                    }
                    Collections.reverse(feed);
                    load_icons. setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);
                    apater.notifyDataSetChanged();
                    return;
                }
                emptydata.setVisibility(View.VISIBLE);
                load_icons. setVisibility(View.GONE);
                footerview. setVisibility(View.GONE);
            } catch (Exception e) {
            }
        }
    }
}
