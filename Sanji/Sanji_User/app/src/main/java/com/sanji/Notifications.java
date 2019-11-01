package com.sanji;

import adapter.NotificationsListAdapter;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import data.Notifications_FeedItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Notifications extends AppCompatActivity {
    ImageView back;
    RelativeLayout content;
    Notification_Databasehandler db = new Notification_Databasehandler(this);
    ImageView deleteall;
    Typeface face;
    Typeface face1;
    private List<Notifications_FeedItem> feedItems;
    boolean flag = false;
    RelativeLayout footerview;
    public int limit = 0;
    ListView list;
    private NotificationsListAdapter listAdapter;
    ImageView nodata;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_notifications);
        try {
            list = (ListView) findViewById(R.id.list);
            nodata = (ImageView) findViewById(R.id.nodata);
            back = (ImageView) findViewById(R.id.back);
            deleteall = (ImageView) findViewById(R.id.deleteall);
            content = (RelativeLayout) findViewById(R.id.content);
            footerview = (RelativeLayout) getLayoutInflater().inflate(R.layout.footerview, null);
            list.addFooterView(footerview);
            footerview.setVisibility(View.GONE);
            back.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    Notifications.onBackPressed();
                }
            });
            list.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            text = (TextView) findViewById(R.id.text);
            face = Typeface.createFromAsset(getAssets(), "font/proxibold.otf");
            face1 = Typeface.createFromAsset(getAssets(), "font/proximanormal.ttf");
            text.setText("നോട്ടിഫിക്കേഷനുകള്‍ ");
            text.setTypeface(face1);
            deleteall.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Notifications.showalertdelete("നോട്ടിഫിക്കേഷന്‍ എല്ലാം ക്ലിയര്‍ ചെയ്യാം അല്ലെ ?");
                }
            });
            db.deletecount();
            feedItems = new ArrayList();
            listAdapter = new NotificationsListAdapter(this, feedItems);
            list.setAdapter(listAdapter);
            list.setOnScrollListener(new OnScrollListener() {
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (visibleItemCount == totalItemCount - firstVisibleItem && Notifications.flag) {
                        Notifications notifications = Notifications.this;
                        notifications.flag = false;
                        if (notifications.footerview.getVisibility() != 0) {
                            Notifications.footerview.setVisibility(View.VISIBLE);
                            Notifications.limit += 30;
                            Notifications.loaddata();
                        }
                    }
                }

                public void onScrollStateChanged(AbsListView arg0, int arg1) {
                    if (arg1 == 2) {
                        Notifications.flag = true;
                    }
                }
            });
        } catch (Exception e) {
        }
        try {
            list.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            feedItems.clear();
            limit = 0;
            loaddata();
        } catch (Exception e2) {
        }
    }

    public void loaddata() {
        String str = "Asia/Calcutta";
        try {
            Notification_Databasehandler notification_Databasehandler = db;
            StringBuilder sb = new StringBuilder();
            sb.append(limit);
            sb.append("");
            ArrayList<String> id1 = notification_Databasehandler.getnoti1(sb.toString());
            String[] c = (String[]) id1.toArray(new String[id1.size()]);
            if (c.length > 0) {
                int a = c.length / 5;
                int m = -1;
                for (int j = 1; j <= a; j++) {
                    Notifications_FeedItem item = new Notifications_FeedItem();
                    int m2 = m + 1;
                    item.setsn(c[m2]);
                    int m3 = m2 + 1;
                    item.setnotitype(c[m3]);
                    int m4 = m3 + 1;
                    item.setnotititle(c[m4]);
                    int m5 = m4 + 1;
                    item.setmessage(c[m5]);
                    m = m5 + 1;
                    try {
                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone(str));
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                        sdf.setTimeZone(TimeZone.getTimeZone(str));
                        c1.setTime(sdf.parse(c[m]));
                        item.settime(getFormattedDate(c1.getTimeInMillis()));
                    } catch (Exception e) {
                        item.settime(c[m]);
                    }
                    feedItems.add(item);
                }
                list.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
                listAdapter.notifyDataSetChanged();
                footerview.setVisibility(View.GONE);
            } else if (feedItems.size() == 0) {
                footerview.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
            } else {
                footerview.setVisibility(View.GONE);
            }
        } catch (Exception e2) {
        }
    }

    public void clearitem(int position) {
        feedItems.remove(position);
        listAdapter.notifyDataSetChanged();
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        String str = "h:mm a";
        String str2 = "MMM d h:mm a";
        String str3 = "h:mm a";
        if (now.get(5) == smsTime.get(5)) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateFormat.format(str3, smsTime));
            sb.append("");
            return sb.toString();
        } else if (now.get(5) - smsTime.get(5) == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Yesterday ");
            sb2.append(DateFormat.format(str3, smsTime));
            return sb2.toString();
        } else if (now.get(1) == smsTime.get(1)) {
            return DateFormat.format("MMM d h:mm a", smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }
    }

    public void showalertdelete(String message) {
        Builder builder = new Builder(this);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Notifications.db.deletecount();
                Notifications.db.deletenoti();
                Notifications notifications = Notifications.this;
                notifications.limit = 0;
                notifications.list.setVisibility(View.GONE);
                Notifications.nodata.setVisibility(View.VISIBLE);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(67108864);
            startActivity(intent);
        } catch (Exception e) {
        }
    }
}
