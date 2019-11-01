package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.dialybill_shops.Add_AdminProducts;
import com.dialybill_shops.ConnectionDetecter;
import com.dialybill_shops.DatabaseHandler;
import com.dialybill_shops.Delivery_Boy;
import com.dialybill_shops.ProductDatabase;
import com.dialybill_shops.R;
import com.dialybill_shops.Temp;
import com.dialybill_shops.UserDatabaseHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import data.Productlist_FeedItem;
import data.Productlist_admin_FeedItem;
import es.dmoral.toasty.Toasty;

public class Productlist_Admin_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    ConnectionDetecter cd;
    public Context context;
    public DatabaseHandler db;
    Typeface face;
    public List<Productlist_admin_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    UserDatabaseHandler udb;
    ProductDatabase pdb;
    public String ogunit1="",itemid1="",itemname1="",itemimgsig1="",shopunit="",shopminunit="",shopprice="";
    public Productlist_Admin_Adapter(Activity activity2, List<Productlist_admin_FeedItem> feedItems2) {
        activity = activity2;
        feedItems = feedItems2;
        context = activity2.getApplicationContext();
        db = new DatabaseHandler(context);
        pdb=new ProductDatabase(context);
        udb = new UserDatabaseHandler(context);
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
        face = Typeface.createFromAsset(context.getAssets(), "proxibold.otf");
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        TextView itemname;
        ImageView image;

        public viewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            itemname = (TextView) itemView.findViewById(R.id.itemname);
            image = itemView.findViewById(R.id.image);
        }
    }

    public class viewHolderFooter extends RecyclerView.ViewHolder {
        RelativeLayout layout1;

        public viewHolderFooter(View itemView) {
            super(itemView);
            layout1 = (RelativeLayout) itemView.findViewById(R.id.layout1);
        }
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_admin_productlist, parent, false));
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

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof viewHolder) {
            try {
                if (position == feedItems.size() - 1 && feedItems.size() > 40) {
                    ((Add_AdminProducts) activity).loadmore();
                }
                Productlist_admin_FeedItem item = (Productlist_admin_FeedItem) feedItems.get(position);
                viewHolder viewHolder = (viewHolder) holder;
                viewHolder.itemname.setTypeface(face);
                viewHolder.itemname.setText(item.getItemname());

                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getImgsig()));
                Glide.with(context).load(Temp.weblink+"productpicsmall/"+item.getSn()+".jpg").apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.image);


                viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ogunit1=item.getUnit();
                        itemid1=item.getSn();
                        itemname1=item.getItemname();
                        itemimgsig1=item.getImgsig();
                        add_product();
                    }
                });

            } catch (Exception e) {
            }
        }
    }

    public void add_product() {
        Dialog dialog1 = new Dialog(activity);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.custom_addproduct_fromadmin);
        EditText price = (EditText) dialog1.findViewById(R.id.price);
        TextView txtprice = (TextView) dialog1.findViewById(R.id.txtprice);
        Button update = (Button) dialog1.findViewById(R.id.add);
        TextView txtunit = (TextView) dialog1.findViewById(R.id.txtunit);
        Spinner unit = (Spinner) dialog1.findViewById(R.id.unit);
        EditText minimum = (EditText) dialog1.findViewById(R.id.minimum);
        TextView itemname = (TextView) dialog1.findViewById(R.id.itemname);

        final List<String> lst_unittypes = new ArrayList<>();

        if(ogunit1.equalsIgnoreCase("1")) //kg
        {
            lst_unittypes.add("gm");
            lst_unittypes.add("Kg");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("2")) //Nos
        {
            lst_unittypes.add("Number");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("3")) //Ltr
        {
            lst_unittypes.add("Mi.Ltr");
            lst_unittypes.add("Ltr");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("4")) //portion
        {
            lst_unittypes.add("Qurator");
            lst_unittypes.add("Half");
            lst_unittypes.add("Full");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("5")) //Box
        {
            lst_unittypes.add("Box");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("6")) //Pair
        {
            lst_unittypes.add("Pair");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("7")) //Set
        {
            lst_unittypes.add("Set");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("8")) //Set
        {
            lst_unittypes.add("Bottle");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("9")) //Set
        {
            lst_unittypes.add("Meter");
            lst_unittypes.add("Other");
        }
        else if(ogunit1.equalsIgnoreCase("10")) //Set
        {
            lst_unittypes.add("Weight");
            lst_unittypes.add("Other");
        }
        itemname.setTypeface(face);
        itemname.setText(itemname1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,lst_unittypes) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#000000"));
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#000000"));
                ((TextView) v).setTextSize(16.0f);
                ((TextView) v).setTypeface(face);
                return v;
            }
        };
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(dataAdapter2);
        unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                shopunit =lst_unittypes.get(arg2);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minimum.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(context, "Please enter minimum unit", Toast.LENGTH_SHORT).show();
                    minimum.requestFocus();
                }
                else if (price.getText().toString().equalsIgnoreCase("")) {
                    Toasty.success(context, "Please enter price", Toast.LENGTH_SHORT).show();
                    price.requestFocus();
                }
                else if (cd.isConnectingToInternet()) {
                    shopminunit = minimum.getText().toString();
                    shopprice=price.getText().toString();
                    new updateproduct().execute();
                    dialog1.dismiss();
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog1.setCancelable(true);
        dialog1.show();
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                d.dismiss();
            }
        }, time);
    }
    public class updateproduct extends AsyncTask<String, Void, String> {
        public void onPreExecute() {
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
            timerDelayRemoveDialog(50000, pd);
        }
        public String doInBackground(String... arg0) {
            try {
                String link= Temp.weblink+"addshopproductfromadmin.php";
                String data  = URLEncoder.encode("item", "UTF-8")
                        + "=" + URLEncoder.encode(udb.get_shopid()+":%"+itemid1+":%"+shopunit+":%"+shopminunit+":%"+shopprice, "UTF-8");
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
                if (result.contains(",ok")) {
                    String[] s=result.split(",");
                    pdb.addproduct(s[0],itemid1,itemname1,shopprice,shopminunit,shopunit,itemimgsig1,"1",ogunit1);
                    Toasty.info(context, "Add to your product list", Toast.LENGTH_SHORT).show();

                }
                else if(result.contains("exist"))
                {
                    Toasty.info(context, "Sorry ! This Product is already exist in your product list", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
