package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sanji.DatabaseHandler;
import com.sanji.Notification_Databasehandler;
import com.sanji.Notifications;
import com.sanji.Youtube_Player;
import data.Notifications_FeedItem;
import java.util.List;

public class NotificationsListAdapter extends BaseAdapter {

    public Activity activity;

    public Context context;
    Notification_Databasehandler db = new Notification_Databasehandler(context);
    DatabaseHandler db1 = new DatabaseHandler(context);
    Typeface face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    Typeface face1 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");

    public List<Notifications_FeedItem> feedItems;
    private LayoutInflater inflater;

    public NotificationsListAdapter(Activity activity2, List<Notifications_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
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
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_notification, null);
        } else {
            convertView2 = convertView;
        }
        RelativeLayout txtlayout = (RelativeLayout) convertView2.findViewById(R.id.txtlayout);
        RelativeLayout imglayout = (RelativeLayout) convertView2.findViewById(R.id.imglayout);
        RelativeLayout videolayout = (RelativeLayout) convertView2.findViewById(R.id.videolayout);
        TextView heading1 = (TextView) convertView2.findViewById(R.id.heading1);
        TextView heading2 = (TextView) convertView2.findViewById(R.id.heading2);
        TextView heading3 = (TextView) convertView2.findViewById(R.id.heading3);
        TextView title1 = (TextView) convertView2.findViewById(R.id.title1);
        TextView time = (TextView) convertView2.findViewById(R.id.time);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        ImageView image2 = (ImageView) convertView2.findViewById(R.id.image2);
        ImageView image3 = (ImageView) convertView2.findViewById(R.id.image3);
        ImageView playicon = (ImageView) convertView2.findViewById(R.id.playicon);
        Notifications_FeedItem item = (Notifications_FeedItem) feedItems.get(i);
        View convertView3 = convertView2;
        heading1.setTypeface(face);
        title1.setTypeface(face1);
        time.setTypeface(face);
        ImageView playicon2 = playicon;
        if (item.getnotitype().equalsIgnoreCase("1")) {
            txtlayout.setVisibility(View.VISIBLE);
            imglayout.setVisibility(View.GONE);
            videolayout.setVisibility(View.GONE);
            heading1.setText(item.getnotititle());
            title1.setText(item.getmessage());
            TextView textView = heading1;
        } else if (item.getnotitype().equalsIgnoreCase("2")) {
            txtlayout.setVisibility(View.GONE);
            imglayout.setVisibility(View.VISIBLE);
            videolayout.setVisibility(View.GONE);
            heading2.setText(item.getnotititle());
            float ogheight1 = ((Float.parseFloat(db1.getscreenwidth()) - 50.0f) / 4.0f) * 3.0f;
            TextView textView2 = heading1;
            image2.getLayoutParams().height = Math.round(ogheight1);
            Glide.with(context).load(item.getmessage()).transition(DrawableTransitionOptions.withCrossFade()).into(image2);
        } else {
            if (item.getnotitype().equalsIgnoreCase("3")) {
                txtlayout.setVisibility(View.GONE);
                imglayout.setVisibility(View.GONE);
                videolayout.setVisibility(View.VISIBLE);
                heading3.setText(item.getnotititle());
                float ogheight12 = Float.valueOf(db1.getscreenwidth()).floatValue() - 50.0f;
                float calheight1 = 0.75f * ogheight12;
                float f = ogheight12;
                image3.getLayoutParams().height = Math.round(calheight1);
                StringBuilder sb = new StringBuilder();
                sb.append("https://img.youtube.com/vi/");
                sb.append(item.getmessage());
                sb.append("/0.jpg");
                String url = sb.toString();
                String str = url;
                Glide.with(context).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(image3);
            }
        }
        time.setText(item.gettime());
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Notifications_FeedItem notifications_FeedItem = (Notifications_FeedItem) NotificationsListAdapter.feedItems.get(i);
                NotificationsListAdapter.db.deletenoti(((Notifications_FeedItem) NotificationsListAdapter.feedItems.get(i)).getsn());
                ((Notifications) NotificationsListAdapter.activity).clearitem(i);
            }
        });
        playicon2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Notifications_FeedItem notifications_FeedItem = (Notifications_FeedItem) NotificationsListAdapter.feedItems.get(i);
                NotificationsListAdapter.db1.addyoutubelink(((Notifications_FeedItem) NotificationsListAdapter.feedItems.get(i)).getmessage());
                Intent i = new Intent(NotificationsListAdapter.context, Youtube_Player.class);
                i.setFlags(268435456);
                NotificationsListAdapter.context.startActivity(i);
            }
        });
        image3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Notifications_FeedItem notifications_FeedItem = (Notifications_FeedItem) NotificationsListAdapter.feedItems.get(i);
                NotificationsListAdapter.db1.addyoutubelink(((Notifications_FeedItem) NotificationsListAdapter.feedItems.get(i)).getmessage());
                Intent i = new Intent(NotificationsListAdapter.context, Youtube_Player.class);
                i.setFlags(268435456);
                NotificationsListAdapter.context.startActivity(i);
            }
        });
        return convertView3;
    }
}
