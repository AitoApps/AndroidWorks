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
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_admin.ConnectionDetecter;
import com.sanji_admin.DatabaseHandler;
import com.sanji_admin.Image_Viewer;
import com.sanji_admin.Product_verification;
import com.sanji_admin.Temp;
import data.ApprovelProduct_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ApprovelProduct_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<ApprovelProduct_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtproductid = "";
    public String txtproductname = "";
    public String txtshopid = "";

    public class unverify_product extends AsyncTask<String, Void, String> {
        public unverify_product() {
        }
        public void onPreExecute() {
            ApprovelProduct_ListAdapter.pd.setMessage("Please wait...");
            ApprovelProduct_ListAdapter.pd.setCancelable(false);
            ApprovelProduct_ListAdapter.pd.show();
            ApprovelProduct_ListAdapter.timerDelayRemoveDialog(50000, ApprovelProduct_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("unverifyproduct.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(ApprovelProduct_ListAdapter.txtproductid);
                sb3.append(":%");
                sb3.append(ApprovelProduct_ListAdapter.txtshopid);
                sb3.append(":%");
                sb3.append(ApprovelProduct_ListAdapter.txtproductname);
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
            if (ApprovelProduct_ListAdapter.pd != null || ApprovelProduct_ListAdapter.pd.isShowing()) {
                ApprovelProduct_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(ApprovelProduct_ListAdapter.context, "UnVerified", Toast.LENGTH_SHORT).show();
                    ((Product_verification) ApprovelProduct_ListAdapter.activity).removeitem(ApprovelProduct_ListAdapter.pos);
                    return;
                }
                Toasty.info(ApprovelProduct_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class verify_product extends AsyncTask<String, Void, String> {
        public verify_product() {
        }
        public void onPreExecute() {
            ApprovelProduct_ListAdapter.pd.setMessage("Please wait...");
            ApprovelProduct_ListAdapter.pd.setCancelable(false);
            ApprovelProduct_ListAdapter.pd.show();
            ApprovelProduct_ListAdapter.timerDelayRemoveDialog(50000, ApprovelProduct_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("verifyproduct.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(ApprovelProduct_ListAdapter.txtproductid);
                sb3.append(":%");
                sb3.append(ApprovelProduct_ListAdapter.txtshopid);
                sb3.append(":%");
                sb3.append(ApprovelProduct_ListAdapter.txtproductname);
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
            if (ApprovelProduct_ListAdapter.pd != null || ApprovelProduct_ListAdapter.pd.isShowing()) {
                ApprovelProduct_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(ApprovelProduct_ListAdapter.context, "Verified", Toast.LENGTH_SHORT).show();
                    ((Product_verification) ApprovelProduct_ListAdapter.activity).removeitem(ApprovelProduct_ListAdapter.pos);
                    return;
                }
                Toasty.info(ApprovelProduct_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ApprovelProduct_ListAdapter(Activity activity2, List<ApprovelProduct_FeedItem> feedItems2) {
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
        int i = position;
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView2 = inflater.inflate(R.layout.custom_pendingproductlist, null);
        } else {
            convertView2 = convertView;
        }
        TextView txtofferprice = (TextView) convertView2.findViewById(R.id.txtofferprice);
        TextView txtofferpricedot = (TextView) convertView2.findViewById(R.id.txtofferpricedot);
        TextView txtorginalprice = (TextView) convertView2.findViewById(R.id.txtorginalprice);
        TextView txtorginalpricedot = (TextView) convertView2.findViewById(R.id.txtorginalpricedot);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView offerprice = (TextView) convertView2.findViewById(R.id.offerprice);
        TextView shopname = (TextView) convertView2.findViewById(R.id.shopname);
        TextView shopmobile = (TextView) convertView2.findViewById(R.id.shopmobile);
        TextView txtunit = (TextView) convertView2.findViewById(R.id.txtunit);
        TextView unit = (TextView) convertView2.findViewById(R.id.unit);
        TextView txtminqty = (TextView) convertView2.findViewById(R.id.txtminqty);
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView minqty = (TextView) convertView2.findViewById(R.id.minqty);
        TextView orginalprice = (TextView) convertView2.findViewById(R.id.orginalprice);
        ImageView verify = (ImageView) convertView2.findViewById(R.id.verify);
        ImageView unverify = (ImageView) convertView2.findViewById(R.id.unverify);
        View convertView3 = convertView2;
        ApprovelProduct_FeedItem item = (ApprovelProduct_FeedItem) feedItems.get(i);
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
        itemname.setText(item.getitemname());
        shopname.setText(item.getshopname());
        shopmobile.setText(item.getshopmobile());
        offerprice.setTypeface(face);
        TextView textView2 = txtofferpricedot;
        TextView txtofferpricedot2 = orginalprice;
        txtofferpricedot2.setTypeface(face);
        itemname.setTypeface(face);
        shopname.setTypeface(face);
        shopmobile.setTypeface(face);
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        TextView textView3 = txtorginalprice;
        sb.append(item.getofferprice());
        offerprice.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(rupee);
        sb2.append(item.getorginalprice());
        txtofferpricedot2.setText(sb2.toString());
        String str = rupee;
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
        RequestManager with = Glide.with(context);
        StringBuilder sb3 = new StringBuilder();
        TextView textView4 = txtofferpricedot2;
        sb3.append(Temp.weblink);
        sb3.append("productpicsmall/");
        sb3.append(item.getsn());
        sb3.append("_1.jpg");
        ImageView image2 = image;
        with.load(sb3.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image2);
        final int i2 = position;
        image2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ApprovelProduct_FeedItem approvelProduct_FeedItem = (ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2);
                ApprovelProduct_FeedItem item = (ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2);
                Temp.img_title = item.getitemname();
                Temp.img_imgsig = item.getimgsig1();
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("productpicsmall/");
                sb.append(item.getsn());
                sb.append("_1.jpg");
                Temp.img_link = sb.toString();
                Intent i = new Intent(ApprovelProduct_ListAdapter.context, Image_Viewer.class);
                i.setFlags(268435456);
                ApprovelProduct_ListAdapter.context.startActivity(i);
            }
        });
        shopmobile.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    ApprovelProduct_FeedItem approvelProduct_FeedItem = (ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2);
                    ((Product_verification) ApprovelProduct_ListAdapter.activity).call(((ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2)).getshopmobile());
                } catch (Exception e) {
                }
            }
        });
        RequestOptions requestOptions = rep;
        verify.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ApprovelProduct_FeedItem approvelProduct_FeedItem = (ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2);
                ApprovelProduct_FeedItem item = (ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2);
                ApprovelProduct_ListAdapter.pos = i2;
                ApprovelProduct_ListAdapter.txtproductid = item.getsn();
                ApprovelProduct_ListAdapter.txtshopid = item.getshopid();
                ApprovelProduct_ListAdapter.txtproductname = item.getitemname();
                if (ApprovelProduct_ListAdapter.cd.isConnectingToInternet()) {
                    ApprovelProduct_ListAdapter.showalert_verify("Are you sure want to verify this product ?");
                } else {
                    Toasty.info(ApprovelProduct_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        unverify2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ApprovelProduct_FeedItem approvelProduct_FeedItem = (ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2);
                ApprovelProduct_FeedItem item = (ApprovelProduct_FeedItem) ApprovelProduct_ListAdapter.feedItems.get(i2);
                ApprovelProduct_ListAdapter.pos = i2;
                ApprovelProduct_ListAdapter.txtproductid = item.getsn();
                ApprovelProduct_ListAdapter.txtshopid = item.getshopid();
                ApprovelProduct_ListAdapter.txtproductname = item.getitemname();
                if (ApprovelProduct_ListAdapter.cd.isConnectingToInternet()) {
                    ApprovelProduct_ListAdapter.showalert_unverify("Are you sure want to UnVerify this product ?");
                } else {
                    Toasty.info(ApprovelProduct_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
                if (ApprovelProduct_ListAdapter.cd.isConnectingToInternet()) {
                    new verify_product().execute(new String[0]);
                } else {
                    Toasty.info(ApprovelProduct_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
                if (ApprovelProduct_ListAdapter.cd.isConnectingToInternet()) {
                    new unverify_product().execute(new String[0]);
                } else {
                    Toasty.info(ApprovelProduct_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
