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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.dailybill_admin.Add_New_Product;
import com.dailybill_admin.ConnectionDetecter;
import com.dailybill_admin.Product_List;
import com.dailybill_admin.R;
import com.dailybill_admin.Temp;

import data.Productlist_FeedItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Productlist_ListAdapter extends BaseAdapter {
    public Activity activity;
    public String catogery1;
    public ConnectionDetecter cd;
    public Context context;
    Typeface face;
    public List<Productlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    public String itemkeywords1;
    public String itemname1;
    ProgressDialog pd;
    int pos = 0;
    public String sn1;
    public String status1;
    public String unit1;
    public String imgsig1="";

    public Productlist_ListAdapter(Activity activity2, List<Productlist_FeedItem> feedItems2) {
        sn1 = "";
        itemname1 = "";
        itemkeywords1 = "";
        catogery1 = "";
        unit1 = "";
        imgsig1="";
        status1 = "";
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        face = Typeface.createFromAsset(context.getAssets(), "proximanormal.ttf");
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
            convertView = inflater.inflate(R.layout.custom_productlist, null);
        }
        TextView itemname = (TextView) convertView.findViewById(R.id.itemname);
        Button disable = (Button) convertView.findViewById(R.id.disable);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView image=convertView.findViewById(R.id.image);
        Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
        String string = context.getResources().getString(R.string.Rs);
        if (item.getStatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getStatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        itemname.setText(item.getItemname());

        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgisg()));
        Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                    sn1 = item.getSn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this product ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                Temp.itemname = item.getItemname();
                Temp.itemkeywords = item.getItemkeywords();
                Temp.catogery = item.getCatogery();
                Temp.unit = item.getUnit();
                Temp.productsn = item.getSn();
                Temp.isproductedit = 1;
                Intent i = new Intent(context, Add_New_Product.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                sn1 = item.getSn();
                itemname1 = item.getItemname();
                itemkeywords1 = item.getItemkeywords();
                unit1 = item.getUnit();
                catogery1 = item.getCatogery();
                status1 = item.getStatus();
                imgsig1=item.getImgisg();
                pos = position;
                if (item.getStatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this product ?");
                }
                else
                {
                    status1="1";
                    showalert_changestatus("Are you sure want to activate this product ?");
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

    public void showalert_changestatus(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new productchangestatus().execute(new String[0]);
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
                String link= Temp.weblink+"admin_deleteproduct_admin.php";
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
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Product_List) activity).removeitem(pos);
                    return;
                }
                Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class productchangestatus extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"admin_changeproductstatus.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(sn1+":%"+status1, "UTF-8");
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
                    Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                    ((Product_List) activity).changeitem(pos, sn1, itemname1, itemkeywords1, catogery1, unit1, imgsig1, status1);
                    return;
                }
                Toast.makeText(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
