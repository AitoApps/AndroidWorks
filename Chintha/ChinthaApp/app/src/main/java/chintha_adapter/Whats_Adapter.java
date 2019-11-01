package chintha_adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.suhi_chintha.HeartOf_App;
import com.suhi_chintha.R;

import java.io.File;
import java.util.List;

import chintha_data.Whats_Feed;

public class Whats_Adapter extends BaseAdapter {

    public Activity activity;
    private Context context;
    private List<Whats_Feed> dataItems;
    Typeface face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/common_heading.otf");
    private LayoutInflater inflater;
    DownloadManager manager = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE));
    ProgressDialog pd;

    public Whats_Adapter(Activity activity2, List<Whats_Feed> dataItems2) {
        activity = activity2;
        dataItems = dataItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
    }

    public int getCount() {
        return dataItems.size();
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public Object getItem(int location) {
        return dataItems.get(location);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.wplist_custom, null);
            }
            AdView adView = (AdView) convertView.findViewById(R.id.adView1);
            ImageView img = (ImageView) convertView.findViewById(R.id.img);
            final VideoView vid = (VideoView) convertView.findViewById(R.id.video);
            ImageView downicon = (ImageView) convertView.findViewById(R.id.downloadicon);
            ImageView share = (ImageView) convertView.findViewById(R.id.shareicon);
            final ImageView pause = (ImageView) convertView.findViewById(R.id.pausevideo);
            final ImageView play = (ImageView) convertView.findViewById(R.id.videoplay);
            final Whats_Feed item = (Whats_Feed) dataItems.get(position);
            if (item.get_showads().equalsIgnoreCase("1")) {
                adView.setVisibility(View.VISIBLE);
                adView.loadAd(new Builder().build());
            } else {
                adView. setVisibility(View.GONE);
            }
            if (item.getfpath().endsWith(".mp4")) {
                img. setVisibility(View.GONE);
                pause.setVisibility(View.INVISIBLE);
                vid.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                vid.setVideoURI(Uri.parse(item.getfpath()));
                play.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        vid.start();
                        pause.setVisibility(View.VISIBLE);
                        play.setVisibility(View.INVISIBLE);
                    }
                });
                vid.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        vid.seekTo(100);
                    }
                });
                pause.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        vid.pause();
                        pause.setVisibility(View.INVISIBLE);
                        play.setVisibility(View.VISIBLE);
                    }
                });
            } else if (item.getfpath().endsWith(".jpg")) {
                img.setVisibility(View.VISIBLE);
                vid. setVisibility(View.GONE);
                play. setVisibility(View.GONE);
                pause. setVisibility(View.GONE);
                img.setImageBitmap(BitmapFactory.decodeFile(item.getfpath()));
            } else if (item.getfpath().endsWith(".gif")) {
                img.setVisibility(View.VISIBLE);
                vid. setVisibility(View.GONE);
                play. setVisibility(View.GONE);
                pause. setVisibility(View.GONE);
                Glide.with(context).load(Uri.fromFile(new File(item.getfpath()))).into(img);
            }
            share.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        ((HeartOf_App) activity).wpshare(new File(item.getfpath()));
                    } catch (Exception e) {
                    }
                }
            });
            downicon.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        ((HeartOf_App) activity).videosave(new File(item.getfpath()));
                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e) {
        }
        return convertView;
    }
}
