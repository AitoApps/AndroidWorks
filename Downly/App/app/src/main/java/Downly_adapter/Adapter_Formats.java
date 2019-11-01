package Downly_adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.downly_app.R;
import com.downly_app.Splitter_WP;
import com.downly_app.Temp;
import com.downly_app.YoutubeFormat_Window;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Downly_Data.Feed_CutVideoList;
import Downly_Data.Feed_Formats;
import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Adapter_Formats extends BaseAdapter {
    public Activity activity;
    public Context context;
    Typeface face,face1;
    public List<Feed_Formats> feedItems;
    private LayoutInflater inflater;
    public Adapter_Formats(Activity activity2, List<Feed_Formats> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        face = Typeface.createFromAsset(context.getAssets(), "proximanormal.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }

    public int getCount() {
        return feedItems.size();
    }

    public Object getItem(int location) {
        return feedItems.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_filesize, null);
        }
        ImageView icon=(ImageView)convertView.findViewById(R.id.icon);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        TextView filesize=convertView.findViewById(R.id.size);
        TextView filetype=convertView.findViewById(R.id.filetype);
        TextView fileformat=convertView.findViewById(R.id.fileformat);
        CircularProgressView progressBar=convertView.findViewById(R.id.progressBar);
        Feed_Formats item = (Feed_Formats) feedItems.get(position);

        filetype.setTypeface(face1);
        fileformat.setTypeface(face);
        filesize.setTypeface(face1);

        if(item.getType().equalsIgnoreCase("Audio")||item.getType().equalsIgnoreCase("MP3")){
            fileformat.setText("Audio");
            filetype.setText(item.getAudiobitrate()+" kb/s");
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.musicicon));
        }
        if(item.getType().equalsIgnoreCase("JPG"))
        {
            filetype.setText(item.getType());
            fileformat.setText("JPG");
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.imgicon));
        }
        else
        {
            filetype.setText(item.getType());
            fileformat.setText("MP4");
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.videoicon));
        }
        if(item.getSize().equalsIgnoreCase("0") && !item.getUrl().equalsIgnoreCase(""))
        {
            filesize.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        }
        else
        {

            if(!item.getUrl().equalsIgnoreCase(""))
            {
                icon.setVisibility(View.VISIBLE);
                filesize.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);


                    if(item.getAudiourlsize().equalsIgnoreCase(""))
                    {
                        if(item.getSize().equalsIgnoreCase("--"))
                        {
                            filesize.setText(item.getSize());
                        }
                        else
                        {
                            filesize.setText(getFileSize(Long.parseLong(item.getSize())));
                        }

                    }
                    else
                    {
                        if(item.getSize().equalsIgnoreCase("--"))
                        {
                            filesize.setText(item.getSize());
                        }
                        else
                        {
                            filesize.setText(getFileSize(Long.parseLong(item.getSize())+Long.parseLong(item.getAudiourlsize())));
                        }

                    }
            }
            else
            {

                icon.setVisibility(View.GONE);
                filetype.setText("Sorry ! Unable to Download");
                fileformat.setText("");
                filesize.setText("");
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Feed_Formats item = (Feed_Formats) feedItems.get(position);

                if(!item.getUrl().equalsIgnoreCase("")) {
                    if (item.getVideosource().equalsIgnoreCase("youtube")) {
                        if (!item.getAudiourl().equalsIgnoreCase("") && item.getSize().equalsIgnoreCase("0")) {
                            Toasty.info(context, "Please click only after file size is loaded", Toast.LENGTH_SHORT).show();
                        } else if (!item.getAudiourl().equalsIgnoreCase("") && !item.getSize().equalsIgnoreCase("0")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            String audioname = "YT_" + ctime + ".m4a";
                            String videoname1 = "YT_" + ctime + ".mp4";

                            String audiolocation = "file://" + Environment.getExternalStorageDirectory() + "/YoutubeDownloads/" + audioname;
                            String videolocation = "file://" + Environment.getExternalStorageDirectory() + "/YoutubeDownloads/" + videoname1;
                            YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                            if (Long.parseLong(item.getAudiourlsize()) > Long.parseLong(item.getSize())) {
                                yf.download(audiolocation, item.getAudiourl(), audioname, "YT", false, "yes", ctime,"1");
                                yf.download(videolocation, item.getUrl(), videoname1, "YT", true, "yes", ctime,"0");
                            } else {
                                yf.download(audiolocation, item.getAudiourl(), audioname, "YT", true, "yes", ctime,"0");
                                yf.download(videolocation, item.getUrl(), videoname1, "YT", false, "yes", ctime,"1");
                            }
                        } else {
                            //normal download
                            YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                            String ctime = sdf.format(new Date());
                            String downname = "";
                            if (item.getType().equalsIgnoreCase("Audio") || item.getType().equalsIgnoreCase("MP3")) {
                                downname = "YT_" + ctime + ".MP3";
                            } else {
                                downname = "YT_" + ctime + ".MP4";
                            }
                            String savelocation = "file://" + Environment.getExternalStorageDirectory() + "/YoutubeDownloads/" + downname;
                            yf.download(savelocation, item.getUrl(), downname, "YT_", false, "no", ctime,"1");
                        }
                    }
                    if (item.getVideosource().equalsIgnoreCase("facebook")) {
                        YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "";
                        if (item.getType().equalsIgnoreCase("Audio") || item.getType().equalsIgnoreCase("MP3")) {
                            downname = "FB_" + ctime + ".MP3";
                        } else {
                            downname = "FB_" + ctime + ".MP4";
                        }

                        String savelocation = "file://" + Environment.getExternalStorageDirectory() + "/FBDownloads/" + downname;

                        yf.download(savelocation, item.getUrl(), downname, "FB", false, "no", ctime,"1");
                    }
                    else if(item.getVideosource().equalsIgnoreCase("tiktok")) {
                        YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "";
                        if (item.getType().equalsIgnoreCase("Audio") || item.getType().equalsIgnoreCase("MP3")) {
                            downname = "TIKS_" + ctime + ".MP3";
                        } else {
                            downname = "TIKS_" + ctime + ".MP4";
                        }
                        String savelocation = "file://" + Environment.getExternalStorageDirectory() + "/TikTokDownloads/" + downname;
                        yf.download(savelocation, item.getUrl(), downname, "TIKS_",false,"no",ctime,"1");
                    }
                    else if(item.getVideosource().equalsIgnoreCase("twitter")) {
                        YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "";
                        if (item.getType().equalsIgnoreCase("Audio") || item.getType().equalsIgnoreCase("MP3")) {
                            downname = "TW_" + ctime + ".MP3";
                        } else {
                            downname = "TW_" + ctime + ".MP4";
                        }
                        String savelocation = "file://" + Environment.getExternalStorageDirectory() + "/TwitterDownloads/" + downname;
                        yf.download(savelocation, item.getUrl(), downname, "TW_",false,"no",ctime,"1");
                    }

                    else if(item.getVideosource().equalsIgnoreCase("sharechat")) {
                        YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "";
                        if (item.getType().equalsIgnoreCase("JPG")) {
                            downname = "SC_" + ctime + ".jpg";
                        }
                        else if(item.getType().equalsIgnoreCase("GIF"))
                        {
                            downname = "SC_" + ctime + ".gif";
                        }
                        else {
                            downname = "SC_" + ctime + ".mp4";
                        }
                        String savelocation = "file://" + Environment.getExternalStorageDirectory() + "/SharechatDownloads/" + downname;
                        yf.download(savelocation, item.getUrl(), downname, "SC_",false,"no",ctime,"1");
                    }

                    else if(item.getVideosource().equalsIgnoreCase("insta")) {
                        YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "";
                        if (item.getType().equalsIgnoreCase("JPG")) {
                            downname = "INSTA_" + ctime + ".jpg";
                        }
                        else if(item.getType().equalsIgnoreCase("GIF"))
                        {
                            downname = "INSTA_" + ctime + ".gif";
                        }
                        else {
                            downname = "INSTA_" + ctime + ".mp4";
                        }
                        String savelocation = "file://" + Environment.getExternalStorageDirectory() + "/InstaDownloads/" + downname;
                        yf.download(savelocation, item.getUrl(), downname, "INSTA_",false,"no",ctime,"1");
                    }
                    else if(item.getVideosource().equalsIgnoreCase("pinterest")) {
                        YoutubeFormat_Window yf = (YoutubeFormat_Window) activity;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
                        String ctime = sdf.format(new Date());
                        String downname = "";
                        if (item.getType().equalsIgnoreCase("JPG")) {
                            downname = "PIN_" + ctime + ".jpg";
                        }
                        else if(item.getType().equalsIgnoreCase("GIF"))
                        {
                            downname = "PIN_" + ctime + ".gif";
                        }
                        else {
                            downname = "PIN_" + ctime + ".mp4";
                        }
                        String savelocation = "file://" + Environment.getExternalStorageDirectory() + "/PinterestDownloads/" + downname;
                        yf.download(savelocation, item.getUrl(), downname, "PIN_",false,"no",ctime,"1");
                    }
                    YoutubeFormat_Window yt=(YoutubeFormat_Window)activity;
                    yt.exitform();

                }

             }
        });

        return convertView;
    }


    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


}
