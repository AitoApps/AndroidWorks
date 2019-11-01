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
import com.daydeal_admin.Add_Marketting_Staff;
import com.daydeal_admin.ConnectionDetecter;
import com.daydeal_admin.Image_Viewer;
import com.daydeal_admin.Marketting_Staff;
import com.daydeal_admin.R;
import com.daydeal_admin.Temp;
import data.Marketting_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Markettingstaff_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    Typeface face;
    public List<Marketting_FeedItem> feedItems;
    public String imgsigadhar1;
    public String imgsigphoto1;
    private LayoutInflater inflater;
    public String mobile1_1;
    public String mobile2_1;
    public String name1;
    ProgressDialog pd;
    public String place1;
    int pos = 0;
    public String sn1;
    public String status1;
    public Markettingstaff_ListAdapter(Activity activity2, List<Marketting_FeedItem> feedItems2) {
        sn1 ="";
        name1 ="";
        place1 ="";
        mobile1_1 ="";
        mobile2_1 ="";
        imgsigphoto1 ="";
        imgsigadhar1 ="";
        status1 = "";
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
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
            convertView2 = inflater.inflate(R.layout.custom_marketingmanager, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView place = (TextView) convertView2.findViewById(R.id.place);
        TextView mobile = (TextView) convertView2.findViewById(R.id.mobile);
        TextView name = (TextView) convertView2.findViewById(R.id.name);
        Button disable = (Button) convertView2.findViewById(R.id.disable);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        ImageView relogin = (ImageView) convertView2.findViewById(R.id.relogin);
        name.setTypeface(face);
        place.setTypeface(face);
        mobile.setTypeface(face);
        disable.setTypeface(face);
        Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(i);
        if (item.getStatus().equalsIgnoreCase("1")) {
            disable.setText("ACTIVE");
            disable.setBackgroundColor(Color.parseColor("#205c14"));
        } else if (item.getStatus().equalsIgnoreCase("0")) {
            disable.setText("INACTIVE");
            disable.setBackgroundColor(-7829368);
        }
        name.setText(item.getName());
        mobile.setText(item.getMobile1());
        place.setText(item.getPlace());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsigphoto()));
        Glide.with(context).load(Temp.weblink+"staffphotosmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(i);
                Temp.img_title = item.getName();
                Temp.img_imgsig = item.getImgsigphoto();
                Temp.img_link = Temp.weblink+"staffphoto/"+item.getSn()+".jpg";
                Intent i = new Intent(context, Image_Viewer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(i);
                    sn1 = item.getSn();
                    pos = i;
                    showalert_delete("Are you sure want to delete this staff ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(i);
                Temp.mrktid = item.getSn();
                Temp.name = item.getName();
                Temp.place = item.getPlace();
                Temp.mobile1 = item.getMobile1();
                Temp.mobile2 = item.getMobile2();
                Temp.proofimgsig = item.getImgsigadhar();
                Temp.profileimgsig = item.getImgsigphoto();
                Temp.markettingstaffedit = 1;
                Intent i = new Intent(context, Add_Marketting_Staff.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        disable.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(i);
                sn1 = item.getSn();
                name1 = item.getName();
                place1 = item.getPlace();
                mobile1_1 = item.getMobile1();
                mobile2_1 = item.getMobile2();
                imgsigphoto1 = item.getImgsigphoto();
                imgsigadhar1 = item.getImgsigadhar();
                pos = i;
                if (item.getStatus().equalsIgnoreCase("1")) {
                    status1 = "0";
                    showalert_changestatus("Are you sure want to inactive this staff ?");
                }
                else
                {
                    status1 = "1";
                    showalert_changestatus("Are you sure want to activate this staff ?");
                }
            }
        });
        relogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Marketting_FeedItem item = (Marketting_FeedItem) feedItems.get(i);
                    sn1 = item.getSn();
                    pos = i;
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

                String link= Temp.weblink +"deletemarketingstaff_admin.php";
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
                    ((Marketting_Staff) activity).removeitem(pos);
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

                String link= Temp.weblink +"changemarkettingstaffstatus.php";
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
                    ((Marketting_Staff) activity).changeitem(pos, sn1, name1, place1, mobile1_1, mobile2_1, imgsigphoto1, imgsigadhar1, status1);
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

                String link= Temp.weblink +"reloginmarkettingstaff.php";
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
