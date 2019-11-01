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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.daydeal_admin.Add_Shop;
import com.daydeal_admin.ConnectionDetecter;
import com.daydeal_admin.DatabaseHandler;
import com.daydeal_admin.Image_Viewer;
import com.daydeal_admin.OfferList_Shops;
import com.daydeal_admin.R;
import com.daydeal_admin.ShopList;
import com.daydeal_admin.Temp;
import data.Shoplist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Shoplist_ListAdapter extends BaseAdapter {

    public Activity activity;
    public ConnectionDetecter cd;

    public Context context;
    DatabaseHandler db;
    Typeface face;

    public List<Shoplist_FeedItem> feedItems;
    public String imgsig1;
    private LayoutInflater inflater;
    public String latitude1;
    public String longtitude1;
    public String ownername1;
    ProgressDialog pd;
    public String place1;
    int pos = 0;
    public String shopname1;
    public String sn1;
    public String status1;
    public String t_mobile1;
    public String t_mobile2;
    public String trust;
    public Shoplist_ListAdapter(Activity activity2, List<Shoplist_FeedItem> feedItems2) {
        sn1 = "";
        shopname1 = "";
        ownername1 = "";
        t_mobile1 = "";
        t_mobile2 = "";
        place1 = "";
        latitude1 = "";
        longtitude1 ="";
        imgsig1 = "";
        status1 = "";
        trust = "";
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

        if (item.getstatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else {
            if (item.getstatus().equalsIgnoreCase("0")) {
                disable.setText("INACTIVE");
                disable.setBackgroundColor(Color.parseColor("#b8b8b8"));
            }
        }
        shopname.setText(item.getshopname());
        mobile1.setText(item.getmobile1());
        mobile2.setText(item.getmobile2());
        place.setText(item.getplace());
        ownername.setText(item.getownername());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
        Glide.with(context).load(Temp.weblink+"shoppicsmall/"+item.getsn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);

        if (item.getTrust().equalsIgnoreCase("1")) {
            untrusted.setVisibility(View.INVISIBLE);
            trusted.setVisibility(View.VISIBLE);
        } else {
            untrusted.setVisibility(View.VISIBLE);
            trusted.setVisibility(View.INVISIBLE);
        }
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                Temp.img_title = item.getshopname();
                Temp.img_imgsig = item.getimgsig();
                Temp.img_link = Temp.weblink+"shoppics/"+item.getsn()+".jpg";
                Intent i = new Intent(context, Image_Viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        trusted.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                sn1 = item.getsn();
                shopname1 = item.getshopname();
                ownername1 = item.getownername();
                t_mobile1 = item.getmobile1();
                t_mobile2 = item.getmobile2();
                place1 = item.getplace();
                latitude1 = item.getlatitude();
                longtitude1 = item.getlongtitude();
                imgsig1 = item.getimgsig();
                trust = "0";
                pos = position;
                showalert_untrusted("Are you sure want to Un Trust this shop ?");
            }
        });
        untrusted.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                sn1 = item.getsn();
                shopname1 = item.getshopname();
                ownername1 = item.getownername();
                t_mobile1 = item.getmobile1();
                t_mobile2 = item.getmobile2();
                place1 = item.getplace();
                latitude1 = item.getlatitude();
                longtitude1 = item.getlongtitude();
                imgsig1 = item.getimgsig();
                trust = "1";
                pos = position;
                showalert_trusted("Are you sure want to Trust this shop ?");
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                    sn1 = item.getsn();
                    pos = position;
                    showalert_delete("Are you sure want to delete this shop ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                Temp.edit_shopid = item.getsn();
                Temp.edit_shopname = item.getshopname();
                Temp.edit_ownername = item.getownername();
                Temp.edit_mobile1 = item.getmobile1();
                Temp.edit_mobile2 = item.getmobile2();
                Temp.edit_place = item.getplace();
                Temp.edit_latitude = item.getlatitude();
                Temp.edit_longitude = item.getlongtitude();
                Temp.isshopedit = 1;
                Intent i = new Intent(context, Add_Shop.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                sn1 = item.getsn();
                shopname1 = item.getshopname();
                ownername1 = item.getownername();
                t_mobile1 = item.getmobile1();
                t_mobile2 = item.getmobile2();
                place1 = item.getplace();
                latitude1 = item.getlatitude();
                longtitude1 = item.getlongtitude();
                imgsig1 = item.getimgsig();
                trust = item.getTrust();
                pos = position;
                if (item.getstatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this shop ?");
                }
                else
                {
                    status1 = "1";
                    showalert_changestatus("Are you sure want to activate this shop ?");
                }
            }
        });
        relogin2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Shoplist_FeedItem item = (Shoplist_FeedItem) feedItems.get(position);
                    sn1 = item.getsn();
                    pos = position;
                    showalert_reloagin("Are you sure want to enable login ?");
                } catch (Exception e) {
                }
            }
        });
        productslist2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Temp.edit_shopid = ((Shoplist_FeedItem) feedItems.get(position)).getsn();
                Intent i = new Intent(context, OfferList_Shops.class);
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

                String link= Temp.weblink +"deleteshop_byadmin.php";
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
                    ((ShopList) activity).removeitem(pos);
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

                String link= Temp.weblink +"changeshopstatus.php";
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
                    ((ShopList) activity).changeitem(pos, sn1, shopname1, ownername1, t_mobile1, t_mobile2, place1, latitude1, longtitude1, imgsig1, status1, trust);
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

                String link= Temp.weblink +"reloginshop_byadmin.php";
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

                String link= Temp.weblink +"trustshop_byadmin.php";
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
                    ((ShopList) activity).changeitem(pos, sn1, shopname1, ownername1, t_mobile1, t_mobile2, place1, latitude1, longtitude1, imgsig1, status1, trust);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
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

                String link= Temp.weblink +"untrustshop_byadmin.php";
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
                    ((ShopList) activity).changeitem(pos, sn1, shopname1, ownername1, t_mobile1, t_mobile2, place1, latitude1, longtitude1, imgsig1, status1, trust);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
