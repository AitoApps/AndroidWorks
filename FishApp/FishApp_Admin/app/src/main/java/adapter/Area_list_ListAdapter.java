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
import android.widget.Toast;
import com.fishappadmin.Add_Area;
import com.fishappadmin.Advt_List;
import com.fishappadmin.Area_Management;
import com.fishappadmin.Client_Catogery;
import com.fishappadmin.ConnectionDetecter;
import com.fishappadmin.FishCatogery;
import com.fishappadmin.R;
import com.fishappadmin.Temp;
import data.Arealist_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Area_list_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;

    public Context context;
    Typeface face;

    public List<Arealist_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String pcatid = "";
    ProgressDialog pd;
    int pos = 0;
    public Area_list_ListAdapter(Activity activity2, List<Arealist_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
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
            convertView = inflater.inflate(R.layout.custom_arealist, null);
        }
        TextView areaname = (TextView) convertView.findViewById(R.id.areaname);
        Button fishes = (Button) convertView.findViewById(R.id.fishes);
        Button clients = (Button) convertView.findViewById(R.id.clients);
        Button advts = (Button) convertView.findViewById(R.id.advts);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        areaname.setText(((Arealist_FeedItem) feedItems.get(position)).getAreaname());
        areaname.setTypeface(face);
        fishes.setTypeface(face);
        clients.setTypeface(face);
        advts.setTypeface(face);
        fishes.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Arealist_FeedItem arealist_FeedItem = (Arealist_FeedItem) feedItems.get(position);
                Arealist_FeedItem item = (Arealist_FeedItem) feedItems.get(position);
                Temp.areaid = item.getSn();
                Temp.areaname = item.getAreaname();
                Intent i = new Intent(context, FishCatogery.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        clients.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Arealist_FeedItem arealist_FeedItem = (Arealist_FeedItem) feedItems.get(position);
                Arealist_FeedItem item = (Arealist_FeedItem) feedItems.get(position);
                Temp.areaid = item.getSn();
                Temp.areaname = item.getAreaname();
                Intent i = new Intent(context, Client_Catogery.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        advts.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Arealist_FeedItem arealist_FeedItem = (Arealist_FeedItem) feedItems.get(position);
                Arealist_FeedItem item = (Arealist_FeedItem) feedItems.get(position);
                Temp.areaid = item.getSn();
                Temp.areaname = item.getAreaname();
                Intent i = new Intent(context, Advt_List.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Arealist_FeedItem arealist_FeedItem = (Arealist_FeedItem) feedItems.get(position);
                    Arealist_FeedItem item = (Arealist_FeedItem) feedItems.get(position);
                    pcatid = item.getSn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this area ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Arealist_FeedItem arealist_FeedItem = (Arealist_FeedItem) feedItems.get(position);
                Arealist_FeedItem item = (Arealist_FeedItem) feedItems.get(position);
                Temp.areaid = item.getSn();
                Temp.areaname = item.getAreaname();
                Temp.deliverytime = item.getDeiverytime();
                Temp.areaedit = 1;
                Intent i = new Intent(context, Add_Area.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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

                String link= Temp.weblink +"delete_area.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(pcatid, "UTF-8");
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
                    ((Area_Management) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
