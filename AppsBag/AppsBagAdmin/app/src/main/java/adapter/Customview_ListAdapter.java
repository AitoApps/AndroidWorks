package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsbag_admin.Add_Apps;
import com.appsbag_admin.Add_Video;
import com.appsbag_admin.Add_View;
import com.appsbag_admin.ConnectionDetecter;
import com.appsbag_admin.DatabaseHandler;
import com.appsbag_admin.R;
import com.appsbag_admin.Temp;
import com.appsbag_admin.Video_List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import data.CustomViewlist_FeedItem;
import data.VideoList_FeedItem;

public class Customview_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    Typeface face;
    public List<CustomViewlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String pcatid = "";
    ProgressDialog pd;
    int pos = 0;
    public DatabaseHandler db;
    public Customview_ListAdapter(Activity activity2, List<CustomViewlist_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db=new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
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
            convertView = inflater.inflate(R.layout.custom_views, null);
        }
        TextView title= (TextView) convertView.findViewById(R.id.title);

        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        CustomViewlist_FeedItem item = (CustomViewlist_FeedItem) feedItems.get(position);

        try
        {
            File file = new File(item.getPhotopath());
            Uri imageUri = Uri.fromFile(file);
            Glide.with(context).load(imageUri).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        }
        catch (Exception a)
        {

        }
        title.setTypeface(face);
        title.setText(item.getEnglish());

        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    CustomViewlist_FeedItem item = (CustomViewlist_FeedItem) feedItems.get(position);
                    pcatid = item.getPkey();
                     pos = position;
                   showalert_delete("Are you sure want to delete this ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CustomViewlist_FeedItem item = (CustomViewlist_FeedItem) feedItems.get(position);
                Temp.appviewedit=1;
                Temp.appview_pkey=item.getPkey();
                Temp.appview_english=item.getEnglish();
                Temp.appview_malayalam=item.getMalayalam();
                Temp.appview_hindi=item.getHindi();
                Temp.appview_tamil=item.getTamil();
                Temp.appview_telugu=item.getTelugu();
                Intent i = new Intent(context, Add_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView;
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert_delete(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    db.deleteappview_byid(pcatid);
                    Add_Apps h=(Add_Apps)activity;
                    h.removeitem(pos);
                    dialog.dismiss();
                } else {
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
}
