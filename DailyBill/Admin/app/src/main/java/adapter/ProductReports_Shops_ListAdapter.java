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
import com.dailybill_admin.ConnectionDetecter;
import com.dailybill_admin.DatabaseHandler;
import com.dailybill_admin.ImageViewer;
import com.dailybill_admin.Product_Report_Shops;
import com.dailybill_admin.R;
import com.dailybill_admin.Temp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import data.Product_Reports_FeedItem;
import es.dmoral.toasty.Toasty;

public class ProductReports_Shops_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Product_Reports_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtproductid = "";
    public String txtproductname = "";
    public String txtshopid = "";
    public String txtstatus = "0";
    public ProductReports_Shops_ListAdapter(Activity activity2, List<Product_Reports_FeedItem> feedItems2) {
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
        int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_productreports_byshop, null);
        } else {
            convertView2 = convertView;
        }
        TextView txtofferprice = (TextView) convertView2.findViewById(R.id.txtofferprice);
        TextView txtofferpricedot = (TextView) convertView2.findViewById(R.id.txtofferpricedot);
        TextView txtorginalprice = (TextView) convertView2.findViewById(R.id.txtorginalprice);
        TextView txtorginalpricedot = (TextView) convertView2.findViewById(R.id.txtorginalpricedot);
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView offerprice = (TextView) convertView2.findViewById(R.id.offerprice);
        TextView orginalprice = (TextView) convertView2.findViewById(R.id.orginalprice);
        TextView txtunit = (TextView) convertView2.findViewById(R.id.txtunit);
        TextView unit = (TextView) convertView2.findViewById(R.id.unit);
        TextView txtminqty = (TextView) convertView2.findViewById(R.id.txtminqty);
        TextView minqty = (TextView) convertView2.findViewById(R.id.minqty);
        Button active = (Button) convertView2.findViewById(R.id.active);
        Button inactive = (Button) convertView2.findViewById(R.id.inactive);
        ImageView unverify = (ImageView) convertView2.findViewById(R.id.unverify);
        View convertView3 = convertView2;
        Product_Reports_FeedItem item = (Product_Reports_FeedItem) feedItems.get(i);
        ImageView unverify2 = unverify;
        String rupee = context.getResources().getString(R.string.Rs);
        txtofferprice.setTypeface(face);
        txtofferpricedot.setTypeface(face);
        txtorginalprice.setTypeface(face);
        txtorginalpricedot.setTypeface(face);
        txtunit.setTypeface(face);
        txtminqty.setTypeface(face);
        List<String> lst_unittype = new ArrayList<>();
        TextView textView = txtofferprice;
        lst_unittype.add("Select Unit Type");
        lst_unittype.add("ഗ്രാം");
        lst_unittype.add("കിലോ");
        lst_unittype.add("എണ്ണം");
        lst_unittype.add("ലിറ്റര്‍");
        lst_unittype.add("മില്ലി ലിറ്റര്‍");
        lst_unittype.add("പാക്കറ്റ്‌");
        lst_unittype.add("തൂക്കം");
        lst_unittype.add("ബോക്‌സ്‌");
        lst_unittype.add("ജോടി");
        lst_unittype.add("സെറ്റ്");
        lst_unittype.add("ബോട്ടില്‍");
        lst_unittype.add("മീറ്റര്‍");
        unit.setText((CharSequence) lst_unittype.get(Integer.parseInt(item.getUnit())));
        minqty.setText(item.getMinqty());
        itemname.setText(item.getItemname());
        offerprice.setTypeface(face);
        orginalprice.setTypeface(face);
        itemname.setTypeface(face);

        offerprice.setText(rupee+item.getOfferprice());
        orginalprice.setText(rupee+item.getOrginalprice());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig1()));
        Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getSn()+"_1.jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        if (item.getStatus().equalsIgnoreCase("1")) {

            active.setVisibility(View.VISIBLE);
            inactive.setVisibility(View.GONE);
        } else {
            if (item.getStatus().equalsIgnoreCase("4")) {
                active.setVisibility(View.GONE);
                inactive.setVisibility(View.VISIBLE);
            }
        }
        active.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Product_Reports_FeedItem item = (Product_Reports_FeedItem) feedItems.get(position);
                pos =position;
                txtproductid = item.getSn();
                txtshopid = item.getShopid();
                txtproductname = item.getItemname();
                txtstatus = "4";
                if (cd.isConnectingToInternet()) {
                    showalert_inactive("Are you sure want to in active this product ?");
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        inactive.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Product_Reports_FeedItem item = (Product_Reports_FeedItem) feedItems.get(position);
                pos = position;
                txtproductid = item.getSn();
                txtshopid = item.getShopid();
                txtproductname = item.getItemname();
                txtstatus = "1";
                if (cd.isConnectingToInternet()) {
                    showalert_active("Are you sure want to active this product ?");
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Product_Reports_FeedItem item = (Product_Reports_FeedItem) feedItems.get(position);
                Temp.img_title = item.getItemname();
                Temp.img_imgsig = item.getImgsig1();
                Temp.img_link = Temp.weblink+"productpicsmall/"+item.getSn()+"_1.jpg";
                Intent i = new Intent(context, ImageViewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        unverify2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Product_Reports_FeedItem item = (Product_Reports_FeedItem) feedItems.get(position);
                pos = position;
                txtproductid = item.getSn();
                txtshopid = item.getShopid();
                txtproductname = item.getItemname();
                if (cd.isConnectingToInternet()) {
                    showalert_unverify("Are you sure want to Delete this product ?");
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView3;
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }

    public void showalert_unverify(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new unverify_product().execute(new String[0]);
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

    public void showalert_active(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new active_product().execute(new String[0]);
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

    public void showalert_inactive(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new inactive_product().execute(new String[0]);
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
    public class active_product extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"activeproduct_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+txtshopid+":%"+txtproductname+":%"+txtstatus, "UTF-8");
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
                    Toasty.info(context, "Activation Successfull", Toast.LENGTH_SHORT).show();
                    ((Product_Report_Shops) activity).changeitem(pos, txtstatus);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class inactive_product extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"inactiveproduct_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+txtshopid+":%"+txtproductname+":%"+txtstatus, "UTF-8");
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
                    Toasty.info(context, "InActivation Successfull", Toast.LENGTH_SHORT).show();
                    ((Product_Report_Shops) activity).changeitem(pos, txtstatus);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class unverify_product extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"deleteadmindproduct.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+txtshopid+":%"+txtproductname, "UTF-8");
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
                    ((Product_Report_Shops) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
