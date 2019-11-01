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
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.DatabaseHandler;
import com.sanji_shop.Image_Viewer;
import com.sanji_shop.R;
import com.sanji_shop.SalesmanList_for_Select;
import com.sanji_shop.Temp;
import com.sanji_shop.UserDatabaseHandler;
import data.Salesmanlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Salesmanlist_Choose_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Salesmanlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String sn1 = "";
    UserDatabaseHandler udb;

    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }
        public void onPreExecute() {
            Salesmanlist_Choose_ListAdapter.pd.setMessage("Updating...Please wait");
            Salesmanlist_Choose_ListAdapter.pd.setCancelable(false);
            Salesmanlist_Choose_ListAdapter.pd.show();
            Salesmanlist_Choose_ListAdapter.timerDelayRemoveDialog(50000, Salesmanlist_Choose_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("shops_picksalesman.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Salesmanlist_Choose_ListAdapter.sn1);
                sb3.append(":%");
                sb3.append(Salesmanlist_Choose_ListAdapter.udb.get_shopid());
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
            if (Salesmanlist_Choose_ListAdapter.pd != null || Salesmanlist_Choose_ListAdapter.pd.isShowing()) {
                Salesmanlist_Choose_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Salesmanlist_Choose_ListAdapter.context, "Sucessfully Choosed", Toast.LENGTH_SHORT).show();
                    ((SalesmanList_for_Select) Salesmanlist_Choose_ListAdapter.activity).exitfun();
                    return;
                }
                Toasty.info(Salesmanlist_Choose_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Salesmanlist_Choose_ListAdapter(Activity activity2, List<Salesmanlist_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        udb = new UserDatabaseHandler(context);
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
            convertView = inflater.inflate(R.layout.custom_salesmanlist_choose, null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView salesmanname = (TextView) convertView.findViewById(R.id.salesmanname);
        TextView place = (TextView) convertView.findViewById(R.id.place);
        TextView mobile = (TextView) convertView.findViewById(R.id.mobile);
        TextView idcard = (TextView) convertView.findViewById(R.id.idcard);
        Button pick = (Button) convertView.findViewById(R.id.pick);
        salesmanname.setTypeface(face);
        place.setTypeface(face);
        mobile.setTypeface(face);
        idcard.setTypeface(face);
        Salesmanlist_FeedItem item = (Salesmanlist_FeedItem) feedItems.get(position);
        salesmanname.setText(item.getname());
        mobile.setText(item.getmobile());
        place.setText(item.getplace());
        idcard.setText(item.getidcard());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
        RequestManager with = Glide.with(context);
        StringBuilder sb = new StringBuilder();
        sb.append(Temp.weblink);
        sb.append("salesmanpicsmall/");
        sb.append(item.getsn());
        sb.append(".jpg");
        with.load(sb.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Salesmanlist_FeedItem salesmanlist_FeedItem = (Salesmanlist_FeedItem) Salesmanlist_Choose_ListAdapter.feedItems.get(position);
                Salesmanlist_FeedItem item = (Salesmanlist_FeedItem) Salesmanlist_Choose_ListAdapter.feedItems.get(position);
                Temp.img_title = item.getname();
                Temp.img_imgsig = item.getimgsig();
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("salesmanpic/");
                sb.append(item.getsn());
                sb.append(".jpg");
                Temp.img_link = sb.toString();
                Intent i = new Intent(Salesmanlist_Choose_ListAdapter.context, Image_Viewer.class);
                i.setFlags(268435456);
                Salesmanlist_Choose_ListAdapter.context.startActivity(i);
            }
        });
        pick.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Salesmanlist_FeedItem salesmanlist_FeedItem = (Salesmanlist_FeedItem) Salesmanlist_Choose_ListAdapter.feedItems.get(position);
                    Salesmanlist_FeedItem item = (Salesmanlist_FeedItem) Salesmanlist_Choose_ListAdapter.feedItems.get(position);
                    Salesmanlist_Choose_ListAdapter.sn1 = item.getsn();
                    Salesmanlist_Choose_ListAdapter.pos = position;
                    Salesmanlist_Choose_ListAdapter.showalert_delete("Are you sure want to choose this salesman for your shop ?");
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
                if (Salesmanlist_Choose_ListAdapter.cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(Salesmanlist_Choose_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
