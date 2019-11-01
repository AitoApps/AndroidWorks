package chintha_adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.bumptech.glide.signature.ObjectKey;
import com.suhi_chintha.Chintha_bloked;
import com.suhi_chintha.DataDB4;
import com.suhi_chintha.Image_View;
import com.suhi_chintha.NetConnection;
import com.suhi_chintha.R;
import com.suhi_chintha.Static_Variable;
import com.suhi_chintha.User_DataDB;
import com.suhi_chintha.Users_Chinthakal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import chintha_data.LikeFeed;
import es.dmoral.toasty.Toasty;

public class BlckdAdapter extends BaseAdapter {

    public AppCompatActivity activity;
    NetConnection cd;
    public Context context;
    DataDB4 db4;
    public List<LikeFeed> feedItems;
    private LayoutInflater inflater;
    int pos = 0;
    ProgressDialog progress;
    User_DataDB udb;

    public BlckdAdapter(AppCompatActivity activity2, List<LikeFeed> feed) {
        activity = activity2;
        feedItems = feed;
        context = activity2.getApplicationContext();
        progress = new ProgressDialog(activity2);
        cd = new NetConnection(context);
        udb = new User_DataDB(context);
        db4 = new DataDB4(context);
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.blocked_custom, null);
        }
        ImageView displypic = (ImageView) convertView.findViewById(R.id.img);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
        ImageView chinthas_histry = (ImageView) convertView.findViewById(R.id.chintha_histroy);
        LikeFeed item = (LikeFeed) feedItems.get(position);
        ((TextView) convertView.findViewById(R.id.name)).setText(item.getName());
        Glide.with(context).load(item.get_dppic()).apply(new RequestOptions().signature(new ObjectKey(item.get_imgsig()))).transition(DrawableTransitionOptions.withCrossFade()).into(displypic);
        chinthas_histry.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LikeFeed item = (LikeFeed) feedItems.get(position);
                pos = position;
                Static_Variable.adminidof_blocked = udb.get_userid();
                Static_Variable.userId_Blocked = item.get_userid();
                Static_Variable.name_blocked = item.getName();
                showalert_blocksingleuser("ശ്രദ്ധിക്കുക !! ഇനി "+item.getName()+" ന്റെ ഭാഗത്ത് നിന്നും ഒരു ബുദ്ധിമുട്ടും ഉണ്ടാകില്ല എന്നുറപ്പുണ്ടെങ്കില്‍ മാത്രം അണ്‍ബ്ലോക്ക് ചെയ്യുക.");
            }
        });
        displypic.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                LikeFeed item = (LikeFeed) feedItems.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Image_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LikeFeed item = (LikeFeed) feedItems.get(position);
                Static_Variable.userid = item.get_userid();
                Static_Variable.username = item.getName();
                Intent i = new Intent(context, Users_Chinthakal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView;
    }

    public void showalert_blocksingleuser(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("UNBLOCK", new DialogInterface.OnClickListener() {
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
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();
        }
        public String doInBackground(String... arg0) {
            try {

                String link= Static_Variable.entypoint1 +"unblocksingleuser.php";
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
            if (progress != null || progress.isShowing()) {
                progress.dismiss();
                if (result.contains("ok")) {
                    ((Chintha_bloked) activity).removeitem(pos);
                    Toasty.info(context, (CharSequence) "അണ്‍ബ്ലോക്ക് ചെയ്തു", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toasty.info(context, (CharSequence) Static_Variable.reason_tmpprobs, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
