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
import com.dailybill_admin.Add_Shops;
import com.dailybill_admin.ConnectionDetecter;
import com.dailybill_admin.DatabaseHandler;
import com.dailybill_admin.ImageViewer;
import com.dailybill_admin.Product_Report_Shops;
import com.dailybill_admin.R;
import com.dailybill_admin.ShopManagement;
import com.dailybill_admin.Temp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import data.Shoplist_FeedItem;
import es.dmoral.toasty.Toasty;

public class Shoplist_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    public String delicharge = "",delidisc = "",delikm="";
    Typeface face;
    public List<Shoplist_FeedItem> feedItems;
    public String imgsig1 = "";
    private LayoutInflater inflater;
    public String shopcatogery="",shopdays1="",trust="",t_mobile2="",latitude1 = "",longtitude1 = "",minordramt="",ownername1="",place1="",shopname1="",sn1="",status1="",t_mobile1="";
    ProgressDialog pd;
    int pos = 0;
    public Shoplist_ListAdapter(Activity activity2, List<Shoplist_FeedItem> feedItems2) {
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
            convertView2 = inflater.inflate(R.layout.custom_shoplist, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView shopname = (TextView) convertView2.findViewById(R.id.shopname);
        TextView ownername = (TextView) convertView2.findViewById(R.id.ownername);
        TextView mobile1 = (TextView) convertView2.findViewById(R.id.mobile1);
        TextView mobile2 = (TextView) convertView2.findViewById(R.id.mobile2);
        TextView place = (TextView) convertView2.findViewById(R.id.place);
        Button disable = (Button) convertView2.findViewById(R.id.disable);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        ImageView relogin = (ImageView) convertView2.findViewById(R.id.relogin);
        ImageView trusted = (ImageView) convertView2.findViewById(R.id.trusted);
        ImageView untrusted = (ImageView) convertView2.findViewById(R.id.untrusted);
        ImageView productslist = (ImageView) convertView2.findViewById(R.id.productslist);
        View convertView3 = convertView2;
        shopname.setTypeface(face);
        ownername.setTypeface(face);
        mobile1.setTypeface(face);
        mobile2.setTypeface(face);
        place.setTypeface(face);
        disable.setTypeface(face);
        Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
        ImageView productslist2 = productslist;
        ImageView relogin2 = relogin;
        if (item.getStatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getStatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(Color.parseColor("#cbcbcb"));
        }
        shopname.setText(item.getShopname());
        mobile1.setText(item.getMobile1());
        mobile2.setText(item.getMobile2());
        place.setText(item.getPlace());
        ownername.setText(item.getOwnername());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"shoppicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        if (item.getTrust().equalsIgnoreCase("1")) {
            untrusted.setVisibility(View.INVISIBLE);
            trusted.setVisibility(View.VISIBLE);
        } else {
            untrusted.setVisibility(View.VISIBLE);
            trusted.setVisibility(View.INVISIBLE);
        }
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem shoplist_FeedItem = (Shoplist_FeedItem) feedItems.get(position);
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                Temp.img_title = item.getShopname();
                Temp.img_imgsig = item.getImgsig();
                Temp.img_link = Temp.weblink+"shoppics/"+item.getSn()+".jpg";
                Intent i = new Intent(context, ImageViewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        trusted.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                sn1 = item.getSn();
                shopname1 = item.getShopname();
                ownername1 = item.getOwnername();
                t_mobile1 = item.getMobile1();
                t_mobile2 = item.getMobile2();
                place1 = item.getPlace();
                latitude1 = item.getLatitude();
                longtitude1 = item.getLongtitude();
                imgsig1 = item.getImgsig();
                delicharge = item.getDelicharge();
                delidisc = item.getDelidisc();
                delikm = item.getDelikm();
                minordramt = item.getMinorderamt();
                shopdays1 = item.getShopdays();
                shopcatogery=item.getShopcatogery();
                trust = "0";
                pos = position;
                showalert_untrusted("Are you sure want to Un Trust this shop ?");
            }
        });
        untrusted.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                sn1 = item.getSn();
                shopname1 = item.getShopname();
                ownername1 = item.getOwnername();
                t_mobile1 = item.getMobile1();
                t_mobile2 = item.getMobile2();
                place1 = item.getPlace();
                latitude1 = item.getLatitude();
                longtitude1 = item.getLongtitude();
                imgsig1 = item.getImgsig();
                delicharge = item.getDelicharge();
                delidisc = item.getDelidisc();
                minordramt = item.getMinorderamt();
                delikm = item.getDelikm();
                shopdays1 = item.getShopdays();
                shopcatogery=item.getShopcatogery();
                trust = "1";
                pos = position;
                showalert_trusted("Are you sure want to Trust this shop ?");
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                    sn1 = item.getSn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this shop ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                Temp.edit_delichrge = item.getDelicharge();
                Temp.edit_delidisc = item.getDelidisc();
                Temp.edit_shopid = item.getSn();
                Temp.edit_shopname = item.getShopname();
                Temp.edit_ownername = item.getOwnername();
                Temp.edit_mobile1 = item.getMobile1();
                Temp.edit_mobile2 = item.getMobile2();
                Temp.edit_place = item.getPlace();
                Temp.edit_latitude = item.getLatitude();
                Temp.edit_longitude = item.getLongtitude();
                Temp.edit_shopdays = item.getShopdays();
                Temp.edit_delinkm = item.getDelikm();
                Temp.edit_minorderamt = item.getMinorderamt();
                Temp.edit_shopcatogery=item.getShopcatogery();
                Temp.editshop_homedelivery=item.getHomedelivery();
                Temp.isshopedit = 1;
                Intent i = new Intent(context, Add_Shops.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                sn1 = item.getSn();
                shopname1 = item.getShopname();
                ownername1 = item.getOwnername();
                t_mobile1 = item.getMobile1();
                t_mobile2 = item.getMobile2();
                place1 = item.getPlace();
                latitude1 = item.getLatitude();
                longtitude1 = item.getLongtitude();
                imgsig1 = item.getImgsig();
                delicharge = item.getDelicharge();
                delidisc = item.getDelidisc();
                delikm = item.getDelikm();
                shopdays1 = item.getShopdays();
                trust = item.getTrust();
                minordramt = item.getMinorderamt();
                shopcatogery=item.getShopcatogery();
                pos = position;
                if (item.getStatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this product ?");
                    return;
                }
                status1 = "1";
                showalert_changestatus("Are you sure want to activate this product ?");
            }
        });
        relogin2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                    sn1 = item.getSn();
                    pos = position;
                    showalert_reloagin("Are you sure want to enable login ?");
                } catch (Exception e) {
                }
            }
        });
        productslist2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.edit_shopid = ((Shoplist_FeedItem) feedItems.get(position)).getSn();
                Intent i = new Intent(context, Product_Report_Shops.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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

    public void showalert_untrusted(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new update_untrusted().execute(new String[0]);
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

    public void showalert_trusted(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new update_trusted().execute(new String[0]);
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
                String link=Temp.weblink+"deleteshop_byadmin.php";
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
                    ((ShopManagement) activity).removeitem(pos);
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
                String link=Temp.weblink+"changeshopstatus.php";
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
                    ShopManagement h = (ShopManagement) activity;
                    h.changeitem(pos,sn1,shopname1,ownername1,t_mobile1,t_mobile2,place1,latitude1,longtitude1,imgsig1,status1,shopdays1,delicharge,delidisc,trust,delikm,minordramt,shopcatogery);
                }
                else
                {
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }

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
                String link=Temp.weblink+"reloginshop_byadmin.php";
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

    public class update_trusted extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"trustshop_byadmin.php";
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
                    Toasty.info(context, "Trusted", Toast.LENGTH_SHORT).show();
                    ShopManagement h = (ShopManagement) activity;
                    h.changeitem(pos,sn1,shopname1,ownername1,t_mobile1,t_mobile2,place1,latitude1,longtitude1,imgsig1,status1,shopdays1,delicharge,delidisc,trust,delikm,minordramt,shopcatogery);
                }
                else
                {
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class update_untrusted extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link=Temp.weblink+"untrustshop_byadmin.php";
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
                    Toasty.info(context, "Un Trusted", Toast.LENGTH_SHORT).show();
                    ShopManagement h = (ShopManagement) activity;
                    h.changeitem(pos,sn1,shopname1,ownername1,t_mobile1,t_mobile2,place1,latitude1,longtitude1,imgsig1,status1,shopdays1,delicharge,delidisc,trust,delikm,minordramt,shopcatogery);
                }
                else
                {
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
