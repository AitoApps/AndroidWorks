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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.sanji_admin.Add_Franchiesis;
import com.sanji_admin.ConnectionDetecter;
import com.sanji_admin.DatabaseHandler;
import com.sanji_admin.Franchiesis;
import com.sanji_admin.Marketting_Team;
import com.sanji_admin.Temp;
import data.Franchisis_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Franchaisis_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Franchisis_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String mobile1 = "";
    public String name1 = "";
    ProgressDialog pd;
    int pos = 0;
    public String sn1 = "";
    public String status1 = "";

    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }
        public void onPreExecute() {
            Franchaisis_ListAdapter.pd.setMessage("Please wait...");
            Franchaisis_ListAdapter.pd.setCancelable(false);
            Franchaisis_ListAdapter.pd.show();
            Franchaisis_ListAdapter.timerDelayRemoveDialog(50000, Franchaisis_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("deletefranchisis_byadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Franchaisis_ListAdapter.sn1);
                sb3.append("");
                sb2.append(URLEncoder.encode(sb3.toString(), "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb4 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb4.toString();
                    }
                    sb4.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (Franchaisis_ListAdapter.pd != null || Franchaisis_ListAdapter.pd.isShowing()) {
                Franchaisis_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Franchaisis_ListAdapter.context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Franchiesis) Franchaisis_ListAdapter.activity).removeitem(Franchaisis_ListAdapter.pos);
                    return;
                }
                Toasty.info(Franchaisis_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Franchaisis_ListAdapter(Activity activity2, List<Franchisis_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
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
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_franchiesi, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView area = (TextView) convertView.findViewById(R.id.area);
        TextView mobile = (TextView) convertView.findViewById(R.id.mobile);
        Button staffs = (Button) convertView.findViewById(R.id.staffs);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        name.setTypeface(face);
        mobile.setTypeface(face);
        staffs.setTypeface(face);
        Franchisis_FeedItem item = (Franchisis_FeedItem) feedItems.get(position);
        name.setText(item.getName());
        mobile.setText(item.getMobile());
        area.setText(item.getArea());
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Franchisis_FeedItem franchisis_FeedItem = (Franchisis_FeedItem) Franchaisis_ListAdapter.feedItems.get(position);
                    Franchisis_FeedItem item = (Franchisis_FeedItem) Franchaisis_ListAdapter.feedItems.get(position);
                    Franchaisis_ListAdapter.sn1 = item.getSn();
                    Franchaisis_ListAdapter.pos = position;
                    Franchaisis_ListAdapter.showalert_delete("Are you sure want to delete ?");
                } catch (Exception e) {
                }
            }
        });
        staffs.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Franchisis_FeedItem franchisis_FeedItem = (Franchisis_FeedItem) Franchaisis_ListAdapter.feedItems.get(position);
                Temp.fr_sn = ((Franchisis_FeedItem) Franchaisis_ListAdapter.feedItems.get(position)).getSn();
                Intent i = new Intent(Franchaisis_ListAdapter.context, Marketting_Team.class);
                i.setFlags(268435456);
                Franchaisis_ListAdapter.context.startActivity(i);
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Franchisis_FeedItem franchisis_FeedItem = (Franchisis_FeedItem) Franchaisis_ListAdapter.feedItems.get(position);
                Franchisis_FeedItem item = (Franchisis_FeedItem) Franchaisis_ListAdapter.feedItems.get(position);
                Temp.fr_sn = item.getSn();
                Temp.fr_name = item.getName();
                Temp.fr_area = item.getArea();
                Temp.fr_mobile = item.getMobile();
                Temp.fr_address = item.getAddress();
                Temp.fr_agrementid = item.getAgreid();
                Temp.frachedit = 1;
                Intent i = new Intent(Franchaisis_ListAdapter.context, Add_Franchiesis.class);
                i.setFlags(268435456);
                Franchaisis_ListAdapter.context.startActivity(i);
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
                if (Franchaisis_ListAdapter.cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(Franchaisis_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
}
