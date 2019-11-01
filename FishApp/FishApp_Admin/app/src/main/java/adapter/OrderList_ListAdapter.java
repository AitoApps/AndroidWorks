package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fishappadmin.ConnectionDetecter;
import com.fishappadmin.Order_List;
import com.fishappadmin.R;
import com.fishappadmin.Temp;
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
    Typeface face;
    public List<Order_List_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    String t_itemname;
    String t_orderid;
   
    public OrderList_ListAdapter(Activity activity2, List<Order_List_FeedItem> feedItems2) {
        String str = "";
        t_orderid = str;
        t_itemname = str;
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
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
            convertView2 = inflater.inflate(R.layout.custom_orderlist, null);
        } else {
            convertView2 = convertView;
        }
        Order_List_FeedItem item = (Order_List_FeedItem) feedItems.get(i);
        String rupee = context.getResources().getString(R.string.Rs);
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView txtitemqty = (TextView) convertView2.findViewById(R.id.txtitemqty);
        TextView qty = (TextView) convertView2.findViewById(R.id.qty);
        TextView txtitemprice = (TextView) convertView2.findViewById(R.id.txtitemprice);
        TextView itemprice = (TextView) convertView2.findViewById(R.id.itemprice);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        itemname.setTypeface(face);
        txtitemqty.setTypeface(face);
        qty.setTypeface(face);
        txtitemprice.setTypeface(face);
        itemprice.setTypeface(face);
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
        Glide.with(context).load(Temp.weblink+"fishpicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        if (Temp.orderlisttype == 1) {
            delete.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
        }
        itemname.setText(item.getFishname());
        float kilograms = Float.parseFloat(item.getQty()) / Float.parseFloat("1000");

        qty.setText(String.format("%.2f", kilograms)+"");
        itemprice.setText(rupee+String.format("%.2f", Float.parseFloat(item.getPrice())));

        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Order_List_FeedItem order_List_FeedItem = (Order_List_FeedItem) feedItems.get(i);
                Order_List_FeedItem item = (Order_List_FeedItem) feedItems.get(i);
                t_orderid = item.getSn();
                t_itemname = item.getFishname();
                pos = i;
                showalert_cancellorder("Are sure want to cancell this order ?");
            }
        });
        return convertView2;
    }

    public void showalert_cancellorder(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cd.isConnectingToInternet()) {
                    new cancellorder().execute(new String[0]);
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


    public class cancellorder extends AsyncTask<String, Void, String> {
        public cancellorder() {
        }


        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }


        public String doInBackground(String... arg0) {

            try {

                String link= Temp.weblink +"cancellindividualorder_admin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(t_orderid+":%"+t_itemname, "UTF-8");
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
                    Toasty.info(context, "Order is cancelled", Toast.LENGTH_SHORT).show();
                    ((Order_List) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
