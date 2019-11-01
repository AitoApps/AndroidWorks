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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.daydeal_shop.Add_Product;
import com.daydeal_shop.ConnectionDetecter;
import com.daydeal_shop.DatabaseHandler;
import com.daydeal_shop.Image_Viewer;
import com.daydeal_shop.Product_Management;
import com.daydeal_shop.R;
import com.daydeal_shop.Temp;
import com.daydeal_shop.UserDatabaseHandler;
import data.Productlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Productlist_ListAdapter extends BaseAdapter {
    
    public Activity activity;
    public String cashback1;
    public String catid1;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    public String discription1;
    Typeface face;
    public List<Productlist_FeedItem> feedItems;
    public String imgsig2;
    private LayoutInflater inflater;
    public String itemkeyword1;
    public String itemname1;
    public String offerprice1;
    public String orginalprice1;
    ProgressDialog pd;
    int pos = 0;
    public String status1;
    public String txtproductid;
    UserDatabaseHandler udb;
    public Productlist_ListAdapter(Activity activity2, List<Productlist_FeedItem> feedItems2) {
        txtproductid = "";
        status1 = "0";
        itemname1 = "";
        offerprice1 = "";
        orginalprice1 = "";
        discription1 = "";
        imgsig2 = "";
        itemkeyword1 ="";
        cashback1 = "";
        catid1 = "";
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        udb = new UserDatabaseHandler(context);
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
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_productlist, null);
        } else {
            convertView2 = convertView;
        }
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView offerprice = (TextView) convertView2.findViewById(R.id.offerprice);
        TextView orginalprice = (TextView) convertView2.findViewById(R.id.orginalprice);
        TextView status = (TextView) convertView2.findViewById(R.id.status);
        TextView txtofferprice = (TextView) convertView2.findViewById(R.id.txtofferprice);
        TextView txtofferpricedot = (TextView) convertView2.findViewById(R.id.txtofferpricedot);
        TextView txtorginalprice = (TextView) convertView2.findViewById(R.id.txtorginalprice);
        TextView txtorginalpricedot = (TextView) convertView2.findViewById(R.id.txtorginalpricedot);
        TextView txtcashback = (TextView) convertView2.findViewById(R.id.txtcashback);
        TextView cashback = (TextView) convertView2.findViewById(R.id.cashback);
        Button disable = (Button) convertView2.findViewById(R.id.disable);
        TextView txtstatus = (TextView) convertView2.findViewById(R.id.txtstatus);
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        itemname.setTypeface(face);
        offerprice.setTypeface(face);
        orginalprice.setTypeface(face);
        cashback.setTypeface(face);
        status.setTypeface(face);
        txtofferprice.setTypeface(face);
        txtofferpricedot.setTypeface(face);
        txtorginalprice.setTypeface(face);
        txtorginalpricedot.setTypeface(face);
        txtcashback.setTypeface(face);
        txtstatus.setTypeface(face);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
        String rupee = context.getResources().getString(R.string.Rs);
        itemname.setText(item.getitemname());

        offerprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getofferprice())));
        orginalprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getorginalprice())));
        cashback.setText(rupee+String.format("%.2f", Float.parseFloat(item.getCashback())));


        if (item.getstatus().equalsIgnoreCase("1")) {
            disable.setVisibility(View.VISIBLE);
            status.setText("Verified");
            status.setTextColor(Color.parseColor("#2a6827"));
        } else {
            if (item.getstatus().equalsIgnoreCase("3")) {
                disable.setVisibility(View.GONE);
                status.setText("Waiting for verification");
                status.setTextColor(Color.parseColor("#fe4a4a"));
            } else if (item.getstatus().equalsIgnoreCase("4")) {
                disable.setVisibility(View.GONE);
                status.setText("Unable to Verify");
                status.setTextColor(Color.parseColor("#fe4a4a"));
            } else if (item.getstatus().equalsIgnoreCase("0")) {
                disable.setVisibility(View.VISIBLE);
                status.setText("Inactive");
            }
        }
        if (item.getstatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getstatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(Color.parseColor("#b8b8b8"));
        }
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
        Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getsn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                Temp.img_title = item.getitemname();
                Temp.img_imgsig = item.getimgsig1();
                Temp.img_link = Temp.weblink+"productpic/"+item.getsn()+".jpg";
                Intent i = new Intent(context, Image_Viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                    txtproductid = item.getsn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this product ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                Temp.edit_catid = item.getcatid();
                Temp.edit_productid = item.getsn();
                Temp.edit_itemname = item.getitemname();
                Temp.edit_offerprice = item.getofferprice();
                Temp.edit_orginalprice = item.getorginalprice();
                Temp.edit_dscription = item.getdiscription();
                Temp.edit_imgsig1 = item.getimgsig1();
                Temp.edit_itemkeywords = item.getItemkeyword();
                Temp.edit_cashback = item.getCashback();
                Temp.isproductedit = 1;
                Intent i = new Intent(context, Add_Product.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(position);
                txtproductid = item.getsn();
                itemname1 = item.getitemname();
                offerprice1 = item.getofferprice();
                orginalprice1 = item.getorginalprice();
                discription1 = item.getdiscription();
                imgsig2 = item.getimgsig1();
                itemkeyword1 = item.getItemkeyword();
                cashback1 = item.getCashback();
                catid1 = item.getcatid();
                pos = position;
                if (item.getstatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this product ?");
                }
                else
                {
                    status1 = "1";
                    showalert_changestatus("Are you sure want to activate this product ?");

                }
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
    public class delete_product extends AsyncTask<String, Void, String> {

        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"deleteproductbyshopid.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid, "UTF-8");
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
                    ((Product_Management) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
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

                String link= Temp.weblink +"changeproductstatusbyshops.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+status1+":%"+udb.get_shopid(), "UTF-8");
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
                if (result.contains("ok,")) {
                    String[] p = result.trim().split(",");
                    Toasty.info(context, "Status Changed", Toast.LENGTH_SHORT).show();
                    ((Product_Management) activity).changeitem(pos, txtproductid, itemname1, offerprice1, orginalprice1, discription1, imgsig2, p[1], itemkeyword1, cashback1, catid1);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
