package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.footballstatus.ConnectionDetecter;
import com.footballstatus.R;
import com.footballstatus.Temp;
import com.footballstatus.UserDatabaseHandler;
import com.footballstatus.VideoPlayer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd.OnUnifiedNativeAdLoadedListener;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;

import data.videoList_Feed;
import es.dmoral.toasty.Toasty;

public class Video_List_Adapter extends BaseAdapter {

    public Activity activity;
    ConnectionDetecter cd;
    public Context context;
    public UserDatabaseHandler udb;
    Typeface face;
    Typeface face1;

    public List<videoList_Feed> feedItems;
    private LayoutInflater inflater;
    UnifiedNativeAd nativeAd;
    ProgressDialog pd;

    String[] PERMISSIONS = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE
    };

    public Video_List_Adapter(Activity activity2, List<videoList_Feed> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        udb = new UserDatabaseHandler(context);
        cd=new ConnectionDetecter(context);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2;
        final int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.video_customlayout, null);
        } else {
            convertView2 = convertView;
        }
        TextView title = (TextView) convertView2.findViewById(R.id.title);
        ImageView img = (ImageView) convertView2.findViewById(R.id.img);
        ImageView playicon = (ImageView) convertView2.findViewById(R.id.playicon);
        RelativeLayout layout = (RelativeLayout) convertView2.findViewById(R.id.layout);
        TextView time = (TextView) convertView2.findViewById(R.id.time);
        ImageView downloadicon = (ImageView) convertView2.findViewById(R.id.downloadicon);
        final FrameLayout adplaceholder = (FrameLayout) convertView2.findViewById(R.id.adplaceholder);
        final videoList_Feed item = (videoList_Feed) feedItems.get(i);
        title.setTypeface(face1);
        time.setTypeface(face1);
        time.setText(item.getRegdate());
        String[] k = item.getDim().split("x");
        float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(udb.getscreenwidth()).floatValue() - 40.0f);
        img.getLayoutParams().height = Math.round(calheight);
        Glide.with(context).load(item.getImgsrc()).transition(DrawableTransitionOptions.withCrossFade()).into(img);
        if (item.getIsadshow()==0) {
            adplaceholder. setVisibility(View.GONE);
        } else {
            adplaceholder. setVisibility(View.VISIBLE);
            Builder builder = new Builder((Context) activity, "ca-app-pub-5517777745693327/9083774607");
            builder.forUnifiedNativeAd(new OnUnifiedNativeAdLoadedListener() {
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    if (nativeAd != null) {
                        nativeAd.destroy();
                    }
                    nativeAd = unifiedNativeAd;
                    UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified, null);
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    adplaceholder.removeAllViews();
                    adplaceholder.addView(adView);
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
            builder.withAdListener(new AdListener() {
                public void onAdFailedToLoad(int errorCode) {
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
        if (item.getMediatype().equalsIgnoreCase("1")) {
            playicon.setVisibility(View.VISIBLE);
        } else if (item.getMediatype().equalsIgnoreCase("2")) {
            playicon. setVisibility(View.GONE);
        }
        if (item.getTitle().equalsIgnoreCase("NA") || item.getTitle().equalsIgnoreCase("")) {
            title. setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
            title.setText(item.getTitle());
        }
        downloadicon.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                videoList_Feed item = (videoList_Feed) feedItems.get(i);
                if (item.getMediatype().equalsIgnoreCase("1")) {

                    if (item.getTitle().equalsIgnoreCase("NA") || item.getTitle().equalsIgnoreCase("")) {
                       Temp.videodownslink = System.currentTimeMillis()+".mp4";
                    } else {
                        Temp.videodownslink = item.getTitle().replaceAll(" ", "_")+".mp4";
                    }
                    download(item.getVideosrc());
                }
                else
                {
                    if (item.getTitle().equalsIgnoreCase("NA") || item.getTitle().equalsIgnoreCase("")) {
                        Temp.videodownslink = System.currentTimeMillis()+".jpg";
                    } else {
                        Temp.videodownslink = item.getTitle().replaceAll(" ", "_")+".jpg";
                    }
                    download(item.getImgsrc());
                }
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (item.getMediatype().equalsIgnoreCase("1")) {
                    Temp.isvideo=1;
                    Temp.videolinks = ((videoList_Feed) feedItems.get(i)).getVideosrc();
                    Intent i = new Intent(context, VideoPlayer.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
        return convertView2;
    }

    public void showalert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < ((long) unit)) {
            StringBuilder sb = new StringBuilder();
            sb.append(bytes);
            sb.append(" B");
            return sb.toString();
        }
        int exp = (int) (Math.log((double) bytes) / Math.log((double) unit));
        StringBuilder sb2 = new StringBuilder();
        sb2.append((si ? "kMGTPE" : "KMGTPE").charAt(exp - 1));
        sb2.append(si ? "" : "i");
        String pre = sb2.toString();
        double d = (double) bytes;
        double pow = Math.pow((double) unit, (double) exp);
        Double.isNaN(d);
        return String.format("%.1f %sB", new Object[]{Double.valueOf(d / pow), pre});
    }

    public void addvideo(String sdlink, String sdsize, String hdlink, String hdsize) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.downloadalert);
        View findViewById = dialog.findViewById(R.id.sdicon);
        View findViewById2 = dialog.findViewById(R.id.hdicon);
        TextView sd = (TextView) dialog.findViewById(R.id.sd);
        TextView hd = (TextView) dialog.findViewById(R.id.hd);
        RelativeLayout first = (RelativeLayout) dialog.findViewById(R.id.first);
        RelativeLayout second = (RelativeLayout) dialog.findViewById(R.id.second);
        StringBuilder sb = new StringBuilder();
        sb.append("SD MP4(");
        sb.append(sdsize);
        sb.append(")");
        sd.setText(sb.toString());
        if (hdsize.contains("0.00 Bytes")) {
            second. setVisibility(View.GONE);
        } else {
            second.setVisibility(View.VISIBLE);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("HD MP4(");
            sb2.append(hdsize);
            sb2.append(")");
            hd.setText(sb2.toString());
        }
        first.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        second.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void download(String downlink1) {
        try {

            if (!hasPermissions(context, PERMISSIONS)) {
                ActivityCompat.requestPermissions(activity,PERMISSIONS, 1);

            }
            else
            {
                Request request = new Request(Uri.parse(downlink1));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(1);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Temp.videodownslink);
                ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
                Toasty.success(context, (CharSequence) "Download Started", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
         // Log.w("Gdsd",Log.getStackTraceString(e));
        }
    }

    public String getfilesize(String url1) {
        long file_size = 0;
        try {
            URLConnection urlConnection = new URL(url1).openConnection();
            urlConnection.connect();
            file_size = (long) urlConnection.getContentLength();
        } catch (Exception e) {
        }
        return formatFileSize(file_size);
    }

    public static String formatFileSize(long size) {
        long j = size;
        double b = (double) j;
        double d = (double) j;
        Double.isNaN(d);
        double k = d / 1024.0d;
        double d2 = (double) j;
        Double.isNaN(d2);
        double m = (d2 / 1024.0d) / 1024.0d;
        double d3 = (double) j;
        Double.isNaN(d3);
        double g = ((d3 / 1024.0d) / 1024.0d) / 1024.0d;
        double d4 = (double) j;
        Double.isNaN(d4);
        double t = (((d4 / 1024.0d) / 1024.0d) / 1024.0d) / 1024.0d;
        DecimalFormat dec = new DecimalFormat("0.00");
        if (t > 1.0d) {
            return dec.format(t).concat(" TB");
        }
        if (g > 1.0d) {
            return dec.format(g).concat(" GB");
        }
        if (m > 1.0d) {
            return dec.format(m).concat(" MB");
        }
        if (k > 1.0d) {
            return dec.format(k).concat(" KB");
        }
        return dec.format(b).concat(" Bytes");
    }


    public void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd2, UnifiedNativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd2.getHeadline());
        if (nativeAd2.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd2.getBody());
        }
        if (nativeAd2.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd2.getCallToAction());
        }
        if (nativeAd2.getIcon() == null) {
            adView.getIconView(). setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd2.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd2.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd2.getPrice());
        }
        if (nativeAd2.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd2.getStore());
        }
        if (nativeAd2.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd2.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd2.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd2.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd2);
        VideoController vc = nativeAd2.getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoLifecycleCallbacks() {
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {

        try
        {
            if (!(context == null || permissions == null)) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != 0) {
                        return false;
                    }
                }
            }

        }
        catch (Exception a)
        {
            //Log.w("Athiss",Log.getStackTraceString(a));
        }
        return true;
    }

}
