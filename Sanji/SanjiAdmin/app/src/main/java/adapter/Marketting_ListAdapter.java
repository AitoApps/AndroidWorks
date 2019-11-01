package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.sanji_admin.Add_Marketting_Staff;
import com.sanji_admin.ConnectionDetecter;
import com.sanji_admin.DatabaseHandler;
import com.sanji_admin.Marketting_Team;
import com.sanji_admin.R;
import com.sanji_admin.Temp;
import data.Marketting_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Marketting_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Marketting_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String mobile1 = "";
    public String name1 = "";
    ProgressDialog pd;
    int pos = 0;
    public String sn1 = "";
    public String status1 = "";

    public class delete_product extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"deletemarketting_byadmin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(sn1, "UTF-8");
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
                    Toasty.info(context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Marketting_Team) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class productchangestatus extends AsyncTask<String, Void, String> {
        public productchangestatus() {
        }
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("changemktstatus.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sn1);
                sb3.append(":%");
                sb3.append(status1);
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(context, "Status Changed", Toast.LENGTH_SHORT).show();
                    ((Marketting_Team) activity).changeitem(pos, sn1, name1, mobile1, status1);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class relogin_product extends AsyncTask<String, Void, String> {
        public relogin_product() {
        }
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("reloginmkt_byadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sn1);
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(context, "Login Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public Marketting_ListAdapter(Activity activity2, List<Marketting_FeedItem> feedItems2) {
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
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_marketting, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView mobile = (TextView) convertView.findViewById(R.id.mobile);
        Button disable = (Button) convertView.findViewById(R.id.disable);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView relogin = (ImageView) convertView.findViewById(R.id.relogin);
        name.setTypeface(face);
        mobile.setTypeface(face);
        disable.setTypeface(face);
        Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(position);
        if (item.getstatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getstatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(-7829368);
        }
        name.setText(item.getname());
        mobile.setText(item.getmobile());
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Marketting_FeedItem marketting_FeedItem = (Marketting_FeedItem) feedItems.get(position);
                    Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(position);
                    sn1 = item.getsn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this marketting staff ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Marketting_FeedItem marketting_FeedItem = (Marketting_FeedItem) feedItems.get(position);
                Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(position);
                Temp.edit_mkt_id = item.getsn();
                Temp.edit_mkt_name = item.getname();
                Temp.edit_mkt_mobile = item.getmobile();
                Temp.ismarketedit = 1;
                Intent i = new Intent(context, Add_Marketting_Staff.class);
                i.setFlags(268435456);
                context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Marketting_FeedItem marketting_FeedItem = (Marketting_FeedItem) feedItems.get(position);
                Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(position);
                sn1 = item.getsn();
                mobile1 = item.getmobile();
                name1 = item.getname();
                status1 = item.getstatus();
                pos = position;
                if (item.getstatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this product ?");
                    return;
                }
                status1 = "1";
                showalert_changestatus("Are you sure want to activate this product ?");
            }
        });
        relogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Marketting_FeedItem marketting_FeedItem = (Marketting_FeedItem) feedItems.get(position);
                    Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(position);
                    sn1 = item.getsn();
                    pos = position;
                    showalert_reloagin("Are you sure want to enable login ?");
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
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void showalert_changestatus(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new productchangestatus().execute(new String[0]);
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void showalert_reloagin(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new relogin_product().execute(new String[0]);
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
