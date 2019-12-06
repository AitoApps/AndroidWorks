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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fishappadmin.Add_Fish_Catogery;
import com.fishappadmin.ConnectionDetecter;
import com.fishappadmin.FishCatogery;
import com.fishappadmin.FishList;
import com.fishappadmin.R;
import com.fishappadmin.Temp;
import data.FishCatgery_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class FishCatogery_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;

    public Context context;
    Typeface face;

    public List<FishCatgery_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String pcatid = "";
    ProgressDialog pd;
    int pos = 0;
    public FishCatogery_ListAdapter(Activity activity2, List<FishCatgery_FeedItem> feedItems2) {
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
            convertView = inflater.inflate(R.layout.custom_fishcatogery, null);
        }
        TextView catogeryname = (TextView) convertView.findViewById(R.id.catogeryname);
        Button fishes = (Button) convertView.findViewById(R.id.fishes);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        FishCatgery_FeedItem item = (FishCatgery_FeedItem) feedItems.get(position);
        catogeryname.setText(item.getCatogery());
        catogeryname.setTypeface(face);
        fishes.setTypeface(face);
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"fishcatogery/"+item.getSn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        fishes.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FishCatgery_FeedItem fishCatgery_FeedItem = (FishCatgery_FeedItem) feedItems.get(position);
                FishCatgery_FeedItem item = (FishCatgery_FeedItem) feedItems.get(position);
                Temp.clientcatsn = item.getSn();
                Temp.clientcatname = item.getCatogery();
                Intent i = new Intent(context, FishList.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    FishCatgery_FeedItem fishCatgery_FeedItem = (FishCatgery_FeedItem) feedItems.get(position);
                    FishCatgery_FeedItem item = (FishCatgery_FeedItem) feedItems.get(position);
                    pcatid = item.getSn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this catogery ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FishCatgery_FeedItem fishCatgery_FeedItem = (FishCatgery_FeedItem) feedItems.get(position);
                FishCatgery_FeedItem item = (FishCatgery_FeedItem) feedItems.get(position);
                Temp.clientcatsn = item.getSn();
                Temp.clientcatname = item.getCatogery();
                Temp.clientcatimgsig = item.getImgsig();
                Temp.clientcatedit = 1;
                Intent i = new Intent(context, Add_Fish_Catogery.class);
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

                String link= Temp.weblink +"delete_fishcatogery.php";
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
                    ((FishCatogery) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
