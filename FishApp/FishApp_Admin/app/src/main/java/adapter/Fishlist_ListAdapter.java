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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fishappadmin.Add_Fish;
import com.fishappadmin.ConnectionDetecter;
import com.fishappadmin.DatabaseHandler;
import com.fishappadmin.FishList;
import com.fishappadmin.R;
import com.fishappadmin.Temp;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import data.Fishlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Fishlist_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    public DatabaseHandler db;
    public String discription1;
    Typeface face;
    public List<Fishlist_FeedItem> feedItems;
    public String fishname1;
    public String fishsn1;
    public String imgsig1;
    private LayoutInflater inflater;
    public String isstock;
    public String pcatid;
    ProgressDialog pd;
    int pos = 0;
    public String price1;
    public String qty1;
    public String stock1;
    public String unit1;
    public Fishlist_ListAdapter(Activity activity2, List<Fishlist_FeedItem> feedItems2) {
        String str = "";
        pcatid = str;
        isstock = str;
        fishsn1 = str;
        fishname1 = str;
        qty1 = str;
        unit1 = str;
        price1 = str;
        stock1 = str;
        imgsig1 = str;
        discription1 = str;
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2;
        final int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_fishlist, null);
        } else {
            convertView2 = convertView;
        }
        String rupee = context.getResources().getString(R.string.Rs);
        TextView fishid = (TextView) convertView2.findViewById(R.id.fishid);
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView price = (TextView) convertView2.findViewById(R.id.price);
        TextView qty = (TextView) convertView2.findViewById(R.id.qty);
        TextView unit = (TextView) convertView2.findViewById(R.id.unit);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        LabeledSwitch labeledSwitch = (LabeledSwitch) convertView2.findViewById(R.id.stock);
        itemname.setTypeface(face);
        price.setTypeface(face);
        qty.setTypeface(face);
        unit.setTypeface(face);
        labeledSwitch.setTypeface(face);
        Fishlist_FeedItem item = (Fishlist_FeedItem) feedItems.get(i);
        if (item.getStock().equalsIgnoreCase("1")) {
            labeledSwitch.setOn(true);
        } else {
            labeledSwitch.setOn(false);
        }
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    isstock = "1";
                } else {
                    isstock = "0";
                }
                if (cd.isConnectingToInternet()) {
                    Fishlist_FeedItem fishlist_FeedItem = (Fishlist_FeedItem) feedItems.get(i);
                    Fishlist_FeedItem item = (Fishlist_FeedItem) feedItems.get(i);
                    pos = i;
                    pcatid = item.getSn();
                    fishsn1 = item.getSn();
                    fishname1 = item.getFishname();
                    qty1 = item.getQty();
                    unit1 = item.getUnit();
                    price1 = item.getPrice();
                    stock1 = item.getStock();
                    imgsig1 = item.getImgsig();
                    discription1 = item.getDiscription();
                    new stock_update().execute(new String[0]);
                    return;
                }
                Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
            }
        });
        itemname.setText(item.getFishname());
        price.setText(" | "+rupee+item.getPrice());
        qty.setText(item.getQty());
        unit.setText(item.getUnit());
        fishid.setText("Fish ID : "+item.getSn());

        String str = rupee;
        TextView textView = fishid;
        TextView textView2 = itemname;
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));

        Glide.with(context).load(Temp.weblink+"fishpicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Fishlist_FeedItem fishlist_FeedItem = (Fishlist_FeedItem) feedItems.get(i);
                    Fishlist_FeedItem item = (Fishlist_FeedItem) feedItems.get(i);
                    pcatid = item.getSn();
                    pos = i;
                    showalert_delete("Are you sure want to delete this fish ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Fishlist_FeedItem fishlist_FeedItem = (Fishlist_FeedItem) feedItems.get(i);
                Fishlist_FeedItem item = (Fishlist_FeedItem) feedItems.get(i);
                Temp.fishsn = item.getSn();
                Temp.fishname = item.getFishname();
                Temp.fishqty = item.getQty();
                Temp.fishunit = item.getUnit();
                Temp.fishprice = item.getPrice();
                Temp.fishstock = item.getStock();
                Temp.fishimgsig = item.getImgsig();
                Temp.fishdiscription = item.getDiscription();
                Temp.fishedit = 1;
                Intent i = new Intent(context, Add_Fish.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return convertView2;
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

                String link= Temp.weblink +"deletefish.php";
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
                    ((FishList) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class stock_update extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Updating...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"updatefishstock.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(pcatid+":%"+isstock, "UTF-8");
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
                    ((FishList) activity).changeitem(pos, fishsn1, fishname1, qty1, unit1, price1, isstock, imgsig1, discription1);
                    return;
                }
                else
                {
                    Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


}
