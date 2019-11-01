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
import com.suhi_chintha.ExtendTextView;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.Lists_ChinthaComments;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Replay;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.User_DataDB;
import com.suhi_chintha.Users_Chinthakal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import chintha_data.CommentFeed;
import es.dmoral.toasty.Toasty;

public class CommentsAdapter extends BaseAdapter {

    public AppCompatActivity activity;
    public NetConnection cd;

    public Context context;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    public DataDB2 dataDb2;
    DataDB4 dataDb4;

    public List<CommentFeed> feed;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public User_DataDB userDataDB;

    public CommentsAdapter(AppCompatActivity activity2, List<CommentFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
        cd = new NetConnection(context);
        pd = new ProgressDialog(activity2);
        dataDb1 = new DataDB1(context);
        dataDb = new DataDb(context);
        dataDb2 = new DataDB2(context);
        userDataDB = new User_DataDB(context);
        dataDb4 = new DataDB4(context);
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
            convertView2 = inflater.inflate(R.layout.comments_custom, null);
        } else {
            convertView2 = convertView;
        }
        TextView name = (TextView) convertView2.findViewById(R.id.name);
        ImageView dppic = (ImageView) convertView2.findViewById(R.id.img);
        final ExtendTextView status = (ExtendTextView) convertView2.findViewById(R.id.chintha);
        ImageView reportstatus1 = (ImageView) convertView2.findViewById(R.id.reportstatus1);
        TextView posttime = (TextView) convertView2.findViewById(R.id.post_time);
        TextView replay = (TextView) convertView2.findViewById(R.id.replay);
        ImageView delete1 = (ImageView) convertView2.findViewById(R.id.delete1);
        ImageView blocksinguser = (ImageView) convertView2.findViewById(R.id.blocksinguser);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.del);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        CommentFeed item = (CommentFeed) feed.get(i);
        name.setText(item.get_name());
        if (item.getreplay().equalsIgnoreCase("0")) {
            replay.setText("Reply");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(item.getreplay());
            sb.append(" Reply");
            replay.setText(sb.toString());
        }
        posttime.setText(item.get_postdate());
        ArrayList<String> id1 = userDataDB.get_user();
        TextView textView = posttime;
        ArrayList arrayList = id1;
        if (item.getuserid().equalsIgnoreCase(((String[]) id1.toArray(new String[id1.size()]))[0])) {
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            blocksinguser. setVisibility(View.GONE);
        } else {
            edit.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
            blocksinguser.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getcomments())) {
            try {
                status.setText(new String(Base64.decode(item.getcomments(), 0), StandardCharsets.UTF_8));
                status.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }
        } else {
            status. setVisibility(View.GONE);
        }
        new RequestOptions().placeholder((int) R.drawable.img_noimage);
        Glide.with(context).load(item.get_dppic()).apply(RequestOptions.circleCropTransform().signature(new ObjectKey(item.get_imgsig()))).transition(DrawableTransitionOptions.withCrossFade()).into(dppic);
        dppic.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                CommentFeed item = (CommentFeed) feed.get(i);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.get_name();
                Intent i = new Intent(context, Image_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                CommentFeed item = (CommentFeed) feed.get(i);
                try {
                    Static_Variable.id_comment = item.getsn();
                    Lists_ChinthaComments.txtcommentid = item.getsn();
                    Lists_ChinthaComments.txtcomments = new String(Base64.decode(item.getcomments(), 0), StandardCharsets.UTF_8);
                    ((Lists_ChinthaComments) activity).status_edit();
                } catch (Exception e) {
                }
            }
        });
        name.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CommentFeed item = (CommentFeed) feed.get(i);
                Static_Variable.userid = item.getuserid();
                Static_Variable.username = item.get_name();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                CommentFeed item = (CommentFeed) feed.get(i);
                pos = i;
                Static_Variable.id_comment = item.getsn();
                showalert("Are you sure want to delete this comment");
            }
        });
        replay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    CommentFeed item = (CommentFeed) feed.get(i);
                    dataDb2.add_replycmnt(item.getsn(), item.getuserid(), item.get_name(), item.get_dppic(), status.getText().toString(), item.get_imgsig());
                    dataDb2.add_rplyvisible("1");
                    Intent i = new Intent(context, Replay.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (Exception e) {
                }
            }
        });
        delete1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CommentFeed item = (CommentFeed) feed.get(i);
                pos = i;
                Static_Variable.id_comment = item.getsn();
                Static_Variable.cmnted_userid = item.getuserid();
                showalert2("Are you sure want to delete this comment");
            }
        });
        blocksinguser.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    CommentFeed item = (CommentFeed) feed.get(i);
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
        reportstatus1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Static_Variable.userid = ((CommentFeed) feed.get(i)).getuserid();
                showalert6("Are you sure want to block img_user");
            }
        });
        return convertView2;
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new delete_status().execute(new String[0]);
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

    public void showalert2(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new statusdeleteadmin().execute(new String[0]);
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

    public void showalert6(String message) {
        Builder builder = new Builder(activity);
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

    public void showalert_blocksingleuser(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("BLOCK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new blocksingleuser().execute(new String[0]);
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
    public class blocksingleuser extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
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
                Toasty.info(context, Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
            }
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

                String link= Static_Variable.entypoint1 +"blockuser_admin_direc.php";
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

                String link= Static_Variable.entypoint1 +"deletecomments.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.id_comment+":%"+Static_Variable.chintha_Id, "UTF-8");
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
                    ((Lists_ChinthaComments) activity).removeitem(pos);
                } else {
                    Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class statusdeleteadmin extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"deletecomments_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(Static_Variable.id_comment+":%"+Static_Variable.chintha_Id+":%"+Static_Variable.cmnted_userid, "UTF-8");
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
                    ((Lists_ChinthaComments) activity).removeitem(pos);
                } else {
                    Toasty.info(context, (CharSequence) "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
