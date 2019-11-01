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
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.daydeal_admin.ConnectionDetecter;
import com.daydeal_admin.DatabaseHandler;
import com.daydeal_admin.Image_Viewer;
import com.daydeal_admin.Product_Verification;
import com.daydeal_admin.R;
import com.daydeal_admin.Temp;
import data.Offerlist_Verification_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Offerlist_Verifcation_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;

    public Context context;
    DatabaseHandler db;
    Typeface face;

    public List<Offerlist_Verification_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtitemname;
    public String txtproductid;
    public String txtshopid;

    public Offerlist_Verifcation_ListAdapter(Activity activity2, List<Offerlist_Verification_FeedItem> feedItems2) {
        txtproductid = "";
        txtshopid = "";
        txtitemname = "";
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
            convertView2 = inflater.inflate(R.layout.custom_offerlist_verification, null);
        } else {
            convertView2 = convertView;
        }
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView offerprice = (TextView) convertView2.findViewById(R.id.offerprice);
        TextView orginalprice = (TextView) convertView2.findViewById(R.id.orginalprice);
        TextView txtofferprice = (TextView) convertView2.findViewById(R.id.txtofferprice);
        TextView txtofferpricedot = (TextView) convertView2.findViewById(R.id.txtofferpricedot);
        TextView txtorginalprice = (TextView) convertView2.findViewById(R.id.txtorginalprice);
        TextView txtorginalpricedot = (TextView) convertView2.findViewById(R.id.txtorginalpricedot);
        TextView txtcashback = (TextView) convertView2.findViewById(R.id.txtcashback);
        TextView cashback = (TextView) convertView2.findViewById(R.id.cashback);
        TextView txtshopname = (TextView) convertView2.findViewById(R.id.txtshopname);
        ImageView unverify = (ImageView) convertView2.findViewById(R.id.unverify);
        TextView txtshopplace = (TextView) convertView2.findViewById(R.id.txtshopplace);
        ImageView verify = (ImageView) convertView2.findViewById(R.id.verify);
        final TextView txtmobile1 = (TextView) convertView2.findViewById(R.id.txtmobile1);
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        final TextView txtmobile2 = (TextView) convertView2.findViewById(R.id.txtmobile2);
        View convertView3 = convertView2;
        itemname.setTypeface(face);
        offerprice.setTypeface(face);
        orginalprice.setTypeface(face);
        cashback.setTypeface(face);
        txtofferprice.setTypeface(face);
        txtofferpricedot.setTypeface(face);
        txtorginalprice.setTypeface(face);
        txtorginalpricedot.setTypeface(face);
        txtcashback.setTypeface(face);
        txtshopname.setTypeface(face);
        txtshopplace.setTypeface(face);
        txtmobile1.setTypeface(face);
        txtmobile2.setTypeface(face);
        Offerlist_Verification_FeedItem item = (Offerlist_Verification_FeedItem) feedItems.get(position);
        String rupee = context.getResources().getString(R.string.Rs);
        itemname.setText(item.getitemname());

        offerprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getofferprice())));
        orginalprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getorginalprice())));
        cashback.setText(rupee+String.format("%.2f", Float.parseFloat(item.getCashback())));
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
        Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getsn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);


        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Offerlist_Verification_FeedItem item = (Offerlist_Verification_FeedItem) feedItems.get(position);
                Temp.img_title = item.getitemname();
                Temp.img_imgsig = item.getimgsig1();
                Temp.img_link = Temp.weblink+"productpic/"+item.getsn()+".jpg";
                Intent i = new Intent(context, Image_Viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Offerlist_Verification_FeedItem item = (Offerlist_Verification_FeedItem) feedItems.get(position);
                    txtproductid = item.getsn();
                    txtshopid = item.getShopid();
                    txtitemname = item.getitemname();
                    pos = position;
                    showalert_verify("Are you sure want to verify this product ?");
                } catch (Exception e) {
                }
            }
        });
        Offerlist_Verification_FeedItem offerlist_Verification_FeedItem = item;
        unverify.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Offerlist_Verification_FeedItem item = (Offerlist_Verification_FeedItem) feedItems.get(position);
                    txtproductid = item.getsn();
                    txtshopid = item.getShopid();
                    txtitemname = item.getitemname();
                    pos = position;
                    showalert_unverify("Are you sure want to un verify this product ?");
                } catch (Exception e) {
                }
            }
        });
        txtmobile1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!txtmobile1.getText().toString().equalsIgnoreCase("")) {

                    ((Product_Verification) activity).call(((Offerlist_Verification_FeedItem) feedItems.get(position)).getShopmobile1());
                }
            }
        });
        txtmobile2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!txtmobile2.getText().toString().equalsIgnoreCase("")) {
                    ((Product_Verification) activity).call(((Offerlist_Verification_FeedItem) feedItems.get(position)).getShopmobile2());
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

    public void showalert_verify(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new product_verify().execute(new String[0]);
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

    public void showalert_unverify(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new product_unverify().execute(new String[0]);
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
    public class product_unverify extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"adminproductunverification.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+txtshopid+":%"+txtitemname, "UTF-8");
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
                    Toasty.info(context, "Un Verified", Toast.LENGTH_SHORT).show();
                    ((Product_Verification) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class product_verify extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"adminproductverification.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(txtproductid+":%"+txtshopid+":%"+txtitemname, "UTF-8");
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
                    Toasty.info(context, "Verified", Toast.LENGTH_SHORT).show();
                    ((Product_Verification) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
