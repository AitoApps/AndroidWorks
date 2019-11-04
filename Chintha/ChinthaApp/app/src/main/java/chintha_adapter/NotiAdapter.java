package chintha_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.suhi_chintha.Chintha_Likes;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.R;
import com.suhi_chintha.Replay;
import com.suhi_chintha.Static_Variable;
import com.vanniktech.emoji.EmojiTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import chintha_data.NotiFeed;

public class NotiAdapter extends BaseAdapter {

    public AppCompatActivity activity;

    public Context context;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;

    public List<NotiFeed> feed;
    private LayoutInflater inflater;

    public NotiAdapter(AppCompatActivity activity2, List<NotiFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
        dataDb1= new DataDB1(context);
        dataDb2= new DataDB2(context);
    }

    public int getCount() {
        return feed.size();
    }

    public Object getItem(int location) {
        return feed.get(location);
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
            convertView2 = inflater.inflate(R.layout.noti_custom_replay, null);
        } else {
            convertView2 = convertView;
        }
        TextView noticount = (TextView) convertView2.findViewById(R.id.countofnoti);
        EmojiTextView text_noti = (EmojiTextView) convertView2.findViewById(R.id.notitext);
        TextView txttime = (TextView) convertView2.findViewById(R.id.txttime);
        ImageView img = (ImageView) convertView2.findViewById(R.id.img);
        RelativeLayout layouts = (RelativeLayout) convertView2.findViewById(R.id.layout);
        NotiFeed item = (NotiFeed) feed.get(i);
        if (item.get_isread().equalsIgnoreCase("0")) {
            layouts.setBackgroundColor(Color.parseColor("#e9ebee"));
        } else {
            layouts.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (item.gettype().equalsIgnoreCase("0")) {
            if (item.getcount().equalsIgnoreCase("1")) {
                noticount.setText(item.getlastname()+" Likes your status");
            } else {
                noticount.setText(item.getlastname()+" And "+(Integer.parseInt(item.getcount()) - 1)+" Others Likes your status");
            }
        } else if (item.gettype().equalsIgnoreCase("1") || item.gettype().equalsIgnoreCase("3")) {
            if (item.getcount().equalsIgnoreCase("1")) {
                noticount.setText(item.getlastname()+" Commented on this");
            } else {
                noticount.setText(item.getlastname()+" And "+(Integer.parseInt(item.getcount()) - 1)+" Others Commented on this");
            }
        } else if (item.gettype().equalsIgnoreCase("2") || item.gettype().equalsIgnoreCase("4")) {
            if (item.getcount().equalsIgnoreCase("1")) {
                noticount.setText(item.getlastname()+" Replyed on this");
            } else {

                noticount.setText(item.getlastname()+" And "+(Integer.parseInt(item.getcount()) - 1)+" Others Replyed on this");
            }
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timezoneID = TimeZone.getDefault().getID();
            sdf.setTimeZone(TimeZone.getTimeZone(timezoneID));
            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone(timezoneID));
            c1.setTime(sdf.parse(item.get_createddate()));
            txttime.setText(getTimeAgo(c1.getTimeInMillis(), context));
        } catch (Exception e) {
            txttime.setText(item.get_createddate());
        }
        if (item.gettype().equalsIgnoreCase("3")) {
            String[] m = item.gettext().split(":%");
            text_noti.setText(fromHtml("<b><asset_fonts color='#e62020'>Blood : </asset_fonts></b>"+m[2]+" - "+m[1]));
        } else if (item.gettype().equalsIgnoreCase("4")) {
            String p = item.gettext().replace("Blood : ", "");
            text_noti.setText(fromHtml("<b><asset_fonts color='#e62020'>Blood : </asset_fonts></b>"+p));
        } else {
            text_noti.setText(fromHtml(item.gettext()));
        }
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.get_imgsig()));
        Glide.with(context).load(Static_Variable.entypoint1+"userphotosmall/"+item.getuserid()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(img);
        text_noti.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                Log.w("Salmana",item.gettype());
                NotiFeed item = (NotiFeed) feed.get(i);
                if (item.gettype().equalsIgnoreCase("0")) {
                    dataDb1.updatereadstatus(item.getpkey());
                    Static_Variable.userid = item.get_ogId();
                    Intent i = new Intent(context, Chintha_Likes.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (item.gettype().equalsIgnoreCase("1")) {
                    dataDb1.updatereadstatus(item.getpkey());
                    dataDb1.deletecmntvisible();
                    dataDb1.add_cmntvisible("1");
                    dataDb1.deletecmntdetails();
                    dataDb1.add_cmntdtails(item.get_ogId(), item.get_chinthauserid(), item.get_chintha_username(), item.gettext(), item.get_imgsig(), item.getstatustype(), item.get_picurl(), item.get_photodim());
                    Intent i2 = new Intent(context, Lists_ChinthaComments.class);
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i2);
                } else if (!item.gettype().equalsIgnoreCase("3")) {
                    if (item.gettype().equalsIgnoreCase("1")) {
                        dataDb1.updatereadstatus(item.getpkey());
                        dataDb2.add_replycmnt(item.get_ogId(), item.get_chinthauserid(),item.get_chintha_username(),Static_Variable.entypoint1+"userphotosmall/"+item.get_chinthauserid()+".jpg", item.gettext(), item.get_imgsig());
                        dataDb2.add_rplyvisible("1");
                        Intent i3 = new Intent(context, Replay.class);
                        i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i3);
                    } else if (item.gettype().equalsIgnoreCase("2")) {
                        dataDb1.updatereadstatus(item.getpkey());
                        dataDb2.add_replycmnt(item.get_ogId(), item.get_chinthauserid(), item.get_chintha_username(),Static_Variable.entypoint1+"userphotosmall/"+item.get_chinthauserid()+".jpg", item.gettext(), item.get_imgsig());
                        dataDb2.add_rplyvisible("1");
                        Intent i4 = new Intent(context, Replay.class);
                        i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i4);
                    }
                }
            }
        });
        layouts.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                NotiFeed item = (NotiFeed) feed.get(i);
                if (item.gettype().equalsIgnoreCase("0")) {
                    dataDb1.updatereadstatus(item.getpkey());
                    Static_Variable.userid = item.get_ogId();
                    Intent i = new Intent(context, Chintha_Likes.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (item.gettype().equalsIgnoreCase("1")) {
                    dataDb1.updatereadstatus(item.getpkey());
                    dataDb1.deletecmntvisible();
                    dataDb1.add_cmntvisible("1");
                    dataDb1.deletecmntdetails();
                    dataDb1.add_cmntdtails(item.get_ogId(), item.get_chinthauserid(), item.get_chintha_username(), item.gettext(), item.get_imgsig(), item.getstatustype(), item.get_picurl(), item.get_photodim());
                    Intent i2 = new Intent(context, Lists_ChinthaComments.class);
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i2);
                } else if (!item.gettype().equalsIgnoreCase("3")) {
                    if (item.gettype().equalsIgnoreCase("1")) {
                        dataDb1.updatereadstatus(item.getpkey());
                        dataDb2.add_replycmnt(item.get_ogId(), item.get_chinthauserid(), item.get_chintha_username(), Static_Variable.entypoint1 + "userphotosmall/" + item.get_chinthauserid() + ".jpg", item.gettext(), item.get_imgsig());
                        dataDb2.add_rplyvisible("1");
                        Intent i3 = new Intent(context, Replay.class);
                        i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i3);
                    } else if (item.gettype().equalsIgnoreCase("2")) {
                        dataDb1.updatereadstatus(item.getpkey());
                        dataDb2.add_replycmnt(item.get_ogId(), item.get_chinthauserid(), item.get_chintha_username(), Static_Variable.entypoint1 + "userphotosmall/" + item.get_chinthauserid() + ".jpg", item.gettext(), item.get_imgsig());
                        dataDb2.add_rplyvisible("1");
                        Intent i4 = new Intent(context, Replay.class);
                        i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i4);
                    }
                }
            }
        });
        return convertView2;
    }


    public static String getTimeAgo(long time, Context ctx) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "1 Minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " Minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "1 Hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " Hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            return diff / DAY_MILLIS + " Days ago";
        }
    }

    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(html, 0);
        }
        return Html.fromHtml(html);
    }
}
