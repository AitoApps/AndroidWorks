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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.daydeal_admin.Add_ProductCatogery;
import com.daydeal_admin.Catogery_List;
import com.daydeal_admin.ConnectionDetecter;
import com.daydeal_admin.DatabaseHandler;
import com.daydeal_admin.R;
import com.daydeal_admin.Temp;
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
    public String catimgsig;
    public String catorder;
    public String cattitle;
    public ConnectionDetecter cd;

    public Context context;
    DatabaseHandler db;
    Typeface face;

    public List<Product_cat_FeedItem> feedItems;
    private LayoutInflater inflater;
    public float ogheight;
    public String pcatid;
    ProgressDialog pd;
    int pos;

    public class viewHolder extends ViewHolder {
        ImageView delete;
        ImageView edit;
        ImageView image;
        TextView itemname;
        RelativeLayout layout;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            edit = (ImageView) itemView.findViewById(R.id.edit);
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
        String str = "";
        pcatid = str;
        catorder = str;
        cattitle = str;
        catimgsig = str;
        pos = 0;
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db = new DatabaseHandler(context);
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
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
                viewHolder viewHolder2 = (viewHolder) holder;
                viewHolder2.itemname.setVisibility(View.VISIBLE);
                viewHolder2.itemname.setText(item.gettitle());
                viewHolder2.itemname.setTypeface(face);
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig()));
                Glide.with(context).load(Temp.weblink+"productcatogery/"+item.getsn()+".png").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.delete.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Product_cat_FeedItem item = (Product_cat_FeedItem) feedItems.get(position);
                            pcatid = item.getsn();
                            pos = position;
                            showalert_delete("Are you sure want to delete this catogery ?");
                        } catch (Exception e) {
                        }
                    }
                });
                viewHolder2.edit.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Product_cat_FeedItem item = (Product_cat_FeedItem) feedItems.get(position);
                        Temp.productcateditsn = item.getsn();
                        Temp.edit_catogeryname = item.gettitle();
                        Temp.edit_imgsig = item.getimgsig();
                        Temp.productcatedit = 1;
                        Intent i = new Intent(context, Add_ProductCatogery.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            } catch (Exception a) {
               // Toasty.info(context, Log.getStackTraceString(a), Toast.LENGTH_LONG).show();
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
    public class delete_product extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }


        public String doInBackground(String... arg0) {
            try {

                String link= Temp.weblink +"deleteproductcatogery.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(pcatid, "UTF-8");
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
                    ((Catogery_List) activity).removeitem(pos);
                    return;
                }
                Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
