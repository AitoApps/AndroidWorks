package com.suhi_chintha;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import chintha_adapter.Video_List_Adapter;
import chintha_data.videoList_Feed;
import com.airbnb.lottie.LottieAnimationView;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class video_frag extends Fragment {
    NetConnection cd;

    public Context context;
    public DataDB1 db;
    public DataDB4 db4;
    Typeface face;

    public List<videoList_Feed> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView list;

    public Video_List_Adapter listAdapter;
    LottieAnimationView loadingicon;
    ImageView nointernet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.farg_video, container, false);
        try {
            context = getActivity();
            db = new DataDB1(context);
            db4 = new DataDB4(context);
            face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
            nointernet = (ImageView) rootView.findViewById(R.id.nonet);
            list = (ListView) rootView.findViewById(R.id.listview);
            cd = new NetConnection(context);
            footerview = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.bottomview, null);
            list.addFooterView(footerview);
            footerview. setVisibility(View.GONE);
            feedItems = new ArrayList();
            listAdapter = new Video_List_Adapter(getActivity(), feedItems);
            list.setAdapter(listAdapter);
            loadingicon = (LottieAnimationView) rootView.findViewById(R.id.lotty_loadin);
            loadingicon.setAnimation("loading.json");
            loadingicon.playAnimation();
            loadingicon.loop(true);
            list.setOnScrollListener(new OnScrollListener() {
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (visibleItemCount == totalItemCount - firstVisibleItem && flag) {
                        flag = false;
                        if (!cd.isConnectingToInternet()) {
                            Toast.makeText(context, Static_Variable.nonet_scroll, Toast.LENGTH_SHORT).show();
                        } else if (footerview.getVisibility() != View.VISIBLE) {
                            limit += 30;
                            new getyoutube1().execute(new String[0]);
                        }
                    }
                }

                public void onScrollStateChanged(AbsListView arg0, int arg1) {
                    if (arg1 == 2) {
                        flag = true;
                    }
                }
            });
            nointernet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nointernet. setVisibility(View.GONE);
                        limit = 0;
                        new getyoutube().execute(new String[0]);
                        return;
                    }
                    nointernet.setVisibility(View.VISIBLE);
                    Toast.makeText(context, Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }
        return rootView;
    }

    public void refresh() {
        try {
            if (cd.isConnectingToInternet()) {
                nointernet. setVisibility(View.GONE);
                limit = 0;
                new getyoutube().execute(new String[0]);
                return;
            }
            nointernet.setVisibility(View.VISIBLE);
            Toast.makeText(context, Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        } catch (Exception a) {
            Toasty.info(context, (CharSequence) Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public class getyoutube extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            feedItems.clear();
            nointernet. setVisibility(View.GONE);
            list. setVisibility(View.GONE);
            loadingicon.setVisibility(View.VISIBLE);
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"getvideolist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 7;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        videoList_Feed item = new videoList_Feed();
                        m=m+1;
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(got[m]));
                            item.setRegdate(getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e) {
                            item.setRegdate(got[m]);
                        }
                        m=m+1;
                        item.setMediatype(got[m]);
                        m=m+1;
                        item.setTitle(got[m]);
                        m=m+1;
                        item.setFbid(got[m]);
                        m=m+1;
                        item.setDim(got[m]);
                        m=m+1;
                        item.setImgsrc(got[m]);
                        m=m+1;
                        item.setVideosrc(got[m]);
                        feedItems.add(item);
                    }
                    listAdapter.notifyDataSetChanged();
                    list.setVisibility(View.VISIBLE);
                    loadingicon. setVisibility(View.GONE);
                    return;
                }
                loadingicon. setVisibility(View.GONE);
                footerview. setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public class getyoutube1 extends AsyncTask<String, Void, String> {
        public getyoutube1() {
        }
        public void onPreExecute() {
            footerview.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"getvideolist.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+"", "UTF-8");
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
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 7;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        videoList_Feed item = new videoList_Feed();
                        m=m+1;
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(got[m]));
                            item.setRegdate(getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e) {
                            item.setRegdate(got[m]);
                        }
                        m=m+1;
                        item.setMediatype(got[m]);
                        m=m+1;
                        item.setTitle(got[m]);
                        m=m+1;
                        item.setFbid(got[m]);
                        m=m+1;
                        item.setDim(got[m]);
                        m=m+1;
                        item.setImgsrc(got[m]);
                        m=m+1;
                        item.setVideosrc(got[m]);
                        feedItems.add(item);
                    }
                    return;
                }
                loadingicon. setVisibility(View.GONE);
                footerview. setVisibility(View.GONE);
            } catch (Exception e2) {
            }
        }
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        final String timeFormatString = "h:mm a";
        final String dateTimeFormatString = "MMM d h:mm a";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return DateFormat.format(timeFormatString, smsTime)+"";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }

    }
}
