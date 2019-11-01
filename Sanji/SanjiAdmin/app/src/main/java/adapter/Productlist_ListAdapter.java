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
import com.sanji_admin.Add_Product;
import com.sanji_admin.ConnectionDetecter;
import com.sanji_admin.DatabaseHandler;
import com.sanji_admin.Image_Viewer;
import com.sanji_admin.Product_List;
import com.sanji_admin.Temp;
import data.Productlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Productlist_ListAdapter extends BaseAdapter {
    public Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Productlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtproductid = "";

    public class delete_product extends AsyncTask<String, Void, String> {
        public delete_product() {
        }
        public void onPreExecute() {
            Productlist_ListAdapter.pd.setMessage("Please wait...");
            Productlist_ListAdapter.pd.setCancelable(false);
            Productlist_ListAdapter.pd.show();
            Productlist_ListAdapter.timerDelayRemoveDialog(50000, Productlist_ListAdapter.pd);
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("deleteprodutc_byadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Productlist_ListAdapter.txtproductid);
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
            if (Productlist_ListAdapter.pd != null || Productlist_ListAdapter.pd.isShowing()) {
                Productlist_ListAdapter.pd.dismiss();
                if (result.contains("ok")) {
                    Toasty.info(Productlist_ListAdapter.context, "Deleted", Toast.LENGTH_SHORT).show();
                    ((Product_List) Productlist_ListAdapter.activity).removeitem(Productlist_ListAdapter.pos);
                    return;
                }
                Toasty.info(Productlist_ListAdapter.context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Productlist_ListAdapter(Activity activity2, List<Productlist_FeedItem> feedItems2) {
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
            convertView2 = inflater.inflate(R.layout.custom_productlist, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView offerprice = (TextView) convertView2.findViewById(R.id.offerprice);
        TextView orginalprice = (TextView) convertView2.findViewById(R.id.orginalprice);
        TextView txtofferprice = (TextView) convertView2.findViewById(R.id.txtofferprice);
        TextView txtofferpricedot = (TextView) convertView2.findViewById(R.id.txtofferpricedot);
        TextView txtorginalprice = (TextView) convertView2.findViewById(R.id.txtorginalprice);
        TextView txtorginalpricedot = (TextView) convertView2.findViewById(R.id.txtorginalpricedot);
        TextView txtunit = (TextView) convertView2.findViewById(R.id.txtunit);
        TextView unit = (TextView) convertView2.findViewById(R.id.unit);
        TextView txtminqty = (TextView) convertView2.findViewById(R.id.txtminqty);
        TextView minqty = (TextView) convertView2.findViewById(R.id.minqty);
        itemname.setTypeface(face);
        offerprice.setTypeface(face);
        orginalprice.setTypeface(face);
        txtunit.setTypeface(face);
        txtminqty.setTypeface(face);
        txtofferprice.setTypeface(face);
        txtofferpricedot.setTypeface(face);
        txtorginalprice.setTypeface(face);
        txtorginalpricedot.setTypeface(face);
        ImageView delete = (ImageView) convertView2.findViewById(R.id.delete);
        TextView textView = txtofferprice;
        ImageView edit = (ImageView) convertView2.findViewById(R.id.edit);
        TextView textView2 = txtofferpricedot;
        Productlist_FeedItem item = (Productlist_FeedItem) feedItems.get(i);
        TextView textView3 = txtorginalprice;
        TextView textView4 = txtorginalpricedot;
        String rupee = context.getResources().getString(R.string.Rs);
        List<String> lst_unittype = new ArrayList<>();
        TextView textView5 = txtunit;
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
        unit.setText((CharSequence) lst_unittype.get(Integer.parseInt(item.getUnittype())));
        itemname.setText(item.getitemname());
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        TextView textView6 = itemname;
        sb.append(item.getofferprice());
        offerprice.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(rupee);
        sb2.append(item.getorginalprice());
        orginalprice.setText(sb2.toString());
        minqty.setText(item.getMinorder());
        TextView textView7 = offerprice;
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
        RequestManager with = Glide.with(context);
        StringBuilder sb3 = new StringBuilder();
        TextView textView8 = orginalprice;
        sb3.append(Temp.weblink);
        sb3.append("productpicsmall_admin/");
        sb3.append(item.getsn());
        sb3.append("_1.jpg");
        with.load(sb3.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Productlist_FeedItem productlist_FeedItem = (Productlist_FeedItem) Productlist_ListAdapter.feedItems.get(i);
                Productlist_FeedItem item = (Productlist_FeedItem) Productlist_ListAdapter.feedItems.get(i);
                Temp.img_title = item.getitemname();
                Temp.img_imgsig = item.getimgsig1();
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("productpicsmall_admin/");
                sb.append(item.getsn());
                sb.append("_1.jpg");
                Temp.img_link = sb.toString();
                Intent i = new Intent(Productlist_ListAdapter.context, Image_Viewer.class);
                i.setFlags(268435456);
                Productlist_ListAdapter.context.startActivity(i);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Productlist_FeedItem productlist_FeedItem = (Productlist_FeedItem) Productlist_ListAdapter.feedItems.get(i);
                    Productlist_FeedItem item = (Productlist_FeedItem) Productlist_ListAdapter.feedItems.get(i);
                    Productlist_ListAdapter.txtproductid = item.getsn();
                    Productlist_ListAdapter.pos = i;
                    Productlist_ListAdapter.showalert_delete("Are you sure want to delete this product ?");
                } catch (Exception e) {
                }
            }
        });
        edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Productlist_FeedItem productlist_FeedItem = (Productlist_FeedItem) Productlist_ListAdapter.feedItems.get(i);
                Productlist_FeedItem item = (Productlist_FeedItem) Productlist_ListAdapter.feedItems.get(i);
                Temp.edit_catid = item.getcatid();
                Temp.edit_productid = item.getsn();
                Temp.edit_itemname = item.getitemname();
                Temp.edit_offerprice = item.getofferprice();
                Temp.edit_orginalprice = item.getorginalprice();
                Temp.edit_dscription = item.getdiscription();
                Temp.edit_imgsig1 = item.getimgsig1();
                Temp.edit_imgsig2 = item.getimgsig1();
                Temp.edit_imgsig3 = item.getimgsig1();
                Temp.edit_minimum = item.getMinorder();
                Temp.edit_unit = item.getUnittype();
                Temp.edit_itemkeywords = item.getItemkeyword();
                Temp.isproductedit = 1;
                Intent i = new Intent(Productlist_ListAdapter.context, Add_Product.class);
                i.setFlags(268435456);
                Productlist_ListAdapter.context.startActivity(i);
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
                if (Productlist_ListAdapter.cd.isConnectingToInternet()) {
                    new delete_product().execute(new String[0]);
                } else {
                    Toasty.info(Productlist_ListAdapter.context, Temp.nointernet, Toast.LENGTH_SHORT).show();
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
