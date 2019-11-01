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
import com.dialybill_shops.Add_Delivery_Boy;
import com.dialybill_shops.ConnectionDetecter;
import com.dialybill_shops.DatabaseHandler;
import com.dialybill_shops.Delivery_Boy;
import com.dialybill_shops.Image_Viewer;
import com.dialybill_shops.R;
import com.dialybill_shops.Temp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import data.Deliveryboys_FeedItem;
import es.dmoral.toasty.Toasty;
public class DeliveryBoys_ListAdapter extends BaseAdapter {
    public Activity activity;
    public String address1 = "";
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Deliveryboys_FeedItem> feedItems;
    public String idcard1 = "";
    public String imgsig1 = "";
    private LayoutInflater inflater;
    public String mobile1 = "";
    public String name1 = "";
    ProgressDialog pd;
    public String place1 = "";
    int pos = 0;
    public String shopid1 = "";
    public String sn1 = "";
    public String status1 = "";
    public DeliveryBoys_ListAdapter(Activity activity2, List<Deliveryboys_FeedItem> feedItems2) {
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
            convertView2 = inflater.inflate(R.layout.custom_deliveryboys, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView name = (TextView) convertView2.findViewById(R.id.name);
        TextView place = (TextView) convertView2.findViewById(R.id.place);
        TextView mobile = (TextView) convertView2.findViewById(R.id.mobile);
        TextView idcard = (TextView) convertView2.findViewById(R.id.idcard);
        Button disable = (Button) convertView2.findViewById(R.id.disable);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        ImageView relogin = (ImageView) convertView2.findViewById(R.id.relogin);
        name.setTypeface(face);
        place.setTypeface(face);
        mobile.setTypeface(face);
        idcard.setTypeface(face);
        disable.setTypeface(face);
        Deliveryboys_FeedItem item = (Deliveryboys_FeedItem) feedItems.get(position);
        if (item.getStatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getStatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(Color.parseColor("#b6b6b6"));
        } else if (item.getStatus().equalsIgnoreCase("3")) {
            disable.setText("Pending");
            disable.setBackgroundColor(Color.parseColor("#1b4f73"));
        }
        name.setText(item.getName());
        mobile.setText(item.getMobile());
        place.setText(item.getPlace());
        idcard.setText(item.getIdcard());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"deliverypicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Deliveryboys_FeedItem item = (Deliveryboys_FeedItem) feedItems.get(pos);
                Temp.img_title = item.getName();
                Temp.img_imgsig = item.getImgsig();
                Temp.img_link = Temp.weblink+"deliverypic/"+item.getSn()+".jpg";
                Intent i = new Intent(context, Image_Viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Deliveryboys_FeedItem item = (Deliveryboys_FeedItem) feedItems.get(position);
                    sn1 = item.getSn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this Delivery Boy ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Deliveryboys_FeedItem item = (Deliveryboys_FeedItem) feedItems.get(position);
                Temp.editdeli_address = item.getAddress();
                Temp.editdeli_id = item.getSn();
                Temp.editdeli_shopid = item.getShopid();
                Temp.editdeli_name = item.getName();
                Temp.editdeli_idcard= item.getIdcard();
                Temp.editdeli_mobile = item.getMobile();
                Temp.editdeli_place = item.getPlace();
                Temp.editdeli_imgsig = item.getImgsig();
                Temp.isdeliveryboyedit = 1;
                Intent i = new Intent(context, Add_Delivery_Boy.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Deliveryboys_FeedItem item = (Deliveryboys_FeedItem) feedItems.get(position);
                sn1 = item.getSn();
                shopid1 = item.getShopid();
                name1 = item.getName();
                address1 = item.getAddress();
                idcard1 = item.getIdcard();
                mobile1 = item.getMobile();
                place1 = item.getPlace();
                imgsig1 = item.getImgsig();
                status1 = item.getStatus();
                pos = position;
                if (item.getStatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this Delivery Boy ?");
                }
                else
                {
                    status1 = "1";
                    showalert_changestatus("Are you sure want to activate this Delivery Boy?");
                }

            }
        });
        relogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Deliveryboys_FeedItem item = (Deliveryboys_FeedItem) feedItems.get(position);
                    sn1 = item.getSn();
                    pos = position;
                    showalert_reloagin("Are you sure want to enable login ?");
                } catch (Exception e) {
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

    public void showalert_reloagin(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new relogin_product().execute(new String[0]);
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
                String link= Temp.weblink+"shops_deletedeliveryboy.php";
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
                    Toasty.info(context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Delivery_Boy) activity).removeitem(pos);
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
                String link= Temp.weblink+"shop_changedeliveryboy.php";
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
                    Toasty.info(context, "Status Changed", Toast.LENGTH_SHORT).show();
                    ((Delivery_Boy) activity).changeitem(pos, sn1, shopid1, name1, address1, idcard1, mobile1, place1, imgsig1, status1);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class relogin_product extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"shops_relogindeliveryboy.php";
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
                    Toasty.info(context, "Login Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
