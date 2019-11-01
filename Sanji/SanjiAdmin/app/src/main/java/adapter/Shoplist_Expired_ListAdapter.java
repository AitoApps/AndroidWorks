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
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_admin.Add_Shops;
import com.sanji_admin.ConnectionDetecter;
import com.sanji_admin.DatabaseHandler;
import com.sanji_admin.Image_Viewer;
import com.sanji_admin.Shop_Expire_Controller;
import com.sanji_admin.Temp;
import data.Shoplist_Expired_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Shoplist_Expired_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    public String expdate1 = "";
    Typeface face;
    public List<Shoplist_Expired_FeedItem> feedItems;
    public String imgsig1 = "";
    private LayoutInflater inflater;
    public String latitude1 = "";
    public String longtitude1 = "";
    public String ownername1 = "";
    ProgressDialog pd;
    public String place1 = "";
    int pos = 0;
    public String shopname1 = "";
    public String sn1 = "";
    public String status1 = "";
    public String t_mobile1 = "";
    public String t_mobile2 = "";
    public String townid1 = "";

    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }
        public void onPreExecute() {
            Shoplist_Expired_ListAdapter.pd.setMessage("Please wait...");
            Shoplist_Expired_ListAdapter.pd.setCancelable(false);
            Shoplist_Expired_ListAdapter.pd.show();
            Shoplist_Expired_ListAdapter.timerDelayRemoveDialog(50000, Shoplist_Expired_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("deleteshop_byadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Shoplist_Expired_ListAdapter.sn1);
                sb3.append("");
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
            if (Shoplist_Expired_ListAdapter.pd != null || Shoplist_Expired_ListAdapter.pd.isShowing()) {
                Shoplist_Expired_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Shoplist_Expired_ListAdapter.context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Shop_Expire_Controller) Shoplist_Expired_ListAdapter.activity).removeitem(Shoplist_Expired_ListAdapter.pos);
                    return;
                }
                Toasty.info(Shoplist_Expired_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class productchangestatus extends AsyncTask<String, Void, String> {
        public productchangestatus() {
        }
        public void onPreExecute() {
            Shoplist_Expired_ListAdapter.pd.setMessage("Please wait...");
            Shoplist_Expired_ListAdapter.pd.setCancelable(false);
            Shoplist_Expired_ListAdapter.pd.show();
            Shoplist_Expired_ListAdapter.timerDelayRemoveDialog(50000, Shoplist_Expired_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("changeshopstatus.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Shoplist_Expired_ListAdapter.sn1);
                sb3.append(":%");
                sb3.append(Shoplist_Expired_ListAdapter.status1);
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
            if (Shoplist_Expired_ListAdapter.pd != null || Shoplist_Expired_ListAdapter.pd.isShowing()) {
                Shoplist_Expired_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Shoplist_Expired_ListAdapter.context, "Status Changed", Toast.LENGTH_SHORT).show();
                    ((Shop_Expire_Controller) Shoplist_Expired_ListAdapter.activity).changeitem(Shoplist_Expired_ListAdapter.pos, Shoplist_Expired_ListAdapter.sn1, Shoplist_Expired_ListAdapter.shopname1, Shoplist_Expired_ListAdapter.ownername1, Shoplist_Expired_ListAdapter.t_mobile1, Shoplist_Expired_ListAdapter.t_mobile2, Shoplist_Expired_ListAdapter.place1, Shoplist_Expired_ListAdapter.latitude1, Shoplist_Expired_ListAdapter.longtitude1, Shoplist_Expired_ListAdapter.imgsig1, Shoplist_Expired_ListAdapter.status1, Shoplist_Expired_ListAdapter.expdate1);
                    return;
                }
                Toasty.info(Shoplist_Expired_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class relogin_product extends AsyncTask<String, Void, String> {
        public relogin_product() {
        }
        public void onPreExecute() {
            Shoplist_Expired_ListAdapter.pd.setMessage("Please wait...");
            Shoplist_Expired_ListAdapter.pd.setCancelable(false);
            Shoplist_Expired_ListAdapter.pd.show();
            Shoplist_Expired_ListAdapter.timerDelayRemoveDialog(50000, Shoplist_Expired_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("reloginshop_byadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Shoplist_Expired_ListAdapter.sn1);
                sb3.append("");
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
            if (Shoplist_Expired_ListAdapter.pd != null || Shoplist_Expired_ListAdapter.pd.isShowing()) {
                Shoplist_Expired_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Shoplist_Expired_ListAdapter.context, "Login Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(Shoplist_Expired_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public Shoplist_Expired_ListAdapter(Activity activity2, List<Shoplist_Expired_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2;
        final int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_expireshoplist, null);
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
        ImageView timeextend = (ImageView) convertView2.findViewById(R.id.timeextend);
        TextView expiredate = (TextView) convertView2.findViewById(R.id.expiredate);
        shopname.setTypeface(face);
        ownername.setTypeface(face);
        mobile1.setTypeface(face);
        mobile2.setTypeface(face);
        place.setTypeface(face);
        disable.setTypeface(face);
        expiredate.setTypeface(face);
        Shoplist_Expired_FeedItem item = (Shoplist_Expired_FeedItem) feedItems.get(i);
        View convertView3 = convertView2;
        ImageView relogin2 = relogin;
        if (item.getstatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getstatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(-7829368);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expr : ");
        sb.append(item.getexpireddt());
        expiredate.setText(sb.toString());
        shopname.setText(item.getshopname());
        mobile1.setText(item.getmobile1());
        mobile2.setText(item.getmobile2());
        place.setText(item.getplace());
        ownername.setText(item.getownername());
        TextView textView = shopname;
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
        RequestManager with = Glide.with(context);
        StringBuilder sb2 = new StringBuilder();
        TextView textView2 = ownername;
        sb2.append(Temp.weblink);
        sb2.append("shoppicsmall/");
        sb2.append(item.getsn());
        sb2.append(".jpg");
        with.load(sb2.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_Expired_FeedItem shoplist_Expired_FeedItem = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                Shoplist_Expired_FeedItem item = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                Temp.img_title = item.getshopname();
                Temp.img_imgsig = item.getimgsig();
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("shoppics/");
                sb.append(item.getsn());
                sb.append(".jpg");
                Temp.img_link = sb.toString();
                Intent i = new Intent(Shoplist_Expired_ListAdapter.context, Image_Viewer.class);
                i.setFlags(268435456);
                Shoplist_Expired_ListAdapter.context.startActivity(i);
            }
        });
        timeextend.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Shoplist_Expired_FeedItem shoplist_Expired_FeedItem = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                    ((Shop_Expire_Controller) Shoplist_Expired_ListAdapter.activity).setdate(i, ((Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i)).getsn());
                } catch (Exception e) {
                }
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Shoplist_Expired_FeedItem shoplist_Expired_FeedItem = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                    Shoplist_Expired_FeedItem item = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                    Shoplist_Expired_ListAdapter.sn1 = item.getsn();
                    Shoplist_Expired_ListAdapter.pos = i;
                    Shoplist_Expired_ListAdapter.showalert_delete("Are you sure want to delete this shop ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_Expired_FeedItem shoplist_Expired_FeedItem = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                Shoplist_Expired_FeedItem item = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                Temp.edit_shopid = item.getsn();
                Temp.edit_shopname = item.getshopname();
                Temp.edit_ownername = item.getownername();
                Temp.edit_mobile1 = item.getmobile1();
                Temp.edit_mobile2 = item.getmobile2();
                Temp.edit_place = item.getplace();
                Temp.edit_latitude = item.getlatitude();
                Temp.edit_longitude = item.getlongtitude();
                Temp.isshopedit = 1;
                Intent i = new Intent(Shoplist_Expired_ListAdapter.context, Add_Shops.class);
                i.setFlags(268435456);
                Shoplist_Expired_ListAdapter.context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Shoplist_Expired_FeedItem shoplist_Expired_FeedItem = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                Shoplist_Expired_FeedItem item = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                Shoplist_Expired_ListAdapter.sn1 = item.getsn();
                Shoplist_Expired_ListAdapter.shopname1 = item.getshopname();
                Shoplist_Expired_ListAdapter.ownername1 = item.getownername();
                Shoplist_Expired_ListAdapter.t_mobile1 = item.getmobile1();
                Shoplist_Expired_ListAdapter.t_mobile2 = item.getmobile2();
                Shoplist_Expired_ListAdapter.place1 = item.getplace();
                Shoplist_Expired_ListAdapter.latitude1 = item.getlatitude();
                Shoplist_Expired_ListAdapter.longtitude1 = item.getlongtitude();
                Shoplist_Expired_ListAdapter.imgsig1 = item.getimgsig();
                Shoplist_Expired_ListAdapter.expdate1 = item.getexpireddt();
                Shoplist_Expired_ListAdapter.pos = i;
                if (item.getstatus().equalsIgnoreCase("1")) {
                    Shoplist_Expired_ListAdapter.status1 = "0";
                    Shoplist_Expired_ListAdapter.showalert_changestatus("Are you sure want to inactive this shops ?");
                    return;
                }
                Shoplist_Expired_ListAdapter.status1 = "1";
                Shoplist_Expired_ListAdapter.showalert_changestatus("Are you sure want to activate this shops ?");
            }
        });
        relogin2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Shoplist_Expired_FeedItem shoplist_Expired_FeedItem = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                    Shoplist_Expired_FeedItem item = (Shoplist_Expired_FeedItem) Shoplist_Expired_ListAdapter.feedItems.get(i);
                    Shoplist_Expired_ListAdapter.sn1 = item.getsn();
                    Shoplist_Expired_ListAdapter.pos = i;
                    Shoplist_Expired_ListAdapter.showalert_reloagin("Are you sure want to enable login ?");
                } catch (Exception e) {
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
                if (Shoplist_Expired_ListAdapter.cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(Shoplist_Expired_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
                if (Shoplist_Expired_ListAdapter.cd.isConnectingToInternet()) {
                    new productchangestatus().execute(new String[0]);
                } else {
                    Toasty.info(Shoplist_Expired_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
                if (Shoplist_Expired_ListAdapter.cd.isConnectingToInternet()) {
                    new relogin_product().execute(new String[0]);
                } else {
                    Toasty.info(Shoplist_Expired_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
