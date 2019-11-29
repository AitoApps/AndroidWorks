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
import android.os.Environment;
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
import com.hellokhd.ConnectionDetecter;
import com.hellokhd.R;
import com.hellokhd.Temp;
import com.hellokhd.UserDatabaseHandler;
import com.hellokhd.Video_Player;

import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

import data.Instagram_Feed;
import es.dmoral.toasty.Toasty;

public class Instagram_List_Adapter extends BaseAdapter {

    public Activity activity;
    ConnectionDetecter cd;
    public Context context;
    public UserDatabaseHandler udb;
    Typeface face;
    Typeface face1;

    public List<Instagram_Feed> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;

    String[] PERMISSIONS = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_WIFI_STATE
    };

    public Instagram_List_Adapter(Activity activity2, List<Instagram_Feed> feedItems2) {
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
            convertView2 = inflater.inflate(R.layout.instagram_customlayout, null);
        } else {
            convertView2 = convertView;
        }
        TextView title = (TextView) convertView2.findViewById(R.id.title);
        ImageView img = (ImageView) convertView2.findViewById(R.id.img);
        ImageView playicon = (ImageView) convertView2.findViewById(R.id.playicon);
        RelativeLayout layout = (RelativeLayout) convertView2.findViewById(R.id.layout);

        ImageView downloadicon = (ImageView) convertView2.findViewById(R.id.downloadicon);
        final Instagram_Feed item = (Instagram_Feed) feedItems.get(i);
        title.setTypeface(face1);
        String[] k = item.getDim().split("x");
        float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(udb.getscreenwidth()).floatValue() - 40.0f);
        img.getLayoutParams().height = Math.round(calheight);
        Glide.with(context).load(item.getUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(img);
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
                Instagram_Feed item = (Instagram_Feed) feedItems.get(i);
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
                    download(item.getUrl());
                }
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                if (item.getMediatype().equalsIgnoreCase("1")) {
                    Temp.isvideo=1;
                    Temp.videolinks = ((Instagram_Feed) feedItems.get(i)).getVideosrc();
                    Intent i = new Intent(context, Video_Player.class);
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
