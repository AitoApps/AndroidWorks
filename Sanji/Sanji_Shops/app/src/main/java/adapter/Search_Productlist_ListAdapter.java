package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sanji_shop.Add_Product;
import com.sanji_shop.ConnectionDetecter;
import com.sanji_shop.DatabaseHandler;
import com.sanji_shop.Image_Viewer;
import com.sanji_shop.R;
import com.sanji_shop.Temp;
import data.Search_Productlist_FeedItem;
import java.util.List;

public class Search_Productlist_ListAdapter extends BaseAdapter {
    private Activity activity;
    public ConnectionDetecter cd;
    public Context context;
    DatabaseHandler db;
    Typeface face;
    public List<Search_Productlist_FeedItem> feedItems;
    private LayoutInflater inflater;
    ProgressDialog pd;
    int pos = 0;
    public String txtproductid = "";

    public Search_Productlist_ListAdapter(Activity activity2, List<Search_Productlist_FeedItem> feedItems2) {
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
            convertView2 = inflater.inflate(R.layout.custom_adminproductlist, null);
        } else {
            convertView2 = convertView;
        }
        ImageView image = (ImageView) convertView2.findViewById(R.id.image);
        TextView itemname = (TextView) convertView2.findViewById(R.id.itemname);
        TextView offerprice = (TextView) convertView2.findViewById(R.id.offerprice);
        TextView orginalprice = (TextView) convertView2.findViewById(R.id.orginalprice);
        Button choose = (Button) convertView2.findViewById(R.id.choose);
        TextView txtofferprice = (TextView) convertView2.findViewById(R.id.txtofferprice);
        TextView txtofferpricedot = (TextView) convertView2.findViewById(R.id.txtofferpricedot);
        TextView txtorginalprice = (TextView) convertView2.findViewById(R.id.txtorginalprice);
        TextView txtorginalpricedot = (TextView) convertView2.findViewById(R.id.txtorginalpricedot);
        TextView txtunit = (TextView) convertView2.findViewById(R.id.txtunit);
        TextView unit = (TextView) convertView2.findViewById(R.id.unit);
        TextView txtminqty = (TextView) convertView2.findViewById(R.id.txtminqty);
        TextView minqty = (TextView) convertView2.findViewById(R.id.minqty);
        View convertView3 = convertView2;
        itemname.setTypeface(face);
        offerprice.setTypeface(face);
        orginalprice.setTypeface(face);
        txtunit.setTypeface(face);
        txtminqty.setTypeface(face);
        txtofferprice.setTypeface(face);
        txtofferpricedot.setTypeface(face);
        txtorginalprice.setTypeface(face);
        txtorginalpricedot.setTypeface(face);
        Search_Productlist_FeedItem item = (Search_Productlist_FeedItem) feedItems.get(i);
        TextView textView = txtofferprice;
        TextView textView2 = txtofferpricedot;
        String rupee = context.getResources().getString(R.string.Rs);
        TextView textView3 = txtorginalprice;
        unit.setText((CharSequence) Temp.lst_unittype.get(Integer.parseInt(item.getUnittype())));
        itemname.setText(item.getitemname());
        StringBuilder sb = new StringBuilder();
        sb.append(rupee);
        sb.append(item.getofferprice());
        offerprice.setText(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(rupee);
        sb2.append(item.getorginalprice());
        orginalprice.setText(sb2.toString());
        minqty.setText(item.getMinorder());
        TextView textView4 = itemname;
        RequestOptions rep = new RequestOptions().signature(new ObjectKey(item.getimgsig1()));
        RequestManager with = Glide.with(context);
        StringBuilder sb3 = new StringBuilder();
        TextView textView5 = offerprice;
        sb3.append(Temp.weblink);
        sb3.append("productpicsmall_admin/");
        sb3.append(item.getsn());
        sb3.append("_1.jpg");
        with.load(sb3.toString()).apply(rep).transition(DrawableTransitionOptions.withCrossFade()).into(image);
        image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Search_Productlist_FeedItem search_Productlist_FeedItem = (Search_Productlist_FeedItem) Search_Productlist_ListAdapter.feedItems.get(i);
                Search_Productlist_FeedItem item = (Search_Productlist_FeedItem) Search_Productlist_ListAdapter.feedItems.get(i);
                Temp.img_title = item.getitemname();
                Temp.img_imgsig = item.getimgsig1();
                StringBuilder sb = new StringBuilder();
                sb.append(Temp.weblink);
                sb.append("productpicsmall_admin/");
                sb.append(item.getsn());
                sb.append("_1.jpg");
                Temp.img_link = sb.toString();
                Intent i = new Intent(Search_Productlist_ListAdapter.context, Image_Viewer.class);
                i.setFlags(268435456);
                Search_Productlist_ListAdapter.context.startActivity(i);
            }
        });
        choose.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Search_Productlist_FeedItem search_Productlist_FeedItem = (Search_Productlist_FeedItem) Search_Productlist_ListAdapter.feedItems.get(i);
                Search_Productlist_FeedItem item = (Search_Productlist_FeedItem) Search_Productlist_ListAdapter.feedItems.get(i);
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
                Temp.isproductedit = 2;
                Intent i = new Intent(Search_Productlist_ListAdapter.context, Add_Product.class);
                i.setFlags(268435456);
                Search_Productlist_ListAdapter.context.startActivity(i);
            }
        });
        return convertView3;
    }
}
