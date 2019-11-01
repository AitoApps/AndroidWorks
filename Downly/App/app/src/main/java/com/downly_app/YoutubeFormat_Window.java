package com.downly_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Downly_Data.Feed_DownloadList;
import Downly_Data.Feed_Dumy_Formats;
import Downly_Data.Feed_Formats;
import Downly_adapter.Adapter_DownloadList;
import Downly_adapter.Adapter_Formats;
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import es.dmoral.toasty.Toasty;
import eu.amirs.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class YoutubeFormat_Window extends Activity {
    private Adapter_Formats listAdapter;
    private List<Feed_Formats> feedItems;
    private List<Feed_Dumy_Formats> feedItems1;
    ListView list;
    ShimmerFrameLayout shimmer_view_container;
    TextView txtheader;
    public OkHttpClient client = new OkHttpClient();
    String hdlink = "NA";
    String sdlink = "NA";
    private MediaPlayer mp;
    final DataBase db=new DataBase(this);
    AdRequest adRequest;
    public AdView adView1;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_format__window);

        if (android.os.Build.VERSION.SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        adView1 = (AdView) findViewById(R.id.adView1);
        adRequest = new AdRequest.Builder().build();
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

        } catch (Exception e) {
        }

        shimmer_view_container=findViewById(R.id.shimmer_view_container);
        list=findViewById(R.id.list);
        txtheader=findViewById(R.id.txtheader);
        feedItems = new ArrayList();
        feedItems1 = new ArrayList();
        listAdapter = new Adapter_Formats(this, feedItems);
        list.setAdapter(listAdapter);

        if(Temp.videosource.equalsIgnoreCase("1"))
        {
            getyoutube("https://www.youtube.com/watch?v="+extractYTId(Temp.youtubeurl));
        }
        else if(Temp.videosource.equalsIgnoreCase("2"))
        {
            Facebook_parser(Temp.youtubeurl);
        }
        else if(Temp.videosource.equalsIgnoreCase("3"))
        {
            TikTok_Parser(Temp.youtubeurl);
        }
        else if(Temp.videosource.equalsIgnoreCase("4"))
        {
            Tiwtter_Parser(Temp.youtubeurl);
        }
        else if(Temp.videosource.equalsIgnoreCase("5"))
        {
            sharechat_parser(Temp.youtubeurl);
        }
        else if(Temp.videosource.equalsIgnoreCase("6"))
        {
            instagram_parser(Temp.youtubeurl);
        }
        else if(Temp.videosource.equalsIgnoreCase("7"))
        {
            pintrest_parser(Temp.youtubeurl);
        }


    }


    public String extractYTId(String ytUrl) {
        Matcher matcher = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE).matcher(ytUrl);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }


    public void Facebook_parser(final String link1) {
        txtheader.setText("Please wait...");
        shimmer_view_container.setVisibility(View.VISIBLE);
        shimmer_view_container.startShimmerAnimation();
        list.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {


                Request.Builder builder = new Request.Builder();
                builder.header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                builder.url(link1);
                Request request = builder.build();

                try {
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    final String pattern = "\\\"?hd_src\\\"?:\\\"([^\\\"]+)";
                    final String pattern1 = "\\\"?sd_src_no_ratelimit\\\"?:\\\"([^\\\"]+)";

                    if(!responseString.contains("hd_src") && !responseString.contains("sd_src_no_ratelimit"))
                    {
                        Document doc = Jsoup.parse(responseString);
                        Elements metaTags = doc.getElementsByTag("meta");
                        int i=0;
                        for (Element metaTag : metaTags) {
                            String propery=metaTag.attr("property");
                            if(propery.equalsIgnoreCase("og:video"))
                            {
                                i=1;
                                try
                                {
                                    Feed_Formats item=new Feed_Formats();
                                    item.setUrl(metaTag.attr("content"));
                                    item.setVideosource("facebook");
                                    item.setItag(0);
                                    item.setType("MP4 SD");

                                    item.setSize("0");
                                    item.setAudiourl("");
                                    item.setAudiobitrate("");
                                    item.setAudiourlsize("");
                                    item.setHeight(0);
                                    feedItems.add(item);

                                }
                                catch (Exception a)
                                {

                                }

                                break;
                            }
                        }

                        if(i==0)
                        {

                            Feed_Formats item=new Feed_Formats();
                            item.setUrl("");
                            item.setVideosource("facebook");
                            item.setItag(0);
                            item.setType("");
                            item.setSize("0");
                            item.setAudiourl("");
                            item.setAudiobitrate("");
                            item.setAudiourlsize("");
                            item.setHeight(0);
                            feedItems.add(item);

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtheader.setText("Download");
                                shimmer_view_container.stopShimmerAnimation();
                                shimmer_view_container.setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                                listAdapter.notifyDataSetChanged();
                                calculatesize();
                            }
                        });
                    }
                    else
                    {
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(responseString);

                        Pattern r1 = Pattern.compile(pattern1);
                        Matcher m1 = r1.matcher(responseString);

                        try {
                            if (m.find()) {
                                hdlink = m.group(1);
                            } else {
                                hdlink = "NA";
                            }
                        } catch (Exception a) {
                            hdlink = "NA";
                        }


                        try {
                            if (m1.find()) {
                                sdlink = m1.group(1);
                            } else {
                                sdlink = "NA";
                            }
                        } catch (Exception a) {
                            sdlink = "NA";
                        }

                        feedItems.clear();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                if(sdlink.equalsIgnoreCase("NA") && hdlink.equalsIgnoreCase("NA"))
                                {
                                    Feed_Formats item=new Feed_Formats();
                                    item.setUrl("");
                                    item.setVideosource("facebook");
                                    item.setItag(0);
                                    item.setType("");
                                    item.setSize("0");
                                    item.setAudiourl("");
                                    item.setAudiobitrate("");
                                    item.setAudiourlsize("");
                                    item.setHeight(0);
                                    feedItems.add(item);
                                }
                                else
                                {
                                    if(!sdlink.equalsIgnoreCase("NA"))
                                    {
                                        Feed_Formats item=new Feed_Formats();
                                        item.setUrl(sdlink);
                                        item.setVideosource("facebook");
                                        item.setItag(0);
                                        item.setType("MP4 SD");
                                        item.setSize("0");
                                        item.setAudiourl("");
                                        item.setAudiobitrate("");
                                        item.setAudiourlsize("");
                                        item.setHeight(0);
                                        feedItems.add(item);
                                    }
                                    if(!hdlink.equalsIgnoreCase("NA"))
                                    {
                                        Feed_Formats item=new Feed_Formats();
                                        item.setUrl(hdlink);
                                        item.setVideosource("facebook");
                                        item.setItag(0);
                                        item.setType("MP4 HD");
                                        item.setSize("0");
                                        item.setAudiourl("");
                                        item.setAudiobitrate("");
                                        item.setAudiourlsize("");
                                        item.setHeight(0);
                                        feedItems.add(item);
                                    }


                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtheader.setText("Download");
                                        shimmer_view_container.stopShimmerAnimation();
                                        shimmer_view_container.setVisibility(View.GONE);
                                        list.setVisibility(View.VISIBLE);
                                        listAdapter.notifyDataSetChanged();
                                        calculatesize();
                                    }
                                });


                            }
                        }).start();

                    }



                } catch (Exception e) {


                }
            }
        }).start();
    }



    public void TikTok_Parser(final String ticklink) {
        try {
            txtheader.setText("Please wait...");
            shimmer_view_container.setVisibility(View.VISIBLE);
            shimmer_view_container.startShimmerAnimation();
            list.setVisibility(View.INVISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("id", ticklink)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://ssstiktok.com/results")
                            .post(body)
                            .build();

                    Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, final Response response) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        String result = response.body().string();
                                        Document doc = Jsoup.parse(result);
                                        Element table = doc.select("table").get(0); //select the first table.
                                        Elements rows = table.select("tr");
                                        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                                            Element row = rows.get(i);
                                            Elements cols = row.select("td");


                                            if (cols.get(0).html().contains("With watermark")) {

                                            } else if (cols.get(0).html().contains("Without watermark")) {
                                                Document doc1 = Jsoup.parse(cols.get(1).html());
                                                Element link = doc1.select("a").first();
                                                String downlink = link.attr("href");

                                                Feed_Formats item = new Feed_Formats();
                                                item.setUrl(downlink);
                                                item.setVideosource("tiktok");
                                                item.setItag(0);
                                                item.setType("MP4 HD");
                                                item.setSize("0");
                                                item.setAudiourl("");
                                                item.setAudiobitrate("");
                                                item.setAudiourlsize("");
                                                item.setHeight(0);
                                                feedItems.add(item);

                                            } else if (cols.get(0).html().contains("MP3")) {
                                                Document doc1 = Jsoup.parse(cols.get(1).html());
                                                Element link = doc1.select("a").first();
                                                String downlink = link.attr("href");
                                                Feed_Formats item1 = new Feed_Formats();
                                                item1.setUrl(downlink);
                                                item1.setVideosource("tiktok");
                                                item1.setItag(0);
                                                item1.setType("MP3");
                                                item1.setSize("0");
                                                item1.setAudiourl("");
                                                item1.setAudiobitrate("128");
                                                item1.setAudiourlsize("");
                                                item1.setHeight(0);
                                                feedItems.add(item1);
                                            }

                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtheader.setText("Download");
                                                shimmer_view_container.stopShimmerAnimation();
                                                shimmer_view_container.setVisibility(View.GONE);
                                                list.setVisibility(View.VISIBLE);
                                                listAdapter.notifyDataSetChanged();
                                                calculatesize();
                                            }
                                        });

                                    } catch (Exception a) {


                                    }
                                }
                            });
                        }
                    });

                }
            }).start();



        } catch (Exception a) {

        }
    }
    public void getyoutube(String link1) {

        txtheader.setText("Please wait...");
        shimmer_view_container.setVisibility(View.VISIBLE);
        shimmer_view_container.startShimmerAnimation();
        list.setVisibility(View.INVISIBLE);

        new YouTubeExtractor(this) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {

                    feedItems1.clear();
                    feedItems.clear();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String highaudiourl="",lowaudiourl="",highaudiosize="",lowaudiosize="";
                            for (int i = 0; i < ytFiles.size(); i++) {
                                final int itag = ytFiles.keyAt(i);
                                int audioBitrate=ytFiles.get(itag).getFormat().getAudioBitrate();
                                int height = ytFiles.get(itag).getFormat().getHeight();
                                String ext=ytFiles.get(itag).getFormat().getExt();
                                if(height==-1 && audioBitrate>=100 && ext.equalsIgnoreCase("m4a"))
                                {
                                    highaudiourl=ytFiles.get(itag).getUrl();

                                }
                                else if(height==-1 && audioBitrate<100 && ext.equalsIgnoreCase("m4a"))
                                {
                                    lowaudiourl=ytFiles.get(itag).getUrl();
                                }
                            }

                            if(!highaudiourl.equalsIgnoreCase(""))
                            {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder().url(highaudiourl).head().build();
                                Response response = client.newCall(request).execute();
                                if (response.isSuccessful()) {
                                    highaudiosize=response.header("Content-Length");
                                }
                            } catch (Exception a) {

                            }

                            }
                            if(!lowaudiourl.equalsIgnoreCase(""))
                            {
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url(lowaudiourl).head().build();
                                    Response response = client.newCall(request).execute();
                                    if (response.isSuccessful()) {
                                        lowaudiosize=response.header("Content-Length");
                                    }
                                } catch (Exception a) {

                                }
                            }


                            for (int i = 0; i < ytFiles.size(); i++) {
                                final int itag = ytFiles.keyAt(i);
                                Feed_Dumy_Formats item = new Feed_Dumy_Formats();
                                item.setUrl(ytFiles.get(itag).getUrl());
                                item.setItag(itag);
                                item.setVideosource("youtube");

                                int height = ytFiles.get(itag).getFormat().getHeight();
                                int audioBitrate=ytFiles.get(itag).getFormat().getAudioBitrate();

                                if(audioBitrate==-1 && height!=-1)
                                {

                                    if(height>=300)
                                    {
                                       if(!highaudiourl.equalsIgnoreCase(""))
                                       {
                                           item.setAudiourl(highaudiourl);
                                           item.setAudiourlsize(highaudiosize);
                                       }
                                       else if(!lowaudiourl.equalsIgnoreCase(""))
                                       {
                                           item.setAudiourl(lowaudiourl);
                                           item.setAudiourlsize(lowaudiosize);
                                       }
                                       else
                                       {
                                           item.setAudiourl("");
                                           item.setAudiourlsize("");
                                       }
                                    }
                                    else if(height<300)
                                    {

                                        if(!lowaudiourl.equalsIgnoreCase(""))
                                        {
                                            item.setAudiourl(lowaudiourl);
                                            item.setAudiourlsize(lowaudiosize);
                                        }
                                        else if(!highaudiourl.equalsIgnoreCase(""))
                                        {
                                            item.setAudiourl(highaudiourl);
                                            item.setAudiourlsize(highaudiosize);
                                        }
                                        else
                                        {
                                            item.setAudiourl("");
                                            item.setAudiourlsize("");
                                        }
                                    }
                                }
                                else
                                {
                                    item.setAudiourl("");
                                    item.setAudiourlsize("");
                                }

                                item.setAudiobitrate(audioBitrate+"");
                                item.setHeight(height);

                                    if(height==-1)
                                    {
                                        item.setType("Audio");
                                    }
                                    else
                                    {
                                        if(height<240)
                                        {
                                            item.setType("144p LOW");
                                        }
                                        else if(height <360)
                                        {
                                            item.setType("240p MED");
                                        }
                                        else if(height <480)
                                        {
                                            item.setType("360p HIGH");
                                        }
                                        else if(height <720)
                                        {
                                            item.setType("480 HD");
                                        }
                                        else if(height <1080)
                                        {
                                            item.setType("720p HD");
                                        }
                                        else if(height <2160)
                                        {
                                            item.setType("1080p FHD");
                                        }
                                        else if(height <3840)
                                        {
                                            item.setType("2048p 2K");
                                        }
                                        else if(height>=3840){
                                            item.setType("3840p 4K");
                                        }
                                        else
                                        {
                                            item.setType("Video");
                                        }

                                    }
                                item.setSize("0");
                                feedItems1.add(item);

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    Collections.sort(feedItems1, new Comparator<Feed_Dumy_Formats>() {
                                        @Override
                                        public int compare(Feed_Dumy_Formats o1, Feed_Dumy_Formats o2) {
                                            return o1.getHeight()-o2.getHeight();
                                        }
                                    });

                                    ArrayList<Integer> dellist = new ArrayList<Integer>();
                                    String lasttype="",lastaudio="";
                                    for(int i=0;i<feedItems1.size();i++)
                                    {

                                        Feed_Dumy_Formats ff=feedItems1.get(i);
                                        String current=ff.getType();
                                        String audiof=ff.getAudiobitrate();
                                        if(lasttype.equalsIgnoreCase(current) && !current.equalsIgnoreCase("Audio")){

                                            if(audiof.equalsIgnoreCase("-1") && lastaudio.equalsIgnoreCase("-1"))
                                            {
                                                dellist.add(i);
                                            }
                                            else if(audiof.equalsIgnoreCase("-1") && !lastaudio.equalsIgnoreCase("-1"))
                                            {
                                                dellist.add(i);
                                            }
                                            else if(!audiof.equalsIgnoreCase("-1") && lastaudio.equalsIgnoreCase("-1")){
                                                dellist.add((i-1));
                                            }

                                        }
                                        lasttype=current;
                                        lastaudio=audiof;
                                    }

                                    for(int i=0;i<feedItems1.size();i++) {
                                        if (!dellist.contains(i)) {
                                            Feed_Dumy_Formats item1 = feedItems1.get(i);

                                            Feed_Formats item=new Feed_Formats();
                                            item.setSize(item1.getSize());
                                            item.setUrl(item1.getUrl());
                                            item.setType(item1.getType());
                                            item.setVideosource(item1.getVideosource());
                                            item.setAudiourl(item1.getAudiourl());
                                            item.setAudiobitrate(item1.getAudiobitrate());
                                            item.setHeight(item1.getHeight());
                                            item.setItag(item1.getItag());
                                            item.setSize("0");
                                            item.setAudiourlsize(item1.getAudiourlsize());
                                            feedItems.add(item);
                                        }
                                    }

                                    txtheader.setText("Download");
                                    shimmer_view_container.stopShimmerAnimation();
                                    shimmer_view_container.setVisibility(View.GONE);
                                    list.setVisibility(View.VISIBLE);
                                    listAdapter.notifyDataSetChanged();
                                    calculatesize();
                                }
                            });


                        }
                    }).start();

                }
            }
        }.extract(link1, true, true);
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Tiwtter_Parser(final String twitlink) {
        try {

            txtheader.setText("Please wait...");
            shimmer_view_container.setVisibility(View.VISIBLE);
            shimmer_view_container.startShimmerAnimation();
            list.setVisibility(View.INVISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {


                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("URL", twitlink)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://twdown.net/download.php")
                            .post(body)
                            .build();

                    Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                   Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, final Response response) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        String result = response.body().string();
                                        Document doc = Jsoup.parse(result);
                                        Element table = doc.select("table").get(0); //select the first table.
                                        Elements rows = table.select("tr");

                                        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                                            Element row = rows.get(i);
                                            Elements cols = row.select("td");
                                            if(cols.get(1).html().equalsIgnoreCase("MP3"))
                                            {
                                                Document doc1 = Jsoup.parse(cols.get(2).html());
                                                Element link = doc1.select("a").first();
                                                String downlink = "https://twdown.net"+link.attr("href");

                                                if(!downlink.equalsIgnoreCase(""))
                                                {
                                                    Feed_Formats item=new Feed_Formats();
                                                    item.setUrl(downlink);
                                                    item.setVideosource("twitter");
                                                    item.setItag(0);
                                                    item.setType("MP3");

                                                    item.setSize("0");
                                                    item.setAudiourl("");
                                                    item.setAudiobitrate("128");
                                                    item.setAudiourlsize("");
                                                    item.setHeight(0);
                                                    feedItems.add(item);
                                                }
                                                else
                                                {
                                                    Feed_Formats item=new Feed_Formats();
                                                    item.setUrl("");
                                                    item.setVideosource("twitter");
                                                    item.setItag(0);
                                                    item.setSize("0");
                                                    item.setAudiourl("");
                                                    item.setAudiobitrate("");
                                                    item.setAudiourlsize("");
                                                    item.setHeight(0);
                                                    item.setType("");
                                                    feedItems.add(item);

                                                }

                                            }
                                            else
                                            {
                                                Document doc1 = Jsoup.parse(cols.get(3).html());
                                                Element link = doc1.select("a").first();
                                                String downlink = link.attr("href").replace("?tag=10","");

                                                if(!downlink.equalsIgnoreCase(""))
                                                {
                                                    Feed_Formats item=new Feed_Formats();
                                                    item.setUrl(downlink);
                                                    item.setVideosource("twitter");
                                                    item.setItag(0);
                                                    item.setType("MP4");
                                                    item.setSize("0");
                                                    item.setAudiourl("");
                                                    item.setAudiobitrate("");
                                                    item.setAudiourlsize("");
                                                    item.setHeight(0);
                                                    feedItems.add(item);

                                                }
                                                else
                                                {
                                                    Feed_Formats item=new Feed_Formats();
                                                    item.setUrl("");
                                                    item.setVideosource("twitter");
                                                    item.setItag(0);
                                                    item.setType("");
                                                    item.setSize("0");
                                                    item.setAudiourl("");
                                                    item.setAudiobitrate("");
                                                    item.setAudiourlsize("");
                                                    item.setHeight(0);
                                                    feedItems.add(item);

                                                }
                                            }


                                        }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtheader.setText("Download");
                                                shimmer_view_container.stopShimmerAnimation();
                                                shimmer_view_container.setVisibility(View.GONE);
                                                list.setVisibility(View.VISIBLE);
                                                listAdapter.notifyDataSetChanged();
                                                calculatesize();
                                            }
                                        });
                                    } catch (Exception a) {


                                        //Toasty.info(getApplicationContext(), "1"+Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    });

                }
            }).start();



        } catch (Exception a) {

        }
    }





    public void pintrest_parser(final String twitlink) {
        try {

            txtheader.setText("Please wait...");
            shimmer_view_container.setVisibility(View.VISIBLE);
            shimmer_view_container.startShimmerAnimation();
            list.setVisibility(View.INVISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {


                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("url", twitlink)
                            .build();
                    Request request = new Request.Builder()
                            .url("https://www.expertsphp.com/download.php")
                            .post(body)
                            .build();

                    Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                   // Toasty.info(getApplicationContext(), Temp.nointernet, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, final Response response) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        String result = response.body().string();
                                        Document doc = Jsoup.parse(result);
                                        Element table = doc.select("table").get(0); //select the first table.
                                        Elements rows = table.select("tr");

                                        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                                            Element row = rows.get(i);
                                            Elements cols = row.select("td");
                                            Document doc1 = Jsoup.parse(cols.get(0).html());
                                            Element link = doc1.select("a").first();
                                            String downlink = link.attr("href");

                                            String downname=downlink.substring(downlink.lastIndexOf('/')+1);
                                            if(!downlink.equalsIgnoreCase(""))
                                            {
                                                Feed_Formats item=new Feed_Formats();
                                                item.setUrl(downlink);
                                                item.setVideosource("pinterest");
                                                item.setItag(0);
                                                if(downname.toLowerCase().contains(".jpg") || downname.toLowerCase().contains(".png"))
                                                {
                                                    item.setType("JPG");
                                                }
                                                else if(downname.toLowerCase().contains(".gif"))
                                                {
                                                    item.setType("GIF");
                                                }
                                                else
                                                {
                                                    item.setType("MP4");
                                                }
                                                item.setSize("0");
                                                item.setAudiourl("");
                                                item.setAudiobitrate("128");
                                                item.setAudiourlsize("");
                                                item.setHeight(0);
                                                feedItems.add(item);
                                            }
                                            else
                                            {
                                                Feed_Formats item=new Feed_Formats();
                                                item.setUrl("");
                                                item.setVideosource("pinterest");
                                                item.setItag(0);
                                                item.setSize("0");
                                                item.setAudiourl("");
                                                item.setAudiobitrate("");
                                                item.setAudiourlsize("");
                                                item.setHeight(0);
                                                item.setType("");
                                                feedItems.add(item);

                                            }
                                        }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtheader.setText("Download");
                                                shimmer_view_container.stopShimmerAnimation();
                                                shimmer_view_container.setVisibility(View.GONE);
                                                list.setVisibility(View.VISIBLE);
                                                listAdapter.notifyDataSetChanged();
                                                calculatesize();
                                            }
                                        });
                                    } catch (Exception a) {


                                        //Toasty.info(getApplicationContext(), "1"+Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    });

                }
            }).start();



        } catch (Exception a) {

        }
    }


    public void instagram_parser(final String link) {
        try {

            txtheader.setText("Please wait...");
            shimmer_view_container.setVisibility(View.VISIBLE);
            shimmer_view_container.startShimmerAnimation();
            list.setVisibility(View.INVISIBLE);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        Request.Builder builder = new Request.Builder();
                        builder.url(link);
                        Request request = builder.build();
                        Response response = client.newCall(request).execute();
                        String datas = response.body().string();
                        if (datas.contains("Page Not Found &bull; Instagram")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Feed_Formats item=new Feed_Formats();
                                    item.setUrl("");
                                    item.setVideosource("insta");
                                    item.setItag(0);
                                    item.setSize("0");
                                    item.setAudiourl("");
                                    item.setAudiobitrate("");
                                    item.setAudiourlsize("");
                                    item.setHeight(0);
                                    item.setType("");
                                    feedItems.add(item);
                                }
                            });
                        } else {
                            JSON json = new JSON(datas);
                            JSON js = json.key("graphql");
                            JSON json1 = js.key("shortcode_media");
                            String types = json1.key("__typename").toString();
                            String downname = "",downloadurl="",savelocation="";
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            if (types.equalsIgnoreCase("GraphImage")) {
                                downloadurl  = json1.key("display_url").toString();
                                downname = "INSTA_" + ctime + ".jpg";
                            } else if (types.equalsIgnoreCase("GraphVideo")) {
                                downloadurl = json1.key("video_url").toString();
                                downname = "INSTA_" + ctime + ".mp4";
                            }


                            if(!downloadurl .equalsIgnoreCase(""))
                            {
                                Feed_Formats item=new Feed_Formats();
                                item.setUrl(downloadurl);
                                item.setVideosource("insta");
                                item.setItag(0);
                                if(downname.toLowerCase().contains(".jpg") || downname.toLowerCase().contains(".png"))
                                {
                                    item.setType("JPG");
                                }
                                else if(downname.toLowerCase().contains(".gif"))
                                {
                                    item.setType("GIF");
                                }
                                else
                                {
                                    item.setType("MP4");
                                }
                                item.setSize("0");
                                item.setAudiourl("");
                                item.setAudiobitrate("128");
                                item.setAudiourlsize("");
                                item.setHeight(0);
                                feedItems.add(item);
                            }
                            else
                            {
                                Feed_Formats item=new Feed_Formats();
                                item.setUrl("");
                                item.setVideosource("insta");
                                item.setItag(0);
                                item.setSize("0");
                                item.setAudiourl("");
                                item.setAudiobitrate("");
                                item.setAudiourlsize("");
                                item.setHeight(0);
                                item.setType("");
                                feedItems.add(item);

                            }

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtheader.setText("Download");
                                shimmer_view_container.stopShimmerAnimation();
                                shimmer_view_container.setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                                listAdapter.notifyDataSetChanged();
                                calculatesize();
                            }
                        });
                    }
                    catch (Exception a)
                    {

                    }
                }
            }).start();



        } catch (Exception a) {

        }
    }



    public void sharechat_parser(final String link) {
        try {

            txtheader.setText("Please wait...");
            shimmer_view_container.setVisibility(View.VISIBLE);
            shimmer_view_container.startShimmerAnimation();
            list.setVisibility(View.INVISIBLE);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        final StringBuilder builder = new StringBuilder();

                        URL url = new URL(link);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                        url.openStream()));

                        String line = null;
                        while ((line = in.readLine()) != null) {
                            builder.append(line);
                        }

                        String datas = builder.toString();
                        String newurl="";
                        String downname="";
                        if (datas.contains("compressedVideoUrl") && datas.contains("videoCompressedSize")) {
                            int index = datas.indexOf("compressedVideoUrl");
                            int index1 = datas.indexOf("videoCompressedSize");
                            String a1 = datas.substring(index + 21, index1 - 3);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            downname = "SC_" + ctime + ".mp4";
                            String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                            String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                            newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                            //download(path,newurl,downname,"SC",ctime);

                        } else if (datas.contains("og:video:url") && datas.contains("_c_v.mp4")) {
                            int index = datas.indexOf("og:video:url");
                            int index1 = datas.indexOf("_c_v.mp4");
                            String a1 = datas.substring(index + 23, index1) + "_c_v.mp4";
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            downname = "SC_" + ctime + ".mp4";
                            String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                            String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                            newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                            //download(path,newurl,downname,"SC",ctime);
                        } else if (datas.contains("og:video:url") && datas.contains("compressed_vat.mp4")) {
                            int index = datas.indexOf("og:video:url");
                            int index1 = datas.indexOf("compressed_vat.mp4");
                            String a1 = datas.substring(index + 23, index1) + "compressed_vat.mp4";
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            downname = "SC_" + ctime + ".mp4";
                            String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                            String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                            newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                            //  download(path,newurl,downname,"SC",ctime);
                        } else if (datas.contains("og:video:url") && datas.contains("compressed.mp4")) {
                            int index = datas.indexOf("og:video:url");
                            int index1 = datas.indexOf("compressed.mp4");
                            String a1 = datas.substring(index + 23, index1) + "compressed.mp4";

                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            downname = "SC_" + ctime + ".mp4";
                            String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                            String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                            newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                            //download(path,newurl,downname,"SC",ctime);
                        } else if (datas.contains("og:video:url") && datas.contains(".mp4")) {
                            int index = datas.indexOf("og:video:url");
                            int index1 = datas.indexOf(".mp4");
                            String a1 = datas.substring(index + 23, index1) + ".mp4";

                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            downname = "SC_" + ctime + ".mp4";
                            String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                            String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                            newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                            //download(path,newurl,downname,"SC",ctime);
                        } else if (datas.contains("og:image") && !datas.contains("compressed_thumb.jpeg")) {
                            int index = datas.indexOf("og:image");
                            int index1 = datas.indexOf("da:req:type");
                            String a1 = datas.substring(index + 19, index1 - 34);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            downname = "SC_" + ctime + ".jpg";
                            String path = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                            String newdownpath = URLEncoder.encode(a1.substring(a1.lastIndexOf('/') + 1), "UTF-8");
                            newurl = a1.substring(0, a1.lastIndexOf('/') + 1) + newdownpath;
                            //  download(path,newurl,downname,"SC",ctime);
                        }

                        if(!newurl.equalsIgnoreCase(""))
                        {
                            Feed_Formats item=new Feed_Formats();
                            item.setUrl(newurl);
                            item.setVideosource("sharechat");
                            item.setItag(0);
                            if(downname.toLowerCase().contains(".jpg") || downname.toLowerCase().contains(".png"))
                            {
                                item.setType("JPG");
                            }
                            else if(downname.toLowerCase().contains(".gif"))
                            {
                                item.setType("GIF");
                            }
                            else
                            {
                                item.setType("MP4");
                            }
                            item.setSize("0");
                            item.setAudiourl("");
                            item.setAudiobitrate("128");
                            item.setAudiourlsize("");
                            item.setHeight(0);
                            feedItems.add(item);
                        }
                        else
                        {
                            Feed_Formats item=new Feed_Formats();
                            item.setUrl("");
                            item.setVideosource("sharechat");
                            item.setItag(0);
                            item.setSize("0");
                            item.setAudiourl("");
                            item.setAudiobitrate("");
                            item.setAudiourlsize("");
                            item.setHeight(0);
                            item.setType("");
                            feedItems.add(item);

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtheader.setText("Download");
                                shimmer_view_container.stopShimmerAnimation();
                                shimmer_view_container.setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                                listAdapter.notifyDataSetChanged();
                                calculatesize();
                            }
                        });
                    }
                    catch (Exception a)
                    {

                    }
                }
            }).start();



        } catch (Exception a) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try
        {
            count = 0;
            try {
                adView1.loadAd(adRequest);
            } catch (Exception e) {

            }
        }
        catch (Exception a)
        {

        }
    }

    public void download(String path, String downurl, String downname1, String appid,boolean hide,String audioreq,String ctime,String isshow) {
        try {


            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(downurl));
            req.setDestinationUri(Uri.parse(path));
            req.setTitle(downname1.replace("m4a","mp4"));

            if (hide) {
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                req.setVisibleInDownloadsUi(false);
            }
            else
            {
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                req.setVisibleInDownloadsUi(true);
            }

            long downloadId = ((DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE)).enqueue(req);
            IntentFilter filters = new IntentFilter();

            filters.addAction("extra_click_download_ids");
            filters.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED");
            filters.addAction("android.intent.action.DOWNLOAD_COMPLETE");
            registerReceiver(new NotiHandler(), new IntentFilter(filters));
            try {

                db.add_downloadList(downloadId+"", appid, downname1, path, downurl, "0",audioreq,ctime,isshow);
                if(!hide)
                {
                    playmusic();
                }
            } catch (Exception e) {

            }
        } catch (Exception e2) {

        }
    }
    public void playmusic() {
        mp = MediaPlayer.create(getApplicationContext(), R.raw.downtone);
        mp.setVolume(0.1f, 0.1f);
        mp.start();
    }

    public void exitform()
    {
        finish();
        return;
    }
    public void calculatesize()
    {

        for (int i = 0; i < list.getCount(); i++) {
            Feed_Formats item1=feedItems.get(i);
            String url=item1.getUrl();
            final int pos=i;


            if(item1.getVideosource().equalsIgnoreCase("tiktok") && item1.getType().equalsIgnoreCase("MP4 HD"))
            {
                Feed_Formats item=new Feed_Formats();
                item.setSize("--");
                item.setUrl(item1.getUrl());
                item.setType(item1.getType());
                item.setVideosource(item1.getVideosource());
                item.setAudiourl(item1.getAudiourl());
                item.setAudiobitrate(item1.getAudiobitrate());
                item.setHeight(item1.getHeight());
                item.setItag(item1.getItag());
                item.setAudiourlsize(item1.getAudiourlsize());
                feedItems.remove(pos);
                feedItems.add(pos,item);
                listAdapter.notifyDataSetChanged();
            }
            else
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            int postiion=pos;
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(url).head().build();
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Feed_Formats item=new Feed_Formats();
                                        item.setSize(response.header("Content-Length"));
                                        item.setUrl(item1.getUrl());
                                        item.setType(item1.getType());
                                        item.setVideosource(item1.getVideosource());
                                        item.setAudiourl(item1.getAudiourl());
                                        item.setAudiobitrate(item1.getAudiobitrate());
                                        item.setHeight(item1.getHeight());
                                        item.setItag(item1.getItag());
                                        item.setAudiourlsize(item1.getAudiourlsize());
                                        feedItems.remove(postiion);
                                        feedItems.add(postiion,item);
                                        listAdapter.notifyDataSetChanged();
                                    }
                                });


                            }
                        } catch (Exception a) {

                        }

                    }
                }).start();
            }

        }


    }
}
