package chintha_adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.suhi_chintha.DataDB1;
import com.suhi_chintha.DataDB4;
import com.suhi_chintha.DataDb;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.Users_Chinthakal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import chintha_data.SearchsFeed;
import es.dmoral.toasty.Toasty;

public class SearchsAdapter extends BaseAdapter {
    private AppCompatActivity activity;
    public NetConnection cd;
    public Context context;
    public DataDb dataDb;
    public DataDB1 dataDb1;
    DataDB4 dataDb4;
    public List<SearchsFeed> feed;
    private LayoutInflater inflater;
    ProgressDialog pd;
    public SearchsAdapter(AppCompatActivity activity2, List<SearchsFeed> feed2) {
        activity = activity2;
        feed = feed2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        dataDb = new DataDb(context);
        dataDb1 = new DataDB1(context);
        dataDb4 = new DataDB4(context);
        cd=new NetConnection(context);
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.searchlist_custom, null);
        }
        ImageView dppic = (ImageView) convertView.findViewById(R.id.img);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        ImageView history = (ImageView) convertView.findViewById(R.id.chintha_histroy);
        final ImageView likes = (ImageView) convertView.findViewById(R.id.liked);
        final ImageView nolikes = (ImageView) convertView.findViewById(R.id.nolike);
        ImageView blockuser = (ImageView) convertView.findViewById(R.id.blockuser);
        ImageView unblockuser = (ImageView) convertView.findViewById(R.id.unblockuser);
        SearchsFeed item = (SearchsFeed) feed.get(position);
        ((TextView) convertView.findViewById(R.id.name)).setText(item.getName());
        if (dataDb.getfvr_usr(item.get_userid()).equalsIgnoreCase("")) {
            nolikes.setVisibility(View.VISIBLE);
            likes.setVisibility(View.INVISIBLE);
        } else {
            nolikes.setVisibility(View.INVISIBLE);
            likes.setVisibility(View.VISIBLE);
        }
        Glide.with(context).load(item.getDppic()).apply(new RequestOptions().placeholder((int) R.drawable.img_noimage)).transition(DrawableTransitionOptions.withCrossFade()).into(dppic);
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SearchsFeed item = (SearchsFeed) feed.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        dppic.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                SearchsFeed item = (SearchsFeed) feed.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Image_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        likes.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                dataDb.del_fvrtusr(((SearchsFeed) feed.get(position)).get_userid());
                Toasty.info(context, (CharSequence) "Removed from favourite list", Toast.LENGTH_SHORT).show();
                likes.setVisibility(View.INVISIBLE);
                nolikes.setVisibility(View.VISIBLE);
            }
        });
        nolikes.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    SearchsFeed item = (SearchsFeed) feed.get(position);
                    dataDb.add_fvrtuser(item.get_userid(), item.getName());
                    likes.setVisibility(View.VISIBLE);
                    nolikes.setVisibility(View.INVISIBLE);
                    Toasty.info(context, (CharSequence) "Added to favourite list", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });
        history.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SearchsFeed item = (SearchsFeed) feed.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        unblockuser.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Static_Variable.userid = ((SearchsFeed) feed.get(position)).get_userid();
                showalert3("Are you sure want to unblock ?");
            }
        });
        blockuser.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Static_Variable.userid = ((SearchsFeed) feed.get(position)).get_userid();
                showalert2("Are you sure want to block ?");
            }
        });
        return convertView;
    }

    public void showalert3(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new unblock().execute(new String[0]);
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
                    new userblock().execute(new String[0]);
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

    public void timerupdate_Time(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }
    public class unblock extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerupdate_Time(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"unblockuser.php";
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
                    Toasty.info(context, (CharSequence) "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class userblock extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerupdate_Time(50000, pd);
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
                    Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
