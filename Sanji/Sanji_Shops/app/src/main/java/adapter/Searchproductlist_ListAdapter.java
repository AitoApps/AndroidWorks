package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.R;
import com.sanji_shop.Temp;
import com.sanji_shop.UserDatabaseHandler;
import com.sanji_shops.R;
import com.sanji_shops.Temp;

import data.Search_Productlist_FeedItem;
import es.dmoral.toasty.Toasty;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class Searchproductlist_ListAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_NULL = 2;
    private Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    Typeface face;
    Typeface face1;
    Typeface face2;
    public List<Search_Productlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txt_catid = "";
    public String txt_discription = "";
    public String txt_imgsig = "";
    public String txt_itemname = "";
    public String txt_keyword = "";
    public String txt_minqty = "";
    public String txt_minqty1 = "";
    public String txt_offerprice = "";
    public String txt_offerprice1 = "";
    public String txt_originalprice = "";
    public String txt_originalprice1 = "";
    public String txt_unittype = "";
    public String txt_unittype1 = "";
    public String txtproductid = "";
    public UserDatabaseHandler udb;

    public class update_item extends AsyncTask<String, Void, String> {
        public update_item() {
        }
        public void onPreExecute() {
            pd.setMessage("Updating...");
            pd.setCancelable(false);
            pd.show();
        }
        public String doInBackground(String... arg0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("addproductbyshops_fromadmin.php");
                String link = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode("item", "UTF-8"));
                sb2.append("=");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(txt_catid);
                sb3.append(":%");
                sb3.append(udb.get_shopid());
                sb3.append(":%");
                sb3.append(txt_itemname);
                sb3.append(":%");
                sb3.append(txt_offerprice1);
                sb3.append(":%");
                sb3.append(txt_originalprice1);
                sb3.append(":%");
                sb3.append(txt_discription);
                sb3.append(":%");
                sb3.append(txt_unittype1);
                sb3.append(":%");
                sb3.append(txt_minqty1);
                sb3.append(":%");
                sb3.append(txt_keyword);
                sb3.append(":%");
                sb3.append(txtproductid);
                sb3.append(":%");
                sb3.append(txt_imgsig);
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
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
                if (result.contains("ok")) {
                    Context access$100 = context;
                    StringBuilder sb = new StringBuilder();
                    sb.append("നന്ദി ! ");
                    sb.append(txt_itemname);
                    sb.append(" എന്ന ഉല്‍പ്പന്നം ചേര്‍ത്തിരിക്കുന്നു");
                    Toasty.info(access$100, sb.toString(), Toast.LENGTH_SHORT).show();
                } else if (result.contains("notpermit")) {
                    Toasty.info(context, "ക്ഷമിക്കണം ! കാലാവധി കഴിഞ്ഞിരിക്കുന്നു.ദയവായി അഡ്മിനുമായി ബന്ധപ്പെടുക ", Toast.LENGTH_SHORT).show();
                } else if (result.contains("deleted")) {
                    Toasty.info(context, "ക്ഷമിക്കണം ! താങ്കള്‍ക്ക് പ്രൊഡക്ട് ചേര്‍ക്കാന്‍ കഴിയില്ല ", Toast.LENGTH_SHORT).show();
                } else if (result.contains("exceeed")) {
                    String[] s = result.split(",");
                    Context access$1002 = context;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("ക്ഷമിക്കണം ! താങ്കള്‍ക്ക് പരമാവധി ");
                    sb2.append(s[1]);
                    sb2.append(" ഉല്‍പ്പന്നങ്ങള്‍ മാത്രമേ ഒരേ സമയം നല്‍കുവാന്‍ കഴിയുകയൊള്ളൂ ");
                    Toasty.info(access$1002, sb2.toString(), Toast.LENGTH_SHORT).show();
                } else if (result.contains("exit")) {
                    String[] split = result.split(",");
                    Context access$1003 = context;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("ക്ഷമിക്കണം ! ");
                    sb3.append(txt_itemname);
                    sb3.append(" എന്ന ഉത്പ്പന്നം ഇതിന് മുമ്പ് താങ്കള്‍ ചേര്‍ത്തിട്ടുണ്ട്‌ ");
                    Toasty.info(access$1003, sb3.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.info(context, Temp.tempproblem, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class viewHolder extends ViewHolder {
        ImageView image;
        TextView itemname;
        RelativeLayout layout;

        public viewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
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

    public Searchproductlist_ListAdapter(Activity activity2, List<Search_Productlist_FeedItem> feedItems2) {
        activity = activity2;
        context = activity2.getApplicationContext();
        feedItems = feedItems2;
        pd = new ProgressDialog(activity2);
        cd = new ConnectionDetecter(context);
        udb = new UserDatabaseHandler(context);
        face = Typeface.createFromAsset(context.getAssets(), "font/Rachana.ttf");
        face1 = Typeface.createFromAsset(context.getAssets(), "font/proxibold.otf");
        face2 = Typeface.createFromAsset(context.getAssets(), "font/proximanormal.ttf");
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new viewHolder(LayoutInflater.from(context).inflate(R.layout.custom_productlist_foradmin, parent, false));
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
                Search_Productlist_FeedItem item = (Search_Productlist_FeedItem) feedItems.get(position);
                viewHolder viewHolder2 = (viewHolder) holder;
                String string = context.getResources().getString(R.string.Rs);
                viewHolder2.itemname.setText(item.getitemname());
                RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
                RequestManager with = Glide.with(context);
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("productpicsmall_admin/");
                sb.append(item.getsn());
                sb.append("_1.jpg");
                with.load(sb.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder2.image);
                viewHolder2.layout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Search_Productlist_FeedItem search_Productlist_FeedItem = (Search_Productlist_FeedItem) feedItems.get(position);
                        Search_Productlist_FeedItem item = (Search_Productlist_FeedItem) feedItems.get(position);
                        txtproductid = item.getsn();
                        pos = position;
                        txt_itemname = item.getitemname();
                        txt_imgsig = item.getimgsig1();
                        txt_offerprice = item.getofferprice();
                        txt_originalprice = item.getorginalprice();
                        txt_minqty = item.getMinorder();
                        txt_unittype = item.getUnittype();
                        txt_keyword = item.getItemkeyword();
                        txt_catid = item.getcatid();
                        txt_discription = item.getdiscription();
                        add_product();
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    public void updateList(List<Search_Productlist_FeedItem> list) {
        feedItems = list;
        notifyDataSetChanged();
    }

    public void add_product() {
        Dialog dialog1 = new Dialog(activity);
        dialog1.requestWindowFeature(1);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setContentView(R.layout.custom_addproduct_fromadmin);
        EditText originalprice = (EditText) dialog1.findViewById(R.id.orginalprice);
        TextView txtofferprice = (TextView) dialog1.findViewById(R.id.txtofferprice);
        EditText offerprice = (EditText) dialog1.findViewById(R.id.offerprice);
        Button update = (Button) dialog1.findViewById(R.id.add);
        TextView txtunit = (TextView) dialog1.findViewById(R.id.txtunit);
        Spinner unit = (Spinner) dialog1.findViewById(R.id.unit);
        TextView txtminimum = (TextView) dialog1.findViewById(R.id.txtminimum);
        EditText minimum = (EditText) dialog1.findViewById(R.id.minimum);
        TextView itemname = (TextView) dialog1.findViewById(R.id.itemname);
        TextView txtorginalprice=dialog1.findViewById(R.id.txtorginalprice);
        txtorginalprice.setTypeface(face1);
        txtofferprice.setTypeface(face1);
        txtunit.setTypeface(face1);
        txtminimum.setTypeface(face1);
        itemname.setTypeface(face1);
        originalprice.setTypeface(face2);
        offerprice.setTypeface(face2);
        minimum.setTypeface(face2);
        itemname.setTypeface(face2);
        itemname.setText(txt_itemname);
        originalprice.setText(txt_originalprice);
        offerprice.setText(txt_offerprice);
        originalprice.setSelection(originalprice.getText().toString().length());
        minimum.setText(txt_minqty);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, Temp.lst_unittype) {
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
        unit.setSelection(Integer.parseInt(txt_unittype));
        unit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                txt_unittype1 =arg2+"";
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minimum.getText().toString().equalsIgnoreCase("")) {
                    Toasty.info(context, "Please enter minimum price", Toast.LENGTH_SHORT).show();
                    minimum.requestFocus();
                }
                else if (originalprice.getText().toString().equalsIgnoreCase("")) {
                    Toasty.success(context, "Please enter original price", Toast.LENGTH_SHORT).show();
                    originalprice.requestFocus();
                } else if (offerprice.getText().toString().equalsIgnoreCase("")) {
                    Toasty.success(context, "Please enter offer price", Toast.LENGTH_SHORT).show();
                    offerprice.requestFocus();
                } else if (cd.isConnectingToInternet()) {
                    txt_offerprice1 = offerprice.getText().toString();
                    txt_originalprice1 = originalprice.getText().toString();
                    txt_minqty1 = minimum.getText().toString();
                    new update_item().execute(new String[0]);
                    dialog1.dismiss();
                } else {
                    Toasty.info(context, Temp.nointernet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog1.setCancelable(true);
        dialog1.show();
    }
}
