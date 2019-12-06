package com.zemose.admin;

import adapter.ChatHistory_ListAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.utils.SharedPrefsUtils.Keys;
import data.ChatHistory_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Chat_History extends AppCompatActivity {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ImageView back;
    ConnectionDetecter cd;
    final ChatDB cdb = new ChatDB(this);
    EditText chatbox;

    public List<ChatHistory_FeedItem> feedItems;

    public ChatHistory_ListAdapter listAdapter;
    ImageView loading;
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            try {
                JSONObject json = new JSONObject(arg1.getExtras().getString("zemosechatuser"));
                if (json.getString("notitype").equalsIgnoreCase("chatmsg") && !Chat_History.this.msg.equalsIgnoreCase(json.getString(NotificationCompat.CATEGORY_MESSAGE).replaceAll("^\"|\"$", "")) && Temp_Variable.chatuserid.equalsIgnoreCase(json.getString(Keys.USER_ID).replaceAll("^\"|\"$", "").toString()) && Temp_Variable.issupplier.contains(json.getString("usertype").replaceAll("^\"|\"$", ""))) {
                    ChatHistory_FeedItem item = new ChatHistory_FeedItem();
                    item.setpkey("0");
                    try {
                        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                        c1.setTime(sdf.parse(json.getString("chattime").replaceAll("^\"|\"$", "").toString()));
                        item.setchattime(Chat_History.this.getFormattedDate(c1.getTimeInMillis()));
                    } catch (Exception e) {
                        item.setchattime(json.getString("chattime").replaceAll("^\"|\"$", "").toString());
                    }
                    item.setuserid(json.getString(Keys.USER_ID).replaceAll("^\"|\"$", "").toString());
                    item.setusername(json.getString("userName").replaceAll("^\"|\"$", "").toString());
                    item.setmsg(json.getString(NotificationCompat.CATEGORY_MESSAGE).replaceAll("^\"|\"$", "").toString());
                    item.setchattype("2");
                    Chat_History.this.feedItems.add(item);
                    Chat_History.this.listAdapter.notifyDataSetChanged();
                    Chat_History.this.recylerview.scrollToPosition(Chat_History.this.feedItems.size());
                    try {
                        Chat_History.this.recmp = MediaPlayer.create(Chat_History.this.getApplicationContext(), R.raw.msgrecived);
                        Chat_History.this.recmp.start();
                    } catch (Exception e2) {
                    }
                    Chat_History.this.msg = json.getString(NotificationCompat.CATEGORY_MESSAGE).replaceAll("^\"|\"$", "");
                }
            } catch (Exception e3) {
            }
        }
    };
    String msg = "";
    ImageView nodata;

    public MediaPlayer recmp;
    RecyclerView recylerview;
    RotateAnimation rotate;
    ImageView send;

    public MediaPlayer sendmp;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_chat__history);
        this.cd = new ConnectionDetecter(this);
        this.back = (ImageView) findViewById(R.id.back);
        this.text = (TextView) findViewById(R.id.text);
        this.chatbox = (EditText) findViewById(R.id.chatbox);
        this.send = (ImageView) findViewById(R.id.send);
        this.loading = (ImageView) findViewById(R.id.loading);
        this.cd = new ConnectionDetecter(this);
        this.recylerview = (RecyclerView) findViewById(R.id.recylerview);
        this.nodata = (ImageView) findViewById(R.id.nodata);
        this.text.setText(Temp_Variable.chatusername);
        this.feedItems = new ArrayList();
        this.listAdapter = new ChatHistory_ListAdapter(this, this.feedItems);
        this.recylerview.setLayoutManager(new GridLayoutManager(this, 1));
        this.recylerview.setAdapter(this.listAdapter);
        try {
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, 1, 0.5f, 1, 0.5f);
            this.rotate = rotateAnimation;
            this.rotate.setDuration(5000);
            this.rotate.setRepeatCount(-1);
            this.rotate.setInterpolator(new LinearInterpolator());
        } catch (Exception e) {
        }
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Chat_History.this.onBackPressed();
            }
        });
        this.send.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Chat_History.this.chatbox.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(Chat_History.this.getApplicationContext(), "Please enter message", Toast.LENGTH_SHORT).show();
                    Chat_History.this.chatbox.requestFocus();
                } else if (Chat_History.this.cd.isConnectingToInternet()) {
                    Chat_History.this.send.startAnimation(Chat_History.this.rotate);
                    Chat_History.this.sendmessage();
                } else {
                    Toasty.info(Chat_History.this.getApplicationContext(), "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.feedItems.clear();
        try {
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mHandleMessageReceiver, new IntentFilter("com.zemosechat_user.Message"));
        } catch (Exception e2) {
        }
        loaddata();
    }
    public void onResume() {
        super.onResume();
        this.cdb.add_isopened("1");
    }
    public void onPause() {
        super.onPause();
        this.cdb.updatereadcount(Temp_Variable.chatuserid);
        this.cdb.delete_isopened();
    }

    public void loaddata() {
        ArrayList<String> id1 = this.cdb.get_chathistory(Temp_Variable.issupplier);
        String[] c = (String[]) id1.toArray(new String[id1.size()]);
        if (c.length > 0) {
            int a = c.length / 6;
            int m = -1;
            for (int j = 1; j <= a; j++) {
                ChatHistory_FeedItem item = new ChatHistory_FeedItem();
                int m2 = m + 1;
                item.setpkey(c[m2]);
                int m3 = m2 + 1;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                    Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                    c1.setTime(sdf.parse(c[m3]));
                    item.setchattime(getFormattedDate(c1.getTimeInMillis()));
                } catch (Exception e) {
                    item.setchattime(c[m3].trim());
                }
                int m4 = m3 + 1;
                item.setuserid(c[m4]);
                int m5 = m4 + 1;
                item.setusername(c[m5]);
                int m6 = m5 + 1;
                item.setmsg(c[m6]);
                m = m6 + 1;
                item.setchattype(c[m]);
                this.feedItems.add(item);
            }
            this.recylerview.setVisibility(View.VISIBLE);
            this.listAdapter.notifyDataSetChanged();
            this.nodata.setVisibility(View.GONE);
            this.recylerview.scrollToPosition(this.feedItems.size());
        } else if (this.feedItems.size() == 0) {
            this.nodata.setVisibility(View.VISIBLE);
            this.recylerview.setVisibility(View.GONE);
        }
    }

    public void sendmessage() {
        this.chatbox.setEnabled(false);
        OkHttpClient client = new OkHttpClient();
        JSONObject jo = new JSONObject();
        try {
            jo.put("isSupplier", Temp_Variable.issupplier);
            jo.put(Keys.USER_ID, Temp_Variable.chatuserid);
            jo.put("userName", "Admin");
            jo.put(NotificationCompat.CATEGORY_MESSAGE, this.chatbox.getText().toString());
        } catch (JSONException e) {
        }
        RequestBody body = RequestBody.create(this.JSON, jo.toString());
        Builder builder = new Builder();
        StringBuilder sb = new StringBuilder();
        sb.append(Temp_Variable.baseurl);
        sb.append("appadmin/chattouser");
        client.newCall(builder.url(sb.toString()).post(body).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Chat_History.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Chat_History.this.chatbox.setEnabled(true);
                        Chat_History.this.send.clearAnimation();
                        Toasty.info(Chat_History.this.getApplicationContext(), "Goof Please try later", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public void onResponse(Call call, final Response response) throws IOException {
                Chat_History.this.runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Chat_History.this.chatbox.setEnabled(true);
                            Chat_History.this.send.clearAnimation();
                            JSONObject jo = new JSONObject(response.body().string());
                            if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("No")) {
                                Toasty.info(Chat_History.this.getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
                            } else if (jo.getString(PayUmoneyConstants.MESSAGE).equalsIgnoreCase("Ok")) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                                String strDate = sdf.format(c.getTime());
                                Chat_History.this.cdb.add_chatmsg(strDate, Temp_Variable.chatuserid, Temp_Variable.chatusername, Chat_History.this.chatbox.getText().toString(), "1", "1", Temp_Variable.issupplier);
                                ChatHistory_FeedItem item = new ChatHistory_FeedItem();
                                item.setpkey("0");
                                try {
                                    Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                    c1.setTime(sdf.parse(strDate));
                                    item.setchattime(Chat_History.this.getFormattedDate(c1.getTimeInMillis()));
                                } catch (Exception e) {
                                    item.setchattime(strDate);
                                }
                                item.setuserid(Temp_Variable.chatuserid);
                                item.setusername("Admin");
                                item.setmsg(Chat_History.this.chatbox.getText().toString());
                                item.setchattype("1");
                                Chat_History.this.feedItems.add(item);
                                Chat_History.this.listAdapter.notifyDataSetChanged();
                                Chat_History.this.recylerview.scrollToPosition(Chat_History.this.feedItems.size());
                                Chat_History.this.chatbox.setText("");
                                try {
                                    Chat_History.this.sendmp = MediaPlayer.create(Chat_History.this.getApplicationContext(), R.raw.msgsend);
                                    Chat_History.this.sendmp.start();
                                } catch (Exception e2) {
                                }
                            } else {
                                Toasty.info(Chat_History.this.getApplicationContext(), " Please try later", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e3) {
                        }
                    }
                });
            }
        });
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        String str = "h:mm a";
        String str2 = "MMM d h:mm a";
        if (now.get(5) == smsTime.get(5)) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateFormat.format("h:mm a", smsTime));
            sb.append("");
            return sb.toString();
        } else if (now.get(5) - smsTime.get(5) == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Yesterday ");
            sb2.append(DateFormat.format("h:mm a", smsTime));
            return sb2.toString();
        } else if (now.get(1) == smsTime.get(1)) {
            return DateFormat.format("MMM d h:mm a", smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }
    }
}
