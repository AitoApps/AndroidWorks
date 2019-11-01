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
import android.util.Log;
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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.daydeal_admin.ConnectionDetecter;
import com.daydeal_admin.DatabaseHandler;
import com.daydeal_admin.Image_Viewer;
import com.daydeal_admin.OfferList_Shops;
import com.daydeal_admin.R;
import com.daydeal_admin.Temp;
import data.Offerlist_Shops_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Offerlist_Shops_ListAdapter extends BaseAdapter {

    public Activity activity;
    public String cashback1;
    public ConnectionDetecter cd;

    public Context context;
    DatabaseHandler db;
    public String discription1;
    Typeface face;

    public List<Offerlist_Shops_FeedItem> feedItems;
    public String imgsig2;
    private LayoutInflater inflater;
    public String itemkeyword1;
    public String itemname1;
    public String offerprice1;
    public String orginalprice1;
    ProgressDialog pd;
    int pos = 0;
    public String status1;
    public String txtitemname;
    public String txtproductid;

    public Offerlist_Shops_ListAdapter(Activity activity2, List<Offerlist_Shops_FeedItem> feedItems2) {
        txtproductid = "";
        status1 = "0";
        txtitemname = "";
        itemname1 = "";
        offerprice1 = "";
        orginalprice1 = "";
        discription1 = "";
        imgsig2 = "";
        itemkeyword1 ="";
        cashback1 = "";
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
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_offerlist_shops, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
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
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        TextView txtstatus = (TextView) convertView2.findViewById(R.id.txtstatus);
        View convertView3 = convertView2;
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
        Offerlist_Shops_FeedItem item = (Offerlist_Shops_FeedItem) feedItems.get(position);
        String rupee = context.getResources().getString(R.string.Rs);
        itemname.setText(item.getitemname());
        offerprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getofferprice())));
        orginalprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getorginalprice())));
        cashback.setText(rupee+String.format("%.2f", Float.parseFloat(item.getCashback())));
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
        Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getsn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);



        if (item.getstatus().equalsIgnoreCase("1")) {
            status.setText("Verified");
            status.setTextColor(Color.parseColor("#2a6827"));
        } else {

            if (item.getstatus().equalsIgnoreCase("3")) {
                status.setText("Waiting for verification");
                status.setTextColor(Color.parseColor("#fe4a4a"));
            } else {
                if (item.getstatus().equalsIgnoreCase("4")) {
                    status.setText("Unable to Verify");
                    status.setTextColor(Color.parseColor("#fe4a4a"));
                } else if (item.getstatus().equalsIgnoreCase("0")) {
                    status.setText("Inactive");
                    status.setTextColor(Color.parseColor("#fe4a4a"));
                }
            }
        }
        if (item.getstatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getstatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(Color.parseColor("#b8b8b8"));
        }

        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Offerlist_Shops_FeedItem item = (Offerlist_Shops_FeedItem) feedItems.get(position);
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
                    Offerlist_Shops_FeedItem item = (Offerlist_Shops_FeedItem) feedItems.get(position);
                    txtproductid = item.getsn();
                    txtitemname = item.getitemname();
                    pos = position;
                    showalert_delete("Are you sure want to delete this product ?");
                } catch (Exception e) {
                }
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Offerlist_Shops_FeedItem item = (Offerlist_Shops_FeedItem) feedItems.get(position);
                txtproductid = item.getsn();
                itemname1 = item.getitemname();
                offerprice1 = item.getofferprice();
                orginalprice1 = item.getorginalprice();
                discription1 = item.getdiscription();
                imgsig2 = item.getimgsig1();
                itemkeyword1 = item.getItemkeyword();
                cashback1 = item.getCashback();
                txtitemname = item.getitemname();
                pos = position;
                if (item.getstatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this product ?");
                }
                else
                {
                    status1="0";
                    showalert_changestatus("Are you sure want to activate this product ?");
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

                String link= Temp.weblink +"deleteshopproductbyadmin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+Temp.edit_shopid+":%"+txtitemname, "UTF-8");
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
                    ((OfferList_Shops) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class productchangestatus extends AsyncTask<String, Void, String> {
        public productchangestatus() {
        }


        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"changeproductstatusbyadmin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+status1+":%"+Temp.edit_shopid+":%"+txtitemname, "UTF-8");
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
                    Toasty.info(context, "Status Changed", Toast.LENGTH_SHORT).show();
                    ((OfferList_Shops) activity).changeitem(pos, txtproductid, itemname1, offerprice1, orginalprice1, discription1, imgsig2, status1, itemkeyword1, cashback1);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
