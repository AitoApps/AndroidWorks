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
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.suhi_chintha.Chintha_Fvrtusers;
import com.suhi_chintha.Chintha_Likes;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.DataDB4;
import com.suhi_chintha.DataDb;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Updates_ChinthaLikes;
import com.suhi_chintha.User_DataDB;
import com.suhi_chintha.Users_Chinthakal;
import com.vanniktech.emoji.EmojiTextView;

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
import java.util.Date;
import java.util.List;

import chintha_data.ChinthaFeeds;
import es.dmoral.toasty.Toasty;

public class FvrtChinthaAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    public AppCompatActivity activity;
    public NetConnection cd;

    public Context context;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    DataDB4 dataDb4;
    public String downlink = "";
    Typeface face;
    private List<ChinthaFeeds> feedStatuses;
    ProgressDialog pd;
    int pos = 0;
    public String statusid = "";
    public User_DataDB userDataDB;
    public class viewHolder extends ViewHolder {
        TextView comment;
        TextView count;
        ImageView delete;
        ImageView fvrt;
        ImageView img;
        TextView mobile;
        TextView name;
        ImageView nofvrt;
        ImageView pictstatus;
        TextView posttime;
        ImageView settings;
        ImageView share;
        EmojiTextView status;
        ImageView statusimage;
        RelativeLayout statuslyt;
        RelativeLayout statuspart;
        ImageView veriicon;

        public viewHolder(View itemView) {
            super(itemView);
            fvrt = (ImageView) itemView.findViewById(R.id.liked);
            nofvrt = (ImageView) itemView.findViewById(R.id.nolike);
            share = (ImageView) itemView.findViewById(R.id.share);
            veriicon = (ImageView) itemView.findViewById(R.id.verifiedicon);
            comment = (TextView) itemView.findViewById(R.id.comment);
            posttime = (TextView) itemView.findViewById(R.id.post_time);
            count = (TextView) itemView.findViewById(R.id.likecount);
            status = (EmojiTextView) itemView.findViewById(R.id.chintha);
            statusimage = (ImageView) itemView.findViewById(R.id.imagetostatus);
            img = (ImageView) itemView.findViewById(R.id.img);
            delete = (ImageView) itemView.findViewById(R.id.del);
            pictstatus = (ImageView) itemView.findViewById(R.id.photostatus);
            settings = (ImageView) itemView.findViewById(R.id.settings);
            statuspart = (RelativeLayout) itemView.findViewById(R.id.statuspart);
            statuslyt = (RelativeLayout) itemView.findViewById(R.id.statuslyt);
            name = (TextView) itemView.findViewById(R.id.name);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public FvrtChinthaAdapter(AppCompatActivity activity2, List<ChinthaFeeds> feedStatuses2) {
        activity = activity2;
        feedStatuses = feedStatuses2;
        context = activity2.getApplicationContext();
        dataDb = new DataDb(context);
        dataDb1 = new DataDB1(context);
        dataDb2 = new DataDB2(context);
        cd = new NetConnection(context);
        userDataDB = new User_DataDB(context);
        pd = new ProgressDialog(activity2);
        dataDb4 = new DataDB4(context);
        face = Typeface.createFromAsset(context.getAssets(), "asset_fonts/font_rachana.ttf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.chinthakal_customlyt, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.bottomview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.full_loaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if (position == feedStatuses.size() && feedStatuses.size() > 10) {
            return 1;
        }
        if (position < feedStatuses.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return feedStatuses.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                final ChinthaFeeds item = (ChinthaFeeds) feedStatuses.get(position);
                final viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.settings. setVisibility(View.GONE);
                viewHolder2.statuslyt.setVisibility(View.VISIBLE);
                viewHolder2.name.setText(item.getName());
                viewHolder2.count.setText("0 ലൈക്ക് ");
                viewHolder2.comment.setText("0 അഭിപ്രായം");
                if (item.get_iscmntlock().equalsIgnoreCase("1")) {
                    viewHolder2.comment.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder2.comment.setVisibility(View.VISIBLE);
                }
                if (item.getverified().equalsIgnoreCase("1")) {
                    viewHolder2.veriicon.setVisibility(View.VISIBLE);
                } else {
                    viewHolder2.veriicon. setVisibility(View.GONE);
                }
                if (item.getshowmobile().equalsIgnoreCase("1")) {
                    viewHolder2.mobile.setText(item.getMobile());
                    viewHolder2.mobile.setVisibility(View.VISIBLE);
                } else {
                    viewHolder2.mobile. setVisibility(View.GONE);
                }
                if (item.getstatustype().equalsIgnoreCase("0")) {
                    viewHolder2.status.setVisibility(View.VISIBLE);
                    viewHolder2.pictstatus. setVisibility(View.GONE);
                    viewHolder2.statusimage.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(item.getStatus())) {
                        try {
                            viewHolder2.status.setText(item.getStatus());
                        } catch (Exception e) {
                        }
                    } else {
                        viewHolder2.status. setVisibility(View.GONE);
                    }
                } else if (item.getstatustype().equalsIgnoreCase("1")) {
                    viewHolder2.status.setVisibility(View.VISIBLE);
                    viewHolder2.pictstatus.setVisibility(View.VISIBLE);
                    viewHolder2.statusimage. setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(item.getStatus())) {
                        try {
                            viewHolder2.status.setText(item.getStatus());
                        } catch (Exception e2) {
                        }
                    } else {
                        viewHolder2.status. setVisibility(View.GONE);
                    }
                    String[] k = item.get_photodim().split("x");
                    float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(dataDb2.get_screenwidth()).floatValue() - 20.0f);
                    viewHolder2.pictstatus.getLayoutParams().height = Math.round(calheight);
                    Glide.with(context).load(item.get_imgurl()).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.pictstatus);
                }
                viewHolder2.pictstatus.setOnLongClickListener(new OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        try {
                            showsave(Static_Variable.wanttosave, viewHolder2.pictstatus);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                });
                Glide.with(context).load(item.get_dppic()).apply(new RequestOptions().signature(new ObjectKey(item.getimgsig()))).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.img);
                if (dataDb2.get_fvrt(item.getId()).equalsIgnoreCase("")) {
                    viewHolder2.nofvrt.setVisibility(View.VISIBLE);
                    viewHolder2.fvrt.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder2.nofvrt.setVisibility(View.INVISIBLE);
                    viewHolder2.fvrt.setVisibility(View.VISIBLE);
                }
                TextView textView = viewHolder2.count;
                StringBuilder sb = new StringBuilder();
                sb.append(item.get_likes());
                sb.append(" ലൈക്ക് ");
                textView.setText(sb.toString());
                if (Integer.parseInt(item.getcmntcount()) < 0) {
                    viewHolder2.comment.setText("0 അഭിപ്രായം ");
                } else {
                    TextView textView2 = viewHolder2.comment;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(item.getcmntcount());
                    sb2.append(" അഭിപ്രായം ");
                    textView2.setText(sb2.toString());
                }
                viewHolder2.posttime.setText(item.get_postdate());
                viewHolder2.status.setOnLongClickListener(new OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        try {
                            Static_Variable.chintha_text = item.getStatus();
                            if (VERSION.SDK_INT < 11) {
                                ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(Static_Variable.chintha_text);
                            } else {
                                ((android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("salmansuhailamp", Static_Variable.chintha_text));
                            }
                            Toasty.info(context, (CharSequence) "Copied", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                        }
                        return false;
                    }
                });

                viewHolder2.status.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(viewHolder2.status.getMaxLines()==Integer.MAX_VALUE)
                        {
                            viewHolder2.status.setMaxLines(8);
                        }
                        else
                        {
                            viewHolder2.status.setMaxLines(Integer.MAX_VALUE);
                        }

                    }
                });
                viewHolder2.comment.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        try {
                            dataDb1.deletecmntdetails();
                            dataDb1.add_cmntdtails(item.getId(), item.getuserid(), item.getName(), item.getStatus(), item.getimgsig(), item.getstatustype(), item.get_imgurl(), item.get_photodim());
                            Static_Variable.viewd_pfle = 1;
                            Intent i = new Intent(context, Lists_ChinthaComments.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        } catch (Exception e) {
                        }
                    }
                });
                viewHolder2.img.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        try {
                            Static_Variable.viewd_pfle = 1;
                            Static_Variable.userid = item.getuserid();
                            Static_Variable.username = item.getName();
                            Intent i = new Intent(context, Image_View.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        } catch (Exception a) {
                           // Toasty.info(context, (CharSequence) Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                viewHolder2.nofvrt.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        try {
                            ArrayList arrayList = userDataDB.get_user();
                            String[] c = (String[]) arrayList.toArray(new String[arrayList.size()]);
                            try {
                                dataDb2.addtofvrt(item.getId(), item.getuserid(), item.getName(), item.getMobile(), Base64.encodeToString(item.getStatus().getBytes(StandardCharsets.UTF_8), 0), item.getshowmobile(), item.getstatustype(), item.get_imgurl(), item.get_photodim());
                                dataDb1.add_fvrtstaus(c[0], item.getId(), item.getuserid(), c[1], item.getStatus(), userDataDB.get_imgsig());
                            } catch (Exception e) {
                            }
                            try {
                                viewHolder2.fvrt.setVisibility(View.VISIBLE);
                                viewHolder2.nofvrt.setVisibility(View.INVISIBLE);
                                String[] p = viewHolder2.count.getText().toString().split(" ");
                                viewHolder2.count.setText((Integer.parseInt(p[0]) + 1)+" ലൈക്ക് ");
                                if (Integer.parseInt(viewHolder2.count.getText().toString().split(" ")[0]) < 0) {
                                    viewHolder2.count.setText("0  ലൈക്ക് ");
                                }
                                new Updates_ChinthaLikes(context).likeupdate();
                            } catch (Exception e2) {
                            }
                        } catch (Exception e3) {
                        }
                    }
                });
                viewHolder2.fvrt.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        try {
                            dataDb2.deletefvrt(item.getId());
                            Toasty.info(context, (CharSequence) "Removed from favourite list", Toast.LENGTH_SHORT).show();
                            viewHolder2.fvrt.setVisibility(View.INVISIBLE);
                            viewHolder2.nofvrt.setVisibility(View.VISIBLE);
                            String[] p = viewHolder2.count.getText().toString().split(" ");
                            viewHolder2.count.setText((Integer.parseInt(p[0]) - 1)+" ലൈക്ക് ");
                            if (Integer.parseInt(viewHolder2.count.getText().toString().split(" ")[0]) < 0) {
                                viewHolder2.count.setText("0  ലൈക്ക് ");
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                viewHolder2.share.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Static_Variable.viewd_pfle = 1;
                        if (item.getstatustype().equalsIgnoreCase("0")) {
                            try {
                                ((Chintha_Fvrtusers) activity).status_share(item.getStatus());
                            } catch (Exception e) {
                            }
                        } else if (item.getstatustype().equalsIgnoreCase("1")) {
                            try {
                                viewHolder2.pictstatus.buildDrawingCache();
                                ((Chintha_Fvrtusers) activity).sharetoall(viewHolder2.pictstatus.getDrawingCache());
                            } catch (Exception e2) {
                            }
                        }
                    }
                });
                viewHolder2.name.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Static_Variable.userid = item.getuserid();
                        Static_Variable.username = item.getName();
                        Static_Variable.usermobile = item.getMobile();
                        Static_Variable.showmobile = item.getshowmobile();
                        Intent i = new Intent(context, Users_Chinthakal.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                viewHolder2.mobile.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Static_Variable.userid = item.getuserid();
                        Static_Variable.username = item.getName();
                        Static_Variable.usermobile = item.getMobile();
                        Static_Variable.showmobile = item.getshowmobile();
                        Intent i = new Intent(context, Users_Chinthakal.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                viewHolder2.count.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Static_Variable.viewd_pfle = 1;
                        Static_Variable.userid = item.getId();
                        Intent i = new Intent(context, Chintha_Likes.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
                viewHolder2.delete.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Static_Variable.userid = item.getId();
                        pos = position;
                        showalert1("Are you sure want to delete status");
                    }
                });
            } catch (Exception e3) {
            }
        } else if (holder instanceof viewHolderFooter) {
            ViewHolder viewHolder3 = holder;
            if (feedStatuses.size() > 0) {
                ((Chintha_Fvrtusers) activity).loadmore();
            }
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert1(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    try {
                        new delete_status().execute(new String[0]);
                    } catch (Exception a) {
                        Toasty.info(context, (CharSequence) Log.getStackTraceString(a), Toast.LENGTH_SHORT).show();
                    }
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

    public void showalert(String message) {
        Builder builder = new Builder(context);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new blockuser().execute(new String[0]);
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
        builder.create().show();
    }

    public void showsave(String message, final ImageView imgview) {
        Builder builder = new Builder(activity);
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
    public class blockuser extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"blockuser.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.userid, "UTF-8");
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
                    Toasty.info(context, (CharSequence) "Blocked", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class delete_status extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"deletestatus.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.userid, "UTF-8");
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
                    ((Chintha_Fvrtusers) activity).removeitem(pos);
                    Toasty.info(context, (CharSequence) "Deleted", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
