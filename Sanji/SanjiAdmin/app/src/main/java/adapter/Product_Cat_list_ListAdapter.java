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
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_admin.Add_ProductCat;
import com.sanji_admin.ConnectionDetecter;
import com.sanji_admin.DatabaseHandler;
import com.sanji_admin.Product_Catogery;
import com.sanji_admin.Temp;
import data.Product_cat_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Product_Cat_list_ListAdapter extends Adapter<ViewHolder> {
    private final int TYPE_FOOTER = 1;
    private final int TYPE_ITEM = 0;
    private final int TYPE_NULL = 2;
    public Activity activity;
    public String catimgsig = "";
    public String catorder = "";
    public String cattitle = "";
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Product_cat_FeedItem> feedItems;
    private LayoutInflater inflater;
    public float ogheight;
    public String pcatid = "";
    ProgressDialog pd;
    int pos = 0;

    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }
        public void onPreExecute() {
            Product_Cat_list_ListAdapter.pd.setMessage("Please wait...");
            Product_Cat_list_ListAdapter.pd.setCancelable(false);
            Product_Cat_list_ListAdapter.pd.show();
            Product_Cat_list_ListAdapter.timerDelayRemoveDialog(50000, Product_Cat_list_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("deleteproductcatogery.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_Cat_list_ListAdapter.pcatid);
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
            if (Product_Cat_list_ListAdapter.pd != null || Product_Cat_list_ListAdapter.pd.isShowing()) {
                Product_Cat_list_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Product_Cat_list_ListAdapter.context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Product_Catogery) Product_Cat_list_ListAdapter.activity).removeitem(Product_Cat_list_ListAdapter.pos);
                    return;
                }
                Toasty.info(Product_Cat_list_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class productitemorder extends AsyncTask<String, Void, String> {
        public productitemorder() {
        }
        public void onPreExecute() {
            Product_Cat_list_ListAdapter.pd.setMessage("Please wait...");
            Product_Cat_list_ListAdapter.pd.setCancelable(false);
            Product_Cat_list_ListAdapter.pd.show();
            Product_Cat_list_ListAdapter.timerDelayRemoveDialog(50000, Product_Cat_list_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("changeproductcat_type.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_Cat_list_ListAdapter.pcatid);
                sb3.append(":%");
                sb3.append(Product_Cat_list_ListAdapter.catorder);
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
            if (Product_Cat_list_ListAdapter.pd != null || Product_Cat_list_ListAdapter.pd.isShowing()) {
                Product_Cat_list_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Product_Cat_list_ListAdapter.context, "Order Listing Changed", Toast.LENGTH_SHORT).show();
                    ((Product_Catogery) Product_Cat_list_ListAdapter.activity).changeitem(Product_Cat_list_ListAdapter.pos, Product_Cat_list_ListAdapter.pcatid, Product_Cat_list_ListAdapter.cattitle, Product_Cat_list_ListAdapter.catimgsig, Product_Cat_list_ListAdapter.catorder);
                    return;
                }
                Toasty.info(Product_Cat_list_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class viewHolder extends ViewHolder {
        ImageView delete;
        ImageView edit;
        ImageView image;
        TextView itemname;
        EditText itemorder1;
        RelativeLayout layout;
        Button setorder;
        TextView txtitemorder1;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            itemorder1 = (EditText) itemView.findViewById(R.id.itemorder1);
            setorder = (Button) itemView.findViewById(R.id.setorder);
            txtitemorder1 = (TextView) itemView.findViewById(R.id.txtitemorder1);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Product_Cat_list_ListAdapter(Activity activity2, List<Product_cat_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db = new DatabaseHandler(context);
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_productcat, parent, false));
        }
        if (viewType == 1) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.footerview, parent, false));
        }
        if (viewType == 2) {
            return new viewHolderFooter(LayoutInflater.from(context).inflate(R.layout.fullloaded, parent, false));
        }
        return null;
    }

    public int getItemViewType(int position) {
        if (position < feedItems.size()) {
            return 0;
        }
        return 2;
    }

    public int getItemCount() {
        return feedItems.size() + 1;
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                Product_cat_FeedItem item = (Product_cat_FeedItem) feedItems.get(position);
                final viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.itemname.setVisibility(View.VISIBLE);
                viewHolder2.itemname.setText(item.gettitle());
                viewHolder2.itemname.setTypeface(face);
                viewHolder2.txtitemorder1.setTypeface(face);
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
                RequestManager with = Glide.with(context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("productcatogery/");
                sb.append(item.getsn());
                sb.append(".png");
                with.load(sb.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.itemorder1.setText(item.getcatorder());
                viewHolder2.setorder.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (viewHolder2.itemorder1.getText().toString().equalsIgnoreCase("")) {
                            Toasty.info(Product_Cat_list_ListAdapter.context, "Please enter catogery order", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Product_cat_FeedItem product_cat_FeedItem = (Product_cat_FeedItem) Product_Cat_list_ListAdapter.feedItems.get(position);
                        Product_cat_FeedItem item = (Product_cat_FeedItem) Product_Cat_list_ListAdapter.feedItems.get(position);
                        Product_Cat_list_ListAdapter.pcatid = item.getsn();
                        Product_Cat_list_ListAdapter.catorder = viewHolder2.itemorder1.getText().toString();
                        Product_Cat_list_ListAdapter.cattitle = item.gettitle();
                        Product_Cat_list_ListAdapter.catimgsig = item.getimgsig();
                        Product_Cat_list_ListAdapter.pos = position;
                        Product_Cat_list_ListAdapter.showalert_itemorder("Are you sure want to change the order ?");
                    }
                });
                viewHolder2.delete.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Product_cat_FeedItem product_cat_FeedItem = (Product_cat_FeedItem) Product_Cat_list_ListAdapter.feedItems.get(position);
                            Product_cat_FeedItem item = (Product_cat_FeedItem) Product_Cat_list_ListAdapter.feedItems.get(position);
                            Product_Cat_list_ListAdapter.pcatid = item.getsn();
                            Product_Cat_list_ListAdapter.pos = position;
                            Product_Cat_list_ListAdapter.showalert_delete("Are you sure want to delete this catogery ?");
                        } catch (Exception e) {
                        }
                    }
                });
                viewHolder2.edit.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_cat_FeedItem product_cat_FeedItem = (Product_cat_FeedItem) Product_Cat_list_ListAdapter.feedItems.get(position);
                        Product_cat_FeedItem item = (Product_cat_FeedItem) Product_Cat_list_ListAdapter.feedItems.get(position);
                        Temp.productcateditsn = item.getsn();
                        Temp.edit_catogeryname = item.gettitle();
                        Temp.edit_imgsig = item.getimgsig();
                        Temp.productcatedit = 1;
                        Intent i = new Intent(Product_Cat_list_ListAdapter.context, Add_ProductCat.class);
                        i.setFlags(268435456);
                        Product_Cat_list_ListAdapter.context.startActivity(i);
                    }
                });
            } catch (Exception e) {
            }
        }
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
                if (Product_Cat_list_ListAdapter.cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(Product_Cat_list_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void showalert_itemorder(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (Product_Cat_list_ListAdapter.cd.isConnectingToInternet()) {
                    new productitemorder().execute(new String[0]);
                } else {
                    Toasty.info(Product_Cat_list_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
