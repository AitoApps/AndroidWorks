package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hello_khd_verification_admin.ConnectionDetecter;
import com.hello_khd_verification_admin.MainActivity;
import com.hello_khd_verification_admin.R;
import com.hello_khd_verification_admin.Temp;
import com.hello_khd_verification_admin.UserDatabaseHandler;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import data.Verification_FeedItem;

public class Verificationlist_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    Typeface face;
    public List<Verification_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String pcatid = "",pmark="";
    ProgressDialog pd;
    public UserDatabaseHandler udb;
    int pos = 0;
    public Verificationlist_ListAdapter(Activity activity2, List<Verification_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        udb=new UserDatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
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
            convertView = inflater.inflate(R.layout.custom_stdulist, null);
        }
        TextView name= (TextView) convertView.findViewById(R.id.name);
        TextView itemname= (TextView) convertView.findViewById(R.id.itemname);
        TextView schoolname= (TextView) convertView.findViewById(R.id.schoolname);
        TextView txtmark=convertView.findViewById(R.id.txtmark);
        EditText mark=convertView.findViewById(R.id.mark);

        ImageView confirm = (ImageView) convertView.findViewById(R.id.confirm);

        Verification_FeedItem item = (Verification_FeedItem) feedItems.get(position);


        name.setTypeface(face);
        itemname.setTypeface(face);
        schoolname.setTypeface(face);
        mark.setTypeface(face);
        txtmark.setTypeface(face);

        name.setText(item.getStudentname());
        itemname.setText(item.getItemname());
        schoolname.setText(item.getSchoolname());
        mark.setText(item.getMark());


        confirm.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Verification_FeedItem item = (Verification_FeedItem) feedItems.get(position);
                    pcatid = item.getSn();
                    pmark=item.getMark();
                     pos = position;
                   showalert_delete("This mark is correct ?");
                } catch (Exception e) {
                }
            }
        });
        return convertView;
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert_delete(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toast.makeText(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public class delete_product extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink +"mark_verification.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(pcatid+":%"+pmark+":%"+udb.get_userid(), "UTF-8");
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
                    ((MainActivity) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
