package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chintha_admin.ConnectionDetecter;
import com.chintha_admin.DatabaseHandler;
import com.chintha_admin.ExpandableTextView;
import com.chintha_admin.R;
import com.chintha_admin.Report_Status;
import com.chintha_admin.Tempvariable;
import data.Report_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import okhttp3.MediaType;

public class ReportListAdapter extends BaseAdapter {
    public final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    public DatabaseHandler db;
    public List<Report_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String reportid = "";
    public String statusid = "";
    public String statustype = "";
    public String userid = "";

    public ReportListAdapter(Activity activity2, List<Report_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db = new DatabaseHandler(context);
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
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
        View convertView2;
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView2 = inflater.inflate(R.layout.report_customlayout, null);
            } else {
                convertView2 = convertView;
            }
            try {
                ExpandableTextView status = (ExpandableTextView) convertView2.findViewById(R.id.status);
                TextView reporttype = (TextView) convertView2.findViewById(R.id.reporttype);
                ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
                ImageView block = (ImageView) convertView2.findViewById(R.id.block);
                ImageView verify = (ImageView) convertView2.findViewById(R.id.verify);
                TextView txtreporttype = (TextView) convertView2.findViewById(R.id.txtreporttype);
                ImageView photostatus = (ImageView) convertView2.findViewById(R.id.photostatus);
                try {
                    Report_FeedItem item = (Report_FeedItem) feedItems.get(position);
                    delete.setTag(Integer.valueOf(position));
                    block.setTag(Integer.valueOf(position));
                    verify.setTag(Integer.valueOf(position));
                    if (!TextUtils.isEmpty(item.getstatus())) {
                        try {
                            status.setText(item.getstatus());
                            status.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                        }
                    } else {
                        status.setVisibility(View.GONE);
                    }
                    if (item.getstatustype1().equalsIgnoreCase("0")) {
                        photostatus.setVisibility(View.GONE);
                    } else if (item.getstatustype1().equalsIgnoreCase("1")) {
                        photostatus.setVisibility(View.VISIBLE);
                        String[] k = item.getphotodemension().split("x");
                        float calheight = (Float.valueOf(k[1]).floatValue() / Float.valueOf(k[0]).floatValue()) * (Float.valueOf(db.getscreenwidth()).floatValue() - 20.0f);
                        photostatus.getLayoutParams().height = Math.round(calheight);
                        Glide.with(context).load(item.getphotourl()).transition(DrawableTransitionOptions.withCrossFade()).into(photostatus);
                    }
                    if (item.getreporttype().equalsIgnoreCase("1")) {
                        reporttype.setText("Chatting");
                    } else if (item.getreporttype().equalsIgnoreCase("2")) {
                        reporttype.setText("Thery");
                    } else if (item.getreporttype().equalsIgnoreCase("3")) {
                        reporttype.setText("Parihasam");
                    } else if (item.getreporttype().equalsIgnoreCase("4")) {
                        reporttype.setText("Ashleelam");
                    } else if (item.getreporttype().equalsIgnoreCase("5")) {
                        reporttype.setText("Other");
                    }
                    if (item.getstatustype().equalsIgnoreCase("0")) {
                        txtreporttype.setText("സ്റ്റാറ്റസ് ");
                    } else {
                        txtreporttype.setText("പാട്ട്‌ ");
                    }
                    delete.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            int position = ((Integer) arg0.getTag()).intValue();
                            Report_FeedItem item = (Report_FeedItem) feedItems.get(position);
                            statusid = item.getstatusid();
                            reportid = item.getsn();
                            statustype = item.getstatustype();
                            pos = position;
                            showalert1("Are you sure want to delete this status");
                        }
                    });
                    block.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            int position = ((Integer) arg0.getTag()).intValue();
                            Report_FeedItem item = (Report_FeedItem) feedItems.get(position);
                            userid = item.getstatususerid();
                            reportid = item.getsn();
                            pos = position;
                            statustype = item.getstatustype();
                            showalert2("Are you sure want to blockuser");
                        }
                    });
                    verify.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            int position = ((Integer) v.getTag()).intValue();
                            Report_FeedItem item = (Report_FeedItem) feedItems.get(position);
                            reportid = item.getsn();
                            pos = position;
                            statustype = item.getstatustype();
                            showalert3("Are you sure want to verify");
                        }
                    });
                } catch (Exception e2) {
                    Toast.makeText(context, Log.getStackTraceString(e2), Toast.LENGTH_LONG).show();

                }

                return convertView2;

            } catch (Exception e3) {

            }
        } catch (Exception e4) {

        }
        return null;
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
                    new statusdelete().execute(new String[0]);
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
                    new userblock().execute(new String[0]);
                } else {
                    Toast.makeText(context, Tempvariable.nointernet, Toast.LENGTH_SHORT).show();
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

    public void showalert3(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new statusverify().execute(new String[0]);
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
    public class statusdelete extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"deletestatus_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(statusid+":%"+reportid+":%"+statustype, "UTF-8");
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
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Report_Status) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class statusverify extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"verifystatus_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(reportid, "UTF-8");
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
                    Toast.makeText(context, "verifeid", Toast.LENGTH_SHORT).show();
                    ((Report_Status) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class userblock extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Tempvariable.weblink+"blockuser_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(userid+":%"+reportid+":%"+statusid, "UTF-8");
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
                    Toast.makeText(context, "blocked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Temporary Error ! Please try later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
