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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.DatabaseHandler;
import com.sanji_shop.Image_Viewer;
import com.sanji_shop.Order_List;
import com.sanji_shop.R;
import com.sanji_shop.Temp;
import data.Order_List_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class OrderList_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face2;
    public List<Order_List_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String t_imgsig = "";
    public String t_orderdate = "";
    public String t_orderid = "";
    public String t_productid = "";
    public String t_productname = "";
    public String t_productprice = "";
    public String t_productqty = "";
    public String t_producttotal = "";

    public class cancellorder extends AsyncTask<String, Void, String> {
        public cancellorder() {
        }
        public void onPreExecute() {
            OrderList_ListAdapter.pd.setMessage("Please wait...");
            OrderList_ListAdapter.pd.setCancelable(false);
            OrderList_ListAdapter.pd.show();
            OrderList_ListAdapter.timerDelayRemoveDialog(50000, OrderList_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("cancellproductorder_shops.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                sb2.append(URLEncoder.encode(OrderList_ListAdapter.t_orderid, "UTF-8"));
                String data2 = sb2.toString();
                URLConnection conn = new URL(link).openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    String readLine = reader.readLine();
                    String line = readLine;
                    if (readLine == null) {
                        return sb3.toString();
                    }
                    sb3.append(line);
                }
            } catch (Exception e) {
                return new String("Unable to connect server! Please check your internet connection");
            }
        }
        public void onPostExecute(String result) {
            if (OrderList_ListAdapter.pd != null || OrderList_ListAdapter.pd.isShowing()) {
                OrderList_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(OrderList_ListAdapter.context, "Product Order Cancelled", Toast.LENGTH_SHORT).show();
                    ((Order_List) OrderList_ListAdapter.activity).removeitem(OrderList_ListAdapter.pos);
                    return;
                }
                Toasty.info(OrderList_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public OrderList_ListAdapter(Activity activity2, List<Order_List_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        cd = new ConnectionDetecter(context);
        pd = new ProgressDialog(activity2);
        db = new DatabaseHandler(context);
        face2 = Typeface.createFromAsset(context.getAssets(), "font/heading.otf");
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
            convertView2 = inflater.inflate(R.layout.custom_orderlist, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView orderdate = (TextView) convertView2.findViewById(R.id.orderdate);
        TextView itemprice = (TextView) convertView2.findViewById(R.id.itemprice);
        TextView itemqty = (TextView) convertView2.findViewById(R.id.itemqty);
        TextView itemtotal = (TextView) convertView2.findViewById(R.id.itemtotal);
        TextView orderid = (TextView) convertView2.findViewById(R.id.orderid);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        RelativeLayout relativeLayout = (RelativeLayout) convertView2.findViewById(R.id.layout);
        Order_List_FeedItem item = (Order_List_FeedItem) feedItems.get(i);
        String rupee = context.getResources().getString(R.string.Rs);
        itemname.setTypeface(face2);
        orderdate.setTypeface(face2);
        itemprice.setTypeface(face2);
        itemqty.setTypeface(face2);
        itemtotal.setTypeface(face2);
        orderid.setTypeface(face2);
        StringBuilder sb = new StringBuilder();
        sb.append(Temp.grpid);
        sb.append(item.getorderid());
        orderid.setText(sb.toString());
        orderdate.setText(item.getorderdate());
        itemname.setText(item.getproductname());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(rupee);
        TextView textView = itemname;
        TextView textView2 = orderdate;
        sb2.append(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(item.getproductprice()))}));
        itemprice.setText(sb2.toString());
        itemqty.setText(item.getproductqty());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(rupee);
        sb3.append(String.format("%.2f", new Object[]{Float.valueOf(Float.parseFloat(item.getproducttotal()))}));
        itemtotal.setText(sb3.toString());
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
        RequestManager with = Glide.with(context);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(Temp.weblink);
        sb4.append("productpicsmall/");
        sb4.append(item.getproductid());
        sb4.append("_1.jpg");
        with.load(sb4.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List_FeedItem order_List_FeedItem = (Order_List_FeedItem) OrderList_ListAdapter.feedItems.get(i);
                Order_List_FeedItem item = (Order_List_FeedItem) OrderList_ListAdapter.feedItems.get(i);
                Temp.img_title = item.getproductname();
                Temp.img_imgsig = item.getimgsig();
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("productpic/");
                sb.append(item.getproductid());
                sb.append("_1.jpg");
                Temp.img_link = sb.toString();
                Intent i = new Intent(OrderList_ListAdapter.context, Image_Viewer.class);
                i.setFlags(268435456);
                OrderList_ListAdapter.context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List_FeedItem order_List_FeedItem = (Order_List_FeedItem) OrderList_ListAdapter.feedItems.get(i);
                Order_List_FeedItem item = (Order_List_FeedItem) OrderList_ListAdapter.feedItems.get(i);
                OrderList_ListAdapter.t_orderid = item.getorderid();
                OrderList_ListAdapter.pos = i;
                OrderList_ListAdapter.showalert_cancellorder("Are sure want to cancell ?");
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

    public void showalert_cancellorder(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (OrderList_ListAdapter.cd.isConnectingToInternet()) {
                    new cancellorder().execute(new String[0]);
                } else {
                    Toasty.info(OrderList_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
