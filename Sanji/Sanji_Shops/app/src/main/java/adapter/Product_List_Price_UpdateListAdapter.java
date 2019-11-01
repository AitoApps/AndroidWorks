package adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.Price_Update;
import com.sanji_shop.R;
import com.sanji_shop.Temp;
import data.Productlist_priceUpdate_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Product_List_Price_UpdateListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<Productlist_priceUpdate_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txt_imgsig = "";
    public String txt_itemname = "";
    public String txt_offerprice = "";
    public String txt_offerprice1 = "";
    public String txt_originalprice = "";
    public String txt_originalprice1 = "";
    public String txtproductid = "";

    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }
        public void onPreExecute() {
            Product_List_Price_UpdateListAdapter.pd.setMessage("Please wait...");
            Product_List_Price_UpdateListAdapter.pd.setCancelable(false);
            Product_List_Price_UpdateListAdapter.pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("deleteprodutc_byshop.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_List_Price_UpdateListAdapter.txtproductid);
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
            if (Product_List_Price_UpdateListAdapter.pd != null || Product_List_Price_UpdateListAdapter.pd.isShowing()) {
                Product_List_Price_UpdateListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Product_List_Price_UpdateListAdapter.context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Price_Update) Product_List_Price_UpdateListAdapter.activity).removeitem(Product_List_Price_UpdateListAdapter.pos);
                    return;
                }
                Toasty.info(Product_List_Price_UpdateListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class update_price extends AsyncTask<String, Void, String> {
        public update_price() {
        }
        public void onPreExecute() {
            Product_List_Price_UpdateListAdapter.pd.setMessage("Updating...");
            Product_List_Price_UpdateListAdapter.pd.setCancelable(false);
            Product_List_Price_UpdateListAdapter.pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("updateproductprice_byshop.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Product_List_Price_UpdateListAdapter.txtproductid);
                sb3.append(":%");
                sb3.append(Product_List_Price_UpdateListAdapter.txt_offerprice1);
                sb3.append(":%");
                sb3.append(Product_List_Price_UpdateListAdapter.txt_originalprice1);
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
            if (Product_List_Price_UpdateListAdapter.pd != null || Product_List_Price_UpdateListAdapter.pd.isShowing()) {
                Product_List_Price_UpdateListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Product_List_Price_UpdateListAdapter.context, "updated", Toast.LENGTH_SHORT).show();
                    ((Price_Update) Product_List_Price_UpdateListAdapter.activity).changeitem(Product_List_Price_UpdateListAdapter.pos, Product_List_Price_UpdateListAdapter.txtproductid, Product_List_Price_UpdateListAdapter.txt_itemname, Product_List_Price_UpdateListAdapter.txt_offerprice1, Product_List_Price_UpdateListAdapter.txt_originalprice1, Product_List_Price_UpdateListAdapter.txt_imgsig);
                    return;
                }
                Toasty.info(Product_List_Price_UpdateListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class viewHolder extends ViewHolder {
        ImageView delete;
        ImageView edit;
        ImageView image;
        TextView itemname;
        TextView offerprice;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            offerprice = (TextView) itemView.findViewById(R.id.offerprice);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
        }
    }

    public class viewHolderFooter extends ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }

    public Product_List_Price_UpdateListAdapter(Activity activity2, List<Productlist_priceUpdate_FeedItem> feedItems2) {
        activity = activity2;
        context = activity2.getApplicationContext();
        feedItems = feedItems2;
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face2 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_productlist_forprice, parent, false));
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
                feedItems.size();
                Productlist_priceUpdate_FeedItem item = (Productlist_priceUpdate_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                String rupee = context.getResources().getString(R.string.Rs);
                viewHolder2.offerprice.setTypeface(face2);
                TextView textView = viewHolder2.offerprice;
                StringBuilder sb = new StringBuilder();
                sb.append(rupee);
                sb.append(item.getOfferprice());
                textView.setText(sb.toString());
                viewHolder2.itemname.setText(item.getItemname());
                viewHolder2.itemname.setTypeface(face1);
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig1()));
                RequestManager with = Glide.with(context);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Temp.weblink);
                sb2.append("productpicsmall/");
                sb2.append(item.getSn());
                sb2.append("_1.jpg");
                with.load(sb2.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.delete.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Productlist_priceUpdate_FeedItem productlist_priceUpdate_FeedItem = (Productlist_priceUpdate_FeedItem) Product_List_Price_UpdateListAdapter.feedItems.get(position);
                            Productlist_priceUpdate_FeedItem item = (Productlist_priceUpdate_FeedItem) Product_List_Price_UpdateListAdapter.feedItems.get(position);
                            Product_List_Price_UpdateListAdapter.txtproductid = item.getSn();
                            Product_List_Price_UpdateListAdapter.pos = position;
                            Product_List_Price_UpdateListAdapter.showalert_delete("Are you sure want to delete this product ?");
                        } catch (Exception e) {
                        }
                    }
                });
                viewHolder2.edit.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Productlist_priceUpdate_FeedItem productlist_priceUpdate_FeedItem = (Productlist_priceUpdate_FeedItem) Product_List_Price_UpdateListAdapter.feedItems.get(position);
                        Productlist_priceUpdate_FeedItem item = (Productlist_priceUpdate_FeedItem) Product_List_Price_UpdateListAdapter.feedItems.get(position);
                        Product_List_Price_UpdateListAdapter.txtproductid = item.getSn();
                        Product_List_Price_UpdateListAdapter.pos = position;
                        Product_List_Price_UpdateListAdapter.txt_itemname = item.getItemname();
                        Product_List_Price_UpdateListAdapter.txt_imgsig = item.getImgsig1();
                        Product_List_Price_UpdateListAdapter.txt_offerprice = item.getOfferprice();
                        Product_List_Price_UpdateListAdapter.txt_originalprice = item.getOrginalprice();
                        Product_List_Price_UpdateListAdapter.change_price();
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    public void showalert_delete(String message) {
        Builder builder = new Builder(activity);
        builder.setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (Product_List_Price_UpdateListAdapter.cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(Product_List_Price_UpdateListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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

    public void updateList(List<Productlist_priceUpdate_FeedItem> list) {
        feedItems = list;
        notifyDataSetChanged();
    }

    public void change_price() {
        final Dialog dialog1 = new Dialog(activity);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.custom_change_price);
        final EditText originalprice = (EditText) dialog1.findViewById(R.id.originalprice);
        TextView txtofferprice = (TextView) dialog1.findViewById(R.id.txtofferprice);
        final EditText offerprice = (EditText) dialog1.findViewById(R.id.offerprice);
        Button update = (Button) dialog1.findViewById(R.id.update);
        ((TextView) dialog1.findViewById(R.id.txtoriginalprice)).setTypeface(face1);
        txtofferprice.setTypeface(face1);
        originalprice.setTypeface(face2);
        offerprice.setTypeface(face2);
        originalprice.setText(txt_originalprice);
        offerprice.setText(txt_offerprice);
        originalprice.setSelection(originalprice.getText().toString().length());
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (originalprice.getText().toString().equalsIgnoreCase("")) {
                    Toasty.success(Product_List_Price_UpdateListAdapter.context, "Please enter original price", Toast.LENGTH_SHORT).show();
                    originalprice.requestFocus();
                } else if (offerprice.getText().toString().equalsIgnoreCase("")) {
                    Toasty.success(Product_List_Price_UpdateListAdapter.context, "Please enter offer price", Toast.LENGTH_SHORT).show();
                    offerprice.requestFocus();
                } else if (Product_List_Price_UpdateListAdapter.cd.isConnectingToInternet()) {
                    Product_List_Price_UpdateListAdapter.txt_offerprice1 = offerprice.getText().toString();
                    Product_List_Price_UpdateListAdapter.txt_originalprice1 = originalprice.getText().toString();
                    new update_price().execute(new String[0]);
                    dialog1.dismiss();
                } else {
                    Toasty.info(Product_List_Price_UpdateListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog1.setCancelable(true);
        dialog1.show();
    }
}
