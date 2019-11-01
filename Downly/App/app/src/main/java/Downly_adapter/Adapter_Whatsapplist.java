package Downly_adapter;

import Downly_Data.Feed_WP;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.downly_app.DataBase;
import com.downly_app.InternetConncetivity;
import com.downly_app.R;
import com.downly_app.Status_WP;
import java.io.File;
import java.util.List;

public class Adapter_Whatsapplist extends BaseAdapter {
    public Activity activity;
    public InternetConncetivity cd;
    private Context context;
    DataBase db;
    Typeface face;
    private List<Feed_WP> feedItems;
    private LayoutInflater inflater;
    DownloadManager manager;
    ProgressDialog pd;
    int pos = 0;

    public Adapter_Whatsapplist(Activity activity2, List<Feed_WP> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new InternetConncetivity(context);
        pd = new ProgressDialog(activity2);
        db = new DataBase(context);
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        face = Typeface.createFromAsset(context.getAssets(), "commonfont.otf");
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

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_list_wp, null);
            }
            ImageView image = (ImageView) convertView.findViewById(R.id.image);
            final VideoView video = (VideoView) convertView.findViewById(R.id.video);
            final ImageView playvideo = (ImageView) convertView.findViewById(R.id.playvideo);
            final ImageView pausevideo = (ImageView) convertView.findViewById(R.id.pausevideo);
            ImageView downloadicon = (ImageView) convertView.findViewById(R.id.downloadicon);
            ImageView shareicon = (ImageView) convertView.findViewById(R.id.shareicon);
            final Feed_WP item = (Feed_WP) feedItems.get(position);
            if (item.getFilepath().endsWith(".mp4")) {
                image.setVisibility(View.GONE);
                video.setVisibility(View.VISIBLE);
                playvideo.setVisibility(View.VISIBLE);
                pausevideo.setVisibility(View.INVISIBLE);
                video.setVideoURI(Uri.parse(item.getFilepath()));
                video.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        video.seekTo(100);
                    }
                });
                playvideo.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        video.start();
                        pausevideo.setVisibility(View.VISIBLE);
                        playvideo.setVisibility(View.INVISIBLE);
                    }
                });
                pausevideo.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        video.pause();
                        pausevideo.setVisibility(View.INVISIBLE);
                        playvideo.setVisibility(View.VISIBLE);
                    }
                });
            } else if (item.getFilepath().endsWith(".jpg")) {
                image.setVisibility(View.VISIBLE);
                video.setVisibility(View.GONE);
                playvideo.setVisibility(View.GONE);
                pausevideo.setVisibility(View.GONE);
                image.setImageBitmap(BitmapFactory.decodeFile(item.getFilepath()));
            } else if (item.getFilepath().endsWith(".gif")) {
                image.setVisibility(View.VISIBLE);
                video.setVisibility(View.GONE);
                playvideo.setVisibility(View.GONE);
                pausevideo.setVisibility(View.GONE);
                Glide.with(context).load(Uri.fromFile(new File(item.getFilepath()))).into(image);
            }
            downloadicon.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        ((Status_WP)activity).savevideo(new File(item.getFilepath()));
                    } catch (Exception e) {
                    }
                }
            });
            shareicon.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        ((Status_WP)activity).share_to_WhatsApp(new File(item.getFilepath()));
                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e) {

          //  Toast.makeText(context, Log.getStackTraceString(e),Toast.LENGTH_LONG).show();
        }
        return convertView;
    }
}
