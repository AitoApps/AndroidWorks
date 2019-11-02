package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsbag_admin.Add_Apps;
import com.appsbag_admin.Add_View;
import com.appsbag_admin.App_Details;
import com.appsbag_admin.Apps_List;
import com.appsbag_admin.ConnectionDetecter;
import com.appsbag_admin.DatabaseHandler;
import com.appsbag_admin.R;
import com.appsbag_admin.Temp;
import com.appsbag_admin.Video_List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import data.Appslist_FeedItem;
import data.CustomViewlist_FeedItem;

public class Applist_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    Typeface face;
    public List<Appslist_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String pcatid = "";
    ProgressDialog pd;
    int pos = 0;
    public DatabaseHandler db;
    public Applist_ListAdapter(Activity activity2, List<Appslist_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db=new DatabaseHandler(context);
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
            convertView = inflater.inflate(R.layout.custom_applist, null);
        }
        TextView title= (TextView) convertView.findViewById(R.id.title);

        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        Appslist_FeedItem item = (Appslist_FeedItem) feedItems.get(position);


        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"applogo/"+item.getSn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);



        title.setTypeface(face);
        title.setText(item.getAppname());

        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Appslist_FeedItem item = (Appslist_FeedItem) feedItems.get(position);
                    pcatid = item.getSn();
                    pos = position;
                   showalert_delete("Are you sure want to delete this ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Appslist_FeedItem item = (Appslist_FeedItem) feedItems.get(position);
                Temp.appedit = 1;
                Temp.appeditsn=item.getSn();
                Temp.appimgsig=item.getImgsig();
                db.deleteappdetails();
                db.deleteappview();
                db.addappdetails(item.getAppname(),item.getAppurl(),item.getDisctitle(),item.getDiscfooter(),"NA");

                if(!item.getAppdiscs().equalsIgnoreCase(""))
                {
                    if(item.getAppdiscs().contains("##"))
                    {
                        String[] p=item.getAppdiscs().split("##");
                        for(int i=0;i<p.length;i++)
                        {
                            int a=i;
                            i=i+1;
                            int a1=i;
                            i=i+1;
                            int a2=i;
                            i=i+1;
                            int a3=i;
                            i=i+1;
                            int a4=i;
                            i=i+1;
                            int a5=i;
                            i=i+1;
                            int a6=i;
                            db.addappview(p[a],p[a1],p[a2],p[a3],p[a4],"NA",p[a6],p[a5]);
                        }
                    }
                }
                Intent i = new Intent(context, App_Details.class);
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
                    new delete_product().execute();
                    dialog.dismiss();
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
                String link= Temp.weblink +"delete_video.php";
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
                    ((Apps_List) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
