package com.suhi_chintha;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import chintha_adapter.PriyapettavrudeChinathakalAdapter;
import chintha_data.ChinthaFeeds;
import com.airbnb.lottie.LottieAnimationView;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Chintha_Fvrtusers extends AppCompatActivity {
    public static int limit = 0;
    ImageView addtofvrt;
    RelativeLayout alphalayout;
    public PriyapettavrudeChinathakalAdapter apater;
    NetConnection cd;
    RelativeLayout content;
    public DataDb dataDb = new DataDb(this);
    public DataDB1 dataDb1 = new DataDB1(this);
    final DataDB4 dataDb4 = new DataDB4(this);
    RelativeLayout extrasettings;
    Typeface face;
    Typeface face3;

    public List<ChinthaFeeds> feedStatuses;
    RelativeLayout fifth;
    boolean flag = false;

    public SwipeRefreshLayout layout;
    LottieAnimationView load_icon;
    ImageView move_back;
    ImageView nonet;
    ProgressDialog pd;
    RecyclerView recylerview;
    public String reporttype = "";
    RelativeLayout settingslayout;
    RelativeLayout sixth;
    TextView text;
    RelativeLayout third;
    public TextView txtcopy;
    public TextView txtreportstatus;
    public TextView txtstatustoimage;
    public User_DataDB userDataDB = new User_DataDB(this);
    public String userslist = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.chintha_fvrtusers_actvty);
        try {
            face = Typeface.createFromAsset(getAssets(), "asset_fonts/font_rachana.ttf");
            load_icon = (LottieAnimationView) findViewById(R.id.lotty_loadin);
            txtstatustoimage = (TextView) findViewById(R.id.txtstatustoimage);
            sixth = (RelativeLayout) findViewById(R.id.sixth);
            alphalayout = (RelativeLayout) findViewById(R.id.alphalayout);
            extrasettings = (RelativeLayout) findViewById(R.id.extrasettings);
            face3 = Typeface.createFromAsset(getAssets(), "asset_fonts/proxibold.otf");
            cd = new NetConnection(this);
            pd = new ProgressDialog(this);
            recylerview = (RecyclerView) findViewById(R.id.recylerview);
            nonet = (ImageView) findViewById(R.id.nonets);
            move_back = (ImageView) findViewById(R.id.moveback);
            content = (RelativeLayout) findViewById(R.id.content);
            addtofvrt = (ImageView) findViewById(R.id.addfvrt);
            layout = (SwipeRefreshLayout) findViewById(R.id.layout);
            layout.setEnabled(true);
            text = (TextView) findViewById(R.id.text);
            third = (RelativeLayout) findViewById(R.id.lyt_third);
            fifth = (RelativeLayout) findViewById(R.id.fifth);
            txtcopy = (TextView) findViewById(R.id.txtcopy);
            txtreportstatus = (TextView) findViewById(R.id.txtreportstatus);
            settingslayout = (RelativeLayout) findViewById(R.id.settingslayout);
            txtcopy.setText("കോപ്പി ");
            txtreportstatus.setText("റിപ്പോര്‍ട്ട്‌ ");
            txtstatustoimage.setText("ചിത്രമുണ്ടാക്കാം ");
            txtcopy.setTypeface(face3);
            txtreportstatus.setTypeface(face3);
            txtstatustoimage.setTypeface(face3);
            move_back.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        onBackPressed();
                    } catch (Exception e) {
                    }
                }
            });
            feedStatuses = new ArrayList();
            apater = new PriyapettavrudeChinathakalAdapter(this, feedStatuses);
            recylerview.setLayoutManager(new LinearLayoutManager(this));
            recylerview.setAdapter(apater);
            text.setText("പ്രിയപ്പെട്ടവരുടെ സ്റ്റാറ്റസുകള്‍ ");
            text.setTypeface(face);
            text.setSelected(true);
            alphalayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    settingslayout. setVisibility(View.GONE);
                }
            });
            extrasettings.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                }
            });
            txtcopy.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    settingslayout. setVisibility(View.GONE);
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(Static_Variable.chintha_text);
                    } else {
                        ((android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", Static_Variable.chintha_text));
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                }
            });
            third.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    settingslayout. setVisibility(View.GONE);
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(Static_Variable.chintha_text);
                    } else {
                        ((android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", Static_Variable.chintha_text));
                    }
                    Toasty.info(getApplicationContext(), (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                }
            });
            txtreportstatus.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        report_status();
                    } catch (Exception e) {
                    }
                }
            });
            fifth.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        settingslayout. setVisibility(View.GONE);
                        report_status();
                    } catch (Exception e) {
                    }
                }
            });
            sixth.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Status_To_Image.class));
                }
            });
            layout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    layout.setRefreshing(true);
                    nonet. setVisibility(View.GONE);
                    limit = 0;
                    new load_status2().execute(new String[0]);
                }
            });
            nonet.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (cd.isConnectingToInternet()) {
                        nonet. setVisibility(View.GONE);
                        limit = 0;
                        new load_data().execute(new String[0]);
                        return;
                    }
                    nonet.setVisibility(View.VISIBLE);
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            });
            addtofvrt.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Search_Result.txtsearch = "";
                    startActivity(new Intent(getApplicationContext(), Search_Result.class));
                }
            });
        } catch (Exception e) {
        }
    }
    public void onResume() {
        super.onResume();
        try {
            if (cd.isConnectingToInternet()) {
                nonet. setVisibility(View.GONE);
                limit = 0;
                userslist = "";
                new load_data().execute(new String[0]);
                return;
            }
            nonet.setVisibility(View.VISIBLE);
            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void getuserslist() {
        userslist = "";
        ArrayList<String> id1 = dataDb.get_fvrt();
        String[] c = (String[]) id1.toArray(new String[id1.size()]);
        if (c.length > 0) {
            for (int i = 0; i < c.length; i++) {
                if (userslist.equalsIgnoreCase("")) {
                    userslist = c[i];
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(userslist);
                    sb.append(",");
                    sb.append(c[i]);
                    userslist = sb.toString();
                }
            }
        }
    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);
        Calendar now = Calendar.getInstance();
        final String timeFormatString = "h:mm a";
        final String dateTimeFormatString = "MMM d h:mm a";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return DateFormat.format(timeFormatString, smsTime)+"";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy h:mm a", smsTime).toString();
        }

    }

    public void removeitem(int position) {
        feedStatuses.remove(position);
        apater.notifyDataSetChanged();
    }

    public void timer_update(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public boolean checkcharector(String text2) {
        boolean found = false;
        int i = 0;
        while (true) {
            if (i < text2.length()) {
                if (text2.toUpperCase().charAt(i) >= 'A' && text2.toUpperCase().charAt(i) <= 'Z') {
                    found = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return found;
    }

    public void loadmore() {
        if (!cd.isConnectingToInternet()) {
            Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
        } else if (feedStatuses.size() > 10) {
            new load_status1().execute(new String[0]);
        }
    }

    public void report_status() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.report_chinthakal);
        View findViewById = dialog.findViewById(R.id.rdgroup);
        TextView text2 = (TextView) dialog.findViewById(R.id.text);
        TextView text1 = (TextView) dialog.findViewById(R.id.text1);
        Button update = (Button) dialog.findViewById(R.id.rpt_update);
        RadioButton chatting = (RadioButton) dialog.findViewById(R.id.rpt_chatting);
        RadioButton thery = (RadioButton) dialog.findViewById(R.id.rpt_thery);
        RadioButton parihasam = (RadioButton) dialog.findViewById(R.id.parihasam);
        RadioButton ashleelam = (RadioButton) dialog.findViewById(R.id.rpt_ashleelam);
        RadioButton other = (RadioButton) dialog.findViewById(R.id.other);
        text2.setTypeface(face);
        text1.setTypeface(face);
        thery.setText(Static_Variable.r_theri);
        parihasam.setText(Static_Variable.r_parihasakan);
        ashleelam.setText(Static_Variable.reason_ashleelam);
        other.setText(Static_Variable.other_reason);
        text2.setText(Static_Variable.chinthas_rpt);
        text1.setText(Static_Variable.status_rpt1);
        chatting.setText(Static_Variable.r_chattings);

        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatting.isChecked() || thery.isChecked() || parihasam.isChecked() || ashleelam.isChecked() || other.isChecked()) {
                    if (chatting.isChecked()) {
                        reporttype = "1";
                    } else if (thery.isChecked()) {
                        reporttype = "2";
                    } else if (parihasam.isChecked()) {
                        reporttype = "3";
                    } else if (ashleelam.isChecked()) {
                        reporttype = "4";
                    } else if (other.isChecked()) {
                        reporttype = "5";
                    }
                    Static_Variable.typeofstatus = "0";
                    new rpt_status_async().execute(new String[0]);
                    dialog.dismiss();
                    return;
                }
                Toasty.info(getApplicationContext(), (CharSequence) "Please select atleast one option", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
    }

    public void status_share(String status) {
        Intent sharingIntent = new Intent("android.intent.action.SEND");
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra("android.intent.extra.SUBJECT", "Status");
        StringBuilder sb = new StringBuilder();
        sb.append(status);
        sb.append("\n ");
        sb.append(Static_Variable.appshare_link);
        sharingIntent.putExtra("android.intent.extra.TEXT", sb.toString());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void sharetoall(Bitmap bitmap) {
        try {

            File file = new File(getCacheDir(), "Chintha_"+System.currentTimeMillis()+".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
            intent.setType("image/*");
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    public void show_popup() {
        settingslayout.setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {
        if (settingslayout.getVisibility() == View.VISIBLE) {
            settingslayout. setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
    public class load_data extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            if (userslist.equalsIgnoreCase("")) {
                getuserslist();
            }
            recylerview. setVisibility(View.GONE);
            load_icon.setVisibility(View.VISIBLE);
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Static_Variable.entypoint1 +"getstatus_fvrt_1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+userslist, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }
        public void onPostExecute(String result) {
            int ispinned;
            try {
                layout.setRefreshing(false);
                if (result.contains("%:ok")) {
                    try {
                        feedStatuses.clear();
                        String[] got = result.split("%:");
                        int k = (got.length - 1) / 18;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            m=m+1;
                            ChinthaFeeds item = new ChinthaFeeds();
                            item.setId(got[m]);
                            m=m+1;
                            item.setuserid(got[m]);
                            item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                            m=m+1;
                            item.set_name(got[m]);
                            m=m+1;
                            item.setMobile(got[m]);
                            m=m+1;
                            if (got[m].equalsIgnoreCase("0")) {
                                ispinned = 0;
                            } else {
                                ispinned = 1;
                            }
                            item.setpinned(got[m]);
                            m=m+1;
                            if (ispinned == 1) {
                                try {
                                    item.setStatus(got[m]);
                                } catch (Exception e) {
                                }
                            } else {
                                item.setStatus(new String(Base64.decode(got[m], 0), StandardCharsets.UTF_8));
                            }
                            m=m+1;
                            item.setlikes(got[m]);
                            m=m+1;
                            item.setshowmobile(got[m]);
                            m=m+1;
                            item.setverified(got[m]);
                            m=m+1;
                            item.setcmntcount(got[m]);
                            m=m+1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                c1.setTime(sdf.parse(got[m]));
                                item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e2) {
                                item.set_postdate(got[m]);
                            }
                            m=m+1;
                            item.setimgsig(got[m]);
                            m=m+1;
                            item.setstatustype(got[m]);
                            m=m+1;
                            item.setphotourl(got[m]);
                            m=m+1;
                            item.setphotodimension(got[m]);
                            m=m+1;
                            item.setshortstatus(got[m]);
                            m=m+1;
                            item.set_iscmntlock(got[m]);
                            m=m+1;
                            item.set_statuscount(got[m]);
                            if (i % 5 == 0) {
                                item.setshowads("1");
                            } else {
                                item.setshowads("0");
                            }
                            feedStatuses.add(item);
                        }
                    } catch (Exception e3) {
                    }
                    load_icon. setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    apater.notifyDataSetChanged();
                    return;
                }
                load_icon. setVisibility(View.GONE);
            } catch (Exception e4) {
            }
        }
    }

    public class load_status1 extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            if (userslist.equalsIgnoreCase("")) {
                getuserslist();
            }
            limit += 30;
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"getstatus_fvrt_1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+userslist, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            int ispinned;
            try {
                if (result.contains("%:ok")) {
                    String[] got = result.split("%:");
                    int k = (got.length - 1) / 18;
                    int m = -1;
                    for (int i = 1; i <= k; i++) {
                        m=m+1;
                        ChinthaFeeds item = new ChinthaFeeds();
                        item.setId(got[m]);
                        m=m+1;
                        item.setuserid(got[m]);
                        item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                        m=m+1;
                        item.set_name(got[m]);
                        m=m+1;
                        item.setMobile(got[m]);
                        m=m+1;
                        if (got[m].equalsIgnoreCase("0")) {
                            ispinned = 0;
                        } else {
                            ispinned = 1;
                        }
                        item.setpinned(got[m]);
                        m=m+1;
                        if (ispinned == 1) {
                            try {
                                item.setStatus(got[m]);
                            } catch (Exception e) {
                            }
                        } else {
                            item.setStatus(new String(Base64.decode(got[m], 0), StandardCharsets.UTF_8));
                        }
                        m=m+1;
                        item.setlikes(got[m]);
                        m=m+1;
                        item.setshowmobile(got[m]);
                        m=m+1;
                        item.setverified(got[m]);
                        m=m+1;
                        item.setcmntcount(got[m]);
                        m=m+1;
                        try {
                            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                            c1.setTime(sdf.parse(got[m]));
                            item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                        } catch (Exception e2) {
                            item.set_postdate(got[m]);
                        }
                        m=m+1;
                        item.setimgsig(got[m]);
                        m=m+1;
                        item.setstatustype(got[m]);
                        m=m+1;
                        item.setphotourl(got[m]);
                        m=m+1;
                        item.setphotodimension(got[m]);
                        m=m+1;
                        item.setshortstatus(got[m]);
                        m=m+1;
                        item.set_iscmntlock(got[m]);
                        m=m+1;
                        item.set_statuscount(got[m]);
                        if (i % 5 == 0) {
                            item.setshowads("1");
                        } else {
                            item.setshowads("0");
                        }
                        feedStatuses.add(item);
                    }
                    apater.notifyDataSetChanged();
                    return;
                }
                load_icon. setVisibility(View.GONE);
            } catch (Exception e3) {
            }
        }
    }

    public class load_status2 extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            if (userslist.equalsIgnoreCase("")) {
                getuserslist();
            }
            limit = 0;
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"getstatus_fvrt_1.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(limit+":%"+userslist, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            int ispinned;
            try {
                layout.setRefreshing(false);
                if (result.contains("%:ok")) {
                    try {
                        feedStatuses.clear();
                        String[] got = result.split("%:");
                        int k = (got.length - 1) / 18;
                        int m = -1;
                        for (int i = 1; i <= k; i++) {
                            m=m+1;
                            ChinthaFeeds item = new ChinthaFeeds();
                            item.setId(got[m]);
                            m=m+1;
                            item.setuserid(got[m]);
                            item.setProfilePic(Static_Variable.entypoint1+"userphotosmall/"+got[m]+".jpg");
                            m=m+1;
                            item.set_name(got[m]);
                            m=m+1;
                            item.setMobile(got[m]);
                            m=m+1;
                            if (got[m].equalsIgnoreCase("0")) {
                                ispinned = 0;
                            } else {
                                ispinned = 1;
                            }
                            item.setpinned(got[m]);
                            m=m+1;
                            if (ispinned == 1) {
                                try {
                                    item.setStatus(got[m]);
                                } catch (Exception e) {
                                }
                            } else {
                                item.setStatus(new String(Base64.decode(got[m], 0), StandardCharsets.UTF_8));
                            }
                            m=m+1;
                            item.setlikes(got[m]);
                            m=m+1;
                            item.setshowmobile(got[m]);
                            m=m+1;
                            item.setverified(got[m]);
                            m=m+1;
                            item.setcmntcount(got[m]);
                            m=m+1;
                            try {
                                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                                c1.setTime(sdf.parse(got[m]));
                                item.set_postdate(getFormattedDate(c1.getTimeInMillis()));
                            } catch (Exception e2) {
                                item.set_postdate(got[m]);
                            }
                            m=m+1;
                            item.setimgsig(got[m]);
                            m=m+1;
                            item.setstatustype(got[m]);
                            m=m+1;
                            item.setphotourl(got[m]);
                            m=m+1;
                            item.setphotodimension(got[m]);
                            m=m+1;
                            item.setshortstatus(got[m]);
                            m=m+1;
                            item.set_iscmntlock(got[m]);
                            m=m+1;
                            item.set_statuscount(got[m]);
                            if (i % 5 == 0) {
                                item.setshowads("1");
                            } else {
                                item.setshowads("0");
                            }
                            feedStatuses.add(item);
                        }
                    } catch (Exception e3) {
                    }
                    layout.setRefreshing(false);
                    load_icon. setVisibility(View.GONE);
                    recylerview.setVisibility(View.VISIBLE);
                    apater.notifyDataSetChanged();
                    return;
                }
                load_icon. setVisibility(View.GONE);
            } catch (Exception e4) {
            }
        }
    }

    public class rpt_status_async extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timer_update(50000, pd);
        }
        public String doInBackground(String... arg0) {

            try {

                String link= Static_Variable.entypoint1 +"reportstatus.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(userDataDB.get_userid()+":%"+Static_Variable.userid+":%"+Static_Variable.chintha_Id+":%"+reporttype, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }

        }
        public void onPostExecute(String result) {
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reported, Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(getApplicationContext(), (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
