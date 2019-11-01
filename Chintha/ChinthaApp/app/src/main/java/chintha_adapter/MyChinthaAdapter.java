package chintha_adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.text.ClipboardManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.suhi_chintha.Chintha_Likes;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.DataDB4;
import com.suhi_chintha.DataDb;
import com.suhi_chintha.ExtendTextView;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.My_Chinthakal;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Status_To_Image;
import com.suhi_chintha.User_DataDB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import chintha_data.MyChinthakalFeed;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyChinthaAdapter extends BaseAdapter {

    public AppCompatActivity activity;
    public NetConnection cd;
    public String cmntcount1 = "";
    public String commentlock1 = "";

    public Context context;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    DataDB4 dataDb4;
    Typeface face;

    public List<MyChinthakalFeed> feed;
    public String fvrt1 = "";
    public String imgsig1 = "";
    private LayoutInflater inflater;
    ProgressDialog pd;
    public String photodim = "";
    public String photourl1 = "";
    int pos = 0;
    public String postdate1 = "";
    public String showads1 = "";
    public String status1 = "";
    public String statusid = "";
    public String statusid1 = "";
    public String statustype1 = "";
    public User_DataDB userDataDB;

    public MyChinthaAdapter(AppCompatActivity activity2, List<MyChinthakalFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
        dataDb = new DataDb(context);
        userDataDB = new User_DataDB(context);
        pd = new ProgressDialog(activity2);
        dataDb4 = new DataDB4(context);
        dataDb2 = new DataDB2(context);
        dataDb1 = new DataDB1(context);
        cd = new NetConnection(context);
        face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
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
        TextView commentboxopentext;
        final int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_mystatus, null);
        } else {
            convertView2 = convertView;
        }
        ImageView share = (ImageView) convertView2.findViewById(R.id.share);
        ImageView statusimage = (ImageView) convertView2.findViewById(R.id.imagetostatus);
        final ImageView photostatus = (ImageView) convertView2.findViewById(R.id.photostatus);
        TextView count = (TextView) convertView2.findViewById(R.id.likecount);
        TextView comment = (TextView) convertView2.findViewById(R.id.comment);
        TextView posttime = (TextView) convertView2.findViewById(R.id.post_time);
        ExtendTextView status = (ExtendTextView) convertView2.findViewById(R.id.chintha);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.del);
        ImageView cmntboxopen = (ImageView) convertView2.findViewById(R.id.cmntboxopen);
        TextView commentboxopentext2 = (TextView) convertView2.findViewById(R.id.commentboxopentext);
        MyChinthakalFeed item = (MyChinthakalFeed) feed.get(i);
        View convertView3 = convertView2;
        if (item.get_lockedcomments().equalsIgnoreCase("1")) {
            comment.setVisibility(View.INVISIBLE);
            cmntboxopen.setVisibility(View.VISIBLE);
            commentboxopentext2.setVisibility(View.VISIBLE);
        } else {
            comment.setVisibility(View.VISIBLE);
            cmntboxopen.setVisibility(View.INVISIBLE);
            commentboxopentext2.setVisibility(View.INVISIBLE);
        }
        commentboxopentext2.setText("തുറക്കാം ");
        commentboxopentext2.setTypeface(face);
        if (item.get_statustype().equalsIgnoreCase("0")) {
            status.setVisibility(View.VISIBLE);
            photostatus. setVisibility(View.GONE);
            statusimage.setVisibility(View.VISIBLE);
            try {
                commentboxopentext = commentboxopentext2;
                try {
                    status.setText(new String(Base64.decode(item.getchintha(), 0), StandardCharsets.UTF_8));
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                commentboxopentext = commentboxopentext2;
            }
        } else {
            commentboxopentext = commentboxopentext2;
            if (item.get_statustype().equalsIgnoreCase("1")) {
                status.setVisibility(View.VISIBLE);
                photostatus.setVisibility(View.VISIBLE);
                statusimage. setVisibility(View.GONE);
                edit. setVisibility(View.GONE);
                try {
                    status.setText(new String(Base64.decode(item.getchintha(), 0), StandardCharsets.UTF_8));
                } catch (Exception e3) {
                }
                String[] k = item.get_photodim().split("x");
                float ogheight = Float.valueOf(dataDb2.get_screenwidth()).floatValue() - 20.0f;
                float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * ogheight;
                photostatus.getLayoutParams().height = Math.round(calheight);
                Glide.with(context).load(item.getphotourl()).transition(DrawableTransitionOptions.withCrossFade()).into(photostatus);
            }
        }
        count.setText(item.get_fvrt()+" ലൈക്ക് ");
        comment.setText(item.getcmntcount()+" അഭിപ്രായം ");
        posttime.setText(item.getpostdate());
        share.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    MyChinthakalFeed item = (MyChinthakalFeed) feed.get(i);
                    if (item.get_statustype().equalsIgnoreCase("0")) {
                        try {
                            ((My_Chinthakal) activity).status_share(new String(Base64.decode(item.getchintha(), 0), StandardCharsets.UTF_8));
                        } catch (Exception e) {
                        }
                    } else if (item.get_statustype().equalsIgnoreCase("1")) {
                        try {
                            My_Chinthakal h = (My_Chinthakal) activity;
                            photostatus.buildDrawingCache();
                            h.sharetoall(photostatus.getDrawingCache());
                        } catch (Exception e2) {
                        }
                    }
                } catch (Exception e3) {
                }
            }
        });
        status.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                Object obj = feed.get(i);
                try {
                    String text1 = new String(Base64.decode(((MyChinthakalFeed) feed.get(i)).getchintha(), 0), StandardCharsets.UTF_8);
                    if (VERSION.SDK_INT < 11) {
                        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(text1);
                    } else {
                        ((android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmanmambip", text1));
                    }
                    Toasty.info(context, (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
                return false;
            }
        });
        photostatus.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                try {
                    showsave(Static_Variable.wanttosave, photostatus);
                } catch (Exception e) {
                }
                return true;
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                pos = i;
                Static_Variable.chintha_Id = ((MyChinthakalFeed) feed.get(i)).get_chinthaid();
                showalert("Are you sure want to delete this chintha");
            }
        });
        count.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Static_Variable.userid = ((MyChinthakalFeed) feed.get(i)).get_chinthaid();
                Intent i = new Intent(context, Chintha_Likes.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    MyChinthakalFeed item = (MyChinthakalFeed) feed.get(i);
                    Static_Variable.chintha_Id = item.get_chinthaid();
                    Static_Variable.chintha_text = new String(Base64.decode(item.getchintha(), 0), StandardCharsets.UTF_8);
                    ((My_Chinthakal) activity).edit_status();
                } catch (Exception e) {
                }
            }
        });
        comment.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    MyChinthakalFeed item = (MyChinthakalFeed) feed.get(i);
                    dataDb1.deletecmntvisible();
                    dataDb1.add_cmntvisible("1");
                    String txtstatus = new String(Base64.decode(item.getchintha(), 0), StandardCharsets.UTF_8);
                    ArrayList arrayList = userDataDB.get_user();
                    String[] c = (String[]) arrayList.toArray(new String[arrayList.size()]);
                    dataDb1.deletecmntdetails();
                    dataDb1.add_cmntdtails(item.get_chinthaid(), c[0], c[1], txtstatus, item.getimg_sig(), item.get_statustype(), item.getphotourl(), item.get_photodim());
                    Intent i = new Intent(context, Lists_ChinthaComments.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        cmntboxopen.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyChinthakalFeed item = (MyChinthakalFeed) feed.get(i);
                pos = i;
                statusid1 = item.get_chinthaid();
                status1 = item.getchintha();
                commentlock1 = item.get_lockedcomments();
                showads1 = item.getshowads();
                fvrt1 = item.get_fvrt();
                cmntcount1 = item.getcmntcount();
                postdate1 = item.getpostdate();
                imgsig1 = item.getimg_sig();
                statustype1 = item.get_statustype();
                photourl1 = item.getphotourl();
                photodim = item.get_photodim();
                Static_Variable.chintha_Id = item.get_chinthaid();
                showalert_commentblokopen();
            }
        });
        commentboxopentext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyChinthakalFeed item = (MyChinthakalFeed) feed.get(i);
                pos = i;
                statusid1 = item.get_chinthaid();
                status1 = item.getchintha();
                fvrt1 = item.get_fvrt();
                photodim = item.get_photodim();
                commentlock1 = item.get_lockedcomments();
                showads1 = item.getshowads();
                cmntcount1 = item.getcmntcount();
                postdate1 = item.getpostdate();
                imgsig1 = item.getimg_sig();
                statustype1 = item.get_statustype();
                photourl1 = item.getphotourl();
                Static_Variable.chintha_Id = item.get_chinthaid();
                showalert_commentblokopen();
            }
        });
        statusimage.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    Static_Variable.sm_text = new String(Base64.decode(((MyChinthakalFeed) feed.get(i)).getchintha(), 0), StandardCharsets.UTF_8);
                    Intent i = new Intent(context, Status_To_Image.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        return convertView3;
    }

    public void showalert_commentblokopen() {
        Builder builder = new Builder(activity);
        builder.setMessage("ഈ സ്റ്റാറ്റസിന്റെ കമന്റ് ബോക്‌സ് തുറക്കാം അല്ലെ ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    commentblockopen();
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(R.id.text)).setTypeface(face);
        } catch (Exception e) {
        }
    }

    public void commentblockopen() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerLoading_Time(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"updatecommentblockopen.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", Static_Variable.chintha_Id).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                                if (result.contains("ok")) {
                                    My_Chinthakal j = (My_Chinthakal) activity;
                                    j.changeitem(pos, statusid1, status1, fvrt1, cmntcount1, postdate1, imgsig1, statustype1, photourl1, photodim, "0", showads1);
                                }
                                else
                                {
                                    Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void showalert(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    statusdelete();
                } else {
                    Toasty.info(context, (CharSequence) "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
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

    public void timerLoading_Time(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void statusdelete() {
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
        timerLoading_Time(50000, pd);
        OkHttpClient httpClient = new OkHttpClient();
        String url = Static_Variable.entypoint1+"deletestatus.php";
        httpClient.newCall(new Request.Builder().url(url).post(new FormBody.Builder().add("item", Static_Variable.chintha_Id).build()).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (response.isSuccessful()) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            if (pd != null || pd.isShowing()) {
                                pd.dismiss();
                                if (result.contains("ok")) {
                                    Toasty.info(context, (CharSequence) "Updated", Toast.LENGTH_SHORT).show();
                                    ((My_Chinthakal) activity).removeitem(pos);
                                    return;
                                }
                                Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void showsave(String message, final ImageView imgview) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog4, int id) {
                imgview.buildDrawingCache();
                Bitmap bm = imgview.getDrawingCache();
                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"Status_Chinthakal/pic_"+timeStamp+".png"));
                    bm.compress(CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toasty.info(context, (CharSequence) "Saved", Toast.LENGTH_SHORT).show();
                    try {
                        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(Environment.getExternalStorageDirectory()+File.separator+"Status_Chinthakal/pic_"+timeStamp+".png"))));
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                    Toasty.info(context, (CharSequence) "Unable to Save", Toast.LENGTH_SHORT).show();
                }
                dialog4.dismiss();
                return;
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        try {
            ((TextView) alert.findViewById(R.id.text)).setTypeface(face);
        } catch (Exception e) {
        }
    }
}
