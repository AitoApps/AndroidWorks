package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.chintha_admin.ConnectionDetecter;
import com.chintha_admin.DatabaseHandler;
import com.chintha_admin.MK_EASY_RECHARGE_NEW;
import com.chintha_admin.R;
import com.chintha_admin.Tempvariable;
import data.Recharge_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class RechargeListAdapterNew extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    public DatabaseHandler db;
    public List<Recharge_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtsn;
    public String txtuserid;
    public View views;

    public RechargeListAdapterNew(Activity activity2, List<Recharge_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.recharge_customlayout, null);
            }
            views = convertView;
            TextView operator = (TextView) convertView.findViewById(R.id.operator);
            TextView coupon = (TextView) convertView.findViewById(R.id.coupon);
            ImageView verify = (ImageView) convertView.findViewById(R.id.verify);
            ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
            Recharge_FeedItem item = (Recharge_FeedItem) feedItems.get(position);
            operator.setTag(Integer.valueOf(position));
            coupon.setTag(Integer.valueOf(position));
            verify.setTag(Integer.valueOf(position));
            delete.setTag(Integer.valueOf(position));
            operator.setText(item.getoperator());
            coupon.setText(item.getcoponcode());
            verify.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    int position = ((Integer) arg0.getTag()).intValue();
                    Recharge_FeedItem item = (Recharge_FeedItem) feedItems.get(position);
                    txtsn = item.getsn();
                    pos = position;
                    txtuserid = item.getuserid();
                    showalert1("Are you sure want to agree this recharge ?");
                }
            });
            delete.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    int position = ((Integer) v.getTag()).intValue();
                    Recharge_FeedItem item = (Recharge_FeedItem) feedItems.get(position);
                    txtsn = item.getsn();
                    pos = position;
                    txtuserid = item.getuserid();
                    showalert2("Are you sure want to DIS -DONT ALLOW agree this recharge ?");
                }
            });
        } catch (Exception e) {
        }
        return convertView;
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
                    new agreecoupon().execute(new String[0]);
                } else {
                    Toast.makeText(context, "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
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
                    new dontagreecoupon().execute(new String[0]);
                } else {
                    Toast.makeText(context, "Please make sure your internet connection is active", Toast.LENGTH_SHORT).show();
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
    public class agreecoupon extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink3+"agreerecharge.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtsn+":%"+txtuserid, "UTF-8");
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
                    Toast.makeText(context, "Payment Agreed", Toast.LENGTH_SHORT).show();
                    ((MK_EASY_RECHARGE_NEW) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class dontagreecoupon extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink3+"disagreerecharge.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtsn+":%"+txtuserid, "UTF-8");
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
                    Toast.makeText(context, "DIS Agreed", Toast.LENGTH_SHORT).show();
                    ((MK_EASY_RECHARGE_NEW) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
