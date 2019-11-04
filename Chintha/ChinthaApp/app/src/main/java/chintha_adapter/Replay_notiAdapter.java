package chintha_adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB2;
import com.suhi_chintha.DataDB4;
import com.suhi_chintha.DataDb;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.List_noti;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Replay;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.User_DataDB;
import com.suhi_chintha.Users_Chinthakal;
import com.vanniktech.emoji.EmojiTextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import chintha_data.Replay_Feed;
import es.dmoral.toasty.Toasty;

public class Replay_notiAdapter extends BaseAdapter {

    public AppCompatActivity activity;
    public NetConnection cd;
    public Context context;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    DataDB4 dataDb4;
    public List<Replay_Feed> feed;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public User_DataDB userDataDB;
    public Replay_notiAdapter(AppCompatActivity activity2, List<Replay_Feed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
        cd = new NetConnection(context);
        pd = new ProgressDialog(activity2);
        dataDb1 = new DataDB1(context);
        dataDb4 = new DataDB4(context);
        dataDb = new DataDb(context);
        dataDb2 = new DataDB2(context);
        userDataDB = new User_DataDB(context);
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
            convertView2 = inflater.inflate(R.layout.customlayout_reply, null);
        } else {
            convertView2 = convertView;
        }
        TextView name = (TextView) convertView2.findViewById(R.id.name);
        ImageView dpPic = (ImageView) convertView2.findViewById(R.id.img);
        EmojiTextView status = (EmojiTextView) convertView2.findViewById(R.id.chintha);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.del);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        TextView posttime = (TextView) convertView2.findViewById(R.id.post_time);
        ImageView blocksinguser = (ImageView) convertView2.findViewById(R.id.blocksinguser);
        Replay_Feed item = (Replay_Feed) feed.get(i);
        name.setText(item.get_name());
        posttime.setText(item.get_postdate());
        ArrayList<String> id1 = userDataDB.get_user();
        if (item.getuserid().equalsIgnoreCase(((String[]) id1.toArray(new String[id1.size()]))[0])) {
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            blocksinguser. setVisibility(View.GONE);
        } else {
            edit. setVisibility(View.GONE);
            delete. setVisibility(View.GONE);
            blocksinguser.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.get_cmnts())) {
            try {
                status.setText(new String(Base64.decode(item.get_cmnts(), 0), StandardCharsets.UTF_8));
                status.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }
        } else {
            status. setVisibility(View.GONE);
        }

        status.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.getMaxLines()==Integer.MAX_VALUE)
                {
                    status.setMaxLines(8);
                }
                else
                {
                    status.setMaxLines(Integer.MAX_VALUE);
                }

            }
        });

        Glide.with(context).load(item.get_dppic()).apply(RequestOptions.circleCropTransform().placeholder(R.drawable.img_placeholder).signature(new ObjectKey(item.get_imgsig()))).transition(DrawableTransitionOptions.withCrossFade()).into(dpPic);

        name.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Replay_Feed item = (Replay_Feed) feed.get(i);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.get_name();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Replay_Feed item = (Replay_Feed) feed.get(i);
                pos = i;
                Static_Variable.chintha_Id = item.get_sn();
                showalert("Are you sure want to delete this Reply ?");
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Replay_Feed item = (Replay_Feed) feed.get(i);
                try {
                    Static_Variable.id_comment =item.get_sn();
                    Lists_ChinthaComments.txtcommentid = item.get_sn();
                    List_noti.comments_txt = new String(Base64.decode(item.get_cmnts(), 0), StandardCharsets.UTF_8);
                    ((Replay) activity).editreply();
                } catch (Exception e) {
                }
            }
        });
        dpPic.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Replay_Feed item = (Replay_Feed) feed.get(i);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.get_name();
                Intent i = new Intent(context, Image_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        blocksinguser.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Replay_Feed item = (Replay_Feed) feed.get(i);
                    Static_Variable.userid = userDataDB.get_userid();
                    Static_Variable.cmnted_userid = item.getuserid();
                    Static_Variable.username = item.get_name();
                    Static_Variable.userId_Blocked = item.getuserid();
                    Static_Variable.name_blocked = item.get_name();
                    Static_Variable.adminidof_blocked = userDataDB.get_userid();
                    showalert_blocksingleuser("താങ്കളുടെ കമന്റ് ബോക്‌സിലോ റിപ്ലേ ബോക്‌സിലോ "+item.get_name()+" എന്തെങ്കിലും ബുദ്ധിമുട്ട് ഉണ്ടാക്കുന്നുവെങ്കില്‍ ബ്ലോക്ക് ചെയ്യാവുന്നതാണ്.");
                } catch (Exception e) {
                }
            }
        });
        return convertView2;
    }

    public void timer_updateTime(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert_blocksingleuser(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("BLOCK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new singleuser_block().execute(new String[0]);
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.nonet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showalert(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new replaydelete().execute(new String[0]);
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
    public class replaydelete extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timer_updateTime(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"deletereplay.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.chintha_Id+":%"+Static_Variable.sn_comments, "UTF-8");
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
                    ((Replay) activity).removeitem1(pos);
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class singleuser_block extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timer_updateTime(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"blocksingleuser.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.adminidof_blocked+":%"+Static_Variable.userId_Blocked, "UTF-8");
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
                    Toasty.info(context, "നന്ദി !! ഇനി "+Static_Variable.name_blocked+" ന്റെ ഭാഗത്ത് നിന്നും ഒരു ബുദ്ധിമുട്ടും കമന്റ് ബോക്‌സിലോ റിപ്ലേ ബോക്‌സിലോ ഉണ്ടായിരിക്കുന്നതല്ല.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
