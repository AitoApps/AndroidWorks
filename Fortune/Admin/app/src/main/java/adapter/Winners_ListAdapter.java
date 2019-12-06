package adapter;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fortune_admin.ConnectionDetecter;
import com.fortune_admin.CustomerView;
import com.fortune_admin.LuckyDrawWinner;
import com.fortune_admin.Lucky_Draw_Types;
import com.fortune_admin.R;
import com.fortune_admin.Temp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import data.Customers_FeedItem;
import data.LuckyDrawPrizes_FeedItem;
import data.Winnerslist_FeedItem;
import es.dmoral.toasty.Toasty;

public class Winners_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    private Context context;
    Typeface face;
    public List<Winnerslist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_dateid="";
    public Winners_ListAdapter(Activity activity2, List<Winnerslist_FeedItem> feedItems2) {
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
            convertView = inflater.inflate(R.layout.custom_winners, null);
        }
        ImageView image=convertView.findViewById(R.id.image);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView mobile=convertView.findViewById(R.id.mobile);
        ImageView priceimg=convertView.findViewById(R.id.priceimg);
        TextView pricename=convertView.findViewById(R.id.pricename);
        ImageView delete=convertView.findViewById(R.id.delete);
        ImageView call=convertView.findViewById(R.id.call);
        RelativeLayout layout=convertView.findViewById(R.id.layout);
        Winnerslist_FeedItem item =feedItems.get(position);
        name.setTypeface(face);
        mobile.setTypeface(face);
        pricename.setTypeface(face);

        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"userimagesmall/"+item.getCustomerid()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);


        RequestOptions rep1 = new RequestOptions().signature(new ObjectKey(item.getPriceimgsig()));
        Glide.with(context).load(Temp.weblink+"prizepics/"+item.getPrizeid()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(priceimg);


        name.setText(item.getCustomername());
        mobile.setText(item.getCustomermobile());
        pricename.setText(item.getPricename());

        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Temp.lcustomerid=item.getCustomerid();
                Intent i = new Intent(context, CustomerView.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Winnerslist_FeedItem item =feedItems.get(position);
                pos = position;
                t_dateid=item.getDataid();
                showalert_delete("Are you sure want to delete ?");
            }
        });

        call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Winnerslist_FeedItem item =feedItems.get(position);
                LuckyDrawWinner h=(LuckyDrawWinner)activity;
                h.call(item.getCustomermobile());

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
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new delete_data().execute();
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

    public class delete_data extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink+"deletewinner.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(t_dateid, "UTF-8");
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
                    ((LuckyDrawWinner) activity).removeitem(pos);
                }
                else{
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
